package com.nauval.aplikasigithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nauval.aplikasigithubuser.database.FavoriteUser
import com.nauval.aplikasigithubuser.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val _favUserRepo = FavoriteUserRepository(application)

    fun insertUser(user: FavoriteUser) = _favUserRepo.insertUser(user)
    fun deleteUser(user: FavoriteUser) = _favUserRepo.deleteUser(user)
    fun getFavUsers(): LiveData<List<FavoriteUser>> = _favUserRepo.getFavUsers()
    fun isAFavUser(username: String): LiveData<Int> = _favUserRepo.isAFavUser(username)
}