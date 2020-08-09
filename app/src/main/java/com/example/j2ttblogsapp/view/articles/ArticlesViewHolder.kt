package com.example.j2ttblogsapp.view.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.j2ttblogsapp.R
import com.example.j2ttblogsapp.data.Article
import com.example.j2ttblogsapp.utils.convertTime
import com.example.j2ttblogsapp.utils.getCompactedNumber
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_articles_list.view.*

class ArticlesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(article: Article?, context: Context) {
        if (article != null) {
            itemView.tv_article_content.text = article.content
            itemView.tv_blog_time.text = article.createdAt.convertTime()
            if (article.user.isNotEmpty()) {
                itemView.tv_user_name.text = article.user[0].name + " " + article.user[0].lastname
                itemView.tv_user_designation.text = article.user[0].designation
            }
            if (article.media.isNotEmpty()) {
                itemView.tv_article_title.text = article.media[0].title
                itemView.tv_article_url.text = article.media[0].url
            }
            itemView.tv_likes.text = article.likes.getCompactedNumber() + context.getString(R.string.likes)
            itemView.tv_comments.text = article.comments.getCompactedNumber() + context.getString(R.string.comments)
            if (article.media.isNotEmpty() && article.media[0].image.isNotEmpty()) {
                Picasso.get().load(article.media[0].image)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(itemView.iv_article)
            }
            if (article.user.isNotEmpty() && article.user[0].avatar.isNotEmpty()) {
                Picasso.get().load(article.user[0].avatar)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .error(R.mipmap.ic_launcher)
                    .into(itemView.iv_user)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ArticlesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_articles_list, parent, false)
            return ArticlesViewHolder(view)
        }
    }
}