package llayout.displayers

import llayout.utilities.*
import java.awt.Graphics

class TextScrollPane : ResizableDisplayer {

    private companion object{

        private const val PIXELS_PER_UNIT_SCROLLED : Int = 10

    }

    private var totalHeight : Int = 0

    private var stableText : MutableList<MutableList<StringDisplay>> = mutableListOf()

    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    private var indexToVerify : LObservable<Int?> = LObservable<Int?>(null).addListener{initialize()}

    private var resetLines : Boolean = false

    private var scrollReference : Int = 0

    private var recalculateDrawingParameters : LObservable<Boolean> = LObservable(true).addListener{initialize()}

    private var lowerDrawingIndex : Int = 0

    private var higherDrawingIndex : Int = 0

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

    fun writeln() : TextScrollPane{
        lines.add(mutableListOf())
        stableText.add(mutableListOf())
        setlineToUpdate(lines.size - 1)
        return this
    }

    fun writeln(s : StringDisplay) : TextScrollPane{
        writeln()
        return write(s)
    }

    fun writeln(c : CharSequence) : TextScrollPane = this.writeln(StringDisplay(c))

    fun writeln(c : Char) : TextScrollPane = this.writeln(StringDisplay(c))

    fun writeln(i : Int) : TextScrollPane = this.writeln(StringDisplay(i))

    fun writeln(d : Double) : TextScrollPane = this.writeln(StringDisplay(d))

    fun writeln(f : Float) : TextScrollPane = this.writeln(StringDisplay(f))

    fun writeln(l : Long) : TextScrollPane = this.writeln(StringDisplay(l))

    fun writeln(s : Short) : TextScrollPane = this.writeln(StringDisplay(s))

    fun writeln(b : Byte) : TextScrollPane = this.writeln(StringDisplay(b))

    fun writeln(b : Boolean) : TextScrollPane = this.writeln(StringDisplay(b))

    fun write(s : StringDisplay) : TextScrollPane{
        if(lines.size == 0) lines.add(mutableListOf())
        if(stableText.size == 0) stableText.add(mutableListOf())
        lines[lines.size - 1].add(s)
        stableText[stableText.size - 1].add(s)
        setlineToUpdate(lines.size - 1)
        scrollToBottom()
        return this
    }

    fun write(c : CharSequence) : TextScrollPane = this.write(StringDisplay(c))

    fun write(c : Char) : TextScrollPane = this.write(StringDisplay(c))

    fun write(i : Int) : TextScrollPane = this.write(StringDisplay(i))

    fun write(d : Double) : TextScrollPane = this.write(StringDisplay(d))

    fun write(f : Float) : TextScrollPane = this.write(StringDisplay(f))

    fun write(l : Long) : TextScrollPane = this.write(StringDisplay(l))

    fun write(s : Short) : TextScrollPane = this.write(StringDisplay(s))

    fun write(b : Byte) : TextScrollPane = this.write(StringDisplay(b))

    fun write(b : Boolean) : TextScrollPane = this.write(StringDisplay(b))

    fun clear(){
        lines.clear()
        stableText.clear()
    }

    fun scrollToBottom() : TextScrollPane{
        scrollReference = if(totalHeight <= height()) 0 else height() - totalHeight
        return this
    }

    private fun lines() : MutableList<MutableList<StringDisplay>> = lines

    private fun drawingStartPosition() : Int = drawingStartPosition

    private fun lowerDrawingIndex() : Int = lowerDrawingIndex

    private fun higherDrawingIndex() : Int = higherDrawingIndex

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

        }
    }

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