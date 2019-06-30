package com.julien.realestatemanager.controller.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.julien.realestatemanager.R
import kotlinx.android.synthetic.main.activity_new_property.*

class NewPropertyActivity : AppCompatActivity() {

    //private lateinit var editWordView: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_property)
        //editWordView = findViewById(R.id.edit_word)

        //val button = findViewById<Button>(R.id.button_save)
        button_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_type.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val type = edit_type.text.toString()
                val price = edit_price.text.toString()
                val area = edit_area.text.toString()
                val numberOfRooms = edit_number_of_romms.text.toString()
                val description = edit_description.text.toString()
                val adress = edit_adress.text.toString()
                val placeNearby = edit_place_nearby.text.toString()
                val status = edit_status.text.toString()
                val createdDate = edit_created_date.text.toString()
                val dateOfSale = edit_date_of_sale.text.toString()
                val realEstateAgent = edit_real_estate_agent.text.toString()
                val photo = edit_photo.text.toString()

                replyIntent.putExtra("type", type)
                replyIntent.putExtra("price", price)
                replyIntent.putExtra("area", area)
                replyIntent.putExtra("numberOfRooms", numberOfRooms)
                replyIntent.putExtra("description", description)
                replyIntent.putExtra("adress", adress)
                replyIntent.putExtra("placeNearby", placeNearby)
                replyIntent.putExtra("status", status)
                replyIntent.putExtra("createdDate", createdDate)
                replyIntent.putExtra("dateOfSale", dateOfSale)
                replyIntent.putExtra("realEstateAgent", realEstateAgent)
                replyIntent.putExtra("photo", photo)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
