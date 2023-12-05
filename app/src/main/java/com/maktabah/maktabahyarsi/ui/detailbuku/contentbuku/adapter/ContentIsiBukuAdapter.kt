    package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.adapter

    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.maktabah.maktabahyarsi.R
    import com.maktabah.maktabahyarsi.data.network.api.model.book.ContentData

    class ContentIsiBukuAdapter(var contentList: List<ContentData>) :
        RecyclerView.Adapter<ContentIsiBukuAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_content_buku, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val contentData = contentList[position]
            Log.d("ContentIsiBukuAdapter", "Binding data at position $position: $contentData")

            holder.headingTextView.text = contentData.heading
            holder.textTextView.text = contentData.text
            holder.pageTextView.text = contentData.page.toString()
        }

        override fun getItemCount(): Int {
            return contentList.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val headingTextView: TextView = itemView.findViewById(R.id.tv_bab)
            val textTextView: TextView = itemView.findViewById(R.id.tv_isi)
            val pageTextView: TextView = itemView.findViewById(R.id.tv_halaman)
        }

        fun updateData(newData: List<ContentData>) {
            contentList = newData
            notifyDataSetChanged()
        }


    }
