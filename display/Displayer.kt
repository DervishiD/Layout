package display

import geometry.Point
import geometry.Vector
import geometry.Vector.Companion.NULL
import main.Action
import main.MouseWheelAction
import utilities.LProperty
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
 * @see Screen
 * @see CustomContainer
 * @see MouseInteractable
 * @see Point
 */
abstract class Displayer : JLabel, MouseInteractable, HavingDimension, LTimerUpdatable {

    private var requestCoordinateUpdate : LProperty<Boolean> = LProperty(true)

    protected var absoluteX : LProperty<Int> = LProperty(0)

    protected var absoluteY : LProperty<Int> = LProperty(0)

    protected var relativeX : Double? = null

    protected var relativeY : Double? = null

    /**
     * The width of this Displayer on its parent's graphical context.
     */
    override var w : LProperty<Int> = LProperty(0)

    /**
     * The height of this Displayer on its parent's graphical context.
     */
    override var h : LProperty<Int> = LProperty(0)

    protected var leftSideX : LProperty<Int> = LProperty(0)
    protected var upSideY : LProperty<Int> = LProperty(0)
    protected var rightSideX : LProperty<Int> = LProperty(0)
    protected var downSideY : LProperty<Int> = LProperty(0)

    init{

        fun updateLowestHighestX(){
            leftSideX.value = absoluteX.value -w.value
            rightSideX.value = absoluteX.value +w.value
        }

        fun updateLowestHighestY(){
            upSideY.value = absoluteY.value -h.value
            downSideY.value = absoluteY.value +h.value
        }

        absoluteX.addListener("[VERBOSE INTERNAL LISTENER] -- UPDATE LOWEST/HIGHEST X"){updateLowestHighestX()}
        absoluteY.addListener("[VERBOSE INTERNAL LISTENER] -- UPDATE LOWEST/HIGHEST Y"){updateLowestHighestY()}
        w.addListener("[VERBOSE INTERNAL LISTENER] -- UPDATE LOWEST/HIGHEST X"){updateLowestHighestX()}
        h.addListener("[VERBOSE INTERNAL LISTENER] -- UPDATE LOWEST/HIGHEST Y"){updateLowestHighestY()}
    }

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
    private var absoluteAlignLeftTo : LProperty<Int?> = LProperty(null)

    /**
     * Encodes the coordinate at which the right side of this Displayer is fixed.
     * It is null if the Displayer's right side isn't fixed anywhere.
     * Fixing an alignment of the right side will annihilate any existing alignment of
     * the left side.
     * @see align
     * @see alignLeftTo
     */
    private var absoluteAlignRightTo : LProperty<Int?> = LProperty(null)

    /**
     * Encodes the coordinate at which the up side of this Displayer is fixed.
     * It is null if the Displayer's up side isn't fixed anywhere.
     * Fixing an alignment of the up side will annihilate any existing alignment of
     * the down side.
     * @see align
     * @see alignDownTo
     */
    private var absoluteAlignUpTo : LProperty<Int?> = LProperty(null)

    /**
     * Encodes the coordinate at which the down side of this Displayer is fixed.
     * It is null if the Displayer's down side isn't fixed anywhere.
     * Fixing an alignment of the down side will annihilate any existing alignment of
     * the up side.
     * @see align
     * @see alignDownTo
     */
    private var absoluteAlignDownTo : LProperty<Int?> = LProperty(null)

    private var relativeAlignLeftTo : Double? = null

    private var relativeAlignRightTo : Double? = null

    private var relativeAlignUpTo : Double? = null

    private var relativeAlignDownTo : Double? = null

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

    private var horizontallyAlignedDisplayer : Displayer? = null

    private var verticallyAlignedDisplayer : Displayer? = null

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

    constructor(x : Int, y : Int){
        absoluteX.value = x
        absoluteY.value = y
    }

    constructor(x : Int, y : Double){
        absoluteX.value = x
        relativeY = y
    }

    constructor(x : Double, y : Int){
        relativeX = x
        absoluteY.value = y
    }

    constructor(x : Double, y : Double){
        relativeX = x
        relativeY = y
    }

