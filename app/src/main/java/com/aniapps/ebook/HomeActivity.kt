package com.aniapps.ebook

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.aniapps.adapters.HomeAdapter
import com.aniapps.models.HomeModel

open class HomeActivity : AppCompatActivity() {
    lateinit var rvHome: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rvHome = findViewById(R.id.rvHome);
        rvHome.layoutManager = LinearLayoutManager(this);
        rvHome.layoutManager = GridLayoutManager(this, 2);
        val homelist = ArrayList<HomeModel>()
        val rainbow = getResources().getStringArray(R.array.rainbow)
        homelist.add(HomeModel("Short Stories", BitmapFactory.decodeResource(resources, R.drawable.storry), rainbow[0]))
        homelist.add(HomeModel("Proverbs & Sayings", BitmapFactory.decodeResource(resources, R.drawable.storry), rainbow[1]))
        homelist.add(HomeModel("Quotes", BitmapFactory.decodeResource(resources, R.drawable.storry), rainbow[2]))
        homelist.add(HomeModel("Messages", BitmapFactory.decodeResource(resources, R.drawable.storry), rainbow[3]))
        homelist.add(HomeModel("Kids Stories", BitmapFactory.decodeResource(resources, R.drawable.storry), rainbow[4]))
        homelist.add(HomeModel("Tongue Twisters", BitmapFactory.decodeResource(resources, R.drawable.storry), rainbow[5]))
        homelist.add(HomeModel("Favorites", BitmapFactory.decodeResource(resources, R.drawable.storry), rainbow[7]))
        rvHome.adapter = HomeAdapter(homelist)
    }
}