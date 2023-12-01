package com.linha.mystoryapp.view.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.linha.mystoryapp.data.api.ApiConfig
import com.linha.mystoryapp.data.api.response.GetStoryResponse
import com.linha.mystoryapp.data.api.response.ListStoryItem
import com.linha.mystoryapp.data.user.UserRepository
import com.linha.mystoryapp.data.user.pref.UserModel
import com.linha.mystoryapp.view.detailStory.DetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel (private val repository: UserRepository): ViewModel() {
    private val _items = MutableLiveData<List<ListStoryItem?>?>()
    val items: LiveData<List<ListStoryItem?>?> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStoryLocation(token : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStoriesWithLocation(token)
        client.enqueue(object : Callback<GetStoryResponse> {
            override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure: getStoryLocation fail 1")
            }

            override fun onResponse(
                call: Call<GetStoryResponse>,
                response: Response<GetStoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _items.value = responseBody.listStory
                    }
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: getStoryLocation fail 2")
                }

            }
        })
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}