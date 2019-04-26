package llayout.displayers

import llayout.interfaces.LContainer
import llayout.geometry.Point
import java.awt.Component

/**
 * An abstract Displayer that implements the LContainer interface.
 * @see Displayer
 * @see LContainer
 */
abstract class AbstractDisplayerContainer : Displayer, LContainer {

    /**
     * The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     */
    private var relativeW : Double? = null

    /**
     * The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    private var relativeH : Double? = null

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param p The center Point of this AbstractDisplayerContainer, as coordinates in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     * @see Point
     */
    constructor(p : Point, width : Int, height : Int) : super(p){
        w.value = width
        h.value = height
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     */
    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param p The center Point of this AbstractDisplayerContainer, as coordinates in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, in pixels.
     * @see Point
     */
    constructor(p : Point, width : Double, height : Int) : super(p){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param p The center Point of this AbstractDisplayerContainer, as coordinates in pixels.
     * @param width The width of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @see Point
     */
    constructor(p : Point, width : Double, height : Double) : super(p){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs an AbstractDisplayerContainer with the given parameters.
     * @param p The center Point of this AbstractDisplayerContainer, as coordinates in pixels.
     * @param width The width of this AbstractDisplayerContainer, in pixels.
     * @param height The height of this AbstractDisplayerContainer, as a proportion of its container's height.
     * @see Point
     */
    constructor(p : Point, width : Int, height : Double) : super(p){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): Displayer {
        if(relativeW != null) w.value = (relativeW!! * frameWidth).toInt()
        if(relativeH != null) h.value = (relativeH!! * frameHeight).toInt()
        return super.updateRelativeValues(frameWidth, frameHeight)
    }

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
        val component : Component = getComponentAt(x, y)
        return when(component){
            is AbstractDisplayerContainer -> {
                if(component == this){
                    this
                }else component.displayerAt(x - component.leftSideX(), y - component.upSideY())
            }
            is Displayer -> component
            else -> throw Exception("Something is really wrong in AbstractDisplayerContainer. The event handling" +
                    " does some weird shit.")
        }
    }

}