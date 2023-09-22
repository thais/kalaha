package com.game.kalaha.repository

import com.game.kalaha.model.Board
import com.game.kalaha.model.Game
import com.game.kalaha.model.Player
import com.game.kalaha.model.Status
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDateTime


@DataMongoTest
class GameRepositoryTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate
    @Autowired
    private lateinit var gameRepository: GameRepository

    @Test
    fun `Should find game by id`() {
        val id = 1L
        val players = listOf(Player(1L, "A"), Player(2L, "B"))
        val game = Game(
            id = id,
            status = Status.CREATED,
            createdAt = LocalDateTime.now(),
            players = players,
            board = Board(),
            turn = players.first().id
        )
        val entity = gameRepository.save(game)
        println(entity)

        val result = gameRepository.findById(id)

        assertThat(result.get().id, Matchers.equalTo(id))
    }
}