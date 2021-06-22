package com.iti.example.findpe2.jobsendrequest.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.ActivityJobRequestBinding
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.*
import com.iti.example.findpe2.utils.setAllClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val TAG = "JobRequestActivity"

class JobRequestActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    lateinit var binding: ActivityJobRequestBinding
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var forACompanion = intent.getBooleanExtra(Keys.FOR_A_COMPANION_KEY, false)
        val jobOffer = intent.getParcelableExtra<SentJobRequest>(Keys.JOB_OFFER)
        if(jobOffer != null){
            binding.deleteBtn.visibility = View.VISIBLE
            binding.jobDescriptionText.setText(jobOffer.desc)
            binding.jobTaskText.setText(jobOffer.tasks?.get(0))
            forACompanion = true
            binding.jobRequestCost.setText(jobOffer.offer.toString())
            if(jobOffer.statue != RequestStatus.WAITING.value){
                binding.jobDescriptionText.isEnabled = false
                binding.jobTaskText.isEnabled = false
                binding.jobRequestCost.isEnabled = false
                binding.sendRequestBtn.visibility = View.INVISIBLE
            }
        }

        job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Job Request"
        binding.sendRequestBtn.setOnClickListener {
            binding.jobDescriptionText.apply {
                if (text.isEmpty()) {
                    error = "Please enter job description"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            binding.jobTaskText.apply {
                if (text.isEmpty()) {
                    error = "Please enter job tasks"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            binding.jobRequestCost.apply {
                if (text.isEmpty()) {
                    error = "Please enter job price"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            scope.launch {
                setLoading()
                val id = FirebaseAuth.getInstance().currentUser?.uid!!
                val name = FirebaseAuth.getInstance().currentUser?.displayName!!
                val image =  FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
                if (!forACompanion) {
                    try {
                        TripApi.addANewJob(
                            com.iti.example.findpe2.pojos.Job(
                                0,
                                id,
                                binding.jobDescriptionText.text.toString(),
                                binding.jobRequestCost.text.toString().toInt(),
                                "2021-06-20T17:57:20.114Z",
                                "2021-06-20T17:57:20.114Z",
                                false,
                                0,
                                0,
                                JobType.PERSONAL.value,
                                null,
                                null
                            )
                        )
                    } catch (t: Throwable) {
                        Log.i(TAG, "onCreate: ${t.localizedMessage}")
                        Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()

                    }
                } else {
                    val companion = intent.getParcelableExtra<CompanionUser>(Keys.COMPANION_ID_KEY)

                    database = FirebaseDatabase.getInstance().reference
                    database.child("ReceivedJobRequests")
                        .child(companion?.companionID ?: jobOffer?.companionID!!)
                        .child(id)
                        .setValue(
                            ReceivedJobRequest(
                                id,
                                name,
                                image,
                                binding.jobDescriptionText.text.toString(),
                                binding.jobRequestCost.text.toString().toLong(),
                                listOf(binding.jobTaskText.text.toString()),
                            )
                        )
                    database.child("SentJobRequests")
                        .child(id)
                        .child(companion?.companionID ?: jobOffer?.companionID!!)
                        .setValue(
                            SentJobRequest(
                                companion?.companionID  ?: jobOffer?.companionID!!,
                                companion?.userInfo?.name ?: jobOffer?.name,
                                binding.jobDescriptionText.text.toString(),
                                binding.jobRequestCost.text.toString().toLong(),
                                listOf(binding.jobTaskText.text.toString()),
                                RequestStatus.WAITING.value
                            )
                        )
                }

                Toast.makeText(applicationContext, "Request Sent", Toast.LENGTH_SHORT).show()
                finish()
            }


        }
        binding.deleteBtn.setOnClickListener {
            scope.launch {
                val id = FirebaseAuth.getInstance().currentUser?.uid!!
                setLoading()
                database = FirebaseDatabase.getInstance().reference
                database.child("SentJobRequests")
                    .child(id)
                    .child(jobOffer?.companionID!!)
                    .removeValue()
                database.child("ReceivedJobRequests")
                    .child(jobOffer.companionID)
                    .child(id)
                    .removeValue()
                Toast.makeText(applicationContext, "Request deleted", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    private fun setLoading() {
        binding.requestProgressBar.visibility = View.VISIBLE
        binding.requestViewGroup.setAllClickable(false)
    }

}