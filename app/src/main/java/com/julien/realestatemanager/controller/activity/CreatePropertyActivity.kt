package com.julien.realestatemanager.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Media
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyViewModel
import com.tayfuncesur.stepper.Stepper
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_create_property.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment5.*
import java.util.*

class CreatePropertyActivity : AppCompatActivity() {

    var photoList: ArrayList<String> = ArrayList()
    var editTextList: ArrayList<EditText> = ArrayList()

    private lateinit var propertyViewModel: PropertyViewModel

    lateinit var photo: String

    lateinit var city:String
    lateinit var type:String
    lateinit var price:String
    lateinit var area:String
    lateinit var numberOfRooms:String
    lateinit var description:String
    lateinit var adress:String
    lateinit var placeNearby:String
    lateinit var status:String
    lateinit var createdDate:String
    lateinit var realEstateAgent:String
    lateinit var numberOfBathrooms:String
    lateinit var numberOfBedrooms:String
    lateinit var additionAdress:String
    lateinit var postalCode:String
    lateinit var country:String
    lateinit var dateOfSale:String
    var latitude = 0.0
    var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_property)

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(nav_host_fragment).navigateUp()


    fun insertInDatabase(){
        val newId = UUID.randomUUID().toString()
        val newProperty = Property(
            newId,
            city,
            type,
            price,
            area,
            numberOfRooms,
            description,
            adress,
            placeNearby,
            status,
            createdDate,
            dateOfSale,
            realEstateAgent,
            photo,
            numberOfBathrooms,
            numberOfBedrooms,
            additionAdress,
            postalCode,
            country,
            latitude,
            longitude
        )
        propertyViewModel.insert(newProperty)


        for (i in 0 until photoList.size) {
            val idMedia = UUID.randomUUID().toString()
            val media = Media(
                idMedia,
                editTextList[i].text.toString(),
                photoList[i],
                newProperty.id
            )
            propertyViewModel.insertMedia(media)
        }

        finish()
    }

}
