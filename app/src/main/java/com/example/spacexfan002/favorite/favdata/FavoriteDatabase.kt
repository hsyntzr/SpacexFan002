package com.example.spacexfan002.favorite.favdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
@TypeConverters(OriginalTypeConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
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