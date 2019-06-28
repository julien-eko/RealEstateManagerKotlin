package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julien.realestatemanager.R

class PropertyAdaptater(var propertyList: List<String>): RecyclerView.Adapter<PropertyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_property_list_item, parent, false)
        return PropertyViewHolder(v)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.update(propertyList.get(position))
    }

    override fun getItemCount() = propertyList.size





}