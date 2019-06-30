package com.openclassrooms.realestatemanager.views

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Property
import kotlinx.android.synthetic.main.fragment_property_list_item.view.*

class PropertyViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    val propertyType = itemView.findViewById<TextView>(R.id.property_type_text_view)
    val propertyCity = itemView.findViewById<TextView>(R.id.city_property_tewt_view)


    fun update(test: Property){
        propertyType.text = test.uid.toString()
        propertyCity.text = test.type
    }
}