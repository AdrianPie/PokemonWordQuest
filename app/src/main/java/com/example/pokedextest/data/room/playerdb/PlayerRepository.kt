package com.example.pokedextest.data.room.playerdb

import com.example.pokedextest.data.models.Player
import javax.inject.Inject


class PlayerRepository @Inject constructor(
    private val playerDao: PlayerDao
    ) {

    suspend fun insert(player: Player) {
        playerDao.insertPlayer(player)
    }

    suspend fun update(player: Player) {
        playerDao.updatePlayer(player)
    }

    suspend fun delete(player: Player) {
        playerDao.deletePlayer(player)
    }
    suspend fun get(playerId: Int): Player? {
        return playerDao.getPlayer(playerId)
    }
}