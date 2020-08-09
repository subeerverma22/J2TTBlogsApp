package com.example.j2ttblogsapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * Data source factory class to get the ArticlesDataSource object
 */
class ArticlesDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val apiService: ApiService
) : DataSource.Factory<Int, Article>() {

    val articlesDataSourceLiveData = MutableLiveData<ArticlesDataSource>()

    override fun create(): DataSource<Int, Article> {
        val articlesDataSource = ArticlesDataSource(apiService, compositeDisposable)
        articlesDataSourceLiveData.postValue(articlesDataSource)
        return articlesDataSource
    }
}