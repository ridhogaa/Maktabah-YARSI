package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse.Data.Sub
import com.maktabah.maktabahyarsi.databinding.ItemContentBukuBinding
import com.maktabah.maktabahyarsi.utils.highlightText


class ItemContentAdapter(private val text: String) :
    RecyclerView.Adapter<ItemContentAdapter.LinearViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<Sub>() {
            override fun areItemsTheSame(
                oldItem: Sub,
                newItem: Sub,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Sub,
                newItem: Sub,
            ): Boolean {
                return oldItem == newItem
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemContentAdapter.LinearViewHolder =
        LinearViewHolder(
            ItemContentBukuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemContentAdapter.LinearViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class LinearViewHolder(private val binding: ItemContentBukuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sub: Sub) {
            with(binding) {
                tvSubBab.text = sub.heading
                tvIsi.text = if (text.isNotEmpty()) highlightText(
                    text.lowercase(),
                    sub.text.toString(),
                    tvIsi.context
                ) else sub.text
            }
        }
    }

    fun setData(data: List<Sub>) {
        differ.submitList(data)
    }

    fun clearData() {
        differ.submitList(emptyList())
    }
}