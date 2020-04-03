package com.example.buffsports.modules.buff.model

import com.google.gson.annotations.SerializedName

data class BuffResponse(
    val result: Buff
) {
    data class Buff(
        val id: Int,
        @SerializedName("client_id")
        val clientId: String,
        @SerializedName("stream_id")
        val streamId: String,
        @SerializedName("time_to_show")
        val timeToShow: Int,
        val priority: Int,
        @SerializedName("created_at")
        val createdAt: String,
        val author: Author,
        val question: Question,
        val answers: List<Answer>,
        val language: String
    ) {
        data class Author(
            val firstName: String,
            val lastName: String
        )

        data class Question(
            val id: Int,
            val title: String,
            val category: Int
        )

        data class Answer(
            val id: Int,
            @SerializedName("buff_id")
            val buffId: Int,
            val title: String
        )
    }
}