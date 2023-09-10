package com.example.adi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.adi.database.WarriorDataBase
import com.example.adi.domain.asDataBaseModel
import com.example.adi.domain.asDomainsModel
import com.example.adi.models.Hinduism
import com.example.adi.models.HinduismItem
import com.example.adi.models.King
import com.example.adi.models.WarriorsItem
import com.example.adi.network.ApiClient
import com.example.adi.network.ApiResponse
import com.example.adi.network.NetworkStatusWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HinduismRepository @Inject constructor(
    private val warriorDataBase: WarriorDataBase,
    private val apiClient: ApiClient
) {
    // 01. Warriors Data (FOR HOME SCREEN)
    // State Flow for Home Screen Warrior response from network
    private val _hinduismStateFlow = MutableStateFlow<ApiResponse<List<Hinduism>>>(
        ApiResponse(
            status = ApiResponse.Status.Loading,
            data = null,
            exception = null
        )
    )
    val hinduismStateFlow: StateFlow<ApiResponse<List<Hinduism>>>
        get() = _hinduismStateFlow

    suspend fun getHinduism(query: String) {
        val request = apiClient.getHinduism(query)
        if (request.isFailed || !request.isSucceed) {
            _hinduismStateFlow.emit(
                ApiResponse(
                    status = ApiResponse.Status.Failure,
                    data = null,
                    exception = Exception()
                )
            )
        } else {

        /*    withContext(Dispatchers.IO) {
                warriorDataBase.warriorDao().insertWarriors(request.asDataBaseModel())
            }*/
            _hinduismStateFlow.emit(
                request
            )
        }
    }

    // 02. Warriors Details (FOR DETAILS SCREEN)
    private val _hinduismDetailsStateFlow = MutableStateFlow<ApiResponse<List<HinduismItem>>>(
        ApiResponse(
            status = ApiResponse.Status.Loading,
            data = null,
            exception = null
        )
    )
    val hinduismDetailsStateFlow: StateFlow<ApiResponse<List<HinduismItem>>>
        get() = _hinduismDetailsStateFlow

    suspend fun getHinduismDetails(query: String) {
        val request = apiClient.getHinduismDetails("data[?(@.name==\"${query}\")]")

        if (request.isFailed || !request.isSucceed) {
            _hinduismDetailsStateFlow.emit(
                ApiResponse(
                    status = ApiResponse.Status.Failure,
                    data = null,
                    exception = Exception()
                )
            )
        } else {
            _hinduismDetailsStateFlow.emit(
                request
            )
        }
    }


    private val _warriorsList = MutableLiveData<NetworkStatusWrapper<List<King>>>()
    val warriorsList: LiveData<NetworkStatusWrapper<List<King>>>
        get() = _warriorsList


    /* suspend fun getKingsList(query: String) {
         try {
             _warriorsList.postValue(NetworkStatusWrapper.Loading())
             val request = warriorService.getWarriors(query)
             if (request.isSuccessful && request.body() != null) {
                 _warriorsList.postValue(NetworkStatusWrapper.Success(request.body()!!))
             } else if (request.errorBody() != null) {
                 _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
             } else {
                 _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
             }
         } catch (e: Exception) {
             _warriorsList.postValue(NetworkStatusWrapper.Failure("Error"))
         }
     }*/


    private val _data = MutableLiveData<NetworkStatusWrapper<List<WarriorsItem>>>()
    val data: LiveData<NetworkStatusWrapper<List<WarriorsItem>>>
        get() = _data

    /* suspend fun getWarriorsData(query: String) {
         try {
             _data.postValue(NetworkStatusWrapper.Loading())
             val request = warriorService.getWarriorsDetails("data[?(@.name==\"${query}\")]")

             if (request.isSuccessful && request.body() != null) {
                 _data.postValue(NetworkStatusWrapper.Success(request.body()!!))
             } else if (request.errorBody() != null) {
                 _data.postValue(NetworkStatusWrapper.Failure("Error"))
             } else {
                 _data.postValue(NetworkStatusWrapper.Failure())
             }
         } catch (e: Exception) {
             _data.postValue(NetworkStatusWrapper.Failure())

         }
     }*/


    private val _favourites = MutableLiveData<List<King>>()
    val favourites: LiveData<List<King>>
        get() = warriorDataBase.favouriteDao().queryWarriors().map {
            it.asDomainsModel()
        }

    suspend fun saveFavourite(favouriteKing: King) {
        warriorDataBase.favouriteDao().insertWarrior(favouriteKing.asDataBaseModel())
    }

}