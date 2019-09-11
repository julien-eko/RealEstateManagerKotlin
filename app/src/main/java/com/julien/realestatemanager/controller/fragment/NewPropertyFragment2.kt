package com.julien.realestatemanager.controller.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.navigation.findNavController

import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.CreatePropertyActivity
import com.tayfuncesur.stepper.Stepper
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment2.*

/**
 * A simple [Fragment] subclass.
 */
class NewPropertyFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_property_fragment2, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PushDownAnim.setPushDownAnimTo(nextToC).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            val createPropertyActivity: CreatePropertyActivity = activity as CreatePropertyActivity

            if (validateForm()) {
                save(createPropertyActivity)
                view.findNavController().navigate(R.id.fragmentBtoC)
                activity?.findViewById<Stepper>(R.id.Stepper)?.forward()

            }

        }

        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().popBackStack()
            activity?.findViewById<Stepper>(R.id.Stepper)?.back()
        }
    }
    private fun save(createPropertyActivity: CreatePropertyActivity){

        createPropertyActivity.type = edit_type_fragment_2.text.toString()
        createPropertyActivity.numberOfRooms = edit_number_of_romms_fragment_2.text.toString().toInt()
        createPropertyActivity.numberOfBathrooms = edit_number_of_batthrooms_fragment_2.text.toString().toInt()
        createPropertyActivity.numberOfBedrooms = edit_number_of_bedrooms_fragment_2.text.toString().toInt()
        createPropertyActivity.area = edit_area_fragment_2.text.toString().toInt()
        createPropertyActivity.price = edit_price_fragment_2.text.toString().toInt()
    }

    private fun validateForm():Boolean{
        if (edit_type_fragment_2.text.toString().trim() != "" &&
            edit_number_of_romms_fragment_2.text.toString().trim() != "" &&
            edit_number_of_romms_fragment_2.text.toString().trim() != "" &&
            edit_number_of_bedrooms_fragment_2.text.toString().trim() != "" &&
            edit_area_fragment_2.text.toString().trim() != "" &&
            edit_price_fragment_2.text.toString().trim() != ""  ){
            return true
        }else{
            if(edit_type_fragment_2.text.toString().trim() == ""){
                edit_type_fragment_2.error = "This field cannot be blank"
            }
            if(edit_number_of_romms_fragment_2.text.toString().trim() == ""){
                edit_number_of_romms_fragment_2.error = "This field cannot be blank"
            }
            if(edit_number_of_batthrooms_fragment_2.text.toString().trim() == "" ){
                edit_number_of_batthrooms_fragment_2.error = "This field cannot be blank"
            }
            if (edit_number_of_bedrooms_fragment_2.text.toString().trim() == ""){
                edit_number_of_bedrooms_fragment_2.error = "This field cannot be blank"
            }
            if (edit_area_fragment_2.text.toString().trim() == ""){
                edit_area_fragment_2.error = "This field cannot be blank"
            }
            if (edit_price_fragment_2.text.toString().trim() == ""){
                edit_price_fragment_2.error = "This field cannot be blank"
            }

            return false
        }
    }


}
