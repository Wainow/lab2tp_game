package com.wainow.tp_lab_2.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> Fragment.observe(liveData: LiveData<T>, crossinline observer: (T) -> Unit) =
    liveData.observeForever(Observer {
        observer(it)
    })