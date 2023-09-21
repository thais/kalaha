package com.game.kalaha.service

import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.model.dto.GamePlayerDTO
import com.game.kalaha.exceptions.GameCreationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class GameServiceTest {
    @Test
    fun `Should create a valid game when it receives the right parameters`() {
        val service = GameService()
        val firstPlayer = GamePlayerDTO(UUID.randomUUID())
        val secondPlayer = GamePlayerDTO(UUID.randomUUID())

        val game = service.create(GameDTO(listOf(firstPlayer, secondPlayer)))

        assertThat(game.players.first().id).isEqualTo(firstPlayer.id)
        assertThat(game.players.last().id).isEqualTo(secondPlayer.id)
    }

    @Test
    fun `Should not create a game with just one player`() {
        val service = GameService()
        val firstPlayer = GamePlayerDTO(UUID.randomUUID())

        val exception = assertThrows<GameCreationException> {
            service.create(GameDTO(listOf(firstPlayer)))
        }

        assertThat(exception.message).isEqualTo("You need 2 players to start a game")
    }

    @Test
    fun `Should not create a game with over two player`() {
        val service = GameService()

        val exception = assertThrows<GameCreationException> {
            service.create(GameDTO(IntRange(0, 2).map { GamePlayerDTO(UUID.randomUUID()) }))
        }

        assertThat(exception.message).isEqualTo("You need 2 players to start a game")
    }

}
