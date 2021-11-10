package com.wainow.tp_lab_2.domain

enum class Operation(type: Int, ) {
    PLUS(1),
    MINUS(2),
    MULT(3),
    DIVIDE(4)
}

fun getStringByOperation(op: Operation): String {
    return when(op) {
        Operation.PLUS -> " + "
        Operation.DIVIDE -> " / "
        Operation.MINUS -> " - "
        Operation.MULT -> " * "
        else -> ""
    }
}