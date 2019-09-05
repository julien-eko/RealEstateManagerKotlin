package com.julien.realestatemanager.models

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.julien.realestatemanager.R
import kotlinx.android.synthetic.main.activity_new_property.view.*
import kotlinx.android.synthetic.main.custom_marker_layout.view.*

class CustomInfoWindowGoogleMap(val context: Context,val property: Property) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {

        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.custom_marker_layout, null)


        mInfoView.marker_type.text = property.type
        mInfoView.marker_area.text = property.area
        mInfoView.marker_price.text = property.price
        mInfoView.marker_real_estate_agent.text = "Real estate agent: " + property.realEstateAgent
        mInfoView.marker_statut.text = property.status

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}