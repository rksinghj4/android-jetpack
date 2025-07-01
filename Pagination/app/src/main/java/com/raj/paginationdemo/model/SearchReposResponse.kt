package com.raj.paginationdemo.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*
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

}*/

@Serializable
data class SearchReposResponse(
    val incomplete_results: Boolean,
    val items: List<Repo>,
    val total_count: Int
) {
    @Serializable
    data class Repo(
        val id: Long,
        val name: String,
        val full_name: String,
        val description: String?,
        val html_url: String,
        val stargazers_count: Int,
        val forks_count: Int,
        val language: String?
    )
}