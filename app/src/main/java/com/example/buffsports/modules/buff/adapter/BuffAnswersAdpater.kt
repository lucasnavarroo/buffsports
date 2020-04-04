package com.example.buffsports.modules.buff.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buffsports.R
import com.example.buffsports.modules.buff.model.BuffResponse
import com.example.buffsports.modules.buff.view.AnswerViewHolder

class BuffAnswersAdpater(
    private val clickListener: (answersId: Int) -> Unit
) : RecyclerView.Adapter<AnswerViewHolder>() {

    private var answers: List<BuffResponse.Buff.Answer> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.buff_item, parent, false)
        return AnswerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(answers[position], clickListener)
    }

    fun refresh(answers: List<BuffResponse.Buff.Answer>) {
        this.answers = answers
        notifyDataSetChanged()
    }
}

