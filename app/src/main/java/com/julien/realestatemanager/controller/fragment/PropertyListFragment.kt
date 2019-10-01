package com.julien.realestatemanager.controller.fragment


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.MainActivity
import com.julien.realestatemanager.controller.activity.PropertyDetailActivity
import com.julien.realestatemanager.models.*
import com.openclassrooms.realestatemanager.views.PropertyAdaptater
import com.openclassrooms.realestatemanager.views.PropertyViewHolder
import kotlinx.android.synthetic.main.fragment_property_list.*
import kotlinx.android.synthetic.main.fragment_property_list_item.*
import java.io.ByteArrayOutputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PropertyListFragment : androidx.fragment.app.Fragment() {


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PropertyAdaptater
    private lateinit var propertyDetail: PropertyDetailFragment
    private lateinit var propertyList: PropertyListFragment

    private lateinit var propertyViewModel: PropertyViewModel

    private lateinit var lastId:String


    lateinit var idProperty: String

    //private lateinit var photo:ByteArray

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //recycler_view_property?.layoutManager = LinearLayoutManager(context)


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
        var isUSD = preferences.getBoolean("isUSD",true)

        adapter = PropertyAdaptater(listOf(), idProperty,isUSD)
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
            // do something here


            this.idProperty = id

            val isTablet: Boolean = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {
                propertyDetail = PropertyDetailFragment()
                propertyList = PropertyListFragment()
                activity?.supportFragmentManager?.inTransaction {
                    //replace(R.id.frame_layout_property_list, propertyList, id)
                    replace(R.id.frame_layout_property_detail, propertyDetail, id)

                }

            } else {

                if (lastId == id){
                    val intent = Intent(context, PropertyDetailActivity::class.java)
                    intent.putExtra("id", id)
                    // start your next activity
                    startActivity(intent)
                    //Toast.makeText(context, pos.toString(), Toast.LENGTH_SHORT).show()
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
            // Update the cached copy of the words in the adapter.
            property?.let {
                //adapter.setWords(it)
                adapter.setProperties(property)
            }
        })
    }

    fun searchProperties(typeProperty: String,
                         minArea: Int,
                         maxArea: Int,
                         minPrice: Int,
                         maxPrice: Int,
                         minDateOfSale: Long,
                         maxDateOfSale: Long,
                         statut: String,
                         minDateOfCreated: Long,
                         maxDateOfCreated: Long,
                         city:String,
                         minRoom: Int,
                         maxRoom: Int) {
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
            // Update the cached copy of the words in the adapter.
            property?.let {

                //adapter.setWords(it)
                cancel_search_button.visibility =View.VISIBLE
                adapter.setProperties(property)
            }
        })
    }
}
