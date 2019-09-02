package com.julien.realestatemanager.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julien.realestatemanager.R
import com.julien.realestatemanager.extensions.listen
import com.julien.realestatemanager.models.Media
import com.julien.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.views.PropertyViewHolder

class MediaAdaptater(var mediaList:List<Media>) : RecyclerView.Adapter<MediaViewHolder>() {


    var listener: ((String)->Unit)? = null
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        context = parent?.context
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_property_detaill_list_item, parent, false)
        return MediaViewHolder(v).listen { pos, type ->
            //val item = items.get(pos)
            //Toast.makeText(parent.context, pos.toString(), Toast.LENGTH_SHORT).show()
            listener?.invoke(mediaList.get(pos).id)
        }

    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.update(mediaList.get(position),context)
    }

    internal fun setMedia(media: List<Media>) {
        this.mediaList = media
        notifyDataSetChanged()
    }

    override fun getItemCount() = mediaList.size


}