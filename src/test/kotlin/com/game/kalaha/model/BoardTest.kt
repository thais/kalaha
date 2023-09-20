package com.game.kalaha.model

import com.game.kalaha.exceptions.IllegalMoveException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {

    //   13 12 11 10 9 8
    // [0]          [7]
    //   1 2 3 4 5 6
    @Test
    fun `should create a board with empty stores and pits with 6 seeds`() {
        val board = Board()

        assertThat(board.state()[0].seeds).isEqualTo(0)
        assertThat(board.state()[7].seeds).isEqualTo(0)
    }

    @Test
    fun `when selecting a pit for the first time, the seeds must be cleared and move for the following pits`() {

        val board = Board().move(6)

        assertThat(board[6].seeds).isEqualTo(0)
        assertThat(board[7].seeds).isEqualTo(1)
        assertThat(board[8].seeds).isEqualTo(7)
        assertThat(board[9].seeds).isEqualTo(7)
        assertThat(board[10].seeds).isEqualTo(7)
        assertThat(board[11].seeds).isEqualTo(7)
        assertThat(board[12].seeds).isEqualTo(7)
    }

    @Test
    fun `throws an exception when selecting a pit with an empty house`() {

        var board = Board()

        val message = assertThrows<IllegalMoveException> {
            board.move(6)
            board.move(6)
        }
    }

    //   13 12 11 10 9 8
    // [0]          [7]
    //   1 2 3 4 5 6

    @Test
    fun `should move counterclockwise to beginning of the list when it finishes`() {

        val board = Board().move(12)

        assertThat(board[12].seeds).isEqualTo(0)
        assertThat(board[13].seeds).isEqualTo(7)
        assertThat(board[0].seeds).isEqualTo(1)
        assertThat(board[1].seeds).isEqualTo(7)
        assertThat(board[2].seeds).isEqualTo(7)
        assertThat(board[3].seeds).isEqualTo(7)
        assertThat(board[4].seeds).isEqualTo(7)
    }

}
