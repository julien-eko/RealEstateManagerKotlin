package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Property
import kotlinx.android.synthetic.main.fragment_property_list_item.view.*
import kotlin.coroutines.coroutineContext

class PropertyViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    val propertyType = itemView.findViewById<TextView>(R.id.property_type_text_view)
    val propertyCity = itemView.findViewById<TextView>(R.id.property_city_text_view)
    val propertyPrice = itemView.findViewById<TextView>(R.id.price_text_view)
    val propertyPhoto = itemView.findViewById<ImageView>(R.id.fragment_property_list_photo)


    fun update(property: Property, context: Context){
        propertyType.text = property.type
        propertyCity.text = property.city
        propertyPrice.text = property.price


        if(property.photo != null){

            val bitmap: Bitmap = BitmapFactory.decodeByteArray(property.photo, 0, property.photo.size)
            //photo.text = property.photo

            Glide.with(context) //SHOWING PREVIEW OF IMAGE
                .load(bitmap)
                .apply(RequestOptions.centerCropTransform())
                .into(propertyPhoto)

        }
    }
}