package com.openclassrooms.realestatemanager.views

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.julien.realestatemanager.R
import com.julien.realestatemanager.extensions.listen
import com.julien.realestatemanager.models.Property

class PropertyAdaptater(var propertyList: List<Property>): androidx.recyclerview.widget.RecyclerView.Adapter<PropertyViewHolder>() {

    var listener: ((Int)->Unit)? = null
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        context = parent?.context
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_property_list_item, parent, false)
        return PropertyViewHolder(v).listen { pos, type ->
            //val item = items.get(pos)
            //Toast.makeText(parent.context, pos.toString(), Toast.LENGTH_SHORT).show()
            listener?.invoke(propertyList.get(pos).id)
            //TODO do other stuff here
        }

    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.update(propertyList.get(position),context)
    }

    internal fun setProperties(properties: List<Property>) {
        this.propertyList = properties
        notifyDataSetChanged()
    }

    override fun getItemCount() = propertyList.size





}