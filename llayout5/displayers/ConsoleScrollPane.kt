package llayout5.displayers

import llayout5.DEFAULT_SMALL_FONT
import llayout5.utilities.LObservable
import llayout5.utilities.StringDisplay
import llayout5.utilities.toLines
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * A ResizableDisplayer that acts like a console output, in the sense that it displays text.
 * It is equivalent to a [TextScrollPane], but it is optimized for a single font.
 * @see ResizableDisplayer
 * @see TextScrollPane
 * @since LLayout 1
 */
class ConsoleScrollPane : ResizableDisplayer {

    private companion object{

        /**
         * The number of pixels scrolled by units scrolled by the user's mouse.
         * @since LLayout 1
         */
        private const val PIXELS_PER_UNIT_SCROLLED : Int = 10

        /**
         * The default font used by a ConsoleScrollPane.
         * @since LLayout 1
         */
        private val DEFAULT_FONT : Font = DEFAULT_SMALL_FONT

    }

    /**
     * The font used to draw the text.
     * @since LLayout 1
     */
    private var textFont : Font

    /**
     * The total height of all the written text.
     * @since LLayout 1
     */
    private var totalHeight : Int = 0

    /**
     * The non modified text written by the user.
     * @since LLayout 1
     */
    private var stableText : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * The text written by the user cut in lines that fit inside the width of this ConsoleScrollPane.
     * @since LLayout 1
     */
    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * The index of the first line that must be verified, that is, every line following this one (included) must be
     * verified.
     * @see LObservable
     * @since LLayout 1
     */
    private var indexToVerify : LObservable<Int?> = LObservable<Int?>(null).addListener{initialize()}

    /**
     * True if all the lines must be reset.
     * @since LLayout 1
     */
    private var resetLines : Boolean = false

    /**
     * The point representing the position of the top of the text.
     * @since LLayout 1
     */
    private var scrollReference : Int = 0

    /**
     * The index of the first line appearing on the screen.
     * @since LLayout 1
     */
    private var lowerDrawingIndex : Int = 0

    /**
     * The index of the last line appearing on the screen.
     * @since LLayout 1
     */
    private var higherDrawingIndex : Int = 0

    /**
     * The position of the top of the first line on the screen.
     * @since LLayout 1
     */
    private var drawingStartPosition : Int = 0

    /**
     * The ascent of the font.
     * @since LLayout 1
     */
    private var ascent : Int = 0

    /**
     * The descent of the font.
     * @since LLayout 1
     */
    private var descent : Int = 0

    /**
     * The height of a line of the current font.
     * @since LLayout 1
     */
    private var lineHeight : Int = 0

    /**
     * The prompt that is generated at the start of each line.
     * @since LLayout 1
     */
    private var prompt : Collection<StringDisplay> = setOf()

