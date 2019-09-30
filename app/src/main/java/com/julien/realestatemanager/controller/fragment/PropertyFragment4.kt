package com.julien.realestatemanager.controller.fragment


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.PropertyActivity
import com.julien.realestatemanager.models.PropertyViewModel
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_new_property_fragment4.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment4.backArrow
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import androidx.lifecycle.Observer
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass.
 */


private const val PERMS = android.Manifest.permission.READ_EXTERNAL_STORAGE

private const val RC_IMAGE_PERMS = 100

private const val RC_CHOOSE_PHOTO = 200

private const val REQUEST_IMAGE_CAPTURE = 1


class NewPropertyFragment4 : Fragment() {

    private var choice: Int = 0





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_property_fragment4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val propertyActivity: PropertyActivity = activity as PropertyActivity


        if (!propertyActivity.intent.getBooleanExtra("isNewProperty",true)){

            loadProperty(propertyActivity)
        }

        PushDownAnim.setPushDownAnimTo(nextToE).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {


            if (propertyActivity.photo == "" && propertyActivity.intent.getBooleanExtra("isNewProperty",true) ){
                Toast.makeText(context,getString(R.string.select_main_picture),Toast.LENGTH_SHORT).show()
            }else{
                view.findNavController().navigate(R.id.fragmentDtoE)
                //activity?.findViewById<Stepper>(R.id.Stepper)?.forward()
            }

        }
        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().popBackStack()
            //activity?.findViewById<Stepper>(R.id.Stepper)?.back()
        }

        button_main_photo_capture.setOnClickListener {
            choice = 1
            if( context?.packageManager!!.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY )  ){
                dispatchTakePictureIntent()
            }else{
                Toast.makeText(context,getString(R.string.not_camera),Toast.LENGTH_SHORT).show()
            }

            //onClickAddFile()
        }

        button_main_photo_galery.setOnClickListener {
            choice = 1
            onClickAddFile()
        }

        button_album_capture.setOnClickListener {
            choice = 2

            if( context?.packageManager!!.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY )  ){
                dispatchTakePictureIntent()
            }else{
                Toast.makeText(context,getString(R.string.not_camera),Toast.LENGTH_SHORT).show()
            }
        }


        button_album_galery.setOnClickListener {
            choice = 2
            onClickAddFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 6 - Calling the appropriate method after activity result
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap


            val id = UUID.randomUUID().toString()
            val photoChoice = saveToInternalStorage(context!!,imageBitmap,id)
            val propertyActivity: PropertyActivity =
                activity as PropertyActivity

            if (choice == 1) {
                propertyActivity.photo = photoChoice
                addMainPicture(propertyActivity.photo)
                //uri = data!!.data
            } else {

                propertyActivity.editTextList.add(addPicture(photoChoice))
                propertyActivity.photoList.add(photoChoice)
                //editTextList.add(addPhoto(data!!.data.toString()))
                //uriList.add(data!!.data)
            }

        }
        if (requestCode == RC_CHOOSE_PHOTO && resultCode == Activity.RESULT_OK) {
            handleResponse(requestCode, resultCode, data)
        }

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

    private fun chooseImageFromPhone() {
        if (!EasyPermissions.hasPermissions(context!!, PERMS)) {
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

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context?.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }



    // 4 - Handle activity response (after user has chosen or not a picture)
    private fun handleResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) { //SUCCESS
                //uriImage = data!!.data

                var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,data!!.data)
                val id = UUID.randomUUID().toString()
                val photoChoice = saveToInternalStorage(context!!,bitmap,id)
                val propertyActivity: PropertyActivity =
                    activity as PropertyActivity

                if (choice == 1) {
                    propertyActivity.photo = photoChoice
                    addMainPicture(propertyActivity.photo)
                    //uri = data!!.data
                } else {

                    propertyActivity.editTextList.add(addPicture(photoChoice))
                    propertyActivity.photoList.add(photoChoice)
                    //editTextList.add(addPhoto(data!!.data.toString()))
                    //uriList.add(data!!.data)
                }

            } else {
                Toast.makeText(context, getString(R.string.no_picture_choose), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun addMainPicture(photo:String){
        var file = File(photo)
        Picasso.get().load(file).resize(200, 200).into(main_picture)
        text_view_main_picture.setText("Change main picture")

    }
    private fun addPicture(photo: String): EditText {
        var image = ImageView(context)
        var editText = EditText(context)
        var linearLayout = LinearLayout(context)
        //var deleteButton = ImageButton(context)
        //deleteButton.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.property))
        //deleteButton.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.baseline_delete_black_24))
        //imageButton.background = ContextCompat.getDrawable(context!!,R.drawable.baseline_add_24)

        linearLayout.orientation = LinearLayout.HORIZONTAL

        var file = File(photo)
        Picasso.get().load(file).resize(200, 200).into(image)

        linearLayout.addView(image)
        linearLayout.addView(editText)
        //linearLayout.addView(deleteButton)
        other_photo.addView(linearLayout)

        return editText

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

    private fun loadProperty(propertyActivity: PropertyActivity){

        val propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)



        propertyViewModel.getProperty(propertyActivity.intent.getStringExtra("id")).observe(this, Observer { property ->
            // Update the cached copy of the words in the adapter.
            property?.let {

                propertyActivity.photo = property.photo!!
                addMainPicture(property.photo!!)

            }
        })


        propertyViewModel.getMedia(propertyActivity.intent.getStringExtra("id")).observe(this, Observer { medias ->
            // Update the cached copy of the words in the adapter.
            medias?.let {

                for (media in medias){

                    var image = ImageView(context)
                    var editText = EditText(context)
                    editText.setText(media.description)
                    var linearLayout = LinearLayout(context)
                    linearLayout.orientation = LinearLayout.HORIZONTAL

                    var file = File(media.photo)
                    Picasso.get().load(file).resize(200, 200).into(image)

                    linearLayout.addView(image)
                    linearLayout.addView(editText)
                    other_photo.addView(linearLayout)

                    propertyActivity.editTextListEdited.add(editText)
                    propertyActivity.idMedia.add(media.id)
                    propertyActivity.photoListEdited.add(media.photo!!)


                }

            }
        })

    }
}
