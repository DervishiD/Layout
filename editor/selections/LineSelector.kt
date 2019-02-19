package editor.selections

import editor.GridDisplayer
import kotlin.math.max
import kotlin.math.min

class LineSelector(gridDisplayer: GridDisplayer) : LinearSelector(gridDisplayer){

    override fun updateSelection() {
        indicesSet.clear()
        for(i : Int in min(startingLine!!, hoveredLine!!)..max(startingLine!!, hoveredLine!!)){
            indicesSet.add(i)
        }
        updateCurrentSelection()
        updateXORSet()
    }

    private fun updateCurrentSelection(){
        //TODO
    }

    private fun updateXORSet(){
        //TODO
    }

}