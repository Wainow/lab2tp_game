package com.wainow.tp_lab_2.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.wainow.tp_lab_2.R
import com.wainow.tp_lab_2.domain.User

class MainFragment : Fragment() {
    // TODO:
    //  2) добавить использование available...
    //  3) взаимодействие с сервером
    //  4) отображение вражеских цифр

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var user: User? = null
    private var enemy: User? = null
    private var charCount = 0
    private var numCount = 0

    private lateinit var btnNum1: Button
    private lateinit var btnNum2: Button
    private lateinit var btnNum3: Button
    private lateinit var btnNum4: Button
    private lateinit var btnNum5: Button
    private lateinit var btnCharacter1: Button
    private lateinit var btnCharacter2: Button
    private lateinit var btnCharacter3: Button
    private lateinit var btnCharacter4: Button
    private lateinit var btnCharacter5: Button
    private lateinit var btnStart: Button
    private lateinit var message: TextView
    private lateinit var enemyMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        initView(view)
        setListeners()
        return view
    }

    private fun initView(view: View) {
        btnStart = view.findViewById(R.id.btn_start)
        btnNum1 = view.findViewById(R.id.btn_1)
        btnNum2 = view.findViewById(R.id.btn_2)
        btnNum3 = view.findViewById(R.id.btn_3)
        btnNum4 = view.findViewById(R.id.btn_4)
        btnNum5 = view.findViewById(R.id.btn_5)
        btnCharacter1 = view.findViewById(R.id.crt_1)
        btnCharacter2 = view.findViewById(R.id.crt_2)
        btnCharacter3 = view.findViewById(R.id.crt_3)
        btnCharacter4 = view.findViewById(R.id.crt_4)
        btnCharacter5 = view.findViewById(R.id.crt_5)
        enemyMessage = view.findViewById(R.id.enemy_message)
        message = view.findViewById(R.id.message)

        setEnablesAllButtons(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        btnStart.setOnClickListener {
            if(!viewModel.isInitialized) initUser()
            val result = viewModel.sendResult()
            btnStart.text = "game started"
            if(result == "200") {
                if(viewModel.getGame()?.isStarted() == true) {
                    viewModel.isWaiting = false
                    btnStart.isEnabled = false
                    setEnablesAllButtons(true)
                } else {
                    viewModel.isWaiting = true
                    btnStart.text = "WAITING USER 2 (REFRESH)"
                    setEnablesAllButtons(false)
                }
            } else if(result == "226") {
                btnStart.text = "game was started already (refresh)"
                setEnablesAllButtons(false)
            } else if(result == "") {
                btnStart.text = "game started (offline)"
            }
        }
        viewModel.stringResultLiveData.observeForever {
            viewModel.userLiveData.value?.result = viewModel.getResultString()
            viewModel.userLiveData.value?.currentNumber = viewModel.resultLiveData.value ?: 0
            message.text = viewModel.getResultString()
            if(viewModel.isInitialized && !viewModel.isWaiting) {
                enemyMessage.text = viewModel.getEnemyResultString()
            }
            val result = viewModel.sendResult()
            if(result == "42") {
                setEnablesAllButtons(false)
                btnStart.isEnabled = true
                btnStart.isClickable = false
                btnStart.text = "You are winner"
            } else if(result == "43") {
                setEnablesAllButtons(false)
                btnStart.isEnabled = true
                btnStart.isClickable = false
                btnStart.text = "You are loser"
            }
        }
    }

    private fun initUser(isNew: Boolean = true) {
        user = viewModel.createUser(isNew)
        with(user!!) {
            btnNum1.text = availableNumbers[0].toString()
            btnNum2.text = availableNumbers[1].toString()
            btnNum3.text = availableNumbers[2].toString()
            btnNum4.text = availableNumbers[3].toString()
            btnNum5.text = availableNumbers[4].toString()

            btnCharacter1.text = availableOperations[0].name
            btnCharacter2.text = availableOperations[1].name
            btnCharacter3.text = availableOperations[2].name
            btnCharacter4.text = availableOperations[3].name
            btnCharacter5.text = availableOperations[4].name
            numCount = 0
            charCount = 0

            setEnablesAllButtons(true)
        }
    }

    private fun setListeners() {
        btnCharacter1.setOnClickListener {
            setCharClickListener(0, btnCharacter1)
        }
        btnCharacter2.setOnClickListener {
            setCharClickListener(1, btnCharacter2)
        }
        btnCharacter3.setOnClickListener {
            setCharClickListener(2, btnCharacter3)
        }
        btnCharacter4.setOnClickListener {
            setCharClickListener(3, btnCharacter4)
        }
        btnCharacter5.setOnClickListener {
            setCharClickListener(4, btnCharacter5)
        }
        btnNum1.setOnClickListener {
            setNumClickListener(0, btnNum1)
        }
        btnNum2.setOnClickListener {
            setNumClickListener(1, btnNum2)
        }
        btnNum3.setOnClickListener {
            setNumClickListener(2, btnNum3)
        }
        btnNum4.setOnClickListener {
            setNumClickListener(3, btnNum4)
        }
        btnNum5.setOnClickListener {
            setNumClickListener(4, btnNum5)
        }
    }

    private fun setCharClickListener(i: Int, button: Button) {
        if(user != null) {
            with(user!!.availableOperations[i]){
                button.isEnabled = !viewModel.addOperation(this)
                charCount++
                if(charCount == 5) {
                    initUser(false)
                }
                //viewModel.userLiveData.value?.availableOperations?.remove(this)
            }
        }
    }

    private fun setNumClickListener(i: Int, button: Button) {
        if(user != null) {
            with(user!!.availableNumbers[i]){
                button.isEnabled = !viewModel.addNumber(this)
                numCount++
                if(numCount == 5) {
                    // do nothing
                }
                //viewModel.userLiveData.value?.availableNumbers?.remove(this)
            }
        }
    }

    private fun setEnablesAllButtons(it: Boolean) {
        btnNum1.isEnabled = it
        btnNum2.isEnabled = it
        btnNum3.isEnabled = it
        btnNum4.isEnabled = it
        btnNum5.isEnabled = it
        btnCharacter1.isEnabled = it
        btnCharacter2.isEnabled = it
        btnCharacter3.isEnabled = it
        btnCharacter4.isEnabled = it
        btnCharacter5.isEnabled = it
    }
}