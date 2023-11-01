package com.example.roomwordsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomwordsample.databinding.ActivityWordListBinding

class WordListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordListBinding
    private val viewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getStringExtra(NewWordActivity.NEW_WORD)?.let { newWord ->
                    viewModel.insert(Word(newWord))
                }
            }
        }

        binding.fab.setOnClickListener {
            val newWordActivityIntent = Intent(this, NewWordActivity::class.java)
            launcher.launch(newWordActivityIntent)
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = WordListAdapter()
        binding.recyclerview.adapter = adapter

        viewModel.allWordsLiveData.observe(this) { updatedWordList ->
            updatedWordList?.let {
                adapter.submitList(it)
            }
        }

    }
}