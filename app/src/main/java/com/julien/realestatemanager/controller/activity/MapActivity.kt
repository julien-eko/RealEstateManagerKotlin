package com.julien.realestatemanager.controller.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.CustomInfoWindowGoogleMap
import com.julien.realestatemanager.models.InfoWindowData
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyViewModel
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.fragment_property_detail.*
import java.io.File

private const val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100

class MapActivity : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var propertyViewModel: PropertyViewModel
    private lateinit var mFusedLocationProviderClient:FusedLocationProviderClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mMapView = findViewById(R.id.mapView_activity)
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        configureToolbar()


        mMapView.getMapAsync(this)



        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        //add marker for properties with a valid address
        propertyViewModel.getAllProperty().observe(this, Observer { properties ->
            // Update the cached copy of the words in the adapter.
            properties?.let {

               for (i in properties){
                   if (i.latitude != 0.0 && i.longitude != 0.0){
                       addMarker(i.longitude,i.latitude,i)
                   }
               }
            }


        })
    }



    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        checkPermitionLocation()

    }

    private fun configureToolbar(){
        setSupportActionBar(activity_map_toolbar)

        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle("Map")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    //add a custom marker
    private fun addMarker(longitude:Double, latitude:Double, property: Property){
        val location = LatLng(latitude,longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
        val info = InfoWindowData(property.type, property.area.toString(),
            property.price.toString(),
            property.realEstateAgent,
            property.status
        )
        val customInfoWindow = CustomInfoWindowGoogleMap(this)

        mMap!!.setInfoWindowAdapter(customInfoWindow)

        val marker = mMap!!.addMarker(markerOptions)
        marker.tag = info
        marker.showInfoWindow()


    }



    fun checkPermitionLocation(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)


        } else {
            // Permission has already been granted
            mMap.isMyLocationEnabled = true
            getDeviceLocation()






        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {


                    mMap.isMyLocationEnabled = true
                    getDeviceLocation()

                } else {

                    Toast.makeText(this,getString(R.string.permission_denied),Toast.LENGTH_SHORT).show()

                }
                return
            }

            else -> {
            }
        }
    }


    fun getDeviceLocation(){
        var locationResult = mFusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener { p0 ->
            if(p0.isSuccessful){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(p0.result!!.latitude,p0.result!!.longitude),18.0f))

            }else{

            }
        }
    }


}
