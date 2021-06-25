package com.iti.example.findpe2.home.chat.chatInstance.views

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Constants
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.ActivityChatPageBinding
import com.iti.example.findpe2.home.chat.chatInstance.viewModels.ChatPageViewModel
import com.iti.example.findpe2.home.chat.chatInstance.viewModels.ChatPageViewModelFactory
import com.iti.example.findpe2.pojos.ChatRoom


class ChatPageActivity : AppCompatActivity() {

    //lateinit var binding : ActivityChatPageBinding
    lateinit var chatPageViewModel: ChatPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatPageBinding.inflate(layoutInflater)
        //setLoading()
        val chatRoom = intent.getParcelableExtra<ChatRoom>(Keys.CHAT_ROOM_KEY)
        chatPageViewModel = ViewModelProvider(this, ChatPageViewModelFactory(chatRoom!!)).get(
            ChatPageViewModel::class.java
        )
        binding.lifecycleOwner = this
        binding.chatRoom = chatRoom
        binding.chatPageViewModel = chatPageViewModel
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        val messagesListAdapter = MessagesListAdapter(Firebase.auth.currentUser?.uid!!)
        messagesListAdapter.registerAdapterDataObserver(
            ScrollToBottomObserver(
                binding.msgListRcyViewChatPageChatActivity,
                messagesListAdapter,
                layoutManager
            )
        )
        binding.msgListRcyViewChatPageChatActivity.layoutManager = layoutManager
        binding.msgListRcyViewChatPageChatActivity.adapter = messagesListAdapter

        /*chatPageViewModel.messagesList.observe(this){
            it?.let {
                clearLoading()
                messagesListAdapter.submitList(it)
            }
        }*/
        chatPageViewModel.errorMsg.observe(this) {
            it?.let {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()

            }
        }

        binding.msgEditTxtChatPageChatActivity.addTextChangedListener(SendButtonObserver(binding.sendMsgImgViewChatPageChatActivity))
        binding.sendMsgImgViewChatPageChatActivity.setOnClickListener {
            chatPageViewModel.onSendMsg(binding.msgEditTxtChatPageChatActivity.text.toString())
            binding.msgEditTxtChatPageChatActivity.setText("")
        }
        binding.openMapBtnChat.setOnClickListener {
            //check for location permission and location service enabled
            if (!checkLocationService()) {
                checkLocationPermission()
            } else {
                //permissions already granted invoke opening map
                chatPageViewModel.onPermissionsGranted()
            }
        }

        chatPageViewModel.onPermissionsGrantedEvent.observe(this) {
            it?.let {
                val gmmIntentUri = Uri.parse("geo:${it.first},${it.second}")
                /*val mapIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=${it.first},${it.second}")
                )*/
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                try {
                    startActivity(mapIntent)
                } catch (e: ActivityNotFoundException) {
                    Log.i("Chat", e.localizedMessage)
                }
                chatPageViewModel.onDoneOpeningMap()

            }
        }

        chatPageViewModel.navigateUptoHome.observe(this) {
            it?.let {
                finish()
                chatPageViewModel.onDoneNavigationToHome()
            }
        }


        chatPageViewModel.onSendMsgSuccess.observe(this) {
            it?.let {
                if (it) {
                    scrollToBottom(binding.msgListRcyViewChatPageChatActivity)
                }
                chatPageViewModel.onDoneSendingMsg()
            }
        }

        scrollToBottom(binding.msgListRcyViewChatPageChatActivity)

        setContentView(binding.root)
    }

    /*private fun setLoading() {
        binding.progressBarChatPageChatActivity.visibility = View.VISIBLE
        binding.root.setAllClickable(false)
    }

    private fun clearLoading() {
        binding.progressBarChatPageChatActivity.visibility = View.GONE
        binding.root.setAllClickable(true)

    }*/
    private fun scrollToBottom(recyclerView: RecyclerView) {
        // scroll to last item to get the view of last item
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val adapter = recyclerView.adapter
        val lastItemPosition = adapter!!.itemCount - 1
        layoutManager!!.scrollToPositionWithOffset(lastItemPosition, 0)
        recyclerView.post { // then scroll to specific offset
            val target = layoutManager.findViewByPosition(lastItemPosition)
            if (target != null) {
                val offset = recyclerView.measuredHeight - target.measuredHeight
                layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
            }
        }
    }


    //Location
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    }
                    .create()
                    .apply {
                        setOnShowListener {
                            this.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                                application.resources.getColor(
                                    R.color.title_text_custom_color
                                )
                            )
                        }
                    }
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()
            }
        }
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                Constants.MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    //check for location service
                    if (checkLocationService()) {
                        //permission and service enabled open map with the companion location
                        //homeViewModel.saveCurrentCompanionLocation()
                        //Update from onResume
                    } else {
                        openLocationSettings()
                    }
                    Log.i("Home", "permission granted")

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(
                        this,
                        "permission denied other users will not be able to locate you on their maps",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    private fun checkLocationService(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //check location service enabled or not
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val result = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!result) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Location service must be enabled",
                    Snackbar.LENGTH_LONG
                ).show()
                openLocationSettings()
            }
            return result
        }
        return false
    }

    private fun openLocationSettings() {
        AlertDialog.Builder(this)
            .setTitle("Location Service Needed")
            .setMessage("This app needs the Location service to be enabled to use the open map feature")
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                //Prompt the user once explanation has been shown
                //open location settings
                val openLocationSettingsIntent =
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(openLocationSettingsIntent)
            }
            .create()
            .apply {
                setOnShowListener {
                    this.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        application.resources.getColor(
                            R.color.title_text_custom_color
                        )
                    )
                }
            }
            .show()
    }


}