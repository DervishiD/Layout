package display

import geometry.Point
import geometry.Vector
import java.awt.FontMetrics
import java.awt.Graphics
import java.lang.IllegalArgumentException
import javax.swing.JLabel

/**
 * General class for displayed texts and buttons
 */
abstract class Displayed : JLabel {

    /**
     * The central Point of the object
     */
    private var point : Point

    /**
     * The width of the component
     */
    protected abstract var w : Int

    /**
     * The height of the component
     */
    protected abstract var h : Int

    /**
     * The displayed Text, as a list of StringDisplays.
     * @see StringDisplay
     */
    private var txt : ArrayList<StringDisplay>

    protected var lines : ArrayList<ArrayList<StringDisplay>>

    /**
     * Detects if the component is being initialized
     */
    private var initphase : Boolean = true

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
     * The maximal allowed line length
     */
    private var maxLineLength : Int? = null

    init{
        setBounds(1, 1, 1, 1)
    }

    constructor(p : Point, text : ArrayList<StringDisplay>) : super(){
        if(text.size == 0) throw IllegalArgumentException("A text displayer must display text.")
        point = p
        txt = text
        lines = txt.toLinesList()
    }
    constructor(p : Point, text : StringDisplay) : this(p, arrayListOf<StringDisplay>(text))
    constructor(p : Point, text : String) : this(p, StringDisplay(text))

    /**
     * Aligns left to the given position
     */
    public infix fun alignLeftTo(position : Int){
        alignLeftTo = position
        alignRightTo = null
    }

    /**
     * Aligns right to the given position
     */
    public infix fun alignRightTo(position : Int){
        alignRightTo = position
        alignLeftTo = null
    }

    /**
     * Aligns up to the given position
     */
    public infix fun alignUpTo(position : Int){
        alignUpTo = position
        alignDownTo = null
    }

    /**
     * Aligns down to the given position
     */
    public infix fun alignDownTo(position : Int){
        alignDownTo = position
        alignUpTo = null
    }

    /**
     * Aligns left to the given position
     */
    public infix fun alignLeftTo(position : Double) = alignLeftTo(position.toInt())

    /**
     * Aligns right to the given position
     */
    public infix fun alignRightTo(position : Double) = alignRightTo(position.toInt())

    /**
     * Aligns up to the given position
     */
    public infix fun alignUpTo(position : Double) = alignUpTo(position.toInt())

    /**
     * Aligns down to the given position
     */
    public infix fun alignDownTo(position : Double) = alignDownTo(position.toInt())

    /**
     * Resets this component's alignment constraints
     */
    public fun resetAlignment(){
        alignLeftTo = null
        alignRightTo = null
        alignUpTo = null
        alignDownTo = null
    }

    /**
     * Sets the maximal line length for this Component
     */
    public infix fun setMaxLineLength(length : Int){
        maxLineLength = length
    }

    /**
     * Sets the maximal line length for this Component.
     */
    public infix fun setMaxLineLength(length : Double) = setMaxLineLength(length.toInt())

    /**
     * Returns the displayed text
     */
    public fun text() : String = txt.collapse()

    /**
     * The center Point
     */
    public fun center() : Point = point

    /**
     * The width of the component
     */
    public fun width() : Int = w

    /**
     * The height of the component
     */
    public fun height() : Int = h

    /**
     * The lowest y coordinate of the component
     */
    public fun lowestY() : Int = (point.y - h / 2).toInt()

    /**
     * The highest y coordinate of the component
     */
    public fun highestY() : Int = (point.y + h / 2).toInt()

    /**
     * The lowest x coordinate of this component
     */
    public fun lowestX() : Int = (point.x - w / 2).toInt()

    /**
     * The highest x coordinate of the component
     */
    public fun highestX() : Int = (point.x + w / 2).toInt()

