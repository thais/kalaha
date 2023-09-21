package com.game.kalaha.model

import java.time.LocalDateTime

data class MoveResponse(val playerId: Long, val gameId: Long, val position: Int, val createdAt: LocalDateTime) {

}
