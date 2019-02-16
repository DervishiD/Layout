package display.selectors

import display.Button
import display.CustomContainer
import display.DEFAULT_COLOR
import display.DEFAULT_FONT
import geometry.Point
import main.Action
import main.GraphicAction
import java.awt.Color
import java.awt.Font
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
        private val DEFAULT_ARROW_COLOR : Color = Color(71, 200, 66)

        private fun leftArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h/2, 0, h), 3)
        }}
        private fun rightArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, 0, w), intArrayOf(0, h, h/2), 3)
        }}
        private fun upArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(h, 0, h), 3)
        }}
        private fun downArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(0, h, 0), 3)
        }}

        private val DEFAULT_LEFT_ARROW_DRAWER : GraphicAction = leftArrowDrawer()
        private val DEFAULT_RIGHT_ARROW_DRAWER : GraphicAction = rightArrowDrawer()
        private val DEFAULT_UP_ARROW_DRAWER : GraphicAction = upArrowDrawer()
        private val DEFAULT_DOWN_ARROW_DRAWER : GraphicAction = downArrowDrawer()

        private const val LINE_THICKNESS : Int = 5
        private const val INTERIOR_DELTA : Int = 2
        private const val EXTERIOR_DELTA : Int = 5

        private val DISPLAYED_FONT : Font = DEFAULT_FONT
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

    /**
     * The maximal length of a line
     */
    private var maxLineLength : Int? = null

    /**
     * The displayed lines
     */
    private var lines : ArrayList<String> = lines(displayedText)

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
        lines = lines(displayedText)
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
        lines = lines(displayedText)
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
     * Sets the maximal line length for the Selector
     */
    infix fun setMaxLineLength(length : Int){
        maxLineLength = length
    }

    /**
     * Sets the maximal line length for the Selector
     */
    infix fun setMaxLineLength(length : Double) = setMaxLineLength(length.toInt())

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

    override fun loadParameters(g: Graphics) {
        lines = lines(displayedText)
        forceMaxLineLength(g)
        computeWidth(g)
        computeHeight(g)
        align()
        setArrowsPosition()
    }

    private fun forceMaxLineLength(g : Graphics){
        if(maxLineLength != null){
            val result : ArrayList<String> = ArrayList()
            val fm : FontMetrics = g.getFontMetrics(DISPLAYED_FONT)
            val additionalWidth : Int = 2 * (INTERIOR_DELTA + LINE_THICKNESS) + if(isHorizontal) 2 * (EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH) else 0
            var currentLine : String = ""
            var currentLineLength : Int = additionalWidth
            var words : List<String>
            var currentWord : String
            var currentWordLength : Int
            var currentWordAndSpaceLength : Int
            var chars : List<String>
            var currentCharLength : Int
            for(line : String in lines){
                if(fm.stringWidth(line) + additionalWidth <= maxLineLength!!){ //LINE FITS
                    result.add(line)
                }else{ //LINE DOESN'T FIT
                    words = line.split(" ")
                    for(i : Int in 0 until words.size){
                        currentWord = words[i]
                        currentWordLength = fm.stringWidth(currentWord)
                        currentWordAndSpaceLength = fm.stringWidth(" $currentWord")
                        if(currentLine == ""){ //FIRST WORD OF THE LINE
                            if(currentWordLength + currentLineLength <= maxLineLength!!){ //FIRST WORD FITS
                                currentLine += currentWord
                                currentLineLength += currentWordLength
                            }else{ //FIRST WORD DOESN'T FIT
                                chars = currentWord.split("")
                                println(chars[0])
                                for(char : String in chars){
                                    currentCharLength = fm.stringWidth(char)
                                    if(currentCharLength + currentLineLength <= maxLineLength!!){ //CHAR FITS
                                        currentLine += char
                                        currentLineLength += currentCharLength
                                    }else{ //CHAR DOESN'T FIT
                                        result.add(currentLine)
                                        currentLineLength = additionalWidth + currentCharLength
                                        currentLine = char
                                    }
                                }
                            }
                        }else{ //NOT FIRST WORD OF THE LINE
                            if(currentWordAndSpaceLength + currentLineLength <= maxLineLength!!){ //WORD FITS
                                currentLine += " $currentWord"
                                currentLineLength += currentWordAndSpaceLength
                            }else{ //WORD DOESN'T FIT
                                if(currentWordLength + additionalWidth <= maxLineLength!!){ //WORD FITS IN NEW LINE
                                    result.add(currentLine)
                                    currentLineLength = currentWordLength + additionalWidth
                                    currentLine = currentWord
                                }else{ //WORD DOESN'T FIT IN NEW LINE
                                    chars = currentWord.split("")
                                    if(currentLineLength + fm.stringWidth(" ${chars[0]}") <= maxLineLength!!){ //IF SPACE AND FIRST CHAR FIT
                                        currentLineLength += fm.stringWidth(" ")
                                        currentLine += " "
                                        for(char : String in chars){
                                            currentCharLength = fm.stringWidth(char)
                                            if(currentCharLength + currentLineLength <= maxLineLength!!){ //CHAR FITS
                                                currentLine += char
                                                currentLineLength += currentCharLength
                                            }else{ //CHAR DOESN'T FIT
                                                result.add(currentLine)
                                                currentLineLength = additionalWidth + currentCharLength
                                                currentLine = char
                                            }
                                        }
                                    }else{ //SPACE AND FIRST CHAR DON'T FIT --> START NEW LINE
                                        currentLineLength = additionalWidth
                                        result.add(currentLine)
                                        currentLine = ""
                                        for(char : String in chars){
                                            currentCharLength = fm.stringWidth(char)
                                            if(currentCharLength + currentLineLength <= maxLineLength!!){ //CHAR FITS
                                                currentLine += char
                                                currentLineLength += currentCharLength
                                            }else{ //CHAR DOESN'T FIT
                                                result.add(currentLine)
                                                currentLineLength = additionalWidth + currentCharLength
                                                currentLine = char
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(currentLine != ""){
                    result.add(currentLine)
                    currentLine = ""
                    currentLineLength = additionalWidth
                }
            }
            lines = result
        }
    }

    private fun computeWidth(g : Graphics){
        w = 2 * (INTERIOR_DELTA + LINE_THICKNESS)
        var maxLine : Int = 0
        val fm = g.getFontMetrics(DISPLAYED_FONT)
        for(line : String in lines){
            val lineWidth : Int = fm.stringWidth(line)
            if(lineWidth > maxLine){
                maxLine = lineWidth
            }
        }
        w += maxLine
    }

    private fun computeHeight(g : Graphics){
        h = 2 * (INTERIOR_DELTA + LINE_THICKNESS)
        val fm : FontMetrics = g.getFontMetrics(DISPLAYED_FONT)
        for(i : Int in 1..lines.size){
            h += fm.maxAscent + fm.maxDescent
        }
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
        val fm : FontMetrics = g.getFontMetrics(DISPLAYED_FONT)
        var textDrawHeight : Int = LINE_THICKNESS + INTERIOR_DELTA + fm.maxAscent
        g.color = DEFAULT_COLOR
        g.font = DISPLAYED_FONT
        for(line : String in lines){
            g.drawString(line, LINE_THICKNESS + INTERIOR_DELTA, textDrawHeight)
            textDrawHeight += fm.maxDescent + fm.maxAscent
        }

        g.fillRect(0, 0, LINE_THICKNESS, h)
        g.fillRect(0, 0, w, LINE_THICKNESS)
        g.fillRect(0, h - LINE_THICKNESS, w, LINE_THICKNESS)
        g.fillRect(w - LINE_THICKNESS, 0, w, h)
        setArrowsPosition()
    }

    /**
     * Splits the given text in lines
     */
    private fun lines(text : String) : ArrayList<String>{
        val result : ArrayList<String> = ArrayList()
        result.addAll(text.split("\n"))
        return result
    }

}