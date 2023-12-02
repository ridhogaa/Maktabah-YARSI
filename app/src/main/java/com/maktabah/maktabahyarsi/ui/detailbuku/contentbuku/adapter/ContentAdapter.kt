package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.data.network.api.model.book.SubItem
import com.maktabah.maktabahyarsi.databinding.ItemDaftarIsiBinding

class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private var data: List<SubItem> = emptyList()
    private val expandedItems = HashSet<Int>()

    fun setData(newData: List<SubItem>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDaftarIsiBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDaftarIsiBinding.bind(itemView)

        init {
            binding.ivDown.setOnClickListener {
                toggleItemExpansion(adapterPosition)
            }
        }

        fun bind(item: SubItem) {
            binding.tvTitle.text = item.name

            if (item.sub?.isNotEmpty() == true) {
                binding.ivDown.visibility = View.VISIBLE

                val iconResource = if (expandedItems.contains(adapterPosition)) {
                    R.drawable.baseline_arrow_drop_up_24
                } else {
                    R.drawable.chevron_down_24
                }
                binding.ivDown.setImageResource(iconResource)

                val nestedAdapter = ContentAdapter()
                nestedAdapter.setData(item.sub)

                binding.rvSubItems.layoutManager = LinearLayoutManager(binding.root.context)
                binding.rvSubItems.adapter = nestedAdapter

                binding.rvSubItems.visibility =
                    if (expandedItems.contains(adapterPosition)) View.VISIBLE else View.GONE

                nestedAdapter.setOnItemExpansionListener(object : OnItemExpansionListener {
                    override fun onItemExpanded(position: Int) {
                        notifyItemChanged(adapterPosition)
                    }

                    override fun onItemCollapsed(position: Int) {
                        notifyItemChanged(adapterPosition)
                    }
                })
            } else {
                binding.ivDown.visibility = View.GONE
                binding.rvSubItems.visibility = View.GONE
            }
        }

        private fun toggleItemExpansion(position: Int) {
            if (expandedItems.contains(position)) {
                expandedItems.remove(position)
            } else {
                expandedItems.add(position)
            }
            notifyItemChanged(position)
        }
    }

    interface OnItemExpansionListener {
        fun onItemExpanded(position: Int)
        fun onItemCollapsed(position: Int)
    }

    private var itemExpansionListener: OnItemExpansionListener? = null

    fun setOnItemExpansionListener(listener: OnItemExpansionListener) {
        itemExpansionListener = listener
    }
}
