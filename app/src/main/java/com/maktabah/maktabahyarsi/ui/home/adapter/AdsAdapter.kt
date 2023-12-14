package com.maktabah.maktabahyarsi.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R

class AdsAdapter (private var dataSlider: List<Int>): RecyclerView.Adapter<AdsAdapter.ViewHolder>()  {
    class ViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
        var img = view.findViewById<ImageView>(R.id.carousel_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(dataSlider[position])
    }

    override fun getItemCount(): Int {
        return dataSlider.size
    }

}