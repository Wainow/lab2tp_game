package com.wainow.tp_lab_2.domain

data class Game(
    val user1: User?,
    val user2: User?
) {
    fun isStarted() = user1 != null && user2 != null
}
