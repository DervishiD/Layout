package display.frame

import geometry.Point
import geometry.Vector

/**
 * A class representing the position and displacement of the mouse.
 */
class LMouse{

    companion object {

        /**
         * The mouse's position.
         */
        private var mousePosition : Point = Point()

        /**
         * The mouse's displacement.
         */
        private var displacement : Vector = Vector()

        /**
         * The mouse's current position.
         * @return The mouse's current position.
         * @see Point
         * @see mousePosition
         */
        fun mousePosition() : Point = mousePosition

        /**
         * The last displacement of the mouse.
         * @return The mouse's last displacement.
         * @see Vector
         * @see displacement
         */
        fun mouseDisplacement() : Vector = displacement

        /**
         * The x coordinate of the mouse.
         * @see mousePosition
         */
        fun mouseX() : Int = mousePosition.intx()

        /**
         * The y coordinate of the mouse.
         * @see mousePosition
         */
        fun mouseY() : Int = mousePosition.inty()

        /**
         * Moves the mouse position at the given coordinates and saves the displacement.
         * @see mousePosition
         * @see mouseDisplacement
         */
        internal fun setNewMousePosition(newx : Int, newy : Int){
            displacement setx newx - mousePosition.x
            displacement sety newy - mousePosition.y
            mousePosition setx newx
            mousePosition sety newy
        }

    }

}
