package com.linha.mystoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linha.mystoryapp.data.story.StoryRepository
import com.linha.mystoryapp.data.user.UserRepository
import com.linha.mystoryapp.data.user.di.Injection
import com.linha.mystoryapp.data.user.di.StoryInjection
import com.linha.mystoryapp.view.detailStory.DetailViewModel
import com.linha.mystoryapp.view.login.LoginViewModel
import com.linha.mystoryapp.view.main.MainViewModel
import com.linha.mystoryapp.view.maps.MapsViewModel

class ViewModelFactory(private val repository: UserRepository, private val Srepository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository, Srepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), StoryInjection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}