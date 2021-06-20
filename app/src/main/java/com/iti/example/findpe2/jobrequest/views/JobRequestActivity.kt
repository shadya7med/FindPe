package com.iti.example.findpe2.jobrequest.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.ActivityJobRequestBinding
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.JobType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val TAG = "JobRequestActivity"
class JobRequestActivity : AppCompatActivity() {
    lateinit var binding: ActivityJobRequestBinding
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = null
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
                try {
                    val id = FirebaseAuth.getInstance().currentUser?.uid!!
                    val user = TripApi.getUserByID(id)
                    TripApi.addANewJob(com.iti.example.findpe2.pojos.Job(
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
                        null))
                }catch (t: Throwable){
                    Log.i(TAG, "onCreate: ${t.localizedMessage}")
                }
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
}