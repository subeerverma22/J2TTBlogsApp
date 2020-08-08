package com.example.j2ttblogsapp.view.articles

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.j2ttblogsapp.R
import com.example.j2ttblogsapp.data.Status
import kotlinx.android.synthetic.main.item_list_footer.view.*

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: Status?) {
        itemView.progress_bar.visibility = if (status == Status.LOADING) VISIBLE else INVISIBLE
        itemView.tv_error.visibility = if (status == Status.ERROR) VISIBLE else INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.tv_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}