package com.game.kalaha.model.response

import com.game.kalaha.model.Player
import com.game.kalaha.model.Status
import java.time.LocalDateTime
import java.util.*

data class GameResponse(
    val id: UUID,
    val status: Status,
    val createdAt: LocalDateTime,
    val players: List<Player>,
    val board: String,
    val result: String
)


