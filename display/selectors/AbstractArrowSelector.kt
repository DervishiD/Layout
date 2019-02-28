package display.selectors

import display.Button
import display.CustomContainer
import display.Displayer
import geometry.Point
import main.Action
import main.GraphicAction
import java.awt.Color
import java.awt.Graphics

abstract class AbstractArrowSelector<K, T> : Displayer, AbstractSelector<K, T>{

    protected companion object {
        private const val HORIZONTAL_ARROW_WIDTH : Int = 30
        private const val HORIZONTAL_ARROW_HEIGHT : Int = 30
        private const val VERTICAL_ARROW_WIDTH : Int = 30
        private const val VERTICAL_ARROW_HEIGHT : Int = 30
        private val DEFAULT_ARROW_COLOR : Color = Color(71, 200, 66)

        private fun leftArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h/2, 0, h), 3)
        }}
        private fun rightArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, 0, w), intArrayOf(0, h, h/2), 3)
        }}
        private fun upArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(h, 0, h), 3)
        }}
        private fun downArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(0, h, 0), 3)
        }}

        private val DEFAULT_LEFT_ARROW_DRAWER : GraphicAction = leftArrowDrawer()
        private val DEFAULT_RIGHT_ARROW_DRAWER : GraphicAction = rightArrowDrawer()
        private val DEFAULT_UP_ARROW_DRAWER : GraphicAction = upArrowDrawer()
        private val DEFAULT_DOWN_ARROW_DRAWER : GraphicAction = downArrowDrawer()

        protected const val EXTERIOR_DELTA : Int = 5

    }

    override var currentOption: Int = 0

    override var options : ArrayList<Pair<K, T>> = ArrayList()

    /**
     * The Action of the 'previous' arrow
     */
    private var previousAction : Action = {this.previous()}
    /**
     * The Action of the 'next' arrow
     */
    private var nextAction : Action = {this.next()}

    /**
     * Encodes if the arrows are placed horizontally or vertically
     */
    private val isHorizontal : Boolean
    /**
     * The 'previous' arrow
     */
    private var previousArrow : Button
    /**
     * The 'next' arrow
     */
    private var nextArrow : Button

    constructor(p : Point, options : List<Pair<K, T>>, isHorizontal : Boolean = true) : super(p){
        setOptionsList(options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }
    constructor(x : Int, y : Int, options : List<Pair<K, T>>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Double, y : Int, options : List<Pair<K, T>>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Int, y : Double, options : List<Pair<K, T>>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Double, y : Double, options : List<Pair<K, T>>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(p : Point, keys : List<K>, values : List<T>, isHorizontal : Boolean = true) : super(p){
        setOptionsList(keys, values)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }
    constructor(x : Int, y : Int, keys : List<K>, values : List<T>, isHorizontal : Boolean = true) : this(Point(x, y), keys, values, isHorizontal)
    constructor(x : Double, y : Int, keys : List<K>, values : List<T>, isHorizontal : Boolean = true) : this(Point(x, y), keys, values, isHorizontal)
    constructor(x : Int, y : Double, keys : List<K>, values : List<T>, isHorizontal : Boolean = true) : this(Point(x, y), keys, values, isHorizontal)
    constructor(x : Double, y : Double, keys : List<K>, values : List<T>, isHorizontal : Boolean = true) : this(Point(x, y), keys, values, isHorizontal)

    /**
     * Goes to the next option
     */
    protected open fun next(){
        if(currentOption < options.size - 1){
            currentOption++
        }else{
            currentOption = 0
        }
        initphase = true
    }

    /**
     * Goes to the previous option
     */
    protected open fun previous(){
        if(currentOption > 0){
            currentOption--
        }else{
            currentOption = options.size - 1
        }
        initphase = true
    }

    /**
     * Initializes the 'previous' arrow
     */
    private fun initializePreviousArrow() : Button {
        return if(isHorizontal){
            Button(
                point.x,
                point.y,
                previousAction,
                DEFAULT_LEFT_ARROW_DRAWER,
                HORIZONTAL_ARROW_WIDTH,
                HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            Button(
                point.x,
                point.y,
                previousAction,
                DEFAULT_DOWN_ARROW_DRAWER,
                VERTICAL_ARROW_WIDTH,
                VERTICAL_ARROW_HEIGHT
            )
        }
    }

    /**
     * Initializes the 'next' arrow
     */
    private fun initializeNextArrow() : Button {
        return if(isHorizontal){
            Button(
                point.x,
                point.y,
                nextAction,
                DEFAULT_RIGHT_ARROW_DRAWER,
                HORIZONTAL_ARROW_WIDTH,
                HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            Button(
                point.x,
                point.y,
                nextAction,
                DEFAULT_UP_ARROW_DRAWER,
                VERTICAL_ARROW_WIDTH,
                VERTICAL_ARROW_HEIGHT
            )
        }
    }

    /**
     * Sets a new color for both arrows
     */
    infix fun setArrowsColor(color : Color){
        setPreviousArrowColor(color)
        setNextArrowColor(color)
    }

    /**
     * Sets the colour of the 'previous' arrow
     */
    infix fun setPreviousArrowColor(color : Color){
        if(isHorizontal){
            previousArrow.setImage(leftArrowDrawer(color), HORIZONTAL_ARROW_WIDTH, HORIZONTAL_ARROW_HEIGHT)
        }else{
            previousArrow.setImage(downArrowDrawer(color), VERTICAL_ARROW_WIDTH, VERTICAL_ARROW_HEIGHT)
        }
    }

    /**
     * Sets the colour of the 'next' arrow
     */
    infix fun setNextArrowColor(color: Color){
        if(isHorizontal){
            nextArrow.setImage(rightArrowDrawer(color), HORIZONTAL_ARROW_WIDTH, HORIZONTAL_ARROW_HEIGHT)
        }else{
            nextArrow.setImage(upArrowDrawer(color), VERTICAL_ARROW_WIDTH, VERTICAL_ARROW_HEIGHT)
        }
    }

    override fun lowestX() : Int = super.lowestX() - if(isHorizontal) EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH else 0

    override fun highestX(): Int = super.highestX() + if(isHorizontal) EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH else 0

    override fun lowestY() : Int = super.lowestY() - if(!isHorizontal) EXTERIOR_DELTA + VERTICAL_ARROW_HEIGHT else 0

    override fun highestY() : Int = super.highestY() + if(!isHorizontal) EXTERIOR_DELTA + VERTICAL_ARROW_HEIGHT else 0

    override infix fun alignLeftTo(position : Int){
        if(isHorizontal){
            super.alignLeftTo(position + HORIZONTAL_ARROW_WIDTH + EXTERIOR_DELTA)
        }else{
            super.alignLeftTo(position)
        }
    }

    override infix fun alignRightTo(position : Int){
        if(isHorizontal){
            super.alignRightTo(position - HORIZONTAL_ARROW_WIDTH - EXTERIOR_DELTA)
        }else{
            super.alignRightTo(position)
        }
    }

    override infix fun alignUpTo(position : Int){
        if(isHorizontal){
            super.alignUpTo(position)
        }else{
            super.alignUpTo(position + VERTICAL_ARROW_HEIGHT + EXTERIOR_DELTA)
        }
    }

    override infix fun alignDownTo(position : Int){
        if(isHorizontal){
            super.alignDownTo(position)
        }else{
            super.alignDownTo(position - VERTICAL_ARROW_HEIGHT - EXTERIOR_DELTA)
        }
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
     * Sets the position of the arrows
     */
    protected fun setArrowsPosition(){
        if(isHorizontal){
            setHorizontalArrowPosition()
        }else{
            setVerticalArrowPosition()
        }
    }

    /**
     * Sets the position of the arrows if they're horizontally aligned
     */
    private fun setHorizontalArrowPosition(){
        previousArrow.moveTo(point.x - w/2 - EXTERIOR_DELTA - HORIZONTAL_ARROW_WIDTH /2, point.y)
        nextArrow.moveTo(point.x + w/2 + EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH /2, point.y)
    }

    /**
     * Sets the position of the arrows if they're vertically aligned
     */
    private fun setVerticalArrowPosition(){
        previousArrow.moveTo(point.x, point.y + h/2 + EXTERIOR_DELTA + VERTICAL_ARROW_HEIGHT /2)
        nextArrow.moveTo(point.x, point.y - h/2 - EXTERIOR_DELTA - VERTICAL_ARROW_HEIGHT /2)
    }

}