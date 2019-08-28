package com.julien.realestatemanager.controller.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.julien.realestatemanager.R
import kotlinx.android.synthetic.main.activity_new_property.*
import pub.devrel.easypermissions.AfterPermissionGranted
import java.util.jar.Manifest
import pub.devrel.easypermissions.EasyPermissions
import android.provider.MediaStore


import android.graphics.Bitmap.CompressFormat
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.models.Media
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_property_detail.*

import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


private const val PERMS = android.Manifest.permission.READ_EXTERNAL_STORAGE

    private const val RC_IMAGE_PERMS = 100

    private  const val RC_CHOOSE_PHOTO = 200

class NewPropertyActivity : AppCompatActivity() {



    private var uriList:ArrayList<Uri> = ArrayList()
    private var editTextList:ArrayList<EditText> = ArrayList()

    private lateinit var uri: Uri

    private var choice:Int = 0

    private lateinit var propertyViewModel: PropertyViewModel

    private lateinit var datePickerSale: DatePicker






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_property)
        //editWordView = findViewById(R.id.edit_word)
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        //DatePicker_date_of_sale.visibility = View.INVISIBLE
        //val button = findViewById<Button>(R.id.button_save)
        button_save.setOnClickListener {
            //val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_type.text)) {
                //setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val city = edit_city.text.toString()
                val type = edit_type.text.toString()
                val price = edit_price.text.toString()
                val area = edit_area.text.toString()
                val numberOfRooms = edit_number_of_romms.text.toString()
                val description = edit_description.text.toString()
                val adress = edit_adress.text.toString()
                val placeNearby = edit_place_nearby.text.toString()
                val status = spinner_status.selectedItem.toString()
                val createdDate = datePicker(DatePicker_created_date)
                val realEstateAgent = edit_real_estate_agent.text.toString()
                val numberOfBathrooms = edit_number_of_batthrooms.text.toString()
                val numberOfBedrooms = edit_number_of_bedrooms.text.toString()
                val additionAdress = edit_additional_adress.text.toString()
                val postalCode = edit_postal_code.text.toString()
                val country = edit_country.text.toString()
                var dateOfSale = ""
                if (status.equals("Vendu")){
                    dateOfSale = datePicker(datePickerSale)
                }



                val newId = UUID.randomUUID().toString()
                val newProperty = Property(newId,city,type,price,area,numberOfRooms,description,adress,placeNearby,status,createdDate,dateOfSale,realEstateAgent,uri.toString(),numberOfBathrooms,numberOfBedrooms,additionAdress,postalCode,country)
                propertyViewModel.insert(newProperty)


                //Log.e("urilist",uriList.toString())
                for (i in 0 until uriList.size){
                    val idMedia = UUID.randomUUID().toString()
                    val media = Media(idMedia,editTextList[i].text.toString(),uriList[i].toString(),newProperty.id)
                    propertyViewModel.insertMedia(media)
                }
                /*
                for( i in uriList ){
                    val idMedia = UUID.randomUUID().toString()
                    val media = Media(idMedia,"test",i.toString(),newProperty.id)

                    propertyViewModel.insertMedia(media)
                }
*/
            }
            finish()
        }

        button_photo.setOnClickListener {
            choice = 1
            onClickAddFile()
        }

        button_album.setOnClickListener {
            choice = 2
           onClickAddFile()
        }

        spinner_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                datePickerSale = DatePicker(baseContext)

                if(position == 2){
                    activity_new_property_layout_date.addView(datePickerSale)
                }else{
                    activity_new_property_layout_date.removeAllViews()
                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 6 - Calling the appropriate method after activity result
        handleResponse(requestCode, resultCode, data)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 2 - Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    fun onClickAddFile() { chooseImageFromPhone()}


    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }

    // --------------------
    // FILE MANAGEMENT
    // --------------------

    private fun chooseImageFromPhone() {
        if (!EasyPermissions.hasPermissions(this, PERMS)) {
            EasyPermissions.requestPermissions(
                this,
                "permission ?",
                RC_IMAGE_PERMS,
                PERMS
            )
            return
        }
        // 3 - Launch an "Selection Image" Activity
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, RC_CHOOSE_PHOTO)
    }

    // 4 - Handle activity response (after user has chosen or not a picture)
    private fun handleResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) { //SUCCESS
                //uriImage = data!!.data
                if ( choice == 1){
                    uri = data!!.data
                }else{
                    editTextList.add( addPhoto(data!!.data.toString()))
                    uriList.add(data!!.data)
                }

            } else {
                Toast.makeText(this, "pas d'image choisie", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun addPhoto(phototest:String):EditText{
        var image = ImageView(this)
        var editText = EditText(this)
        var linearLayout = LinearLayout(this)
        linearLayout.orientation=LinearLayout.HORIZONTAL

        Picasso.get().load(Uri.parse(phototest)).resize(200,200).into(image)

        linearLayout.addView(image)
        linearLayout.addView(editText)
        new_property_activity_album_photo.addView(linearLayout)

        return editText

    }


    fun datePicker(datePicker:DatePicker):String{
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        return day.toString() + "/" + month.toString() + "/" + year.toString()
    }
}
