package com.game.kalaha.model

import com.game.kalaha.model.response.GameResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime

@Document(collection = "game")
data class Game(
    @Id
    var id: Long = Instant.now().toEpochMilli(),
    var status: Status,
    val createdAt: LocalDateTime,
    val players: List<Player>,
    var board: Board,
    var turn: Long = players[0].id) {

}

fun Game.toGameResponse(): GameResponse {
    return GameResponse(id, status, createdAt, players, board.print(), turn)
}