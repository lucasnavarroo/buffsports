package com.example.buffsports.modules.buff.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.buffsports.modules.buff.model.BuffResponse
import kotlinx.android.synthetic.main.buff_item.view.*

class AnswerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(answer: BuffResponse.Buff.Answer, clickListener: (buffId: Int) -> Unit) {
        with(view) {

            setOnClickListener {
                clickListener(answer.id)
            }

            textAnswerItem.text = answer.title
        }
    }
}