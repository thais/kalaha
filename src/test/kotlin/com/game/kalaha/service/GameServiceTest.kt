package com.game.kalaha.service

import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.model.dto.GamePlayerDTO
import com.game.kalaha.exceptions.GameCreationException
import com.game.kalaha.model.Board
import com.game.kalaha.model.Game
import com.game.kalaha.model.Player
import com.game.kalaha.model.Status
import com.game.kalaha.repository.GameRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*
import java.util.UUID.randomUUID

class GameServiceTest {


    @Test
    fun `Should create a valid game when it receives the right parameters`() {
        val repository = mockk<GameRepository>()
        val service = GameService(repository)
        val firstPlayer = GamePlayerDTO(randomUUID())
        val secondPlayer = GamePlayerDTO(randomUUID())

        every { repository.save(any())  } returns createGame(firstPlayer.id, secondPlayer.id)

        val game = service.create(GameDTO(listOf(firstPlayer, secondPlayer)))

        assertThat(game.players.first().id).isEqualTo(firstPlayer.id)
        assertThat(game.players.last().id).isEqualTo(secondPlayer.id)
    }

    @Test
    fun `Should not create a game with just one player`() {
        val service = GameService(mockk<GameRepository>())
        val firstPlayer = GamePlayerDTO(randomUUID())

        val exception = assertThrows<GameCreationException> {
            service.create(GameDTO(listOf(firstPlayer)))
        }

        assertThat(exception.message).isEqualTo("You need 2 players to start a game")
    }

    @Test
    fun `Should not create a game with over two player`() {
        val service = GameService(mockk<GameRepository>())

        val exception = assertThrows<GameCreationException> {
            service.create(GameDTO(IntRange(0, 2).map { GamePlayerDTO(randomUUID()) }))
        }

        assertThat(exception.message).isEqualTo("You need 2 players to start a game")
    }

    fun createGame(player1:  UUID, player2 : UUID) : Game {
        return Game(id = randomUUID(), Status.CREATED, createdAt = LocalDateTime.now(), players = listOf(Player(player1, "asd"), Player(player2,"asd")), Board(),"")
    }

}
