package com.linha.mystoryapp.view.detailStory

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.linha.mystoryapp.data.api.response.GetStoryResponse
import com.linha.mystoryapp.data.api.response.ListStoryItem
import com.linha.mystoryapp.databinding.ActivityDetailStoryBinding
import com.linha.mystoryapp.view.ViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailStoryActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding : ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val id = intent.getStringExtra(ID)

        viewModel.getSession().observe(this) { user ->
            val token = user.token
            lifecycleScope.launch {
                try {
                    if (id != null) {
                        viewModel.getDetailStory(token, id)
                    }
                    viewModel.items.observe(this@DetailStoryActivity) { items ->
                        setDetailUser(items)
                    }
                    showToast("data berhasil ditampilkan dengan token $token")
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, GetStoryResponse::class.java)
                    showToast(errorResponse.message)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailUser(detail: ListStoryItem) {
        binding.detailItemName.text = detail.name
        binding.detailItemDescription.text = detail.description
        Glide.with(this@DetailStoryActivity)
            .load(detail.photoUrl)
            .into(binding.detailItemPhoto)
//        showToast(detail.name + detail.description + detail.photoUrl)
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ID = "ID USER"
        const val TOKEN = "TOKEN USER"
    }
}