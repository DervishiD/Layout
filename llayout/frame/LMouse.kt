package llayout.frame

/**
 * A class representing the position and displacement of the mouse.
 */
class LMouse{

    companion object {

        /**
         * The mouse's position.
         */
        private var mouseX : Int = 0

        private var mouseY : Int = 0

        private var mouseDisplacementX : Int = 0

        private var mouseDisplacementY : Int = 0

        /**
         * The x coordinate of the mouse.
         * @see mousePosition
         */
        fun mouseX() : Int = mouseX

        /**
         * The y coordinate of the mouse.
         * @see mousePosition
         */
        fun mouseY() : Int = mouseY

        fun mouseDisplacementX() : Int = mouseDisplacementX

        fun mouseDisplacementY() : Int = mouseDisplacementY

        /**
         * Moves the mouse position at the given coordinates and saves the displacement.
         * @see mousePosition
         * @see mouseDisplacement
         */
        internal fun setNewMousePosition(newx : Int, newy : Int){
            mouseDisplacementX = newx - mouseX
            mouseDisplacementY = newy - mouseY
            mouseX = newx
            mouseY = newy
        }

    }

}
