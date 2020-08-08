package com.example.j2ttblogsapp.data

data class Article(
    val content: String,
    val comments: Int,
    val likes: Int,
    val media: List<Media>,
    val user: List<User>
)

data class Media(
    val image: String,
    val title: String,
    val url: String
)

data class User(
    val name: String,
    val lastname: String,
    val designation: String,
    val avatar: String
)