package llayout.displayers

/**
 * A Displayer that can contain other Displayers.
 * @see ResizableDisplayer
 * @since LLayout 1
 */
open class DisplayerContainer : ResizableDisplayer {

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    constructor() : super()

    /**
     * Adds the given Displayers to this container.
     * @return this
     * @since LLayout 1
     */
    fun add(vararg components : Displayer) : Displayer{
        for(component : Displayer in components){
            core.add(core(component))
        }
        return this
    }

    /**
     * Adds the given Displayers to this container.
     * @return this
     * @since LLayout 1
     */
    fun add(components : Collection<Displayer>) : Displayer{
        for(component : Displayer in components){
            core.add(core(component))
        }
        return this
    }

    /**
     * Removes the given Displayers from this container
     * @return this
     * @since LLayout 1
     */
    fun remove(vararg components : Displayer) : Displayer{
        for(component : Displayer in components){
            core.remove(core(component))
        }
        return this
    }

    /**
     * Removes the given Dispalyers from this container.
     * @return this
     * @since LLayout 1
     */
    fun remove(components : Collection<Displayer>) : Displayer{
        for(component : Displayer in components){
            core.remove(core(component))
        }
        return this
    }

}