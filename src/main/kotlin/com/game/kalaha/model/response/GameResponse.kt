package com.game.kalaha.model.response

import com.game.kalaha.model.Player
import com.game.kalaha.model.Status
import java.time.LocalDateTime

data class GameResponse(
    val id: Long,
    val status: Status,
    val createdAt: LocalDateTime,
    val players: List<Player>,
    val board: String,
    val turn: Long
)


