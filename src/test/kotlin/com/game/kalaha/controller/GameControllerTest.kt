package com.game.kalaha.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.game.kalaha.model.dto.GameDTO
import com.game.kalaha.model.dto.GamePlayerDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.util.*


@AutoConfigureMockMvc
@SpringBootTest
class GameControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `Should create game when all parameters are valid`() {
        mockMvc.post("/games") {
            content = asJson(createValidGame())
            contentType = APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id").exists()
            jsonPath("$.result").exists()
            jsonPath("$.board").exists()
            jsonPath("$.status").exists()
            jsonPath("$.createdAt").exists()
            jsonPath("$.players").exists()
        }
    }

    @Test
    fun `Should return bad request when body is missing`() {
        mockMvc.post("/games") {
            contentType = APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `Should return bad request when less than two player provided`() {
        mockMvc.post("/games") {
            content = asJson(createGameWithXPlayer(1))
            contentType = APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `Should return bad request when more than two players provided`() {
        mockMvc.post("/games") {
            content = asJson(createGameWithXPlayer(3))
            contentType = APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `Should return unsupported media type when content type not json`() {
        mockMvc.post("/games") {
            content = asJson(createValidGame())
            contentType = APPLICATION_OCTET_STREAM
        }.andExpect {
            status { isUnsupportedMediaType() }
        }
    }

    private fun createGameWithXPlayer(x: Int): GameDTO = GameDTO(
        IntRange(0, x - 1).map { GamePlayerDTO(id = UUID.randomUUID()) }.toList()
    )

    private fun createValidGame(): GameDTO = GameDTO(
        listOf(GamePlayerDTO(id = UUID.randomUUID()), GamePlayerDTO(id = UUID.randomUUID()))
    )

    private fun asJson(input: Any): String = objectMapper.writeValueAsString(input)
}