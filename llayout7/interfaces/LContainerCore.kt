package llayout7.interfaces

/**
 * The root of the objects that can contain Displayable.
 * There are no add or remove methods, the implementing class can create them as they will.
 * @see HavingDimension
 * @see StandardLContainer
 * @since LLayout 1
 */
interface LContainerCore : HavingDimension {

    /**
     * The Displayers contained in this Container
     * @see Displayable
     * @since LLayout 1
     */
    val parts : MutableCollection<Displayable>

    /**
     * Forces the initialization of its DisplayerCore components.
     * This should normally not be called by the User.
     * @see Displayable
     * @see Displayable.initialize
     * @see parts
     * @since LLayout 1
     */
    fun initialization(){
        for(part : Displayable in parts){
            part.initialize()
        }
    }

}