package llayout.displayers

import llayout.DEFAULT_SMALL_FONT
import llayout.utilities.LObservable
import llayout.utilities.StringDisplay
import llayout.utilities.toLines
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics

class ConsoleScrollPane : ResizableDisplayer {

    private companion object{

        private const val PIXELS_PER_UNIT_SCROLLED : Int = 10

        private val DEFAULT_FONT : Font = DEFAULT_SMALL_FONT

    }

    private var textFont : Font

    private var totalHeight : Int = 0

    private var stableText : MutableList<MutableList<StringDisplay>> = mutableListOf()

    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    private var indexToVerify : LObservable<Int?> = LObservable<Int?>(null).addListener{initialize()}

    private var resetLines : Boolean = false

    private var scrollReference : Int = 0

    private var lowerDrawingIndex : Int = 0

    private var higherDrawingIndex : Int = 0

    private var drawingStartPosition : Int = 0

    private var ascent : Int = 0

    private var descent : Int = 0

    private var lineHeight : Int = 0

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

    fun writeln() : ConsoleScrollPane{
        lines.add(mutableListOf())
        lines.last().addAll(prompt)
        stableText.add(mutableListOf())
        stableText.last().addAll(prompt)
        setlineToUpdate(lines.size - 1)
        return this
    }

    fun writeln(s : StringDisplay) : ConsoleScrollPane{
        writeln()
        return write(s)
    }

    fun writeln(s : CharSequence) : ConsoleScrollPane = this.writeln(StringDisplay(s))

    fun writeln(c : Char) : ConsoleScrollPane = this.writeln(StringDisplay(c))

    fun writeln(i : Int) : ConsoleScrollPane = this.writeln(StringDisplay(i))

    fun writeln(d : Double) : ConsoleScrollPane = this.writeln(StringDisplay(d))

    fun writeln(f : Float) : ConsoleScrollPane = this.writeln(StringDisplay(f))

    fun writeln(l : Long) : ConsoleScrollPane = this.writeln(StringDisplay(l))

    fun writeln(s : Short) : ConsoleScrollPane = this.writeln(StringDisplay(s))

    fun writeln(b : Byte) : ConsoleScrollPane = this.writeln(StringDisplay(b))

    fun writeln(b : Boolean) : ConsoleScrollPane = this.writeln(StringDisplay(b))

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

    fun write(s : CharSequence) : ConsoleScrollPane = this.write(StringDisplay(s))

    fun write(c : Char) : ConsoleScrollPane = this.write(StringDisplay(c))

    fun write(i : Int) : ConsoleScrollPane = this.write(StringDisplay(i))

    fun write(d : Double) : ConsoleScrollPane = this.write(StringDisplay(d))

    fun write(f : Float) : ConsoleScrollPane = this.write(StringDisplay(f))

    fun write(l : Long) : ConsoleScrollPane = this.write(StringDisplay(l))

    fun write(s : Short) : ConsoleScrollPane = this.write(StringDisplay(s))

    fun write(b : Byte) : ConsoleScrollPane = this.write(StringDisplay(b))

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

    fun scrollToBottom() : ConsoleScrollPane{
        scrollReference = if(totalHeight <= height()) 0 else height() - totalHeight
        recalculateDrawingParameters()
        return this
    }

    private fun lines() : MutableList<MutableList<StringDisplay>> = lines

    private fun textFont() : Font = textFont

    private fun drawingStartPosition() : Int = drawingStartPosition

    private fun lowerDrawingIndex() : Int = lowerDrawingIndex

    private fun higherDrawingIndex() : Int = higherDrawingIndex

    private fun ascent() : Int = ascent

    private fun descent() : Int = descent

    private fun setlineToUpdate(index : Int){
        if(indexToVerify.value == null || indexToVerify.value!! > index) indexToVerify.value = index
    }

    private fun verifyScrollReference(){
        if(scrollReference > 0 || totalHeight < height()){
            scrollReference = 0
        }else if(scrollReference < height() - totalHeight){
            scrollReference = height() - totalHeight
        }
    }

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

    private fun recalculateDrawingParameters(){
        if(lineHeight != 0){
            lowerDrawingIndex = - scrollReference / lineHeight
            higherDrawingIndex = (height() - scrollReference) / lineHeight
            if(higherDrawingIndex >= lines.size) higherDrawingIndex = lines.size - 1
            drawingStartPosition = scrollReference + lowerDrawingIndex * lineHeight
        }
    }

}