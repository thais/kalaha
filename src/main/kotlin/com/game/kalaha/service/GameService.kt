package com.game.kalaha.service

import com.game.kalaha.controller.GameDTO
import com.game.kalaha.exceptions.GameCreationException
import com.game.kalaha.model.Board
import com.game.kalaha.model.Game
import com.game.kalaha.model.Player
import com.game.kalaha.model.Status.CREATED
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class GameService {
    fun create(game: GameDTO): Game {
        //TODO extract message to a message properties file
        if (game.players.size != 2) {
            throw GameCreationException("You need 2 players to start a game")
        }

        parsePlayers(game)

        return Game(
            id = UUID.randomUUID(),
            status = CREATED,
            players = parsePlayers(game),
            createdAt = LocalDateTime.now(),
            board = Board(),
            result = ""
        )
    }

    private fun parsePlayers(game: GameDTO): List<Player> = game.players.map { Player(it.id, "name") }

}
