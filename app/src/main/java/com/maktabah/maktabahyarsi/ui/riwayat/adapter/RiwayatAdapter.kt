package com.maktabah.maktabahyarsi.ui.riwayat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity
import com.maktabah.maktabahyarsi.databinding.ItemBukuLinearVerticalBinding
import com.maktabah.maktabahyarsi.utils.loadImage

class RiwayatAdapter(
    private val itemClick: (HistoryBookEntity) -> Unit
) : RecyclerView.Adapter<RiwayatAdapter.LinearViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<HistoryBookEntity>() {
            override fun areItemsTheSame(
                oldItem: HistoryBookEntity,
                newItem: HistoryBookEntity,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HistoryBookEntity,
                newItem: HistoryBookEntity,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LinearViewHolder =
        LinearViewHolder(
            ItemBukuLinearVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LinearViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class LinearViewHolder(
        private val binding: ItemBukuLinearVerticalBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: HistoryBookEntity) {
            with(binding) {
                tvJudulBuku.text = book.title
                tvDescBuku.text = book.desc
                tvJumlahHalaman.text =
                    itemView.context.getString(R.string.halaman, book.page.toString())
                tvPenulisBuku.text = book.creator
                coverBuku.loadImage(itemView.context, book.imageUrl)
                root.setOnClickListener {
                    itemClick(book)
                }
            }
        }
    }

    fun setData(data: List<HistoryBookEntity>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}
