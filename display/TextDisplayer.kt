package display

import geometry.Point
import main.GraphicAction
import utilities.copy
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * General class for displayed texts and buttons
 */
abstract class TextDisplayer : Displayer {

    /**
     * The displayed Text, as a list of StringDisplays.
     * @see StringDisplay
     */
    private var txt : ArrayList<StringDisplay> = ArrayList()

    protected var lines : ArrayList<ArrayList<StringDisplay>> = ArrayList()

    /**
     * The maximal allowed line length
     */
    private var maxLineLength : Int? = null

    /**
     * The GraphicAction that draws the background of this Displayer
     */
    protected var backgroundDrawer : GraphicAction

    constructor(p : Point, text : List<StringDisplay>, background : GraphicAction = NO_BACKGROUND) : super(p){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        point = p
        for(s : StringDisplay in text){
            txt.add(s)
        }
        lines = txt.toLinesList()
        backgroundDrawer = background
    }
    constructor(p : Point, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : this(p, arrayListOf<StringDisplay>(text), background)
    constructor(p : Point, text : String, background: GraphicAction = NO_BACKGROUND) : this(p, StringDisplay(text), background)

    /**
     * Sets the maximal line length for this Component
     */
    infix fun setMaxLineLength(length : Int){
        maxLineLength = length
    }

    /**
     * Sets the maximal line length for this Component.
     */
    infix fun setMaxLineLength(length : Double) = setMaxLineLength(length.toInt())

    /**
     * Returns the displayed text
     */
    fun text() : String = txt.collapse()

    /**
     * Forces the component to respect the maximal line length constraint
     */
    protected fun forceMaxLineLength(g : Graphics, delta : Int){ //IF IT WORKS, DON'T TOUCH IT
        if(maxLineLength != null){
            val result : ArrayList<ArrayList<StringDisplay>> = ArrayList()
            val currentLine : ArrayList<StringDisplay> = ArrayList()
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
    infix fun setDisplayedText(text : ArrayList<StringDisplay>){
        txt = if(text.size == 0) arrayListOf(StringDisplay("")) else text
        lines = txt.toLinesList()
        initphase = true
    }

    /**
     * Modifies the displayed text
     */
    infix fun setDisplayedText(text : StringDisplay) = this setDisplayedText arrayListOf(text)

    /**
     * Modifies the displayed text
     */
    infix fun setDisplayedText(text : String) = this setDisplayedText StringDisplay(text)

    /**
     * Computes the total height of the displayed component
     */
    protected fun computeTotalHeight(g : Graphics, delta : Int){
        h = 2 * delta
        for(line : ArrayList<StringDisplay> in lines){
            h += line.lineHeight(g)
        }
    }

    /**
     * Computes the maximal length of the StringDisplays' lines
     */
    protected fun computeMaxLength(g : Graphics, delta : Int){
        var maxLength = 0
        for(line : ArrayList<StringDisplay> in lines){
            val lineLength : Int = line.lineLength(g)
            if(lineLength > maxLength){
                maxLength = lineLength
            }
        }
        w = maxLength + 2 * delta
    }

    /**
     * Draws the background of the component.
     */
    private fun drawBackground(g : Graphics) = backgroundDrawer.invoke(g, w, h)

    override fun drawDisplayer(g : Graphics){
        drawBackground(g)
        drawText(g)
    }

    /**
     * Draws Text on the component.
     */
    protected abstract fun drawText(g : Graphics)

}