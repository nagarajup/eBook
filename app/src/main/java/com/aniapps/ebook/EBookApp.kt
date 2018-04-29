package com.aniapps.ebook

import android.app.Application

class EBookApp : Application() {
    companion object {
        lateinit var instance: EBookApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}