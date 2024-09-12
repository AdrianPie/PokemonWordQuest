package com.example.pokedextest.shopscreen

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pokedextest.R

@Composable
fun ShopScreen(
    viewModel: ShopScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            TopSection(
                viewModel = viewModel,
                navController = navController
            )
            Spacer(modifier = Modifier.height(50.dp))
            ChestSection(viewModel = viewModel)
        }

    }
}

@Composable
fun ChestSection(
    viewModel: ShopScreenViewModel
) {
    var opened by remember {
        mutableStateOf(false)
    }
    var animPlaying by remember {
        mutableStateOf(1)
    }
    var context = LocalContext.current
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.nextchest))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = opened,
        clipSpec = LottieClipSpec.Frame(1,animPlaying)
    )
    val chestOffset by viewModel.chestOffsetY.collectAsState()
    val animatedOffset by animateDpAsState(
        targetValue = chestOffset,
        label = "chest animation",
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
            )
        )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .size(300.dp)
            .offset(y = animatedOffset)
    )

    Spacer(modifier = Modifier.height(5.dp))
    
    ElevatedButton(
        onClick = {
            if (viewModel.player.value.gold >= 5){
                animPlaying = 1
                viewModel.addRandomItem()
                viewModel.onBuyClick()
                opened = true
                viewModel.getPlayer()
            }else {
                Toast.makeText(context, "Not enough money", Toast.LENGTH_SHORT).show()
            }
        },
        colors = ButtonDefaults.buttonColors(Color.Gray)) {
        Text(text = "150g")
    }
    if (viewModel.isDialogShown){
        CustomDialog(
            onDismiss = { viewModel.onDismissDialog() },
            viewModel = viewModel,
            onConfirm = {animPlaying = 3}
        )
    }
}

@Composable
fun TopSection(
    navController: NavController,
    viewModel: ShopScreenViewModel
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.goldpile))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    val player by viewModel.player.collectAsState()
Column(
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Spacer(modifier = Modifier.height(16.dp))
    Row {
        Text(
            text = "Shop",
            fontSize = 20.sp
            )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
        ) {
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "Gold: ${player.gold}",
                modifier = Modifier.padding(5.dp),
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(60.dp)
                )
        }

    }
    Divider(
        color = Color.Gray,
        thickness = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
 }
}

@Composable
fun CustomDialog(
    onDismiss:()-> Unit,
    onConfirm:()-> Unit,
    viewModel: ShopScreenViewModel
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .border(1.dp, color = Color.DarkGray, shape = RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your reward: ${viewModel.randomItem.name}",
                    textAlign = TextAlign.Center
                )
                Image(painter = painterResource(id = viewModel.randomItem.icon), contentDescription = "weapon")
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )
                Text(text = "Atk: ${viewModel.randomItem.atk} Def: ${viewModel.randomItem.def}")
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }) {
                        Text(text = "Add to lot")
                }
            }
        }
    }
}

