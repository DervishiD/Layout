package llayout5.displayers.cores

import llayout5.*
import llayout5.interfaces.*
import llayout5.utilities.LObservable
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JLabel

/**
 * DisplayerCore is the core component of the LLayout. It extends JLabel to be displayed on a screen, and Displayable to
 * be displayed as an object used by LLayout.
 * Classes that inherit DisplayerCore are other core classes.
 * The classes that the user uses (i.e. the non-core classes) wrap core classes.
 * The core classes should not be inherited by custom classes.
 * @see JLabel
 * @see Displayable
 * @since LLayout 1
 */
abstract class DisplayerCore : JLabel, Displayable {

    companion object{

        /**
         * A static variable that encodes the index of the next DisplayerCore created.
         * The index uniquely identifies a DisplayerCore. It is used to create unique LObservable keys, for example.
         * @see LObservable
         * @see displayerIndex
         * @since LLayout 1
         */
        @JvmStatic private var staticDisplayerIndex : Int = Int.MIN_VALUE

        /**
         * Returns the Displayer Index of the next DisplayerCore and updates the next value.
         * @see staticDisplayerIndex
         * @since LLayout 1
         */
        private fun staticDisplayerIndex() : Int = staticDisplayerIndex++

    }

    /**
     * The index uniquely identifies a DisplayerCore. It is used to create unique LObservable keys, for example.
     * @see LObservable
     * @see staticDisplayerIndex
     * @since LLayout 1
     */
    private var displayerIndex : Int

    /**
     * The absolute x coordinate of this DisplayerCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var absoluteX : LObservable<Int> = LObservable(0)

    /**
     * The absolute y coordinate of this DisplayerCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var absoluteY : LObservable<Int> = LObservable(0)

    /**
     * The relative x coordinate of this DisplayerCore, as a proportion of its container's width.
     * @see updateRelativeCoordinates
     * @see absoluteX
     * @since LLayout 1
     */
    private var relativeX : Double? = null

    /**
     * The relative y coordinate of this DisplayerCore, as a proportion of its container's height.
     * @see updateRelativeCoordinates
     * @see absoluteY
     * @since LLayout 1
     */
    private var relativeY : Double? = null

    /**
     * The width of this DisplayerCore, in pixels, as a LObservable.
     * @see LObservable
     * @since LLayout 1
     */
    protected var w : LObservable<Int> = LObservable(0)

    /**
     * The height of this DisplayerCore, in pixels, as a LObservable.
     * @see LObservable
     * @since LLayout 1
     */
    protected var h : LObservable<Int> = LObservable(0)

    /**
     * The x coordinate of the left side of this DisplayerCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var leftSideX : LObservable<Int> = LObservable(0)

    /**
     * The y coordinate of the upper side of this DisplayerCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var upSideY : LObservable<Int> = LObservable(0)

    /**
     * The x coordinate of the right side of this DisplayerCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var rightSideX : LObservable<Int> = LObservable(0)

    /**
     * The y coordinate of the lower side of this DisplayerCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var downSideY : LObservable<Int> = LObservable(0)

    init{

        /**
         * Updates the leftSideX and rightSideX properties as functions of the position and dimensions of this DisplayerCore.
         * @see leftSideX
         * @see rightSideX
         * @see absoluteX
         * @see w
         * @since LLayout 1
         */
        fun updateLowestHighestX(){
            leftSideX.value = absoluteX.value -w.value / 2
            rightSideX.value = absoluteX.value +w.value / 2
        }

        /**
         * Updates the upSideY and downSideY properties as functions of the position and dimensions of this DisplayerCore.
         * @see upSideY
         * @see downSideY
         * @see absoluteY
         * @see h
         * @since LLayout 1
         */
        fun updateLowestHighestY(){
            upSideY.value = absoluteY.value -h.value / 2
            downSideY.value = absoluteY.value +h.value / 2
        }

