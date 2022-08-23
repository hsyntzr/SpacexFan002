package com.example.spacexfan002.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.spacexfan002.favorite.favdata.FavoriteDao
import com.example.spacexfan002.favorite.favdata.FavoriteDatabase
import com.example.spacexfan002.favorite.favdata.FavoriteRepository
import com.example.spacexfan002.favorite.favdata.Favorites
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData  = MutableLiveData<List<Favorites>>()
    private val repository: FavoriteRepository


    init {
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
    }


    fun updateFavorite(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorites)
        }
    }

    fun listenAllList(lifecycle: Lifecycle, scope: LifecycleCoroutineScope) {
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