package com.julien.realestatemanager.controller.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.julien.realestatemanager.R
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.fragment_new_property_fragment1.*

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)



        //Save in sharedPreference currency preference of user
        var preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var isUSD = preferences.getBoolean("isUSD",true)

        if (isUSD){
            spinner_currency.setSelection(0)
        }else{
            spinner_currency.setSelection(1)
        }

        setting_button_validate.setOnClickListener {
            if (spinner_currency.selectedItemPosition == 0){
               var preferences = PreferenceManager.getDefaultSharedPreferences(this)
               var editor = preferences.edit()
                editor.putBoolean("isUSD",true)
                editor.commit()
            }else{
                var preferences = PreferenceManager.getDefaultSharedPreferences(this)
                var editor = preferences.edit()
                editor.putBoolean("isUSD",false)
                editor.commit()
            }


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }




    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
