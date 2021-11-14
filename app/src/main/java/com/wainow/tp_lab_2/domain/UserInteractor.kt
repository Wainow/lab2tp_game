package com.wainow.tp_lab_2.domain

import com.wainow.tp_lab_2.data.api.RetrofitBuilder
import com.wainow.tp_lab_2.data.db.DBHelper

class UserInteractor {
    private val apiService = RetrofitBuilder.apiService

    fun createUser() = User(
        id = (0..9999).random(),
        currentNumber = 0,
        availableNumbers = arrayListOf(
            (1..9).random(),
            (1..9).random(),
            (1..9).random(),
            (1..9).random(),
            (1..9).random()
        ),
        availableOperations = arrayListOf(
            Operation.values().toList().shuffled().first(),
            Operation.values().toList().shuffled().first(),
            Operation.values().toList().shuffled().first(),
            Operation.values().toList().shuffled().first(),
            Operation.values().toList().shuffled().first()
        )
    )

    suspend fun getGame(): Game? = apiService.getGame()

    suspend fun getLocalGame(): Game {
        val users = DBHelper.getUsers()
        return if(users.isNotEmpty()) {
            val user1 = users.last()
            Game(user1, createUser())
        } else {
            Game(createUser(), createUser())
        }
    }

    suspend fun sendUser(user: String) = apiService.addUser(user)

    suspend fun saveUser(user: User) {
        DBHelper.saveUsers(listOf(user))
    }

}