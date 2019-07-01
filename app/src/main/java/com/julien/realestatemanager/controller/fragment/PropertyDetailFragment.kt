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

                    photo.text = property.photo
                    description_text_view.text = property.description
                    area_text_view.text = property.area
                    number_of_rooms_text_view.text = property.numberOfRooms
                    adress_text_view.text = property.adress
                }


            })
        }

    }


}
