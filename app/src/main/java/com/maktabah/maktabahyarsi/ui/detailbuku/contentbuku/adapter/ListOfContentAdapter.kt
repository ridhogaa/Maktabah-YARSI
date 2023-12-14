package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBook
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemListContent
import com.maktabah.maktabahyarsi.data.network.api.model.book.SubItem
import com.maktabah.maktabahyarsi.databinding.ItemBukuGridBinding
import com.maktabah.maktabahyarsi.databinding.ItemDaftarIsiBinding
import com.maktabah.maktabahyarsi.utils.highlightText
import com.maktabah.maktabahyarsi.utils.loadImage


class ListOfContentAdapter(
    private val itemClick: () -> Unit,
    private val sub: List<SubItem>? = null
) : RecyclerView.Adapter<ListOfContentAdapter.ListOfContentViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<DataItemListContent>() {
            override fun areItemsTheSame(
                oldItem: DataItemListContent,
                newItem: DataItemListContent,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemListContent,
                newItem: DataItemListContent,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListOfContentViewHolder =
        ListOfContentViewHolder(
            ItemDaftarIsiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListOfContentViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListOfContentViewHolder(private val binding: ItemDaftarIsiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: DataItemListContent) {
            with(binding) {
                tvTitle.text = book.name
                root.setOnClickListener {
                    itemClick()
                }
                bindSub(book.sub)
            }
        }

        private fun bindSub(sub: List<SubItem>){
            if (sub.isNotEmpty()) {
                val subAdapter = ListOfContentAdapter({}, sub)
                binding.recyclerViewSub.adapter = subAdapter
                binding.recyclerViewSub.layoutManager = LinearLayoutManager(itemView.context)
            } else {
                // Sembunyikan RecyclerView jika tidak ada sub-nodes
                binding.recyclerViewSub.visibility = View.GONE
            }
        }
    }

    fun setData(data: List<DataItemListContent>) {
        differ.submitList(data)
    }
}