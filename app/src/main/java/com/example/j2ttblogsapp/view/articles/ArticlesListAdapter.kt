package com.example.j2ttblogsapp.view.articles

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.j2ttblogsapp.data.Article
import com.example.j2ttblogsapp.data.Status

class ArticlesListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<Article, RecyclerView.ViewHolder>(articlesDiffCallback) {

    private val VIEW_TYPE_ARTICLE = 1
    private val VIEW_TYPE_FOOTER = 2
    private var state = Status.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ARTICLE) ArticlesViewHolder.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ARTICLE)
            (holder as ArticlesViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) VIEW_TYPE_ARTICLE else VIEW_TYPE_FOOTER
    }

    companion object {
        val articlesDiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == Status.LOADING || state == Status.ERROR)
    }

    fun setState(state: Status) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}