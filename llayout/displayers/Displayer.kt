package llayout.displayers

import llayout.*
import llayout.frame.LScene
import llayout.geometry.Point
import llayout.geometry.Vector
import llayout.geometry.Vector.Companion.NULL
import llayout.interfaces.LContainer
import llayout.interfaces.HavingDimension
import llayout.interfaces.LTimerUpdatable
import llayout.interfaces.MouseInteractable
import llayout.utilities.LProperty
import java.awt.Graphics
import javax.swing.JLabel

/**
 * A Displayer is the type of Component that is added on LContainer objects.
 * @see LScene
 * @see LContainer
 * @see MouseInteractable
 * @see Point
 */
abstract class Displayer : JLabel, MouseInteractable, HavingDimension, LTimerUpdatable {

    companion object{

        /**
         * A static variable that encodes the index xof the next Displayer.
         * The index uniquely defines a Displayer. It is used to create unique LProperty keys, for example.
         * @see LProperty
         * @see displayerIndex
         */
        @JvmStatic private var staticDisplayerIndex : Long = 0L

    }

    /**
     * A LProperty that is used to force the container to update this Displayer's relative coordinates.
     * @see updateRelativeValues
     * @see LContainer
     */
    private var requestCoordinateUpdate : LProperty<Boolean> = LProperty(true)

    /**
     * The index uniquely defines a Displayer. It is used to create unique LProperty keys, for example.
     * @see LProperty
     * @see staticDisplayerIndex
     */
    private var displayerIndex : Long

    /**
     * The absolute x coordinate of this Displayer.
     * @see LProperty
     */
    protected var absoluteX : LProperty<Int> = LProperty(0)

    /**
     * The absolute y coordinate of this Displayer.
     * @see LProperty
     */
    protected var absoluteY : LProperty<Int> = LProperty(0)

    /**
     * The relative x coordinate of this Displayer, as a proportion of its container's width.
     * @see updateRelativeCoordinates
     * @see absoluteX
     */
    private var relativeX : Double? = null

    /**
     * The relative y coordinate of this Displayer, as a proportion of its container's height.
     * @see updateRelativeCoordinates
     * @see absoluteY
     */
    private var relativeY : Double? = null

    override var w : LProperty<Int> = LProperty(0)

    override var h : LProperty<Int> = LProperty(0)

    /**
     * The x coordinate of the left side of this Displayer.
     * @see LProperty
     */
    private var leftSideX : LProperty<Int> = LProperty(0)

    /**
     * The y coordinate of the upper side of this Displayer.
     * @see LProperty
     */
    private var upSideY : LProperty<Int> = LProperty(0)

    /**
     * The x coordinate of the right side of this Displayer.
     * @see LProperty
     */
    private var rightSideX : LProperty<Int> = LProperty(0)

    /**
     * The y coordinate of the lower side of this Displayer.
     * @see LProperty
     */
    private var downSideY : LProperty<Int> = LProperty(0)

    init{

        /**
         * Updates the leftSideX and rightSideX properties.
         * @see leftSideX
         * @see rightSideX
         */
        fun updateLowestHighestX(){
            leftSideX.value = absoluteX.value -w.value / 2
            rightSideX.value = absoluteX.value +w.value / 2
        }

        /**
         * Updates the upSideY and downSideY properties.
         * @see upSideY
         * @see downSideY
         */
        fun updateLowestHighestY(){
            upSideY.value = absoluteY.value -h.value / 2
            downSideY.value = absoluteY.value +h.value / 2
        }

        /*
         * Adds listeners to the xywh properties, and defines this Displayer's index.
         */

        absoluteX.addListener{updateLowestHighestX()}
        absoluteY.addListener{updateLowestHighestY()}
        w.addListener{updateLowestHighestX()}
        h.addListener{updateLowestHighestY()}
        displayerIndex = staticDisplayerIndex
        staticDisplayerIndex++
    }

    /**
     * Encodes if this Displayer is in its initialization phase.
     * The Displayer must be initialized if some of its information must
     * be calculated the next time it is drawn on its container.
     * @see initialize
     * @see LContainer.initialization
     */
    protected var initphase : Boolean = true

