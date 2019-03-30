package display

import geometry.Point
import geometry.Vector
import geometry.Vector.Companion.NULL
import main.Action
import main.MouseWheelAction
import java.awt.Graphics
import javax.swing.JLabel

/**
 * An alignment constraint to another Displayer.
 * It is composed of the other Displayer, an Int representing the distance between the
 * Displayers, in pixels, and an AlignmentConstraintType.
 * @see AlignmentConstraintsType
 * @see Displayer
 * @see Displayer.align
 */
private typealias AlignmentConstraint = Triple<Displayer, Int, AlignmentConstraintsType>

/**
 * The type of an alignment to another Displayer. For example, the UP_TO_DOWN type means
 * that the alignment is between the UP side of this Displayer and the DOWN side of
 * the other one.
 * @see AlignmentConstraint
 * @see Displayer
 * @see Displayer.align
 */
private enum class AlignmentConstraintsType{
    UP_TO_UP, UP_TO_DOWN,
    LEFT_TO_LEFT, LEFT_TO_RIGHT,
    RIGHT_TO_RIGHT, RIGHT_TO_LEFT,
    DOWN_TO_DOWN, DOWN_TO_UP;
}

/**
 * A Displayer is the type of Component that is added on CustomContainer objects.
 * That is, everything that appears on a Screen must be a Displayer.
 * @param p The position of the Displayer on its parent.
 * @see Screen
 * @see CustomContainer
 * @see MouseInteractable
 * @see Point
 */
abstract class Displayer(p: Point) : JLabel(), MouseInteractable {

    /**
     * The position of this Displayer on its parent, as a Point.
     * @see Point
     */
    protected var point : Point = p

    /**
     * The width of this Displayer on its parent's graphical context.
     */
    protected var w : Int = 0

    /**
     * The height of this Displayer on its parent's graphical context.
     */
    protected var h : Int = 0

    /**
     * Encodes if this Displayer is in its initialization phase.
     * The Displayer must be initialized if some of its informations must
     * be calculated the next time it is drawn on its parent's graphical context.
     * @see initialize
     * @see CustomContainer.initialization
     */
    protected var initphase : Boolean = true

    /**
     * Encodes the coordinate at which the left side of this Displayer is fixed.
     * It is null if the Displayer's left side isn't fixed anywhere.
     * Fixing an alignment of the left side will annihilate any existing alignment of
     * the right side.
     * @see align
     * @see alignRightTo
     */
    private var alignLeftTo : Int? = null

    /**
     * Encodes the coordinate at which the right side of this Displayer is fixed.
     * It is null if the Displayer's right side isn't fixed anywhere.
     * Fixing an alignment of the right side will annihilate any existing alignment of
     * the left side.
     * @see align
     * @see alignLeftTo
     */
    private var alignRightTo : Int? = null

    /**
     * Encodes the coordinate at which the up side of this Displayer is fixed.
     * It is null if the Displayer's up side isn't fixed anywhere.
     * Fixing an alignment of the up side will annihilate any existing alignment of
     * the down side.
     * @see align
     * @see alignDownTo
     */
    private var alignUpTo : Int? = null

    /**
     * Encodes the coordinate at which the down side of this Displayer is fixed.
     * It is null if the Displayer's down side isn't fixed anywhere.
     * Fixing an alignment of the down side will annihilate any existing alignment of
     * the up side.
     * @see align
     * @see alignDownTo
     */
    private var alignDownTo : Int? = null

    /**
     * A vertical alignment to another Displayer is encoded in this variable.
     * @see AlignmentConstraint
     * @see AlignmentConstraintsType
     * @see align
     */
    private var verticalDisplayerAlignment : AlignmentConstraint? = null

    /**
     * A horizontal alignment to another Displayer is encoded in this variable.
     * @see AlignmentConstraint
     * @see AlignmentConstraintsType
     * @see align
     */
    private var horizontalDisplayerAlignment : AlignmentConstraint? = null

    /**
     * The minimal width of this component. It is null if there is no minimal width.
     * @see setPreferredWidth
     * @see applyPreferredWidth
     */
    protected var preferredWidth : Int? = null

    /**
     * The minimal height of this component. It is null if there is no minimal height.
     * @see setPreferredHeight
     * @see applyPreferredHeight
     */
    protected var preferredHeight : Int? = null

    override var onClick : Action = {}
    override var onPress : Action = {}
    override var onRelease : Action = {}
    override var onEnter : Action = {}
    override var onExit : Action = {}
    override var onDrag : Action = {}
    override var onMove : Action = {}
    override var onWheelMoved : MouseWheelAction = {_ -> }

