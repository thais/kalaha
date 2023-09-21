package com.game.kalaha.repository

import com.game.kalaha.model.Game
import org.springframework.data.mongodb.repository.MongoRepository

interface GameRepository : MongoRepository<Game, String> {
}