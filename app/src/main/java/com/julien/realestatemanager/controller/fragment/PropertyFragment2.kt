package com.julien.realestatemanager.controller.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.PropertyActivity
import com.julien.realestatemanager.models.PropertyViewModel
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_new_property_fragment2.*

/**
 * A simple [Fragment] subclass.
 */
class PropertyFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_property_fragment2, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val propertyActivity: PropertyActivity = activity as PropertyActivity


        if (!propertyActivity.intent.getBooleanExtra("isNewProperty",true)){

            loadProperty(propertyActivity)
        }

        PushDownAnim.setPushDownAnimTo(nextToC).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {


            if (validateForm()) {
                save(propertyActivity)
                view.findNavController().navigate(R.id.fragmentBtoC)
                //activity?.findViewById<Stepper>(R.id.Stepper)?.forward()

            }

        }

        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().popBackStack()
           // activity?.findViewById<Stepper>(R.id.Stepper)?.back()
        }
    }
    private fun save(propertyActivity: PropertyActivity){

        propertyActivity.type = spinner_type.selectedItem.toString()
        propertyActivity.numberOfRooms = edit_number_of_romms_fragment_2.text.toString().toInt()
        propertyActivity.numberOfBathrooms = edit_number_of_batthrooms_fragment_2.text.toString().toInt()
        propertyActivity.numberOfBedrooms = edit_number_of_bedrooms_fragment_2.text.toString().toInt()
        propertyActivity.area = edit_area_fragment_2.text.toString().toInt()
        propertyActivity.price = edit_price_fragment_2.text.toString().toInt()
    }

    private fun validateForm():Boolean{
        if (edit_number_of_romms_fragment_2.text.toString().trim() != "" &&
            edit_number_of_romms_fragment_2.text.toString().trim() != "" &&
            edit_number_of_bedrooms_fragment_2.text.toString().trim() != "" &&
            edit_area_fragment_2.text.toString().trim() != "" &&
            edit_price_fragment_2.text.toString().trim() != ""  ){
            return true
        }else{

            if(edit_number_of_romms_fragment_2.text.toString().trim() == ""){
                edit_number_of_romms_fragment_2.error = getString(R.string.field_cannot_be_blank)
            }
            if(edit_number_of_batthrooms_fragment_2.text.toString().trim() == "" ){
                edit_number_of_batthrooms_fragment_2.error = getString(R.string.field_cannot_be_blank)
            }
            if (edit_number_of_bedrooms_fragment_2.text.toString().trim() == ""){
                edit_number_of_bedrooms_fragment_2.error = getString(R.string.field_cannot_be_blank)
            }
            if (edit_area_fragment_2.text.toString().trim() == ""){
                edit_area_fragment_2.error = getString(R.string.field_cannot_be_blank)
            }
            if (edit_price_fragment_2.text.toString().trim() == ""){
                edit_price_fragment_2.error = getString(R.string.field_cannot_be_blank)
            }

            return false
        }
    }

    private fun loadProperty(propertyActivity: PropertyActivity){

        val propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)



        propertyViewModel.getProperty(propertyActivity.intent.getStringExtra("id")).observe(this, Observer { property ->
            // Update the cached copy of the words in the adapter.
            property?.let {

                if (property.type == "Appartement"){
                    spinner_type.setSelection(0)
                }
                if (property.type == "Loft"){
                    spinner_type.setSelection(1)
                }
                if (property.type == "Manoir"){
                    spinner_type.setSelection(2)
                }
                if (property.type == "Maison"){
                    spinner_type.setSelection(3)
                }
                edit_number_of_romms_fragment_2.setText(property.numberOfRooms.toString())
                edit_number_of_batthrooms_fragment_2.setText(property.numberOfBathrooms.toString())
                edit_number_of_bedrooms_fragment_2.setText(property.numberOfBathrooms.toString())
                edit_area_fragment_2.setText(property.area.toString())
                edit_price_fragment_2.setText(property.price.toString())




            }
        })

    }
}
