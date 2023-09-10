package com.example.adi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adi.models.HinduismItem
import com.example.adi.models.WarriorsItem
import com.example.adi.network.ApiResponse
import com.example.adi.network.NetworkStatusWrapper
import com.example.adi.repository.HinduismRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HinduismDetailsViewModel @Inject constructor(
    private val repository: HinduismRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    val hinduismDetailsStateFlow: StateFlow<ApiResponse<List<HinduismItem>>>
        get() = repository.hinduismDetailsStateFlow

    val warriorsDataListState: LiveData<NetworkStatusWrapper<List<WarriorsItem>>>
        get() = repository.data

    init {
        val kingName = savedStateHandle.get<String>("kingName") ?: "Maharaja Suraj Mal"
        loadWarriorsData(kingName)
    }

    fun loadWarriorsData(query: String) {
        viewModelScope.launch {
            repository.getHinduismDetails(query)
        }
    }

}