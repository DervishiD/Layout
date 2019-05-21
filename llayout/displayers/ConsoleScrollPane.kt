package llayout.displayers

import llayout.DEFAULT_SMALL_FONT
import llayout.utilities.*
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.lang.IllegalArgumentException

class ConsoleScrollPane : ResizableDisplayer {

    private companion object{

        /**
         * The number of pixels scrolled per mouse scroll unit.
         * @see onMouseWheelMovedAction
         */
        private const val PIXELS_PER_UNIT_SCROLLED : Int = 10

        private val DEFAULT_FONT : Font = DEFAULT_SMALL_FONT

    }

    private var textFont : Font

    /**
     * The total height of the lines contained by this [ConsoleScrollPane].
     * @see lines
     */
    private var totalHeight : Int = 0

    private var stableText : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * The lines of text displayed by this [ConsoleScrollPane].
     */
    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * When something is written at the end of the liens, the [ConsoleScrollPane] doesn't need to
     * update every line, only those that follow a certain index.
     * indexToVerify is the index of the first line that must be verified.
     * When modified, it calls the [initialize] method of this [ConsoleScrollPane].
     * @see LProperty
     * @see initialize
     */
    private var indexToVerify : LProperty<Int?> = LProperty<Int?>(null).addListener{initialize()}

    private var resetLines : Boolean = false

    /**
     * The 'level of scrolling' is encoded by the coordinate, in pixels, of a point. It is zero if the
     * first line is at the top of the [ConsoleScrollPane] and decreases linearly as the user scrolls down,
     * pushing the lines upwards.
     * @see lines
     */
    private var scrollReference : Int = 0

    /**
     * This variable encodes the index of the first line to be actually displayed on this [ConsoleScrollPane].
     * @see lines
     */
    private var lowerDrawingIndex : Int = 0

    /**
     * This variable encodes the index of the last line to be actually displayed on this [ConsoleScrollPane].
     * @see lines
     */
    private var higherDrawingIndex : Int = 0

    /**
     * This variable encodes the coordinate of the top of the first line drawn on this [ConsoleScrollPane].
     * @see lowerDrawingIndex
     */
    private var drawingStartPosition : Int = 0

    private var ascent : Int = 0

    private var descent : Int = 0

    private var lineHeight : Int = 0

    private var prompt : Collection<StringDisplay> = setOf()

    /*
     * If the width is modified, all the lines must be rearranged.
     */
    init{
        w.addListener{
            resetLines = true
            initialize()
        }
        h.addListener{
            recalculateDrawingParameters()
            verifyScrollReference()
        }
        setOnMouseWheelMovedAction { e -> run{
            if(totalHeight > height()){
                scrollReference -= e.unitsToScroll * PIXELS_PER_UNIT_SCROLLED
                verifyScrollReference()
            }
            recalculateDrawingParameters()
        } }
    }

    constructor(width : Int, height : Int, font : Font = DEFAULT_FONT) : super(width, height){
        textFont = font
    }

    constructor(width : Double, height : Int, font : Font = DEFAULT_FONT) : super(width, height){
        textFont = font
    }

    constructor(width : Int, height : Double, font : Font = DEFAULT_FONT) : super(width, height){
        textFont = font
    }

    constructor(width : Double, height : Double, font : Font = DEFAULT_FONT) : super(width, height){
        textFont = font
    }

    /**
     * Adds a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln() : ConsoleScrollPane{
        lines.add(mutableListOf())
        lines.last().addAll(prompt)
        stableText.add(mutableListOf())
        stableText.last().addAll(prompt)
        setlineToUpdate(lines.size - 1)
        return this
    }

    /**
     * Adds a StringDisplay in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     * @see StringDisplay
     */
    fun writeln(s : StringDisplay) : ConsoleScrollPane{
        writeln()
        return write(s)
    }

