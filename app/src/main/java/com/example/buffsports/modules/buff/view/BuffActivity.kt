package com.example.buffsports.modules.buff.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buffsports.R
import com.example.buffsports.modules.buff.adapter.BuffAnswersAdpater
import com.example.buffsports.modules.buff.model.BuffResponse
import com.example.buffsports.modules.buff.viewmodel.BuffViewModel
import kotlinx.android.synthetic.main.activity_buff.*

class BuffActivity : AppCompatActivity() {

    private lateinit var buffViewModel: BuffViewModel

    private val buffAnswersAdapter by lazy {
        BuffAnswersAdpater(clickListener = {})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buff)

        buffViewModel = ViewModelProvider(this).get(BuffViewModel::class.java)

        setupRecyclerView()
        subscribeUI()
        configVideoStream()

        buffViewModel.getBuff()
    }

    private fun configVideoStream() {
        with(videoView) {
            setVideoPath("https://buffup-public.s3.eu-west-2.amazonaws.com/video/toronto+nba+cut+3.mp4")
            start()
            setOnPreparedListener() {
                buffViewModel.isPlaying = true
                buffViewModel.checkBuffState()
            }
        }
    }

    private fun setupRecyclerView() {
        with(recyclerviewBuffsAnswers) {
            setHasFixedSize(true)
            adapter = buffAnswersAdapter
        }
    }

    private fun subscribeUI() {
        with(buffViewModel) {

            onLoadFinished.observe(this@BuffActivity, Observer {

            })

            onError.observe(this@BuffActivity, Observer { errorMessage ->
                Log.d("BUFF-ERROR", errorMessage)
            })

            buffVisibility.observe(this@BuffActivity, Observer { visible ->
                if (visible) buffCard.visibility = View.VISIBLE
                else buffCard.visibility = View.GONE
            })

            buff.observe(this@BuffActivity, Observer { buff ->

                updateBuffView(buff)

                buffViewModel.timer = buff.timeToShow
                buffViewModel.checkBuffState()
            })
        }
    }

    private fun updateBuffView(buff: BuffResponse.Buff) {
        buffQuestion.text = buff.question.title
        buffName.text = "${buff.author.firstName} ${buff.author.lastName}"
        buffTimer.text = buff.timeToShow.toString()
        buffAnswersAdapter.refresh(buff.answers)
    }
}
