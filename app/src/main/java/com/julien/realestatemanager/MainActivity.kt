package com.julien.realestatemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.views.PropertyAdaptater
import kotlinx.android.synthetic.main.activity_property_detail.*
import kotlinx.android.synthetic.main.fragment_property_list.*

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
            if(frame_layout_property_detail != null){

                add(R.id.frame_layout_property_detail,propertyDetail)

            }
        }


    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
