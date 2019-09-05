package com.julien.realestatemanager.controller.fragment


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.findNavController

import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.CreatePropertyActivity
import com.squareup.picasso.Picasso
import com.tayfuncesur.stepper.Stepper
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_new_property.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment4.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment4.button_album
import kotlinx.android.synthetic.main.fragment_new_property_fragment4.button_photo
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
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
        PushDownAnim.setPushDownAnimTo(nextToE).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            val createPropertyActivity: CreatePropertyActivity = activity as CreatePropertyActivity

            if (createPropertyActivity.photo == ""){
                Toast.makeText(context,"Select main picture",Toast.LENGTH_SHORT).show()
            }else{
                view.findNavController().navigate(R.id.fragmentDtoE)
                activity?.findViewById<Stepper>(R.id.Stepper)?.forward()
            }

        }
        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().popBackStack()
            activity?.findViewById<Stepper>(R.id.Stepper)?.back()
        }

        button_photo.setOnClickListener {
            choice = 1
            onClickAddFile()
        }

        button_album.setOnClickListener {
            choice = 2
            onClickAddFile()
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

    // 4 - Handle activity response (after user has chosen or not a picture)
    private fun handleResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) { //SUCCESS
                //uriImage = data!!.data

                var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,data!!.data)
                val id = UUID.randomUUID().toString()
                val photoChoice = saveToInternalStorage(context!!,bitmap,id)
                val createPropertyActivity: CreatePropertyActivity =
                    activity as CreatePropertyActivity

                if (choice == 1) {
                    createPropertyActivity.photo = photoChoice
                    addMainPicture(createPropertyActivity.photo)
                    //uri = data!!.data
                } else {

                    createPropertyActivity.editTextList.add(addPicture(photoChoice))
                    createPropertyActivity.photoList.add(photoChoice)
                    //editTextList.add(addPhoto(data!!.data.toString()))
                    //uriList.add(data!!.data)
                }

            } else {
                Toast.makeText(context, "pas d'image choisie", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun addMainPicture(photo:String){
        var file = File(photo)
        Picasso.get().load(file).resize(200, 200).into(main_picture)

    }
    fun addPicture(photo: String): EditText {
        var image = ImageView(context)
        var editText = EditText(context)
        var linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        var file = File(photo)
        Picasso.get().load(file).resize(200, 200).into(image)

        linearLayout.addView(image)
        linearLayout.addView(editText)
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
}
