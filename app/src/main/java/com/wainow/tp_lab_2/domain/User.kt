package com.wainow.tp_lab_2.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: Int,
    var currentNumber: Int,
    val availableOperations: ArrayList<Operation>,
    val availableNumbers: ArrayList<Int>,
    var result: String = ""
)
