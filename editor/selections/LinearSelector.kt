package editor.selections

import editor.GridDisplayer

abstract class LinearSelector(gridDisplayer: GridDisplayer) : AbstractGridSelector(gridDisplayer){

    protected val indicesSet : HashSet<Int> = HashSet()

}