    /**
     * Adds a String in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(s : CharSequence) : ConsoleScrollPane = this.writeln(StringDisplay(s))

    /**
     * Adds a Char in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(c : Char) : ConsoleScrollPane = this.writeln(StringDisplay(c))

    /**
     * Adds an Int in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(i : Int) : ConsoleScrollPane = this.writeln(StringDisplay(i))

    /**
     * Adds a Double in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(d : Double) : ConsoleScrollPane = this.writeln(StringDisplay(d))

    /**
     * Adds a Float in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(f : Float) : ConsoleScrollPane = this.writeln(StringDisplay(f))

    /**
     * Adds a Long in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(l : Long) : ConsoleScrollPane = this.writeln(StringDisplay(l))

    /**
     * Adds a Short in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(s : Short) : ConsoleScrollPane = this.writeln(StringDisplay(s))

    /**
     * Adds a Byte in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(b : Byte) : ConsoleScrollPane = this.writeln(StringDisplay(b))

    /**
     * Adds a Boolean in a new line to the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun writeln(b : Boolean) : ConsoleScrollPane = this.writeln(StringDisplay(b))

    /**
     * Adds a [StringDisplay] in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     * @see StringDisplay
     */
    fun write(s : StringDisplay) : ConsoleScrollPane{
        if(lines.size == 0) lines.add(mutableListOf())
        if(stableText.size == 0) stableText.add(mutableListOf())
        s.font = textFont
        lines[lines.size - 1].add(s)
        stableText[stableText.size - 1].add(s)
        setlineToUpdate(lines.size - 1)
        scrollToBottom()
        return this
    }

    /**
     * Adds a String in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(s : CharSequence) : ConsoleScrollPane = this.write(StringDisplay(s))

    /**
     * Adds a Char in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(c : Char) : ConsoleScrollPane = this.write(StringDisplay(c))

    /**
     * Adds an Int in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(i : Int) : ConsoleScrollPane = this.write(StringDisplay(i))

    /**
     * Adds a Double in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(d : Double) : ConsoleScrollPane = this.write(StringDisplay(d))

    /**
     * Adds a Float in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(f : Float) : ConsoleScrollPane = this.write(StringDisplay(f))

    /**
     * Adds a Long in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(l : Long) : ConsoleScrollPane = this.write(StringDisplay(l))

    /**
     * Adds a Short in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(s : Short) : ConsoleScrollPane = this.write(StringDisplay(s))

    /**
     * Adds a Byte in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(b : Byte) : ConsoleScrollPane = this.write(StringDisplay(b))

    /**
     * Adds a Boolean in the last line of the list.
     * @return This ConsoleScrollPane
     * @see lines
     */
    fun write(b : Boolean) : ConsoleScrollPane = this.write(StringDisplay(b))

    fun setPrompt(vararg prompt : CharSequence) : ConsoleScrollPane{
        for(sd : CharSequence in prompt){
            if(sd.contains("\n")) throw IllegalArgumentException("Prompt contains a \"\\n\" character.")
        }
        val newPrompt : MutableCollection<StringDisplay> = mutableSetOf()
        for(p : CharSequence in prompt){
            newPrompt.add(StringDisplay(p))
        }
        this.prompt = newPrompt
        return this
    }

    fun setPrompt(vararg prompt : StringDisplay) : ConsoleScrollPane{
        for(sd : StringDisplay in prompt){
            if(sd.contains("\n")) throw IllegalArgumentException("Prompt contains a \"\\n\" character.")
            sd.font = textFont
        }
        this.prompt = prompt.asList()
        return this
    }

    fun setPrompt(prompt : Collection<StringDisplay>) : ConsoleScrollPane{
        for(sd : StringDisplay in prompt){
            if(sd.contains("\n")) throw IllegalArgumentException("Prompt contains a \"\\n\" character.")
            sd.font = textFont
        }
        this.prompt = prompt
        return this
    }

    fun clearConsole() : ConsoleScrollPane{
        lines.clear()
        stableText.clear()
        return this
    }

