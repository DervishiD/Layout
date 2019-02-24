package editor.selections

import editor.GridDisplayer
import gamepackage.gamegeometry.Cell
import kotlin.math.max
import kotlin.math.min

class LineSelector(gridDisplayer: GridDisplayer) : LinearSelector(gridDisplayer){

    override fun updateSelection() {
        currentMinIndex = max(0, min(startingLine!!, hoveredLine!!))
        currentMaxIndex = min(gridDisplayer.gridHeight() - 1, max(startingLine!!, hoveredLine!!))
    }

    override fun currentSelection(): HashSet<Cell> {
        val result : HashSet<Cell> = HashSet()
        for(i : Int in previousIndicesSet){
            result.addAll(gridDisplayer.line(i))
        }
        for(i : Int in currentMinIndex!!..currentMaxIndex!!){
            if(!previousIndicesSet.contains(i)){
                result.addAll(gridDisplayer.line(i))
            }
        }
        return result
    }

    override fun cellMatch(line : Int, column : Int): Boolean = (line in currentMinIndex!!..currentMaxIndex!!)

}