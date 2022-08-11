package com.example.spacexfan002.favorite.favdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        // private var INSTANCE: FavoriteDatabase? = null
        var tempInstance: FavoriteDatabase? = null
        fun getDatabase(context: Context): FavoriteDatabase {

            if (tempInstance != null) {
                return tempInstance as FavoriteDatabase
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorite_database")
                        .build()
                tempInstance = instance
                return instance
            }
        }
    }
}