    /**
     * Scrolls to the bottom of the text.
     * @see lines
     * @see onMouseWheelMoved
     */
    fun scrollToBottom() : ConsoleScrollPane{
        scrollReference = if(totalHeight <= height) 0 else height() - totalHeight
        recalculateDrawingParameters()
        return this
    }

    /**
     * Sets the first index of the lines to update.
     * @see indexToVerify
     */
    private fun setlineToUpdate(index : Int){
        if(indexToVerify.value == null || indexToVerify.value!! > index) indexToVerify.value = index
    }

    /**
     * Verifies that this [ConsoleScrollPane] doesn't scroll too far.
     * @see scrollReference
     */
    private fun verifyScrollReference(){
        if(scrollReference > 0 || totalHeight < height()){
            scrollReference = 0
        }else if(scrollReference < height() - totalHeight){
            scrollReference = height() - totalHeight
        }
    }

    /**
     * Computes the total height of all the lines displayed by this [ConsoleScrollPane].
     * @see totalHeight
     */
    private fun recomputeTotalHeight(){
        totalHeight = lineHeight * lines.size
    }

    override fun loadParameters(g: Graphics) {
        if(lines.isNotEmpty()){
            resetLines(g)
            setLinesParameters(g)
            verifyLines(g)
        }
        recomputeTotalHeight()
        verifyScrollReference()
        recalculateDrawingParameters()
    }

    private fun setLinesParameters(g : Graphics){
        ascent = g.getFontMetrics(textFont).maxAscent
        descent = g.getFontMetrics(textFont).maxDescent
        lineHeight = ascent + descent
    }

    private fun resetLines(g : Graphics){
        if(resetLines){
            lines.clear()
            for(line : MutableList<StringDisplay> in stableText){
                lines.addAll(line.toLines(width(), g))
            }
            resetLines = false
        }
    }

    /**
     * Verifies that the lines after the [indexToVerify] index are indeed lines, and does what is needed
     * to create correctly formed lines if it's not the case.
     * @see indexToVerify
     */
    private fun verifyLines(g : Graphics){
        if(indexToVerify.value != null){
            val linesToVerify : MutableList<MutableList<StringDisplay>> = mutableListOf()

            for(i : Int in indexToVerify.value!! until lines.size){
                linesToVerify.add(lines[i])
            }

            for(i : Int in lines.size - 1 downTo indexToVerify.value!!){
                lines.removeAt(i)
            }
            for(lineToVerify : MutableList<StringDisplay> in linesToVerify){
                lines.addAll(lineToVerify.toLines(width(), g))
            }
            indexToVerify.value = null
        }
    }

    /**
     * Recalculates some parameters used to draw the lines on this [ConsoleScrollPane].
     * @see recalculateDrawingParameters
     * @see lowerDrawingIndex
     * @see higherDrawingIndex
     * @see totalHeight
     * @see drawingStartPosition
     */
    private fun recalculateDrawingParameters(){
        if(lineHeight != 0){
            lowerDrawingIndex = - scrollReference / lineHeight
            higherDrawingIndex = (height() - scrollReference) / lineHeight
            if(higherDrawingIndex >= lines.size) higherDrawingIndex = lines.size - 1
            drawingStartPosition = scrollReference + lowerDrawingIndex * lineHeight
        }
    }

    override fun drawDisplayer(g: Graphics) {
        if(lines.isNotEmpty()){

            g.font = textFont

            val fm : FontMetrics = g.getFontMetrics(textFont)

            var drawingPosition : Int = drawingStartPosition

            var startingX : Int = 0

            for(i : Int in lowerDrawingIndex..higherDrawingIndex){
                val line : MutableList<StringDisplay> = lines[i]
                drawingPosition += ascent
                for(sd : StringDisplay in line){
                    g.color = sd.color
                    g.drawString(sd.text, startingX, drawingPosition)
                    startingX += fm.stringWidth(sd.text)
                }
                startingX = 0
                drawingPosition += descent
            }
        }

    }

}