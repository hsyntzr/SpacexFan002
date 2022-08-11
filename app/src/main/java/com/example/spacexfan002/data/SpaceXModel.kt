package com.example.spacexfan002.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpaceXModel(
    @SerializedName("name")
    var name: String?,
    @SerializedName("details")
    var details: String?,
    @SerializedName("upcoming")
    var upcoming: Boolean,
    @SerializedName("date_precision")
    var date_precision: String,
    @SerializedName("date_local")
    var date_local: String,
    @SerializedName("flight_number")
    var flight_number: Int,
    @SerializedName("id")
    var id:String,
    @SerializedName("links")
    var links: Links?,

) : Serializable
data class Links(
    @SerializedName("patch")
    var patch: Patch?,
    @SerializedName("flickr")
    var flickr: Flickr
)
data class Patch(
    @SerializedName("small")
    var small:String?
)
data class Flickr(
    @SerializedName(value = "original")
    var original: ArrayList<String>?
)