package com.game.kalaha.service

import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.exceptions.GameCreationException
import com.game.kalaha.exceptions.IllegalMoveException
import com.game.kalaha.model.*
import com.game.kalaha.model.Status.*
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

        if (game.status in listOf(FINISHED, DRAW, PLAYER_1_WON, PLAYER_2_WON))
            throw IllegalMoveException("Game is finished, Moves are not allowed")

        if (game.turn != move.player) throw IllegalMoveException("Invalid player for the turn")

        if (game.players[0].id == move.player && move.pit !in IntRange(1,6)) {
            throw IllegalMoveException("Player 1 is only allowed to move between pits 1 to 6")
        }
        if (game.players[1].id == move.player && move.pit !in IntRange(8,13)) {
            throw IllegalMoveException("Player 2 is only allowed to move between pits 8 to 13")
        }

        game.board.move(move.pit)
        game.turn = if(game.players[0].id == move.player) { game.players[1].id } else { game.players[0].id }
        gameRepository.save(game)

        val player1Pits = game.board.state().sliceArray(IntRange(1,6))
        val player2Pits = game.board.state().sliceArray(IntRange(8,13))
        val player1SeedsInPit = player1Pits.sumOf { it.seeds }
        val player2SeedsInPit = player2Pits.sumOf { it.seeds }

        if (player1SeedsInPit == 0) {
            game.board.pits.get(0).seeds += player2SeedsInPit
            if (game.board.pits.get(0).seeds > game.board.pits.get(7).seeds) game.status = PLAYER_2_WON
            if (game.board.pits.get(0).seeds < game.board.pits.get(7).seeds) game.status = PLAYER_1_WON
            if (game.board.pits.get(0).seeds == game.board.pits.get(7).seeds) game.status = DRAW

            gameRepository.save(game)
        }
        if (player2SeedsInPit == 0) {
            game.board.pits.get(7).seeds += player1SeedsInPit
            if (game.board.pits.get(0).seeds > game.board.pits.get(7).seeds) game.status = PLAYER_2_WON
            if (game.board.pits.get(0).seeds < game.board.pits.get(7).seeds) game.status = PLAYER_1_WON
            if (game.board.pits.get(0).seeds == game.board.pits.get(7).seeds) game.status = DRAW

            gameRepository.save(game)
        }

        return game
    }

    private fun parsePlayers(game: GameDTO): List<Player> = game.players.map { Player(it.id, "name") }

}
