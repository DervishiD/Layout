package llayout.displayers

import llayout.DEFAULT_COLOR
import llayout.DEFAULT_SMALL_FONT
import llayout.utilities.matches
import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*

/**
 * A simple TextField. Quite simple, AND probably breakable.
 * @see Displayer
 */
class TextField : Displayer {

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

        private const val NO_ENTER : String = "[^\n]"

        private const val ALL_CHARS : String = "."

        private const val DIGITS_ONLY : String = "\\d"

        private const val DOUBLES : String = "\\d|\\."

        private const val WORD_CHARACTERS : String = "\\w"

        private const val LETTERS : String = "[a-zA-Z]"

        private const val LETTERS_OR_DIGITS : String = "$DIGITS_ONLY|$LETTERS"

        private val DEFAULT_REGEX : Regex = Regex(NO_ENTER)

    }

    /**
     * The text currently typed on this TextField.
     * @see type
     */
    private var typedText : StringBuilder

    /**
     * True if this TextField is focused.
     * @see focus
     */
    private var isFocused : Boolean = false

    /**
     * A Regex that defines which characters can be typed in this TextField.
     * @see type
     */
    private var regex : Regex = DEFAULT_REGEX
        set(value){
            field = value
            cleanTypedText()
        }

    private var relativeW : Double? = null

    private var clickAt : Int = 0

    private var caretPosition : Int = 0

    private var clicked : Boolean = false

    /*
     * BEFORE THE caretIndex CHARACTER.
     * -1 INDICATES THE END OF THE STRING.
     * THAT IS, THE USER TYPES THE caretIndex CHARACTER, OR AT THE END IF -1
     */
    private var caretIndex : Int = -1

    init{
        setOnMouseReleasedAction { e -> run{
            focus()
            clickAt = e.x
            clicked = true
            initialize()
        } }
        setOnKeyTypedAction { e -> type(e.keyChar) }
        setOnKeyPressedAction { e -> type(e.keyCode) }
    }

    constructor(width : Int = DEFAULT_WIDTH, defaultText: CharSequence = "") : super(){
        typedText = StringBuilder(defaultText)
        w.value = width
    }

    constructor(width : Double, defaultText: CharSequence = "") : super(){
        typedText = StringBuilder(defaultText)
        relativeW = width
        requestUpdate()
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): Displayer {
        if(relativeW != null) w.value = (relativeW!! * frameWidth).toInt()
        return super.updateRelativeValues(frameWidth, frameHeight)
    }

    fun noEnter() : TextField{
        regex = Regex(NO_ENTER)
        return this
    }

    fun allInputs() : TextField{
        regex = Regex(ALL_CHARS)
        return this
    }

    fun digitsOnly() : TextField{
        regex = Regex(DIGITS_ONLY)
        return this
    }

    fun numbersOnly() : TextField{
        regex = Regex(DOUBLES)
        return this
    }

    fun wordsOnly() : TextField{
        regex = Regex(WORD_CHARACTERS)
        return this
    }

    fun lettersOnly() : TextField{
        regex = Regex(LETTERS)
        return this
    }

    fun lettersOrDigits() : TextField{
        regex = Regex(LETTERS_OR_DIGITS)
        return this
    }

    private fun cleanTypedText() : TextField{
        for(i : Int in typedText.length - 1 downTo 0){
            if(!regex.matches(typedText[i])){
                typedText.deleteCharAt(i)
            }
        }
        return this
    }

    /**
     * Types the given character (as a keyCode) in this TextField.
     * @param keyCode The code of the typed key.
     * @see typedText
     * @see regex
     */
    fun type(keyCode : Int) : TextField {
        when(keyCode){
            VK_LEFT -> caretLeft()
            VK_RIGHT -> caretRight()
        }
        initialize()
        return this
    }

    infix fun type(char : Char) : TextField{
        when{
            char == '\b' -> backspace()
            char == VK_DELETE.toChar() -> deleteKey()
            regex.matches(char) -> insertCharacter(char)
        }
        initialize()
        return this
    }

    infix fun type(e : KeyEvent) : TextField{
        return if(e.id == KEY_TYPED) type(e.keyChar) else type(e.keyCode)
    }

    private infix fun insertCharacter(c : Char) : TextField{
        if(caretIndex == -1){
            typedText.append(c)
        }else{
            typedText.insert(caretIndex, c)
            caretIndex++
        }
        return this
    }

    /**
     * Removes the last character of the typed text.
     * @see typedText
     * @see type
     */
    private fun backspace(){
        when(caretIndex){
            -1 -> if(typedText.isNotEmpty()) typedText.deleteCharAt(typedText.length - 1)
            0 -> {}
            else -> run{
                typedText.deleteCharAt(caretIndex - 1)
                caretIndex--
            }
        }
    }

    private fun deleteKey() : TextField{
        when(caretIndex){
            -1 -> {}
            else -> run{
                typedText.deleteCharAt(caretIndex)
                if(caretIndex == typedText.length) caretIndex = -1
            }
        }
        return this
    }

    fun caretLeft() : TextField{
        when(caretIndex){
            0 -> {}
            -1 -> caretIndex = typedText.length - 1
            else -> caretIndex--
        }
        initialize()
        return this
    }

    fun caretRight() : TextField{
        when(caretIndex){
            -1 -> {}
            typedText.length - 1 -> caretIndex = -1
            else -> caretIndex++
        }
        initialize()
        return this
    }

    /**
     * Focuses this TextField.
     * @see FOCUSED_COLOR
     */
    fun focus() : TextField {
        isFocused = true
        return this
    }

    /**
     * Unfocuses this TextField.
     * @see UNFOCUSED_COLOR
     */
    fun unfocus() : TextField {
        isFocused = false
        return this
    }

    /**
     * Returns the typed text.
     * @return The typed text.
     * @see typedText
     */
    fun typedText() : String = typedText.toString()

    /**
     * Clears the typed text.
     * @see typedText
     */
    fun clear() : TextField {
        typedText.clear()
        initialize()
        return this
    }

    override fun loadParameters(g: Graphics) {
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_SMALL_FONT)
        h.value = fm.maxAscent + fm.maxDescent + 2 * (LINE_THICKNESS + DELTA)
        if(clicked){
            var s = ""
            var without = 0
            var with = 0
            var found = false
            for(i : Int in 0 until typedText.length){
                without = fm.stringWidth(s) + LINE_THICKNESS + DELTA
                s += typedText[i]
                with = fm.stringWidth(s) + LINE_THICKNESS + DELTA
                if(clickAt in without..with){
                    found = true
                    caretIndex = i
                    break
                }
            }
            if(found){
                if(clickAt - without < with - clickAt){
                    caretPosition = without
                }else{
                    caretPosition = with
                    caretIndex++
                }
            }else{
                caretPosition = fm.stringWidth(typedText.toString()) + LINE_THICKNESS + DELTA
                caretIndex = -1
            }
            clicked = false
        }else if(caretIndex == -1){
            caretPosition = fm.stringWidth(typedText.toString()) + LINE_THICKNESS + DELTA
        }else{
            caretPosition = fm.stringWidth(typedText.substring(0, caretIndex)) + LINE_THICKNESS + DELTA
        }
    }

    override fun drawDisplayer(g: Graphics){
        drawText(g).drawBackground(g)
    }

    /**
     * Draws the text of this TextField.
     * @param g The Graphics context of the drawing.
     * @see typedText
     */
    private fun drawText(g : Graphics) : TextField{
        val fm : FontMetrics = g.getFontMetrics(DEFAULT_SMALL_FONT)
        g.color = if(isFocused) FOCUSED_COLOR else UNFOCUSED_COLOR
        g.font = DEFAULT_SMALL_FONT
        val stringWidth = fm.stringWidth(typedText())
        if(stringWidth <= width() - 2 * LINE_THICKNESS - 2 * DELTA){
            g.drawString(typedText(), LINE_THICKNESS + DELTA, fm.maxAscent)
        }else{
            g.drawString(typedText(), width() - LINE_THICKNESS - DELTA - stringWidth, fm.maxAscent)
        }
        return this
    }

    /**
     * Draws the background of this TextField.
     * @param g The Graphics context of the drawing.
     * @see FOCUSED_COLOR
     * @see UNFOCUSED_COLOR
     * @see DELTA
     * @see LINE_THICKNESS
     */
    private fun drawBackground(g : Graphics) : TextField = drawBoundary(g).drawCaret(g)

    private infix fun drawBoundary(g : Graphics) : TextField{
        g.fillRect(0, 0, LINE_THICKNESS, height())
        g.fillRect(0, 0, width(), LINE_THICKNESS)
        g.fillRect(0, height() - LINE_THICKNESS, width(), LINE_THICKNESS)
        g.fillRect(width() - LINE_THICKNESS, 0, width(), height())
        return this
    }

    private infix fun drawCaret(g : Graphics) : TextField{
        g.drawLine(caretPosition, 0, caretPosition, height())
        return this
    }

}