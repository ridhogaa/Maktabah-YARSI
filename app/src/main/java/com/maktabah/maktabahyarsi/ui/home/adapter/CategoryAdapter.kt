package com.maktabah.maktabahyarsi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.category.DataItemCategory
import com.maktabah.maktabahyarsi.databinding.ItemCategoryBinding


class CategoryAdapter(
    private val itemClick: (DataItemCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.GridViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<DataItemCategory>() {
            override fun areItemsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GridViewHolder =
        GridViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class GridViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: DataItemCategory) {
            with(binding) {
                tvNameCategory.text = category.name
//                tvJumlahBuku.text =
//                    itemView.context.getString(
//                        R.string.category_buku_format,
//                        category.subcategories.size.toString()
//                    )
                root.setOnClickListener {
                    itemClick(category)
                }
            }
        }
    }

    fun setData(data: List<DataItemCategory>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}