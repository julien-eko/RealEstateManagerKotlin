package com.julien.realestatemanager.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.fragment.PropertyDetailFragment
import com.julien.realestatemanager.controller.fragment.PropertyListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var propertyList: PropertyListFragment
    private lateinit var propertyDetail: PropertyDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyList = PropertyListFragment()
        propertyDetail = PropertyDetailFragment()
       // propertyList =  supportFragmentManager.findFragmentById(R.id.frame_layout_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout_property_list, propertyList)
            val isTablet: Boolean = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {
                    add(R.id.frame_layout_property_detail,propertyDetail)

            }
        }


    }

    inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
