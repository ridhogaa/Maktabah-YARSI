package com.maktabah.maktabahyarsi.ui.home.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.category.DataItemSubCategory
import com.maktabah.maktabahyarsi.databinding.ItemCategoryBinding


class SubCategoryAdapter(
    private val itemClick: (DataItemSubCategory) -> Unit
) : RecyclerView.Adapter<SubCategoryAdapter.GridViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<DataItemSubCategory>() {
            override fun areItemsTheSame(
                oldItem: DataItemSubCategory,
                newItem: DataItemSubCategory,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemSubCategory,
                newItem: DataItemSubCategory,
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SubCategoryAdapter.GridViewHolder =
        GridViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SubCategoryAdapter.GridViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class GridViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: DataItemSubCategory) {
            with(binding) {
                tvNameCategory.text = category.name
                tvJumlahBuku.text =
                    itemView.context.getString(
                        R.string.category_buku_format,
                        category.total.toString()
                    )
                root.setOnClickListener {
                    itemClick(category)
                }
            }
        }
    }

    fun setData(data: List<DataItemSubCategory>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}