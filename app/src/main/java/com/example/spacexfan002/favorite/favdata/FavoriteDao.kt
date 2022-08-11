package com.example.spacexfan002.favorite.favdata

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavoriteDao {

    @Query(value = "SELECT * FROM favorites ORDER BY id ASC")
    fun readAllData(): LiveData<List<Favorites>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favorites: Favorites)

    @Update
    fun updateFavorite(favorites: Favorites)

    @Delete
    fun deleteFavorite(favorites: Favorites)
}