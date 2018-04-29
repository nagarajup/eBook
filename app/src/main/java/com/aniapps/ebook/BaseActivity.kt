package com.aniapps.ebook

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.aniapps.utils.MaterialRippleLayout
import kotlinx.android.synthetic.main.app_bar_base.*

abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal lateinit var lay_fav: MaterialRippleLayout
    internal lateinit var lay_share: MaterialRippleLayout
    internal lateinit var lay_audio: MaterialRippleLayout
    internal lateinit var lay_settingss: MaterialRippleLayout
    internal lateinit var settings: SharedPreferences
    internal lateinit var editor: SharedPreferences.Editor
    internal lateinit var img_speaker: ImageView
    internal lateinit var img_fav: ImageView
    internal lateinit var img_fav_done: ImageView
    internal lateinit var  drawer_layout: DrawerLayout
    internal lateinit var  nav_view: NavigationView
    val myPref = "MyPrefs"
    protected abstract fun getLayoutResourceId(): Int
    internal lateinit var builder: AlertDialog.Builder
    internal var selection: Int? = -1
    override fun onCreate(savedInstanceState: Bundle?) {


        //test
//        NightOwl.owlBeforeCreate(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
      //  NightOwl.owlAfterCreate(this)
        setSupportActionBar(toolbar)

        settings = this.getSharedPreferences(myPref, Context.MODE_PRIVATE)
        editor = settings.edit()
        lay_audio = findViewById(R.id.lay_audio) as MaterialRippleLayout
        lay_settingss = findViewById(R.id.lay_settingss) as MaterialRippleLayout
        lay_fav = findViewById(R.id.lay_fav) as MaterialRippleLayout
        img_fav = findViewById(R.id.img_fav) as ImageView
        img_fav_done = findViewById(R.id.img_fav_done) as ImageView
        lay_share = findViewById(R.id.lay_share) as MaterialRippleLayout
        img_speaker = findViewById(R.id.img_speaker) as ImageView
         fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show()
         }

         drawer_layout=findViewById(R.id.drawer_layout) as DrawerLayout
         nav_view=findViewById(R.id.nav_view) as NavigationView

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}