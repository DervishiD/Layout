package llayout.displayers.cores

import llayout.*
import llayout.frame.LSceneCore
import llayout.interfaces.*
import llayout.utilities.LObservable
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JLabel

/**
 * A DisplayerCore is the type of Component that is added on StandardLContainer objects.
 * @see LSceneCore
 * @see StandardLContainer
 */
abstract class DisplayerCore : JLabel, Displayable {

    companion object{

        /**
         * A static variable that encodes the index xof the next DisplayerCore.
         * The index uniquely defines a DisplayerCore. It is used to create unique LObservable keys, for example.
         * @see LObservable
         * @see displayerIndex
         */
        @JvmStatic private var staticDisplayerIndex : Int = 0

    }

    /**
     * The index uniquely defines a DisplayerCore. It is used to create unique LObservable keys, for example.
     * @see LObservable
     * @see staticDisplayerIndex
     */
    private var displayerIndex : Int

    /**
     * The absolute x coordinate of this DisplayerCore.
     * @see LObservable
     */
    protected var absoluteX : LObservable<Int> = LObservable(0)

    /**
     * The absolute y coordinate of this DisplayerCore.
     * @see LObservable
     */
    protected var absoluteY : LObservable<Int> = LObservable(0)

    /**
     * The relative x coordinate of this DisplayerCore, as a proportion of its container's width.
     * @see updateRelativeCoordinates
     * @see absoluteX
     */
    private var relativeX : Double? = null

    /**
     * The relative y coordinate of this DisplayerCore, as a proportion of its container's height.
     * @see updateRelativeCoordinates
     * @see absoluteY
     */
    private var relativeY : Double? = null

    var w : LObservable<Int> = LObservable(0)

    var h : LObservable<Int> = LObservable(0)

    /**
     * The x coordinate of the left side of this DisplayerCore.
     * @see LObservable
     */
    private var leftSideX : LObservable<Int> = LObservable(0)

    /**
     * The y coordinate of the upper side of this DisplayerCore.
     * @see LObservable
     */
    private var upSideY : LObservable<Int> = LObservable(0)

    /**
     * The x coordinate of the right side of this DisplayerCore.
     * @see LObservable
     */
    private var rightSideX : LObservable<Int> = LObservable(0)

    /**
     * The y coordinate of the lower side of this DisplayerCore.
     * @see LObservable
     */
    private var downSideY : LObservable<Int> = LObservable(0)

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
         * Adds listeners to the xywh properties, and defines this DisplayerCore's index.
         */

