package com.example.spacexfan002.favorite.favdata

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.spacexfan002.data.SpaceXModel

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val readAllData: LiveData<List<Favorites>> = favoriteDao.readAllData()

    fun updateFavorite(favorites: Favorites) {
        Log.d("fatih", "update fav worked.")
        favoriteDao.updateFavorite(favorites)
    }

    fun addList(list: List<SpaceXModel>) {
        Log.d("Fatih", "addlist worked.")
        for (item in list) {
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