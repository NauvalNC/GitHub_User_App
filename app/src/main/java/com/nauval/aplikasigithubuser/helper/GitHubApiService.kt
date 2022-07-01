package com.nauval.aplikasigithubuser.helper

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users")
//    @Headers("Authorization: token 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")
    fun getUsers(@Query("page") page: String): Call<List<UserResponse>>

    @GET("search/users")
//    @Headers("Authorization: token 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")
    fun findUser(@Query("q") username: String): Call<UserSearchResponse>

    @GET("users/{username}")
//    @Headers("Authorization: token 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")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailsResponse>

    @GET("users/{username}/followers")
//    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<UserResponse>>

    @GET("users/{username}/following")
//    @Headers("Authorization: token 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")
    fun getFollowings(@Path("username") username: String): Call<List<UserResponse>>
}