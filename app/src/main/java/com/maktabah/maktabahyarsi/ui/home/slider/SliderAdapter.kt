package com.maktabah.maktabahyarsi.ui.home.slider

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.maktabah.maktabahyarsi.R

class SliderAdapter (private var dataSlider: List<ModelSlider>): RecyclerView.Adapter<SliderAdapter.ViewHolder>()  {
    class ViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
        var img = view.findViewById<ImageView>(R.id.carousel_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderAdapter.ViewHolder, position: Int) {
        holder.img.setImageResource(dataSlider[position].img)
    }

    override fun getItemCount(): Int {
        return dataSlider.size
    }

}