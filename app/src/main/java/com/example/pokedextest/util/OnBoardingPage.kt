package com.example.pokedextest.util

import androidx.annotation.RawRes
import com.example.pokedextest.R


sealed class OnBoardingPage(
    @RawRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.raw.waveanimation,
        title = "Welcome",
        description = " to your new best learning application!"
    )

    object Second : OnBoardingPage(
        image = R.raw.funanimation,
        title = "Discover New Words",
        description = "Here, you are going to learn new words and have great fun at the same time."
    )

    object Third : OnBoardingPage(
        image = R.raw.gaminganimation,
        title = "Play",
        description = "Embark on an exciting RPG journey in your quest for knowledge."
    )
}