    /**
     * Aligns the left side of this Displayer to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this Displayer's left side will be aligned.
     * @see alignLeftTo
     * @see align
     */
    open infix fun alignLeftTo(position : Int){
        alignLeftTo = position
        alignRightTo = null
        if(horizontalDisplayerAlignment?.third == AlignmentConstraintsType.RIGHT_TO_RIGHT
            || horizontalDisplayerAlignment?.third == AlignmentConstraintsType.RIGHT_TO_LEFT){
            horizontalDisplayerAlignment = null
        }
    }

    /**
     * Aligns the right side of this Displayer to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this Displayer's right side will be aligned.
     * @see alignRightTo
     * @see align
     */
    open infix fun alignRightTo(position : Int){
        alignRightTo = position
        alignLeftTo = null
        if(horizontalDisplayerAlignment?.third == AlignmentConstraintsType.LEFT_TO_RIGHT
            || horizontalDisplayerAlignment?.third == AlignmentConstraintsType.LEFT_TO_LEFT){
            horizontalDisplayerAlignment = null
        }
    }

    /**
     * Aligns the up side of this Displayer to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this Displayer's up side will be aligned.
     * @see alignUpTo
     * @see align
     */
    open infix fun alignUpTo(position : Int){
        alignUpTo = position
        alignDownTo = null
        if(verticalDisplayerAlignment?.third == AlignmentConstraintsType.DOWN_TO_DOWN
            || verticalDisplayerAlignment?.third == AlignmentConstraintsType.DOWN_TO_UP){
            verticalDisplayerAlignment = null
        }
    }

    /**
     * Aligns the down side of this Displayer to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this Displayer's down side will be aligned.
     * @see alignDownTo
     * @see align
     */
    open infix fun alignDownTo(position : Int){
        alignDownTo = position
        alignUpTo = null
        if(verticalDisplayerAlignment?.third == AlignmentConstraintsType.UP_TO_DOWN
            || verticalDisplayerAlignment?.third == AlignmentConstraintsType.UP_TO_UP){
            verticalDisplayerAlignment = null
        }
    }

    /**
     * Aligns the left side of this Displayer to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this Displayer's left side will be aligned.
     * @see alignLeftTo
     * @see align
     */
    infix fun alignLeftTo(position : Double) = alignLeftTo(position.toInt())

    /**
     * Aligns the right side of this Displayer to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this Displayer's right side will be aligned.
     * @see alignRightTo
     * @see align
     */
    infix fun alignRightTo(position : Double) = alignRightTo(position.toInt())

    /**
     * Aligns the up side of this Displayer to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this Displayer's up side will be aligned.
     * @see alignUpTo
     * @see align
     */
    infix fun alignUpTo(position : Double) = alignUpTo(position.toInt())

    /**
     * Aligns the down side of this Displayer to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this Displayer's down side will be aligned.
     * @see alignDownTo
     * @see align
     */
    infix fun alignDownTo(position : Double) = alignDownTo(position.toInt())

    /**
     * Aligns the upper part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignUpToUp(component : Displayer, delta : Int = 0){
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.UP_TO_UP)
    }

    /**
     * Aligns the upper part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignUpToDown(component : Displayer, delta : Int = 0){
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.UP_TO_DOWN)
    }

    /**
     * Aligns the leftmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignLeftToLeft(component : Displayer, delta : Int = 0){
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.LEFT_TO_LEFT)
    }

    /**
     * Aligns the leftmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignLeftToRight(component : Displayer, delta : Int = 0){
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.LEFT_TO_RIGHT)
    }

    /**
     * Aligns the rightmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignRightToLeft(component : Displayer, delta : Int = 0){
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.RIGHT_TO_LEFT)
    }

    /**
     * Aligns the rightmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignRightToRight(component : Displayer, delta : Int = 0){
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.RIGHT_TO_RIGHT)
    }

    /**
     * Aligns the lower part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignDownToDown(component : Displayer, delta : Int = 0){
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.DOWN_TO_DOWN)
    }

    /**
     * Aligns the lower part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignDownToUp(component : Displayer, delta : Int = 0){
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.DOWN_TO_UP)
    }

    /**
     * Resets this component's alignment constraints.
     * @see resetCoordinateAlignment
     * @see resetDisplayerAlignment
     */
    private fun resetAlignment(){
        resetDisplayerAlignment()
        resetCoordinateAlignment()
    }

    /**
     * Resets the alignment constraints to other Displayers.
     * @see resetAlignment
     */
    private fun resetDisplayerAlignment(){
        verticalDisplayerAlignment = null
        horizontalDisplayerAlignment = null
    }

    /**
     * Resets the alignment constraints to coordinates.
     * @see resetAlignment
     */
    private fun resetCoordinateAlignment(){
        alignLeftTo = null
        alignRightTo = null
        alignUpTo = null
        alignDownTo = null
    }

