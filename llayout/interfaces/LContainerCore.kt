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

}