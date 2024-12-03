package com.example.newsapp.features.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

data class Article(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val source: Source? = null,
)

@Entity(tableName = "articles")
data class ArticleDbModel(
    var id: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @PrimaryKey val title: String,
    val url: String,
    val urlToImage: String,
)

fun ArticleDbModel.toArticleLocalToArticle(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun Article.toArticleToArticleLocal(): ArticleDbModel {
    return ArticleDbModel(
        id = UUID.randomUUID().toString(),
        author = this.author.orEmpty(),
        content = this.content.orEmpty(),
        description = this.description.orEmpty(),
        publishedAt = this.publishedAt.orEmpty(),
        title = this.title.orEmpty(),
        url = this.url.orEmpty(),
        urlToImage = this.urlToImage.orEmpty()
    )
}