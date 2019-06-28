package com.julien.realestatemanager


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.views.PropertyAdaptater
import kotlinx.android.synthetic.main.fragment_property_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PropertyListFragment : Fragment() {


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PropertyAdaptater

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

        adapter = PropertyAdaptater(listOf("pen", "cup", "dog", "spectacles"))
        recycler_view_property.adapter = adapter

        test_button.setOnClickListener {
            val intent = Intent(context,PropertyDetailActivity::class.java)
            startActivity(intent)
        }
    }



}
