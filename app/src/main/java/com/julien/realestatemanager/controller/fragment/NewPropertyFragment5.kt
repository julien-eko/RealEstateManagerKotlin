package com.julien.realestatemanager.controller.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.CreatePropertyActivity
import com.tayfuncesur.stepper.Stepper
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment5.*

/**
 * A simple [Fragment] subclass.
 */
class NewPropertyFragment5 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_property_fragment5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        PushDownAnim.setPushDownAnimTo(progress).setScale(PushDownAnim.MODE_STATIC_DP, 5F).setOnClickListener {


                activity?.findViewById<Stepper>(R.id.Stepper)?.progress(3)?.addOnCompleteListener {

                    val createPropertyActivity: CreatePropertyActivity =
                        activity as CreatePropertyActivity



                    createPropertyActivity.description = edit_description_fragment_5.text.toString()

                    createPropertyActivity.insertInDatabase()

                    activity?.findViewById<View>(R.id.StepperView)?.background =
                        ContextCompat.getDrawable(context!!, R.drawable.success_gradient)


            }

        }



        PushDownAnim.setPushDownAnimTo(backArrow).setScale(PushDownAnim.MODE_STATIC_DP, 5F).setOnClickListener {
            view.findNavController().popBackStack()
            activity?.findViewById<Stepper>(R.id.Stepper)?.stop()
            activity?.findViewById<Stepper>(R.id.Stepper)?.back()

        }
        PushDownAnim.setPushDownAnimTo(progressStop).setScale(PushDownAnim.MODE_STATIC_DP, 5F).setOnClickListener {
            activity?.findViewById<Stepper>(R.id.Stepper)?.stop()
            activity?.finish()
        }
    }
}
