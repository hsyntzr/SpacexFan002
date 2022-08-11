package com.example.spacexfan002.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object{
        private const val BASE_URL = "https://api.spacexdata.com/"

        fun getRetroInstance(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}