package com.julien.realestatemanager.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.fragment.PropertyDetailFragment

class PropertyDetailActivity : AppCompatActivity() {

    private lateinit var propertyDetail: PropertyDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        propertyDetail = PropertyDetailFragment()


        // propertyList =  supportFragmentManager.findFragmentById(R.id.frame_layout_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout_property_detail, propertyDetail,intent.getIntExtra("test",8).toString())
        }

    }

    inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
