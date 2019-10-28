package com.openclassrooms.realestatemanager.views

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Utils
import com.julien.realestatemanager.Database.Property
import com.squareup.picasso.Picasso
import java.io.File
import java.text.NumberFormat
import java.util.*

class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val propertyType = itemView.findViewById<TextView>(R.id.property_type_text_view)
    val propertyCity = itemView.findViewById<TextView>(R.id.property_city_text_view)
    val propertyPrice = itemView.findViewById<TextView>(R.id.price_text_view)
    val propertyVendu = itemView.findViewById<TextView>(R.id.fragment_property_list_photo_vendu)
    val propertyPhoto = itemView.findViewById<ImageView>(R.id.fragment_property_list_photo)
    val propertyLayout = itemView.findViewById<LinearLayout>(R.id.property_list_main_layout)


    fun update(property: Property, isUSD: Boolean) {

        if (property.status == "Vendu") {
            propertyVendu.visibility = View.VISIBLE
        } else {
            propertyVendu.visibility = View.GONE
        }

        propertyType.text = property.type
        propertyCity.text = property.city
        propertyPrice.text = formatPrice(property.price, isUSD)


        val file = File(property.photo)
        Picasso.get().load(file).resize(100, 100).into(propertyPhoto)


    }

    private fun formatPrice(price: Int, isUSD: Boolean): String {

        if (isUSD) {
            val number: Double = price!!.toDouble()
            val format = NumberFormat.getCurrencyInstance(Locale.US)

            return format.format(number)
        } else {
            val priceEU = Utils.convertDollarToEuro(price)
            val number: Double = priceEU!!.toDouble()
            val format = NumberFormat.getCurrencyInstance(Locale.FRANCE)

            return format.format(number)
        }

    }


}