package com.example.pokedextest.profilscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedextest.R
import com.example.pokedextest.data.models.Item
import com.example.pokedextest.data.models.Player

@Composable
fun PlayerProfileScreen(
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    playerViewModel: PlayerProfileViewModel = hiltViewModel()
) {

    val playerInfo = playerViewModel.player.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        PokemonDetailStateWrapper(
            playerInfo = playerInfo.value,
            modifier = Modifier
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            viewModel = playerViewModel
            )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(playerInfo.value.avatar == R.drawable.player || playerInfo.value.avatar == R.drawable.avatar_three) {
                AsyncImage(
                    model = playerInfo.value.avatar,
                    contentDescription = "avatar",
                    modifier = Modifier
                        .size(pokemonImageSize)
                        .offset(y = topPadding)
                )
            }
            }
    }
}



@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .offset(16.dp, 16.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }


    }
    
}

@Composable
fun PokemonDetailStateWrapper(
    playerInfo: Player,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    viewModel: PlayerProfileViewModel
) {

            PokemonDetailSection(
                playerInfo = playerInfo,
                modifier = modifier
                    .offset(y = ((-20).dp)),
                viewModel = viewModel
            )


}

@Composable
fun PokemonDetailSection(
    playerInfo: Player,
    modifier: Modifier = Modifier,
    viewModel: PlayerProfileViewModel
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
    ) {
        Text(
            text = " ${playerInfo.name}",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )

        PlayerDetailDataSection(
            playerExp = playerInfo.exp,
            playerGold = playerInfo.gold,
            viewmodel = viewModel
        )
        PlayerBaseStats(
            viewModel = viewModel
            )
        WeaponList(
            playerInfo = playerInfo,
            viewModel = viewModel
        )
    }

    
    
    

}


@Composable
fun PlayerDetailDataSection(
    playerExp: Int,
    playerGold: Int,
    sectionHeight: Dp = 80.dp,
    viewmodel: PlayerProfileViewModel
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sectionHeight)
    ) {
        val weapon = viewmodel.weaponEquipped.collectAsState()
        var bool = remember {
            false
        }

        PlayerDetailDataItem(
            dataValue = playerExp,
            dataUnit = " exp",
            dataIcon = painterResource(id = R.drawable.exp_24),
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier
            .size(1.dp, sectionHeight)
            .background(Color.LightGray)
            )
        PlayerDetailDataItem(
            dataValue = playerGold,
            dataUnit = " g",
            dataIcon = painterResource(id = R.drawable.gold_24),
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier
            .size(1.dp, sectionHeight)
            .background(Color.LightGray)
        )

        PlayerDetailDataItem(
                dataValue = weapon.value.atk,
                dataUnit = " atk",
                dataIcon = painterResource(id = weapon.value.icon),
                modifier = Modifier
                    .weight(1f)
        )
    }
}

@Composable
fun PlayerDetailDataItem(
    dataValue: Int,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier) {
        Icon(painter = dataIcon, contentDescription = null, tint = Color.Black, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text ="$dataValue$dataUnit",
            color = MaterialTheme.colors.onSurface,
            fontSize = 16.sp
        )
    }
}

@Composable
fun PlayerStats(
    statsName: String,
    statsValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 100,
    viewModel: PlayerProfileViewModel
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statsValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(height)
                .clip(CircleShape)
                .background(
                    if (isSystemInDarkTheme()) {
                        Color(0xFF505050)
                    } else {
                        Color.LightGray
                    }
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(currentPercent.value)
                    .clip(CircleShape)
                    .background(statColor)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = statsName,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = (currentPercent.value * statMaxValue).toInt().toString(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.width(5.dp))

        IconButton(modifier = Modifier
            .height(height),
            onClick = { viewModel.updatePlayer(exp = statsName, weapon = null, soldItem = null )}) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.add_24px),
                contentDescription = "add button"
            )
        }
    }
    Spacer(modifier = Modifier
        .height(16.dp)
    )

}

@Composable
fun PlayerBaseStats(
    animDelayPerItem: Int = 500,
    viewModel: PlayerProfileViewModel
) {
    val maxBaseStat by viewModel.maxState.collectAsState()
    val mappedStats by viewModel.mappedStats.collectAsState()

    Column(modifier = Modifier
        .fillMaxWidth()) {
        Text(
            text = "Base stats:",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        for (i in mappedStats) {
         if (maxBaseStat > 0)   {
            PlayerStats(
                statsName = i.key,
                statsValue =i.value.first,
                statMaxValue = maxBaseStat,
                statColor = i.value.second,
                animDelay = (animDelayPerItem),
                viewModel = viewModel
            )
        }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun WeaponList(
    playerInfo: Player,
    viewModel: PlayerProfileViewModel
    ) {

    Text(text = "Weapon List: ")
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
    ) {
        itemsIndexed(
            items = playerInfo.weaponList,
            key = { index, _ -> index },
            itemContent =  { index, weapon ->
                WeaponListItem(
                    weapon = weapon,
                    viewModel = viewModel,
                    index = index
                )
            }
        )

    }


}

@Composable
fun WeaponListItem(
    weapon: Item,
    viewModel: PlayerProfileViewModel,
    index: Int

) {
   Box(
       modifier = Modifier
           .fillMaxWidth()
           .height(50.dp)
           .clip(CircleShape)
           .background(Color.LightGray)

   ) {
       Row(
           modifier = Modifier
               .fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Image(painter = painterResource(id = weapon.icon), contentDescription = "weapon image")
           Text(text = "Atk: ${weapon.atk}  Def: ${weapon.def}")

           Row {
               IconButton(onClick = { viewModel.updatePlayer(exp = null, weapon = weapon.icon, soldItem = null) }
               ) {
                   Icon(painter = painterResource(
                       id = R.drawable.add_box_24px ),
                       contentDescription = "Delete button"
                   )
               }
               Spacer(modifier = Modifier.width(16.dp))
               IconButton(onClick = {
                   viewModel.updatePlayer(exp = null, weapon = null, soldItem = index)
               }) {
                   Icon(painter = painterResource(
                       id = R.drawable.sell_24px ),
                       contentDescription = "Delete button"
                   )
               }

           }

       }
   }
    Spacer(modifier = Modifier.height(16.dp))
}

