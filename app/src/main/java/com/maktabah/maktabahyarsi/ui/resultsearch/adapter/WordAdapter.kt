package com.maktabah.maktabahyarsi.ui.resultsearch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.Source
import com.maktabah.maktabahyarsi.databinding.ItemKataBinding
import com.maktabah.maktabahyarsi.utils.highlightText


class WordAdapter(
    private val itemClick: (Source, String) -> Unit, private val query: String
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Source>() {
        override fun areItemsTheSame(
            oldItem: Source,
            newItem: Source,
        ): Boolean {
            return oldItem.idContent == newItem.idContent
        }

        override fun areContentsTheSame(
            oldItem: Source,
            newItem: Source,
        ): Boolean {
            return oldItem.idContent == newItem.idContent
        }
    })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WordViewHolder = WordViewHolder(
        ItemKataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class WordViewHolder(private val binding: ItemKataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Source) {
            with(binding) {
                tvJudulBuku.text = data.heading
                tvJumlahHalaman.text =
                    itemView.context.getString(R.string.halaman_kata, data.page.toString())
                data.text.let {
                    tvKataBuku.text = highlightText(
                        query.lowercase(), it.substring(
                            Regex("(\\w*\\s)${Regex.escape(query)}").find(it)?.range?.first
                                ?: it.indexOf(query, ignoreCase = true)
                        ).split(" ").take(20).joinToString(" ") { it.trim() }, tvKataBuku.context
                    )
                }
                root.setOnClickListener {
                    itemClick(data, query.lowercase())
                }
            }
        }
    }

    fun setData(data: List<Source>) {
        differ.submitList(data)
    }

}