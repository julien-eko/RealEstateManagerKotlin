package com.julien.realestatemanager.controller.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyViewModel
import kotlinx.android.synthetic.main.fragment_property_detail.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.MapView
import com.julien.realestatemanager.controller.activity.FullScreenActivity
import com.julien.realestatemanager.controller.activity.PropertyDetailActivity
import com.julien.realestatemanager.view.MediaAdaptater
import com.openclassrooms.realestatemanager.views.PropertyAdaptater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_property_list.*
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PropertyDetailFragment : androidx.fragment.app.Fragment(),OnMapReadyCallback {


    private lateinit var propertyViewModel: PropertyViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var adapter: MediaAdaptater
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adress ="France"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_property_detail, container, false)
        mMapView = rootView.findViewById(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()
        //mapView = childFragmentManager.findFragmentById(R.id.mapView)

        mMapView.getMapAsync(this)

        return rootView
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        recycler_view_media.layoutManager = linearLayoutManager

        adapter = MediaAdaptater(listOf())
        recycler_view_media.adapter = adapter
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        listener()
        if (tag != null) {




            propertyViewModel.getProperty(tag!!.toString()).observe(this, Observer { property ->
                // Update the cached copy of the words in the adapter.
                property?.let {

                    //Log.e("db date", property.creationDate.toString())

                    //Log.e("convert date", Date(property.creationDate).toString())

                    description_text_view.text = property.description
                    area_text_view.text = property.area.toString()
                    number_of_rooms_text_view.text = property.numberOfRooms.toString()
                    bedrooms_text_view.text = property.numberOfBedrooms.toString()
                    bathrooms_text_view.text = property.numberOfBathrooms.toString()
                    adress_text_view.text = property.adress
                    additional_adress_text_view.text = property.additionalAdress
                    city_text_view.text = property.city
                    postal_code_text_view.text = property.postalCode
                    country_text_view.text = property.country

                    updateMap(property.latitude,property.longitude)
                }


            })

            propertyViewModel.getMedia(tag!!.toString()).observe(this, Observer { media ->
                // Update the cached copy of the words in the adapter.
                media?.let {
                    //Log.e("size media", media.size.toString())
                    adapter.setMedia(media)



                }


            }
            )


        }


    }
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        // Add a marker in Sydney and move the camera


    }

    fun updateMap(latitude:Double,longitude:Double){

        if (latitude == 0.0 && longitude == 0.0){
            Toast.makeText(context,getString(R.string.no_valid_address),Toast.LENGTH_SHORT).show()
        }else{
            var zoom =18.0f
            val location = LatLng(latitude,longitude)
            mMap.addMarker(MarkerOptions().position(location).title("Marker"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,zoom))
        }


    }
    //click
    fun listener(){
        adapter.listener = { id ->
            // do something here
            propertyViewModel.getMediaId(id).observe(this, Observer { media ->
                // Update the cached copy of the words in the adapter.
                media?.let {

                    val intent = Intent(context, FullScreenActivity::class.java)
                    intent.putExtra("photo", media.photo)
                    // start your next activity
                    startActivity(intent)
                    //Toast.makeText(context,media.description,Toast.LENGTH_SHORT).show()
                }


            })



        }
    }

}
