package com.example.pokedextest

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedextest.composable.PokemonWordMenu
import com.example.pokedextest.createcharacterscreen.CreateCharacterScreen
import com.example.pokedextest.pokemonfightscreen.PokemonFightScreen
import com.example.pokedextest.pokemonwordscreen.PokemonWordsScreen
import com.example.pokedextest.profilscreen.PlayerProfileScreen
import com.example.pokedextest.shopscreen.ShopScreen
import com.example.pokedextest.splashscreen.SplashViewModel
import com.example.pokedextest.ui.theme.PokedexTestTheme
import com.example.pokedextest.welcomescreen.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel
    lateinit var screen: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isLoading.value
        }
        setContent {
            PokedexTestTheme {
                screen = splashViewModel.startDestination.value
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = screen
                ) {
                    composable("pokemon_menu_screen") {
                        PokemonWordMenu(navController = navController)
                    }

                    composable("shop_screen") {
                        ShopScreen(
                            navController = navController
                        )
                    }
                    composable("pokemon_fight_screen") {
                        PokemonFightScreen(
                            navController = navController
                        )
                    }

                    composable("player_profile_screen") {
                        PlayerProfileScreen(
                            navController = navController
                        )
                    }
                    composable("pokemon_words_screen") {
                        PokemonWordsScreen(
                        )
                    }
                    composable("welcome_screen") {
                        WelcomeScreen(
                            navController = navController
                        )
                    }
                    composable("create_character_screen") {
                        CreateCharacterScreen(
                            navController = navController
                        )
                    }

                }
            }
        }

    }}