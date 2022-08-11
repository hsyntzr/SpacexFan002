package com.example.spacexfan002.favorite.favdata

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.example.spacexfan002.data.SpaceXModel
import kotlinx.android.synthetic.main.spacex_list.view.*

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val readAllData: LiveData<List<Favorites>> = favoriteDao.readAllData()
    suspend fun addFavorite(favorites: Favorites) {
        favoriteDao.addFavorite(favorites)
    }

    suspend fun deleteFavorite(favorites: Favorites) {
        favoriteDao.deleteFavorite(favorites)
    }
    suspend fun updateFavorite(favorites: Favorites){
        Log.d("fatih","update fav worked.");
        favoriteDao.updateFavorite(favorites)
    }

    suspend fun addList(list: List<SpaceXModel>) {
        Log.d("Fatih","addlist worked.")
        for (item in list){
            val favorite = Favorites(
                id = item.id,
                name = item.name.toString(),
                img = item.links?.patch?.small,
                favorite = false,
                details = item.details,
                upcoming = item.upcoming,
                date_precision = item.date_precision,
                date_local = item.date_local,
                flight_number = item.flight_number,
            )
            favoriteDao.addFavorite(favorite)
        }
    }

}