package com.julien.realestatemanager.controller.fragment


import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast

import com.julien.realestatemanager.R
import com.tayfuncesur.stepper.Stepper
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.*
import androidx.navigation.findNavController
import com.julien.realestatemanager.controller.activity.CreatePropertyActivity
import kotlinx.android.synthetic.main.activity_new_property.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.edit_real_estate_agent
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.spinner_status


/**
 * A simple [Fragment] subclass.
 */
class NewPropertyFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_property_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                if (position == 1) {
                    datePicker_sale_date.visibility = View.VISIBLE
                } else {
                    datePicker_sale_date.visibility = View.GONE
                }
            }

        }



        PushDownAnim.setPushDownAnimTo(nextToB).setScale(PushDownAnim.MODE_STATIC_DP, 5F)
            .setOnClickListener {

                val createPropertyActivity: CreatePropertyActivity = activity as CreatePropertyActivity

                if (edit_real_estate_agent.text.toString().trim() == ""){
                    edit_real_estate_agent.error = "This field cannot be blank"
                }else{
                    save(createPropertyActivity)
                    view.findNavController().navigate(R.id.fragmentAtoB)
                    activity?.findViewById<Stepper>(R.id.Stepper)?.forward()
                }


            }


    }

    private fun datePicker(datePicker: DatePicker): String {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        return day.toString() + "/" + month.toString() + "/" + year.toString()
    }

    private fun save(createPropertyActivity: CreatePropertyActivity){
        createPropertyActivity.realEstateAgent = edit_real_estate_agent.text.toString()
        createPropertyActivity.status = spinner_status.selectedItem.toString()
        createPropertyActivity.createdDate = datePicker(datePicker_created_date)
        createPropertyActivity.dateOfSale = if (createPropertyActivity.status.equals("Vendu")) {
            datePicker(datePicker_sale_date)
        }else{
            ""
        }
    }
}



