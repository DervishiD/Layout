package llayout.displayers

import llayout.utilities.*
import java.awt.Graphics

/**
 * A scroll pane that shows text in a console-like output. One can add text at the end of the already present text.
 * @see ResizableDisplayer
 * @since LLayout 1
 */
class TextScrollPane : ResizableDisplayer {

    private companion object{

        /**
         * The number of pixels scrolled per mouse wheel unit.
         * @since LLayout 1
         */
        private const val PIXELS_PER_UNIT_SCROLLED : Int = 10

    }

    /**
     * The total height of the written text.
     * @since LLayout 1
     */
    private var totalHeight : Int = 0

    /**
     * The unmodified written text.
     * @since LLayout 1
     */
    private var stableText : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * The written text, cut in lines that fit inside the width of this Displayer.
     * @since LLayout 1
     */
    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * The index of the first line to verify.
     * @since LLayout 1
     */
    private var indexToVerify : LObservable<Int?> = LObservable<Int?>(null).addListener{initialize()}

    /**
     * True if the lines must be reset.
     * @since LLayout 1
     */
    private var resetLines : Boolean = false

    /**
     * The point that indicates the position of the top of the written text.
     * @since LLayout 1
     */
    private var scrollReference : Int = 0

    /**
     * True if the drawing parameters must be recalculated.
     * @see LObservable
     * @since LLayout 1
     */
    private var recalculateDrawingParameters : LObservable<Boolean> = LObservable(true).addListener{initialize()}

    /**
     * The index of the first line on the screen.
     * @since LLayout 1
     */
    private var lowerDrawingIndex : Int = 0

    /**
     * The index of the last line on the screen.
     * @since LLayout 1
     */
    private var higherDrawingIndex : Int = 0

    /**
     * The position of the top of the first line on the screnn.
     * @since LLayout 1
     */
    private var drawingStartPosition : Int = 0

