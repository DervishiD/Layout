package editor.selections

import editor.GridDisplayer
import gamepackage.gamegeometry.Cell

/**
 * A GridSelector is an object capable of selecting Cells in a Grid according to given rules
 */
abstract class AbstractGridSelector(protected val gridDisplayer : GridDisplayer) {

    /**
     * The starting line of the selection
     */
    protected var startingLine : Int? = null
    /**
     * The starting column of the selection
     */
    protected var startingColumn : Int? = null
    /**
     * The ending line of the selection
     */
    protected var hoveredLine : Int? = null
    /**
     * The ending column of the selection
     */
    protected var hoveredColumn : Int? = null
    /**
     * The set of the Cells that are already selected
     */
    protected val previouslySelectedSet : HashSet<Cell> = HashSet()
    /**
     * The set of the Cells that the user is currently selecting
     */
    protected val currentlySelectedSet : HashSet<Cell> = HashSet()
    /**
     * Wait, it doesn't mean anything //TODO
     */
    protected val xorSet : HashSet<Cell> = HashSet()

    fun startNewSelection(line : Int, column : Int){
        startingLine = line
        startingColumn = column
        hoveredLine = startingLine
        hoveredColumn = startingColumn
        updateSelection()
    }

    fun mouseHover(line : Int, column : Int){
        if(line != hoveredLine || column != hoveredColumn){
            hoveredLine = line
            hoveredColumn = column
            updateSelection()
        }
    }

    fun currentSelection() : HashSet<Cell> = xorSet

    abstract fun updateSelection()

}