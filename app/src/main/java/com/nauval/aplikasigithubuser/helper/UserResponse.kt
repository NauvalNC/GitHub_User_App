package com.nauval.aplikasigithubuser.helper

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("login")
	val login: String,
	@field:SerializedName("type")
	val type: String,
	@field:SerializedName("avatar_url")
	val avatarUrl: String
) {
	fun getAtUsername() = "@$login"
}

data class UserSearchResponse(
	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<UserResponse>
)

data class UserDetailsResponse(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String
) {
	fun getAtUsername() = "@$login"
	fun getRepoURL() = "https://github.com/$login"
}