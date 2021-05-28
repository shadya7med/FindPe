package com.iti.example.findpe2.home.chat.chatInstance.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.databinding.ActivityChatPageBinding
import com.iti.example.findpe2.home.chat.chatInstance.viewModels.ChatPageViewModel
import com.iti.example.findpe2.home.chat.chatInstance.viewModels.ChatPageViewModelFactory
import com.iti.example.findpe2.home.chat.chatRoomsList.views.ChatFragment
import com.iti.example.findpe2.pojos.ChatRoom
import com.iti.example.findpe2.utils.setAllClickable

class ChatPageActivity : AppCompatActivity() {

    lateinit var binding : ActivityChatPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setLoading()
        val chatRoom = intent.getParcelableExtra<ChatRoom>(ChatFragment.CHAT_ROOM_KEY)
        val chatPageViewModel = ViewModelProvider(this,ChatPageViewModelFactory(chatRoom!!)).get(ChatPageViewModel::class.java)

        /*supportActionBar?.let {
            it.title = chatRoom?.let { chatRoom ->
                chatRoom.destinationUsername
            }
            it.setDisplayHomeAsUpEnabled(true)
        }*/
        binding.chatRoom = chatRoom
        binding.chatPageViewModel = chatPageViewModel
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        val messagesListAdapter = MessagesListAdapter(Firebase.auth.currentUser?.uid!!)
        messagesListAdapter.registerAdapterDataObserver(ScrollToBottomObserver(binding.msgListRcyViewChatPageChatActivity,messagesListAdapter,layoutManager))
        binding.msgListRcyViewChatPageChatActivity.layoutManager = layoutManager
        binding.msgListRcyViewChatPageChatActivity.adapter = messagesListAdapter

        chatPageViewModel.messagesList.observe(this){
            it?.let {
                clearLoading()
                messagesListAdapter.submitList(it)
            }
        }
        chatPageViewModel.errorMsg.observe(this){
            it?.let {
                clearLoading()
                binding.msgListRcyViewChatPageChatActivity.visibility = View.GONE
                binding.topLayoutClChatPageChatActivity.visibility = View.GONE
                binding.bottomLayoutClChatPageChatActivity.visibility = View.GONE
                binding.noChatImgViewChatPageChatActivity.visibility = View.GONE
                binding.noChatTxtViewChatPageChatActivity.visibility = View.GONE

            }
        }

        binding.msgEditTxtChatPageChatActivity.addTextChangedListener(SendButtonObserver(binding.sendMsgImgViewChatPageChatActivity))
        binding.sendMsgImgViewChatPageChatActivity.setOnClickListener {
            chatPageViewModel.onSendMsg(binding.msgEditTxtChatPageChatActivity.text.toString())
        }

        chatPageViewModel.navigateUptoHome.observe(this){
            it?.let{
                finish()
                chatPageViewModel.onDoneNavigationToHome()
            }
        }

        //chatPageViewModel.getChatPageMessages()
        chatPageViewModel.onSendMsgSuccess.observe(this){
            it?.let {
                if (it){
                    binding.msgEditTxtChatPageChatActivity.setText("")
                }
            }
        }


        setContentView(binding.root)
    }

    private fun setLoading() {
        binding.progressBarChatPageChatActivity.visibility = View.VISIBLE
        binding.root.setAllClickable(false)
    }

    private fun clearLoading() {
        binding.progressBarChatPageChatActivity.visibility = View.GONE
        binding.root.setAllClickable(true)

    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
    }*/
}