    /**
     * Sets a preferred width for this Displayer.
     * A negative parameter will be used in absolute value.
     * @param preferredWidth The preferred width of this Displayer.
     * @see preferredWidth
     */
    infix fun setPreferredWidth(preferredWidth : Int){
        this.preferredWidth = if(preferredWidth < 0) - preferredWidth else preferredWidth
    }

    /**
     * Sets a preferred height for this Displayer.
     * A negative parameter will be used in absolute value.
     * @param preferredHeight The preferred height of this Displayer.
     * @see preferredHeight
     */
    infix fun setPreferredHeight(preferredHeight : Int){
        this.preferredHeight = if(preferredHeight < 0) - preferredHeight else preferredHeight
    }

    /**
     * The position of this Displayer, i.e. its center Point, as a Point.
     * @return The center Point of this Displayer.
     */
    fun center() : Point = point

    /**
     * The upper left cornet of this Displayer, as a Point.
     * @return The upper left corner of this Displayer.
     */
    fun upperLeftCorner() : Point = Point(point.x - w / 2, point.y - h / 2)

    /**
     * The upper right cornet of this Displayer, as a Point.
     * @return The upper right corner of this Displayer.
     */
    fun upperRightCorner() : Point = Point(point.x + w / 2, point.y - h / 2)

    /**
     * The lower left cornet of this Displayer, as a Point.
     * @return The lower left corner of this Displayer.
     */
    fun lowerLeftCorner() : Point = Point(point.x - w / 2, point.y + h / 2)

    /**
     * The lower right cornet of this Displayer, as a Point.
     * @return The lower right corner of this Displayer.
     */
    fun lowerRightCorner() : Point = Point(point.x + w / 2, point.y + h / 2)

    /**
     * This Displayer's width.
     */
    fun width() : Int = w

    /**
     * This Displayer's height.
     */
    fun height() : Int = h

    /**
     * The lowest y coordinate of this Displayer.
     */
    open fun lowestY() : Int = (point.y - h / 2).toInt()

    /**
     * The highest y coordinate of this Displayer.
     */
    open fun highestY() : Int = (point.y + h / 2).toInt()

    /**
     * The lowest x coordinate of this Displayer.
     */
    open fun lowestX() : Int = (point.x - w / 2).toInt()

    /**
     * The highest x coordinate of this Displayer.
     */
    open fun highestX() : Int = (point.x + w / 2).toInt()

    /**
     * Forces the initialization of this Displayer.
     * A Displayer is initialized if it must recalculate some parameters the
     * next time it is drawn.
     * @see initphase
     */
    fun initialize(){
        initphase = true
    }

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param p The new position, i.e. center Point, of this Displayer.
     * @see point
     * @see align
     */
    infix fun moveTo(p : Point){
        if(p != point){
            point = p
            resetAlignment()
            loadBounds()
        }
    }

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see point
     * @see align
     */
    fun moveTo(x : Int, y : Int) = this moveTo Point(x, y)

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see point
     * @see align
     */
    fun moveTo(x : Double, y : Int) = this moveTo Point(x, y)

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see point
     * @see align
     */
    fun moveTo(x : Int, y : Double) = this moveTo Point(x, y)

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see point
     * @see align
     */
    fun moveTo(x : Double, y : Double) = this moveTo Point(x, y)

    /**
     * Change this Displayer's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this Displayer's position.
     * @see point
     * @see align
     */
    infix fun setx(x : Int){
        if(x.toDouble() != point.x){
            point setx x
            resetAlignment()
            loadBounds()
        }
    }

    /**
     * Change this Displayer's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this Displayer's position.
     * @see point
     * @see align
     */
    infix fun setx(x : Double) = this setx x.toInt()

    /**
     * Change this Displayer's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this Displayer's position.
     * @see point
     * @see align
     */
    infix fun sety(y : Int){
        if(y.toDouble() != point.y){
            point setx y
            resetAlignment()
            loadBounds()
        }
    }

    /**
     * Change this Displayer's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this Displayer's position.
     * @see point
     * @see align
     */
    infix fun sety(y : Double) = this sety y.toInt()

    /**
     * Moves this Displayer along the given Vector.
     * If the given Vector is not NULL, resets all alignment constraints.
     * @param v The Vector along which the movement is executed.
     * @see Vector
     * @see point
     * @see align
     */
    infix fun moveAlong(v : Vector){
        if(v != NULL){
            point += v
            resetAlignment()
            loadBounds()
        }
    }

    /**
     * Aligns this Displayer according to its alignment constraints.
     * Any Displayer alignment overrides a coordinate alignment.
     * @see performComponentAlignments
     * @see alignLateral
     * @see alignVertical
     */
    protected fun align(){
        performComponentAlignments()
        alignLateral()
        alignVertical()
    }

