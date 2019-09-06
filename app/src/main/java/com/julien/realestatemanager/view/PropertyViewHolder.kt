package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_property_list_item.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.coroutines.coroutineContext

class PropertyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    val propertyType = itemView.findViewById<TextView>(R.id.property_type_text_view)
    val propertyCity = itemView.findViewById<TextView>(R.id.property_city_text_view)
    val propertyPrice = itemView.findViewById<TextView>(R.id.price_text_view)
    val propertyVendu = itemView.findViewById<TextView>(R.id.fragment_property_list_photo_vendu)
    val propertyPhoto = itemView.findViewById<ImageView>(R.id.fragment_property_list_photo)
    val propertyLayout =  itemView.findViewById<LinearLayout>(R.id.property_linear_layout)


    @RequiresApi(Build.VERSION_CODES.M)
    fun update(property: Property, id:String, context: Context){

        if (property.status == "Vendu"){
            propertyVendu.visibility = View.VISIBLE
        }

        propertyType.text = property.type
        propertyCity.text = property.city
        propertyPrice.text = formatPrice(property.price)


        val file = File(property.photo)
        Picasso.get().load(file).resize(100,100).into(propertyPhoto)
         if (property.id == id){
            propertyLayout.setBackgroundColor(context.getColor(R.color.pinkPrimary))
             propertyPrice.setTextColor(Color.WHITE)
         }

    }

    private fun formatPrice(price:String?):String{
        val number:Double = price!!.toDouble()
        val format = NumberFormat.getCurrencyInstance(Locale.FRANCE)

        return format.format(number)
    }

}