        absoluteX.addListener{updateLowestHighestX()}
        absoluteY.addListener{updateLowestHighestY()}
        w.addListener{updateLowestHighestX()}
        h.addListener{updateLowestHighestY()}
        displayerIndex = staticDisplayerIndex
        staticDisplayerIndex++
    }

    /**
     * Encodes if this DisplayerCore is in its initialization phase.
     * The DisplayerCore must be initialized if some of its information must
     * be calculated the next time it is drawn on its container.
     * @see initialize
     * @see StandardLContainer.initialization
     */
    protected var initphase : Boolean = true

    /**
     * Encodes the coordinate at which the left side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's left side isn't fixed anywhere.
     * Fixing an alignment of the left side will annihilate any existing alignment of
     * the right side.
     * @see LObservable
     * @see alignLeftTo
     */
    protected var absoluteAlignLeftTo : LObservable<Int?> = LObservable(null)

    /**
     * Encodes the coordinate at which the right side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's right side isn't fixed anywhere.
     * Fixing an alignment of the right side will annihilate any existing alignment of
     * the left side.
     * @see LObservable
     * @see alignRightTo
     */
    protected var absoluteAlignRightTo : LObservable<Int?> = LObservable(null)

    /**
     * Encodes the coordinate at which the up side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's up side isn't fixed anywhere.
     * Fixing an alignment of the up side will annihilate any existing alignment of
     * the down side.
     * @see LObservable
     * @see alignUpTo
     */
    protected var absoluteAlignUpTo : LObservable<Int?> = LObservable(null)

    /**
     * Encodes the coordinate at which the down side of this DisplayerCore is fixed.
     * It is null if the DisplayerCore's down side isn't fixed anywhere.
     * Fixing an alignment of the down side will annihilate any existing alignment of
     * the up side.
     * @see LObservable
     * @see alignDownTo
     */
    protected var absoluteAlignDownTo : LObservable<Int?> = LObservable(null)

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
     * The relative left alignment of this DisplayerCore, as a proportion of its container's width.
     * @see updateRelativeAlignment
     * @see absoluteAlignLeftTo
     */
    private var relativeAlignLeftTo : Double? = null

    /**
     * The relative right alignment of this DisplayerCore, as a proportion of its container's width.
     * @see updateRelativeAlignment
     * @see absoluteAlignRightTo
     */
    private var relativeAlignRightTo : Double? = null

    /**
     * The relative up alignment of this DisplayerCore, as a proportion of its container's height.
     * @see updateRelativeAlignment
     * @see absoluteAlignUpTo
     */
    private var relativeAlignUpTo : Double? = null

    /**
     * The relative down alignment of this DisplayerCore, as a proportion of its container's height.
     * @see updateRelativeAlignment
     * @see absoluteAlignDownTo
     */
    private var relativeAlignDownTo : Double? = null

    /**
     * The DisplayerCore to which this one is aligned horizontally.
     */
    private var horizontalDisplayerAlignment : DisplayerCore? = null

    /**
     * The DisplayerCore to which this one is aligned vertically.
     */
    private var verticalDisplayerAlignment : DisplayerCore? = null

    /**
     * The Action executed to reset the horizontal DisplayerCore alignment.
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

    private var onMouseClickedAction : (e : MouseEvent) -> Unit = {}
    private var onMousePressedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseReleasedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseEnteredAction : (e : MouseEvent) -> Unit = {}
    private var onMouseExitedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseMovedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseDraggedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseWheelMovedAction : (e : MouseWheelEvent) -> Unit = {}
    private var onKeyTypedAction : (e : KeyEvent) -> Unit = {}
    private var onKeyPressedAction : (e : KeyEvent) -> Unit = {}
    private var onKeyReleasedAction : (e : KeyEvent) -> Unit = {}

    init{
        addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?){
                requestFocusInWindow()
                onMouseClickedAction(e!!)
            }
            override fun mousePressed(e: MouseEvent?) = onMousePressedAction(e!!)
            override fun mouseReleased(e: MouseEvent?) = onMouseReleasedAction(e!!)
            override fun mouseEntered(e: MouseEvent?) = onMouseEnteredAction(e!!)
            override fun mouseExited(e: MouseEvent?) = onMouseExitedAction(e!!)
        })
        addMouseMotionListener(object : MouseMotionListener {
            override fun mouseMoved(e: MouseEvent?) = onMouseMovedAction(e!!)
            override fun mouseDragged(e: MouseEvent?) = onMouseDraggedAction(e!!)
        })
        addMouseWheelListener { e -> onMouseWheelMovedAction(e!!) }
        addKeyListener(object : KeyListener{
            override fun keyTyped(e: KeyEvent?) = onKeyTypedAction(e!!)
            override fun keyPressed(e: KeyEvent?) = onKeyPressedAction(e!!)
            override fun keyReleased(e: KeyEvent?) = onKeyReleasedAction(e!!)
        })
    }

    var requestUpdate: LObservable<Boolean> = LObservable(false)

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, in pixels.
     * @param y The y coordinate of this DisplayerCore's center, in pixels.
     */
    protected constructor(x : Int, y : Int){
        absoluteX.value = x
        absoluteY.value = y
    }

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, in pixels.
     * @param y The y coordinate of this DisplayerCore's center, as a proportion of its container's height.
     */
    protected constructor(x : Int, y : Double){
        absoluteX.value = x
        relativeY = y
    }

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, as a proportion of its container's width.
     * @param y The y coordinate of this DisplayerCore's center, in pixels.
     */
    protected constructor(x : Double, y : Int){
        relativeX = x
        absoluteY.value = y
    }

    /**
     * Constructs a DisplayerCore by its coordinates.
     * @param x The x coordinate of this DisplayerCore's center, as a proportion of its container's width.
     * @param y The y coordinate of this DisplayerCore's center, as a proportion of its container's height.
     */
    protected constructor(x : Double, y : Double){
        relativeX = x
        relativeY = y
    }

    protected constructor() : this(0, 0)

    /**
     * Updates the relative values of this DisplayerCore, using its container's width and height.
     * @see updateRelativeAlignment
     * @see updateRelativeCoordinates
     * @return This DisplayerCore
     */
    override fun updateRelativeValues(frameWidth : Int, frameHeight : Int) {
        updateRelativeCoordinates(frameWidth, frameHeight)
        updateRelativeAlignment(frameWidth, frameHeight)
    }

    /**
     * Updates the relative coordinates of this DisplayerCore, using its container's width and height.
     * @see relativeX
     * @see relativeY
     */
    private fun updateRelativeCoordinates(frameWidth: Int, frameHeight: Int){
        if(relativeX != null) absoluteX.value = (frameWidth * relativeX!!).toInt()
        if(relativeY != null) absoluteY.value = (frameHeight * relativeY!!).toInt()
    }

    /**
     * Updates the relative alignment of this DisplayerCore, using its container's width and height.
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
     * @return This DisplayerCore.
     * @see absoluteAlignLeftTo
     */
    open fun alignLeftTo(position : Int) : DisplayerCore {
        absoluteAlignLeftTo.value = position
        absoluteX.value = absoluteAlignLeftTo.value!! + width() / 2
        return this
    }

    /**
     * Aligns the right side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this DisplayerCore's right side will be aligned.
     * @return This DisplayerCore.
     * @see absoluteAlignRightTo
     */
    open fun alignRightTo(position : Int) : DisplayerCore {
        absoluteAlignRightTo.value = position
        absoluteX.value = absoluteAlignRightTo.value!! - width() / 2
        return this
    }

    /**
     * Aligns the up side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this DisplayerCore's up side will be aligned.
     * @return This DisplayerCore.
     * @see absoluteAlignUpTo
     */
    open fun alignUpTo(position : Int) : DisplayerCore {
        absoluteAlignUpTo.value = position
        absoluteY.value = absoluteAlignUpTo.value!! + height() / 2
        return this
    }

    /**
     * Aligns the down side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this DisplayerCore's down side will be aligned.
     * @return This DisplayerCore.
     * @see absoluteAlignDownTo
     */
    open fun alignDownTo(position : Int) : DisplayerCore {
        absoluteAlignDownTo.value = position
        absoluteY.value = absoluteAlignDownTo.value!! - height() / 2
        return this
    }

    /**
     * Aligns the left side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this DisplayerCore's left side will be aligned, as a proportion of its container's width.
     * @return This DisplayerCore.
     * @see relativeAlignLeftTo
     */
    fun alignLeftTo(position : Double) : DisplayerCore {
        relativeAlignLeftTo = position
        absoluteAlignRightTo.value = null
        relativeAlignRightTo = null
        requestUpdate()
        return this
    }

    /**
     * Aligns the right side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this DisplayerCore's right side will be aligned, as a proportion of its container's width.
     * @return This DisplayerCore.
     * @see relativeAlignRightTo
     */
    fun alignRightTo(position : Double) : DisplayerCore {
        relativeAlignRightTo = position
        absoluteAlignLeftTo.value = null
        relativeAlignLeftTo = null
        requestUpdate()
        return this
    }

    /**
     * Aligns the up side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this DisplayerCore's up side will be aligned, as a proportion of its container's height.
     * @return This DisplayerCore.
     * @see relativeAlignUpTo
     */
    fun alignUpTo(position : Double) : DisplayerCore {
        relativeAlignUpTo = position
        absoluteAlignDownTo.value = null
        relativeAlignDownTo = null
        requestUpdate()
        return this
    }

    /**
     * Aligns the down side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this DisplayerCore's down side will be aligned, as a proportion of its container's height.
     * @return This DisplayerCore.
     * @see relativeAlignDownTo
     */
    fun alignDownTo(position : Double) : DisplayerCore {
        relativeAlignDownTo = position
        absoluteAlignUpTo.value = null
        relativeAlignUpTo = null
        requestUpdate()
        return this
    }

    /**
     * Aligns the left side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the right side.
     * @param position The x coordinate at which this DisplayerCore's left side will be aligned, as a proportion of its container's width.
     * @return This DisplayerCore.
     * @see relativeAlignLeftTo
     */
    fun alignLeftTo(position : Float) : DisplayerCore = alignLeftTo(position.toDouble())

    /**
     * Aligns the right side of this DisplayerCore to the given x coordinate.
     * Resets any alignment of the left side.
     * @param position The x coordinate at which this DisplayerCore's right side will be aligned, as a proportion of its container's width.
     * @return This DisplayerCore.
     * @see relativeAlignRightTo
     */
    fun alignRightTo(position : Float) : DisplayerCore = alignRightTo(position.toDouble())

    /**
     * Aligns the up side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the down side.
     * @param position The y coordinate at which this DisplayerCore's up side will be aligned, as a proportion of its container's height.
     * @return This DisplayerCore.
     * @see relativeAlignUpTo
     */
    fun alignUpTo(position : Float) : DisplayerCore = alignUpTo(position.toDouble())

    /**
     * Aligns the down side of this DisplayerCore to the given y coordinate.
     * Resets any alignment of the up side.
     * @param position The y coordinate at which this DisplayerCore's down side will be aligned, as a proportion of its container's height.
     * @return This DisplayerCore.
     * @see relativeAlignDownTo
     */
    fun alignDownTo(position : Float) : DisplayerCore = alignDownTo(position.toDouble())

    /**
     * Aligns the upper part of this component to the upper part of the other, with the
     * given signed distance, in pixels, between them.
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see verticalDisplayerAlignment
     */
    fun alignUpToUp(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see verticalDisplayerAlignment
     */
    fun alignUpToDown(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see horizontalDisplayerAlignment
     */
    fun alignLeftToLeft(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see horizontalDisplayerAlignment
     */
    fun alignLeftToRight(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see horizontalDisplayerAlignment
     */
    fun alignRightToLeft(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see horizontalDisplayerAlignment
     */
    fun alignRightToRight(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see verticalDisplayerAlignment
     */
    fun alignDownToDown(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
     * @param component The DisplayerCore to which this one will be aligned.
     * @param delta The distance, in pixels, between the two edges.
     * @return This DisplayerCore.
     * @see verticalDisplayerAlignment
     */
    fun alignDownToUp(component : DisplayerCore, delta : Int = 0) : DisplayerCore {
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
    fun resetAlignment(){
        resetDisplayerAlignment()
        resetCoordinateAlignment()
    }

    fun resetHorizontalAlignment(){
        resetHorizontalCoordinateAlignment()
        resetHorizontalDisplayerAlignment()
    }

    fun resetVerticalAlignment(){
        resetVerticalCoordinateAlignment()
        resetVerticalDisplayerAlignment()
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
     * Resets the vertical coordinate alignments of this DisplayerCore.
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
     * Resets the horizontal coordinate alignments of this DisplayerCore.
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
     * Sets a preferred width for this DisplayerCore.
     * A negative parameter will be used in absolute value.
     * @param preferredWidth The preferred width of this DisplayerCore.
     * @return This DisplayerCore.
     * @see preferredWidth
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
     */
    fun centerX() : Int = absoluteX.value

    /**
     * The y coordinate of this DisplayerCore's center.
     * @return The y coordinate of this DisplayerCore's center.
     * @see absoluteY
     */
    fun centerY() : Int = absoluteY.value

    /**
     * The y coordinate of the upper side of this DisplayerCore.
     * @return The y coordinate of the upper side of this DisplayerCore.
     */
    open fun upSideY() : Int = upSideY.value

    /**
     * The y coordinate of the lower side of this DisplayerCore.
     * @return The y coordinate of the lower side of this DisplayerCore.
     */
    open fun downSideY() : Int = downSideY.value

    /**
     * The x coordinate of the left side of this DisplayerCore.
     * @return The x coordinate of the left side of this DisplayerCore.
     */
    open fun leftSideX() : Int = leftSideX.value

    /**
     * The x coordinate of the right side of this DisplayerCore.
     * @return The x coordinate of the right side of this DisplayerCore.
     */
    open fun rightSideX() : Int = rightSideX.value

    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : DisplayerCore {
        onMouseClickedAction = action
        return this
    }

    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : DisplayerCore {
        onMousePressedAction = action
        return this
    }

    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : DisplayerCore {
        onMouseReleasedAction = action
        return this
    }

    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : DisplayerCore {
        onMouseEnteredAction = action
        return this
    }

    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : DisplayerCore {
        onMouseExitedAction = action
        return this
    }

    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : DisplayerCore {
        onMouseMovedAction = action
        return this
    }

    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : DisplayerCore {
        onMouseDraggedAction = action
        return this
    }

    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : DisplayerCore {
        onMouseWheelMovedAction = action
        return this
    }

    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : DisplayerCore {
        onKeyPressedAction = action
        return this
    }

    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : DisplayerCore {
        onKeyReleasedAction = action
        return this
    }

    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : DisplayerCore {
        onKeyTypedAction = action
        return this
    }

    /**
     * Initializes this DisplayerCore.
     * A DisplayerCore is initialized if it must recalculate some parameters the
     * next time it is drawn.
     * @see initphase
     */
    override fun initialize(){
        initphase = true
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun moveTo(x : Int, y : Int) : DisplayerCore {
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
        return this
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun moveTo(x : Double, y : Int) : DisplayerCore {
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
        return this
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun moveTo(x : Int, y : Double) : DisplayerCore {
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
        return this
    }

    /**
     * Change this DisplayerCore's position, i.e. center Point.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The x coordinate of the center of this DisplayerCore.
     * @param y The y coordinate of the center of this DisplayerCore.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun moveTo(x : Double, y : Double) : DisplayerCore {
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
        return this
    }

    /**
     * Change this DisplayerCore's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this DisplayerCore's position.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun setCenterX(x : Int) : DisplayerCore {
        if(x != centerX()){
            absoluteX.value = x
            relativeX = null
            resetHorizontalAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Change this DisplayerCore's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this DisplayerCore's position.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun setCenterX(x : Double) : DisplayerCore {
        if(x != relativeX){
            relativeX = x
            resetHorizontalAlignment()
            requestUpdate()
            loadBounds()
        }
        return this
    }

    /**
     * Change this DisplayerCore's center's x coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param x The new x coordinate of this DisplayerCore's position.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun setCenterX(x : Float) : DisplayerCore = setCenterX(x.toDouble())

    /**
     * Change this DisplayerCore's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this DisplayerCore's position.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun setCenterY(y : Int) : DisplayerCore {
        if(y != centerY()){
            absoluteY.value = y
            relativeY = null
            resetVerticalAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Change this DisplayerCore's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this DisplayerCore's position.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun setCenterY(y : Double) : DisplayerCore {
        if(y != relativeY){
            relativeY = y
            resetVerticalAlignment()
            requestUpdate()
            loadBounds()
        }
        return this
    }

    /**
     * Change this DisplayerCore's center's y coordinate.
     * If the given position is different from the current one, resets all alignment constraints.
     * @param y The new y coordinate of this DisplayerCore's position.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun setCenterY(y : Float) = setCenterY(y.toDouble())

    /**
     * Moves this DisplayerCore along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param x The displacement in x, in pixels.
     * @param y The displacement in y, in pixels.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun moveAlong(x : Int, y : Int) : DisplayerCore {
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
        return this
    }

    /**
     * Moves this DisplayerCore along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param x The displacement in x, in pixels.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun moveAlongX(x : Int) : DisplayerCore {
        if(x != 0){
            absoluteX.value += x
            relativeX = null
            resetHorizontalAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Moves this DisplayerCore along the given direction.
     * If the displacement is not zero, resets all alignment constraints.
     * @param y The displacement in y, in pixels.
     * @return This DisplayerCore.
     * @see resetAlignment
     */
    fun moveAlongY(y : Int) : DisplayerCore {
        if(y != 0){
            absoluteY.value += y
            relativeY = null
            resetVerticalAlignment()
            loadBounds()
        }
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see absoluteX
     * @see Action
     */
    fun addXListener(key : Any?, action : Action) : DisplayerCore {
        absoluteX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see absoluteY
     * @see Action
     */
    fun addYListener(key : Any?, action : Action) : DisplayerCore {
        absoluteY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the width of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see w
     * @see Action
     */
    fun addWidthListener(key : Any?, action : Action) : DisplayerCore {
        w.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the height of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see h
     * @see Action
     */
    fun addHeightListener(key : Any?, action : Action) : DisplayerCore {
        h.addListener(key, action)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see absoluteX
     * @see Action
     */
    fun addXListener(action : Action) : DisplayerCore = addXListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the y coordinate of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see absoluteY
     * @see Action
     */
    fun addYListener(action : Action) : DisplayerCore = addYListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the width of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see w
     * @see Action
     */
    fun addWidthListener(action : Action) : DisplayerCore = addWidthListener(action, action)

    /**
     * Adds a listener that will be triggered on a change of the height of this DisplayerCore.
     * The added listener can't be removed. If the listener should be removed at some point, use the (key, action) method.
     * @param action The triggered Action.
     * @return This DisplayerCore.
     * @see h
     * @see Action
     */
    fun addHeightListener(action : Action) : DisplayerCore = addHeightListener(action, action)

    /**
     * Removes a listener of the x coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addXListener
     */
    fun removeXListener(key : Any?) : DisplayerCore {
        absoluteX.removeListener(key)
        return this
    }

    /**
     * Removes a listener of the y coordinate of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addYListener
     */
    fun removeYListener(key : Any?) : DisplayerCore {
        absoluteY.removeListener(key)
        return this
    }

    /**
     * Removes a listener of the width of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addWidthListener
     */
    fun removeWidthListener(key : Any?) : DisplayerCore {
        w.removeListener(key)
        return this
    }

    /**
     * Removes a listener of the height of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addHeightListener
     */
    fun removeHeightListener(key : Any?) : DisplayerCore {
        h.removeListener(key)
        return this
    }

    /**
     * Adds a listener that will be triggered on a change of the x coordinate of the left side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @param action The listener.
     * @return This DisplayerCore.
     * @see leftSideX
     * @see Action
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
     */
    fun addLeftSideListener(action : Action) : DisplayerCore = addLeftSideListener(action, action)

    /**
     * Removes a listener of the x coordinate of the left side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addLeftSideListener
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
     */
    fun addRightSideListener(action : Action) : DisplayerCore = addRightSideListener(action, action)

    /**
     * Removes a listener of the x coordinate of the right side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addRightSideListener
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
     */
    fun addUpSideListener(action : Action) : DisplayerCore = addUpSideListener(action, action)

    /**
     * Removes a listener of the y coordinate of the up side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addUpSideListener
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
     */
    fun addDownSideListener(action : Action) : DisplayerCore = addDownSideListener(action, action)

    /**
     * Removes a listener of the y coordinate of the down side of this DisplayerCore.
     * @param key The key that identifies the listener.
     * @return This DisplayerCore.
     * @see addDownSideListener
     */
    fun removeDownSideListener(key : Any?) : DisplayerCore {
        downSideY.removeListener(key)
        return this
    }

    /**
     * Sets this component's bounds.
     * @see setBounds
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
     * Draws this DisplayerCore on a Graphics context.
     */
    protected abstract fun drawDisplayer(g : Graphics)

}