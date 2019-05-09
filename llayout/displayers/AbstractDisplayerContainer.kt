package llayout.displayers

import llayout.interfaces.LContainer
import llayout.interfaces.Displayable
import java.awt.Component

/**
 * An abstract Displayer that implements the LContainer interface.
 * @see Displayer
 * @see LContainer
 */
abstract class AbstractDisplayerContainer : ResizableDisplayer, LContainer {

    override val parts: MutableCollection<Displayable> = mutableListOf()

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y, width, height)

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y, width, height)

    override fun mouseClick(x : Int, y : Int){}
    override fun mousePress(x : Int, y : Int){}
    override fun mouseRelease(x : Int, y : Int){}
    override fun mouseEnter(x : Int, y : Int){}
    override fun mouseExit(x : Int, y : Int){}
    override fun mouseDrag(x : Int, y : Int){}
    override fun mouseMoved(x : Int, y : Int){}
    override fun mouseWheelMoved(x : Int, y : Int, units : Int){}

    /**
     * Returns the Displayer at the given coordinates, relative to itself.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The Displayer at the given coordinates, relative to itself.
     * @throws Exception If the coordinates are out of the bounds of this AbstractDisplayerContainer.
     */
    fun displayerAt(x : Int, y : Int) : Displayer {
        return when(val component : Component = getComponentAt(x, y)){
            is AbstractDisplayerContainer -> {
                if(component == this){
                    this
                }else component.displayerAt(x - component.leftSideX(), y - component.upSideY())
            }
            is Displayer -> component
            else -> throw Exception("AbstractDisplayerContainer : Event handling error in method displayerAt(Int, Int) : Displayer.")
        }
    }

}