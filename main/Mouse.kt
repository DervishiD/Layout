package main

import display.ScreenManager
import geometry.Point
import geometry.Vector
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener

/**
 * Mouse Adapter implementation
 */
class Mouse : MouseAdapter() {

    companion object {
        /**
         * The mouse's position.
         */
        @JvmStatic private var mousePosition : Point = Point()

        /**
         * The infinitesimal displacement of the mouse
         */
        @JvmStatic private var displacement : Vector = Vector()

        /**
         * The mouse's position
         */
        @JvmStatic fun mousePosition() : Point = mousePosition

        /**
         * The last displacement of the mouse
         */
        @JvmStatic fun mouseDisplacement() : Vector = displacement
    }

    override fun mouseClicked(e: MouseEvent?) {
        ScreenManager.mouseClick(component(e!!))
    }

    override fun mousePressed(e: MouseEvent?) {
        ScreenManager.mousePress(component(e!!))
    }

    override fun mouseReleased(e: MouseEvent?) {
        ScreenManager.mouseRelease(component(e!!))
    }

    override fun mouseEntered(e: MouseEvent?) {
        ScreenManager.mouseEnter(component(e!!))
    }

    override fun mouseExited(e: MouseEvent?) {
        ScreenManager.mouseExit(component(e!!))
    }

    override fun mouseDragged(e: MouseEvent?) {
        ScreenManager.mouseDrag(component(e!!))
    }

    override fun mouseMoved(e: MouseEvent?) {
        displacement setx e!!.x - mousePosition.x
        displacement sety e.y - mousePosition.y
        mousePosition setx e.x
        mousePosition sety e.y
        ScreenManager.mouseMoved(component(e))
    }

}

class MouseWheel : MouseWheelListener{
    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        ScreenManager.mouseWheelMoved(component(e!!), e.wheelRotation)
    }
}

/**
 * Returns the component at the mouse's position.
 */
private fun component(e : MouseEvent) : Component = mainFrame.contentPane.getComponentAt(e.x, e.y)


