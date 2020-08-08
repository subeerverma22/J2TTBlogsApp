package com.example.j2ttblogsapp.view.articles

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.j2ttblogsapp.R
import com.example.j2ttblogsapp.data.Status
import com.example.j2ttblogsapp.viewmodel.ArticleListViewModel
import kotlinx.android.synthetic.main.activity_article_list.*

class ArticleListActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleListViewModel
    private lateinit var articlesListAdapter: ArticlesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        viewModel = ViewModelProvider(this).get((ArticleListViewModel::class.java))
        initAdapter()
        updateStatus()
    }

    private fun updateStatus() {
        tv_error.setOnClickListener { viewModel.retry() }
        viewModel.getStatus().observe(this, Observer { status ->
            progress_bar.visibility =
                if (viewModel.isListEmpty() && status == Status.LOADING) View.VISIBLE else View.GONE
            tv_error.visibility =
                if (viewModel.isListEmpty() && status == Status.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.isListEmpty()) {
                articlesListAdapter.setState(status ?: Status.SUCCESS)
            }
        })
    }

    private fun initAdapter() {
        articlesListAdapter = ArticlesListAdapter { viewModel.retry() }
        recycler_view.adapter = articlesListAdapter
        viewModel.articleList.observe(this,
            Observer {
                articlesListAdapter.submitList(it)
            })
    }
}
