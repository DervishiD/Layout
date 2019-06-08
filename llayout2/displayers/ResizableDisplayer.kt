package llayout2.displayers

/**
 * A Displayer that possesses methods to modify its size.
 * @see Displayer
 * @since LLayout 1
 */
open class ResizableDisplayer : Displayer {

    protected constructor(width : Int, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Double, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Int, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Double, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor() : this(1, 1)

    /**
     * Sets the width of this Displayer, in pixels.
     * @return this
     * @since LLayout 1
     */
    fun setWidth(width : Int) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setWidth(width)
        return this
    }

    /**
     * Sets the width of this Displayer, as a proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun setWidth(width : Double) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setWidth(width)
        return this
    }

    /**
     * Sets the height of this Displayer, in pixels.
     * @return this
     * @since LLayout 1
     */
    fun setHeight(height : Int) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setHeight(height)
        return this
    }

    /**
     * Sets the height of this Displayer, as a proportion of its container's height.
     * @return
     * @since LLayout 1
     */
    fun setHeight(height : Double) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setHeight(height)
        return this
    }

}