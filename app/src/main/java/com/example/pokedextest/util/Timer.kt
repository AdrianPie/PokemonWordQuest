package com.example.pokedextest.util

import android.os.CountDownTimer

class Timer(private val totalTime: Long, private val interval: Long) {

    private var timer: CountDownTimer? = null
    private var isTimerRunning = false
    private var remainingTime: Long = totalTime
    private var listener: TimerListener? = null

    interface TimerListener {
        fun onTimerTick(remainingTime: Long)
        fun onTimerFinish()
    }

    fun setTimerListener(listener: TimerListener) {
        this.listener = listener
    }

     fun startTimer() {
        if (!isTimerRunning) {
            timer = object : CountDownTimer(remainingTime, interval) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTime = millisUntilFinished
                    listener?.onTimerTick(remainingTime)
                }

                override fun onFinish() {
                    isTimerRunning = false
                    remainingTime = 0
                    listener?.onTimerFinish()
                }
            }
            timer?.start()
            isTimerRunning = true
        }
    }

    fun pauseTimer() {
        if (isTimerRunning) {
            timer?.cancel()
            isTimerRunning = false
        }
    }

    fun resumeTimer() {
        if (!isTimerRunning) {
            startTimer()
        }
    }

    fun cancelTimer() {
        timer?.cancel()
        isTimerRunning = false
        remainingTime = totalTime
    }

    fun isRunning(): Boolean {
        return isTimerRunning
    }

    fun getRemainingTime(): Long {
        return remainingTime
    }
}