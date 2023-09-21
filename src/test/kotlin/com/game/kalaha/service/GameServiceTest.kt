package com.game.kalaha.service

import com.game.kalaha.exceptions.GameCreationException
import com.game.kalaha.model.Board
import com.game.kalaha.model.Game
import com.game.kalaha.model.Player
import com.game.kalaha.model.Status
import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.model.dto.GamePlayerDTO
import com.game.kalaha.model.dto.MoveDTO
import com.game.kalaha.repository.BoardRepository
import com.game.kalaha.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class GameServiceTest {

    @Test
    fun `Should create a valid game when it receives the right parameters`() {
        val repository = mockk<GameRepository>()
        val boardRepository = mockk<BoardRepository>()
        val service = GameService(repository, boardRepository)
        val firstPlayer = GamePlayerDTO(123L)
        val secondPlayer = GamePlayerDTO(124L)

        every { boardRepository.save(any()) } returns Board()
        every { repository.save(any()) } returns createGame(firstPlayer.id, secondPlayer.id)

        val game = service.create(GameDTO(listOf(firstPlayer, secondPlayer)))

        assertThat(game.players.first().id).isEqualTo(firstPlayer.id)
        assertThat(game.players.last().id).isEqualTo(secondPlayer.id)
    }

    @Test
    fun `Should not create a game with just one player`() {
        val service = GameService(mockk<GameRepository>(), mockk<BoardRepository>())
        val firstPlayer = GamePlayerDTO(123L)

        val exception = assertThrows<GameCreationException> {
            service.create(GameDTO(listOf(firstPlayer)))
        }

        assertThat(exception.message).isEqualTo("You need 2 players to start a game")
    }

    @Test
    fun `Should not create a game with over two player`() {
        val service = GameService(mockk<GameRepository>(), mockk<BoardRepository>())

        val exception = assertThrows<GameCreationException> {
            service.create(GameDTO(IntRange(0, 2).map { GamePlayerDTO(123L) }))
        }

        assertThat(exception.message).isEqualTo("You need 2 players to start a game")
    }


    @Test
    fun `Player should be able to move when is its turn`() {
        val gameRepository = mockk<GameRepository>()
        val service = GameService(gameRepository, mockk<BoardRepository>())
        every { gameRepository.findById(any()) } returns Optional.of(createGame(123L, 11L))
        every { gameRepository.save(any()) } returns createGame(123L, 11L)
        val move = service.move(MoveDTO(123, 1), 1)
        
        assertThat(move.board.pits[1].seeds).isEqualTo(0)
    }

    fun createGame(player1: Long, player2: Long): Game {
        return Game(
            id = 123L,
            Status.CREATED,
            createdAt = LocalDateTime.now(),
            players = listOf(Player(player1, "asd"), Player(player2, "asd")),
            Board(),
            player1
        )
    }

}
