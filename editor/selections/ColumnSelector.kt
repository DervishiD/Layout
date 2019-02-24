package editor.selections

import editor.GridDisplayer
import gamepackage.gamegeometry.Cell
import kotlin.math.max
import kotlin.math.min

class ColumnSelector(gridDisplayer: GridDisplayer) : LinearSelector(gridDisplayer) {

    override fun updateSelection() {
        currentMinIndex = max(0, min(startingColumn!!, hoveredColumn!!))
        currentMaxIndex = min(gridDisplayer.gridWidth() - 1, max(startingColumn!!, hoveredColumn!!))
    }

    override fun currentSelection(): HashSet<Cell> {
        val result : HashSet<Cell> = HashSet()
        for(i : Int in previousIndicesSet){
            result.addAll(gridDisplayer.column(i))
        }
        for(i : Int in currentMinIndex!!..currentMaxIndex!!){
            if(!previousIndicesSet.contains(i)){
                result.addAll(gridDisplayer.column(i))
            }
        }
        return result
    }

    override fun cellMatch(line : Int, column : Int): Boolean = (column in currentMinIndex!!..currentMaxIndex!!)

}