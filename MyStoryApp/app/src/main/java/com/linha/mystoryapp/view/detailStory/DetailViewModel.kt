package com.linha.mystoryapp.view.detailStory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.linha.mystoryapp.data.api.ApiConfig
import com.linha.mystoryapp.data.api.response.ListAllStory
import com.linha.mystoryapp.data.api.response.ListStoryItem
import com.linha.mystoryapp.data.user.UserRepository
import com.linha.mystoryapp.data.user.pref.UserModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val repository: UserRepository): ViewModel() {

    private val _items = MutableLiveData<ListStoryItem>()
    val items: LiveData<ListStoryItem> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailStory(token : String, id : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailStory(token, id)
        client.enqueue(object : Callback<ListAllStory> {

            override fun onFailure(call: Call<ListAllStory>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: findDetail fail 1")
            }

            override fun onResponse(
                call: Call<ListAllStory>,
                response: Response<ListAllStory>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _items.value = responseBody.story
                    }
                } else {
                    Log.e(TAG, "onFailure: findDetail fail 2")
                }

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

    companion object {
        const val TAG = "DetailViewModel"
    }
}
