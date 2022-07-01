package com.nauval.aplikasigithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.nauval.aplikasigithubuser.database.FavoriteUser
import com.nauval.aplikasigithubuser.database.FavoriteUserDao
import com.nauval.aplikasigithubuser.database.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val _favUserDao: FavoriteUserDao = FavoriteUserDatabase.getDatabase(application).favUserDao()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getFavUsers(): LiveData<List<FavoriteUser>> = _favUserDao.getFavUsers()
    fun insertUser(user: FavoriteUser) = executorService.execute { _favUserDao.insertFavUser(user) }
    fun deleteUser(user: FavoriteUser) = executorService.execute { _favUserDao.deleteFavUser(user) }
    fun isAFavUser(username: String): LiveData<Int> = _favUserDao.isAFavUser(username)
}