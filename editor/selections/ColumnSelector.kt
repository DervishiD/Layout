package editor.selections

import editor.GridDisplayer
import gamepackage.gamegeometry.Cell

class ColumnSelector(gridDisplayer: GridDisplayer) : LinearSelector(gridDisplayer) {

    override fun updateSelection() {
        //TODO
    }

    override fun currentSelection(): HashSet<Cell> {
        TODO()
    }

    override fun endCurrentSelection() {
        TODO()
    }

    override fun cellMatch(line : Int, column : Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}