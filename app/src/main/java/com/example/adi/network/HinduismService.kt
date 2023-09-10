package com.example.adi.network

import com.example.adi.models.Hinduism
import com.example.adi.models.HinduismItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HinduismService {

    @GET("/v3/b/64c4fe07b89b1e2299c7cef8?meta=false")
    suspend fun getHinduism(@Header("X-JSON-Path") hinduism: String): Response<List<Hinduism>>

    @GET("/v3/b/64c4d2feb89b1e2299c7bf95?meta=false")
    suspend fun getHinduismDetails(@Header("X-JSON-Path") name: String): Response<List<HinduismItem>>

}