    constructor(p : Point) : this(p.intx(), p.inty())

    internal open fun updateRelativeValues(frameWidth : Int, frameHeight : Int) : Displayer{
        updateRelativeCoordinates(frameWidth, frameHeight)
        updateRelativeAlignment(frameWidth, frameHeight)
        return this
    }

    private fun updateRelativeCoordinates(frameWidth: Int, frameHeight: Int){
        if(relativeX != null) absoluteX.value = (frameWidth * relativeX!!).toInt()
        if(relativeY != null) absoluteY.value = (frameHeight * relativeY!!).toInt()
    }

    private fun updateRelativeAlignment(frameWidth: Int, frameHeight: Int){
        if(relativeAlignLeftTo != null){
            absoluteAlignLeftTo.value = (frameWidth * relativeAlignLeftTo!!).toInt()
        }else if(relativeAlignRightTo != null){
            absoluteAlignRightTo.value = (frameWidth * relativeAlignRightTo!!).toInt()
        }
        if(relativeAlignUpTo != null){
            absoluteAlignUpTo.value = (frameHeight * relativeAlignUpTo!!).toInt()
        }else if(relativeAlignDownTo != null){
            absoluteAlignDownTo.value = (frameHeight * relativeAlignDownTo!!).toInt()
        }
    }

    internal fun addRequestUpdateListener(key : Any?, action : Action) = requestCoordinateUpdate.addListener(key, action)
    internal fun removeRequestUpdateListener(key : Any?) = requestCoordinateUpdate.removeListener(key)

    protected fun requestCoordinateUpdate(){
        requestCoordinateUpdate.value = !requestCoordinateUpdate.value
    }

    /**
     * Aligns the left side of this Displayer to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this Displayer's left side will be aligned.
     * @see alignLeftTo
     * @see align
     */
    open infix fun alignLeftTo(position : Int) : Displayer{
        absoluteAlignLeftTo.value = position
        absoluteAlignRightTo.value = null
        relativeAlignRightTo = null
        return this
    }

    /**
     * Aligns the right side of this Displayer to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this Displayer's right side will be aligned.
     * @see alignRightTo
     * @see align
     */
    open infix fun alignRightTo(position : Int) : Displayer{
        absoluteAlignRightTo.value = position
        absoluteAlignLeftTo.value = null
        relativeAlignLeftTo = null
        return this
    }

    /**
     * Aligns the up side of this Displayer to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this Displayer's up side will be aligned.
     * @see alignUpTo
     * @see align
     */
    open infix fun alignUpTo(position : Int) : Displayer{
        absoluteAlignUpTo.value = position
        absoluteAlignDownTo.value = null
        relativeAlignDownTo = null
        return this
    }

    /**
     * Aligns the down side of this Displayer to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this Displayer's down side will be aligned.
     * @see alignDownTo
     * @see align
     */
    open infix fun alignDownTo(position : Int) : Displayer{
        absoluteAlignDownTo.value = position
        absoluteAlignUpTo.value = null
        relativeAlignUpTo = null
        return this
    }

    /**
     * Aligns the left side of this Displayer to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this Displayer's left side will be aligned.
     * @see alignLeftTo
     * @see align
     */
    infix fun alignLeftTo(position : Double) : Displayer{
        relativeAlignLeftTo = position
        absoluteAlignRightTo.value = null
        relativeAlignRightTo = null
        requestCoordinateUpdate()
        return this
    }

    /**
     * Aligns the right side of this Displayer to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this Displayer's right side will be aligned.
     * @see alignRightTo
     * @see align
     */
    infix fun alignRightTo(position : Double) : Displayer{
        relativeAlignRightTo = position
        absoluteAlignLeftTo.value = null
        relativeAlignLeftTo = null
        requestCoordinateUpdate()
        return this
    }

    /**
     * Aligns the up side of this Displayer to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this Displayer's up side will be aligned.
     * @see alignUpTo
     * @see align
     */
    infix fun alignUpTo(position : Double) : Displayer{
        relativeAlignUpTo = position
        absoluteAlignDownTo.value = null
        relativeAlignDownTo = null
        requestCoordinateUpdate()
        return this
    }

