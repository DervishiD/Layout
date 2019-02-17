package display

import geometry.Point
import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics
import kotlin.math.max

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
    private val isResizable : Boolean
    private val baseWidth : Int
    private val regex : Regex

    constructor(p : Point, width : Int, defaultText : String = "", isResizable : Boolean = false, regex : String = ".") : super(p){
        this.baseWidth = width
        this.typedText = defaultText
        this.isResizable = isResizable
        this.regex = Regex(regex)
        this.w = baseWidth
    }
    constructor(x : Double, y : Double, width : Int = DEFAULT_WIDTH, defaultText : String = "", isResizable : Boolean = false, regex : String = ".") : this(Point(x, y), width, defaultText, isResizable, regex)
    constructor(x : Int, y : Double, width : Int = DEFAULT_WIDTH, defaultText : String = "", isResizable : Boolean = false, regex : String = ".") : this(Point(x, y), width, defaultText, isResizable, regex)
    constructor(x : Double, y : Int, width : Int = DEFAULT_WIDTH, defaultText : String = "", isResizable : Boolean = false, regex : String = ".") : this(Point(x, y), width, defaultText, isResizable, regex)
    constructor(x : Int, y : Int, width : Int = DEFAULT_WIDTH, defaultText : String = "", isResizable : Boolean = false, regex : String = ".") : this(Point(x, y), width, defaultText, isResizable, regex)

    fun type(key : String){
        if(key.matches(regex)) typedText += key
        initphase = true
    }

    fun backspace(){
        if(typedText != ""){
            typedText = typedText.substring(0, typedText.length - 1)
            initphase = true
        }
    }

    fun focus(){
        isFocused = true
    }

    fun unfocus(){
        isFocused = false
    }

    fun typedText() : String = typedText

    fun reset(){
        typedText = ""
    }

    override fun loadParameters(g: Graphics) {
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)
        h = fm.maxAscent + fm.maxDescent + 2 * (LINE_THICKNESS + DELTA)
        if(isResizable) w = max(fm.stringWidth(typedText) + 2 * (LINE_THICKNESS + DELTA), baseWidth)
    }

    override fun drawDisplayer(g: Graphics) {
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)

        g.color = if(isFocused) FOCUSED_COLOR else UNFOCUSED_COLOR
        g.font = DEFAULT_FONT

        g.drawString(typedText, LINE_THICKNESS + DELTA, fm.maxAscent)

        g.fillRect(0, 0, LINE_THICKNESS, h)
        g.fillRect(0, 0, w, LINE_THICKNESS)
        g.fillRect(0, h - LINE_THICKNESS, w, LINE_THICKNESS)
        g.fillRect(w - LINE_THICKNESS, 0, w, h)
    }

}