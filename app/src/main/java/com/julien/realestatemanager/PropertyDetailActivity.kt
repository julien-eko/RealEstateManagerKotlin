package com.julien.realestatemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

class PropertyDetailActivity : AppCompatActivity() {

    private lateinit var propertyDetail: PropertyDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        propertyDetail = PropertyDetailFragment()


        // propertyList =  supportFragmentManager.findFragmentById(R.id.frame_layout_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout_property_detail, propertyDetail)
        }

    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
