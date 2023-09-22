package com.game.kalaha.service

import com.game.kalaha.exceptions.GameCreationException
import com.game.kalaha.exceptions.IllegalMoveException
import com.game.kalaha.model.*
import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.model.dto.GamePlayerDTO
import com.game.kalaha.model.dto.MoveDTO
import com.game.kalaha.repository.BoardRepository
import com.game.kalaha.repository.GameRepository
import io.mockk.*
import io.mockk.impl.annotations.SpyK
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
        val slotGame = slot<Game>()
        every { gameRepository.save(capture(slotGame)) } answers { slotGame.captured }
        service.move(MoveDTO(123, 1), 1)

        assertThat(slotGame.captured.board.pits[1].seeds).isEqualTo(0)
    }

    @Test
    fun `Should return an error if its not the player turn`() {
        val gameRepository = mockk<GameRepository>()
        val service = GameService(gameRepository, mockk<BoardRepository>())
        every { gameRepository.findById(any()) } returns Optional.of(createGame(123L, 11L))

        val exception = assertThrows<IllegalMoveException> {
            service.move(MoveDTO(2, 1), 1)
        }

        assertThat(exception.message).isEqualTo("Invalid player for the turn")
    }

    @Test
    fun `Player 1 Should only be able to pick its own pits`() {
        val gameRepository = mockk<GameRepository>()
        val service = GameService(gameRepository, mockk<BoardRepository>())
        every { gameRepository.findById(any()) } returns Optional.of(createGame(123L, 11L))

        val exception = assertThrows<IllegalMoveException> {
            service.move(MoveDTO(123L, 8), 1)
        }

        assertThat(exception.message).isEqualTo("Player 1 is only allowed to move between pits 1 to 6")
    }

    @Test
    fun `Player 2 Should only be able to pick its own pits`() {
        val gameRepository = mockk<GameRepository>()
        val service = GameService(gameRepository, mockk<BoardRepository>())
        every { gameRepository.findById(any()) } returns Optional.of(createGame(123L, 11L, 11L))

        val exception = assertThrows<IllegalMoveException> {
            service.move(MoveDTO(11L, 1), 1)
        }

        assertThat(exception.message).isEqualTo("Player 2 is only allowed to move between pits 8 to 13")
    }

    @Test
    fun `Should not move if the game is finished`() {
        val gameRepository = mockk<GameRepository>()
        val service = GameService(gameRepository, mockk<BoardRepository>())
        every { gameRepository.findById(any()) } returns Optional.of(finishedGame())

        val exception = assertThrows<IllegalMoveException> {
            service.move(MoveDTO(11L, 1), 1)
        }

        assertThat(exception.message).isEqualTo("Game is finished, Moves are not allowed")
    }

    fun createGame(player1: Long, player2: Long, playerOfTurn: Long? = null): Game {
        return Game(
            id = 123L,
            Status.CREATED,
            createdAt = LocalDateTime.now(),
            players = listOf(Player(player1, "asd"), Player(player2, "asd")),
            Board(),
            playerOfTurn ?: player1
        )
    }

    fun playerWon() =
        Game(
            id = 123L,
            Status.IN_PROGRESS,
            createdAt = LocalDateTime.now(),
            players = listOf(Player(11L, "asd"), Player(12L, "asd")),
            Board(),
            11L
        )


    fun finishedGame(): Game {
        return Game(
            id = 123L,
            Status.FINISHED,
            createdAt = LocalDateTime.now(),
            players = listOf(Player(11L, "asd"), Player(12L, "asd")),
            Board(),
            10L
        )
    }

}
