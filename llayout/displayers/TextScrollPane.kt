package llayout.displayers

import llayout.MouseWheelAction
import llayout.geometry.Point
import llayout.utilities.*
import java.awt.Graphics

class TextScrollPane : ResizableDisplayer{

    private companion object{
        private const val PIXELS_PER_UNIT_SCROLLED : Int = 10
    }

    private var totalHeight : Int = 0

    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    private var indexToVerify : LProperty<Int?> = LProperty<Int?>(null).addListener{initialize()}

    private var scrollReference : Int = 0

    private var recalculateDrawingParameters : LProperty<Boolean> = LProperty(true).addListener{initialize()}

    private var lowerDrawingIndex : Int = 0

    private var higherDrawingIndex : Int = 0

    private var drawingStartPosition : Int = 0

    init{
        w.addListener{setlineToUpdate(0)}
    }

    override var onMouseWheelMoved: MouseWheelAction = {units : Int -> run{
        if(totalHeight > height()){
            scrollReference -= units * PIXELS_PER_UNIT_SCROLLED
            verifyScrollReference()
        }
        recalculateDrawingParameters.value = true
    }}

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(p : Point, width : Int, height : Int) : super(p, width, height)

    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(p : Point, width : Double, height : Int) : super(p, width, height)

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(p : Point, width : Double, height : Double) : super(p, width, height)

    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y, width, height)

    constructor(p : Point, width : Int, height : Double) : super(p, width, height)

    fun writeln() : TextScrollPane{
        lines.add(mutableListOf())
        setlineToUpdate(lines.size - 1)
        return this
    }

    fun writeln(s : StringDisplay) : TextScrollPane{
        writeln()
        return write(s)
    }

    fun writeln(s : String) : TextScrollPane = this.writeln(StringDisplay(s))

    fun writeln(c : Char) : TextScrollPane = this.writeln(StringDisplay(c))

    fun writeln(s : StringBuilder) : TextScrollPane = this.writeln(StringDisplay(s))

    fun writeln(i : Int) : TextScrollPane = this.writeln(StringDisplay(i))

    fun writeln(d : Double) : TextScrollPane = this.writeln(StringDisplay(d))

    fun writeln(f : Float) : TextScrollPane = this.writeln(StringDisplay(f))

    fun writeln(l : Long) : TextScrollPane = this.writeln(StringDisplay(l))

    fun writeln(s : Short) : TextScrollPane = this.writeln(StringDisplay(s))

    fun writeln(b : Byte) : TextScrollPane = this.writeln(StringDisplay(b))

    fun writeln(b : Boolean) : TextScrollPane = this.writeln(StringDisplay(b))

    fun write(s : StringDisplay) : TextScrollPane{
        if(lines.size == 0) lines.add(mutableListOf())
        lines[lines.size - 1].add(s)
        setlineToUpdate(lines.size - 1)
        scrollToBottom()
        return this
    }

    fun write(s : String) : TextScrollPane = this.write(StringDisplay(s))

    fun write(c : Char) : TextScrollPane = this.write(StringDisplay(c))

    fun write(s : StringBuilder) : TextScrollPane = this.write(StringDisplay(s))

    fun write(i : Int) : TextScrollPane = this.write(StringDisplay(i))

    fun write(d : Double) : TextScrollPane = this.write(StringDisplay(d))

    fun write(f : Float) : TextScrollPane = this.write(StringDisplay(f))

    fun write(l : Long) : TextScrollPane = this.write(StringDisplay(l))

    fun write(s : Short) : TextScrollPane = this.write(StringDisplay(s))

    fun write(b : Byte) : TextScrollPane = this.write(StringDisplay(b))

    fun write(b : Boolean) : TextScrollPane = this.write(StringDisplay(b))

    fun scrollToBottom() : TextScrollPane{
        scrollReference = if(totalHeight <= height) 0 else height() - totalHeight
        return this
    }

    private fun setlineToUpdate(index : Int){
        if(indexToVerify.value == null || indexToVerify.value!! > index) indexToVerify.value = index
    }

    private fun verifyScrollReference(){
        if(scrollReference > 0){
            scrollReference = 0
        }else if(scrollReference < height() - totalHeight){
            scrollReference = height - totalHeight
        }
    }

    private fun recomputeTotalHeight(g : Graphics){
        totalHeight = 0
        for(line : MutableList<StringDisplay> in lines){
            totalHeight += line.lineHeight(g)
        }
    }

    override fun loadParameters(g: Graphics) {
        if(lines.isNotEmpty()){
            verifyLines(g)
            recalculateDrawingParameters(g)
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

            recomputeTotalHeight(g)

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
            while(zero < height()){
                zero += lines[index].lineHeight(g)
                index++
            }
            higherDrawingIndex = if(index < lines.size) index else lines.size - 1
            recalculateDrawingParameters.value = false

            recomputeTotalHeight(g)

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