package com.linha.mystoryapp.data.user.pref

data class UserModel(
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)