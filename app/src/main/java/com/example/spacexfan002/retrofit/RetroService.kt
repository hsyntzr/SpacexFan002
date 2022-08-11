package com.example.spacexfan002.retrofit

import com.example.spacexfan002.data.SpaceXModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroService {
    @GET("v5/launches")
    fun getSpaceXList(): Call<List<SpaceXModel>>
}