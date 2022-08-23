package com.example.spacexfan002.favorite.favdata

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

    @Query(value = "SELECT * FROM favorites ORDER BY id ASC")
    fun readAllData(): Flow<List<Favorites>>

    @Query(value = "SELECT * FROM favorites WHERE id = :mId")
    fun getFavorite(mId : String) : Favorites

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favorites: Favorites)

    @Update
    fun updateFavorite(favorites: Favorites)

    @Delete
    fun deleteFavorite(favorites: Favorites)
}