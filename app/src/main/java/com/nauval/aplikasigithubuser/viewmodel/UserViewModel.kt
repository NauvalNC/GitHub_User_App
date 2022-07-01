package com.nauval.aplikasigithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nauval.aplikasigithubuser.helper.GitHubApiConfig
import com.nauval.aplikasigithubuser.helper.UserResponse
import com.nauval.aplikasigithubuser.helper.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : MainViewModel() {
    private val _userList = MutableLiveData<List<UserResponse>>()
    val userList: LiveData<List<UserResponse>> = _userList

    private val _searchList = MutableLiveData<List<UserResponse>>()
    val searchList: LiveData<List<UserResponse>> = _searchList

    private val _numOfFound = MutableLiveData<Int>()
    val numOfFound: LiveData<Int> = _numOfFound

    init {
        getHomeUserList()
    }

    private fun getHomeUserList() {
        setOnLoad()

        GitHubApiConfig.getGitHubApiService().getUsers("1")
            .enqueue(object : Callback<List<UserResponse>> {
                override fun onResponse(call: Call<List<UserResponse>>,
                                        response: Response<List<UserResponse>>
                ) {
                    if (response.isSuccessful && response.body() != null) _userList.value = response.body()
                    else setFail()
                    setEndLoad()
                }

                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    setFail()
                    setEndLoad()
                }
            })
    }

    fun searchUser(username: String) {
        setOnLoad()
        GitHubApiConfig.getGitHubApiService().findUser(username)
            .enqueue(object : Callback<UserSearchResponse> {
                override fun onResponse(
                    call: Call<UserSearchResponse>,
                    response: Response<UserSearchResponse>
                ) {
                    if (response.isSuccessful && response.body()?.items != null) {
                        _searchList.value = response.body()?.items
                        _numOfFound.value = response.body()?.totalCount
                    }
                    else setFail()
                    setEndLoad()
                }

                override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                    setFail()
                    setEndLoad()
                }
            })
    }
}