    /**
     * Encodes the coordinate at which the left side of this Displayer is fixed.
     * It is null if the Displayer's left side isn't fixed anywhere.
     * Fixing an alignment of the left side will annihilate any existing alignment of
     * the right side.
     * @see LProperty
     * @see alignLeftTo
     */
    protected var absoluteAlignLeftTo : LProperty<Int?> = LProperty(null)

    /**
     * Encodes the coordinate at which the right side of this Displayer is fixed.
     * It is null if the Displayer's right side isn't fixed anywhere.
     * Fixing an alignment of the right side will annihilate any existing alignment of
     * the left side.
     * @see LProperty
     * @see alignRightTo
     */
    protected var absoluteAlignRightTo : LProperty<Int?> = LProperty(null)

    /**
     * Encodes the coordinate at which the up side of this Displayer is fixed.
     * It is null if the Displayer's up side isn't fixed anywhere.
     * Fixing an alignment of the up side will annihilate any existing alignment of
     * the down side.
     * @see LProperty
     * @see alignUpTo
     */
    protected var absoluteAlignUpTo : LProperty<Int?> = LProperty(null)

    /**
     * Encodes the coordinate at which the down side of this Displayer is fixed.
     * It is null if the Displayer's down side isn't fixed anywhere.
     * Fixing an alignment of the down side will annihilate any existing alignment of
     * the up side.
     * @see LProperty
     * @see alignDownTo
     */
    protected var absoluteAlignDownTo : LProperty<Int?> = LProperty(null)

    init{

        /*
         * Adds listeners to the wh and alignment properties.
         */

        w.addListener{
            if(absoluteAlignLeftTo.value != null){
                alignLeftTo(absoluteAlignLeftTo.value!!)
            }else if(absoluteAlignRightTo.value != null){
                alignRightTo(absoluteAlignRightTo.value!!)
            }
        }
        h.addListener{
            if(absoluteAlignUpTo.value != null){
                alignUpTo(absoluteAlignUpTo.value!!)
            }else if(absoluteAlignDownTo.value != null){
                alignDownTo(absoluteAlignDownTo.value!!)
            }
        }
        absoluteAlignLeftTo.addListener {
            if(absoluteAlignLeftTo.value != null){
                absoluteAlignRightTo.value = null
                relativeAlignRightTo = null
                absoluteX.value = absoluteAlignLeftTo.value!! + width() / 2
            }
        }
        absoluteAlignRightTo.addListener {
            if(absoluteAlignRightTo.value != null){
                absoluteAlignLeftTo.value = null
                relativeAlignLeftTo = null
                absoluteX.value = absoluteAlignRightTo.value!! - width() / 2
            }
        }
        absoluteAlignUpTo.addListener {
            if(absoluteAlignUpTo.value != null){
                absoluteAlignDownTo.value = null
                relativeAlignDownTo = null
                absoluteY.value = absoluteAlignUpTo.value!! + height() / 2
            }
        }
        absoluteAlignDownTo.addListener {
            if(absoluteAlignDownTo.value != null){
                absoluteAlignUpTo.value = null
                relativeAlignUpTo = null
                absoluteY.value = absoluteAlignDownTo.value!! - height() / 2
            }
        }
    }

    /**
     * The relative left alignment of this Displayer, as a proportion of its container's width.
     * @see updateRelativeAlignment
     * @see absoluteAlignLeftTo
     */
    private var relativeAlignLeftTo : Double? = null

    /**
     * The relative right alignment of this Displayer, as a proportion of its container's width.
     * @see updateRelativeAlignment
     * @see absoluteAlignRightTo
     */
    private var relativeAlignRightTo : Double? = null

    /**
     * The relative up alignment of this Displayer, as a proportion of its container's height.
     * @see updateRelativeAlignment
     * @see absoluteAlignUpTo
     */
    private var relativeAlignUpTo : Double? = null

    /**
     * The relative down alignment of this Displayer, as a proportion of its container's height.
     * @see updateRelativeAlignment
     * @see absoluteAlignDownTo
     */
    private var relativeAlignDownTo : Double? = null

