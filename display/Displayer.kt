package display

import geometry.Point
import geometry.Vector
import main.Action
import main.GraphicAction
import main.MouseWheelAction
import java.awt.Graphics
import javax.swing.JLabel

/**
 * An alignment constraint to another Displayer
 */
private typealias AlignmentConstraint = Triple<Displayer, Int, AlignmentConstraintsType>

/**
 * The type of an alignment to another Displayer
 */
private enum class AlignmentConstraintsType{
    UP_TO_UP, UP_TO_DOWN,
    LEFT_TO_LEFT, LEFT_TO_RIGHT,
    RIGHT_TO_RIGHT, RIGHT_TO_LEFT,
    DOWN_TO_DOWN, DOWN_TO_UP;
}

/**
 * Anything that displays anything
 */
abstract class Displayer(p: Point) : JLabel(), MouseInteractable {

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
    protected var w : Int = 0

    /**
     * The height of the component
     */
    protected var h : Int = 0

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
     * The component alignment constraints relative to other components
     */
    private var alignmentConstraints : ArrayList<AlignmentConstraint> = ArrayList()

    /**
     * The minimal width of this component
     */
    private var preferredWidth : Int? = null

    /**
     * The minimal height of this component
     */
    private var preferredHeight : Int? = null

    override var onClick : Action = {}
    override var onPress : Action = {}
    override var onRelease : Action = {}
    override var onEnter : Action = {}
    override var onExit : Action = {}
    override var onDrag : Action = {}
    override var onMove : Action = {}
    override var onWheelMoved : MouseWheelAction = {_ -> }

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
     * Aligns the upper part of this component to the upper part of the other with the given delta
     */
    fun alignUpToUp(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.UP_TO_UP))
    }

    /**
     * Aligns the upper part of this component to the lower part of the other with the given delta
     */
    fun alignUpToDown(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.UP_TO_DOWN))
    }

    /**
     * Aligns the leftmost part of this component to the leftmost part of the other with the given delta
     */
    fun alignLeftToLeft(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.LEFT_TO_LEFT))
    }

    /**
     * Aligns the leftmost part of this component to the rightmost part of the other with the given delta
     */
    fun alignLeftToRight(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.LEFT_TO_RIGHT))
    }

    /**
     * Aligns the rightmost part of this component to the leftmost part of the other with the given delta
     */
    fun alignRightToLeft(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.RIGHT_TO_LEFT))
    }

    /**
     * Aligns the rightmost part of this component to the rightmost part of the other with the given delta
     */
    fun alignRightToRight(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.RIGHT_TO_RIGHT))
    }

    /**
     * Aligns the lower part of this component to the lower part of the other with the given delta
     */
    fun alignDownToDown(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.DOWN_TO_DOWN))
    }

    /**
     * Aligns the lower part of this component to the upper part of the other with the given delta
     */
    fun alignDownToUp(component : Displayer, delta : Int = 0){
        alignmentConstraints.add(AlignmentConstraint(component, delta, AlignmentConstraintsType.DOWN_TO_UP))
    }

    /**
     * Resets this component's alignment constraints
     */
    private fun resetAlignment(){
        alignLeftTo = null
        alignRightTo = null
        alignUpTo = null
        alignDownTo = null
        alignmentConstraints.clear()
    }

    /**
     * Sets a preferred width for this Component
     */
    infix fun setPreferredWidth(preferredWidth : Int){
        this.preferredWidth = preferredWidth
    }

    /**
     * Sets a preferred height for this Component
     */
    infix fun setPreferredHeight(preferredHeight : Int){
        this.preferredHeight = preferredHeight
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
    open fun lowestY() : Int = (point.y - h / 2).toInt()

    /**
     * The highest y coordinate of the component
     */
    open fun highestY() : Int = (point.y + h / 2).toInt()

    /**
     * The lowest x coordinate of this component
     */
    open fun lowestX() : Int = (point.x - w / 2).toInt()

    /**
     * The highest x coordinate of the component
     */
    open fun highestX() : Int = (point.x + w / 2).toInt()

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
        performComponentAlignments()
        alignLateral()
        alignVertical()
    }

    /**
     * Aligns the component with the alignment constraints given by the component constraints
     */
    private fun performComponentAlignments(){
        for(constraint : AlignmentConstraint in alignmentConstraints){
            when(constraint.third){
                AlignmentConstraintsType.UP_TO_UP -> this alignUpTo constraint.first.lowestY() + constraint.second
                AlignmentConstraintsType.UP_TO_DOWN -> this alignUpTo constraint.first.highestY() + constraint.second
                AlignmentConstraintsType.LEFT_TO_LEFT -> this alignLeftTo constraint.first.lowestX() + constraint.second
                AlignmentConstraintsType.LEFT_TO_RIGHT -> this alignLeftTo constraint.first.highestX() + constraint.second
                AlignmentConstraintsType.RIGHT_TO_RIGHT -> this alignRightTo constraint.first.highestX() + constraint.second
                AlignmentConstraintsType.RIGHT_TO_LEFT -> this alignRightTo constraint.first.lowestX() + constraint.second
                AlignmentConstraintsType.DOWN_TO_DOWN -> this alignDownTo constraint.first.highestY() + constraint.second
                AlignmentConstraintsType.DOWN_TO_UP -> this alignDownTo constraint.first.lowestY() + constraint.second
            }
        }
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
    internal open fun onAdd(source : CustomContainer){}

    /**
     * A function called when the Displayer is removed from a Screen
     */
    internal open fun onRemove(source : CustomContainer){}

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            loadParameters(g!!)
            applyPreferredSize()
            align()
            loadBounds()
            initphase = false
        }
        align()
        loadBounds()
        drawDisplayer(g!!)
    }

    /**
     * Applies the preferred size constraints
     */
    private fun applyPreferredSize(){
        applyPreferredWidth()
        applyPreferredHeight()
    }

    /**
     * Applies the preferred width constraint
     */
    private fun applyPreferredWidth(){
        if(preferredWidth != null && w < preferredWidth!!){
            w = preferredWidth!!
        }
    }

    /**
     * Applies the preferred height constraint
     */
    private fun applyPreferredHeight(){
        if(preferredHeight != null && h < preferredHeight!!){
            h = preferredHeight!!
        }
    }

    /**
     * Loads the necessary parameters during the initialization phase.
     */
    protected abstract fun loadParameters(g : Graphics)

    /**
     * Draws this Displayer
     */
    protected abstract fun drawDisplayer(g : Graphics)

}