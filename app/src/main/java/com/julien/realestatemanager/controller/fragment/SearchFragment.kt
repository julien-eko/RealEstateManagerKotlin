package com.julien.realestatemanager.controller.fragment


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment

import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.MainActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var buttonMinCreated:Button
    private lateinit var buttonMaxCreated:Button
    private lateinit var  buttonMinSale:Button
    private lateinit var buttonMaxSale:Button
    private lateinit var buttonSearch:Button
    private lateinit var buttonCancel:Button
    private lateinit var spinner:Spinner
    private var lastButton:Int = 0

    private lateinit var typeEditText:EditText
    private lateinit var cityEditText:EditText
    private lateinit var minRoomEditText:EditText
    private lateinit var maxRoomEditText:EditText
    private lateinit var minAreaEditText:EditText
    private lateinit var maxAreaEditText:EditText
    private lateinit var minPriceEditText:EditText
    private lateinit var maxPricemEditText:EditText


    private lateinit var typeProperty:String
    private lateinit var statut:String
    private lateinit var city:String
    private var minArea:Int = 0
    private var maxArea:Int = 0
    private var minPrice:Int = 0
    private var maxPrice:Int = 0
    private var minRoom:Int = 0
    private var maxRoom:Int = 0
    private var minCreatedDate:Long = 0
    private var maxCreatedDate:Long = 0
    private var minSaleDate:Long = 0
    private var maxSaleDate:Long = 0



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        statut= "Dispo"
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_search, null)

            configureButtonDate(view)

            builder.setView(view)



            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")


    }


    private fun configureButtonDate(view: View) {

        spinner = view.findViewById<View>(R.id.search_fragment_spinner_status) as Spinner
        buttonMinCreated = view.findViewById<View>(R.id.min_created_date_button) as Button
        buttonMaxCreated = view.findViewById<View>(R.id.max_created_date_button) as Button
        buttonMinSale = view.findViewById<View>(R.id.min_sale__date_button) as Button
        buttonMaxSale = view.findViewById<View>(R.id.max_sale__date_button) as Button
        buttonSearch = view.findViewById<View>(R.id.reserach_button) as Button
        buttonCancel = view.findViewById<View>(R.id.cancel_button) as Button


        typeEditText = view.findViewById<View>(R.id.edit_search_fragment_type) as EditText
        cityEditText = view.findViewById<View>(R.id.edit_search_fragment_city) as EditText
        minRoomEditText = view.findViewById<View>(R.id.edit_search_fragment_min_Room) as EditText
        maxRoomEditText = view.findViewById<View>(R.id.edit_search_fragment_max_Room) as EditText
        minAreaEditText = view.findViewById<View>(R.id.edit_search_fragment_min_area) as EditText
        maxAreaEditText = view.findViewById<View>(R.id.edit_search_fragment_max_area) as EditText
        minPriceEditText = view.findViewById<View>(R.id.edit_search_fragment_min_Price) as EditText
        maxPricemEditText = view.findViewById<View>(R.id.edit_search_fragment_max_Price) as EditText

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val calendarMax = Calendar.getInstance()
        val year = calendarMax.get(Calendar.YEAR)
        val month = calendarMax.get(Calendar.MONTH)
        val day = calendarMax.get(Calendar.DAY_OF_MONTH)

        val calendarMin = Calendar.getInstance()
        calendarMin.set(2019,0,1)

        minCreatedDate = calendarMin.time.time
        maxCreatedDate = calendarMax.time.time

        if (statut == "Dispo"){
            minSaleDate = 0
            maxSaleDate = calendarMax.time.time
        }else{
            minSaleDate = calendarMin.time.time
            maxSaleDate = calendarMax.time.time
        }


        buttonMinCreated.text = dateFormat.format(calendarMin.time)
        buttonMinSale.text = dateFormat.format(calendarMin.time)
        buttonMaxCreated.text = dateFormat.format(calendarMax.time)
        buttonMaxSale.text = dateFormat.format(calendarMax.time)

        buttonMinCreated.setOnClickListener {
            lastButton = 1
            val minCreated = DatePickerDialog(context, this, 2019, 1, 1)
            minCreated.show()
        }

        buttonMaxCreated.setOnClickListener {
            lastButton =2
            val maxCreated = DatePickerDialog(context, this, year, month, day)
            maxCreated.show()
        }

        buttonMinSale.setOnClickListener {
            lastButton = 3
            val minSale = DatePickerDialog(context, this,2019, 1, 1)
            minSale.show()
        }

        buttonMaxSale.setOnClickListener {
            lastButton = 4
            val maxSale = DatePickerDialog(context, this, year, month, day)
            maxSale.show()
        }

        buttonSearch.setOnClickListener {
            if(validateForm()){
                runSearch(view)
                dismiss()
            }
        }

        buttonCancel.setOnClickListener {
            dismiss()
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                if (position == 1) {
                    statut = "Vendu"
                    buttonMinSale.visibility = View.VISIBLE
                    buttonMaxSale.visibility = View.VISIBLE
                } else {
                    statut="Dispo"
                    buttonMinSale.visibility = View.GONE
                    buttonMaxSale.visibility = View.GONE
                }
            }

        }
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {


        var calendar = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        when(lastButton){
            1 ->{
                buttonMinCreated.text = dateFormat.format(calendar.time)
                minCreatedDate = calendar.time.time
            }
            2 ->{
                buttonMaxCreated.text = dateFormat.format(calendar.time)
                maxCreatedDate = calendar.time.time
            }
            3 -> {
                buttonMinSale.text = dateFormat.format(calendar.time)
                minSaleDate = calendar.time.time
            }
            4 ->{
                buttonMaxSale.text = dateFormat.format(calendar.time)
                maxSaleDate = calendar.time.time
            }
        }



    }

    private fun runSearch(view: View){



        typeProperty = typeEditText.text.toString().trim().toLowerCase()
        city = cityEditText.text.toString().trim().toLowerCase()
        minArea = minAreaEditText.text.toString().toInt()
        maxArea = maxAreaEditText.text.toString().toInt()
        minRoom = minRoomEditText.text.toString().toInt()
        maxRoom = maxRoomEditText.text.toString().toInt()
        minPrice =minPriceEditText.text.toString().toInt()
        maxPrice = maxPricemEditText.text.toString().toInt()


        val mainActivity: MainActivity = activity as MainActivity
        mainActivity.updateList(typeProperty,minArea,maxArea,minPrice,maxPrice,minSaleDate,maxSaleDate,statut,minCreatedDate,maxCreatedDate,city,minRoom,maxRoom )

    }


    private fun validateForm():Boolean{
        if (typeEditText.text.toString().trim() != "" &&
            cityEditText.text.toString().trim() != "" &&
            minRoomEditText.text.toString().trim() != "" &&
            maxRoomEditText.text.toString().trim() != "" &&
            minPriceEditText.text.toString().trim() != "" &&
            maxPricemEditText.text.toString().trim() != "" &&
            minAreaEditText.text.toString().trim() != "" &&
            maxAreaEditText.text.toString().trim() != ""){
            return true
        }else{
            if(typeEditText.text.toString().trim() == ""){
                typeEditText.error = "This field cannot be blank"
            }
            if(cityEditText.text.toString().trim() == ""){
                cityEditText.error = "This field cannot be blank"
            }
            if(minRoomEditText.text.toString().trim() == "" ){
                minRoomEditText.error = "This field cannot be blank"
            }
            if (maxRoomEditText.text.toString().trim() == ""){
                maxRoomEditText.error = "This field cannot be blank"
            }
            if (minPriceEditText.text.toString().trim() == ""){
                minPriceEditText.error = "This field cannot be blank"
            }
            if (maxPricemEditText.text.toString().trim() == ""){
                maxPricemEditText.error = "This field cannot be blank"
            }
            if (minAreaEditText.text.toString().trim() == ""){
                minAreaEditText.error = "This field cannot be blank"
            }
            if (maxAreaEditText.text.toString().trim() == ""){
                maxAreaEditText.error = "This field cannot be blank"
            }

            return false
        }
    }
}

