package com.example.buffsports.modules.buff.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buffsports.core.livedata.SingleLiveEvent
import com.example.buffsports.modules.buff.business.BuffBusiness
import com.example.buffsports.modules.buff.model.BuffResponse

class BuffViewModel : ViewModel() {

    var buff: MutableLiveData<BuffResponse.Buff> = MutableLiveData()
    var shouldShowBuff: MutableLiveData<Boolean> = MutableLiveData()

    val onLoadFinished = SingleLiveEvent<Void>()
    val onError = SingleLiveEvent<String>()

    var currentBuff = 1
    var isStreamPlaying = false
    var timer = -1

    fun getBuff() {
        BuffBusiness.getBuff(
            currentBuff.toString(),
            onSuccess = { buff ->
                this.buff.value = buff
                onLoadFinished.call()
            },
            onError = { errorMessage ->
                onError.value = errorMessage
                onLoadFinished.call()
            }
        )

        if (currentBuff == 5) currentBuff = 1 else currentBuff += 1
    }

    fun checkBuffState() {
        shouldShowBuff.value = isStreamPlaying && timer >= 0
    }
}