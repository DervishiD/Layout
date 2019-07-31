package llayout.utilities

import java.awt.Graphics

/**
 * A generalization of an image as an action executed on a [Graphics] object.
 * @since LLayout 1
 */
typealias GraphicAction = (Graphics, Int, Int) -> Unit

/**
 * Invokes both [GraphicAction]s.
 * @since LLayout 7
 */
operator fun GraphicAction.plus(other : GraphicAction) : GraphicAction = { g : Graphics, w : Int, h : Int ->
    invoke(g, w, h)
    other(g, w, h)
}
