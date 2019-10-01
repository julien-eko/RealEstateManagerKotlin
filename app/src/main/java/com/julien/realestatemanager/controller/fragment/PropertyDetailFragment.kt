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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.MapView
import com.julien.realestatemanager.Utils
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
class PropertyDetailFragment : androidx.fragment.app.Fragment(), OnMapReadyCallback {


    private lateinit var propertyViewModel: PropertyViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var adapter: MediaAdaptater
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adress = "France"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_property_detail, container, false)

        //create map
        mMapView = rootView.findViewById(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        mMapView.getMapAsync(this)

        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_media.layoutManager = linearLayoutManager

        adapter = MediaAdaptater(listOf())
        recycler_view_media.adapter = adapter
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        listener()
        if (tag != null) {

            propertyViewModel.getProperty(tag!!.toString()).observe(this, Observer { property ->
                // Update the cached copy of the words in the adapter.
                property?.let {


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

                    val fullAdress =
                        property.adress + " " + property.additionalAdress + " " + property.city + " " + property.postalCode + " " + property.country

                    updateMap(property.latitude, property.longitude, fullAdress)
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
    }

    //update mini map
    fun updateMap(latitude: Double, longitude: Double, fullAdress: String) {


        if (latitude == 0.0 && longitude == 0.0) {
            if (Utils.isInternetAvailable(context)) {
                var geocoder = Geocoder(context)
                var listAdress: List<Address> = geocoder.getFromLocationName(fullAdress, 1)

                if (listAdress.size > 0) {
                    var zoom = 18.0f
                    val location = LatLng(listAdress[0].latitude, listAdress[0].longitude)
                    mMap.addMarker(MarkerOptions().position(location).title("Property"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom))

                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.no_valid_address),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.no_internet_cant_display_property),
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            var zoom = 18.0f
            val location = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(location).title("Property"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom))
        }


    }

    //When user click on phoro display the photo in full screen
    fun listener() {
        adapter.listener = { id ->
            // do something here
            propertyViewModel.getMediaId(id).observe(this, Observer { media ->
                // Update the cached copy of the words in the adapter.
                media?.let {

                    val intent = Intent(context, FullScreenActivity::class.java)
                    intent.putExtra("photo", media.photo)
                    intent.putExtra("description", media.description)
                    startActivity(intent)
                }


            })


        }
    }

}
