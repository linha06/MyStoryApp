package com.linha.mystoryapp.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.linha.mystoryapp.data.api.ApiConfig
import com.linha.mystoryapp.data.api.response.GetStoryResponse
import com.linha.mystoryapp.data.api.response.ListStoryItem
import com.linha.mystoryapp.data.story.StoryRepository
import com.linha.mystoryapp.data.user.UserRepository
import com.linha.mystoryapp.data.user.pref.UserModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository, private val sRepository: StoryRepository): ViewModel() {
    private val _items = MutableLiveData<List<ListStoryItem?>?>()
    val items: LiveData<List<ListStoryItem?>?> = _items

    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> =
        sRepository.getStory(token).cachedIn(viewModelScope)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStory(token : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStories(token)
        client.enqueue(object : Callback<GetStoryResponse> {
            override fun onResponse(
                call: Call<GetStoryResponse>,
                response: Response<GetStoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _items.value = response.body()?.listStory
                    }
                } else {
                    Log.e(TAG, "onFailure: getStory  fail1")
                }
            }
            override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: getStory fail 2")
            }
        })
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    companion object{
        private const val TAG = "MainViewModel"
    }

}