package com.example.pokedextest.data.remote.responses

import com.example.pokedextest.data.remote.responses.GenerationI
import com.example.pokedextest.data.remote.responses.GenerationIi
import com.example.pokedextest.data.remote.responses.GenerationIii
import com.example.pokedextest.data.remote.responses.GenerationIv
import com.example.pokedextest.data.remote.responses.GenerationV
import com.example.pokedextest.data.remote.responses.GenerationVi
import com.example.pokedextest.data.remote.responses.GenerationVii
import com.example.pokedextest.data.remote.responses.GenerationViii

data class Versions(
    val generation_i: GenerationI,
    val generation_ii: GenerationIi,
    val generation_iii: GenerationIii,
    val generation_iv: GenerationIv,
    val generation_v: GenerationV,
    val generation_vi: GenerationVi,
    val generation_vii: GenerationVii,
    val generation_viii: GenerationViii
)