package com.game.kalaha.service

import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.exceptions.GameCreationException
import com.game.kalaha.model.*
import com.game.kalaha.model.Status.CREATED
import com.game.kalaha.model.dto.MoveDTO
import com.game.kalaha.repository.BoardRepository
import com.game.kalaha.repository.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GameService (@Autowired val gameRepository: GameRepository,
    @Autowired val boardRepository: BoardRepository) {
    fun create(gameDTO: GameDTO): Game {
        //TODO extract message to a message properties file
        if (gameDTO.players.size != 2) {
            throw GameCreationException("You need 2 players to start a game")
        }

        val board = boardRepository.save(Board())

        val players = parsePlayers(gameDTO)
        val game = Game(                                                                                                                                         
            status = CREATED,
            createdAt = LocalDateTime.now(),
            players = players,
            board = board,
            turn = players[0].id)

        return gameRepository.save(game)
    }

    //TODO PAGINATION
    fun getAll() : List<Game> {
        val findAll = gameRepository.findAll()
        return findAll
    }

    fun getById(id: Long) : Game {
        return gameRepository.findById(id).orElseThrow { throw NotFoundException() }
    }
    
    fun move(move: MoveDTO, id:Long) : Game {
        var game = getById(id)
        if (game.turn == move.player) {
            game.board.move(move.pit)
            gameRepository.save(game)
        }

        return game
    }

    private fun parsePlayers(game: GameDTO): List<Player> = game.players.map { Player(it.id, "name") }

}
