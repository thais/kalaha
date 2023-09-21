package com.game.kalaha.model

import com.game.kalaha.exceptions.IllegalMoveException

class Board {
    var pits = Array(14) { Pit(6) }
    init {
        pits[0] = Pit(0)
        pits[7] = Pit(0)
    }
    // think about board
    //   13 12 11 10 9 8
    // [0]          [7]
    //   1 2 3 4 5 6

    fun state() = pits.clone()


    fun move(i: Int) : Array<Pit> {
        if (i == 0 || i == 7) {
            throw IllegalMoveException("You cannot move storage houses")
        }
        if (pits[i].seeds == 0) {
            throw IllegalMoveException("You cannot move empty houses")
        }
        var next = i+1
        while (pits[i].seeds != 0) {
            if (next == 14) {
                next = 0
            }
            pits[i].seeds--
            pits[next].seeds++
            next++
        }
        return state()
    }

    //TODO Review board
    fun print(): String {
        var boardStatus = """
            [${pits[13].seeds}][${pits[12].seeds}][${pits[11].seeds}][${pits[10].seeds}][${pits[9].seeds}][${pits[8].seeds}]
           (${pits[0].seeds})                  (${pits[7].seeds})
              [${pits[1].seeds}][${pits[2].seeds}][${pits[3].seeds}][${pits[4].seeds}][${pits[5].seeds}][${pits[6].seeds}]
        """.trimIndent()
        return boardStatus
    }

}


data class Pit(var seeds: Int)
