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
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyDao
import com.julien.realestatemanager.models.PropertyViewModel
import com.julien.realestatemanager.models.RealEstateManagerDatabase
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


    private lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager
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
        linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recycler_view_property.layoutManager = linearLayoutManager

        adapter = PropertyAdaptater(listOf())
            recycler_view_property.adapter = adapter

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)

        propertyViewModel.allProperties.observe(this, Observer { property ->
            // Update the cached copy of the words in the adapter.
            property?.let {
                //adapter.setWords(it)
                adapter.setProperties(property)
            }
        })


        test_add_button.setOnClickListener {
            val intent = Intent(context, NewPropertyActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }


        listener()

    }

    //click
    fun listener(){
        adapter.listener = { uid ->
            // do something here

            val isTablet: Boolean = resources.getBoolean(R.bool.isTablet)
            if (isTablet) {

                propertyDetail = PropertyDetailFragment()
                propertyList = PropertyListFragment()
                activity?.supportFragmentManager?.inTransaction {
                    replace(R.id.frame_layout_property_list, propertyList)
                    replace(R.id.frame_layout_property_detail, propertyDetail, uid.toString())
                }

            }else{
                val intent = Intent(context, PropertyDetailActivity::class.java)
                intent.putExtra("uid", uid)
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

    companion object {
        const val newWordActivityRequestCode = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                //val property = Property(it.getStringExtra(NewPropertyActivity.EXTRA_REPLY))
                //Log.e("test2", it.getStringArrayListExtra("photo")[1])
                //var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, Uri.parse(it.getStringExtra("photo")))
                //insertImg(bitmap)
                //var list = convertArrayListStringToByteArray(it.getStringArrayListExtra("photo"))
                var property = Property(0,it.getStringExtra("city"),it.getStringExtra("type"),it.getStringExtra("price"),it.getStringExtra("area"),it.getStringExtra("numberOfRooms"),it.getStringExtra("description"),it.getStringExtra("adress"),it.getStringExtra("placeNearby"),it.getStringExtra("status"),it.getStringExtra("createdDate"),it.getStringExtra("dateOfSale"),it.getStringExtra("realEstateAgent"),convertArrayStringToByteArray(it.getStringExtra("photo")))
                propertyViewModel.insert(property)
            }
        } else {
            Toast.makeText(
                context,
               "erreur",
                Toast.LENGTH_LONG).show()
        }
    }

    fun convertArrayStringToByteArray(photo: String): ByteArray? {

        var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, Uri.parse(photo))

           return getBitmapAsByteArray(bitmap)

    }

/*
    fun convertArrayListStringToByteArray(list: ArrayList<String>): Array<ByteArray?> {
        var listPhoto = arrayOfNulls<ByteArray>(7)

        for(i in list.indices){

            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, Uri.parse(list[i]))
            //Log.e("ok ?",getBitmapAsByteArray(bitmap).toString())
            listPhoto[i]=getBitmapAsByteArray(bitmap)
            //listPhoto.add(getBitmapAsByteArray(bitmap))
        }

        return listPhoto


    }
*/
    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


}
