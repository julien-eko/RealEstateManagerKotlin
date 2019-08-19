package com.julien.realestatemanager.controller.fragment


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
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PropertyDetailFragment : androidx.fragment.app.Fragment() {


    private lateinit var propertyViewModel: PropertyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_property_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        if(tag != null){
            propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)
            propertyViewModel.getProperty(tag!!.toInt()).observe(this, Observer { property ->
                // Update the cached copy of the words in the adapter.
                property?.let {
                    //adapter.setWords(it)
                    //adapter.setProperties(property)
                    //Log.e("test", property.photo[0].toString())
                    //var list: List<String> = Arrays.asList(property.photo.split("\\s*,\\s*"))

                    //var result: List<String> = property.photo.split(",").map { it.trim() }
                    //Log.e("test", result[0].toByteArray().toString())
/*
                    if(property.photo != null){

                        val bitmap:Bitmap = BitmapFactory.decodeByteArray(property.photo, 0, property.photo.size)
                        //photo.text = property.photo

                    Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(bitmap)
                        .apply(RequestOptions.circleCropTransform())
                        .into(image_test)

                    }
*/

                    description_text_view.text = property.description
                    area_text_view.text = property.area
                    number_of_rooms_text_view.text = property.numberOfRooms
                    adress_text_view.text = property.adress
                }


            })
        }
        fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
            return outputStream.toByteArray()
        }
    }


}
