package com.maktabah.maktabahyarsi.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import com.maktabah.maktabahyarsi.data.network.api.model.book.DataItemBook
import com.maktabah.maktabahyarsi.databinding.ItemBukuGridBinding


class FavoriteAdapter(
    private val itemClick: (FavoriteBookEntity) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.GridViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<FavoriteBookEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteBookEntity,
                newItem: FavoriteBookEntity,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FavoriteBookEntity,
                newItem: FavoriteBookEntity,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

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

    inner class GridViewHolder(
        private val binding: ItemBukuGridBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: FavoriteBookEntity) {
            with(binding) {
                tvJudulBuku.text = book.title
                tvDescBuku.text = book.desc
                tvJumlahHalaman.text =
                    itemView.context.getString(R.string.halaman, book.page.toString())
                coverBuku.load(book.imageUrl)
                root.setOnClickListener {
                    itemClick(book)
                }
            }
        }
    }

    fun setData(data: List<FavoriteBookEntity>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}
