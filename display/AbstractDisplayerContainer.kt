package display

import geometry.Point
import java.awt.Component

/**
 * An abstract Displayer that implements the CustomContainer interface.
 * @param p The position of this AbstractDisplayerContainer, as a Point.
 * @see Displayer
 * @see CustomContainer
 */
abstract class AbstractDisplayerContainer(p : Point) : Displayer(p), CustomContainer{

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
    fun displayerAt(x : Int, y : Int) : Displayer{
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