package com.aniapps.utils

import android.content.Context
import android.net.ConnectivityManager
import com.aniapps.ebook.EBookApp


object CommonMethods {
    // check network connection
    fun isNetworkAvailable(): Boolean {
        val manager = EBookApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        var isAvailable = false
        if (networkInfo != null && networkInfo.isConnected) {
            isAvailable = true
        }
        return isAvailable
    }
}