        /*
         * Adds listeners to the x, y, w, h properties, and defines this DisplayerCore's index.
         */
        absoluteX.addListener{updateLowestHighestX()}
        absoluteY.addListener{updateLowestHighestY()}
        w.addListener{updateLowestHighestX()}
        h.addListener{updateLowestHighestY()}
        displayerIndex = staticDisplayerIndex()
    }

    /**
     * Encodes if this DisplayerCore is in its initialization phase.
     * The DisplayerCore must be initialized if some of its information must
     * be calculated the next time it is drawn on its container.
     * @see initialize
     * @see StandardLContainer.initialization
     * @since LLayout 1
     */
    private var initphase : Boolean = true

    /**
     * Encodes the coordinate at which the left side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's left side isn't fixed anywhere.
     * Fixing an alignment of the left side will annihilate any existing alignment of
     * the right side.
     * @see LObservable
     * @see alignLeftTo
     * @since LLayout 1
     */
    private var absoluteAlignLeftTo : LObservable<Int?> = LObservable(null)

    /**
     * Encodes the coordinate at which the right side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's right side isn't fixed anywhere.
     * Fixing an alignment of the right side will annihilate any existing alignment of
     * the left side.
     * @see LObservable
     * @see alignRightTo
     * @since LLayout 1
     */
    private var absoluteAlignRightTo : LObservable<Int?> = LObservable(null)

    /**
     * Encodes the coordinate at which the up side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's up side isn't fixed anywhere.
     * Fixing an alignment of the up side will annihilate any existing alignment of
     * the down side.
     * @see LObservable
     * @see alignTopTo
     * @since LLayout 1
     */
    private var absolutealignTopTo : LObservable<Int?> = LObservable(null)

    /**
     * Encodes the coordinate at which the down side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's down side isn't fixed anywhere.
     * Fixing an alignment of the down side will annihilate any existing alignment of
     * the up side.
     * @see LObservable
     * @see alignBottomTo
     * @since LLayout 1
     */
    private var absolutealignBottomTo : LObservable<Int?> = LObservable(null)

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
            if(absolutealignTopTo.value != null){
                alignTopTo(absolutealignTopTo.value!!)
            }else if(absolutealignBottomTo.value != null){
                alignBottomTo(absolutealignBottomTo.value!!)
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
        absolutealignTopTo.addListener {
            if(absolutealignTopTo.value != null){
                absolutealignBottomTo.value = null
                relativealignBottomTo = null
                absoluteY.value = absolutealignTopTo.value!! + height() / 2
            }
        }
        absolutealignBottomTo.addListener {
            if(absolutealignBottomTo.value != null){
                absolutealignTopTo.value = null
                relativealignTopTo = null
                absoluteY.value = absolutealignBottomTo.value!! - height() / 2
            }
        }
    }

    /**
     * The relative left alignment of this DisplayerCore, as a proportion of its container's width.
     * @see updateRelativeAlignment
     * @see absoluteAlignLeftTo
     * @since LLayout 1
     */
    private var relativeAlignLeftTo : Double? = null

    /**
     * The relative right alignment of this DisplayerCore, as a proportion of its container's width.
     * @see updateRelativeAlignment
     * @see absoluteAlignRightTo
     * @since LLayout 1
     */
    private var relativeAlignRightTo : Double? = null

    /**
     * The relative up alignment of this DisplayerCore, as a proportion of its container's height.
     * @see updateRelativeAlignment
     * @see absolutealignTopTo
     * @since LLayout 1
     */
    private var relativealignTopTo : Double? = null

    /**
     * The relative down alignment of this DisplayerCore, as a proportion of its container's height.
     * @see updateRelativeAlignment
     * @see absolutealignBottomTo
     * @since LLayout 1
     */
    private var relativealignBottomTo : Double? = null

    /**
     * The DisplayerCore to which this one is aligned horizontally.
     * @since LLayout 1
     */
    private var horizontalDisplayerAlignment : DisplayerCore? = null

    /**
     * The DisplayerCore to which this one is aligned vertically.
     * @since LLayout 1
     */
    private var verticalDisplayerAlignment : DisplayerCore? = null

    /**
     * The Action executed to reset the horizontal DisplayerCore alignment.
     * @see Action
     * @since LLayout 1
     */
    private var resetHorizontalDisplayerAlignment : Action = {}

    /**
     * The Action executed to reset the vertical Dispalyer alignment.
     * @see Action
     * @since LLayout 1
     */
    private var resetVerticalDisplayerAlignment : Action = {}

    /**
     * The minimal width of this component. It is null if there is no minimal width.
     * @see setPreferredWidth
     * @see applyPreferredWidth
     * @since LLayout 1
     */
    private var preferredWidth : Int? = null

    /**
     * The minimal height of this component. It is null if there is no minimal height.
     * @see setPreferredHeight
     * @see applyPreferredHeight
     * @since LLayout 1
     */
    private var preferredHeight : Int? = null

    /**
     * The action executed when the mouse clicks this DisplayerCore.
     * @see setOnMouseClickedAction
     * @since LLayout 1
     */
    private var onMouseClickedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse presses this DisplayerCore.
     * @see setOnMousePressedAction
     * @since LLayout 1
     */
    private var onMousePressedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse releases this DisplayerCore.
     * @see setOnMouseReleasedAction
     * @since LLayout 1
     */
    private var onMouseReleasedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse enters this DisplayerCore.
     * @see setOnMouseEnteredAction
     * @since LLayout 1
     */
    private var onMouseEnteredAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse exits this DisplayerCore.
     * @see setOnMouseExitedAction
     * @since LLayout 1
     */
    private var onMouseExitedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse moves over this DisplayerCore.
     * @see setOnMouseMovedAction
     * @since LLayout 1
     */
    private var onMouseMovedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse drags this DisplayerCore.
     * @see setOnMouseDraggedAction
     * @since LLayout 1
     */
    private var onMouseDraggedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse wheel acts over this DisplayerCore.
     * @see setOnMouseWheelMovedAction
     * @since LLayout 1
     */
    private var onMouseWheelMovedAction : (e : MouseWheelEvent) -> Unit = {}

    /**
     * The action executed when the user types a key while this DisplayerCore has the input focus.
     * @see setOnKeyTypedAction
     * @since LLayout 1
     */
    private var onKeyTypedAction : (e : KeyEvent) -> Unit = {}

    /**
     * The action executed when the users presses a key while this DisplayerCore has the input focus.
     * @see setOnKeyPressedAction
     * @since LLayout 1
     */
    private var onKeyPressedAction : (e : KeyEvent) -> Unit = {}

    /**
     * The action executed when the user releases a key while this DisplayerCore has the input focus.
     * @see setOnKeyReleasedAction
     * @since LLayout 1
     */
    private var onKeyReleasedAction : (e : KeyEvent) -> Unit = {}

    /*
     * Adds event listeners
     */
    init{
        super.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?){
                requestFocusInWindow()
                onMouseClickedAction(e!!)
            }
            override fun mousePressed(e: MouseEvent?) = onMousePressedAction(e!!)
            override fun mouseReleased(e: MouseEvent?) = onMouseReleasedAction(e!!)
            override fun mouseEntered(e: MouseEvent?) = onMouseEnteredAction(e!!)
            override fun mouseExited(e: MouseEvent?) = onMouseExitedAction(e!!)
        })
        super.addMouseMotionListener(object : MouseMotionListener {
            override fun mouseMoved(e: MouseEvent?) = onMouseMovedAction(e!!)
            override fun mouseDragged(e: MouseEvent?) = onMouseDraggedAction(e!!)
        })
        addMouseWheelListener { e -> onMouseWheelMovedAction(e!!) }
        super.addKeyListener(object : KeyListener{
            override fun keyTyped(e: KeyEvent?) = onKeyTypedAction(e!!)
            override fun keyPressed(e: KeyEvent?) = onKeyPressedAction(e!!)
            override fun keyReleased(e: KeyEvent?) = onKeyReleasedAction(e!!)
        })
    }

    /**
     * A LObservable that handles the cases where this DisplayerCore needs an update from its container.
     * @see LObservable
     * @since LLayout 1
     */
    private var requestUpdate: LObservable<Boolean> = LObservable(false)

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, in pixels.
     * @param y The y coordinate of this DisplayerCore's center, in pixels.
     * @since LLayout 1
     */
    protected constructor(x : Int, y : Int){
        absoluteX.value = x
        absoluteY.value = y
    }

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, in pixels.
     * @param y The y coordinate of this DisplayerCore's center, as a proportion of its container's height.
     * @since LLayout 1
     */
    protected constructor(x : Int, y : Double){
        absoluteX.value = x
        relativeY = y
    }

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, as a proportion of its container's width.
     * @param y The y coordinate of this DisplayerCore's center, in pixels.
     * @since LLayout 1
     */
    protected constructor(x : Double, y : Int){
        relativeX = x
        absoluteY.value = y
    }

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, as a proportion of its container's width.
     * @param y The y coordinate of this DisplayerCore's center, as a proportion of its container's height.
     * @since LLayout 1
     */
    protected constructor(x : Double, y : Double){
        relativeX = x
        relativeY = y
    }

    /**
     * Constructs a DisplayerCore with its coordinates initialized at (0, 0).
     * @since LLayout 1
     */
    protected constructor() : this(0, 0)

    /**
     * Updates the relative values of this DisplayerCore with its container's width and height.
     * @see updateRelativeAlignment
     * @see updateRelativeCoordinates
     * @since LLayout 1
     */
    override fun updateRelativeValues(frameWidth : Int, frameHeight : Int) {
        updateRelativeCoordinates(frameWidth, frameHeight)
        updateRelativeAlignment(frameWidth, frameHeight)
    }

    /**
     * Updates the relative coordinates of this DisplayerCore with its container's width and height.
     * @see relativeX
     * @see relativeY
     * @since LLayout 1
     */
    private fun updateRelativeCoordinates(frameWidth: Int, frameHeight: Int){
        if(relativeX != null) absoluteX.value = (frameWidth * relativeX!!).toInt()
        if(relativeY != null) absoluteY.value = (frameHeight * relativeY!!).toInt()
    }

    /**
     * Updates the relative alignment of this DisplayerCore with its container's width and height.
     * @see relativeAlignLeftTo
     * @see relativeAlignRightTo
     * @see relativealignTopTo
     * @see relativealignBottomTo
     * @since LLayout 1
     */
    private fun updateRelativeAlignment(frameWidth: Int, frameHeight: Int){
        if(relativeAlignLeftTo != null){
            absoluteAlignLeftTo.value = (frameWidth * relativeAlignLeftTo!!).toInt()
        }else if(relativeAlignRightTo != null){
            absoluteAlignRightTo.value = (frameWidth * relativeAlignRightTo!!).toInt()
        }
        if(relativealignTopTo != null){
            absolutealignTopTo.value = (frameHeight * relativealignTopTo!!).toInt()
        }else if(relativealignBottomTo != null){
            absolutealignBottomTo.value = (frameHeight * relativealignBottomTo!!).toInt()
        }
    }

    override fun addRequestUpdateListener(key : Any?, action : Action) : DisplayerCore {
        requestUpdate.addListener(key, action)
        return this
    }

    override fun requestUpdate(){
        requestUpdate.value = true
    }

    override fun addRequestUpdateListener(action : Action) : DisplayerCore {
        requestUpdate.addListener(action)
        return this
    }

    override fun removeRequestUpdateListener(key : Any?) : DisplayerCore {
        requestUpdate.removeListener(key)
        return this
    }

    override fun width() : Int = w.value

    override fun height(): Int = h.value

    /**
     * Aligns the left side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this DisplayerCore's left side will be aligned.
     * @see absoluteAlignLeftTo
     * @since LLayout 1
     */
    fun alignLeftTo(position : Int) {
        absoluteAlignLeftTo.value = position
        absoluteX.value = absoluteAlignLeftTo.value!! + width() / 2
    }

    /**
     * Aligns the right side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this DisplayerCore's right side will be aligned.
     * @see absoluteAlignRightTo
     * @since LLayout 1
     */
    fun alignRightTo(position : Int) {
        absoluteAlignRightTo.value = position
        absoluteX.value = absoluteAlignRightTo.value!! - width() / 2
    }

    /**
     * Aligns the up side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this DisplayerCore's up side will be aligned.
     * @see absolutealignTopTo
     * @since LLayout 3
     */
    fun alignTopTo(position : Int) {
        absolutealignTopTo.value = position
        absoluteY.value = absolutealignTopTo.value!! + height() / 2
    }

    /**
     * Aligns the down side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this DisplayerCore's down side will be aligned.
     * @see absolutealignBottomTo
     * @since LLayout 3
     */
    fun alignBottomTo(position : Int) {
        absolutealignBottomTo.value = position
        absoluteY.value = absolutealignBottomTo.value!! - height() / 2
    }

    /**
     * Aligns the left side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this DisplayerCore's left side will be aligned, as a proportion of its container's width.
     * @see relativeAlignLeftTo
     * @since LLayout 2
     */
    fun alignLeftTo(position : Double) {
        resetHorizontalAlignment()
        relativeAlignLeftTo = position
        requestUpdate()
    }

    /**
     * Aligns the right side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this DisplayerCore's right side will be aligned, as a proportion of its container's width.
     * @see relativeAlignRightTo
     * @since LLayout 2
     */
    fun alignRightTo(position : Double) {
        resetHorizontalAlignment()
        relativeAlignRightTo = position
        requestUpdate()
    }

    /**
     * Aligns the up side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this DisplayerCore's up side will be aligned, as a proportion of its container's height.
     * @see relativealignTopTo
     * @since LLayout 3
     */
    fun alignTopTo(position : Double) {
        resetVerticalAlignment()
        relativealignTopTo = position
        requestUpdate()
    }

    /**
     * Aligns the down side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this DisplayerCore's down side will be aligned, as a proportion of its container's height.
     * @see relativealignBottomTo
     * @since LLayout 3
     */
    fun alignBottomTo(position : Double) {
        resetVerticalAlignment()
        relativealignBottomTo = position
        requestUpdate()
    }

    /**
     * Aligns the left side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this DisplayerCore's left side will be aligned, as a proportion of its container's width.
     * @see relativeAlignLeftTo
     * @since LLayout 1
     */
    fun alignLeftTo(position : Float) = alignLeftTo(position.toDouble())

    /**
     * Aligns the right side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this DisplayerCore's right side will be aligned, as a proportion of its container's width.
     * @see relativeAlignRightTo
     * @since LLayout 1
     */
    fun alignRightTo(position : Float) = alignRightTo(position.toDouble())

    /**
     * Aligns the up side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this DisplayerCore's up side will be aligned, as a proportion of its container's height.
     * @see relativealignTopTo
     * @since LLayout 3
     */
    fun alignTopTo(position : Float) = alignTopTo(position.toDouble())

    /**
     * Aligns the down side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this DisplayerCore's down side will be aligned, as a proportion of its container's height.
     * @see relativealignBottomTo
     * @since LLayout 3
     */
    fun alignBottomTo(position : Float) = alignBottomTo(position.toDouble())

    /**
     * Aligns the upper part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see verticalDisplayerAlignment
     * @since LLayout 3
     */
    fun alignTopToTop(component : DisplayerCore, delta : Int = 0) {
        val key = "UP TO UP DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignTopTo(component.upSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeUpSideListener(key)
        }
        verticalDisplayerAlignment = component.addUpSideListener(key){ alignTopTo(component.upSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeUpSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
    }

    /**
     * Aligns the upper part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see verticalDisplayerAlignment
     * @since LLayout 3
     */
    fun alignTopToBottom(component : DisplayerCore, delta : Int = 0) {
        val key = "UP TO DOWN DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignTopTo(component.downSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeDownSideListener(key)
        }
        verticalDisplayerAlignment = component.addDownSideListener(key){ alignTopTo(component.downSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeDownSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
    }

    /**
     * Aligns the leftmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see horizontalDisplayerAlignment
     * @since LLayout 1
     */
    fun alignLeftToLeft(component : DisplayerCore, delta : Int = 0) {
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
    }

    /**
     * Aligns the leftmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see horizontalDisplayerAlignment
     * @since LLayout 1
     */
    fun alignLeftToRight(component : DisplayerCore, delta : Int = 0) {
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
    }

    /**
     * Aligns the rightmost part of this component to the leftmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see horizontalDisplayerAlignment
     * @since LLayout 1
     */
    fun alignRightToLeft(component : DisplayerCore, delta : Int = 0) {
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
    }

    /**
     * Aligns the rightmost part of this component to the rightmost part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see horizontalDisplayerAlignment
     * @since LLayout 1
     */
    fun alignRightToRight(component : DisplayerCore, delta : Int = 0) {
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
    }

    /**
     * Aligns the lower part of this component to the lower part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see verticalDisplayerAlignment
     * @since LLayout 3
     */
    fun alignBottomToBottom(component : DisplayerCore, delta : Int = 0) {
        val key = "DOWN TO DOWN DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignBottomTo(component.downSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeDownSideListener(key)
        }
        verticalDisplayerAlignment = component.addDownSideListener(key){ alignBottomTo(component.downSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeDownSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
    }

    /**
     * Aligns the lower part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @see verticalDisplayerAlignment
     * @since LLayout 3
     */
    fun alignBottomToTop(component : DisplayerCore, delta : Int = 0) {
        val key = "DOWN TO UP DISPLAYER ALIGNMENT OF DISPLAYER INDEX : $displayerIndex"
        alignBottomTo(component.upSideY() + delta)
        if(verticalDisplayerAlignment != null){
            verticalDisplayerAlignment!!.removeUpSideListener(key)
        }
        verticalDisplayerAlignment = component.addUpSideListener(key){ alignBottomTo(component.upSideY() + delta) }
        resetVerticalDisplayerAlignment = {
            if(verticalDisplayerAlignment != null){
                verticalDisplayerAlignment!!.removeUpSideListener(key)
                verticalDisplayerAlignment = null
            }
        }
    }

    /**
     * Resets this component's alignment constraints.
     * @see resetCoordinateAlignment
     * @see resetDisplayerAlignment
     * @since LLayout 1
     */
    fun resetAlignment(){
        resetDisplayerAlignment()
        resetCoordinateAlignment()
    }

    /**
     * Resets the horizontal alignment constraints of this DisplayerCore.
     * @since LLayout 1
     */
    fun resetHorizontalAlignment(){
        resetHorizontalCoordinateAlignment()
        resetHorizontalDisplayerAlignment()
    }

    /**
     * Resets the vertical alignment constraints of this DisplayerCore.
     * @since LLayout 1
     */
    fun resetVerticalAlignment(){
        resetVerticalCoordinateAlignment()
        resetVerticalDisplayerAlignment()
    }

    /**
     * Resets the alignment constraints to other Displayers.
     * @see resetAlignment
     * @see resetHorizontalDisplayerAlignment
     * @see resetVerticalDisplayerAlignment
     * @since LLayout 1
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
     * @since LLayout 1
     */
    private fun resetCoordinateAlignment(){
        resetVerticalCoordinateAlignment()
        resetHorizontalCoordinateAlignment()
    }

    /**
     * Resets the vertical coordinate alignments of this DisplayerCore.
     * @see absolutealignTopTo
     * @see absolutealignBottomTo
     * @see relativealignTopTo
     * @see relativealignBottomTo
     * @see resetCoordinateAlignment
     * @since LLayout 1
     */
    private fun resetVerticalCoordinateAlignment(){
        absolutealignTopTo.value = null
        absolutealignBottomTo.value = null
        relativealignTopTo = null
        relativealignBottomTo = null
    }

    /**
     * Resets the horizontal coordinate alignments of this DisplayerCore.
     * @see absoluteAlignLeftTo
     * @see absoluteAlignRightTo
     * @see relativeAlignLeftTo
     * @see relativeAlignRightTo
     * @see resetCoordinateAlignment
     * @since LLayout 1
     */
    private fun resetHorizontalCoordinateAlignment(){
        absoluteAlignLeftTo.value = null
        absoluteAlignRightTo.value = null
        relativeAlignLeftTo = null
        relativeAlignRightTo = null
    }

    /**
     * Sets a preferred width for this DisplayerCore.
     * A negative parameter will be used in absolute value.
     * @param preferredWidth The preferred width of this DisplayerCore.
     * @return This DisplayerCore.
     * @see preferredWidth
     * @since LLayout 1
     */
    fun setPreferredWidth(preferredWidth : Int) : DisplayerCore {
        this.preferredWidth = if(preferredWidth < 0) - preferredWidth else preferredWidth
        initialize()
        return this
    }

    /**
     * Sets a preferred height for this DisplayerCore.
     * A negative parameter will be used in absolute value.
     * @param preferredHeight The preferred height of this DisplayerCore.
     * @return This DisplayerCore.
     * @see preferredHeight
     * @since LLayout 1
     */
    fun setPreferredHeight(preferredHeight : Int) : DisplayerCore {
        this.preferredHeight = if(preferredHeight < 0) - preferredHeight else preferredHeight
        initialize()
        return this
    }

    /**
     * The x coordinate of this DisplayerCore's center.
     * @return The x coordinate of this Dispalyer's center.
     * @see absoluteX
     * @since LLayout 1
     */
    fun centerX() : Int = absoluteX.value

    /**
     * The y coordinate of this DisplayerCore's center.
     * @return The y coordinate of this DisplayerCore's center.
     * @see absoluteY
     * @since LLayout 1
     */
    fun centerY() : Int = absoluteY.value

    /**
     * The y coordinate of the upper side of this DisplayerCore.
     * @return The y coordinate of the upper side of this DisplayerCore.
     * @since LLayout 1
     */
    fun upSideY() : Int = upSideY.value

    /**
     * The y coordinate of the lower side of this DisplayerCore.
     * @return The y coordinate of the lower side of this DisplayerCore.
     * @since LLayout 1
     */
    fun downSideY() : Int = downSideY.value

    /**
     * The x coordinate of the left side of this DisplayerCore.
     * @return The x coordinate of the left side of this DisplayerCore.
     * @since LLayout 1
     */
    fun leftSideX() : Int = leftSideX.value

    /**
     * The x coordinate of the right side of this DisplayerCore.
     * @return The x coordinate of the right side of this DisplayerCore.
     * @since LLayout 1
     */
    fun rightSideX() : Int = rightSideX.value

    /**
     * Sets the action executed when the mouse clicks this DisplayerCore.
     * @see onMouseClickedAction
     * @since LLayout 1
     */
    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) {
        onMouseClickedAction = action
    }

    /**
     * Sets the action executed when the mouse presses this DisplayerCore.
     * @see onMousePressedAction
     * @since LLayout 1
     */
    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) {
        onMousePressedAction = action
    }

    /**
     * Sets the action executed when the mouse releases this DisplayerCore.
     * @see onMouseReleasedAction
     * @since LLayout 1
     */
    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) {
        onMouseReleasedAction = action
    }

    /**
     * Sets the action executed when the mouse enters this DisplayerCore.
     * @see onMouseEnteredAction
     * @since LLayout 1
     */
    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) {
        onMouseEnteredAction = action
    }

    /**
     * Sets the action executed when the mouse exits this DisplayerCore.
     * @see onMouseExitedAction
     * @since LLayout 1
     */
    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) {
        onMouseExitedAction = action
    }

    /**
     * Sets the action executed when the mouse moves over this DisplayerCore.
     * @see onMouseMovedAction
     * @since LLayout 1
     */
    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) {
        onMouseMovedAction = action
    }

    /**
     * Sets the action executed when the mouse drags this DisplayerCore.
     * @see onMouseDraggedAction
     * @since LLayout 1
     */
    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) {
        onMouseDraggedAction = action
    }

    /**
     * Sets the action executed when the mouse wheel acts on this DisplayerCore.
     * @see onMouseWheelMovedAction
     * @since LLayout 1
     */
    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) {
        onMouseWheelMovedAction = action
    }

    /**
     * Sets the action executed when the users presses a key while this DisplayerCore has the input focus.
     * @see onKeyPressedAction
     * @since LLayout 1
     */
    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) {
        onKeyPressedAction = action
    }

    /**
     * Sets the action executed when the users releases a key while this DisplayerCore has the input focus.
     * @see onKeyReleasedAction
     * @since LLayout 1
     */
    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) {
        onKeyReleasedAction = action
    }

    /**
     * Sets the action executed when the users types a key while this DisplayerCore has the input focus.
     * @see onKeyTypedAction
     * @since LLayout 1
     */
    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) {
        onKeyTypedAction = action
    }

    /**
     * Initializes this DisplayerCore.
     * A DisplayerCore is initialized if it must recalculate some parameters the
     * next time it is drawn.
     * @see initphase
     * @since LLayout 1
     */
    override fun initialize(){
        initphase = true
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun moveTo(x : Int, y : Int) {
        if(x != centerX()){
            absoluteX.value = x
            relativeX = null
            resetHorizontalAlignment()
        }
        if(y != centerY()){
            absoluteY.value = y
            relativeY = null
            resetVerticalAlignment()
        }
        loadBounds()
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun moveTo(x : Double, y : Int) {
        if(x != relativeX){
            relativeX = x
            requestUpdate()
            resetHorizontalAlignment()
        }
        if(y != centerY()){
            absoluteY.value = y
            relativeY = null
            resetVerticalAlignment()
        }
        loadBounds()
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun moveTo(x : Int, y : Double) {
        if(x != centerX()){
            absoluteX.value = x
            relativeX = null
            resetHorizontalAlignment()
        }
        if( y != relativeY){
            relativeY = y
            requestUpdate()
            resetVerticalAlignment()
        }
        loadBounds()
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun moveTo(x : Double, y : Double) {
        if(x != relativeX){
            relativeX = x
            resetHorizontalAlignment()
        }
        if(y != relativeY){
            relativeY = y
            resetVerticalAlignment()
        }
        requestUpdate()
        loadBounds()
    }

    /**
     * Change this DisplayerCore's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this DisplayerCore's position.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun setCenterX(x : Int) {
        if(x != centerX()){
            absoluteX.value = x
            relativeX = null
            resetHorizontalAlignment()
            loadBounds()
        }
    }

    /**
     * Change this DisplayerCore's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this DisplayerCore's position.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun setCenterX(x : Double) {
        if(x != relativeX){
            relativeX = x
            resetHorizontalAlignment()
            requestUpdate()
            loadBounds()
        }
    }

    /**
     * Change this DisplayerCore's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this DisplayerCore's position.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun setCenterX(x : Float) = setCenterX(x.toDouble())

    /**
     * Change this DisplayerCore's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this DisplayerCore's position.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun setCenterY(y : Int) {
        if(y != centerY()){
            absoluteY.value = y
            relativeY = null
            resetVerticalAlignment()
            loadBounds()
        }
    }

    /**
     * Change this DisplayerCore's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this DisplayerCore's position.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun setCenterY(y : Double) {
        if(y != relativeY){
            relativeY = y
            resetVerticalAlignment()
            requestUpdate()
            loadBounds()
        }
    }

    /**
     * Change this DisplayerCore's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this DisplayerCore's position.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun setCenterY(y : Float) = setCenterY(y.toDouble())

    /**
     * Moves this DisplayerCore along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param x The displacement in x, in pixels.
     * @param y The displacement in y, in pixels.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun moveAlong(x : Int, y : Int) {
        if(x != 0 || y != 0){
            absoluteX.value += x
            relativeX = null
            resetHorizontalAlignment()
        }
        if(y != 0){
            absoluteY.value += y
            relativeY = null
            resetVerticalAlignment()
        }
        loadBounds()
    }

    /**
     * Moves this DisplayerCore along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param x The displacement in x, in pixels.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun moveAlongX(x : Int) {
        if(x != 0){
            absoluteX.value += x
            relativeX = null
            resetHorizontalAlignment()
            loadBounds()
        }
    }

    /**
     * Moves this DisplayerCore along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param y The displacement in y, in pixels.
     * @see resetAlignment
     * @since LLayout 1
     */
    fun moveAlongY(y : Int) {
        if(y != 0){
            absoluteY.value += y
            relativeY = null
            resetVerticalAlignment()
            loadBounds()
        }
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @see absoluteX
     * @see Action
     * @since LLayout 1
     */
    fun addXListener(key : Any?, action : Action) {
        absoluteX.addListener(key, action)
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @see absoluteY
     * @see Action
     * @since LLayout 1
     */
    fun addYListener(key : Any?, action : Action) {
        absoluteY.addListener(key, action)
    }

    /**
     * Adds a listener that will be triggered on a change of the width of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @see w
     * @see Action
     * @since LLayout 1
     */
    fun addWidthListener(key : Any?, action : Action) {
        w.addListener(key, action)
    }

    /**
     * Adds a listener that will be triggered on a change of the height of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @see h
     * @see Action
     * @since LLayout 1
     */
    fun addHeightListener(key : Any?, action : Action) {
        h.addListener(key, action)
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @see absoluteX
     * @see Action
     * @since LLayout 1
     */
    fun addXListener(action : Action) = addXListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @see absoluteY
     * @see Action
     * @since LLayout 1
     */
    fun addYListener(action : Action) = addYListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the width of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @see w
     * @see Action
     * @since LLayout 1
     */
    fun addWidthListener(action : Action) = addWidthListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the height of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @see h
     * @see Action
     * @since LLayout 1
     */
    fun addHeightListener(action : Action) = addHeightListener(action, action)

    /**
     * Removes a listener of the x coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @see addXListener
     * @since LLayout 1
     */
    fun removeXListener(key : Any?) {
        absoluteX.removeListener(key)
    }

    /**
     * Removes a listener of the y coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @see addYListener
     * @since LLayout 1
     */
    fun removeYListener(key : Any?) {
        absoluteY.removeListener(key)
    }

    /**
     * Removes a listener of the width of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @see addWidthListener
     * @since LLayout 1
     */
    fun removeWidthListener(key : Any?) {
        w.removeListener(key)
    }

    /**
     * Removes a listener of the height of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @see addHeightListener
     * @since LLayout 1
     */
    fun removeHeightListener(key : Any?) {
        h.removeListener(key)
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the left side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see leftSideX
     * @see Action
     * @since LLayout 1
     */
    fun addLeftSideListener(key : Any?, action : Action) : DisplayerCore {
        leftSideX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the left side of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see leftSideX
     * @see Action
     * @since LLayout 1
     */
    fun addLeftSideListener(action : Action) : DisplayerCore = addLeftSideListener(action, action)

    /**
     * Removes a listener of the x coordinate of the left side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addLeftSideListener
     * @since LLayout 1
     */
    fun removeLeftSideListener(key : Any?) : DisplayerCore {
        leftSideX.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the right side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see rightSideX
     * @see Action
     * @since LLayout 1
     */
    fun addRightSideListener(key : Any?, action : Action) : DisplayerCore {
        rightSideX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the right side of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see rightSideX
     * @see Action
     * @since LLayout 1
     */
    fun addRightSideListener(action : Action) : DisplayerCore = addRightSideListener(action, action)

    /**
     * Removes a listener of the x coordinate of the right side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addRightSideListener
     * @since LLayout 1
     */
    fun removeRightSideListener(key : Any?) : DisplayerCore {
        rightSideX.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the up side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see upSideY
     * @see Action
     * @since LLayout 1
     */
    fun addUpSideListener(key : Any?, action : Action) : DisplayerCore {
        upSideY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the up side of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see upSideY
     * @see Action
     * @since LLayout 1
     */
    fun addUpSideListener(action : Action) : DisplayerCore = addUpSideListener(action, action)

    /**
     * Removes a listener of the y coordinate of the up side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addUpSideListener
     * @since LLayout 1
     */
    fun removeUpSideListener(key : Any?) : DisplayerCore {
        upSideY.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the down side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see downSideY
     * @see Action
     * @since LLayout 1
     */
    fun addDownSideListener(key : Any?, action : Action) : DisplayerCore {
        downSideY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of the down side of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see downSideY
     * @see Action
     * @since LLayout 1
     */
    fun addDownSideListener(action : Action) : DisplayerCore = addDownSideListener(action, action)

    /**
     * Removes a listener of the y coordinate of the down side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addDownSideListener
     * @since LLayout 1
     */
    fun removeDownSideListener(key : Any?) : DisplayerCore {
        downSideY.removeListener(key)
        return this
    }

    /**
     * Sets this component's bounds.
     * @see setBounds
     * @since LLayout 1
     */
    private fun loadBounds() = setBounds(centerX() - width() / 2, centerY() - height() / 2, width(), height())

    override fun drawDisplayable(g: Graphics) = paintComponent(g)

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            requestUpdate()
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
     * @since LLayout 1
     */
    private fun applyPreferredSize(){
        applyPreferredWidth()
        applyPreferredHeight()
    }

    /**
     * Applies the preferred width constraint, i.e. makes sure the width is
     * at least preferredWidth.
     * @see preferredWidth
     * @since LLayout 1
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
     * @since LLayout 1
     */
    private fun applyPreferredHeight(){
        if(preferredHeight != null && height() < preferredHeight!!){
            h.value = preferredHeight!!
        }
    }

    /**
     * Loads the necessary parameters during the initialization phase.
     * @see initphase
     * @since LLayout 1
     */
    protected abstract fun loadParameters(g : Graphics)

    /**
     * Draws this DisplayerCore on a Graphics context.
     * @since LLayout 1
     */
    protected abstract fun drawDisplayer(g : Graphics)

    override fun onTimerTick() {}

}