package com.game.kalaha.model

import java.time.LocalDateTime
import java.util.*


class Game(
    val id: UUID,
    var status: Status,
    val createdAt: LocalDateTime,
    val players: List<Player>,
    var board: Board,
    var result: String
)