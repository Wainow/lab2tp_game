package com.wainow.tp_lab_2.domain

import com.wainow.tp_lab_2.data.RetrofitBuilder

class UserInteractor {
    private val apiService = RetrofitBuilder.apiService

    fun createUser() = User(
        name = "User${(0..9999).random()}",
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

    suspend fun addUser(user: String) = apiService.addUser(user)

}