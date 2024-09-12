package com.example.pokedextest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PokedexWordApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}