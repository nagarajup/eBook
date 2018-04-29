package com.aniapps.utils.Models

import com.google.gson.annotations.SerializedName

data class Stories(@SerializedName("stories")
                   val stories: List<StoriesItem>?) {
}