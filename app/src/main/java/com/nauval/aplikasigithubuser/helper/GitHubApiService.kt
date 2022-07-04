package com.nauval.aplikasigithubuser.helper

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users")
    fun getUsers(@Query("page") page: String): Call<List<UserResponse>>

    @GET("search/users")
    fun findUser(@Query("q") username: String): Call<UserSearchResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailsResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username: String): Call<List<UserResponse>>
}
