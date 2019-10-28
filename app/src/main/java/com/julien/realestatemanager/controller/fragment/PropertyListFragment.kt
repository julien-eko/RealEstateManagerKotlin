package com.julien.realestatemanager.controller.fragment


import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.Database.PropertyViewModel
import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.PropertyDetailActivity
import com.openclassrooms.realestatemanager.views.PropertyAdaptater
import kotlinx.android.synthetic.main.fragment_property_list.*


/**
 * property list
 *
 */
class PropertyListFragment : androidx.fragment.app.Fragment() {


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PropertyAdaptater
    private lateinit var propertyDetail: PropertyDetailFragment
    private lateinit var propertyList: PropertyListFragment

    private lateinit var propertyViewModel: PropertyViewModel

    private lateinit var lastId: String


    lateinit var idProperty: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_property_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lastId = ""
        linearLayoutManager = LinearLayoutManager(context)
        recycler_view_property.layoutManager = linearLayoutManager

        if (tag != null) {
            idProperty = tag!!
        } else {
            idProperty = "0"
        }


        var preferences = PreferenceManager.getDefaultSharedPreferences(context)
        var isUSD = preferences.getBoolean("isUSD", true)

        adapter = PropertyAdaptater(listOf(), idProperty, isUSD)
        recycler_view_property.adapter = adapter

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)



        displayAllProperties()



        listener()

        cancel_search_button.setOnClickListener {
            displayAllProperties()
            cancel_search_button.visibility = View.GONE
        }

    }

    //click
    fun listener() {
        adapter.listener = { id ->


            this.idProperty = id

            val isTablet: Boolean = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {
                propertyDetail = PropertyDetailFragment()
                propertyList = PropertyListFragment()
                activity?.supportFragmentManager?.inTransaction {
                    replace(R.id.frame_layout_property_detail, propertyDetail, id)

                }

            } else {

                if (lastId == id) {
                    val intent = Intent(context, PropertyDetailActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                }
                lastId = id
            }


        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }


    fun displayAllProperties() {
        propertyViewModel.allProperties.observe(this, Observer { property ->
            property?.let {
                adapter.setProperties(property)
            }
        })
    }

    fun searchProperties(
        typeProperty: String,
        minArea: Int,
        maxArea: Int,
        minPrice: Int,
        maxPrice: Int,
        minDateOfSale: Long,
        maxDateOfSale: Long,
        statut: String,
        minDateOfCreated: Long,
        maxDateOfCreated: Long,
        city: String,
        minRoom: Int,
        maxRoom: Int
    ) {
        val propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)
        propertyViewModel.getPropertyResearch(
            typeProperty,
            minArea,
            maxArea,
            minPrice,
            maxPrice,
            minDateOfSale,
            maxDateOfSale,
            statut,
            minDateOfCreated,
            maxDateOfCreated,
            city,
            minRoom,
            maxRoom
        ).observe(this, Observer { property ->
            property?.let {

                cancel_search_button.visibility = View.VISIBLE
                adapter.setProperties(property)
            }
        })
    }
}
