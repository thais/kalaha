package com.game.kalaha.controller

import com.game.kalaha.exceptions.GameCreationException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(GameCreationException::class)
    fun handleGameCreationException(ex: GameCreationException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, BAD_REQUEST)
    }

}

class ErrorMessage(var status: Int? = null, var message: String? = null)
