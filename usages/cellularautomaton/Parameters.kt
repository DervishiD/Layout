package usages.cellularautomaton

import java.awt.Color

internal object Parameters {

    private var gridWidth : Int = 0

    private var gridHeight : Int = 0

    private lateinit var deadCellColour : Color

    private lateinit var aliveCellColour : Color

    private lateinit var borderColour : Color

    private lateinit var survivingParameters : Collection<Int>

    private lateinit var birthParameters : Collection<Int>

    private lateinit var grid : Array<Array<Boolean>>

    internal fun setWidth(width : Int){
        gridWidth = width
    }

    internal fun setHeight(height : Int){
        gridHeight = height
    }

    internal fun setDeadCellColour(colour : Color){
        deadCellColour = colour
    }

    internal fun setAliveCellColour(colour : Color){
        aliveCellColour = colour
    }

    internal fun setBorderColour(colour : Color){
        borderColour = colour
    }

    internal fun setSurvivingParameters(parameters : Collection<Int>){
        survivingParameters = parameters
    }

    internal fun setBirthParameters(parameters : Collection<Int>){
        birthParameters = parameters
    }

    internal fun setGrid(grid : Array<Array<Boolean>>){
        this.grid = grid
    }

    internal fun width() : Int = gridWidth

    internal fun height() : Int = gridHeight

    internal fun aliveColour() : Color = aliveCellColour

    internal fun deadColour() : Color = deadCellColour

    internal fun borderColour() : Color = borderColour

    internal fun createGame() : Game = Game(grid, survivingParameters, birthParameters)

}