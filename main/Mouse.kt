package main

import display.ScreenManager
import geometry.Point
import geometry.Vector
import java.awt.event.*

/**
 * Mouse Adapter implementation
 */
class Mouse : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent?) = ScreenManager.mouseClick(e!!.x, e.y)

    override fun mousePressed(e: MouseEvent?) = ScreenManager.mousePress(e!!.x, e.y)

    override fun mouseReleased(e: MouseEvent?) = ScreenManager.mouseRelease(e!!.x, e.y)

    override fun mouseEntered(e: MouseEvent?) = ScreenManager.mouseEnter(e!!.x, e.y)

    override fun mouseExited(e: MouseEvent?) = ScreenManager.mouseExit(e!!.x, e.y)

}

/**
 * A custom mouse wheel listener
 */
internal val mouseWheel : MouseWheelListener = MouseWheelListener { e ->
    ScreenManager.mouseWheelMoved(e!!.x, e.y, e.wheelRotation)
}

internal val mouseMotionListener : MouseMotionListener = object : MouseMotionListener{
    override fun mouseMoved(e: MouseEvent?) {
        setNewMousePosition(e!!.x, e.y)
        ScreenManager.mouseMoved(e.x, e.y)
    }

    override fun mouseDragged(e: MouseEvent?) {
        setNewMousePosition(e!!.x, e.y)
        ScreenManager.mouseDrag(e.x, e.y)
    }

}

/**
 * Moves the mouse position at the given coordinates
 */
private fun setNewMousePosition(newx : Int, newy : Int){
    displacement setx newx - mousePosition.x
    displacement sety newy - mousePosition.y
    mousePosition setx newx
    mousePosition sety newy
}

/**
 * The mouse's position.
 */
private var mousePosition : Point = Point()

/**
 * The infinitesimal displacement of the mouse
 */
private var displacement : Vector = Vector()

/**
 * The mouse's position
 */
fun mousePosition() : Point = mousePosition

/**
 * The last displacement of the mouse
 */
fun mouseDisplacement() : Vector = displacement
