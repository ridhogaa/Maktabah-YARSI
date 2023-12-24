package com.maktabah.maktabahyarsi.ui.resultsearch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchContentResponse
import com.maktabah.maktabahyarsi.databinding.ItemKataBinding
import com.maktabah.maktabahyarsi.utils.highlightText


class WordAdapter(
    private val itemClick: (SearchContentResponse.Data, String) -> Unit,
    private val query: String
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<SearchContentResponse.Data>() {
            override fun areItemsTheSame(
                oldItem: SearchContentResponse.Data,
                newItem: SearchContentResponse.Data,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SearchContentResponse.Data,
                newItem: SearchContentResponse.Data,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WordViewHolder =
        WordViewHolder(
            ItemKataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class WordViewHolder(private val binding: ItemKataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchContentResponse.Data) {
            with(binding) {
                data.source.let {
                    tvJudulBuku.text = it.heading
                    tvJumlahHalaman.text =
                        itemView.context.getString(R.string.halaman_kata, it.page.toString())
                    tvKataBuku.text = highlightText(query.lowercase(), it.text, tvKataBuku.context)
                }
                root.setOnClickListener {
                    itemClick(data, query.lowercase())
                }
            }
        }
    }

    fun setData(data: List<SearchContentResponse.Data>) {
        differ.submitList(data)
    }

}