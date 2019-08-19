package com.julien.realestatemanager.controller.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.julien.realestatemanager.R
import kotlinx.android.synthetic.main.activity_new_property.*
import pub.devrel.easypermissions.AfterPermissionGranted
import java.util.jar.Manifest
import pub.devrel.easypermissions.EasyPermissions
import android.widget.Toast
import android.provider.MediaStore


import android.graphics.Bitmap.CompressFormat

import java.io.ByteArrayOutputStream


private const val PERMS = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private const val RC_IMAGE_PERMS = 100

    private  const val RC_CHOOSE_PHOTO = 200

class NewPropertyActivity : AppCompatActivity() {

    //private lateinit var editWordView: EditText

    //private var uriImage: Uri
    //private var uriImage1: Uri
    //private var uriImage2: Uri
    //private var uriImage3: Uri
    //private var uriImage4: Uri
    //private var uriImage5: Uri
    //private var uriImage6: Uri

    private var uriList:ArrayList<String> = ArrayList<String>()

    private lateinit var uri: Uri

    //private lateinit var bitmap: Bitmap
    //private lateinit var photo:ByteArray




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_property)
        //editWordView = findViewById(R.id.edit_word)

        //val button = findViewById<Button>(R.id.button_save)
        button_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_type.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val city = edit_city.text.toString()
                val type = edit_type.text.toString()
                val price = edit_price.text.toString()
                val area = edit_area.text.toString()
                val numberOfRooms = edit_number_of_romms.text.toString()
                val description = edit_description.text.toString()
                val adress = edit_adress.text.toString()
                val placeNearby = edit_place_nearby.text.toString()
                val status = edit_status.text.toString()
                val createdDate = edit_created_date.text.toString()
                val dateOfSale = edit_date_of_sale.text.toString()
                val realEstateAgent = edit_real_estate_agent.text.toString()



                replyIntent.putExtra("city", city)
                replyIntent.putExtra("type", type)
                replyIntent.putExtra("price", price)
                replyIntent.putExtra("area", area)
                replyIntent.putExtra("numberOfRooms", numberOfRooms)
                replyIntent.putExtra("description", description)
                replyIntent.putExtra("adress", adress)
                replyIntent.putExtra("placeNearby", placeNearby)
                replyIntent.putExtra("status", status)
                replyIntent.putExtra("createdDate", createdDate)
                replyIntent.putExtra("dateOfSale", dateOfSale)
                replyIntent.putExtra("realEstateAgent", realEstateAgent)
                replyIntent.putExtra("photo", uri.toString())

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        button_photo.setOnClickListener {
            onClickAddFile()
            //val photo = edit_photo.text.toString()
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
                uri = data!!.data
            } else {
                Toast.makeText(this, "pas d'image choisie", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
