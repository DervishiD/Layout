package usages.cellularautomaton

import java.awt.Color

internal object Parameters {

    private var gridWidth : Int = 0

    private var gridHeight : Int = 0

    private var deadCellColour : Color = Color.WHITE

    private var aliveCellColour : Color = Color.WHITE

    private var borderColour : Color = Color.WHITE

    private var survivingParameter : Collection<Int> = setOf()

    private var birthParameters : Collection<Int> = setOf()

    private var grid : Array<Array<Boolean>> = Array(1) { Array(1) { true } }

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
        survivingParameter = parameters
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

    internal fun createGame() : Game{
        TODO("Not implemented.")
    }

}