    /**
     * The Displayer to which this one is aligned horizontally.
     */
    private var horizontalDisplayerAlignment : Displayer? = null

    /**
     * The Displayer to which this one is aligned vertically.
     */
    private var verticalDisplayerAlignment : Displayer? = null

    /**
     * The Action executed to reset the horizontal Displayer alignment.
     */
    private var resetHorizontalDisplayerAlignment : Action = {}

    /**
     * The Action executed to reset the vertical Dispalyer alignment.
     */
    private var resetVerticalDisplayerAlignment : Action = {}

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
    override var onWheelMoved : MouseWheelAction = { _ -> }

    /**
     * Constructs a Displayer by its coordinates.
     * @param x The x coordinate of this Displayer's center, in pixels.
     * @param y The y coordinate of this Displayer's center, in pixels.
     */
    constructor(x : Int, y : Int){
        absoluteX.value = x
        absoluteY.value = y
    }

    /**
     * Constructs a Displayer by its coordinates.
     * @param x The x coordinate of this Displayer's center, in pixels.
     * @param y The y coordinate of this Displayer's center, as a proportion of its container's height.
     */
    constructor(x : Int, y : Double){
        absoluteX.value = x
        relativeY = y
    }

    /**
     * Constructs a Displayer by its coordinates.
     * @param x The x coordinate of this Displayer's center, as a proportion of its container's width.
     * @param y The y coordinate of this Displayer's center, in pixels.
     */
    constructor(x : Double, y : Int){
        relativeX = x
        absoluteY.value = y
    }

    /**
     * Constructs a Displayer by its coordinates.
     * @param x The x coordinate of this Displayer's center, as a proportion of its container's width.
     * @param y The y coordinate of this Displayer's center, as a proportion of its container's height.
     */
    constructor(x : Double, y : Double){
        relativeX = x
        relativeY = y
    }

    /**
     * Constructs a Displayer by its coordinates.
     * @param p The center point of this Displayer, as coordinates in pixels.
     * @see Point
     */
    constructor(p : Point) : this(p.intx(), p.inty())

    /**
     * Updates the relative values of this Displayer, using its container's width and height.
     * @see updateRelativeAlignment
     * @see updateRelativeCoordinates
     * @return This Displayer
     */
    internal open fun updateRelativeValues(frameWidth : Int, frameHeight : Int) : Displayer {
        updateRelativeCoordinates(frameWidth, frameHeight)
        updateRelativeAlignment(frameWidth, frameHeight)
        return this
    }

    /**
     * Updates the relative coordinates of this Displayer, using its container's width and height.
     * @see relativeX
     * @see relativeY
     */
    private fun updateRelativeCoordinates(frameWidth: Int, frameHeight: Int){
        if(relativeX != null) absoluteX.value = (frameWidth * relativeX!!).toInt()
        if(relativeY != null) absoluteY.value = (frameHeight * relativeY!!).toInt()
    }

    /**
     * Updates the relative alignment of this Displayer, using its container's width and height.
     * @see relativeAlignLeftTo
     * @see relativeAlignRightTo
     * @see relativeAlignUpTo
     * @see relativeAlignDownTo
     */
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

    /**
     * Adds a listener that triggers when this Displayer requests an update.
     * @see requestCoordinateUpdate
     */
    internal fun addRequestUpdateListener(key : Any?, action : Action) = requestCoordinateUpdate.addListener(key, action)

    /**
     * Removes a request update listener.
     * @see addRequestUpdateListener
     */
    internal fun removeRequestUpdateListener(key : Any?) = requestCoordinateUpdate.removeListener(key)

    /**
     * Requests an update of this Displayer's relative values.
     */
    protected fun requestCoordinateUpdate(){
        requestCoordinateUpdate.value = !requestCoordinateUpdate.value
    }

    /**
     * Aligns the left side of this Displayer to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this Displayer's left side will be aligned.
     * @return This Displayer.
     * @see absoluteAlignLeftTo
     */
    open infix fun alignLeftTo(position : Int) : Displayer {
        absoluteAlignLeftTo.value = position
        absoluteX.value = absoluteAlignLeftTo.value!! + width() / 2
        return this
    }

