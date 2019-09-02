package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.julien.realestatemanager.R
import com.julien.realestatemanager.models.Property
import com.julien.realestatemanager.models.PropertyViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_property_list_item.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.coroutines.coroutineContext

class PropertyViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    val propertyType = itemView.findViewById<TextView>(R.id.property_type_text_view)
    val propertyCity = itemView.findViewById<TextView>(R.id.property_city_text_view)
    val propertyPrice = itemView.findViewById<TextView>(R.id.price_text_view)
    val propertyPhoto = itemView.findViewById<ImageView>(R.id.fragment_property_list_photo)


    fun update(property: Property, context: Context){
        propertyType.text = property.type
        propertyCity.text = property.city
        propertyPrice.text = property.price






            //val bitmap: Bitmap = BitmapFactory.decodeByteArray(convertStringToByte(property.photo,context), 0, convertStringToByte(property.photo,context)!!.size)
            //photo.text = property.photo

        //var file:File = File(Uri.parse(property.photo).path)
        val file = File(property.photo)
        Picasso.get().load(file).into(propertyPhoto)

        /*
            Glide.with(context) //SHOWING PREVIEW OF IMAGE
                .load(File(Uri.parse(property.photo).path))
                .apply(RequestOptions.centerCropTransform())
                .into(propertyPhoto)
*/

    }

    fun convertStringToByte(photo: String?, context: Context): ByteArray? {

        var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, Uri.parse(photo))

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()

    }


}