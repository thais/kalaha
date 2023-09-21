package com.game.kalaha.controller

import com.game.kalaha.model.Game
import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.model.response.GameResponse
import com.game.kalaha.model.toGameResponse
import com.game.kalaha.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/games")
class GameController(val gameService: GameService) {

    @PostMapping
    fun createGame(@RequestBody game: GameDTO): ResponseEntity<GameResponse> =
        ResponseEntity(gameService.create(game).toGameResponse(), HttpStatus.CREATED)

    @GetMapping
    fun getGames(): ResponseEntity<List<Game>> =
        ResponseEntity(gameService.getAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getGameById(@PathVariable id: UUID): ResponseEntity<Game> =
        ResponseEntity(gameService.getByUuid(id), HttpStatus.OK)


    
}


