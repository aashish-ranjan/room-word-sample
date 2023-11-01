package com.example.roomwordsample

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.roomwordsample.databinding.RecyclerviewItemBinding

class WordViewHolder(private val binding: RecyclerviewItemBinding): ViewHolder(binding.root) {
    fun bind(word: Word) {
        binding.tvWord.text = word.word
    }
}