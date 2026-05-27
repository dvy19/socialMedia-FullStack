package com.example.socialmediaf.userDetails

import com.example.socialmediaf.posts.PostRepository
import com.example.socialmediaf.posts.PostViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DetailViewModelFactory(
    private val repository: DetailRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}