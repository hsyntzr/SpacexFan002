package com.example.spacexfan002.favorite.favdata

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    var flight_number:Int?
    ) : Serializable