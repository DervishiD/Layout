package gamepackage.gamegeometry

import geometry.Point
import main.FRAMEX
import main.FRAMEY

class Grid {

    private val lines : Int
    private val columns : Int
    private val mesh : Int = 20 //TODO?

    private var origin : Point = Point(0, 0)
    private val grid : Array<Array<Cell>>

    constructor(lines : Int, columns : Int){
        this.lines = lines
        this.columns = columns
        resetOrigin()
        grid = Array(lines){Array(columns){Cell()}}
    }

    private fun resetOrigin(){
        origin setx (FRAMEX / 2) - (columns / 2) * mesh
        origin sety (FRAMEY / 2) - (lines / 2) * mesh
    }

}