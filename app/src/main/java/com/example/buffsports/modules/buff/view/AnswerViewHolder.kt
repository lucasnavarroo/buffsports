package com.example.buffsports.modules.buff.view

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buffsports.R
import com.example.buffsports.modules.buff.model.BuffResponse
import kotlinx.android.synthetic.main.buff_item.view.*

class AnswerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        buff: BuffResponse.Buff,
        answer: BuffResponse.Buff.Answer,
        clickListener: (buffId: Int) -> Unit
    ) {
        with(view) {

            setOnClickListener {
                clickListener(answer.id)
            }

            textAnswerItem.text = answer.title

            Glide.with(view)
                .load(buff.author?.image)
                .placeholder(R.drawable.ic_account)
                .override(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                )
                .into(personIcon)
        }
    }
}