    /**
     * Forces the component to respect the maximal line length constraint
     */
    protected fun forceMaxLineLength(g : Graphics, delta : Int){ //IF IT WORKS, DON'T TOUCH IT
        if(maxLineLength != null){
            val result : ArrayList<ArrayList<StringDisplay>> = ArrayList<ArrayList<StringDisplay>>()
            val currentLine : ArrayList<StringDisplay> = ArrayList<StringDisplay>()
            var currentDisplay : StringDisplay
            var fm : FontMetrics
            var currentLineLength : Int = 2 * delta
            var currentWord : String
            var currentWordAndSpace : String
            var charLength : Int
            var wordLength : Int
            var wordAndSpaceLength : Int
            var words : List<String>
            var chars : List<String>

            for(line : ArrayList<StringDisplay> in lines){
                for(s : StringDisplay in line){
                    fm = g.getFontMetrics(s.font)
                    words = s.text.split(" ")
                    currentDisplay = StringDisplay("", s.font, s.color)
                    for(i : Int in 0 until words.size){
                        currentWord = words[i]
                        currentWordAndSpace = " $currentWord"
                        wordLength = fm.stringWidth(currentWord)
                        wordAndSpaceLength = fm.stringWidth(currentWordAndSpace)
                        if(currentLineLength + wordAndSpaceLength <= maxLineLength!!){
                            if(i == 0){
                                currentDisplay.push(currentWord)
                                currentLineLength += wordLength
                            }else{
                                currentDisplay.push(currentWordAndSpace)
                                currentLineLength += wordAndSpaceLength
                            }
                        }else{
                            if(i == 0 && currentLineLength + wordLength <= maxLineLength!!){
                                currentDisplay.push(currentWord)
                                currentLine.add(currentDisplay.copy())
                                result.add(currentLine.copy())
                                currentLine.clear()
                                currentDisplay.clear()
                                currentLineLength = 2 * delta
                            }else if(wordLength <= maxLineLength!!){
                                currentLine.add(currentDisplay.copy())
                                result.add(currentLine.copy())
                                currentLine.clear()
                                currentDisplay.clear()
                                currentDisplay.push(currentWord)
                                currentLineLength = 2 * delta + wordLength
                            }else{
                                chars = currentWord.split("")
                                if(i != 0){
                                    val spaceLength = fm.stringWidth(" ")
                                    if(currentLineLength + spaceLength <= maxLineLength!!){
                                        currentDisplay.push(" ")
                                        currentLineLength += spaceLength
                                    }else{
                                        currentLine.add(currentDisplay.copy())
                                        result.add(currentLine.copy())
                                        currentLine.clear()
                                        currentDisplay.clear()
                                        currentLineLength = 2 * delta
                                    }
                                }
                                for(c : String in chars){
                                    charLength = fm.stringWidth(c)
                                    if(currentLineLength + charLength <= maxLineLength!!){
                                        currentDisplay.push(c)
                                        currentLineLength += charLength
                                    }else{
                                        currentLine.add(currentDisplay.copy())
                                        result.add(currentLine.copy())
                                        currentLine.clear()
                                        currentDisplay.clear()
                                        currentDisplay.push(c)
                                        currentLineLength = 2 * delta + charLength
                                    }
                                }
                            }
                        }
                    }
                    currentLine.add(currentDisplay.copy())
                }
                result.add(currentLine.copy())
                currentLine.clear()
                currentLineLength = 2 * delta
            }
            lines = result
        }
    }

    /**
     * Modifies the displayed text
     */
    public infix fun setDisplayedText(text : ArrayList<StringDisplay>){
        if(text.size == 0) throw IllegalArgumentException("A text displayer must display text.")
        txt = text
        lines = txt.toLinesList()
        initphase = true
    }

    /**
     * Modifies the displayed text
     */
    public infix fun setDisplayedText(text : StringDisplay) = this setDisplayedText arrayListOf<StringDisplay>(text)

    /**
     * Modifies the displayed text
     */
    public infix fun setDisplayedText(text : String) = this setDisplayedText StringDisplay(text)

    /**
     * Change this component's center point
     */
    public infix fun moveTo(p : Point){
        point = p
        resetAlignment()
        loadBounds()
    }

    /**
     * Change this component's center point.
     */
    public fun moveTo(x : Int, y : Int) = this moveTo Point(x, y)

    /**
     * Change this component's center point.
     */
    public fun moveTo(x : Double, y : Int) = this moveTo Point(x, y)

