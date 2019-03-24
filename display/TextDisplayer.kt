package display

import geometry.Point
import main.GraphicAction
import utilities.*
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * An abstract class representing the Displayers that display text.
 * @see Displayer
 * @see StringDisplay
 * @see Text
 */
abstract class TextDisplayer : Displayer {

    companion object {

        /**
         * The default background of a TextDisplayer object, i.e. nothing.
         * @see GraphicAction
         * @see backgroundDrawer
         */
        @JvmStatic val NO_BACKGROUND : GraphicAction = { _, _, _ ->  }

    }

    /**
     * The displayed Text, as a list of StringDisplays.
     * @see StringDisplay
     */
    private var txt : MutableCollection<StringDisplay> = mutableListOf()

    /**
     * The displayed text, as a collection of lines.
     * @see txt
     * @see toLinesList
     * @see StringDisplay
     */
    protected var lines : MutableCollection<List<StringDisplay>> = ArrayList()

    /**
     * The maximal allowed line length of this TextDisplayer.
     * @see setMaxLineLength
     * @see forceMaxLineLength
     */
    private var maxLineLength : Int? = null

    /**
     * The GraphicAction that draws the background of this TextDisplayer.
     * @see GraphicAction
     * @see drawBackground
     */
    protected var backgroundDrawer : GraphicAction

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : Collection<StringDisplay>, background : GraphicAction = NO_BACKGROUND) : super(p){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        point = p
        for(s : StringDisplay in text){
            txt.add(s)
        }
        lines = txt.toLinesList()
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : this(p, arrayListOf<StringDisplay>(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a String.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : String, background: GraphicAction = NO_BACKGROUND) : this(p, StringDisplay(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Text object.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : Text, background: GraphicAction = NO_BACKGROUND) : super(p){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        point = p
        for(line in text.asLines()){
            lines.add(line)
            for(sd in line){
                txt.add(sd)
            }
        }
        backgroundDrawer = background
    }

    /**
     * Sets the maximal line length for this TextDisplayer.
     * @see maxLineLength
     * @see forceMaxLineLength
     */
    infix fun setMaxLineLength(length : Int){
        maxLineLength = length
    }

    /**
     * Sets the maximal line length for this TextDisplayer.
     * @see maxLineLength
     * @see forceMaxLineLength
     */
    infix fun setMaxLineLength(length : Double) = setMaxLineLength(length.toInt())

    /**
     * Returns the displayed text as a String.
     * @return The displayed text, as a String.
     * @see txt
     */
    fun text() : String = txt.collapse()

    /**
     * Forces the max line length constraint, i.e. The TextDisplayer must fit in the given width.
     * @param g The Graphics context of the Selector.
     * @see maxLineLength
     */
    protected fun forceMaxLineLength(g : Graphics, delta : Int){ //IF IT WORKS, DON'T TOUCH IT
        if(maxLineLength != null){
            val result : MutableList<List<StringDisplay>> = mutableListOf()
            val currentLine : MutableList<StringDisplay> = mutableListOf()
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

            for(line : List<StringDisplay> in lines){
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
     * Sets the displayed text.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @see StringDisplay
     */
    infix fun setDisplayedText(text : Collection<StringDisplay>){
        txt = if(text is MutableCollection<StringDisplay>) text else text.toMutableCollection()
        lines = txt.toLinesList()
        initphase = true
    }

    /**
     * Sets the displayed text.
     * @param text The displayed text, as a StringDisplay.
     * @see StringDisplay
     */
    infix fun setDisplayedText(text : StringDisplay) = this setDisplayedText arrayListOf(text)

    /**
     * Sets the displayed text.
     * @param text The displayed text, as a String.
     */
    infix fun setDisplayedText(text : String) = this setDisplayedText StringDisplay(text)

    /**
     * Sets the displayed text.
     * @param text The displayed text, as a Text object.
     * @see Text
     */
    infix fun setDisplayedText(text : Text){
        txt = mutableListOf()
        for(line in text.asLines()){
            txt.addAll(line)
        }
        lines = text.asLines().toMutableCollection()
        initphase = true
    }

    /**
     * Computes the total height of the TextDisplayer.
     * @param g The Graphics context.
     * @param delta The distance between the text and the vertical bounds.
     */
    protected fun computeTotalHeight(g : Graphics, delta : Int){
        h = 2 * delta
        for(line : List<StringDisplay> in lines){
            h += line.lineHeight(g)
        }
    }

    /**
     * Computes the maximal length of the StringDisplays' lines.
     * @param g The Graphics context.
     * @param delta The distance between the text and the horizontal bounds.
     */
    protected fun computeMaxLength(g : Graphics, delta : Int){
        var maxLength = 0
        for(line : List<StringDisplay> in lines){
            val lineLength : Int = line.lineLength(g)
            if(lineLength > maxLength){
                maxLength = lineLength
            }
        }
        w = maxLength + 2 * delta
    }

    /**
     * Draws the background of the component.
     * @param g The Graphics context.
     * @see backgroundDrawer
     */
    private fun drawBackground(g : Graphics) = backgroundDrawer.invoke(g, w, h)

    override fun drawDisplayer(g : Graphics){
        drawBackground(g)
        drawText(g)
    }

    /**
     * Draws the text of the TextDisplayer.
     * @param g The Graphics context.
     */
    protected abstract fun drawText(g : Graphics)

}