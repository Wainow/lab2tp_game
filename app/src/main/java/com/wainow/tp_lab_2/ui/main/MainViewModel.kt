package com.wainow.tp_lab_2.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wainow.tp_lab_2.data.db.DBHelper
import com.wainow.tp_lab_2.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel : ViewModel() {
    val resultLiveData = MutableLiveData<Int?>()
    val stringResultLiveData = MutableLiveData("")
    val lastOperationLiveData = MutableLiveData<Operation?>()
    val userLiveData = MutableLiveData<User>()
    var isOperation = true
    val interactor = UserInteractor()

    var isInitialized = false
    var isWaiting = false

    fun createUser(isNew: Boolean = true): User {
        val user = interactor.createUser()
        if(!isNew) {
            user.id = userLiveData.value?.id!!
            user.result = userLiveData.value?.result.toString()
        }
        userLiveData.value = user.copy()
        isInitialized = true
        return user
    }

    private fun setFirstNumber(num: Int) {
        resultLiveData.value = num
        stringResultLiveData.value = num.toString()
    }

    private fun applyOperation(num1: Int, op: Operation, num2: Int): Int {
        return when(op) {
            Operation.PLUS -> num1 + num2
            Operation.DIVIDE -> num1 / num2
            Operation.MINUS -> num1 - num2
            Operation.MULT -> num1 * num2
        }
    }

    fun addNumber(num: Int): Boolean {
        if(userLiveData.value != null) {
            with(userLiveData.value!!) {
                return when {
                    resultLiveData.value == null -> {
                        setFirstNumber(num)
                        isOperation = true
                        true
                    }
                    !isOperation -> {
                        resultLiveData.value = applyOperation(resultLiveData.value!!, lastOperationLiveData.value!!, num)
                        stringResultLiveData.value = stringResultLiveData.value + num.toString()
                        isOperation = true
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        } else {
            return false
        }
    }

    fun addOperation(op: Operation): Boolean {
        return if (isOperation) {
            lastOperationLiveData.value = op
            stringResultLiveData.value = stringResultLiveData.value + getStringByOperation(op)
            isOperation = false
            true
        } else {
            false
        }
    }

    fun getResultString() =
        "${stringResultLiveData.value} = ${resultLiveData.value}"

    fun getEnemyResultString(): String {
        val game = getGame()
        val enemy: User? = if(userLiveData.value?.id == game?.user1?.id) {
            game?.user2
        } else {
            game?.user1
        }
        return enemy?.result ?: ""
    }

    fun sendResult(user: User? = userLiveData.value): String {
        var result = ""
        if(user != null){
            runBlocking {
                val userJson = Gson().toJson(user)
                Log.d("DebugLogs", "userJson: $userJson")
                try {
                    withContext(Dispatchers.IO) {
                        interactor.saveUser(user)
                    }
                    result = interactor.sendUser(userJson)
                } catch (e: Exception) {
                    Log.e("Error", e.printStackTrace().toString())
                }
            }
        }
        Log.d("DebugLogs", "result: $result")
        return result
    }

    fun getGame(): Game? {
        var game: Game? = null
        runBlocking {
            try {
                Log.d("DebugLogs", "getGame")
                game = interactor.getGame()
            } catch (e: Exception) {
                Log.e("Error", e.printStackTrace().toString())
            }
            if(game == null) {
                withContext(Dispatchers.IO) {
                    Log.d("DebugLogs", "getLocalGame")
                    game = interactor.getLocalGame()
                }
            }
        }
        return game
    }

}