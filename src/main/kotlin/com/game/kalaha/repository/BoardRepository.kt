package com.game.kalaha.repository

import com.game.kalaha.model.Board
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: MongoRepository<Board, Long> {
}