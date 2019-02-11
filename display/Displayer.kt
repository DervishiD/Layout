package display

import display.screens.Screen
import geometry.Point
import geometry.Vector
import main.GraphicAction
import java.awt.Graphics
import javax.swing.JLabel

/**
 * Anything that displays anything
 */
abstract class Displayer(p: Point) : JLabel() {

    companion object {
        @JvmStatic val NO_BACKGROUND : GraphicAction = { _, _, _ ->  }
    }

    /**
     * The central Point of the object
     */
    protected var point : Point = p

    /**
     * The width of the component
     */
    protected abstract var w : Int

    /**
     * The height of the component
     */
    protected abstract var h : Int

    /**
     * Detects if the component is being initialized
     */
    protected var initphase : Boolean = true

    /**
     * Left alignment
     */
    private var alignLeftTo : Int? = null
    /**
     * Right alignment
     */
    private var alignRightTo : Int? = null
    /**
     * Up alignment
     */
    private var alignUpTo : Int? = null
    /**
     * Down alignment
     */
    private var alignDownTo : Int? = null

    /**
     * Aligns left to the given position
     */
    open infix fun alignLeftTo(position : Int){
        alignLeftTo = position
        alignRightTo = null
    }

    /**
     * Aligns right to the given position
     */
    open infix fun alignRightTo(position : Int){
        alignRightTo = position
        alignLeftTo = null
    }

    /**
     * Aligns up to the given position
     */
    open infix fun alignUpTo(position : Int){
        alignUpTo = position
        alignDownTo = null
    }

    /**
     * Aligns down to the given position
     */
    open infix fun alignDownTo(position : Int){
        alignDownTo = position
        alignUpTo = null
    }

    /**
     * Aligns left to the given position
     */
    infix fun alignLeftTo(position : Double) = alignLeftTo(position.toInt())

    /**
     * Aligns right to the given position
     */
    infix fun alignRightTo(position : Double) = alignRightTo(position.toInt())

    /**
     * Aligns up to the given position
     */
    infix fun alignUpTo(position : Double) = alignUpTo(position.toInt())

    /**
     * Aligns down to the given position
     */
    infix fun alignDownTo(position : Double) = alignDownTo(position.toInt())

    /**
     * Resets this component's alignment constraints
     */
    private fun resetAlignment(){
        alignLeftTo = null
        alignRightTo = null
        alignUpTo = null
        alignDownTo = null
    }

    /**
     * The center Point
     */
    fun center() : Point = point

    /**
     * The width of the component
     */
    fun width() : Int = w

    /**
     * The height of the component
     */
    fun height() : Int = h

    /**
     * The lowest y coordinate of the component
     */
    fun lowestY() : Int = (point.y - h / 2).toInt()

    /**
     * The highest y coordinate of the component
     */
    fun highestY() : Int = (point.y + h / 2).toInt()

    /**
     * The lowest x coordinate of this component
     */
    fun lowestX() : Int = (point.x - w / 2).toInt()

    /**
     * The highest x coordinate of the component
     */
    fun highestX() : Int = (point.x + w / 2).toInt()

    /**
     * Forces the initphase of this Displayer
     */
    fun initialize(){
        initphase = true
    }

    /**
     * Change this component's center point
     */
    infix fun moveTo(p : Point){
        point = p
        resetAlignment()
        loadBounds()
    }

    /**
     * Change this component's center point.
     */
    fun moveTo(x : Int, y : Int) = this moveTo Point(x, y)

    /**
     * Change this component's center point.
     */
    fun moveTo(x : Double, y : Int) = this moveTo Point(x, y)

    /**
     * Change this component's center point.
     */
    fun moveTo(x : Int, y : Double) = this moveTo Point(x, y)

    /**
     * Change this component's center point.
     */
    fun moveTo(x : Double, y : Double) = this moveTo Point(x, y)

    /**
     * Change this component's x coordinate.
     */
    infix fun setx(x : Int){
        point setx x
        resetAlignment()
        loadBounds()
    }

    /**
     * Change this component's x coordinate.
     */
    infix fun setx(x : Double) = this setx x.toInt()

    /**
     * Change this component's y coordinate.
     */
    infix fun sety(y : Int){
        point setx y
        resetAlignment()
        loadBounds()
    }

    /**
     * Change this component's y coordinate.
     */
    infix fun sety(y : Double) = this sety y.toInt()

    infix fun moveAlong(v : Vector){
        point += v
        resetAlignment()
        loadBounds()
    }

    /**
     * Aligns the component with the alignment constraints
     */
    protected fun align(){
        alignLateral()
        alignVertical()
    }

    /**
     * Aligns the component laterally with the constraints
     */
    private fun alignLateral(){
        if(alignLeftTo != null){
            point setx alignLeftTo!! + w / 2
        }else if(alignRightTo != null){
            point setx alignRightTo!! - w / 2
        }
    }

    /**
     * Aligns the component vertically with the constraints
     */
    private fun alignVertical(){
        if(alignUpTo != null){
            point sety alignUpTo!! + h / 2
        }else if(alignDownTo != null){
            point sety alignDownTo!! - h / 2
        }
    }

    /**
     * Sets this component's required bounds
     */
    private fun loadBounds() = setBounds(point.intx() - w / 2, point.inty() - h / 2, w, h)

    /**
     * A function called when the Displayer is added to a Screen
     */
    internal open fun onAdd(source : Screen){}

    /**
     * A function called when the Displayer is removed from a Screen
     */
    internal open fun onRemove(source : Screen){}

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            loadParameters(g!!)
            align()
            loadBounds()
            initphase = false
        }
        drawDisplayer(g!!)
    }

    /**
     * Loads the necessary parameters during the initialization phase.
     */
    protected abstract fun loadParameters(g : Graphics)

    /**
     * Draws this Displayer
     */
    protected abstract fun drawDisplayer(g : Graphics)

    /**
     * Reacts to a mouse click
     */
    open fun mouseClick(){}

    /**
     * Reacts to a mouse press
     */
    open fun mousePress(){}

    /**
     * Reacts to a mouse release
     */
    open fun mouseRelease(){}

    /**
     * Reacts to the mouse entering
     */
    open fun mouseEnter(){}

    /**
     * Reacts to the mouse exiting
     */
    open fun mouseExit(){}

    /**
     * Reacts to a mouse drag
     */
    open fun mouseDrag(){}

}