    /**
     * Aligns the component with the alignment constraints given by the component constraints.
     * @see AlignmentConstraintsType
     * @see AlignmentConstraint
     * @see verticalDisplayerAlignment
     * @see horizontalDisplayerAlignment
     * @see align
     */
    private fun performComponentAlignments(){
        if(verticalDisplayerAlignment != null){
            when(verticalDisplayerAlignment!!.third){
                AlignmentConstraintsType.UP_TO_UP -> this alignUpTo verticalDisplayerAlignment!!.first.lowestY() + verticalDisplayerAlignment!!.second
                AlignmentConstraintsType.UP_TO_DOWN -> this alignUpTo verticalDisplayerAlignment!!.first.highestY() + verticalDisplayerAlignment!!.second
                AlignmentConstraintsType.DOWN_TO_DOWN -> this alignDownTo verticalDisplayerAlignment!!.first.highestY() + verticalDisplayerAlignment!!.second
                AlignmentConstraintsType.DOWN_TO_UP -> this alignDownTo verticalDisplayerAlignment!!.first.lowestY() + verticalDisplayerAlignment!!.second
                else -> throw Exception("Catastrophic failure : a vertical alignment isn't vertical")
            }
        }

        if(horizontalDisplayerAlignment != null){
            when(horizontalDisplayerAlignment!!.third){
                AlignmentConstraintsType.LEFT_TO_LEFT -> this alignLeftTo horizontalDisplayerAlignment!!.first.lowestX() + horizontalDisplayerAlignment!!.second
                AlignmentConstraintsType.LEFT_TO_RIGHT -> this alignLeftTo horizontalDisplayerAlignment!!.first.highestX() + horizontalDisplayerAlignment!!.second
                AlignmentConstraintsType.RIGHT_TO_RIGHT -> this alignRightTo horizontalDisplayerAlignment!!.first.highestX() + horizontalDisplayerAlignment!!.second
                AlignmentConstraintsType.RIGHT_TO_LEFT -> this alignRightTo horizontalDisplayerAlignment!!.first.lowestX() + horizontalDisplayerAlignment!!.second
                else -> throw Exception("Catastrophic failure : a horizontal alignment isn't horizontal")
            }
        }
    }
    
    /**
     * Aligns the component laterally with the coordinate constraints.
     * @see align
     */
    private fun alignLateral(){
        if(alignLeftTo != null){
            point setx alignLeftTo!! + w / 2
        }else if(alignRightTo != null){
            point setx alignRightTo!! - w / 2
        }
    }

    /**
     * Aligns the component vertically with the coordinate constraints.
     * @see align
     */
    private fun alignVertical(){
        if(alignUpTo != null){
            point sety alignUpTo!! + h / 2
        }else if(alignDownTo != null){
            point sety alignDownTo!! - h / 2
        }
    }

    /**
     * Sets this component's bounds.
     * @see setBounds
     */
    private fun loadBounds() = setBounds(point.intx() - w / 2, point.inty() - h / 2, w, h)

    /**
     * A function called when the Displayer is added to a CustomContainer.
     * The default function does nothing but can be overriden in subclasses.
     * @see CustomContainer
     */
    internal open fun onAdd(source : CustomContainer){}

    /**
     * A function called when the Displayer is removed from a CustomContainer.
     * The default function does nothing but can be overriden in subclasses.
     * @see CustomContainer
     */
    internal open fun onRemove(source : CustomContainer){}

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            loadParameters(g!!)
            applyPreferredSize()
            //align() //Not necessary
            //loadBounds() //Not necessary
            initphase = false
        }
        align()
        loadBounds()
        drawDisplayer(g!!)
    }

    /**
     * Applies the preferred size constraints, i.e. makes sure the width and height
     * are at least preferredWidth and referredHeight.
     * @see preferredWidth
     * @see preferredHeight
     */
    private fun applyPreferredSize(){
        applyPreferredWidth()
        applyPreferredHeight()
    }

    /**
     * Applies the preferred width constraint, i.e. makes sure the width is
     * at least preferredWidth.
     * @see preferredWidth
     */
    private fun applyPreferredWidth(){
        if(preferredWidth != null && w < preferredWidth!!){
            w = preferredWidth!!
        }
    }

    /**
     * Applies the preferred height constraint, i.e. makes sure the height is
     * at least preferredHeight.
     * @see preferredHeight
     */
    private fun applyPreferredHeight(){
        if(preferredHeight != null && h < preferredHeight!!){
            h = preferredHeight!!
        }
    }

    /**
     * Loads the necessary parameters during the initialization phase.
     * @see initphase
     */
    protected abstract fun loadParameters(g : Graphics)

    /**
     * Draws this Displayer on a Graphics context.
     */
    protected abstract fun drawDisplayer(g : Graphics)

}