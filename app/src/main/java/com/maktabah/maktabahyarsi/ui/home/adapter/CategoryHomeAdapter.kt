package com.maktabah.maktabahyarsi.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.category.DataItemCategory
import com.maktabah.maktabahyarsi.databinding.ItemCategoryBinding


class CategoryHomeAdapter(
    private val itemClick: (DataItemCategory) -> Unit,
    private val itemClickElse: () -> Unit,
) : RecyclerView.Adapter<CategoryHomeAdapter.GridViewHolder>() {

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
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryHomeAdapter.GridViewHolder =
        GridViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CategoryHomeAdapter.GridViewHolder, position: Int) =
        if (position == 7) {
            holder.bindCategoryEqualsSeven()
        } else {
            holder.bind(differ.currentList[position])
        }

    override fun getItemCount(): Int = 8

    inner class GridViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: DataItemCategory) {
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

        fun bindCategoryEqualsSeven() {
            with(binding) {
                tvNameCategory.text = itemView.context.getString(R.string.lainnya_category)
                tvJumlahBuku.text = itemView.context.getString(R.string.kategori)
                root.setOnClickListener {
                    itemClickElse()
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