package com.example.j2ttblogsapp.data

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("blogs")
    fun getArticles(
        @Query("page") page: Int,
        @Query("limit") pageSize: Int
    ): Single<List<Article>>

    companion object {
        fun getService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}