package com.julien.realestatemanager.controller.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
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
import android.location.Address
import android.location.Geocoder
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
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


private const val PERMS = android.Manifest.permission.READ_EXTERNAL_STORAGE

private const val RC_IMAGE_PERMS = 100

private const val RC_CHOOSE_PHOTO = 200

class NewPropertyActivity : AppCompatActivity() {
/*

    private var photoList: ArrayList<String> = ArrayList()
    private var editTextList: ArrayList<EditText> = ArrayList()

    private lateinit var photo: String

    private var choice: Int = 0

    private lateinit var propertyViewModel: PropertyViewModel

    private lateinit var datePickerSale: DatePicker

    private lateinit var city:String
    private lateinit var type:String
    private lateinit var price:String
    private lateinit var area:String
    private lateinit var numberOfRooms:String
    private lateinit var description:String
    private lateinit var adress:String
    private lateinit var placeNearby:String
    private lateinit var status:String
    private lateinit var createdDate:String
    private lateinit var realEstateAgent:String
    private lateinit var numberOfBathrooms:String
    private lateinit var numberOfBedrooms:String
    private lateinit var additionAdress:String
    private lateinit var postalCode:String
    private lateinit var country:String
    private lateinit var dateOfSale:String
    private var latitude = 0.0
    private var longitude = 0.0



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
                city = edit_city.text.toString()
                type = edit_type.text.toString()
                price = edit_price.text.toString()
                area = edit_area.text.toString()
                numberOfRooms = edit_number_of_romms.text.toString()
                description = edit_description.text.toString()
                adress = edit_adress.text.toString()
                placeNearby = edit_place_nearby.text.toString()
                status = spinner_status.selectedItem.toString()
                createdDate = datePicker(DatePicker_created_date)
                realEstateAgent = edit_real_estate_agent.text.toString()
                numberOfBathrooms = edit_number_of_batthrooms.text.toString()
                numberOfBedrooms = edit_number_of_bedrooms.text.toString()
                additionAdress = edit_additional_adress.text.toString()
                postalCode = edit_postal_code.text.toString()
                country = edit_country.text.toString()

                dateOfSale = if (status.equals("Vendu")) {
                    datePicker(datePickerSale)
                }else{
                    ""
                }

                val fullAdress = "$adress $additionAdress $postalCode $city $country"

                var geocoder= Geocoder(this)
               // Log.e("test map", adress)
                var listAdress:List<Address> = geocoder.getFromLocationName(fullAdress,1)

                 if (listAdress.size>0) {
                     latitude = listAdress[0].latitude
                     longitude = listAdress[0].longitude
                     insertInDatabase()
                     finish()

                 }else{
                     latitude = 0.0
                     longitude = 0.0
                     alertDialogWrongAdress()

                 }




            }



        }

        button_photo.setOnClickListener {
            choice = 1
            onClickAddFile()
        }

        button_album.setOnClickListener {
            choice = 2
            onClickAddFile()
        }

        spinner_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                datePickerSale = DatePicker(baseContext)

                if (position == 2) {
                    activity_new_property_layout_date.addView(datePickerSale)
                } else {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 2 - Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    fun onClickAddFile() {
        chooseImageFromPhone()
    }


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

                var bitmap:Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,data!!.data)
                val id = UUID.randomUUID().toString()
                val photoChoice = saveToInternalStorage(this,bitmap,id)
                if (choice == 1) {
                    photo = photoChoice
                    //uri = data!!.data
                } else {

                    editTextList.add(addPhoto(photoChoice))
                    photoList.add(photoChoice)
                    //editTextList.add(addPhoto(data!!.data.toString()))
                    //uriList.add(data!!.data)
                }

            } else {
                Toast.makeText(this, "pas d'image choisie", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun addPhoto(photo: String): EditText {
        var image = ImageView(this)
        var editText = EditText(this)
        var linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        var file = File(photo)
        Picasso.get().load(file).resize(200, 200).into(image)

        linearLayout.addView(image)
        linearLayout.addView(editText)
        new_property_activity_album_photo.addView(linearLayout)

        return editText

    }


    fun datePicker(datePicker: DatePicker): String {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        return day.toString() + "/" + month.toString() + "/" + year.toString()
    }

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
    }

    fun alertDialogWrongAdress(){
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Adresse non trouvé")

        builder.setMessage("Votre adresse ne peut pas être localisée voulez-vous la modifier ?")

        builder.setPositiveButton("YES"){dialog, which ->

        }

        builder.setNegativeButton("No"){dialog,which ->
            insertInDatabase()
            finish()
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

    fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, path: String): String {
        val cw = ContextWrapper(context)
// path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
// Create imageDir
        val mypath = File(directory, path)

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
// Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        Log.e("save", mypath.absolutePath)
        return mypath.absolutePath
    }
*/
}
