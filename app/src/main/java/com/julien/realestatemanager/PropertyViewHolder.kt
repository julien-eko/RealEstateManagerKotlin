package com.openclassrooms.realestatemanager.views

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.julien.realestatemanager.R
import kotlinx.android.synthetic.main.fragment_property_list_item.view.*

class PropertyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    //@BindView(R.id.fragment_main_item_test) lateinit var test1: TextView
    //@BindView(R.id.fragment_main_item_test2) lateinit var  test2: TextView


    val test1 = itemView.findViewById<TextView>(R.id.fragment_main_item_test)


    fun update(test: String){
        test1.text = test

    }
}