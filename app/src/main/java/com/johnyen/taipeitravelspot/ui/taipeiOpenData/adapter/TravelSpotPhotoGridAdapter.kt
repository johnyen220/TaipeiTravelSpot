package com.johnyen.taipeitravelspot.ui.taipeiOpenData.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cbes.ezreturn.utils.loadInternalImage
import com.johnyen.taipeitravelspot.databinding.ItemTravelSpotPhotoBinding


class TravelSpotPhotoGridAdapter(private var itemList: MutableList<String>
) : RecyclerView.Adapter<TravelSpotPhotoGridAdapter.ViewHolder>() {


    open inner class ViewHolder(val binding: ItemTravelSpotPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTravelSpotPhotoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(itemList[position]) {
                binding.photo.loadInternalImage(this)
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}