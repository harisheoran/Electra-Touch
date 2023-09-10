package com.example.adi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adi.models.Hinduism
import com.example.adi.models.King
import com.example.adi.network.ApiResponse
import com.example.adi.network.NetworkStatusWrapper
import com.example.adi.repository.HinduismRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HinduismViewModel @Inject constructor(private val repository: HinduismRepository) :
    ViewModel() {

    // State Flow for Home Screen Warrior response from Repository
    val hinduismStateFlow: StateFlow<ApiResponse<List<Hinduism>>>
        get() = repository.hinduismStateFlow

    val warriorsNameListState: LiveData<NetworkStatusWrapper<List<King>>>
        get() = repository.warriorsList

    val favourites: LiveData<List<King>>
        get() = repository.favourites

    init {
        loadWarriorsNames("$.kings[*]")
    }

    // loading Warriors Home Screen Data by passing the query
    fun loadWarriorsNames(query: String) {
        viewModelScope.launch {
            repository.getHinduism(query)
        }
    }

    fun saveFavouriteKing(favouriteKing: King) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.saveFavourite(favouriteKing)
            }
        }
    }
}