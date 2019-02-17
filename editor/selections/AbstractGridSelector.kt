package editor.selections

import geometry.Point

abstract class AbstractGridSelector {

    companion object {
        protected var pressedMouseCell : Point? = null
        protected var releasedMousecell : Point? = null
        protected val selectedSet : HashSet<Point> = HashSet()
        //TODO
    }
}