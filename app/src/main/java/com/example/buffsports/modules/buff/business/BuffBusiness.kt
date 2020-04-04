package com.example.buffsports.modules.buff.business

import com.example.buffsports.modules.buff.model.BuffResponse
import com.example.buffsports.modules.buff.repository.BuffRepository

object BuffBusiness {

    fun getBuff(
        buffId: String,
        onSuccess: (buff: BuffResponse.Buff) -> Unit,
        onError: (error: String) -> Unit
    ) {
        BuffRepository.getBuffFromAPI(
            buffId,
            onSuccess = { buffResponse ->
                onSuccess(buffResponse.result)
            },
            onError = { error ->
                onError(error.message.toString())
            }
        )
    }
}