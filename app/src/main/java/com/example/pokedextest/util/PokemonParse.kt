package com.example.pokedextest.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import com.example.pokedextest.data.remote.responses.Stat
import com.example.pokedextest.data.remote.responses.Type
import com.example.pokedextest.ui.theme.AtkColor
import com.example.pokedextest.ui.theme.DefColor
import com.example.pokedextest.ui.theme.HPColor
import com.example.pokedextest.ui.theme.SpAtkColor
import com.example.pokedextest.ui.theme.SpDefColor
import com.example.pokedextest.ui.theme.SpdColor
import com.example.pokedextest.ui.theme.TypeBug
import com.example.pokedextest.ui.theme.TypeDark
import com.example.pokedextest.ui.theme.TypeDragon
import com.example.pokedextest.ui.theme.TypeElectric
import com.example.pokedextest.ui.theme.TypeFairy
import com.example.pokedextest.ui.theme.TypeFighting
import com.example.pokedextest.ui.theme.TypeFire
import com.example.pokedextest.ui.theme.TypeFlying
import com.example.pokedextest.ui.theme.TypeGhost
import com.example.pokedextest.ui.theme.TypeGrass
import com.example.pokedextest.ui.theme.TypeGround
import com.example.pokedextest.ui.theme.TypeIce
import com.example.pokedextest.ui.theme.TypeNormal
import com.example.pokedextest.ui.theme.TypePoison
import com.example.pokedextest.ui.theme.TypePsychic
import com.example.pokedextest.ui.theme.TypeRock
import com.example.pokedextest.ui.theme.TypeSteel
import com.example.pokedextest.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when(type.type.name.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}
fun parseStatToColor(stat: Stat): Color {
    return when(stat.stat.name.toLowerCase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}
fun parseStatsToAbbr(stat: Stat): String {
    return when(stat.stat.name.toLowerCase()){
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpecAtk"
        "special-defense" -> "SpecDef"
        "speed" -> "Spd"
        else -> ""
    }
}