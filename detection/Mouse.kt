package detection

import geometry.Point
import main.mainFrame
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

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
         * The mouse's position
         */
        @JvmStatic public fun mousePosition() : Point = mousePosition
    }

    public override fun mouseClicked(e: MouseEvent?) {
        EventHandler.press(component(e!!))
    }

    public override fun mouseReleased(e: MouseEvent?) {
        EventHandler.release(component(e!!))
    }

    public override fun mouseEntered(e: MouseEvent?) {
        EventHandler.enter(component(e!!))
    }

    public override fun mouseExited(e: MouseEvent?) {
        EventHandler.exit(component(e!!))
    }

    public override fun mouseMoved(e: MouseEvent?) {
        mousePosition setx e!!.x
        mousePosition sety e.y
    }

    /**
     * Returns the component at the mouse's position.
     */
    private fun component(e : MouseEvent) : Component = mainFrame.contentPane.getComponentAt(e.x, e.y)

}



