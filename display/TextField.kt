package display

import geometry.Point
import main.Action
import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics

class TextField : Displayer{

    private companion object {
        private const val DEFAULT_WIDTH = 25
        private const val LINE_THICKNESS : Int = 2
        private const val DELTA : Int = 2
        private val UNFOCUSED_COLOR : Color = DEFAULT_COLOR
        private val FOCUSED_COLOR : Color = Color(70, 140, 70)
    }

    private var typedText : String
    private var isFocused : Boolean = false
    private val baseWidth : Int
    private val regex : Regex

    override var onRelease: Action = {focus()}

    constructor(p : Point, width : Int, defaultText : String = "", regex : String = ".") : super(p){
        this.baseWidth = width
        this.typedText = defaultText
        this.regex = Regex(regex)
        this.w = baseWidth
    }
    constructor(x : Double, y : Double, width : Int = DEFAULT_WIDTH, defaultText : String = "", regex : String = ".") : this(Point(x, y), width, defaultText, regex)
    constructor(x : Int, y : Double, width : Int = DEFAULT_WIDTH, defaultText : String = "", regex : String = ".") : this(Point(x, y), width, defaultText, regex)
    constructor(x : Double, y : Int, width : Int = DEFAULT_WIDTH, defaultText : String = "", regex : String = ".") : this(Point(x, y), width, defaultText, regex)
    constructor(x : Int, y : Int, width : Int = DEFAULT_WIDTH, defaultText : String = "", regex : String = ".") : this(Point(x, y), width, defaultText, regex)

    infix fun type(keyCode : Int){
        val key : Char = keyCode.toChar()
        when{
            regex.matches(key.toString()) -> typedText += key
            key == '\b' -> backspace()
            key == '\t' -> tab()
        }
    }

    private fun backspace(){
        typedText = typedText.substring(0, typedText.length - 1)
    }

    private fun tab(){
        typedText += "    " //four spaces
    }

    fun focus(){
        isFocused = true
    }

    fun unfocus(){
        isFocused = false
    }

    fun typedText() : String = typedText

    fun clear(){
        typedText = ""
    }

    override fun loadParameters(g: Graphics) {
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)
        h = fm.maxAscent + fm.maxDescent + 2 * (LINE_THICKNESS + DELTA)
    }

    override fun drawDisplayer(g: Graphics) {
        drawText(g)
        drawBackground(g)
    }

    private fun drawText(g : Graphics){
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)
        g.color = if(isFocused) FOCUSED_COLOR else UNFOCUSED_COLOR
        g.font = DEFAULT_FONT
        g.drawString(typedText, LINE_THICKNESS + DELTA, fm.maxAscent)
    }

    private fun drawBackground(g : Graphics){
        g.fillRect(0, 0, LINE_THICKNESS, h)
        g.fillRect(0, 0, w, LINE_THICKNESS)
        g.fillRect(0, h - LINE_THICKNESS, w, LINE_THICKNESS)
        g.fillRect(w - LINE_THICKNESS, 0, w, h)
    }

}