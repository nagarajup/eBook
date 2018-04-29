package com.aniapps.adapters

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.aniapps.utils.CommonMethods
import com.aniapps.ebook.EBookApp
import com.aniapps.ebook.R
import com.aniapps.stories.Stories_List
import com.aniapps.models.HomeModel

class HomeAdapter(val list: ArrayList<HomeModel>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: HomeModel) {
            val _textView: TextView = itemView.findViewById(R.id.tv_title)
            val _imageView: ImageView = itemView.findViewById(R.id.img_home)
            val relativeLayout: RelativeLayout = itemView.findViewById(R.id.lay_bg);
            _textView.text = data.text
            _imageView.setImageBitmap(data.image)
            relativeLayout.setBackgroundColor(Color.parseColor(data.color));
            //set the onclick listener for the singlt list item
                itemView.setOnClickListener({
                    if(CommonMethods.isNetworkAvailable()) {
                        val i = Intent(EBookApp.instance, Stories_List::class.java)
                        i.putExtra("myString", "This is a message for ActivityB")
                        i.putExtra("myInt", 100)
                        EBookApp.instance.startActivity(i)
                    } else {
                        Toast.makeText(EBookApp.instance, "Please check your internet connections and try again", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }


