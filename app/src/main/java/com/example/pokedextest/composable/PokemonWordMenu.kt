package com.example.pokedextest.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pokedextest.R

@Composable
fun PokemonWordMenu(
    navController: NavController
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.background))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = TopCenter

    ) {
        LottieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(),
            composition = composition,
            contentScale =  ContentScale.FillHeight,
            progress = {progress })
        Column {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_pokemon_logo),
                contentDescription = "pokemon logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(130.dp))
            TonalButton(
                onClick = { navController.navigate(
                    "pokemon_fight_screen"
                )},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                text = "Fight"
            )
            Spacer(modifier = Modifier.height(8.dp))
            TonalButton(
                onClick = { navController.navigate(
                    "player_profile_screen"
                )},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                text = "Profile"
            )
            Spacer(modifier = Modifier.height(8.dp))
            TonalButton(
                onClick = { navController.navigate(
                    "shop_screen"
                )},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                text = "Shop"
            )
            Spacer(modifier = Modifier.height(8.dp))
            TonalButton(onClick = {
                navController.navigate(
                    "pokemon_words_screen"
                )
            },modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
                text = "Words"
                )


        }

    }
}





@Composable
fun TonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Text(text = text)
    }
}



