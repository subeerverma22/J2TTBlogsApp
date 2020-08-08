package com.example.j2ttblogsapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class ArticlesDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    var status: MutableLiveData<Status> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        updateStatus(Status.LOADING)
        compositeDisposable.add(
            apiService.getArticles(1, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateStatus(Status.SUCCESS)
                        callback.onResult(
                            response.articleList,
                            null,
                            2
                        )
                    },
                    {
                        updateStatus(Status.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        updateStatus(Status.LOADING)
        compositeDisposable.add(
            apiService.getArticles(params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateStatus(Status.SUCCESS)
                        callback.onResult(
                            response.articleList,
                            params.key + 1
                        )
                    },
                    {
                        updateStatus(Status.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        TODO("Not yet implemented")
    }

    private fun updateStatus(state: Status) {
        this.status.postValue(state)
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }
}