    /**
     * Aligns the down side of this Displayer to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this Displayer's down side will be aligned.
     * @see alignDownTo
     * @see align
     */
    infix fun alignDownTo(position : Double) : Displayer{
        relativeAlignDownTo = position
        absoluteAlignUpTo.value = null
        relativeAlignUpTo = null
        requestCoordinateUpdate()
        return this
    }

    infix fun alignLeftTo(position : Float) : Displayer = alignLeftTo(position.toDouble())

    infix fun alignRightTo(position : Float) : Displayer = alignRightTo(position.toDouble())

    infix fun alignUpTo(position : Float) : Displayer = alignUpTo(position.toDouble())

    infix fun alignDownTo(position : Float) : Displayer = alignDownTo(position.toDouble())

    /**
     * Aligns the upper part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignUpToUp(component : Displayer, delta : Int = 0) : Displayer {
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.UP_TO_UP)
        return this
    }

    /**
     * Aligns the upper part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignUpToDown(component : Displayer, delta : Int = 0) : Displayer {
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.UP_TO_DOWN)
        return this
    }

    /**
     * Aligns the leftmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignLeftToLeft(component : Displayer, delta : Int = 0) : Displayer{
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.LEFT_TO_LEFT)
        return this
    }

    /**
     * Aligns the leftmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignLeftToRight(component : Displayer, delta : Int = 0) : Displayer{
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.LEFT_TO_RIGHT)
        return this
    }

    /**
     * Aligns the rightmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignRightToLeft(component : Displayer, delta : Int = 0) : Displayer{
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.RIGHT_TO_LEFT)
        return this
    }

    /**
     * Aligns the rightmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignRightToRight(component : Displayer, delta : Int = 0) : Displayer{
        horizontalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.RIGHT_TO_RIGHT)
        return this
    }

    /**
     * Aligns the lower part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignDownToDown(component : Displayer, delta : Int = 0) : Displayer{
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.DOWN_TO_DOWN)
        return this
    }

    /**
     * Aligns the lower part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     */
    fun alignDownToUp(component : Displayer, delta : Int = 0) : Displayer{
        verticalDisplayerAlignment = AlignmentConstraint(component, delta, AlignmentConstraintsType.DOWN_TO_UP)
        return this
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
        resetVerticalCoordinateAlignment()
        resetHorizontalCoordinateAlignment()
    }

    private fun resetVerticalCoordinateAlignment(){
        absoluteAlignUpTo.value = null
        absoluteAlignDownTo.value = null
        relativeAlignUpTo = null
        relativeAlignDownTo = null
    }

    private fun resetHorizontalCoordinateAlignment(){
        absoluteAlignLeftTo.value = null
        absoluteAlignRightTo.value = null
        relativeAlignLeftTo = null
        relativeAlignRightTo = null
    }

    /**
     * Sets a preferred width for this Displayer.
     * A negative parameter will be used in absolute value.
     * @param preferredWidth The preferred width of this Displayer.
     * @see preferredWidth
     */
    infix fun setPreferredWidth(preferredWidth : Int) : Displayer{
        this.preferredWidth = if(preferredWidth < 0) - preferredWidth else preferredWidth
        return this
    }

    /**
     * Sets a preferred height for this Displayer.
     * A negative parameter will be used in absolute value.
     * @param preferredHeight The preferred height of this Displayer.
     * @see preferredHeight
     */
    infix fun setPreferredHeight(preferredHeight : Int) : Displayer{
        this.preferredHeight = if(preferredHeight < 0) - preferredHeight else preferredHeight
        return this
    }

    fun centerX() : Int = absoluteX.value

    fun centerY() : Int = absoluteY.value

    /**
     * The position of this Displayer, i.e. its center Point, as a Point.
     * @return The center Point of this Displayer.
     */
    fun center() : Point = Point(centerX(), centerY())

    /**
     * The upper left cornet of this Displayer, as a Point.
     * @return The upper left corner of this Displayer.
     */
    fun upperLeftCorner() : Point = Point(lowestX(), lowestY())

