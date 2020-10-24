package com.example.githubuser.service

import com.example.githubuser.model.UserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("users")
    fun getGitHubUsers(@Query("since") page : Int,
                       @Query("per_page") limit : Int): Call<MutableList<UserListResponse.UserInfo>>
}