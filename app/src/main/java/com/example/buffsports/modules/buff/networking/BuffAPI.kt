package com.example.buffsports.modules.buff.networking

import com.example.buffsports.modules.buff.model.BuffResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface BuffAPI {

    @GET("/buffs/{buffId}")
    fun getBuff(
        @Path("buffId") buffId: String
    ): Observable<BuffResponse>

}