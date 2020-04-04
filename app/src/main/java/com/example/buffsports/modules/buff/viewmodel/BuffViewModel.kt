package com.example.buffsports.modules.buff.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buffsports.core.livedata.SingleLiveEvent
import com.example.buffsports.modules.buff.business.BuffBusiness
import com.example.buffsports.modules.buff.model.BuffResponse

class BuffViewModel : ViewModel() {

    var buff: MutableLiveData<BuffResponse.Buff> = MutableLiveData()

    val onLoadFinished = SingleLiveEvent<Void>()
    val onError = SingleLiveEvent<String>()

    var currentBuff = 1

    fun initBuffs() {
        getBuff(
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
    }

    private fun getBuff(
        buffId: String,
        onSuccess: (buff: BuffResponse.Buff) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        BuffBusiness.getBuff(
            buffId,
            onSuccess = { buff ->
                onSuccess(buff)
            },
            onError = { errorMessage ->
                onError(errorMessage)
            }
        )
    }
}


