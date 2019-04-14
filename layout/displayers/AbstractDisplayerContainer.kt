package layout.displayers

import layout.interfaces.CustomContainer
import layout.geometry.Point
import java.awt.Component

/**
 * An abstract Displayer that implements the CustomContainer interface.
 * @see Displayer
 * @see CustomContainer
 */
abstract class AbstractDisplayerContainer : Displayer, CustomContainer {

    private var relativeW : Double? = null

    private var relativeH : Double? = null

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(p : Point, width : Int, height : Int) : super(p){
        w.value = width
        h.value = height
    }

    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(p : Point, width : Double, height : Int) : super(p){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(p : Point, width : Double, height : Double) : super(p){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

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
                }else component.displayerAt(x - component.lowestX(), y - component.lowestY())
            }
            is Displayer -> component
            else -> throw Exception("Something is really wrong in AbstractDisplayerContainer. The event handling" +
                    " does some weird shit.")
        }
    }

}