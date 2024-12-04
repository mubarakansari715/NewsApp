package com.example.newsapp.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.newsapp.features.model.Article
import com.example.newsapp.features.model.ArticleDbModel

/***
 * 1. create/modified a model of database
 * 2. Create a dao class
 * 3. create a abstract class AppDatabase
 * 4. create a database module
 *      1) create a database provide
 *      2) create a fun for dao assign
 */


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleDbModel)

    @Query("SELECT * FROM articles")
    suspend fun getAllHeadlineData(): List<ArticleDbModel>
}

@Database(entities = [ArticleDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}