    /**
     * Change this component's center point.
     */
    public fun moveTo(x : Int, y : Double) = this moveTo Point(x, y)

    /**
     * Change this component's center point.
     */
    public fun moveTo(x : Double, y : Double) = this moveTo Point(x, y)

    /**
     * Change this component's x coordinate.
     */
    public infix fun setx(x : Int){
        point setx x
        resetAlignment()
        loadBounds()
    }

    /**
     * Change this component's x coordinate.
     */
    public infix fun setx(x : Double) = this setx x.toInt()

    /**
     * Change this component's y coordinate.
     */
    public infix fun sety(y : Int){
        point setx y
        resetAlignment()
        loadBounds()
    }

    /**
     * Change this component's y coordinate.
     */
    public infix fun sety(y : Double) = this sety y.toInt()

    public infix fun moveAlong(v : Vector){
        point += v
        resetAlignment()
        loadBounds()
    }

    /**
     * Aligns the component with the alignment constraints
     */
    private fun align(){
        alignLateral()
        alignVertical()
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
     * Computes the height of a line of StringDisplays
     */
    private fun computeHeight(g : Graphics, line : ArrayList<StringDisplay>) : Int{
        var maxAscent : Int = 0
        var maxDescent : Int = 0
        var fm : FontMetrics
        for(s : StringDisplay in line){
            fm = g.getFontMetrics(s.font)
            if(fm.maxAscent > maxAscent){
                maxAscent = fm.maxAscent
            }
            if(fm.maxDescent > maxDescent){
                maxDescent = fm.maxDescent
            }
        }
        return maxAscent + maxDescent
    }

    /**
     * Returns the ascent of the line
     */
    protected fun ascent(g : Graphics, line : ArrayList<StringDisplay>) : Int{
        var maxAscent : Int = 0
        var fm : FontMetrics
        for(s : StringDisplay in line){
            fm = g.getFontMetrics(s.font)
            if(fm.maxAscent > maxAscent){
                maxAscent = fm.maxAscent
            }
        }
        return maxAscent
    }

    /**
     * Returns the descent of the line.
     */
    protected fun descent(g : Graphics, line : ArrayList<StringDisplay>) : Int{
        var maxDescent : Int = 0
        var fm : FontMetrics
        for(s : StringDisplay in line){
            fm = g.getFontMetrics(s.font)
            if(fm.maxDescent > maxDescent){
                maxDescent = fm.maxDescent
            }
        }
        return maxDescent
    }

    /**
     * Computes the total height of the displayed component
     */
    protected fun computeTotalHeight(g : Graphics, delta : Int){
        h = 2 * delta
        for(line : ArrayList<StringDisplay> in lines){
            h += computeHeight(g, line)
        }
    }

    /**
     * Computes the maximal length of the StringDisplays' lines
     */
    protected fun computeMaxLength(g : Graphics, delta : Int){
        var maxLength = 0
        for(line : ArrayList<StringDisplay> in lines){
            val lineLength : Int = lineLength(g, line)
            if(lineLength > maxLength){
                maxLength = lineLength
            }
        }
        w = maxLength + 2 * delta
    }

    /**
     * Computes the length of a StringDisplay line.
     */
    private fun lineLength(g : Graphics, line : ArrayList<StringDisplay>) : Int{
        var result : Int = 0
        for(s : StringDisplay in line){
            result +=  g.getFontMetrics(s.font).stringWidth(s.text)
        }
        return result
    }

    /**
     * Sets this component's required bounds
     */
    private fun loadBounds() = setBounds(point.intx() - w / 2, point.inty() - h / 2, w, h)

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            loadParameters(g!!)
            align()
            loadBounds()
            initphase = false
        }
        drawBackground(g!!)
        drawText(g)
    }

    /**
     * Loads the necessary parameters during the initialization phase.
     */
    protected abstract fun loadParameters(g : Graphics)

    /**
     * Draws the background of the component.
     */
    protected abstract fun drawBackground(g : Graphics)

    /**
     * Draws Text on the component.
     */
    protected abstract fun drawText(g : Graphics)

}