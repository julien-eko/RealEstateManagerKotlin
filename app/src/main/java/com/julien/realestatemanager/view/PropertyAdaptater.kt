package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.julien.realestatemanager.R
import com.julien.realestatemanager.Database.Property

class PropertyAdaptater(var propertyList: List<Property>, var id: String, var isUSD: Boolean) :
    RecyclerView.Adapter<PropertyViewHolder>() {

    var listener: ((String) -> Unit)? = null
    private lateinit var context: Context
    private var index: Int = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        context = parent?.context
        val v = LayoutInflater.from(parent?.context)
            .inflate(R.layout.fragment_property_list_item, parent, false)
        return PropertyViewHolder(v)

    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.update(propertyList.get(position), isUSD)


        holder.itemView.setOnClickListener {
            listener?.invoke(propertyList[position].id)
            index = position
            notifyDataSetChanged()


        }

        if (index == position) {
            holder.propertyLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context!!.applicationContext,
                    R.color.pinkPrimary
                )
            )
            holder.propertyPrice.setTextColor(Color.WHITE)
        } else {
            holder.propertyLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context!!.applicationContext,
                    R.color.white
                )
            )
            holder.propertyPrice.setTextColor(
                ContextCompat.getColor(
                    context!!.applicationContext,
                    R.color.pinkLight
                )
            )
        }


    }


    internal fun setProperties(properties: List<Property>) {
        this.propertyList = properties
        notifyDataSetChanged()
    }


    override fun getItemCount() = propertyList.size


}