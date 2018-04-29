package com.aniapps.utils.Models

import com.google.gson.annotations.SerializedName

data class StoriesItem(@SerializedName("id")
                       val id: String? = "",
                       @SerializedName("title")
                       val title: String? = "",
                       @SerializedName("desc")
                       val desc: String? = "",
                       @SerializedName("level")
                       val type: String? = "",
                       @SerializedName("fav")
                       val fav: String? = "",
                       @SerializedName("read")
                       val read: String? = "")
