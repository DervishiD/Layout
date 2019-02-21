package editor.selections

import editor.GridDisplayer

abstract class LinearSelector(gridDisplayer: GridDisplayer) : AbstractGridSelector(gridDisplayer){

    protected val previousIndicesSet : HashSet<Int> = HashSet()
    protected var currentMinIndex : Int? = null
    protected var currentMaxIndex : Int? = null

}