    /**
     * The upper right cornet of this Displayer, as a Point.
     * @return The upper right corner of this Displayer.
     */
    fun upperRightCorner() : Point = Point(highestX(), lowestY())

    /**
     * The lower left cornet of this Displayer, as a Point.
     * @return The lower left corner of this Displayer.
     */
    fun lowerLeftCorner() : Point = Point(lowestX(), highestY())

    /**
     * The lower right cornet of this Displayer, as a Point.
     * @return The lower right corner of this Displayer.
     */
    fun lowerRightCorner() : Point = Point(highestX(), highestY())

    /**
     * The lowest y coordinate of this Displayer.
     */
    open fun lowestY() : Int = centerY() - height() / 2

    /**
     * The highest y coordinate of this Displayer.
     */
    open fun highestY() : Int = centerY() + height() / 2

    /**
     * The lowest x coordinate of this Displayer.
     */
    open fun lowestX() : Int = centerX() - width() / 2

    /**
     * The highest x coordinate of this Displayer.
     */
    open fun highestX() : Int = centerX() + width() / 2

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
     * @see Point
     * @see align
     */
    infix fun moveTo(p : Point) : Displayer = moveTo(p.intx(), p.inty())

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see align
     */
    fun moveTo(x : Int, y : Int) : Displayer{
        if(x != centerX() || y != centerY()){
            absoluteX.value = x
            absoluteY.value = y
            relativeX = null
            relativeY = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see align
     */
    fun moveTo(x : Double, y : Int) : Displayer{
        if(x != relativeX || y != centerY()){
            relativeX = x
            absoluteY.value = y
            relativeY = null
            resetAlignment()
            requestCoordinateUpdate()
            loadBounds()
        }
        return this
    }

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see align
     */
    fun moveTo(x : Int, y : Double) : Displayer{
        if(x != centerX() || y != relativeY){
            absoluteX.value = x
            relativeY = y
            relativeX = null
            resetAlignment()
            requestCoordinateUpdate()
            loadBounds()
        }
        return this
    }

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @see align
     */
    fun moveTo(x : Double, y : Double) : Displayer{
        if(x != relativeX || y != relativeY){
            relativeX = x
            relativeY = y
            resetAlignment()
            requestCoordinateUpdate()
            loadBounds()
        }
        return this
    }

    /**
     * Change this Displayer's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this Displayer's position.
     * @see align
     */
    infix fun setx(x : Int) : Displayer{
        if(x != centerX()){
            absoluteX.value = x
            relativeX = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Change this Displayer's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this Displayer's position.
     * @see align
     */
    infix fun setx(x : Double) : Displayer{
        if(x != relativeX){
            relativeX = x
            resetAlignment()
            requestCoordinateUpdate()
            loadBounds()
        }
        return this
    }

    infix fun setx(x : Float) = setx(x.toDouble())

    /**
     * Change this Displayer's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this Displayer's position.
     * @see align
     */
    infix fun sety(y : Int) : Displayer{
        if(y != centerY()){
            absoluteY.value = y
            relativeY = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Change this Displayer's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this Displayer's position.
     * @see align
     */
    infix fun sety(y : Double) : Displayer{
        if(y != relativeY){
            relativeY = y
            resetAlignment()
            requestCoordinateUpdate()
            loadBounds()
        }
        return this
    }

    infix fun sety(y : Float) = sety(y.toDouble())

    /**
     * Moves this Displayer along the given Vector.
     * If the given Vector is not NULL, resets all alignment constraints.
     * @param v The Vector along which the movement is executed.
     * @see Vector
     * @see align
     */
    infix fun moveAlong(v : Vector) : Displayer{
        if(v != NULL){
            absoluteX.value += v.x.toInt()
            absoluteY.value += v.y.toInt()
            relativeX = null
            relativeY = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    fun moveAlong(x : Int, y : Int) : Displayer{
        if(x != 0 || y != 0){
            absoluteX.value += x
            absoluteY.value += y
            relativeX = null
            relativeY = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    infix fun moveAlongX(x : Int) : Displayer{
        if(x != 0){
            absoluteX.value += x
            relativeX = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    infix fun moveAlongY(y : Int) : Displayer{
        if(y != 0){
            absoluteY.value += y
            relativeY = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    fun addXListener(key : Any?, action : Action) : Displayer{
        absoluteX.addListener(key, action)
        return this
    }

    fun addYListener(key : Any?, action : Action) : Displayer{
        absoluteY.addListener(key, action)
        return this
    }

    fun addWidthListener(key : Any?, action : Action) : Displayer{
        w.addListener(key, action)
        return this
    }

    fun addHeightListener(key : Any?, action : Action) : Displayer{
        h.addListener(key, action)
        return this
    }

    infix fun removeXListener(key : Any?) : Displayer{
        absoluteX.removeListener(key)
        return this
    }

    infix fun removeYListener(key : Any?) : Displayer{
        absoluteY.removeListener(key)
        return this
    }

    infix fun removeWidthListener(key : Any?) : Displayer{
        w.removeListener(key)
        return this
    }

    infix fun removeHeightListener(key : Any?) : Displayer{
        h.removeListener(key)
        return this
    }

    fun addLeftSideListener(key : Any?, action : Action) : Displayer{
        leftSideX.addListener(key, action)
        return this
    }

    infix fun removeLeftSideListener(key : Any?) : Displayer{
        leftSideX.removeListener(key)
        return this
    }

    fun addRightSideListener(key : Any?, action : Action) : Displayer{
        rightSideX.addListener(key, action)
        return this
    }

    infix fun removeRightSideListener(key : Any?) : Displayer{
        rightSideX.removeListener(key)
        return this
    }

    fun addUpSideListener(key : Any?, action : Action) : Displayer{
        upSideY.addListener(key, action)
        return this
    }

    infix fun removeUpSideListener(key : Any?) : Displayer{
        upSideY.removeListener(key)
        return this
    }

    fun addDownSideListener(key : Any?, action : Action) : Displayer{
        downSideY.addListener(key, action)
        return this
    }

    infix fun removeDownSideListener(key : Any?) : Displayer{
        downSideY.removeListener(key)
        return this
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
        if(absoluteAlignLeftTo.value != null){
            absoluteX.value = absoluteAlignLeftTo.value!! + width() / 2
        }else if(absoluteAlignRightTo.value != null){
            absoluteX.value = absoluteAlignRightTo.value!! - width() / 2
        }
    }

    /**
     * Aligns the component vertically with the coordinate constraints.
     * @see align
     */
    private fun alignVertical(){
        if(absoluteAlignUpTo.value != null){
            absoluteY.value = absoluteAlignUpTo.value!! + height() / 2
        }else if(absoluteAlignDownTo.value != null){
            absoluteY.value = absoluteAlignDownTo.value!! - height() / 2
        }
    }

    /**
     * Sets this component's bounds.
     * @see setBounds
     */
    private fun loadBounds() = setBounds(centerX() - width() / 2, centerY() - height() / 2, width(), height())

    /**
     * A function called when the Displayer is added to a CustomContainer.
     * The default function does nothing but can be overriden in subclasses.
     * @see CustomContainer
     */
    internal open infix fun onAdd(source : CustomContainer){}

    /**
     * A function called when the Displayer is removed from a CustomContainer.
     * The default function does nothing but can be overriden in subclasses.
     * @see CustomContainer
     */
    internal open infix fun onRemove(source : CustomContainer){}

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            requestCoordinateUpdate()
            loadParameters(g!!)
            applyPreferredSize()
            initphase = false
        }
        align()
        loadBounds()
        drawDisplayer(g!!)
    }

    /**
     * Applies the preferred size constraints, i.e. makes sure the width and height
     * are at least preferredWidth and preferredHeight.
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
        if(preferredWidth != null && width() < preferredWidth!!){
            w.value = preferredWidth!!
        }
    }

    /**
     * Applies the preferred height constraint, i.e. makes sure the height is
     * at least preferredHeight.
     * @see preferredHeight
     */
    private fun applyPreferredHeight(){
        if(preferredHeight != null && height() < preferredHeight!!){
            h.value = preferredHeight!!
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