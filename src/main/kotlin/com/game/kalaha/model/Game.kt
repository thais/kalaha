package com.game.kalaha.model

import com.game.kalaha.model.response.GameResponse
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "Game")
class Game(
    val id: UUID,
    var status: Status,
    val createdAt: LocalDateTime,
    val players: List<Player>,
    var board: Board,
    var result: String
)

fun Game.toGameResponse(): GameResponse {
    return GameResponse(id, status, createdAt, players, board.print(), result)
}