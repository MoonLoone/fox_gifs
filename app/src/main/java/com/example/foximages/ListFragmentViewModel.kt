package com.example.foximages

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.room.Room
import com.example.foximages.local.AppDatabase
import com.example.foximages.pojo.DataFromAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch


class ListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val gifsRepository = GifsRepository(
        Room.databaseBuilder(
            application,
            AppDatabase::class.java, "database.db"
        ).build()
    )

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val gifs = MutableStateFlow(emptyFlow<PagingData<DataFromAPI>>())
    val gifsState: StateFlow<Flow<PagingData<DataFromAPI>>> = gifs

    fun load() = effect {
        _isLoading.value = true
        gifs.value = gifsRepository.allGifs()
        _isLoading.value = false
    }

    fun loadByName(name:String) = effect{
        _isLoading.value = true
        gifsRepository.getGifsByName(name)
        gifs.value = gifsRepository.allGifs()
        _isLoading.value = false
    }

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

}