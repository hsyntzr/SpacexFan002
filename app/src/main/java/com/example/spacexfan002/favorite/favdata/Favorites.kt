package com.example.spacexfan002.favorite.favdata

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable


@Entity(tableName = "favorites")
data class Favorites(
    @PrimaryKey
    val id: String,
    var name: String?,
    var img: String?,
    var favorite: Boolean?,
    var details: String?,
    var upcoming: Boolean?,
    var date_precision: String?,
    var date_local: String?,
    var flight_number: Int?,
    var original:  ArrayList<String>

) : Serializable

class OriginalTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>{
        val listType = object :TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType )
    }
    @TypeConverter
    fun frmArrayList(list: ArrayList<String>): String{
        return Gson().toJson(list)
    }
}