package com.example.j2ttblogsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.j2ttblogsapp.data.*
import io.reactivex.disposables.CompositeDisposable

class ArticleListViewModel : ViewModel() {

    private val apiService = ApiService.getService()
    var articleList: LiveData<PagedList<Article>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 10
    private val articlesDataSourceFactory: ArticlesDataSourceFactory

    init {
        articlesDataSourceFactory = ArticlesDataSourceFactory(compositeDisposable, apiService)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        articleList = LivePagedListBuilder(articlesDataSourceFactory, pagedListConfig).build()
    }

    fun getStatus(): LiveData<Status> = Transformations.switchMap(
        articlesDataSourceFactory.articlesDataSourceLiveData,
        ArticlesDataSource::status
    )

    fun retry() {
        articlesDataSourceFactory.articlesDataSourceLiveData.value?.retry()
    }

    fun isListEmpty(): Boolean {
        return articleList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}