package com.example.buffsports.modules.buff.movieApi

import com.example.buffsports.core.network.BaseNetwork
import com.example.buffsports.modules.buff.model.BuffResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object BuffNetwork : BaseNetwork() {

    private val API by lazy { getRetrofitBuilder().build().create(BuffAPI::class.java) }

    fun getBuffFromAPI(
        buffId: String,
        onSuccess: (buffResponse: BuffResponse) -> Unit,
        onError: (error: String) -> Unit
    ) {
        API.getBuff(buffId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ buffResponse ->
                buffResponse.result?.let {
                    onSuccess(buffResponse)
                }
            }, { error ->
                onError(error.message.toString())
            })
    }

}