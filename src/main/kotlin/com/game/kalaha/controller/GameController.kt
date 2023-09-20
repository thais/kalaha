package com.game.kalaha

import com.game.kalaha.model.Game
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {

    @GetMapping
    fun hello() = "Hello, Jacob! :)"

    @PostMapping
    fun createGame(@RequestBody game: Game) {

    }

}