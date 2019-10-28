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
import com.julien.realestatemanager.Database.PropertyViewModel
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_new_property_fragment5.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment5.backArrow

/**
 *fragment create or edit property part 5
 * description of property
 */
class PropertyFragment5 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_property_fragment5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val propertyActivity: PropertyActivity = activity as PropertyActivity


        if (!propertyActivity.intent.getBooleanExtra("isNewProperty", true)) {

            loadProperty(propertyActivity)
        }

        PushDownAnim.setPushDownAnimTo(progress).setScale(PushDownAnim.MODE_STATIC_DP, 5F)
            .setOnClickListener {


                propertyActivity.description = edit_description_fragment_5.text.toString()

                propertyActivity.insertInDatabase()


            }



        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP, 5F)
            .setOnClickListener {
                view.findNavController().popBackStack()
                //activity?.findViewById<Stepper>(R.id.Stepper)?.stop()
                //activity?.findViewById<Stepper>(R.id.Stepper)?.back()

            }
        PushDownAnim.setPushDownAnimTo(progressStop).setScale(PushDownAnim.MODE_STATIC_DP, 5F)
            .setOnClickListener {
                //activity?.findViewById<Stepper>(R.id.Stepper)?.stop()
                activity?.finish()
            }
    }

    private fun loadProperty(propertyActivity: PropertyActivity) {

        val propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)



        propertyViewModel.getProperty(propertyActivity.intent.getStringExtra("id"))
            .observe(this, Observer { property ->
                // Update the cached copy of the words in the adapter.
                property?.let {

                    edit_description_fragment_5.setText(property.description)

                }
            })

    }
}
