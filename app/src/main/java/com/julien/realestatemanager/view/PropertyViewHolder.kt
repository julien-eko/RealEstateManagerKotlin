package com.openclassrooms.realestatemanager.views

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Property
import kotlinx.android.synthetic.main.fragment_property_list_item.view.*

class PropertyViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    val propertyType = itemView.findViewById<TextView>(R.id.property_type_text_view)
    val propertyCity = itemView.findViewById<TextView>(R.id.property_city_text_view)
    val propertyPrice = itemView.findViewById<TextView>(R.id.price_text_view)


    fun update(property: Property){
        propertyType.text = property.type
        propertyCity.text = property.city
        propertyPrice.text = property.price
    }
}