    init{
        addWidthListener{
            resetLines = true
            initialize()
        }
        addHeightListener{
            recalculateDrawingParameters.value = true
        }
        setOnMouseWheelMovedAction { e ->
            if(totalHeight > height()){
                scrollReference -= e.unitsToScroll * PIXELS_PER_UNIT_SCROLLED
                verifyScrollReference()
            }
            recalculateDrawingParameters.value = true
        }
        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            if(lines().isNotEmpty()){

                var drawingPosition : Int = drawingStartPosition()

                var startingX : Int = 0

                for(i : Int in lowerDrawingIndex()..higherDrawingIndex()){
                    val line : MutableList<StringDisplay> = lines()[i]
                    drawingPosition += line.ascent(g)
                    for(sd : StringDisplay in line){
                        g.font = sd.font
                        g.color = sd.color
                        g.drawString(sd.text, startingX, drawingPosition)
                        startingX += g.getFontMetrics(sd.font).stringWidth(sd.text)
                    }
                    startingX = 0
                    drawingPosition += line.descent(g)
                }
            }
        })
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    /**
     * Writes a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln() : TextScrollPane{
        lines.add(mutableListOf())
        stableText.add(mutableListOf())
        setlineToUpdate(lines.size - 1)
        return this
    }

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(s : StringDisplay) : TextScrollPane{
        writeln()
        return write(s)
    }

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(c : CharSequence) : TextScrollPane = this.writeln(StringDisplay(c))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(c : Char) : TextScrollPane = this.writeln(StringDisplay(c))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(i : Int) : TextScrollPane = this.writeln(StringDisplay(i))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(d : Double) : TextScrollPane = this.writeln(StringDisplay(d))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(f : Float) : TextScrollPane = this.writeln(StringDisplay(f))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(l : Long) : TextScrollPane = this.writeln(StringDisplay(l))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(s : Short) : TextScrollPane = this.writeln(StringDisplay(s))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(b : Byte) : TextScrollPane = this.writeln(StringDisplay(b))

    /**
     * Writes the argument in a new line.
     * @return this
     * @since LLayout 1
     */
    fun writeln(b : Boolean) : TextScrollPane = this.writeln(StringDisplay(b))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(s : StringDisplay) : TextScrollPane{
        if(lines.size == 0) lines.add(mutableListOf())
        if(stableText.size == 0) stableText.add(mutableListOf())
        lines[lines.size - 1].add(s)
        stableText[stableText.size - 1].add(s)
        setlineToUpdate(lines.size - 1)
        scrollToBottom()
        return this
    }

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(c : CharSequence) : TextScrollPane = this.write(StringDisplay(c))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(c : Char) : TextScrollPane = this.write(StringDisplay(c))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(i : Int) : TextScrollPane = this.write(StringDisplay(i))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(d : Double) : TextScrollPane = this.write(StringDisplay(d))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(f : Float) : TextScrollPane = this.write(StringDisplay(f))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(l : Long) : TextScrollPane = this.write(StringDisplay(l))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(s : Short) : TextScrollPane = this.write(StringDisplay(s))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(b : Byte) : TextScrollPane = this.write(StringDisplay(b))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 1
     */
    fun write(b : Boolean) : TextScrollPane = this.write(StringDisplay(b))

    /**
     * Writes the argument in the current line.
     * @return this
     * @since LLayout 7
     */
    fun write(t : Collection<StringDisplay>) : TextScrollPane{
        for(sd : StringDisplay in t) write(sd)
        return this
    }

    /**
     * Clears all the lines.
     * @since LLayout 1
     */
    fun clear(){
        lines.clear()
        stableText.clear()
    }

    /**
     * Scrolls to the bottom.
     * @return this
     * @since LLayout 1
     */
    fun scrollToBottom() : TextScrollPane{
        scrollReference = if(totalHeight <= height()) 0 else height() - totalHeight
        return this
    }

    /**
     * The lines.
     * @since LLayout 1
     */
    private fun lines() : MutableList<MutableList<StringDisplay>> = lines

    /**
     * The drawingStartPosition property
     * @since LLayout 1
     */
    private fun drawingStartPosition() : Int = drawingStartPosition

    /**
     * The lowerDrawingIndex property.
     * @since LLayout 1
     */
    private fun lowerDrawingIndex() : Int = lowerDrawingIndex

    /**
     * The higherDrawingIndex property.
     * @since LLayout 1
     */
    private fun higherDrawingIndex() : Int = higherDrawingIndex

    /**
     * Sets the index of the first line to update.
     * @since LLayout 1
     */
    private fun setlineToUpdate(index : Int){
        if(indexToVerify.value == null || indexToVerify.value!! > index) indexToVerify.value = index
    }

    /**
     * Verifies that the scroll reference lets the text be displayed inside the bounds of the TextScrollPane.
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
    private fun recomputeTotalHeight(g : Graphics){
        totalHeight = 0
        for(line : MutableList<StringDisplay> in lines){
            totalHeight += line.lineHeight(g)
        }
    }

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        if(lines.isNotEmpty()){
            resetLines(g)
            verifyLines(g)
        }
        recomputeTotalHeight(g)
        verifyScrollReference()
        recalculateDrawingParameters(g)
    }

    /**
     * Resets the lines.
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
     * Verifies the lines from the indexToVerify position.
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

        }
    }

    /**
     * Recalculates the parameters used to draw the text.
     * @since LLayout 1
     */
    private fun recalculateDrawingParameters(g : Graphics){
        if(recalculateDrawingParameters.value){
            var zero : Int = scrollReference
            var lowerFound : Boolean = false
            var index : Int = 0
            var lineHeight : Int
            while(!lowerFound){
                lineHeight = lines[index].lineHeight(g)
                if(zero >= 0 || zero + lineHeight < height()){
                    lowerFound = true
                    lowerDrawingIndex = index
                    drawingStartPosition = zero
                }else{
                    zero += lineHeight
                    index++
                }
            }
            while(zero < height() && index < lines.size){
                zero += lines[index].lineHeight(g)
                index++
            }
            higherDrawingIndex = if(index < lines.size) index else lines.size - 1
            recalculateDrawingParameters.value = false

        }
    }

}