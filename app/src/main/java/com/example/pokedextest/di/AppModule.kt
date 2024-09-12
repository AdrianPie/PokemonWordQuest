package com.example.pokedextest.di

import android.content.Context
import androidx.room.Room
import com.example.pokedextest.data.remote.PokeApi
import com.example.pokedextest.data.room.playerdb.PlayerRepository
import com.example.pokedextest.repository.PokemonRepository
import com.example.pokedextest.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)
    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }


}