package com.example.buffsports.modules.buff.view

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buffsports.R
import com.example.buffsports.modules.buff.adapter.BuffAnswersAdpater
import com.example.buffsports.modules.buff.model.BuffResponse
import com.example.buffsports.modules.buff.viewmodel.BuffViewModel
import kotlinx.android.synthetic.main.activity_buff.*

class BuffActivity : AppCompatActivity() {

    private val SHOW_BUFF_DELAY: Long = 8000
    private val HIDE_BUFF_DELAY: Long = 2000

    private lateinit var buffViewModel: BuffViewModel
    private lateinit var buffAnswersAdapter: BuffAnswersAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buff)

        buffViewModel = ViewModelProvider(this).get(BuffViewModel::class.java)
        buffAnswersAdapter = BuffAnswersAdpater(clickListener = { stopTimer() })

        setupRecyclerView()
        subscribeUI()
        configVideoStream()
    }

    private fun configVideoStream() {
        with(videoView) {
            setVideoPath("https://buffup-public.s3.eu-west-2.amazonaws.com/video/toronto+nba+cut+3.mp4")
            start()
            setOnPreparedListener() {
                buffViewModel.isStreamPlaying = true
                buffViewModel.checkBuffState()
            }
        }
    }

    private fun setupRecyclerView() {
        with(recyclerviewBuffsAnswers) {
            setHasFixedSize(true)
            adapter = buffAnswersAdapter
        }

        buffClose.setOnClickListener {
            stopTimer()
        }
    }

    private fun subscribeUI() {
        with(buffViewModel) {

            onLoadFinished.observe(this@BuffActivity, Observer {

            })

            onError.observe(this@BuffActivity, Observer { errorMessage ->
                Toast.makeText(this@BuffActivity, "error: $errorMessage", Toast.LENGTH_SHORT).show()
            })

            shouldShowBuff.observe(this@BuffActivity, Observer { shouldShow ->

                when (shouldShow) {
                    true -> {
                        Handler().postDelayed({
                            buffCard.visibility = View.VISIBLE
                            startTimer()
                        }, SHOW_BUFF_DELAY)
                    }
                    false -> {
                        Handler().postDelayed({
                            buffCard.visibility = View.GONE
                            buffViewModel.getBuff()
                        }, HIDE_BUFF_DELAY)
                    }
                }
            })

            buff.observe(this@BuffActivity, Observer { buff ->
                updateBuffView(buff)
                buffViewModel.checkBuffState()
            })
        }
    }

    private fun updateBuffView(buff: BuffResponse.Buff) {
        buffQuestion.text = buff.question.title
        buffName.text = "${buff.author.firstName} ${buff.author.lastName}"
        buffTimer.text = buff.timeToShow.toString()
        buffViewModel.timer = buff.timeToShow

        buffAnswersAdapter.refresh(buff.answers)
    }

    private lateinit var countDownBuffTimer: CountDownTimer

    private fun startTimer() {
        countDownBuffTimer =
            object : CountDownTimer(buffViewModel.timer.toLong() * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    buffTimer.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    buffViewModel.timer = -1
                    buffViewModel.checkBuffState()
                }
            }
        countDownBuffTimer.start()
    }

    private fun stopTimer() {
        countDownBuffTimer.onFinish()
        countDownBuffTimer.cancel()
    }
}
