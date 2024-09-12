package com.example.pokedextest.data.remote.responses

import com.example.pokedextest.data.remote.responses.MoveLearnMethod
import com.example.pokedextest.data.remote.responses.VersionGroup

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
)