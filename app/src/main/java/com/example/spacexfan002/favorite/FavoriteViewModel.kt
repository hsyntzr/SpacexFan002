package com.example.spacexfan002.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexfan002.favorite.favdata.FavoriteDatabase
import com.example.spacexfan002.favorite.favdata.FavoriteRepository
import com.example.spacexfan002.favorite.favdata.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Favorites>>
    private val repository: FavoriteRepository

    init {
        var favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        readAllData = favoriteDao.readAllData()
    }

    fun addFavorite(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(favorites)
        }
    }

    fun deleteFavorite(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(favorites)
        }
    }
    fun updateFavorite(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorites)
        }
    }
}