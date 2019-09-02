package com.julien.realestatemanager.view

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Media
import com.julien.realestatemanager.models.Property
import com.squareup.picasso.Picasso
import java.io.File

class MediaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {

    val mediaImage = itemView.findViewById<ImageView>(R.id.fragment_detail_list_photo)
    val mediaText = itemView.findViewById<TextView>(R.id.fragment_detail_text_photo)

    fun update(media: Media, context: Context){
        mediaText.text = media.description


        val file = File(media.photo)
        Picasso.get().load(file).resize(165,180).into(mediaImage)
    }
}