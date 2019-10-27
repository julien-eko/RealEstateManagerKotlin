package com.julien.realestatemanager.controller.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.julien.realestatemanager.Notification
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Media
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyViewModel
import com.tayfuncesur.stepper.Stepper
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_create_property.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment5.*
import java.util.*

class PropertyActivity : AppCompatActivity() {

    var photoList: ArrayList<String> = ArrayList()
    var editTextList: ArrayList<EditText> = ArrayList()

    var photoListEdited: ArrayList<String> = ArrayList()
    var editTextListEdited: ArrayList<EditText> = ArrayList()
    var idMedia:ArrayList<String> = ArrayList()

    private lateinit var propertyViewModel: PropertyViewModel

    var photo = ""

    var city:String = ""
    var type:String = ""
    var price:Int = 0
    var area:Int = 0
    var numberOfRooms:Int = 0
    var description:String = ""
    var adress:String = ""
    var placeNearby:String = ""
    var status:String = ""
    var createdDate:Long = 0
    var realEstateAgent:String = ""
    var numberOfBathrooms:Int = 0
    var numberOfBedrooms:Int =0
    var additionAdress:String = ""
    var postalCode:String = ""
    var country:String = ""
    var dateOfSale:Long = 0
    var latitude = 0.0
    var longitude = 0.0

    lateinit var property: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_property)
        this.setFinishOnTouchOutside(false)

        if (savedInstanceState != null) {
            rotation(savedInstanceState)
        }

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        if (!intent.getBooleanExtra("isNewProperty",true)){

            loadProperty()
        }


    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(nav_host_fragment).navigateUp()


    //insert new property or edit property in db
    fun insertInDatabase(){

        var id:String = if (!intent.getBooleanExtra("isNewProperty",true)){
            intent.getStringExtra("id")

        }else{
            UUID.randomUUID().toString()
        }

        //save real estat agent in sharepreferences
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("agent", realEstateAgent)
            commit()
        }

        val newProperty = Property(
            id,
            city.trim().toLowerCase(),
            type.trim().toLowerCase(),
            price,
            area,
            numberOfRooms,
            description,
            adress,
            placeNearby.trim().toLowerCase(),
            status,
            createdDate,
            dateOfSale,
            realEstateAgent,
            photo,
            numberOfBathrooms,
            numberOfBedrooms,
            additionAdress,
            postalCode,
            country.trim().toLowerCase(),
            latitude,
            longitude
        )

        if (!intent.getBooleanExtra("isNewProperty",true)){
            propertyViewModel.updateProperty(newProperty)

            if (photoListEdited.size > 0){
                for (i in 0 until photoListEdited.size) {
                    val media = Media(
                        idMedia[i],
                        editTextListEdited[i].text.toString(),
                        photoListEdited[i],
                        newProperty.id
                    )
                    propertyViewModel.updateMedia(media)
                }
            }
            val notification = Notification()
            notification.createNotification(this,getString(R.string.property_edited_notification_title),getString(R.string.edited_property_notification))


        }else{
            propertyViewModel.insert(newProperty)
            val notification = Notification()
            notification.createNotification(this,getString(R.string.property_creates),getString(R.string.property_save))

        }

        if(photoList.size > 0){
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
        }



        finish()
    }


    fun loadProperty(){

        propertyViewModel.getProperty(intent.getStringExtra("id")).observe(this, Observer { property ->
            // Update the cached copy of the words in the adapter.
            property?.let {
                this.property = property


            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("city",city)
        outState.putString("type",type)
        outState.putString("description",description)
        outState.putString("adress",adress)
        outState.putString("placeNearby",placeNearby)
        outState.putString("status",status)
        outState.putString("realEstateAgent",realEstateAgent)
        outState.putString("additionAdress",additionAdress)
        outState.putString("country",country)
        outState.putString("postalCode",postalCode)
        outState.putInt("price",price)
        outState.putInt("area",area)
        outState.putInt("numberOfRooms",numberOfRooms)
        outState.putInt("numberOfBedrooms",numberOfBedrooms)
        outState.putInt("numberOfBathrooms",numberOfBathrooms)
        outState.putLong("createdDate",createdDate)
        outState.putLong("dateOfSale",dateOfSale)
        outState.putDouble("latitude",latitude)
        outState.putDouble("longitude",longitude)
        outState.putString("photo",photo)



    }

    private fun rotation(savedInstanceState: Bundle?){
        city = savedInstanceState!!.getString("city")

        type = savedInstanceState!!.getString("type")

        description= savedInstanceState!!.getString("description")

        adress= savedInstanceState!!.getString("adress")

        placeNearby= savedInstanceState!!.getString("placeNearby")

        status= savedInstanceState!!.getString("status")

        realEstateAgent= savedInstanceState!!.getString("realEstateAgent")

        additionAdress=savedInstanceState!!.getString("additionAdress")

        country= savedInstanceState!!.getString("country")

        postalCode=savedInstanceState!!.getString("postalCode")

        price= savedInstanceState!!.getInt("price")

        area= savedInstanceState!!.getInt("area")

        numberOfRooms= savedInstanceState!!.getInt("numberOfRooms")

        numberOfBedrooms= savedInstanceState!!.getInt("numberOfBedrooms")

        numberOfBathrooms= savedInstanceState!!.getInt("numberOfBathrooms")

        createdDate=savedInstanceState!!.getLong("createdDate")

        dateOfSale= savedInstanceState!!.getLong("dateOfSale")

        latitude=savedInstanceState!!.getDouble("latitude")

        longitude=savedInstanceState!!.getDouble("longitude")

        photo = savedInstanceState!!.getString("photo")
    }

}
