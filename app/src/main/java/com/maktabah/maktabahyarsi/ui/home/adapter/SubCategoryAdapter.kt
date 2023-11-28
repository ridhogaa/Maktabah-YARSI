package com.maktabah.maktabahyarsi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.category.sub.DataSubCategory
import com.maktabah.maktabahyarsi.data.network.api.model.category.sub.SubcategoriesItem
import com.maktabah.maktabahyarsi.databinding.ItemCategoryBinding


class SubCategoryAdapter(
    private val itemClick: (SubcategoriesItem) -> Unit
) : RecyclerView.Adapter<SubCategoryAdapter.GridViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<SubcategoriesItem>() {
            override fun areItemsTheSame(
                oldItem: SubcategoriesItem,
                newItem: SubcategoriesItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SubcategoriesItem,
                newItem: SubcategoriesItem,
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
        fun bind(category: SubcategoriesItem) {
            with(binding) {
                tvNameCategory.text = category.name
//                tvJumlahBuku.text =
//                    itemView.context.getString(
//                        R.string.category_buku_format,
//                        ""
//                    )
                root.setOnClickListener {
                    itemClick(category)
                }
            }
        }
    }

    fun setData(data: List<SubcategoriesItem>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}