package com.example.buffsports.modules.buff.repository

import com.example.buffsports.modules.buff.model.BuffResponse
import com.example.buffsports.modules.buff.networking.BuffNetworking

open class BuffRepository {

    fun getBuff(
        buffId: String,
        onSuccess: (buff: BuffResponse.Buff) -> Unit,
        onError: (error: String) -> Unit
    ) {
        BuffNetworking.getBuffFromAPI(
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