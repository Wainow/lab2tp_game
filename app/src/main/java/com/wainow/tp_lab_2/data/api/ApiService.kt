package com.wainow.tp_lab_2.data.api

import com.wainow.tp_lab_2.domain.Game
import com.wainow.tp_lab_2.domain.User
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("add_user")
    suspend fun addUser(
        @Query("user") user: String
    ): String

    @GET("game")
    suspend fun getGame(): Game?
}