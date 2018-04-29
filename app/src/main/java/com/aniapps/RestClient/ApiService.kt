package com.aniapps.utils.RestClient

import com.aniapps.utils.Models.Stories
import com.aniapps.utils.Models.StoriesItem
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService  {
    @GET("/apis/getshortstories.php")
    fun getStories(): Call<List<StoriesItem>>
    @GET("/apis/getshortstories.php")
    fun getApiResult(): Call<Stories>

     fun getApiResults(@FieldMap fields: Map<String, String>): Call<String>
}