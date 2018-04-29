package com.aniapps.stories

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.Toast
import com.aniapps.adapters.StoriesListAdapter
import com.aniapps.ebook.BaseActivity
import com.aniapps.ebook.R
import com.aniapps.utils.Models.Stories
import com.aniapps.utils.RestClient.ApiResults
import com.aniapps.utils.RestClient.RestClient


//https://www.mytrendin.com/creating-app-introduction-tour-slider-appintro-library-using-kotlin/

class Stories_List() : BaseActivity() {
    lateinit var rvStories: RecyclerView
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_stories_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_stories_list)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_stories_list, null, false)
        drawer_layout.addView(contentView, 0)
        rvStories = findViewById(R.id.rv_story_titles)
        getStory();
        rvStories.layoutManager = LinearLayoutManager(this)
        rvStories.layoutManager = GridLayoutManager(this, 1)
    }

    private fun getStory() {
        RestClient.getIn().getResults(object : ApiResults {
            override fun onSuccess(res: Stories?) {
                rvStories.adapter = StoriesListAdapter(res, this@Stories_List)
            }

            override fun onFailure(res: String) {
                Toast.makeText(this@Stories_List, res, Toast.LENGTH_SHORT).show()
            }

        })
    }


}


