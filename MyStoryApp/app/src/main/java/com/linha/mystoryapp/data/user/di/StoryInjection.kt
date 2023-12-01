package com.linha.mystoryapp.data.user.di

import android.content.Context
import com.linha.mystoryapp.data.api.ApiConfig
import com.linha.mystoryapp.data.story.StoryDatabase
import com.linha.mystoryapp.data.story.StoryRepository

object StoryInjection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}