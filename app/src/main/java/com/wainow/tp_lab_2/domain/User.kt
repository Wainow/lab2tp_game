package com.wainow.tp_lab_2.domain

data class User(
    var name: String,
    var currentNumber: Int,
    val availableOperations: ArrayList<Operation>,
    val availableNumbers: ArrayList<Int>,
    var result: String = ""
)
