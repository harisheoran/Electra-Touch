package com.example.adi.network

import com.example.adi.models.Hinduism
import com.example.adi.models.HinduismItem
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(private val hinduismService: HinduismService) {

    // get warriors
    suspend fun getHinduism(query: String): ApiResponse<List<Hinduism>> {
        return safeApiCall {
            hinduismService.getHinduism(query)
        }
    }

    // get warriors details
    suspend fun getHinduismDetails(query: String): ApiResponse<List<HinduismItem>> {
        return safeApiCall {
            hinduismService.getHinduismDetails(query)
        }
    }

    // inline function to call service and fet response and create an instance of ApiResponse using its companion object
    private inline fun <T> safeApiCall(apiCallToGetResponse: () -> Response<T>): ApiResponse<T> {
        return try {
            ApiResponse.successResponse(apiCallToGetResponse.invoke())
        } catch (e: Exception) {
            ApiResponse.failureResponse(e)
        }
    }


}