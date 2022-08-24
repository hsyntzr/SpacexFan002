package com.example.spacexfan002.favorite.favdata

import com.example.spacexfan002.data.SpaceXModel
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun updateFavorite(favorites: Favorites) {
        favoriteDao.updateFavorite(favorites)
    }
    fun getFavorite(mId : String) : Favorites{
        return favoriteDao.getFavorite(mId)
    }

    fun addList(list: List<SpaceXModel>) {
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
                original = item.links?.flickr?.original!!
            )
            favoriteDao.addFavorite(favorite)
        }
    }

    fun getAllList() : Flow<List<Favorites>> {
        return favoriteDao.readAllData()
    }
}