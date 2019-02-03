package gamepackage.gamegeometry

import geometry.Point

class Grid {
    companion object {

        private const val STARTINGX : Int = 0
        private const val STARTINGY : Int = 0
        const val MESH : Int = 25 //

        private var origin : Point = Point(STARTINGX, STARTINGY)
        private var grid : ArrayList<ArrayList<Cell>> = ArrayList<ArrayList<Cell>>()

    }
}