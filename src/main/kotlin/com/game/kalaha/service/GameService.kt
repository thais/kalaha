package com.game.kalaha.service

import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.exceptions.GameCreationException
import com.game.kalaha.model.Board
import com.game.kalaha.model.Game
import com.game.kalaha.model.Player
import com.game.kalaha.model.Status.CREATED
import com.game.kalaha.repository.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class GameService (@Autowired val gameRepository: GameRepository) {
    fun create(game: GameDTO): Game {
        //TODO extract message to a message properties file
        if (game.players.size != 2) {
            throw GameCreationException("You need 2 players to start a game")
        }

        parsePlayers(game)
        val asd =gameRepository.save(Game(
            id = UUID.randomUUID(),
            status = CREATED,
            players = parsePlayers(game),
            createdAt = LocalDateTime.now(),
            board = Board(),
            result = ""
        ))
        return asd
    }

    private fun parsePlayers(game: GameDTO): List<Player> = game.players.map { Player(it.id, "name") }

}
