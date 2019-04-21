package llayout.displayers

import llayout.interfaces.AbstractSelector
import llayout.Action
import llayout.interfaces.CustomContainer
import llayout.GraphicAction
import llayout.geometry.Point
import llayout.utilities.LProperty
import java.awt.Color
import java.awt.Graphics

/**
 * An abstract implementation of an Abstract Selector, that is, an object that lets the user select
 * values from a list by using to arrows, 'next' and 'previous'.
 * It is a Displayer, meaning that it can be added to a Screen.
 * The type parameter T is the type of the options to choose from.
 * @see previous
 * @see next
 * @see Displayer
 * @see AbstractSelector
 */
abstract class AbstractArrowSelector<T> : Displayer, AbstractSelector<T> {

    protected companion object {
        /**
         * The width of a horizontal arrow.
         */
        private const val HORIZONTAL_ARROW_WIDTH : Int = 30
        /**
         * The height of a horizontal arrow.
         */
        private const val HORIZONTAL_ARROW_HEIGHT : Int = 30
        /**
         * The width of a vertical arrow.
         */
        private const val VERTICAL_ARROW_WIDTH : Int = 30
        /**
         * The height of a vertical arrow.
         */
        private const val VERTICAL_ARROW_HEIGHT : Int = 30
        /**
         * The default Color of the arrows.
         */
        private val DEFAULT_ARROW_COLOR : Color = Color(71, 200, 66)

        /**
         * A function that produces a GraphicAction that draws a left arrow from the given Color.
         * @param color The Color of the given arrow.
         * @see HORIZONTAL_ARROW_WIDTH
         * @see HORIZONTAL_ARROW_HEIGHT
         * @see GraphicAction
         */
        private fun leftArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h/2, 0, h), 3)
        }}
        /**
         * A function that produces a GraphicAction that draws a right arrow from the given Color.
         * @param color The Color of the given arrow.
         * @see HORIZONTAL_ARROW_WIDTH
         * @see HORIZONTAL_ARROW_HEIGHT
         * @see GraphicAction
         */
        private fun rightArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, 0, w), intArrayOf(0, h, h/2), 3)
        }}
        /**
         * A function that produces a GraphicAction that draws an up arrow from the given Color.
         * @param color The Color of the given arrow.
         * @see VERTICAL_ARROW_WIDTH
         * @see VERTICAL_ARROW_HEIGHT
         * @see GraphicAction
         */
        private fun upArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(h, 0, h), 3)
        }}
        /**
         * A function that produces a GraphicAction that draws a down arrow from the given Color.
         * @param color The Color of the given arrow.
         * @see VERTICAL_ARROW_WIDTH
         * @see VERTICAL_ARROW_HEIGHT
         * @see GraphicAction
         */
        private fun downArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(0, h, 0), 3)
        }}

        /**
         * The default GraphicAction that draws a left arrow.
         * @see GraphicAction
         */
        private val DEFAULT_LEFT_ARROW_DRAWER : GraphicAction =
                leftArrowDrawer()
        /**
         * The default GraphicAction that draws a right arrow.
         * @see GraphicAction
         */
        private val DEFAULT_RIGHT_ARROW_DRAWER : GraphicAction =
                rightArrowDrawer()
        /**
         * The default GraphicAction that draws an up arrow.
         * @see GraphicAction
         */
        private val DEFAULT_UP_ARROW_DRAWER : GraphicAction = upArrowDrawer()
        /**
         * The default GraphicAction that draws a down arrow.
         * @see GraphicAction
         */
        private val DEFAULT_DOWN_ARROW_DRAWER : GraphicAction =
                downArrowDrawer()

        /**
         * The distance, in pixels, between the border of this Displayer and the
         * border of the arrows.
         */
        protected const val EXTERIOR_DELTA : Int = 5

    }

    override var currentOptionIndex: LProperty<Int> = LProperty(0)

    override val options: MutableList<T> = mutableListOf()

    /**
     * The Action of the 'previous' arrow, selects the option before the current
     * one in the list.
     * @see previous
     */
    private var previousAction : Action = {this.previous()}
    /**
     * The Action of the 'next' arrow, selects the option after the current
     * one in the list.
     * @see next
     */
    private var nextAction : Action = {this.next()}

    /**
     * Encodes if the arrows are placed horizontally or vertically
     */
    private val isHorizontal : Boolean
    /**
     * The 'previous' arrow
     */
    private var previousArrow : ImageButton
    /**
     * The 'next' arrow
     */
    private var nextArrow : ImageButton

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Int, y : Int, options : Collection<T>, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The vararg list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Int, y : Int, vararg options : T, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(*options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Int, y : Double, options : Collection<T>, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The vararg list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Int, y : Double, vararg options : T, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(*options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Double, y : Int, options : Collection<T>, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The vararg list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Double, y : Int, vararg options : T, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(*options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Double, y : Double, options : Collection<T>, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The vararg list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(x : Double, y : Double, vararg options : T, isHorizontal : Boolean = true) : super(x, y){
        addOptionsList(*options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(p : Point, options : Collection<T>, isHorizontal : Boolean = true) : super(p){
        addOptionsList(options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Creates an ArrowSelector at the given position, with the given list of options, and
     * horizontal if isHorizontal is true.
     * @param p The position of the Selector
     * @param options The vararg list of options of this Selector.
     * @param isHorizontal True if the Selector is horizontal, false if it is vertical.
     * @see Displayer
     */
    constructor(p : Point, vararg options : T, isHorizontal : Boolean = true) : super(p){
        addOptionsList(*options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }

    /**
     * Sets the current selection to the next value in the list.
     */
    protected open fun next(){
        if(currentOptionIndex.value < optionsNumber() - 1){
            currentOptionIndex.value++
        }else{
            currentOptionIndex.value = 0
        }
        initphase = true
    }

    /**
     * Sets the current selection to the previous value in the list.
     */
    protected open fun previous(){
        if(currentOptionIndex.value > 0){
            currentOptionIndex.value--
        }else{
            currentOptionIndex.value = optionsNumber() - 1
        }
        initphase = true
    }

    /**
     * Initializes the 'previous' arrow.
     * @see previous
     * @see isHorizontal
     */
    private fun initializePreviousArrow() : ImageButton {
        return if(isHorizontal){
            ImageButton(
                    centerX(), centerY(),
                    HORIZONTAL_ARROW_WIDTH,
                    HORIZONTAL_ARROW_HEIGHT,
                    previousAction,
                    DEFAULT_LEFT_ARROW_DRAWER
            )
        }else{
            ImageButton(
                    centerX(), centerY(),
                    VERTICAL_ARROW_WIDTH,
                    VERTICAL_ARROW_HEIGHT,
                    previousAction,
                    DEFAULT_DOWN_ARROW_DRAWER
            )
        }
    }

    /**
     * Initializes the 'next' arrow.
     * @see next
     * @see isHorizontal
     */
    private fun initializeNextArrow() : ImageButton {
        return if(isHorizontal){
            ImageButton(
                    centerX(), centerY(),
                    HORIZONTAL_ARROW_WIDTH,
                    HORIZONTAL_ARROW_HEIGHT,
                    nextAction,
                    DEFAULT_RIGHT_ARROW_DRAWER
            )
        }else{
            ImageButton(
                    centerX(), centerY(),
                    VERTICAL_ARROW_WIDTH,
                    VERTICAL_ARROW_HEIGHT,
                    nextAction,
                    DEFAULT_UP_ARROW_DRAWER
            )
        }
    }

    /**
     * Sets a new color for both arrows.
     * @see setPreviousArrowColor
     * @see setNextArrowColor
     */
    infix fun setArrowsColor(color : Color) : AbstractArrowSelector<T> {
        setPreviousArrowColor(color)
        setNextArrowColor(color)
        return this
    }

    /**
     * Sets the colour of the 'previous' arrow.
     * @see leftArrowDrawer
     * @see downArrowDrawer
     */
    infix fun setPreviousArrowColor(color : Color) : AbstractArrowSelector<T> {
        if(isHorizontal){
            previousArrow.setImage(
                    leftArrowDrawer(color),
                    HORIZONTAL_ARROW_WIDTH,
                    HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            previousArrow.setImage(
                    downArrowDrawer(color),
                    VERTICAL_ARROW_WIDTH,
                    VERTICAL_ARROW_HEIGHT
            )
        }
        return this
    }

    /**
     * Sets the colour of the 'next' arrow.
     * @see rightArrowDrawer
     * @see upArrowDrawer
     */
    infix fun setNextArrowColor(color: Color) : AbstractArrowSelector<T> {
        if(isHorizontal){
            nextArrow.setImage(
                    rightArrowDrawer(color),
                    HORIZONTAL_ARROW_WIDTH,
                    HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            nextArrow.setImage(
                    upArrowDrawer(color),
                    VERTICAL_ARROW_WIDTH,
                    VERTICAL_ARROW_HEIGHT
            )
        }
        return this
    }

    override fun lowestX() : Int = super.lowestX() - if(isHorizontal) EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH else 0

    override fun highestX(): Int = super.highestX() + if(isHorizontal) EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH else 0

    override fun lowestY() : Int = super.lowestY() - if(!isHorizontal) EXTERIOR_DELTA + VERTICAL_ARROW_HEIGHT else 0

    override fun highestY() : Int = super.highestY() + if(!isHorizontal) EXTERIOR_DELTA + VERTICAL_ARROW_HEIGHT else 0

    override infix fun alignLeftTo(position : Int) : AbstractArrowSelector<T> {
        if(isHorizontal){
            super.alignLeftTo(position + HORIZONTAL_ARROW_WIDTH + EXTERIOR_DELTA)
        }else{
            super.alignLeftTo(position)
        }
        return this
    }

    override infix fun alignRightTo(position : Int) : AbstractArrowSelector<T> {
        if(isHorizontal){
            super.alignRightTo(position - HORIZONTAL_ARROW_WIDTH - EXTERIOR_DELTA)
        }else{
            super.alignRightTo(position)
        }
        return this
    }

    override infix fun alignUpTo(position : Int) : AbstractArrowSelector<T> {
        if(isHorizontal){
            super.alignUpTo(position)
        }else{
            super.alignUpTo(position + VERTICAL_ARROW_HEIGHT + EXTERIOR_DELTA)
        }
        return this
    }

    override infix fun alignDownTo(position : Int) : AbstractArrowSelector<T> {
        if(isHorizontal){
            super.alignDownTo(position)
        }else{
            super.alignDownTo(position - VERTICAL_ARROW_HEIGHT - EXTERIOR_DELTA)
        }
        return this
    }

    override fun onAdd(source : CustomContainer) {
        source add previousArrow
        source add nextArrow
    }

    override fun onRemove(source : CustomContainer) {
        source remove previousArrow
        source remove nextArrow
    }

    /**
     * Sets the position of the arrows to align them correctly with this Selector.
     * @see setHorizontalArrowPosition
     * @see setVerticalArrowPosition
     */
    protected fun setArrowsPosition(){
        if(isHorizontal){
            setHorizontalArrowPosition()
        }else{
            setVerticalArrowPosition()
        }
    }

    /**
     * Sets the position of the arrows if they're horizontally aligned, to align them
     * correctly with this Selector
     */
    private fun setHorizontalArrowPosition(){
        previousArrow.moveTo(centerX() - width()/2 - EXTERIOR_DELTA - HORIZONTAL_ARROW_WIDTH /2, centerY())
        nextArrow.moveTo(centerX() + width()/2 + EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH /2, centerY())
    }

    /**
     * Sets the position of the arrows if they're vertically aligned, to align them
     * correctly with this Selector
     */
    private fun setVerticalArrowPosition(){
        previousArrow.moveTo(centerX(), centerY() + height()/2 + EXTERIOR_DELTA + VERTICAL_ARROW_HEIGHT /2)
        nextArrow.moveTo(centerX(), centerY() - height()/2 - EXTERIOR_DELTA - VERTICAL_ARROW_HEIGHT /2)
    }

}