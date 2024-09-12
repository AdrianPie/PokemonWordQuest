package com.example.pokedextest.pokemonfightscreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pokedextest.R
import com.example.pokedextest.composable.TonalButton
import com.example.pokedextest.data.models.Player
import com.example.pokedextest.data.models.Word
import com.example.pokedextest.data.remote.responses.Pokemon
import kotlinx.coroutines.delay

@Composable
fun PokemonFightScreen(
    navController: NavController,
    viewModel: PokemonFightViewModel = hiltViewModel()
) {
    var dmgDone by remember {
        mutableStateOf(0.0)
    }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loadinganimation))

    var pokemonNumber by remember {
        mutableStateOf(0)
    }

    val multiplier = remember {
        mutableStateOf(1.0)
    }

    val randomWord = remember {
        mutableStateOf(Word())
    }

    val pokemonYOffset = viewModel.pokemonOffsetDpY.collectAsState()

    randomWord.value = viewModel.randomWord.collectAsState().value
    val pokemonsState = viewModel.randomPokemonList.collectAsState()
    val pokemons = pokemonsState.value



    val infiniteTransition = rememberInfiniteTransition(label = "Player animation")
    val pokemonOffsetYAnim by infiniteTransition.animateFloat(
        initialValue = pokemonYOffset.value.toFloat(),
        targetValue = pokemonYOffset.value.toFloat() + 10,
        animationSpec = infiniteRepeatable(tween(1600), RepeatMode.Reverse), label = "Player animation"
    )


    val playerOffsetAnim by infiniteTransition.animateFloat(
        initialValue = 530F,
        targetValue = 540f,
        animationSpec = infiniteRepeatable(tween(1400), RepeatMode.Reverse), label = "Player animation"
    )


    val player = viewModel.player.collectAsState()

    if (pokemons.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                    model = pokemons[pokemonNumber].sprites.front_default,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = pokemonOffsetYAnim.dp, x = 0.dp)
                        .zIndex(1f)
            )
            Image(
                painter = painterResource(id  = player.value.avatar),
                contentDescription = null,
                modifier = Modifier
                    .zIndex(1f)
                    .size(200.dp)
                    .offset(y = playerOffsetAnim.dp, x = 200.dp)
            )
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                        Text(
                            text = pokemons[pokemonNumber].name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            modifier = Modifier
                        )
                }
                    PokemonBox(
                        pokemon = pokemons[pokemonNumber],
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.White)
                            .weight(0.7f)
                            .fillMaxWidth()
                    )

                Spacer(modifier = Modifier.size(35.dp))

                Box(modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.LightGray)
                    .weight(1.8f)
                    .fillMaxWidth()
                    .padding(5.dp)
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        var playerBool by remember {
                            mutableStateOf(false)
                        }
                        var enemyBool by remember {
                            mutableStateOf(false)
                        }
                        var scoreBool by remember {
                            mutableStateOf(false)
                        }
                        var nextBool by remember {
                            mutableStateOf(true)
                        }
                        var enteredTranslation by remember {
                            mutableStateOf("")
                        }

                        MiddleScreen(
                            playerTurnVisibility = playerBool,
                            enemyTurnVisibility = enemyBool,
                            nextTurnVisibility = nextBool,
                            timerEndEnemy = {
                                enemyBool = false
                                nextBool = true
                            },
                            timerEndNext = {
                                nextBool = false
                                playerBool = true
                                viewModel.getRandomWord()

                            },
                            timerEndPlayer = {
                                playerBool = false
                                if (viewModel.pokemonHp.value <= 0.0) {
                                    scoreBool = true
                                    viewModel.pokemonDefeated()
                                } else {
                                    enemyBool = true
                                }
                            },
                            randomWord = randomWord.value.english,
                            hint = "",
                            textFieldState = {newValue ->
                                enteredTranslation = newValue
                            },
                            multiplier = {newValue ->
                                multiplier.value = newValue
                            },
                            viewModel = viewModel,
                            multiplierValue = multiplier.value,
                            onAttackClick = { },
                            dmgDone = {newValue ->
                                dmgDone = newValue
                            },
                            dmgValue = dmgDone,
                            pokemon = pokemons[pokemonNumber],
                            scoreTurnVisibility = scoreBool,
                            nextFight = {
                                pokemonNumber += 1
                                viewModel.updatePokemonHp(pokemons[pokemonNumber].stats[0].base_stat.toDouble())
                                scoreBool = false
                                nextBool = true
                            },
                            navController = navController
                        )
                    }
                }
                Spacer(modifier = Modifier.size(35.dp))
                PlayerBox(
                    player = player.value,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                        .weight(0.7f)
                        .fillMaxWidth()
                        ,
                    viewModel = viewModel
                )
            }
        }
    } else{
        LottieAnimation(
            composition = composition,
            modifier = Modifier.fillMaxSize(),
            iterations = LottieConstants.IterateForever
        )
    }

}
@Composable
fun StatsRow(
    statColor: Color,
    currentPercent: Float,
    statsName: String,
    hpAmount: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(currentPercent)
            .clip(CircleShape)
            .background(statColor)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = statsName,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = (hpAmount).toDouble().toString(),
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun PokemonBox(
    pokemon: Pokemon,
    viewModel: PokemonFightViewModel,
    modifier: Modifier
) {

    val pokemonHp = viewModel.pokemonHp.collectAsState()
    LaunchedEffect(Unit) {
        if (pokemonHp.value == 0.0)
            viewModel.updatePokemonHp(pokemon.stats[0].base_stat.toDouble())
    }

    val currentHpPercent = animateFloatAsState(
        targetValue = viewModel.roundTo1DecimalPlace( pokemonHp.value/ pokemon.stats[0].base_stat).toFloat(),
        animationSpec = tween(
            1000
        ), label = "hp animation"
    )
    AnimatedBorderCard(
        modifier = modifier,
        animationDuration = 10000,
        gradient = Brush.sweepGradient(listOf(colorResource(id = R.color.greydark), colorResource(id = R.color.greybrown)))
    ){
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            StatsRow(
                    statColor = Color.Red,
                    currentPercent = currentHpPercent.value,
                    statsName = "HP",
                    hpAmount = viewModel.roundTo1DecimalPlace(pokemonHp.value).toString()
               )
            Spacer(modifier = Modifier.size(5.dp))
            StatsRow(
                statColor = Color.Yellow,
                currentPercent = 0.3f,
                statsName = "ATK",
                hpAmount = pokemon.stats[1].base_stat.toString()
            )
            Spacer(modifier = Modifier.size(5.dp))
            StatsRow(
                statColor = Color.Gray,
                currentPercent = 0.3f,
                statsName = "DEF",
                hpAmount = pokemon.stats[2].base_stat.toString()
            )
        }
    }
}

