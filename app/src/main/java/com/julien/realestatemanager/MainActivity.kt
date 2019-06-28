package com.julien.realestatemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.views.PropertyAdaptater
import kotlinx.android.synthetic.main.fragment_property_list.*

class MainActivity : AppCompatActivity() {

    private lateinit var propertyList: PropertyList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyList = PropertyList()


       // propertyList =  supportFragmentManager.findFragmentById(R.id.frame_layout_main)
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout_main, propertyList)
        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
