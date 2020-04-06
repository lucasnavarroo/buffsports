package com.example.buffsports.modules.buff.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buffsports.core.livedata.SingleLiveEvent
import com.example.buffsports.modules.buff.repository.BuffRepository
import com.example.buffsports.modules.buff.model.BuffResponse

class BuffViewModel(private val buffRepository: BuffRepository) : ViewModel() {

    var buff: MutableLiveData<BuffResponse.Buff> = MutableLiveData()
    var shouldShowBuff: MutableLiveData<Boolean> = MutableLiveData()

    var onLoadFinished = SingleLiveEvent<Void>()
    var onError = SingleLiveEvent<String>()

    var currentBuff = 1
    var isStreamPlaying = false
    var timer = -1

    fun getBuff() {
        buffRepository.getBuff(
            currentBuff.toString(),
            onSuccess = { buff ->
                this.buff.value = buff
            },
            onError = { errorMessage ->
                onError.value = errorMessage
                onError.call()
            }
        )

        if (currentBuff == 5) currentBuff = 1 else currentBuff += 1
    }

    fun checkBuffState() {
        shouldShowBuff.value = isStreamPlaying && timer >= 0
    }
}