@Composable
fun PlayerBox(
    player: Player,
    modifier: Modifier,
    viewModel: PokemonFightViewModel
) {

    val playerHp = viewModel.playerHp.collectAsState()
    LaunchedEffect(player.vit) {
        if (player.vit != 1)
            viewModel.updatePlayerHp(player.vit.toDouble())
    }
    val currentHpPercent = animateFloatAsState(
        targetValue = viewModel.roundTo1DecimalPlace( playerHp.value/ player.vit.toDouble()).toFloat(),
        animationSpec = tween(
            1000
        ), label = "hp animation"
    )

    AnimatedBorderCard(modifier = modifier,
        animationDuration = 10000,
        gradient = Brush.sweepGradient(listOf(colorResource(id = R.color.greydark), colorResource(id = R.color.greybrown)))
    ){
        Column(modifier = Modifier
        ) {
            StatsRow(
                statColor = Color.Yellow,
                currentPercent = 0.3f,
                statsName = "ATK",
                hpAmount = player.atk.toString()
            )
            Spacer(modifier = Modifier.size(5.dp))
            StatsRow(
                statColor = Color.Gray,
                currentPercent = 0.3f,
                statsName = "DEF",
                hpAmount = player.def.toString()
            )
            Spacer(modifier = Modifier.size(5.dp))
            StatsRow(
                    statColor = Color.Red,
                    currentPercent = currentHpPercent.value,
                    statsName = "HP",
                    hpAmount = viewModel.roundTo1DecimalPlace(playerHp.value).toString()
                )


        }
    }
}
@Composable
fun MiddleScreen(
    playerTurnVisibility: Boolean,
    enemyTurnVisibility: Boolean,
    scoreTurnVisibility: Boolean,
    nextTurnVisibility: Boolean,
    timerEndNext: () -> Unit,
    timerEndPlayer: () -> Unit,
    timerEndEnemy: () -> Unit,
    pokemon: Pokemon,
    randomWord: String,
    hint: String,
    textFieldState: (String) -> Unit,
    multiplier: (Double) -> Unit,
    viewModel: PokemonFightViewModel,
    multiplierValue: Double,
    onAttackClick: () -> Unit,
    dmgDone: (Double) -> Unit,
    dmgValue: Double,
    nextFight: () -> Unit,
    navController: NavController

) {
    Box(modifier = Modifier){
        NextTurn(
            visibility = nextTurnVisibility,
            timerEnd = timerEndNext,
            whatNext = "START"
        )
        PlayerTurn(
            visibility = playerTurnVisibility,
            timerEnd = timerEndPlayer,
            randomWord = randomWord,
            textFieldState = textFieldState,
            multiplier = multiplier,
            multiplierValue = multiplierValue,
            onAttackClick = onAttackClick,
            viewModel = viewModel,
            dmgDone = dmgDone,
            dmgValue = dmgValue
        )
        EnemyTurn(
            visibility = enemyTurnVisibility,
            timerEnd = timerEndEnemy,
            viewModel = viewModel,
            multiplier = multiplier,
            multiplierValue = multiplierValue,
            pokemon = pokemon
        )
        ScoreScreen(
            viewModel = viewModel,
            visibility = scoreTurnVisibility,
            nextFight = nextFight,
            navController = navController
        )
    }
}

