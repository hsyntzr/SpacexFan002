package com.example.spacexfan002.rockets


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexfan002.data.SpaceXModel
import com.example.spacexfan002.favorite.favdata.FavoriteDatabase
import com.example.spacexfan002.favorite.favdata.FavoriteRepository
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.retrofit.RetroInstance
import com.example.spacexfan002.retrofit.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RocketViewModel(application: Application) : AndroidViewModel(application) {
     private val repository: FavoriteRepository
    init {
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroService::class.java)
        val call = retroService.getSpaceXList()
        call.enqueue(object : Callback<List<SpaceXModel>> {
            override fun onResponse(
                call: Call<List<SpaceXModel>>,
                response: Response<List<SpaceXModel>>
            ) {
                addList(response.body() as List<SpaceXModel>)
            }

            override fun onFailure(call: Call<List<SpaceXModel>>, t: Throwable) {
                println("Rocket View Model 40")
            }
        })
    }

    fun addList(list: List<SpaceXModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addList(list)
        }
    }

    fun getAllList(): LiveData<List<Favorites>> {
        return repository.readAllData
    }

    fun updateFavorite(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorites)
        }
    }
}