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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpcomingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository
    val readAllData  = MutableLiveData<List<Favorites>>()

    init {
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
    }

    fun listenAllList(lifecycle : Lifecycle, scope: LifecycleCoroutineScope){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllList()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach {
                    readAllData.postValue(it)
                }
                .launchIn(scope)
        }
    }
}