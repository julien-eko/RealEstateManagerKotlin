package com.julien.realestatemanager.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.julien.realestatemanager.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen.*
import kotlinx.android.synthetic.main.activity_map.*
import java.io.File

/**
 * displays full screen image
 */
class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        configureToolbar()

        val file = File(intent.getStringExtra("photo"))
        Picasso.get().load(file).into(full_screen_image)



    }

    //configure toolbar
    //toolbar title: description of the photo
    private fun configureToolbar(){
        setSupportActionBar(activity_full_screen_toolbar)

        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle(intent.getStringExtra("description"))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
