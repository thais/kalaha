package com.game.kalaha.controller

import com.game.kalaha.model.Game
import com.game.kalaha.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/games")
class GameController (val gameService: GameService) {

    @GetMapping
    fun hello() = "Hello, Jacob! :)"

    @PostMapping
    fun createGame(@RequestBody game: GameDTO) : ResponseEntity<Game> {
        return ResponseEntity(gameService.create(game), HttpStatus.CREATED)
    }

}