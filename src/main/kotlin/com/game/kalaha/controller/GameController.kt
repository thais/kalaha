package com.game.kalaha.controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.model.response.GameResponse
import com.game.kalaha.model.toGameResponse
import com.game.kalaha.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/games")
class GameController(val gameService: GameService, val objectMapper: ObjectMapper) {

    @PostMapping
    fun createGame(@RequestBody game: GameDTO): ResponseEntity<GameResponse> =
        ResponseEntity(gameService.create(game).toGameResponse(), HttpStatus.CREATED)

}


