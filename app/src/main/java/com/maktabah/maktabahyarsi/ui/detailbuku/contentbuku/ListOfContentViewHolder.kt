package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import com.amrdeveloper.treeview.TreeNode
import com.amrdeveloper.treeview.TreeViewHolder
import com.maktabah.maktabahyarsi.R
import com.maktabah.maktabahyarsi.databinding.ItemDaftarIsiBinding


class FileViewHolder(private val binding: ItemDaftarIsiBinding) : TreeViewHolder(binding.root) {
    override fun bindTreeNode(node: TreeNode) {
        super.bindTreeNode(node)
        with(binding) {
            tvTitle.text = node.value.toString()
            val stateIcon: Int =
                if (node.isExpanded) R.drawable.chevron_down_24 else R.drawable.chevron_right
            ivDown.setImageResource(stateIcon)
        }
    }
}