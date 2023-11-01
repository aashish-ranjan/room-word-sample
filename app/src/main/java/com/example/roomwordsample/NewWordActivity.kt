package com.example.roomwordsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.roomwordsample.databinding.ActivityNewWordBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewWordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            onSaveButtonClick()
        }
    }

    private fun onSaveButtonClick() {
        with(binding) {
            if (!editWord.text.isNullOrBlank()) {
                val resultIntent = Intent().apply {
                    putExtra(NEW_WORD, editWord.text.toString())
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Snackbar.make(root, R.string.empty_not_saved, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val NEW_WORD = "com.example.roomwordsample.newWord"
    }
}