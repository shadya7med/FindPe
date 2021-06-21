package com.iti.example.findpe2.home.profile.bio.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.ActivityEditBioBinding
import com.iti.example.findpe2.home.profile.bio.viewmodels.EditBioViewModel
import com.iti.example.findpe2.home.profile.bio.viewmodels.EditBioViewModelFactory
import com.iti.example.findpe2.pojos.CompanionUser

class EditBioActivity : AppCompatActivity() {


    lateinit var binding: ActivityEditBioBinding
    lateinit var editBioViewModel: EditBioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBioBinding.inflate(LayoutInflater.from(this))
        binding.lifecycleOwner = this
        val currentCompanion =
            intent.getParcelableExtra<CompanionUser>(Keys.CURRENT_USER_AS_COMPANION)
        editBioViewModel = ViewModelProvider(
            this,
            EditBioViewModelFactory(currentCompanion)
        ).get(EditBioViewModel::class.java)
        binding.editBioViewModel = editBioViewModel

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = null
        }

        editBioViewModel.onUpdateBio.observe(this) {
            it?.let { isUpdated ->
                if (isUpdated) {
                    editBioViewModel.onDoneUpdatingBio()
                    val data = Intent()
                    data.putExtra(Keys.UPDATED_BIO_KEY,editBioViewModel.getUpdatedBio())
                    setResult(RESULT_OK,data)
                    finish()
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Bio is not updated try Again",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_bio_save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_CANCELED)
                finish()
                true
            }
            R.id.save_bio_item_menu -> {
                val enteredBio = binding.bioEdtTextEditBio.text.toString()
                editBioViewModel.updateBioForCompanion(enteredBio)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}