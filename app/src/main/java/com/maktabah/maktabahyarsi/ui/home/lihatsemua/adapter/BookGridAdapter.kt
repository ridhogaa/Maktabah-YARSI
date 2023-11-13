package com.maktabah.maktabahyarsi.ui.home.lihatsemua.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBook
import com.maktabah.maktabahyarsi.databinding.ItemBukuGridBinding


class BookGridAdapter(

) : RecyclerView.Adapter<BookGridAdapter.GridViewHolder>() {

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
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookGridAdapter.GridViewHolder =
        GridViewHolder(
            ItemBukuGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BookGridAdapter.GridViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class GridViewHolder(private val binding: ItemBukuGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: DataItemBook) {
            with(binding) {
                tvJudulBuku.text = book.title
                tvDescBuku.text = book.description
                tvJumlahHalaman.text =
                    itemView.context.getString(R.string.halaman, book.page.toString())
                coverBuku.load(book.imageUrl)
            }
        }
    }

    fun setData(data: List<DataItemBook>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}