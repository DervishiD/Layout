package display.selectors

import display.*
import geometry.Point
import main.GraphicAction
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.FontMetrics
import java.awt.Graphics
import utilities.copy

/**
 * An Arrow Selector that displays text
 */
class TextArrowSelector<T> : AbstractArrowSelector<List<StringDisplay>, T> {

    companion object {
        private const val DEFAULT_LINE_THICKNESS : Int = 5
        private val DEFAULT_LINE_COLOR : Color = BLACK
        private val DEFAULT_BACKGROUND : GraphicAction = { g: Graphics, w: Int, h: Int -> run{
            g.color = DEFAULT_LINE_COLOR
            g.fillRect(0, 0, DEFAULT_LINE_THICKNESS, h)
            g.fillRect(0, 0, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(0, h - DEFAULT_LINE_THICKNESS, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(w - DEFAULT_LINE_THICKNESS, 0, w, h)
        }}
        private const val SIDE_DELTA : Int = 7
    }

    /**
     * The list of displays, in the form of the displayed lines, the width and the height
     * of the component for each value
     */
    private var linesList : ArrayList<Triple<List<List<StringDisplay>>, Int, Int>> = ArrayList()

    /**
     * The background
     */
    private var backgroundDrawer : GraphicAction = DEFAULT_BACKGROUND

    /**
     * The maximal allowed line length
     */
    private var maxLineLength : Int? = null

    constructor(p : Point, options : Map<List<StringDisplay>, T>, isHorizontal: Boolean = true) : super(p, options.keys.toList(), options.values.toList(), isHorizontal){
        for(text : List<StringDisplay> in options.keys){
            linesList.add(Triple(text.toLinesList(), 0, 0))
        }
    }
    constructor(x : Int, y : Int, options : Map<List<StringDisplay>, T>, isHorizontal: Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Int, y : Double, options : Map<List<StringDisplay>, T>, isHorizontal: Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Double, y : Int, options : Map<List<StringDisplay>, T>, isHorizontal: Boolean = true) : this(Point(x, y), options, isHorizontal)
    constructor(x : Double, y : Double, options : Map<List<StringDisplay>, T>, isHorizontal: Boolean = true) : this(Point(x, y), options, isHorizontal)

    /*TODO -- ADD FOLLOWING CONSTRUCTORS FOR P X Y BY SOME WAY MAYBE
    * LSD - LT
    * LS - LT
    * LT
    * MLST -- INCOMPATIBLE
    * MSDT
    * MST
    * */

    /**
     * Changes the background of this Displayer
     */
    infix fun setBackground(background : GraphicAction){
        backgroundDrawer = background
    }

    /**
     * Sets the maximal line length for this Component
     */
    infix fun setMaxLineLength(length : Int){
        maxLineLength = length
    }

    override fun next(){
        super.next()
        reloadDimensions()
    }

    override fun previous(){
        super.previous()
        reloadDimensions()
    }

    /**
     * Changes the dimensions of this Selector
     */
    private fun reloadDimensions(){
        w = linesList[currentOption].second
        h = linesList[currentOption].third
    }

    /**
     * Sets the maximal line length for this Component.
     */
    infix fun setMaxLineLength(length : Double) = setMaxLineLength(length.toInt())

    override fun loadParameters(g: Graphics) {
        forceMaxLineLength(g)
        computeDimensions(g)
        align()
        setArrowsPosition()
    }

    /**
     * Forces the max line length constraint
     */
    private infix fun forceMaxLineLength(g : Graphics){
        if(maxLineLength != null){
            val finalResult : ArrayList<Triple<List<List<StringDisplay>>, Int, Int>> = ArrayList()
            val currentLine : MutableList<StringDisplay> = mutableListOf()
            var currentDisplay : StringDisplay
            var fm : FontMetrics
            var currentLineLength : Int = 2 * SIDE_DELTA
            var currentWord : String
            var currentWordAndSpace : String
            var charLength : Int
            var wordLength : Int
            var wordAndSpaceLength : Int
            var words : List<String>
            var chars : List<String>

            for(triple : Triple<List<List<StringDisplay>>, Int, Int> in linesList){
                val result : ArrayList<ArrayList<StringDisplay>> = ArrayList()
                for(line : List<StringDisplay> in triple.first){
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
                                    currentLineLength = 2 * SIDE_DELTA
                                }else if(wordLength <= maxLineLength!!){
                                    currentLine.add(currentDisplay.copy())
                                    result.add(currentLine.copy())
                                    currentLine.clear()
                                    currentDisplay.clear()
                                    currentDisplay.push(currentWord)
                                    currentLineLength = 2 * SIDE_DELTA + wordLength
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
                                            currentLineLength = 2 * SIDE_DELTA
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
                                            currentLineLength = 2 * SIDE_DELTA + charLength
                                        }
                                    }
                                }
                            }
                        }
                        currentLine.add(currentDisplay.copy())
                    }
                    result.add(currentLine.copy())
                    currentLine.clear()
                    currentLineLength = 2 * SIDE_DELTA
                }
                finalResult.add(Triple(result, 0, 0))
            }
            linesList = finalResult
        }
    }

    /**
     * Loads the dimensions associated with each value
     */
    private fun computeDimensions(g : Graphics){
        val result : ArrayList<Triple<List<List<StringDisplay>>, Int, Int>> = ArrayList()
        for(triple : Triple<List<List<StringDisplay>>, Int, Int> in linesList){
            var width : Int = 0
            var height : Int = 2 * SIDE_DELTA
            for(line : List<StringDisplay> in triple.first){
                val lineLength : Int = line.lineLength(g)
                if(lineLength > width){
                    width = lineLength
                }
                height += line.lineHeight(g)
            }
            width += 2 * SIDE_DELTA
            result.add(Triple(triple.first, width, height))
        }
        linesList = result
        reloadDimensions()
    }

    override fun drawDisplayer(g: Graphics) {
        drawBackground(g)
        drawText(g)
    }

    /**
     * Draws the background of the displayer
     */
    private fun drawBackground(g : Graphics) = backgroundDrawer.invoke(g, w, h)

    /**
     * Draws the text of the displayer
     */
    private fun drawText(g : Graphics){
        var currentX : Int = SIDE_DELTA
        var currentY : Int = SIDE_DELTA

        for(line : List<StringDisplay> in linesList[currentOption].first){
            currentY += line.ascent(g)
            for(s : StringDisplay in line){
                g.font = s.font
                g.color = s.color
                g.drawString(s.text, currentX, currentY)
                currentX += g.getFontMetrics(s.font).stringWidth(s.text)
            }
            currentX = SIDE_DELTA
            currentY += line.descent(g)
        }
    }

}