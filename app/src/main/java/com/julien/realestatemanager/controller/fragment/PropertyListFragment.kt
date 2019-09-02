package com.julien.realestatemanager.controller.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.R
import com.julien.realestatemanager.controller.activity.NewPropertyActivity
import com.julien.realestatemanager.controller.activity.PropertyDetailActivity
import com.julien.realestatemanager.models.*
import com.openclassrooms.realestatemanager.views.PropertyAdaptater
import com.openclassrooms.realestatemanager.views.PropertyViewHolder
import kotlinx.android.synthetic.main.fragment_property_list.*
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
        linearLayoutManager = LinearLayoutManager(context)
        recycler_view_property.layoutManager = linearLayoutManager
        var id:String
        if (tag != null){
            id = tag!!
        }else{
            id = "1"
        }


        adapter = PropertyAdaptater(listOf(),id)
            recycler_view_property.adapter = adapter

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        propertyViewModel.allProperties.observe(this, Observer { property ->
            // Update the cached copy of the words in the adapter.
            property?.let {
                //adapter.setWords(it)
                adapter.setProperties(property)
            }
        })


        listener()

    }

    //click
    fun listener(){
        adapter.listener = { id ->
            // do something here



            val isTablet: Boolean = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {

                propertyDetail = PropertyDetailFragment()
                propertyList = PropertyListFragment()
                activity?.supportFragmentManager?.inTransaction {
                    replace(R.id.frame_layout_property_list, propertyList, id)
                    replace(R.id.frame_layout_property_detail, propertyDetail, id)

                }

            }else{
                val intent = Intent(context, PropertyDetailActivity::class.java)
                intent.putExtra("id", id)
                // start your next activity
                startActivity(intent)
                //Toast.makeText(context, pos.toString(), Toast.LENGTH_SHORT).show()
            }


        }
    }
    inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }











}
