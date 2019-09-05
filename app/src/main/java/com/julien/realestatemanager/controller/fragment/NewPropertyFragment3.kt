package com.julien.realestatemanager.controller.fragment


import android.app.AlertDialog
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.CreatePropertyActivity
import com.tayfuncesur.stepper.Stepper
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_new_property.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment2.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment3.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment3.backArrow

/**
 * A simple [Fragment] subclass.
 */
class NewPropertyFragment3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_property_fragment3, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PushDownAnim.setPushDownAnimTo(nextToD).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            val createPropertyActivity: CreatePropertyActivity = activity as CreatePropertyActivity

            if (validateForm()){
                save(createPropertyActivity)

                val fullAdress = createPropertyActivity.adress + " " + createPropertyActivity.additionAdress + " " + createPropertyActivity.city + " " + createPropertyActivity.postalCode + " " + createPropertyActivity.country

                var geocoder= Geocoder(context)
                var listAdress:List<Address> = geocoder.getFromLocationName(fullAdress,1)

                if (listAdress.size>0) {
                    createPropertyActivity.latitude = listAdress[0].latitude
                    createPropertyActivity.longitude = listAdress[0].longitude
                    view.findNavController().navigate(R.id.fragmentCtoD)
                    activity?.findViewById<Stepper>(R.id.Stepper)?.forward()

                }else{
                    createPropertyActivity.latitude = 0.0
                    createPropertyActivity.longitude = 0.0
                    alertDialogWrongAdress()

                }
            }





        }

        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP,5F).setOnClickListener {
            view.findNavController().popBackStack()
            activity?.findViewById<Stepper>(R.id.Stepper)?.back()
        }
    }
    fun alertDialogWrongAdress(){
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Adresse non trouvé")

        builder.setMessage("Votre adresse ne peut pas être localisée voulez-vous la modifier ?")

        builder.setPositiveButton("YES"){dialog, which ->

        }

        builder.setNegativeButton("No"){dialog,which ->
            view!!.findNavController().navigate(R.id.fragmentCtoD)
            activity?.findViewById<Stepper>(R.id.Stepper)?.forward()
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

    private fun save(createPropertyActivity: CreatePropertyActivity){

        createPropertyActivity.adress = edit_adress_fragment_3.text.toString()
        createPropertyActivity.additionAdress = edit_additional_adress_fragment_3.text.toString()
        createPropertyActivity.country = edit_country_fragment_3.text.toString()
        createPropertyActivity.postalCode = edit_postal_code_fragment_3.text.toString()
        createPropertyActivity.city = edit_city_fragment_3.text.toString()
        createPropertyActivity.placeNearby = edit_place_nearby_fragment_3.text.toString()

    }

    private fun validateForm():Boolean{
        if (edit_adress_fragment_3.text.toString().trim() != "" &&
            edit_city_fragment_3.text.toString().trim() != "" &&
            edit_postal_code_fragment_3.text.toString().trim() != "" &&
            edit_postal_code_fragment_3.text.toString().trim() != ""){
            return true
        }else{
            if(edit_adress_fragment_3.text.toString().trim() == ""){
                edit_adress_fragment_3.error = "This field cannot be blank"
            }
            if(edit_city_fragment_3.text.toString().trim() == ""){
                edit_city_fragment_3.error = "This field cannot be blank"
            }
            if(edit_postal_code_fragment_3.text.toString().trim() == "" ){
                edit_postal_code_fragment_3.error = "This field cannot be blank"
            }
            if (edit_country_fragment_3.text.toString().trim() == ""){
                edit_country_fragment_3.error = "This field cannot be blank"
            }

            return false
        }
    }
}
