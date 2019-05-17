package llayout.displayers

import llayout.MouseWheelAction
import llayout.utilities.*
import java.awt.Graphics
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener

/**
 * A scroll pane scrolling through lines of text, with methods to write at the end of its lines.
 * @see ResizableDisplayer
 */
class TextScrollPane : ResizableDisplayer{

    private companion object{

        /**
         * The number of pixels scrolled per mouse scroll unit.
         * @see onMouseWheelMoved
         */
        private const val PIXELS_PER_UNIT_SCROLLED : Int = 10

    }

    /**
     * The total height of the lines contained by this [TextScrollPane].
     * @see lines
     */
    private var totalHeight : Int = 0

    private var stableText : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * The lines of text displayed by this [TextScrollPane].
     */
    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    /**
     * When something is written at the end of the liens, the [TextScrollPane] doesn't need to
     * update every line, only those that follow a certain index.
     * indexToVerify is the index of the first line that must be verified.
     * When modified, it calls the [initialize] method of this [TextScrollPane].
     * @see LProperty
     * @see initialize
     */
    private var indexToVerify : LProperty<Int?> = LProperty<Int?>(null).addListener{initialize()}

    private var resetLines : Boolean = false

    /**
     * The 'level of scrolling' is encoded by the coordinate, in pixels, of a point. It is zero if the
     * first line is at the top of the [TextScrollPane] and decreases linearly as the user scrolls down,
     * pushing the lines upwards.
     * @see lines
     */
    private var scrollReference : Int = 0

    /**
     * This [LProperty] encodes the fact that some parameters must be recalculated.
     * @see lowerDrawingIndex
     * @see higherDrawingIndex
     * @see drawingStartPosition
     * @see recalculateDrawingParameters
     */
    private var recalculateDrawingParameters : LProperty<Boolean> = LProperty(true).addListener{initialize()}

    /**
     * This variable encodes the index of the first line to be actually displayed on this [TextScrollPane].
     * @see lines
     */
    private var lowerDrawingIndex : Int = 0

    /**
     * This variable encodes the index of the last line to be actually displayed on this [TextScrollPane].
     * @see lines
     */
    private var higherDrawingIndex : Int = 0

    /**
     * This variable encodes the coordinate of the top of the first line drawn on this [TextScrollPane].
     * @see lowerDrawingIndex
     */
    private var drawingStartPosition : Int = 0

    /*
     * If the width is modified, all the lines must be rearranged.
     */
    init{
        w.addListener{
            resetLines = true
            initialize()
        }
        h.addListener{
            recalculateDrawingParameters.value = true
        }
        setOnMouseWheelMovedAction { e -> run{
            if(totalHeight > height()){
                scrollReference -= e.unitsToScroll * PIXELS_PER_UNIT_SCROLLED
                verifyScrollReference()
            }
            recalculateDrawingParameters.value = true
        } }
    }

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y, width, height)

    /**
     * @see ResizableDisplayer
     */
    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y, width, height)

    /**
     * Adds a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln() : TextScrollPane{
        lines.add(mutableListOf())
        stableText.add(mutableListOf())
        setlineToUpdate(lines.size - 1)
        return this
    }

    /**
     * Adds a StringDisplay in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     * @see StringDisplay
     */
    fun writeln(s : StringDisplay) : TextScrollPane{
        writeln()
        return write(s)
    }

    /**
     * Adds a String in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(s : String) : TextScrollPane = this.writeln(StringDisplay(s))

    /**
     * Adds a Char in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(c : Char) : TextScrollPane = this.writeln(StringDisplay(c))

    /**
     * Adds a StringBuilder in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     * @see StringBuilder
     */
    fun writeln(s : StringBuilder) : TextScrollPane = this.writeln(StringDisplay(s))

    /**
     * Adds an Int in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(i : Int) : TextScrollPane = this.writeln(StringDisplay(i))

    /**
     * Adds a Double in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(d : Double) : TextScrollPane = this.writeln(StringDisplay(d))

    /**
     * Adds a Float in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(f : Float) : TextScrollPane = this.writeln(StringDisplay(f))

    /**
     * Adds a Long in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(l : Long) : TextScrollPane = this.writeln(StringDisplay(l))

    /**
     * Adds a Short in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(s : Short) : TextScrollPane = this.writeln(StringDisplay(s))

    /**
     * Adds a Byte in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(b : Byte) : TextScrollPane = this.writeln(StringDisplay(b))

    /**
     * Adds a Boolean in a new line to the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun writeln(b : Boolean) : TextScrollPane = this.writeln(StringDisplay(b))

    /**
     * Adds a [StringDisplay] in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     * @see StringDisplay
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
     * Adds a String in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(s : String) : TextScrollPane = this.write(StringDisplay(s))

    /**
     * Adds a Char in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(c : Char) : TextScrollPane = this.write(StringDisplay(c))

    /**
     * Adds a StringBuilder in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     * @see StringBuilder
     */
    fun write(s : StringBuilder) : TextScrollPane = this.write(StringDisplay(s))

    /**
     * Adds an Int in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(i : Int) : TextScrollPane = this.write(StringDisplay(i))

    /**
     * Adds a Double in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(d : Double) : TextScrollPane = this.write(StringDisplay(d))

    /**
     * Adds a Float in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(f : Float) : TextScrollPane = this.write(StringDisplay(f))

    /**
     * Adds a Long in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(l : Long) : TextScrollPane = this.write(StringDisplay(l))

    /**
     * Adds a Short in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(s : Short) : TextScrollPane = this.write(StringDisplay(s))

    /**
     * Adds a Byte in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(b : Byte) : TextScrollPane = this.write(StringDisplay(b))

    /**
     * Adds a Boolean in the last line of the list.
     * @return This TextScrollPane
     * @see lines
     */
    fun write(b : Boolean) : TextScrollPane = this.write(StringDisplay(b))

    fun clear(){
        lines.clear()
        stableText.clear()
    }

    /**
     * Scrolls to the bottom of the text.
     * @see lines
     * @see onMouseWheelMoved
     */
    fun scrollToBottom() : TextScrollPane{
        scrollReference = if(totalHeight <= height) 0 else height() - totalHeight
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
     * Verifies that this [TextScrollPane] doesn't scroll too far.
     * @see scrollReference
     */
    private fun verifyScrollReference(){
        if(scrollReference > 0 || totalHeight < height()){
            scrollReference = 0
        }else if(scrollReference < height() - totalHeight){
            scrollReference = height - totalHeight
        }
    }

    /**
     * Computes the total height of all the lines displayed by this [TextScrollPane].
     * @see totalHeight
     */
    private fun recomputeTotalHeight(g : Graphics){
        totalHeight = 0
        for(line : MutableList<StringDisplay> in lines){
            totalHeight += line.lineHeight(g)
        }
    }

    override fun loadParameters(g: Graphics) {
        if(lines.isNotEmpty()){
            resetLines(g)
            verifyLines(g)
        }
        recomputeTotalHeight(g)
        verifyScrollReference()
        recalculateDrawingParameters(g)
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

        }
    }

    /**
     * Recalculates some parameters used to draw the lines on this [TextScrollPane].
     * @see recalculateDrawingParameters
     * @see lowerDrawingIndex
     * @see higherDrawingIndex
     * @see totalHeight
     * @see drawingStartPosition
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

    override fun drawDisplayer(g: Graphics) {
        if(lines.isNotEmpty()){

            var drawingPosition : Int = drawingStartPosition

            var startingX : Int = 0

            for(i : Int in lowerDrawingIndex..higherDrawingIndex){
                val line : MutableList<StringDisplay> = lines[i]
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

    }

}