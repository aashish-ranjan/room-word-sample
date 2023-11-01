package com.example.roomwordsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WordViewModel(private val repository: WordRepository): ViewModel() {

    init {
        val person1 = Person("Aashish", 29)
        val person2 = Person("Aashish", 30)
        val personStateFlow = MutableStateFlow(person1)

        viewModelScope.launch {
            personStateFlow.collect {
                println("vaue is ${it.name} with age ${it.age}")
            }
        }

        personStateFlow.update {
            person2
        }
    }

    val allWordsLiveData: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun insert(word: Word) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(word)
        }
    }
}


class WordViewModelFactory(private val repository: WordRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class Person(val name: String, val age: Int)