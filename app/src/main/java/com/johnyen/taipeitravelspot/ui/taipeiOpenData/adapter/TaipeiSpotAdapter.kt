package com.johnyen.taipeitravelspot.ui.taipeiOpenData.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnyen.taipeitravelspot.api.portal.response.model.Data
import com.cbes.ezreturn.utils.loadInternalImage
import com.johnyen.taipeitravelspot.R


class TaipeiSpotAdapter (
    private var tapeiSpotData: List<Data>,
    private val callback: Callback
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaipeiSpotViewHolder(inflater.inflate(R.layout.item_taipei_spot, parent, false),callback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaipeiSpotViewHolder -> {
                holder.bind(
                    tapeiSpotData[position]
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return tapeiSpotData.size
    }

    class TaipeiSpotViewHolder(
        val view: View,
        val callback: Callback
    ) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val contentText: TextView = view.findViewById(R.id.content_text)
        private val images: ImageView = view.findViewById(R.id.imageView)
        private val cell: RelativeLayout = view.findViewById(R.id.cell)
        fun bind(data: Data) {
            title.text = data.name
            contentText.text = data.introduction
            if(data.images?.isNotEmpty() == true){
                images.loadInternalImage(data.images[0].src)
            }
            cell.setOnClickListener { callback.onItemSelect(data) }
        }
    }
    interface Callback {
        fun onItemSelect(data: Data)
    }
}