@Composable
fun PlayerTurn(
    visibility: Boolean,
    timerEnd: () -> Unit,
    randomWord: String,
    viewModel: PokemonFightViewModel,
    textFieldState: (String) -> Unit,
    dmgDone: (Double) -> Unit,
    dmgValue: Double,
    multiplier: (Double) -> Unit,
    multiplierValue: Double,
    onAttackClick: () -> Unit
) {

    var context = LocalContext.current

    var timerText by remember {
        mutableStateOf(30)
    }
    var enteredTranslation by remember {
        mutableStateOf("")
    }
    var hintx by remember {
        mutableStateOf("")
    }

    AnimatedVisibility(visible =  visibility) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)) {
            Column(modifier = Modifier
                .fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = timerText.toString(),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = "Translate: \n${randomWord}",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier =  Modifier.fillMaxWidth()
                    )
                Spacer(modifier = Modifier
                    .height(10.dp))
                Text(
                    text = hintx,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier =  Modifier.fillMaxWidth()
                )
                TextField(
                    value = enteredTranslation,
                    label = {
                        Text(text = "Enter translation")
                    },
                    onValueChange = {
                        enteredTranslation = it
                        textFieldState(enteredTranslation)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(CenterHorizontally)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(onClick = {
                        onAttackClick()
                        if (viewModel.randomWord.value.polish == enteredTranslation) {
                            multiplier(multiplierValue + 0.5)
                            viewModel.getRandomWord()
                            viewModel.cleanHint()
                            viewModel.correctAnswer()
                            hintx = ""
                            enteredTranslation = ""
                        } else {
                            viewModel.wrongAnswer()
                            if (multiplierValue>0.1)  {
                                multiplier(multiplierValue - 0.1)
                            }

                        }
                    }) {
                        Text(text = "X ATTACK!")
                    }
                    Button(onClick = {
                            hintx = viewModel.showHint()
                        if (multiplierValue>0.1) {
                            multiplier(multiplierValue - 0.1)
                        }
                    }) {
                        Text(text = "HINT!")
                    }
                }
                Text(
                    text = "COMBO x ${viewModel.roundTo1DecimalPlace(multiplierValue)}",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier =  Modifier.fillMaxWidth()
                )
            }
            Timer(
                totalTime = 20000L,
                onTick = {timerText--},
                onTimerEnd = {
                    viewModel.updateDmgAnimation(false)
                    val dmg = viewModel.roundTo1DecimalPlace(
                        viewModel.player.value.atk.toDouble() * multiplierValue
                    )
                    dmgDone(dmgValue + dmg + 50)
                    viewModel.updatePokemonHp(viewModel.pokemonHp.value - dmg)
                    multiplier(2.0)
                    Toast.makeText(context, "DMG DONE: $dmg", Toast.LENGTH_LONG).show()
                    viewModel.cleanHint()
                    viewModel.generateThreeRandomWords()
                    hintx = ""
                    enteredTranslation = ""
                    timerText = 30
                    timerEnd()
                },
                isTimerRunning = true,
            )
        }
    }
}
@Composable
fun EnemyTurn(
    visibility: Boolean,
    timerEnd: () -> Unit,
    pokemon: Pokemon,
    viewModel: PokemonFightViewModel,
    multiplier: (Double) -> Unit,
    multiplierValue: Double,
) {
    var timerText by remember {
        mutableStateOf(30)
    }
    var wordIndexSelected by remember {
        mutableStateOf(0)
    }

    var comboAnimation by remember {
        mutableStateOf(false)
    }
    var fontAnimationValue by remember {
        mutableStateOf(10f)
    }
    val fontAnimation by animateFloatAsState(
        targetValue = fontAnimationValue, label = "font animation",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        )

    )

    val context = LocalContext.current
    val wordToTranslate = viewModel.rightWord.collectAsState()

    AnimatedVisibility(visible = visibility) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White),
        ) {
            Column(modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
            ) {
                Text(
                    text = timerText.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier =  Modifier.fillMaxWidth()
                )
                Text(
                    text = "chose right translation: \n ${wordToTranslate.value.polish}",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                )
                Button(onClick = {
                if (wordToTranslate.value.english == viewModel.getThreeAnswerList()[wordIndexSelected]) {
                    viewModel.correctAnswer()
                    Toast.makeText(context, "dobrze", Toast.LENGTH_SHORT).show()
                    multiplier(multiplierValue - 0.1)
                    fontAnimationValue+= 1f
                    viewModel.generateThreeRandomWords()

                } else {
                    viewModel.wrongAnswer()
                    Toast.makeText(context, "zle", Toast.LENGTH_SHORT).show()
                    multiplier(multiplierValue +   0.1)
                    fontAnimationValue-= 1f
                    viewModel.generateThreeRandomWords()

                }}) {
                    Text(text = "Attack!")
                    
                }
                LazyRow {
                    items(viewModel.getThreeAnswerList().size) {
                        Box(modifier = Modifier
                            .padding(15.dp)
                            .size(60.dp)
                            .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                            .clickable {
                                wordIndexSelected = it
                            }
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (wordIndexSelected == it) {
                                    Color.Green
                                } else {
                                    Color.White
                                }
                            )
                        ) {
                            Text(
                                text = viewModel.getThreeAnswerList()[it],
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
                Text(
                    text = "COMBO x ${viewModel.roundTo1DecimalPlace(multiplierValue)}",
                    fontSize = fontAnimation.sp
                )
            }

            Timer(
                totalTime = 30000L,
                onTick = {timerText--},
                onTimerEnd = {
                    timerEnd()
                    viewModel.updatePlayerHp(viewModel.playerHp.value - pokemon.stats[1].base_stat.toDouble() )
                    timerText = 30
                },
                isTimerRunning = true,
            )
        }
    }
}

@Composable
fun ScoreScreen(
    visibility: Boolean,
    viewModel: PokemonFightViewModel,
    nextFight: () -> Unit,
    navController: NavController
) {

    val pokemonDefeated = viewModel.pokemonDefeated.collectAsState()
    val pokemonList = viewModel.randomPokemonList.collectAsState()

    AnimatedVisibility(visible = visibility) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
        ) {
            Column(modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "${pokemonList.value[pokemonDefeated.value].name} defeated",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "You have gained 10 xp, and 15 gold",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier
                    .height(5.dp)
                    .fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TonalButton(onClick = {
                        nextFight()
                    },
                        text = "Next Fight")
                    TonalButton(
                        onClick = { viewModel.onRetreatClick() },
                        text = "Retreat"
                        )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "You defeated ${pokemonDefeated.value} pokemons!")
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Next one: ")
                AsyncImage(
                    model = pokemonList.value[pokemonDefeated.value].sprites.front_default,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .fillMaxWidth()
                )
            }
            if (viewModel.isDialogShown){
                CustomDialog(
                    onDismiss = {
                        viewModel.onDismissDialog()
                        navController.popBackStack()
                                },
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun NextTurn(
    visibility: Boolean,
    timerEnd: () -> Unit,
    whatNext: String
) {
    var timerText by remember {
        mutableStateOf(30)
    }
    AnimatedVisibility(visible = visibility) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)

        ) {
            Column(modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timerText.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(86.dp))
                Text(
                    text = whatNext,
                    textAlign = TextAlign.Center,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Timer(
                totalTime = 4000L,
                onTick = {timerText--},
                onTimerEnd = {
                    timerEnd()
                    timerText = 4
                },
                isTimerRunning = true,
            )
        }
    }
}


@Composable
fun Timer(
    totalTime: Long,
    onTimerEnd: () -> Unit,
    onTick: () -> Unit,
    initialValue: Float = 1f,
    isTimerRunning: Boolean,
) {
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }

    LaunchedEffect(key1 = isTimerRunning , key2 = currentTime) {
        if(currentTime > 0 && isTimerRunning) {
            delay(1000L)
            onTick()
            currentTime -= 1000L
            if (currentTime == 0L) {
                onTimerEnd()
            }
            value = currentTime / totalTime.toFloat()
        }
    }
}
@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 15.dp),
    borderWidth: Dp = 5.dp,
    gradient: Brush,
    animationDuration: Int,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier,
        shape = shape
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(borderWidth)
                .drawWithContent {
                    rotate(degrees = degrees) {
                        drawCircle(
                            brush = gradient,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = MaterialTheme.colorScheme.surface,
            shape = shape
        ) {
            content()
        }
    }
}

@Composable
fun CustomDialog(
    onDismiss:()-> Unit,
    viewModel: PokemonFightViewModel
) {
    val correctAnswersNumber by viewModel.correctQuestionNumber.collectAsState()
    val answersNumber by viewModel.questionNumber.collectAsState()
    val expGained by viewModel.expGained.collectAsState()
    val goldGained by viewModel.goldGained.collectAsState()

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
                    text = "You have gained $expGained exp, $goldGained gold  $expGained!?",
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )
                Text(
                    text = "Correct Translations: ${correctAnswersNumber}/${answersNumber}",
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        onDismiss()
                    }) {
                    Text(text = "Run like a chicken")
                }
            }
        }
    }
}








