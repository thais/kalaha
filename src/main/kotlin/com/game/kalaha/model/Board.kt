package com.game.kalaha.model

import com.game.kalaha.exceptions.IllegalMoveException


class Board () {
    private var pits = Array(14) { Pit(6) }
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

}


data class Pit(var seeds: Int)
