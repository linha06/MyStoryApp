package com.linha.mystoryapp.data.user.di

import android.content.Context
import com.linha.mystoryapp.data.user.UserRepository
import com.linha.mystoryapp.data.user.pref.UserPreference
import com.linha.mystoryapp.data.user.pref.dataStore
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}