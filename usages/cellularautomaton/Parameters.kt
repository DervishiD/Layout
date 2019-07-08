package usages.cellularautomaton

import java.awt.Color

internal object Parameters {

    private var gridWidth : Int = 0

    private var gridHeight : Int = 0

    private var deadCellColour : Color = Color.WHITE

    private var aliveCellColour : Color = Color.WHITE

    private var borderColour : Color = Color.WHITE

    private var neighbourhood : Collection<Pair<Int, Int>> = setOf()

    private var survivingParameter : Collection<Int> = setOf()

    private var deathParameters : Collection<Int> = setOf()

    private var birthParameters : Collection<Int> = setOf()

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

    internal fun setNeighbourhood(neighbourhood : Collection<Pair<Int, Int>>){
        this.neighbourhood = neighbourhood
    }

    internal fun setSurvivingParameters(parameters : Collection<Int>){
        survivingParameter = parameters
    }

    internal fun setDeathParameters(parameters : Collection<Int>){
        deathParameters = parameters
    }

    internal fun setBirthParameters(parameters : Collection<Int>){
        birthParameters = parameters
    }

}