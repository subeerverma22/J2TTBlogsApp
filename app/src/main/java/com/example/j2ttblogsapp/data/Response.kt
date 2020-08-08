package com.example.j2ttblogsapp.data

data class Article(val content: String)

data class Response(
    val articleList: List<Article>
)