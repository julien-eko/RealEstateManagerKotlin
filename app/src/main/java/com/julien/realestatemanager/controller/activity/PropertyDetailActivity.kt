package com.julien.realestatemanager.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.fragment.PropertyDetailFragment
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_property_detail.*
import kotlinx.android.synthetic.main.fragment_property_detail.*

class PropertyDetailActivity : AppCompatActivity() {

    private lateinit var propertyDetail: PropertyDetailFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        propertyDetail = PropertyDetailFragment()

        configureToolbar()


        // propertyList =  supportFragmentManager.findFragmentById(R.id.frame_layout_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout_property_detail, propertyDetail,intent.getStringExtra("id").toString())
        }

    }


    private fun configureToolbar(){
        setSupportActionBar(activity_detail_toolbar)

        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle("Property")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
