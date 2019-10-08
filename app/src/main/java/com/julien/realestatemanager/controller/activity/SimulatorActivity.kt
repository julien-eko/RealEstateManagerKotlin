package com.julien.realestatemanager.controller.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.julien.realestatemanager.R
import com.julien.realestatemanager.Utils
import com.julien.realestatemanager.models.PropertyViewModel
import kotlinx.android.synthetic.main.activity_simulator.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment2.*

class SimulatorActivity : AppCompatActivity() {

    private lateinit var propertyViewModel: PropertyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulator)

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        if (intent.getStringExtra("id") != "0") {
            var preferences = PreferenceManager.getDefaultSharedPreferences(this)
            var isUSD = preferences.getBoolean("isUSD",true)
            //Toast.makeText(this,isUSD.toString(),Toast.LENGTH_SHORT).show()

            setUpPricce(intent.getStringExtra("id"),isUSD)

        }

        simulator_back_button.setOnClickListener {
            finish()
        }

        simuate_button.setOnClickListener {
            if(validateForm()){
                var price = simulator_price.text.toString().toFloat() + (simulator_price.text.toString().toFloat() * (simulator_taux.text.toString().toFloat() / 100))
                price = price - simulator_apport.text.toString().toFloat()
                var time = simulator_duree.text.toString().toFloat() * 12

                var monthPrice = price / time

                alertDialog(monthPrice)
                //Toast.makeText(this,monthPrice.toString(),Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun setUpPricce(id:String, isUSD:Boolean){
        propertyViewModel.getProperty(id).observe(this, Observer { property ->
            // Update the cached copy of the words in the adapter.
            property?.let {
                var price:Int = if(isUSD){
                    property.price
                }else{
                    Utils.convertDollarToEuro(property.price)
                }
                simulator_price.setText(price.toString())
            }
        })
    }

    private fun validateForm():Boolean{
        if (simulator_price.text.toString().trim() != "" &&
                simulator_apport.text.toString().trim() != "" &&
                simulator_taux.text.toString().trim() != "" &&
                simulator_duree.text.toString().trim() != ""
                ){
            if(simulator_taux.text.toString().toFloat() > 0){
                return true
            }else{
                if (simulator_taux.text.toString().toFloat() < 0){
                    simulator_taux.error =getString(R.string.sup)
                }
                return false
            }

        }else{

            if(simulator_price.text.toString().trim() == ""){
                simulator_price.error = getString(R.string.field_cannot_be_blank)
            }
            if(simulator_apport.text.toString().trim() == "" ){
                simulator_apport.error = getString(R.string.field_cannot_be_blank)
            }
            if (simulator_taux.text.toString().trim() == ""){
                simulator_taux.error = getString(R.string.field_cannot_be_blank)
            }
            if (simulator_duree.text.toString().trim() == ""){
                simulator_duree.error = getString(R.string.field_cannot_be_blank)
            }


            return false
        }
    }

    fun alertDialog(price: Float){
        val builder = AlertDialog.Builder(this)
        val price2digits:Double = String.format("%.2f", price).toDouble()
        
        var preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var isUSD = preferences.getBoolean("isUSD",true)
        
        val currency:String = if(isUSD){
            "$"
        }else{
            "€"
        }
        builder.setTitle(getString(R.string.simulator))

        
        builder.setMessage(price2digits.toString() + currency  + " " + getString(R.string.per_month))

        builder.setPositiveButton(getString(R.string.ok)){ dialog, which ->

            finish()
        }



        val dialog: AlertDialog = builder.create()

        dialog.show()
    }
}
