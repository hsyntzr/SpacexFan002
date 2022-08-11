package com.example.spacexfan002.upcoming

import android.app.Application
import androidx.lifecycle.*
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



class UpcomingViewModel  (application: Application): AndroidViewModel(application) {
    var liveDataList: MutableLiveData<List<SpaceXModel>> = MutableLiveData()
    fun getLiveDataOBServer(): MutableLiveData<List<SpaceXModel>> {
        return liveDataList
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
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<SpaceXModel>>, t: Throwable) {
                println("Upcoming View Model 40")
            }
        })
    }

    var favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
    val readAllData: List<Favorites>? = null

    private val repository: FavoriteRepository

    init {
        var favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
    }


    fun addList(list: List<SpaceXModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addList(list)
        }
    }

    fun getAllList() : LiveData<List<Favorites>> {
        return repository.readAllData
    }

    fun addFavorite(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(favorites)
        }
    }
}