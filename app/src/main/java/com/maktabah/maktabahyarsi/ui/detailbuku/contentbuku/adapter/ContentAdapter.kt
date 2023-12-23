package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse
import com.maktabah.maktabahyarsi.databinding.ItemContentBinding

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class ContentAdapter :
    PagingDataAdapter<GetContentResponse.Data, ContentAdapter.ListViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<GetContentResponse.Data>() {
            override fun areItemsTheSame(
                oldItem: GetContentResponse.Data, newItem: GetContentResponse.Data
            ): Boolean = oldItem._id == newItem._id

            override fun areContentsTheSame(
                oldItem: GetContentResponse.Data, newItem: GetContentResponse.Data
            ): Boolean = oldItem._id == newItem._id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder = ListViewHolder(
        ItemContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val itemContentAdapter: ItemContentAdapter by lazy {
            ItemContentAdapter()
        }

        fun bind(item: GetContentResponse.Data) {
            binding.run {
                tvBab.text = item.heading
                item.sub?.let { sub -> setChildAdapter(sub) }
            }
        }

        private fun setChildAdapter(list: List<GetContentResponse.Data.Sub>) {
            binding.run {
                rvContent.apply {
                    adapter = itemContentAdapter
                    layoutManager = LinearLayoutManager(itemView.context)
                }
                itemContentAdapter.setData(list)
            }
        }
    }
}
