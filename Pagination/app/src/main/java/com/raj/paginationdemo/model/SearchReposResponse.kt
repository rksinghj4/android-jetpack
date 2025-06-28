package com.raj.paginationdemo.model


import com.google.gson.annotations.SerializedName

data class SearchReposResponse(
    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @field:SerializedName("items")
    val repos: List<Repo>,
    @field:SerializedName("total_count")
    val totalCount: Int
) {
    data class Repo(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("full_name") val fullName: String,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("html_url") val url: String,
        @field:SerializedName("stargazers_count") val stars: Int,
        @field:SerializedName("forks_count") val forks: Int,
        @field:SerializedName("language") val language: String?
    )

}