package com.aniapps.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.aniapps.ebook.EBookApp
import com.aniapps.ebook.R
import com.aniapps.stories.StoriesAct
import com.aniapps.utils.Models.Stories

class StoriesListAdapter(val items: Stories?, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_story_titles, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvtitle.text = items?.stories?.get(position)?.title
        holder.lay_ll_title.setOnClickListener {
            val in_stories = Intent(EBookApp.instance, StoriesAct::class.java)
            in_stories.putExtra("length", items?.stories?.size!!)
            in_stories.putExtra("pos", position)
            EBookApp.instance.startActivity(in_stories)
        }
        holder.tvtitle.setOnClickListener {
            Toast.makeText(context, "Title:" + items?.stories?.get(position)?.title, Toast.LENGTH_LONG).show();
        }
        holder.img_story_edit.setOnClickListener {
            Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
        }
        holder.img_story_share.setOnClickListener {
            Toast.makeText(context, "Share", Toast.LENGTH_LONG).show();
        }
        holder.img_story_fav_null.setOnClickListener {
            Toast.makeText(context, "Fav Nil", Toast.LENGTH_LONG).show();
        }
        holder.img_story_fav_done.setOnClickListener {
            Toast.makeText(context, "Fav Done", Toast.LENGTH_LONG).show();
        }
        // holder.tvdesc?.text = items?.stories?.get(position)?.type
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return items?.stories?.size!!
    }

}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val lay_ll_title: LinearLayout = itemView.findViewById(R.id.lay_ll_title);
    val tvtitle: TextView = itemView.findViewById(R.id.tv_story_title)
    val img_story_edit: ImageView = itemView.findViewById(R.id.img_story_edit)
    val img_story_share: ImageView = itemView.findViewById(R.id.img_story_share)
    val img_story_fav_null: ImageView = itemView.findViewById(R.id.img_story_fav_null)
    val img_story_fav_done: ImageView = itemView.findViewById(R.id.img_story_fav_done)
}