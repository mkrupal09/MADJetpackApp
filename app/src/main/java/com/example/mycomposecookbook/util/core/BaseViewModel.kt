package com.example.mycomposecookbook.util.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : ViewModel() {

    val loadingFlow = MutableSharedFlow<Boolean>(0)

    fun showLoading(show: Boolean) {
        loadingFlow.tryEmit(show)
    }
}