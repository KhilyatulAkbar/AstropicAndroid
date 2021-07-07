package com.nurmuhammadkhilyatulakbar.astropic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ApodModel : Serializable {
    @SerializedName("apod_site")
    val apodSite: String? = null

    @SerializedName("copyright")
    val copyright: String? = null

    @SerializedName("date")
    val date: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("hdurl")
    val hdurl: String? = null

    @SerializedName("media_type")
    val mediaType: String? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("url")
    val url: String? = null

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String? = null
}
