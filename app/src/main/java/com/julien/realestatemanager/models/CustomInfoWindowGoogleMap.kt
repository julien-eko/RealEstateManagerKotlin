package com.julien.realestatemanager.models

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.julien.realestatemanager.R
import kotlinx.android.synthetic.main.custom_marker_layout.view.*

class CustomInfoWindowGoogleMap(val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {

        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.custom_marker_layout, null)
        var mInfoWindow: InfoWindowData? = p0?.tag as InfoWindowData?

        mInfoView.marker_type.text = mInfoWindow?.mType
        mInfoView.marker_area.text =  mInfoWindow?.mArea
        mInfoView.marker_price.text =  mInfoWindow?.mPrice
        mInfoView.marker_real_estate_agent.text = "Real estate agent: " + mInfoWindow?.mRealEstateAgent
        mInfoView.marker_statut.text =  mInfoWindow?.mStatut

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}