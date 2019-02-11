package main

import display.ScreenManager
import geometry.Point
import geometry.Vector
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

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
        ScreenManager.currentScreen().mouseClick(component(e!!))
    }

    override fun mousePressed(e: MouseEvent?) {
        ScreenManager.currentScreen().mousePress(component(e!!))
    }

    override fun mouseReleased(e: MouseEvent?) {
        ScreenManager.currentScreen().mouseRelease(component(e!!))
    }

    override fun mouseEntered(e: MouseEvent?) {
        ScreenManager.currentScreen().mouseEnter(component(e!!))
    }

    override fun mouseExited(e: MouseEvent?) {
        ScreenManager.currentScreen().mouseExit(component(e!!))
    }

    override fun mouseDragged(e: MouseEvent?) {
        ScreenManager.currentScreen().mouseDrag(component(e!!))
    }

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        //TODO
    }

    override fun mouseMoved(e: MouseEvent?) {
        displacement setx e!!.x - mousePosition.x
        displacement sety e.y - mousePosition.y
        mousePosition setx e.x
        mousePosition sety e.y
    }

    /**
     * Returns the component at the mouse's position.
     */
    private fun component(e : MouseEvent) : Component = mainFrame.contentPane.getComponentAt(e.x, e.y)

}



