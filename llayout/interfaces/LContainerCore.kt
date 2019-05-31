package llayout.interfaces

interface LContainerCore : HavingDimension {

    /**
     * The Displayers contained in this Container
     * @see Displayable
     */
    val parts : MutableCollection<Displayable>

    /**
     * Forces the initialization of its DisplayerCore components.
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

}