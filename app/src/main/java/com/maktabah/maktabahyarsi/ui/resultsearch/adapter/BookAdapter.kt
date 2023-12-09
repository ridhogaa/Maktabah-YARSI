package com.maktabah.maktabahyarsi.ui.resultsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBook
import com.maktabah.maktabahyarsi.databinding.ItemBukuGridBinding
import com.maktabah.maktabahyarsi.utils.highlightText
import com.maktabah.maktabahyarsi.utils.loadImage


class BookAdapter(
    private val itemClick: (DataItemBook) -> Unit,
    private val query: String = ""
) : RecyclerView.Adapter<BookAdapter.GridViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<DataItemBook>() {
            override fun areItemsTheSame(
                oldItem: DataItemBook,
                newItem: DataItemBook,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItemBook,
                newItem: DataItemBook,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GridViewHolder =
        GridViewHolder(
            ItemBukuGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class GridViewHolder(private val binding: ItemBukuGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: DataItemBook) {
            with(binding) {
                tvJudulBuku.text = highlightText(query.lowercase(), book.title, tvJudulBuku.context)
                tvDescBuku.text = book.description
                tvJumlahHalaman.text =
                    itemView.context.getString(R.string.halaman, book.page.toString())
                coverBuku.loadImage(itemView.context, book.imageUrl)
                root.setOnClickListener {
                    itemClick(book)
                }
            }
        }
    }

    fun setData(data: List<DataItemBook>) {
        differ.submitList(data)
    }
}