    init{
        addWidthListener{
            resetLines = true
            initialize()
        }
        addHeightListener{
            recalculateDrawingParameters()
            verifyScrollReference()
        }
        setOnMouseWheelMovedAction { e ->
            if(totalHeight > height()){
                scrollReference -= e.unitsToScroll * PIXELS_PER_UNIT_SCROLLED
                verifyScrollReference()
            }
            recalculateDrawingParameters()
        }
        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            if(lines().isNotEmpty()){

                g.font = textFont()

                val fm : FontMetrics = g.getFontMetrics(textFont())

                var drawingPosition : Int = drawingStartPosition()

                var startingX : Int = 0

                for(i : Int in lowerDrawingIndex()..higherDrawingIndex()){
                    val line : MutableList<StringDisplay> = lines()[i]
                    drawingPosition += ascent()
                    for(sd : StringDisplay in line){
                        g.color = sd.color
                        g.drawString(sd.text, startingX, drawingPosition)
                        startingX += fm.stringWidth(sd.text)
                    }
                    startingX = 0
                    drawingPosition += descent()
                }
            }
        })
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
     * Writes a new empty line.
     * @return this
     * @since LLayout 1
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
     * Writes a StringDisplay on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(s : StringDisplay) : ConsoleScrollPane{
        writeln()
        return write(s)
    }

    /**
     * Writes a CharSequence on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(s : CharSequence) : ConsoleScrollPane = this.writeln(StringDisplay(s))

    /**
     * Writes a Char on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(c : Char) : ConsoleScrollPane = this.writeln(StringDisplay(c))

    /**
     * Writes an Int on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(i : Int) : ConsoleScrollPane = this.writeln(StringDisplay(i))

    /**
     * Writes a Double on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(d : Double) : ConsoleScrollPane = this.writeln(StringDisplay(d))

    /**
     * Writes a Float on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(f : Float) : ConsoleScrollPane = this.writeln(StringDisplay(f))

    /**
     * Writes a Long on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(l : Long) : ConsoleScrollPane = this.writeln(StringDisplay(l))

    /**
     * Writes a Short on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(s : Short) : ConsoleScrollPane = this.writeln(StringDisplay(s))

    /**
     * Writes a Byte on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(b : Byte) : ConsoleScrollPane = this.writeln(StringDisplay(b))

    /**
     * Writes a Boolean on a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(b : Boolean) : ConsoleScrollPane = this.writeln(StringDisplay(b))

    /**
     * Writes a StringDisplay on the current line.
     * @return this
     * @since LLayout 1
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
     * Writes a CharSequence on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(s : CharSequence) : ConsoleScrollPane = this.write(StringDisplay(s))

    /**
     * Writes a Char on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(c : Char) : ConsoleScrollPane = this.write(StringDisplay(c))

    /**
     * Writes an Int on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(i : Int) : ConsoleScrollPane = this.write(StringDisplay(i))

    /**
     * Writes a Double on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(d : Double) : ConsoleScrollPane = this.write(StringDisplay(d))

    /**
     * Writes a Float on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(f : Float) : ConsoleScrollPane = this.write(StringDisplay(f))

    /**
     * Writes a Long on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(l : Long) : ConsoleScrollPane = this.write(StringDisplay(l))

    /**
     * Writes a Short on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(s : Short) : ConsoleScrollPane = this.write(StringDisplay(s))

    /**
     * Writes a Byte on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(b : Byte) : ConsoleScrollPane = this.write(StringDisplay(b))

    /**
     * Writes a Boolean on the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(b : Boolean) : ConsoleScrollPane = this.write(StringDisplay(b))

    /**
     * Sets a new prompt.
     * @throws IllegalArgumentException If the given prompt contains the \\\\\\\\\\\\n character.
     * @return this
     * @since LLayout 1
     */
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

    /**
     * Sets a new prompt.
     * @throws IllegalArgumentException If the given prompt contains the \\\\\\\\\\\\n character.
     * @return this
     * @since LLayout 1
     */
    fun setPrompt(vararg prompt : StringDisplay) : ConsoleScrollPane{
        for(sd : StringDisplay in prompt){
            if(sd.contains("\n")) throw IllegalArgumentException("Prompt contains a \"\\n\" character.")
            sd.font = textFont
        }
        this.prompt = prompt.asList()
        return this
    }

    /**
     * Sets a new prompt.
     * @throws IllegalArgumentException If the given prompt contains the \\\\\\\\\\\\n character.
     * @return this
     * @since LLayout 1
     */
    fun setPrompt(prompt : Collection<StringDisplay>) : ConsoleScrollPane{
        for(sd : StringDisplay in prompt){
            if(sd.contains("\n")) throw IllegalArgumentException("Prompt contains a \"\\n\" character.")
            sd.font = textFont
        }
        this.prompt = prompt
        return this
    }

    /**
     * Clears all the lines.
     * @since LLayout 1
     */
    fun clearConsole() : ConsoleScrollPane{
        lines.clear()
        stableText.clear()
        return this
    }

    /**
     * Scrolls to the bottom of the text.
     * @since LLayout 1
     */
    fun scrollToBottom() : ConsoleScrollPane{
        scrollReference = if(totalHeight <= height()) 0 else height() - totalHeight
        recalculateDrawingParameters()
        return this
    }

    /**
     * The lines.
     * @since LLayout 1
     */
    private fun lines() : MutableList<MutableList<StringDisplay>> = lines

    /**
     * The text font.
     * @since LLayout 1
     */
    private fun textFont() : Font = textFont

    private fun drawingStartPosition() : Int = drawingStartPosition

    private fun lowerDrawingIndex() : Int = lowerDrawingIndex

    private fun higherDrawingIndex() : Int = higherDrawingIndex

    private fun ascent() : Int = ascent

    private fun descent() : Int = descent

    /**
     * Sets the first line to update.
     * @since LLayout 1
     */
    private fun setlineToUpdate(index : Int){
        if(indexToVerify.value == null || indexToVerify.value!! > index) indexToVerify.value = index
    }

    /**
     * Verifies that the scroll reference is in such a position that the text is always shown completely.
     * @since LLayout 1
     */
    private fun verifyScrollReference(){
        if(scrollReference > 0 || totalHeight < height()){
            scrollReference = 0
        }else if(scrollReference < height() - totalHeight){
            scrollReference = height() - totalHeight
        }
    }

    /**
     * Recomputes the total height of the text.
     * @since LLayout 1
     */
    private fun recomputeTotalHeight(){
        totalHeight = lineHeight * lines.size
    }

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        if(lines.isNotEmpty()){
            resetLines(g)
            setLinesParameters(g)
            verifyLines(g)
        }
        recomputeTotalHeight()
        verifyScrollReference()
        recalculateDrawingParameters()
    }

    /**
     * Sets the ascent, descent and line height.
     * @since LLayout 1
     */
    private fun setLinesParameters(g : Graphics){
        ascent = g.getFontMetrics(textFont).maxAscent
        descent = g.getFontMetrics(textFont).maxDescent
        lineHeight = ascent + descent
    }

    /**
     * Resets all the lines if needed.
     * @since LLayout 1
     */
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
     * Loads the lines that must be verified correctly.
     * @since LLayout 1
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
     * Recomputes the lowerDrawingIndex, higherDrawingIndex, drawingStartPosition.
     * @since LLayout 1
     */
    private fun recalculateDrawingParameters(){
        if(lineHeight != 0){
            lowerDrawingIndex = - scrollReference / lineHeight
            higherDrawingIndex = (height() - scrollReference) / lineHeight
            if(higherDrawingIndex >= lines.size) higherDrawingIndex = lines.size - 1
            drawingStartPosition = scrollReference + lowerDrawingIndex * lineHeight
        }
    }

}