    /**
     * Aligns the right side of this Displayer to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this Displayer's right side will be aligned.
     * @return This Displayer.
     * @see absoluteAlignRightTo
     */
    open infix fun alignRightTo(position : Int) : Displayer {
        absoluteAlignRightTo.value = position
        absoluteX.value = absoluteAlignRightTo.value!! - width() / 2
        return this
    }

    /**
     * Aligns the up side of this Displayer to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this Displayer's up side will be aligned.
     * @return This Displayer.
     * @see absoluteAlignUpTo
     */
    open infix fun alignUpTo(position : Int) : Displayer {
        absoluteAlignUpTo.value = position
        absoluteY.value = absoluteAlignUpTo.value!! + height() / 2
        return this
    }

    /**
     * Aligns the down side of this Displayer to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this Displayer's down side will be aligned.
     * @return This Displayer.
     * @see absoluteAlignDownTo
     */
    open infix fun alignDownTo(position : Int) : Displayer {
        absoluteAlignDownTo.value = position
        absoluteY.value = absoluteAlignDownTo.value!! - height() / 2
        return this
    }

    /**
     * Aligns the left side of this Displayer to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this Displayer's left side will be aligned, as a proportion of its container's width.
     * @return This Displayer.
     * @see relativeAlignLeftTo
     */
    infix fun alignLeftTo(position : Double) : Displayer {
        relativeAlignLeftTo = position
        absoluteAlignRightTo.value = null
        relativeAlignRightTo = null
        requestCoordinateUpdate()
        return this
    }

    /**
     * Aligns the right side of this Displayer to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this Displayer's right side will be aligned, as a proportion of its container's width.
     * @return This Displayer.
     * @see relativeAlignRightTo
     */
    infix fun alignRightTo(position : Double) : Displayer {
        relativeAlignRightTo = position
        absoluteAlignLeftTo.value = null
        relativeAlignLeftTo = null
        requestCoordinateUpdate()
        return this
    }

    /**
     * Aligns the up side of this Displayer to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this Displayer's up side will be aligned, as a proportion of its container's height.
     * @return This Displayer.
     * @see relativeAlignUpTo
     */
    infix fun alignUpTo(position : Double) : Displayer {
        relativeAlignUpTo = position
        absoluteAlignDownTo.value = null
        relativeAlignDownTo = null
        requestCoordinateUpdate()
        return this
    }

    /**
     * Aligns the down side of this Displayer to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this Displayer's down side will be aligned, as a proportion of its container's height.
     * @return This Displayer.
     * @see relativeAlignDownTo
     */
    infix fun alignDownTo(position : Double) : Displayer {
        relativeAlignDownTo = position
        absoluteAlignUpTo.value = null
        relativeAlignUpTo = null
        requestCoordinateUpdate()
        return this
    }

    /**
     * Aligns the left side of this Displayer to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this Displayer's left side will be aligned, as a proportion of its container's width.
     * @return This Displayer.
     * @see relativeAlignLeftTo
     */
    infix fun alignLeftTo(position : Float) : Displayer = alignLeftTo(position.toDouble())

    /**
     * Aligns the right side of this Displayer to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this Displayer's right side will be aligned, as a proportion of its container's width.
     * @return This Displayer.
     * @see relativeAlignRightTo
     */
    infix fun alignRightTo(position : Float) : Displayer = alignRightTo(position.toDouble())

    /**
     * Aligns the up side of this Displayer to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this Displayer's up side will be aligned, as a proportion of its container's height.
     * @return This Displayer.
     * @see relativeAlignUpTo
     */
    infix fun alignUpTo(position : Float) : Displayer = alignUpTo(position.toDouble())

    /**
     * Aligns the down side of this Displayer to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this Displayer's down side will be aligned, as a proportion of its container's height.
     * @return This Displayer.
     * @see relativeAlignDownTo
     */
    infix fun alignDownTo(position : Float) : Displayer = alignDownTo(position.toDouble())

