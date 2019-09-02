package com.julien.realestatemanager.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julien.realestatemanager.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen.*
import java.io.File

class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        val file = File(intent.getStringExtra("photo"))
        Picasso.get().load(file).into(full_screen_image)

    }


}
