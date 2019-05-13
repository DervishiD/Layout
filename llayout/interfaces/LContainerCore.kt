package llayout.interfaces

interface LContainerCore : HavingDimension {

    /**
     * The Displayers contained in this Container
     * @see Displayable
     */
    val parts : MutableCollection<Displayable>

    /**
     * Forces the initialization of its Displayer components.
     * This should normally not be called by the User.
     * @see Displayable
     * @see Displayable.initialize
     * @see parts
     */
    fun initialization(){
        for(part : Displayable in parts){
            part.initialize()
        }
    }

    /**
     * Reacts to a key pressed event.
     * The default reaction is to do nothing, but the method can be overriden in subclasses.
     * @param key The Java key code of the pressed key.
     */
    fun pressKey(key : Int){}

    /**
     * Reacts to a key released event.
     * The default reaction is to do nothing, but the method can be overriden in subclasses.
     * @param key The Java key code of the released key.
     */
    fun releaseKey(key : Int){}

    /**
     * Reacts to a mouse click event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseClickAt(x : Int, y : Int){}

    /**
     * Reacts to a mouse press event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mousePressAt(x : Int, y : Int){}

    /**
     * Reacts to a mouse release event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseReleaseAt(x : Int, y : Int){}

    /**
     * Reacts to a mouse enter event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseEnterAt(x : Int, y : Int){}

    /**
     * Reacts to a mouse exit event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseExitAt(x : Int, y : Int){}

    /**
     * Reacts to a mouse drag event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseDragAt(x : Int, y : Int){}

    /**
     * Reacts to a mouse moved event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseMovedAt(x : Int, y : Int){}

    /**
     * Reacts to a mouse wheel moved event on this StandardLContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     * @param units The number of units scrolled by the mouse wheel.
     */
    fun mouseWheelMovedAt(x : Int, y : Int, units : Int){}

}