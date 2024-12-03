package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.room.AppDatabase
import com.example.newsapp.room.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "news_app_db").build()
    }

    @Provides
    @Singleton
    fun provideNewsHeadlineDao(database: AppDatabase): ArticleDao = database.articleDao()
}