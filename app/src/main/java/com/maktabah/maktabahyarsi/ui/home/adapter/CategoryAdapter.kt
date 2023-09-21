package com.maktabah.maktabahyarsi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.databinding.ItemCategoryBinding


class CategoryAdapter(
    private val category: ArrayList<String>,
) : RecyclerView.Adapter<CategoryAdapter.GridViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryAdapter.GridViewHolder =
        GridViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CategoryAdapter.GridViewHolder, position: Int) =
        holder.bind(category[position])

    override fun getItemCount(): Int = category.size

    inner class GridViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.tvCategory.text = category
        }
    }

}