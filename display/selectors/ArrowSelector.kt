package display.selectors

import display.Button
import display.DEFAULT_COLOR
import display.DEFAULT_FONT
import display.screens.Screen
import geometry.Point
import main.Action
import main.GraphicAction
import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * A class representing a Selector with arrows on the side, RISK/TETRIS-style
 */
class ArrowSelector<T> : GeneralSelector<T> {

    companion object {
        private const val HORIZONTAL_ARROW_WIDTH : Int = 30
        private const val HORIZONTAL_ARROW_HEIGHT : Int = 30
        private const val VERTICAL_ARROW_WIDTH : Int = 30
        private const val VERTICAL_ARROW_HEIGHT : Int = 30
        private val ARROW_COLOR : Color = Color(71, 200, 66) //TODO -- REWRITE
        private val LEFT_ARROW_DRAWER : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = ARROW_COLOR
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h/2, 0, h), 3)
        }}
        private val RIGHT_ARROW_DRAWER : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = ARROW_COLOR
            g.fillPolygon(intArrayOf(0, 0, w), intArrayOf(0, h, h/2), 3)
        }}
        private val UP_ARROW_DRAWER : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = ARROW_COLOR
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(h, 0, h), 3)
        }}
        private val DOWN_ARROW_DRAWER : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = ARROW_COLOR
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(0, h, 0), 3)
        }}
        private const val LINE_THICKNESS : Int = 5
        private const val INTERIOR_DELTA : Int = 2
        private const val EXTERIOR_DELTA : Int = 5
    }

    /**
     * The Action of the 'previous' arrow
     */
    private var previousAction : Action = {this.previous()}
    /**
     * The Action of the 'next' arrow
     */
    private var nextAction : Action = {this.next()}

    override var w : Int = 0
    override var h : Int = 0

    /**
     * Encodes if the arrows are placed horizontally or vertically
     */
    private var isHorizontal : Boolean
    /**
     * The 'previous' arrow
     */
    private var previousArrow : Button
    /**
     * The 'next' arrow
     */
    private var nextArrow : Button

    /**
     * The tet displayed by the selector
     */
    private var displayedText : String = selectedOption().toString()

    constructor(p : Point, options : ArrayList<T>, isHorizontal : Boolean = true) : super(p, options){
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
    }
    constructor(x : Double, y : Double, options: ArrayList<T>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Int, y : Double, options: ArrayList<T>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Double, y : Int, options: ArrayList<T>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Int, y : Int, options: ArrayList<T>, isHorizontal : Boolean = true) : this(Point(x, y), options, isHorizontal)

    /**
     * Goes to the next option
     */
    private fun next(){
        if(currentOption < options.size - 1){
            currentOption++
        }else{
            currentOption = 0
        }
        displayedText = selectedOption().toString()
        initphase = true
    }

    /**
     * Goes to the previous option
     */
    private fun previous(){
        if(currentOption > 0){
            currentOption--
        }else{
            currentOption = options.size - 1
        }
        displayedText = selectedOption().toString()
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
                LEFT_ARROW_DRAWER,
                HORIZONTAL_ARROW_WIDTH,
                HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            Button(
                point.x,
                point.y,
                previousAction,
                DOWN_ARROW_DRAWER,
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
                RIGHT_ARROW_DRAWER,
                HORIZONTAL_ARROW_WIDTH,
                HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            Button(
                point.x,
                point.y,
                nextAction,
                UP_ARROW_DRAWER,
                VERTICAL_ARROW_WIDTH,
                VERTICAL_ARROW_HEIGHT
            )
        }
    }

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

    override fun onAdd(source : Screen) {
        source add previousArrow
        source add nextArrow
    }

    override fun onRemove(source : Screen) {
        source remove previousArrow
        source remove nextArrow
    }

    override fun loadParameters(g: Graphics) {
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)
        w = fm.stringWidth(displayedText) + 2 * (LINE_THICKNESS + INTERIOR_DELTA)
        h = fm.maxAscent + fm.maxDescent + 2 * (LINE_THICKNESS + INTERIOR_DELTA)
        align()
        setArrowsPosition()
    }

    /**
     * Sets the position of the arrows
     */
    private fun setArrowsPosition(){
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

    override fun drawDisplayer(g: Graphics) {
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)
        val textDrawHeight : Int = LINE_THICKNESS + INTERIOR_DELTA + fm.maxAscent
        g.color = DEFAULT_COLOR
        g.font = DEFAULT_FONT
        g.drawString(displayedText, LINE_THICKNESS + INTERIOR_DELTA, textDrawHeight)

        g.fillRect(0, 0, LINE_THICKNESS, h)
        g.fillRect(0, 0, w, LINE_THICKNESS)
        g.fillRect(0, h - LINE_THICKNESS, w,
            LINE_THICKNESS
        )
        g.fillRect(w - LINE_THICKNESS, 0, w, h)
    }

}