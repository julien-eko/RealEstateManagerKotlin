package com.julien.realestatemanager.controller.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.R
import com.julien.realestatemanager.Utils
import com.julien.realestatemanager.models.PropertyViewModel
import kotlinx.android.synthetic.main.activity_simulator.*
import java.time.Year

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

                alertDialog(monthPrice(simulator_price.text.toString().toFloat(),simulator_taux.text.toString().toFloat(),simulator_apport.text.toString().toFloat(),simulator_duree.text.toString().toFloat()),totalPrice(simulator_price.text.toString().toFloat(),simulator_taux.text.toString().toFloat()),simulator_duree.text.toString())
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

    fun alertDialog(price: Float, totalPrice:Float, year:String){
        val builder = AlertDialog.Builder(this)
        val price2digits = String.format("%.2f", price)
        val totalPricerice2digits = String.format("%.2f", totalPrice)
        
        var preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var isUSD = preferences.getBoolean("isUSD",true)
        
        val currency:String = if(isUSD){
            "$"
        }else{
            "€"
        }
        builder.setTitle(getString(R.string.simulator))


        builder.setMessage(getString(R.string.totalPrice) + totalPricerice2digits + " " + currency + "\n" + price2digits + " " + currency  + " " + getString(R.string.per_month) + getString(
                    R.string.for_years) + year + getString(R.string.years))

        builder.setPositiveButton(getString(R.string.ok)){ dialog, which ->

            finish()
        }



        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

   fun monthPrice(priceProperty: Float,taux:Float,apport:Float,dure:Float):Float{
        var price = priceProperty + (priceProperty * (taux / 100))
       price -= apport
        var time = dure * 12

       return price / time
    }

    fun totalPrice(priceProperty: Float,taux:Float):Float{
        return priceProperty + (priceProperty * (taux / 100))

    }
}
