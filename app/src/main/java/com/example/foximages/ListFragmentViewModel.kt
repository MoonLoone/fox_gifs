package com.example.foximages

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.PagingData
import androidx.room.Room
import com.example.foximages.local.AppDatabase
import com.example.foximages.pojo.DataFromApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class ListFragmentViewModel(application: Application, private val gifsRepository: GifsRepository) :
    AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(true)

    // FIXME: есть extension, который создается ReadOnlyFlow
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val gifs = MutableStateFlow(emptyFlow<PagingData<DataFromApi>>())
    val gifsState: StateFlow<Flow<PagingData<DataFromApi>>> = gifs

    fun load() = effect {
        _isLoading.value = true
        gifs.value = gifsRepository.gifs()
        _isLoading.value = false
    }

    fun load(name: String) = effect {
        _isLoading.value = true
        gifsRepository.loadGifs(name)
        gifs.value = gifsRepository.gifs()
        _isLoading.value = false
    }

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ListFragmentViewModel(
                    application,
                    gifsRepository = GifsRepository(
                        Room.databaseBuilder(
                            application,
                            AppDatabase::class.java, "database.db"
                        ).build()
                    ),
                ) as T
            }
        }
    }

}