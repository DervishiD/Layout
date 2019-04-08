package display

import geometry.Point
import main.Action
import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * A simple TextField. Quite simple, AND probably breakable.
 * @see Displayer
 */
class TextField : Displayer{

    private companion object {

        /**
         * The default width of a TextDisplayer.
         */
        private const val DEFAULT_WIDTH = 250

        /**
         * The default line thickness in the background.
         * @see drawBackground
         */
        private const val LINE_THICKNESS : Int = 2

        /**
         * The default distance between the line and the start of the text.
         * @see LINE_THICKNESS
         * @see drawText
         */
        private const val DELTA : Int = 2

        /**
         * The color of the TextField when it is not focused.
         * @see focus
         * @see drawDisplayer
         */
        private val UNFOCUSED_COLOR : Color = DEFAULT_COLOR

        /**
         * The color of the TextField when it is focused.
         * @see focus
         * @see drawDisplayer
         */
        private val FOCUSED_COLOR : Color = Color(70, 140, 70)

    }

    /**
     * The text currently typed on this TextField.
     * @see type
     */
    private var typedText : String

    /**
     * True if this TextField is focused.
     * @see focus
     */
    private var isFocused : Boolean = false

    /**
     * A Regex that defines which characters can be typed in this TextField.
     * @see type
     */
    private val regex : Regex

    private var relativeW : Double? = null

    override var onRelease: Action = {focus()}

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Int,
            y : Int,
            width : Int = DEFAULT_WIDTH,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        this.w.value = width
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Int,
            y : Double,
            width : Int = DEFAULT_WIDTH,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        this.w.value = width
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Double,
            y : Int,
            width : Int = DEFAULT_WIDTH,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        this.w.value = width
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Double,
            y : Double,
            width : Int = DEFAULT_WIDTH,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        this.w.value = width
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param p The center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see Point
     * @see regex
     */
    constructor(
            p : Point,
            width : Int = DEFAULT_WIDTH,
            defaultText : String = "",
            regex : String = ".")
            : this(p.intx(), p.inty(), width, defaultText, regex)

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Int,
            y : Int,
            width : Double,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        relativeW = width
        requestCoordinateUpdate()
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Int,
            y : Double,
            width : Double,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        relativeW = width
        requestCoordinateUpdate()
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Double,
            y : Int,
            width : Double,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        relativeW = width
        requestCoordinateUpdate()
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param x The x coordinate of the center point of this TextDisplayer.
     * @param y The y coordinate of the center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see regex
     */
    constructor(
            x : Double,
            y : Double,
            width : Double,
            defaultText : String = "",
            regex : String = ".") : super(x, y){
        this.typedText = defaultText
        this.regex = Regex(regex)
        relativeW = width
        requestCoordinateUpdate()
    }

    /**
     * Constructs a TextField with the given parameters.
     * @param p The center point of this TextDisplayer.
     * @param width The width of this TextField.
     * @param defaultText The text that appears on this TextField.
     * @param regex A Regex that defines which characters can be typed in this TextField.
     * @see Point
     * @see regex
     */
    constructor(
            p : Point,
            width : Double,
            defaultText : String = "",
            regex : String = ".")
            : this(p.intx(), p.inty(), width, defaultText, regex)

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): Displayer {
        if(relativeW != null) w.value = (relativeW!! * frameWidth).toInt()
        return super.updateRelativeValues(frameWidth, frameHeight)
    }

    /**
     * Types the given character (as a keyCode) in this TextField.
     * @param keyCode The code of the typed key.
     * @see typedText
     * @see regex
     */
    infix fun type(keyCode : Int) : TextField{
        val key : Char = keyCode.toChar()
        when{
            regex.matches(key.toString()) -> typedText += key
            key == '\b' -> backspace()
            key == '\t' -> tab()
        }
        return this
    }

    /**
     * Removes the last character of the typed text.
     * @see typedText
     * @see type
     */
    private fun backspace(){
        typedText = typedText.substring(0, typedText.length - 1)
    }

    /**
     * Adds four spaces to the typed text.
     * @see type
     * @see typedText
     */
    private fun tab(){
        typedText += "    " //four spaces
    }

    /**
     * Focuses this TextField.
     * @see FOCUSED_COLOR
     */
    fun focus() : TextField{
        isFocused = true
        return this
    }

    /**
     * Unfocuses this TextField.
     * @see UNFOCUSED_COLOR
     */
    fun unfocus() : TextField{
        isFocused = false
        return this
    }

    /**
     * Returns the typed text.
     * @return The typed text.
     * @see typedText
     */
    fun typedText() : String = typedText

    /**
     * Clears the typed text.
     * @see typedText
     */
    fun clear() : TextField{
        typedText = ""
        return this
    }

    override fun loadParameters(g: Graphics) {
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)
        h.value = fm.maxAscent + fm.maxDescent + 2 * (LINE_THICKNESS + DELTA)
    }

    override fun drawDisplayer(g: Graphics) {
        drawText(g)
        drawBackground(g)
    }

    /**
     * Draws the text of this TextField.
     * @param g The Graphics context of the drawing.
     * @see typedText
     */
    private fun drawText(g : Graphics){
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_FONT)
        g.color = if(isFocused) FOCUSED_COLOR else UNFOCUSED_COLOR
        g.font = DEFAULT_FONT
        g.drawString(typedText, LINE_THICKNESS + DELTA, fm.maxAscent)
    }

    /**
     * Draws the background of this TextField.
     * @param g The Graphics context of the drawing.
     * @see FOCUSED_COLOR
     * @see UNFOCUSED_COLOR
     * @see DELTA
     * @see LINE_THICKNESS
     */
    private fun drawBackground(g : Graphics){
        g.fillRect(0, 0, LINE_THICKNESS, height())
        g.fillRect(0, 0, width(), LINE_THICKNESS)
        g.fillRect(0, height() - LINE_THICKNESS, width(), LINE_THICKNESS)
        g.fillRect(width() - LINE_THICKNESS, 0, width(), height())
    }

}