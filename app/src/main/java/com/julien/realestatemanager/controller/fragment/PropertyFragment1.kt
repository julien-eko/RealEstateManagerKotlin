package com.julien.realestatemanager.controller.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProviders

import com.julien.realestatemanager.R
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.*
import androidx.navigation.findNavController
import com.julien.realestatemanager.controller.activity.PropertyActivity
import com.julien.realestatemanager.models.PropertyViewModel
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.edit_real_estate_agent
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.spinner_status
import java.util.*


/**
 * fragment create or edit property part 1
 * status
 * date created
 * date of sale if property sale
 *real estate agent
 */
class PropertyFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_new_property_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load last agent created property
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = ""
        val lastAgent = sharedPref.getString("agent", defaultValue)
        edit_real_estate_agent.setText(lastAgent)


        val propertyActivity: PropertyActivity = activity as PropertyActivity


        if (!propertyActivity.intent.getBooleanExtra("isNewProperty",true)){
            loadProperty(propertyActivity)
        }


        //if property sale display date of sale
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
                    date_of_sale_text_view_fragment1.visibility =View.VISIBLE
                    datePicker_sale_date.visibility = View.VISIBLE
                } else {
                    date_of_sale_text_view_fragment1.visibility =View.GONE
                    datePicker_sale_date.visibility = View.GONE
                }
            }

        }



        //next fragment
        PushDownAnim.setPushDownAnimTo(nextToB).setScale(PushDownAnim.MODE_STATIC_DP, 5F)
            .setOnClickListener {



                if (edit_real_estate_agent.text.toString().trim() == ""){
                    edit_real_estate_agent.error = getString(R.string.field_cannot_be_blank)
                }else{
                    save(propertyActivity)
                    view.findNavController().navigate(R.id.fragmentAtoB)
                    //activity?.findViewById<Stepper>(R.id.Stepper)?.forward()
                }


            }


    }



    private fun datePicker(datePicker: DatePicker): Long {

        var calendar = Calendar.getInstance()
        calendar.set(datePicker.year,datePicker.month,datePicker.dayOfMonth)

        return calendar.time.time


    }

    private fun save(propertyActivity: PropertyActivity){
        propertyActivity.realEstateAgent = edit_real_estate_agent.text.toString()
        propertyActivity.status = spinner_status.selectedItem.toString()
        propertyActivity.createdDate = datePicker(datePicker_created_date)
        propertyActivity.dateOfSale = if (propertyActivity.status.equals("Vendu")) {
            datePicker(datePicker_sale_date)
        }else{
            Calendar.getInstance().time.time
        }
    }

    private fun loadProperty(propertyActivity: PropertyActivity){

        val propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)



        propertyViewModel.getProperty(propertyActivity.intent.getStringExtra("id")).observe(this, Observer { property ->
            // Update the cached copy of the words in the adapter.
            property?.let {
                edit_real_estate_agent.setText(property.realEstateAgent)

                //val date = Date(property.creationDate)

                val calendar = Calendar.getInstance()
                calendar.time = Date(property.creationDate)
                datePicker_created_date.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))

                if (property.status == "Vendu"){
                spinner_status.setSelection(1)
                    val calendarSale = Calendar.getInstance()
                    calendarSale.time = Date(property.dateOfSale)
                    datePicker_sale_date.updateDate(calendarSale.get(Calendar.YEAR),calendarSale.get(Calendar.MONTH),calendarSale.get(Calendar.DAY_OF_MONTH))

                }


            }
        })

    }
}



