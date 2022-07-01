package com.nauval.aplikasigithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(user: FavoriteUser)

    @Delete
    fun deleteFavUser(user: FavoriteUser)

    @Query("SELECT COUNT (username) from favorite_user WHERE username = :username")
    fun isAFavUser(username: String): LiveData<Int>

    @Query("SELECT * from favorite_user")
    fun getFavUsers(): LiveData<List<FavoriteUser>>
}