package main

import display.CustomContainer
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
        val component : Component = component(e!!)
        if(component is CustomContainer){
            component.mouseClick(component.getComponentAt(e.x - component.x, e.y - component.y))
        }else{
            ScreenManager.mouseClick(component)
        }
    }

    override fun mousePressed(e: MouseEvent?) {
        val component : Component = component(e!!)
        if(component is CustomContainer){
            component.mousePress(component.getComponentAt(e.x - component.x, e.y - component.y))
        }else{
            ScreenManager.mousePress(component)
        }
    }

    override fun mouseReleased(e: MouseEvent?) {
        val component : Component = component(e!!)
        if(component is CustomContainer){
            component.mouseRelease(component.getComponentAt(e.x - component.x, e.y - component.y))
        }else{
            ScreenManager.mouseRelease(component)
        }
    }

    override fun mouseEntered(e: MouseEvent?) {
        val component : Component = component(e!!)
        if(component is CustomContainer){
            component.mouseEnter(component.getComponentAt(e.x - component.x, e.y - component.y))
        }else{
            ScreenManager.mouseEnter(component)
        }
    }

    override fun mouseExited(e: MouseEvent?) {
        val component : Component = component(e!!)
        if(component is CustomContainer){
            component.mouseExit(component.getComponentAt(e.x - component.x, e.y - component.y))
        }else{
            ScreenManager.mouseExit(component)
        }
    }

    override fun mouseDragged(e: MouseEvent?) {
        val component : Component = component(e!!)
        if(component is CustomContainer){
            component.mouseDrag(component.getComponentAt(e.x - component.x, e.y - component.y))
        }else{
            ScreenManager.mouseDrag(component)
        }
    }

    override fun mouseMoved(e: MouseEvent?) {
        setNewMousePosition(e!!.x, e.y)
        val component : Component = component(e)
        if(component is CustomContainer){
            component.mouseMoved(component.getComponentAt(e.x - component.x, e.y - component.y))
        }else{
            ScreenManager.mouseMoved(component)
        }
    }

    private fun setNewMousePosition(newx : Int, newy : Int){
        displacement setx newx - mousePosition.x
        displacement sety newy - mousePosition.y
        mousePosition setx newx
        mousePosition sety newy
    }

}

/**
 * A custom Mouse wheel listener
 */
class MouseWheel : MouseWheelListener{
    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        val component : Component = component(e!!)
        if(component is CustomContainer){
            component.mouseWheelMoved(component.getComponentAt(e.x - component.x, e.y - component.y), e.wheelRotation)
        }else{
            ScreenManager.mouseWheelMoved(component, e.wheelRotation)
        }
    }
}

/**
 * Returns the component at the mouse's position.
 */
private fun component(e : MouseEvent) : Component = mainFrame.contentPane.getComponentAt(e.x, e.y)


