package com.example.buffsports.core.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseNetwork {

    companion object {
        const val BASE_URL: String = "https://buffup.proxy.beeceptor.com"
    }

    protected fun getRetrofitBuilder(): Retrofit.Builder {

        val retrofitBuilder = Retrofit.Builder()

        with(retrofitBuilder) {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(BaseOkHttpClient.defaultOkHttpClient)
        }
        return retrofitBuilder
    }
}