    /**
     * Aligns the upper part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see verticalDisplayerAlignment
     */
    fun alignUpToUp(component : Displayer, delta : Int = 0) : Displayer {
        val key = "UP TO UP DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignUpTo(component.upSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeUpSideListener(key)
        }
        verticalDisplayerAlignment = component.addUpSideListener(key){ alignUpTo(component.upSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeUpSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
        return this
    }

    /**
     * Aligns the upper part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see verticalDisplayerAlignment
     */
    fun alignUpToDown(component : Displayer, delta : Int = 0) : Displayer {
        val key = "UP TO DOWN DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignUpTo(component.downSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeDownSideListener(key)
        }
        verticalDisplayerAlignment = component.addDownSideListener(key){ alignUpTo(component.downSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeDownSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
        return this
    }

    /**
     * Aligns the leftmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see horizontalDisplayerAlignment
     */
    fun alignLeftToLeft(component : Displayer, delta : Int = 0) : Displayer {
        val key = "LEFT TO LEFT DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignLeftTo(component.leftSideX() + delta)
        if(horizontalDisplayerAlignment != null){
            horizontalDisplayerAlignment!!.removeLeftSideListener(key)
        }
        horizontalDisplayerAlignment = component.addLeftSideListener(key){ alignLeftTo(component.leftSideX() + delta) }
        resetHorizontalDisplayerAlignment = {
            if(horizontalDisplayerAlignment != null){
                horizontalDisplayerAlignment!!.removeLeftSideListener(key)
                horizontalDisplayerAlignment = null
            }
        }
        return this
    }

    /**
     * Aligns the leftmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see horizontalDisplayerAlignment
     */
    fun alignLeftToRight(component : Displayer, delta : Int = 0) : Displayer {
        val key = "LEFT TO RIGHT DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignLeftTo(component.rightSideX() + delta)
        if(horizontalDisplayerAlignment != null){
            horizontalDisplayerAlignment!!.removeRightSideListener(key)
        }
        horizontalDisplayerAlignment = component.addRightSideListener(key){ alignLeftTo(component.rightSideX() + delta) }
        resetHorizontalDisplayerAlignment = {
            if(horizontalDisplayerAlignment != null){
                horizontalDisplayerAlignment!!.removeRightSideListener(key)
                horizontalDisplayerAlignment = null
            }
        }
        return this
    }

    /**
     * Aligns the rightmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see horizontalDisplayerAlignment
     */
    fun alignRightToLeft(component : Displayer, delta : Int = 0) : Displayer {
        val key = "RIGHT TO LEFT DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignRightTo(component.leftSideX() + delta)
        if(horizontalDisplayerAlignment != null){
            horizontalDisplayerAlignment!!.removeLeftSideListener(key)
        }
        horizontalDisplayerAlignment = component.addLeftSideListener(key){ alignRightTo(component.leftSideX() + delta) }
        resetHorizontalDisplayerAlignment = {
            if(horizontalDisplayerAlignment != null){
                horizontalDisplayerAlignment!!.removeLeftSideListener(key)
                horizontalDisplayerAlignment = null
            }
        }
        return this
    }

    /**
     * Aligns the rightmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see horizontalDisplayerAlignment
     */
    fun alignRightToRight(component : Displayer, delta : Int = 0) : Displayer {
        val key = "RIGHT TO RIGHT DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignRightTo(component.rightSideX() + delta)
        if(horizontalDisplayerAlignment != null){
            horizontalDisplayerAlignment!!.removeRightSideListener(key)
        }
        horizontalDisplayerAlignment = component.addRightSideListener(key){ alignRightTo(component.rightSideX() + delta) }
        resetHorizontalDisplayerAlignment = {
            if(horizontalDisplayerAlignment != null){
                horizontalDisplayerAlignment!!.removeRightSideListener(key)
                horizontalDisplayerAlignment = null
            }
        }
        return this
    }

    /**
     * Aligns the lower part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see verticalDisplayerAlignment
     */
    fun alignDownToDown(component : Displayer, delta : Int = 0) : Displayer {
        val key = "DOWN TO DOWN DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignDownTo(component.downSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeDownSideListener(key)
        }
        verticalDisplayerAlignment = component.addDownSideListener(key){ alignDownTo(component.downSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeDownSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
        return this
    }

    /**
     * Aligns the lower part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The Displayer to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This Displayer.
     * @see verticalDisplayerAlignment
     */
    fun alignDownToUp(component : Displayer, delta : Int = 0) : Displayer {
        val key = "DOWN TO UP DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignDownTo(component.upSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeUpSideListener(key)
        }
        verticalDisplayerAlignment = component.addUpSideListener(key){ alignDownTo(component.upSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeUpSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
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
     * @see resetHorizontalDisplayerAlignment
     * @see resetVerticalDisplayerAlignment
     */
    private fun resetDisplayerAlignment(){
        resetHorizontalDisplayerAlignment.invoke()
        resetVerticalDisplayerAlignment.invoke()
    }

    /**
     * Resets the alignment constraints to coordinates.
     * @see resetAlignment
     * @see resetHorizontalCoordinateAlignment
     * @see resetVerticalCoordinateAlignment
     */
    private fun resetCoordinateAlignment(){
        resetVerticalCoordinateAlignment()
        resetHorizontalCoordinateAlignment()
    }

    /**
     * Resets the vertical coordinate alignments of this Displayer.
     * @see absoluteAlignUpTo
     * @see absoluteAlignDownTo
     * @see relativeAlignUpTo
     * @see relativeAlignDownTo
     * @see resetCoordinateAlignment
     */
    private fun resetVerticalCoordinateAlignment(){
        absoluteAlignUpTo.value = null
        absoluteAlignDownTo.value = null
        relativeAlignUpTo = null
        relativeAlignDownTo = null
    }

    /**
     * Resets the horizontal coordinate alignments of this Displayer.
     * @see absoluteAlignLeftTo
     * @see absoluteAlignRightTo
     * @see relativeAlignLeftTo
     * @see relativeAlignRightTo
     * @see resetCoordinateAlignment
     */
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
     * @return This Displayer.
     * @see preferredWidth
     */
    infix fun setPreferredWidth(preferredWidth : Int) : Displayer {
        this.preferredWidth = if(preferredWidth < 0) - preferredWidth else preferredWidth
        return this
    }

    /**
     * Sets a preferred height for this Displayer.
     * A negative parameter will be used in absolute value.
     * @param preferredHeight The preferred height of this Displayer.
     * @return This Displayer.
     * @see preferredHeight
     */
    infix fun setPreferredHeight(preferredHeight : Int) : Displayer {
        this.preferredHeight = if(preferredHeight < 0) - preferredHeight else preferredHeight
        return this
    }

    /**
     * The x coordinate of this Displayer's center.
     * @return The x coordinate of this Dispalyer's center.
     * @see absoluteX
     */
    fun centerX() : Int = absoluteX.value

    /**
     * The y coordinate of this Displayer's center.
     * @return The y coordinate of this Displayer's center.
     * @see absoluteY
     */
    fun centerY() : Int = absoluteY.value

    /**
     * The center Point of this Displayer.
     * @return The center Point of this Displayer.
     * @see Point
     * @see centerX
     * @see centerY
     */
    fun center() : Point = Point(centerX(), centerY())

    /**
     * The upper left corner of this Displayer, as a Point.
     * @return The upper left corner of this Displayer.
     * @see leftSideX
     * @see upSideY
     */
    fun upperLeftCorner() : Point = Point(leftSideX(), upSideY())

    /**
     * The upper right corner of this Displayer, as a Point.
     * @return The upper right corner of this Displayer.
     * @see rightSideX
     * @see upSideY
     */
    fun upperRightCorner() : Point = Point(rightSideX(), upSideY())

    /**
     * The lower left corner of this Displayer, as a Point.
     * @return The lower left corner of this Displayer.
     * @see leftSideX
     * @see downSideY
     */
    fun lowerLeftCorner() : Point = Point(leftSideX(), downSideY())

    /**
     * The lower right corner of this Displayer, as a Point.
     * @return The lower right corner of this Displayer.
     * @see rightSideX
     * @see downSideY
     */
    fun lowerRightCorner() : Point = Point(rightSideX(), downSideY())

    /**
     * The y coordinate of the upper side of this Displayer.
     * @return The y coordinate of the upper side of this Displayer.
     */
    open fun upSideY() : Int = upSideY.value

    /**
     * The y coordinate of the lower side of this Displayer.
     * @return The y coordinate of the lower side of this Displayer.
     */
    open fun downSideY() : Int = downSideY.value

    /**
     * The x coordinate of the left side of this Displayer.
     * @return The x coordinate of the left side of this Displayer.
     */
    open fun leftSideX() : Int = leftSideX.value

    /**
     * The x coordinate of the right side of this Displayer.
     * @return The x coordinate of the right side of this Displayer.
     */
    open fun rightSideX() : Int = rightSideX.value

    /**
     * Initializes this Displayer.
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
     * @return This Displayer.
     * @see Point
     * @see resetAlignment
     */
    infix fun moveTo(p : Point) : Displayer = moveTo(p.intx(), p.inty())

    /**
     * Change this Displayer's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this Displayer.
     * @param y The y coordinate of the center of this Displayer.
     * @return This Displayer.
     * @see resetAlignment
     */
    fun moveTo(x : Int, y : Int) : Displayer {
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
     * @return This Displayer.
     * @see resetAlignment
     */
    fun moveTo(x : Double, y : Int) : Displayer {
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
     * @return This Displayer.
     * @see resetAlignment
     */
    fun moveTo(x : Int, y : Double) : Displayer {
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
     * @return This Displayer.
     * @see resetAlignment
     */
    fun moveTo(x : Double, y : Double) : Displayer {
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
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun setx(x : Int) : Displayer {
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
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun setx(x : Double) : Displayer {
        if(x != relativeX){
            relativeX = x
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
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun setx(x : Float) : Displayer = setx(x.toDouble())

    /**
     * Change this Displayer's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this Displayer's position.
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun sety(y : Int) : Displayer {
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
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun sety(y : Double) : Displayer {
        if(y != relativeY){
            relativeY = y
            resetAlignment()
            requestCoordinateUpdate()
            loadBounds()
        }
        return this
    }

    /**
     * Change this Displayer's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this Displayer's position.
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun sety(y : Float) = sety(y.toDouble())

    /**
     * Moves this Displayer along the given Vector.
     * If the given Vector is not NULL, resets all alignment constraints.
     * @param v The Vector along which the movement is executed.
     * @return This Displayer.
     * @see resetAlignment
     * @see Vector
     */
    infix fun moveAlong(v : Vector) : Displayer {
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

    /**
     * Moves this Displayer along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param x The displacement in x, in pixels.
     * @param y The displacement in y, in pixels.
     * @return This Displayer.
     * @see resetAlignment
     */
    fun moveAlong(x : Int, y : Int) : Displayer {
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

    /**
     * Moves this Displayer along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param x The displacement in x, in pixels.
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun moveAlongX(x : Int) : Displayer {
        if(x != 0){
            absoluteX.value += x
            relativeX = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Moves this Displayer along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param y The displacement in y, in pixels.
     * @return This Displayer.
     * @see resetAlignment
     */
    infix fun moveAlongY(y : Int) : Displayer {
        if(y != 0){
            absoluteY.value += y
            relativeY = null
            resetAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see absoluteX
     * @see Action
     */
    fun addXListener(key : Any?, action : Action) : Displayer {
        absoluteX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see absoluteY
     * @see Action
     */
    fun addYListener(key : Any?, action : Action) : Displayer {
        absoluteY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the width of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see w
     * @see Action
     */
    fun addWidthListener(key : Any?, action : Action) : Displayer {
        w.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the height of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see h
     * @see Action
     */
    fun addHeightListener(key : Any?, action : Action) : Displayer {
        h.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see absoluteX
     * @see Action
     */
    infix fun addXListener(action : Action) : Displayer = addXListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see absoluteY
     * @see Action
     */
    infix fun addYListener(action : Action) : Displayer = addYListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the width of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see w
     * @see Action
     */
    infix fun addWidthListener(action : Action) : Displayer = addWidthListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the height of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This Displayer.
     * @see h
     * @see Action
     */
    infix fun addHeightListener(action : Action) : Displayer = addHeightListener(action, action)

    /**
     * Removes a listener of the x coordinate of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addXListener
     */
    infix fun removeXListener(key : Any?) : Displayer {
        absoluteX.removeListener(key)
        return this
    }

    /**
     * Removes a listener of the y coordinate of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addYListener
     */
    infix fun removeYListener(key : Any?) : Displayer {
        absoluteY.removeListener(key)
        return this
    }

    /**
     * Removes a listener of the width of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addWidthListener
     */
    infix fun removeWidthListener(key : Any?) : Displayer {
        w.removeListener(key)
        return this
    }

    /**
     * Removes a listener of the height of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addHeightListener
     */
    infix fun removeHeightListener(key : Any?) : Displayer {
        h.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the left side of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This Displayer.
     * @see leftSideX
     * @see Action
     */
    fun addLeftSideListener(key : Any?, action : Action) : Displayer {
        leftSideX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the left side of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This Displayer.
     * @see leftSideX
     * @see Action
     */
    infix fun addLeftSideListener(action : Action) : Displayer = addLeftSideListener(action, action)

    /**
     * Removes a listener of the x coordinate of the left side of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addLeftSideListener
     */
    infix fun removeLeftSideListener(key : Any?) : Displayer {
        leftSideX.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the right side of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This Displayer.
     * @see rightSideX
     * @see Action
     */
    fun addRightSideListener(key : Any?, action : Action) : Displayer {
        rightSideX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the right side of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This Displayer.
     * @see rightSideX
     * @see Action
     */
    infix fun addRightSideListener(action : Action) : Displayer = addRightSideListener(action, action)

    /**
     * Removes a listener of the x coordinate of the right side of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addRightSideListener
     */
    infix fun removeRightSideListener(key : Any?) : Displayer {
        rightSideX.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the up side of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This Displayer.
     * @see upSideY
     * @see Action
     */
    fun addUpSideListener(key : Any?, action : Action) : Displayer {
        upSideY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the up side of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This Displayer.
     * @see upSideY
     * @see Action
     */
    infix fun addUpSideListener(action : Action) : Displayer = addUpSideListener(action, action)

    /**
     * Removes a listener of the y coordinate of the up side of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addUpSideListener
     */
    infix fun removeUpSideListener(key : Any?) : Displayer {
        upSideY.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the down side of this Displayer.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This Displayer.
     * @see downSideY
     * @see Action
     */
    fun addDownSideListener(key : Any?, action : Action) : Displayer {
        downSideY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the down side of this Displayer.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This Displayer.
     * @see downSideY
     * @see Action
     */
    infix fun addDownSideListener(action : Action) : Displayer = addDownSideListener(action, action)

    /**
     * Removes a listener of the y coordinate of the down side of this Displayer.
     * @param key The key that identifies the listener.
     * @return This Displayer.
     * @see addDownSideListener
     */
    infix fun removeDownSideListener(key : Any?) : Displayer {
        downSideY.removeListener(key)
        return this
    }

    /**
     * Sets this component's bounds.
     * @see setBounds
     */
    private fun loadBounds() = setBounds(centerX() - width() / 2, centerY() - height() / 2, width(), height())

    /**
     * A function called when the Displayer is added to a LContainer.
     * The default function does nothing but can be overriden in subclasses.
     * @see LContainer
     */
    internal open infix fun onAdd(source : LContainer){}

    /**
     * A function called when the Displayer is removed from a LContainer.
     * The default function does nothing but can be overriden in subclasses.
     * @see LContainer
     */
    internal open infix fun onRemove(source : LContainer){}

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            requestCoordinateUpdate()
            loadParameters(g!!)
            applyPreferredSize()
            initphase = false
        }
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