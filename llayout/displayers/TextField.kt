package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.DEFAULT_SMALL_FONT
import llayout.GraphicAction
import llayout.utilities.LObservable
import llayout.utilities.matches
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.event.KeyEvent

class TextField : Displayer {

    private companion object{
        private const val DEFAULT_WIDTH : Int = 200
        private const val TEXT_TO_BOUNDS_DISTANCE : Int = 4
        private const val DEFAULT_BORDER_LINE_THICKNESS : Int = 2
        private val DEFAULT_FONT : Font = DEFAULT_SMALL_FONT
        private val DEFAULT_TEXT_COLOR : Color = DEFAULT_COLOR
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = DEFAULT_TEXT_COLOR
            g.fillRect(0, 0, w, DEFAULT_BORDER_LINE_THICKNESS)
            g.fillRect(0, 0, DEFAULT_BORDER_LINE_THICKNESS, h)
            g.fillRect(0, h - DEFAULT_BORDER_LINE_THICKNESS, w, DEFAULT_BORDER_LINE_THICKNESS)
            g.fillRect(w - DEFAULT_BORDER_LINE_THICKNESS, 0, DEFAULT_BORDER_LINE_THICKNESS, h)
        }
        private const val BACKGROUND_KEY : String = "BACKGROUND FROM ITS WRAPPER"

        private class TextMatcher{

            companion object{

                private const val ANYTHING_CHAR_REGEX           : String = "."
                private const val DIGIT_CHAR_REGEX              : String = "\\d"
                private const val INTEGER_CHAR_REGEX            : String = "-|$DIGIT_CHAR_REGEX"
                private const val POSITIVE_DOUBLE_CHAR_REGEX    : String = "\\d|[.]"
                private const val DOUBLE_CHAR_REGEX             : String = "[-]|[+]|\\d|[.]"
                private const val LETTERS_CHAR_REGEX            : String = "[a-zA-Z]"
                private const val NON_LETTER_CHAR_REGEX         : String = "^[a-zA-Z]"
                private const val ALPHANUMERIC_CHAR_REGEX       : String = "\\w"
                private const val NON_ALPHANUMERIC_CHAR_REGEX   : String = "\\W"
                private const val NO_ENTER_CHAR_REGEX           : String = "^\n"
                private const val LETTER_OR_DIGIT_CHAR_REGEX    : String = "[a-zA-Z0-9]"
                private const val NON_LETTER_DIGIT_CHAR_REGEX   : String = "^[a-zA-Z0-9]"
                private const val LETTER_OR_SPACE_CHAR_REGEX    : String = " |[a-zA-Z]"

                private const val POSITIVE_INTEGER_REGEX        : String = "\\d+"
                private const val INTEGER_REGEX                 : String = "[-+]?\\d*"
                private const val REAL_REGEX                    : String = "[\\-+]?\\d*[.]?\\d*"
                private const val POSITIVE_REAL_REGEX           : String = "\\d*[.]?\\d*"
                private const val ANY_WORD_REGEX                : String = "[a-zA-Z]+"
                private const val ANY_NON_WORD_REGEX            : String = "^[a-zA-Z]+"
                private const val ALPHANUMERIC_REGEX            : String = "\\w+"
                private const val NON_ALPHANUMERIC_REGEX        : String = "\\W+"
                private const val WORD_AND_DIGITS_REGEX         : String = "[a-zA-Z0-9]+"
                private const val NON_WORD_OR_DIGITS_REGEX      : String = "^[a-zA-Z0-9]+"
                private const val SPACED_WORD_REGEX             : String = "[ a-zA-Z]+"
                private const val NO_ENTER_REGEX                : String = "^\n*"

                private val ANYTHING_TEST : (String) -> Boolean = { true }
                private val ANY_POSITIVE_INTEGER_TEST : (String) -> Boolean = { s -> Regex(POSITIVE_INTEGER_REGEX).matches(s) }
                private val ANY_INTEGER_TEST : (String) -> Boolean = { s -> Regex(INTEGER_REGEX).matches(s) }
                private val ANY_REAL_TEST : (String) -> Boolean = { s -> Regex(REAL_REGEX).matches(s) }
                private val ANY_POSITIVE_REAL_TEST : (String) -> Boolean = { s -> Regex(POSITIVE_REAL_REGEX).matches(s) }
                private val ANY_WORD_TEST : (String) -> Boolean = { s -> Regex(ANY_WORD_REGEX).matches(s) }
                private val ANY_NON_WORD_TEST : (String) -> Boolean = { s -> Regex(ANY_NON_WORD_REGEX).matches(s) }
                private val ALPHANUMERIC_TEST : (String) -> Boolean = { s -> Regex(ALPHANUMERIC_REGEX).matches(s) }
                private val NON_ALPHANUMERIC_TEST : (String) -> Boolean = { s -> Regex(NON_ALPHANUMERIC_REGEX).matches(s) }
                private val WORD_OR_DIGITS_TEST : (String) -> Boolean = { s -> Regex(WORD_AND_DIGITS_REGEX).matches(s) }
                private val NON_WORD_OR_DIGITS_TEST : (String) -> Boolean = { s -> Regex(NON_WORD_OR_DIGITS_REGEX).matches(s) }
                private val SPACED_WORD_TEST : (String) -> Boolean = { s -> Regex(SPACED_WORD_REGEX).matches(s) }
                private val NO_ENTER_TEST : (String) -> Boolean = { s -> Regex(NO_ENTER_REGEX).matches(s) }
                private val SINGLE_CHAR_TEST : (String) -> Boolean = { s -> s.length == 1 }
                private val SINGLE_DIGIT_TEST : (String) -> Boolean = { s -> Regex(DIGIT_CHAR_REGEX).matches(s) }
                private val INTEGER_TEST : (String) -> Boolean = { s ->
                    if(Regex(INTEGER_REGEX).matches(s)){
                        try{
                            s.toInt()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                @kotlin.ExperimentalUnsignedTypes
                private val UNSIGNED_INTEGER_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toUInt()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val POSITIVE_INTEGER_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toInt()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val DOUBLE_TEST : (String) -> Boolean = { s ->
                    if(Regex(DOUBLE_CHAR_REGEX).matches(s)){
                        try{
                            s.toDouble()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val POSITIVE_DOUBLE_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_DOUBLE_CHAR_REGEX).matches(s)){
                        try{
                            s.toDouble()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val FLOAT_TEST : (String) -> Boolean = { s ->
                    if(Regex(DOUBLE_CHAR_REGEX).matches(s)){
                        try{
                            s.toFloat()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val POSITIVE_FLOAT_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_DOUBLE_CHAR_REGEX).matches(s)){
                        try{
                            s.toFloat()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val LONG_TEST : (String) -> Boolean = { s ->
                    if(Regex(INTEGER_REGEX).matches(s)){
                        try{
                            s.toLong()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                @kotlin.ExperimentalUnsignedTypes
                private val UNSIGNED_LONG_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toULong()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val POSITIVE_LONG_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toLong()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val SHORT_TEST : (String) -> Boolean = { s ->
                    if(Regex(INTEGER_REGEX).matches(s)){
                        try{
                            s.toShort()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                @kotlin.ExperimentalUnsignedTypes
                private val UNSIGNED_SHORT_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toUShort()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val POSITIVE_SHORT_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toShort()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val BYTE_TEST : (String) -> Boolean = { s ->
                    if(Regex(INTEGER_REGEX).matches(s)){
                        try{
                            s.toByte()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                @kotlin.ExperimentalUnsignedTypes
                private val UNSIGNED_BYTE_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toUByte()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }
                private val POSITIVE_BYTE_TEST : (String) -> Boolean = { s ->
                    if(Regex(POSITIVE_INTEGER_REGEX).matches(s)){
                        try{
                            s.toByte()
                            true
                        }catch(_ : NumberFormatException){
                            false
                        }
                    }else{
                        false
                    }
                }

                val ANYTHING_MATCHER : TextMatcher = TextMatcher()
                val ANY_POSITIVE_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DIGIT_CHAR_REGEX)
                        .setStringMatcher(ANY_POSITIVE_INTEGER_TEST)
                val ANY_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(ANY_INTEGER_TEST)
                val ANY_REAL_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DOUBLE_CHAR_REGEX)
                        .setStringMatcher(ANY_REAL_TEST)
                val ANY_POSITIVE_REAL_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(POSITIVE_DOUBLE_CHAR_REGEX)
                        .setStringMatcher(ANY_POSITIVE_REAL_TEST)
                val ANY_WORD_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(LETTERS_CHAR_REGEX)
                        .setStringMatcher(ANY_WORD_TEST)
                val ANY_NON_WORD_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NON_LETTER_CHAR_REGEX)
                        .setStringMatcher(ANY_NON_WORD_TEST)
                val ALPHANUMERIC_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(ALPHANUMERIC_CHAR_REGEX)
                        .setStringMatcher(ALPHANUMERIC_TEST)
                val NON_ALPHANUMERIC_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NON_ALPHANUMERIC_CHAR_REGEX)
                        .setStringMatcher(NON_ALPHANUMERIC_TEST)
                val WORD_OR_DIGIT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(LETTER_OR_DIGIT_CHAR_REGEX)
                        .setStringMatcher(WORD_OR_DIGITS_TEST)
                val NON_WORD_AND_DIGITS_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NON_LETTER_DIGIT_CHAR_REGEX)
                        .setStringMatcher(NON_WORD_OR_DIGITS_TEST)
                val SPACED_WORD_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(LETTER_OR_SPACE_CHAR_REGEX)
                        .setStringMatcher(SPACED_WORD_TEST)
                val NO_ENTER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NO_ENTER_CHAR_REGEX)
                        .setStringMatcher(NO_ENTER_TEST)
                val SINGLE_CHAR_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(ANYTHING_CHAR_REGEX)
                        .setStringMatcher(SINGLE_CHAR_TEST)
                val SINGLE_DIGIT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DIGIT_CHAR_REGEX)
                        .setStringMatcher(SINGLE_DIGIT_TEST)
                val INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(INTEGER_TEST)
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DIGIT_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_INTEGER_TEST)
                val DOUBLE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DOUBLE_CHAR_REGEX)
                        .setStringMatcher(DOUBLE_TEST)
                val POSITIVE_DOUBLE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(POSITIVE_DOUBLE_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_DOUBLE_TEST)
                val FLOAT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DOUBLE_CHAR_REGEX)
                        .setStringMatcher(FLOAT_TEST)
                val POSITIVE_FLOAT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(POSITIVE_DOUBLE_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_FLOAT_TEST)
                val LONG_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(LONG_TEST)
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_LONG_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_LONG_TEST)
                val SHORT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(SHORT_TEST)
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_SHORT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_SHORT_TEST)
                val BYTE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(BYTE_TEST)
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_BYTE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_BYTE_TEST)
                val POSITIVE_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_INTEGER_TEST)
                val POSITIVE_LONG_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_LONG_TEST)
                val POSITIVE_SHORT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_SHORT_TEST)
                val POSITIVE_BYTE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_BYTE_TEST)

            }

            private var stringMatcher : (String) -> Boolean = ANYTHING_TEST

            private var charMatcher : Regex = Regex(ANYTHING_CHAR_REGEX)

            fun setStringMatcher(matcher : (String) -> Boolean) : TextMatcher{
                stringMatcher = matcher
                return this
            }

            fun setCharMatcher(matcher : String) : TextMatcher{
                charMatcher = Regex(matcher)
                return this
            }

            fun addIfValid(text : StringBuilder, addition : Char, index : Int) : StringBuilder{
                if(index < 0) throw IllegalArgumentException("Index $index is negative, and hence doesn't represent an index. " +
                        "TextMatcher.addIfValid requires a positive index")
                if(index > text.length) throw IllegalArgumentException("Index $index is greater than the length ${text.length} of the " +
                        "submitted text. TextMatcher.addIfValid requires an index bounded by this length.")
                val copy : StringBuilder = StringBuilder(text)
                if(isAValidChar(addition)){
                    copy.insert(index, addition)
                    if(isAValidString(copy)){
                        return copy
                    }
                }
                return text
            }

            fun matches(text : StringBuilder) : Boolean = isAValidString(text)

            private fun isAValidChar(char : Char) : Boolean = charMatcher.matches(char)

            private fun isAValidString(string : CharSequence) : Boolean = stringMatcher(string.toString())

        }

    }

    private var textFont : Font = DEFAULT_FONT

    private var textColor : Color = DEFAULT_TEXT_COLOR

    private var text : StringBuilder = StringBuilder()

    private var clickAt : LObservable<Int> = LObservable(0)

    private var clicked : Boolean = false

    private var caretPosition : Int = 0

    private var caretIndex : Int = 0

    private var textMatcher : TextMatcher = TextMatcher.ANYTHING_MATCHER

    private var textDrawingPosition : Int = 0

    private var firstCharIndexOnScreen : Int = 0

    private var onEnterAction : Action = {}

    init{
        setBackground(DEFAULT_BACKGROUND)
        setOnMouseReleasedAction { e ->
            clicked = true
            clickAt.value = e.x
        }
        clickAt.addListener { initialize() }
        setOnKeyTypedAction { e -> type(e.keyChar) }
        setOnKeyPressedAction { e -> type(e.keyCode) }
        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            val fm : FontMetrics = g.getFontMetrics(textFont())
            g.color = textColor()
            g.font = textFont()
            g.drawString(text(), textDrawingPosition, fm.maxAscent + TEXT_TO_BOUNDS_DISTANCE)
        })
        core.addGraphicAction({ g : Graphics, _ : Int, h : Int ->
            g.color = textColor()
            g.drawLine(caretPosition(), 0, caretPosition(), h)
        })
    }

    constructor(width : Int = DEFAULT_WIDTH) : super(){
        //DON'T
        core.setWidth(width)
    }

    constructor(width : Double) : super(){
        //DON'T
        core.setWidth(width)
    }

    private fun textFont() : Font = textFont

    private fun textColor() : Color = textColor

    private fun caretPosition() : Int = caretPosition

    private fun clickAt() : Int = clickAt.value

    fun text() : String = text.toString()

    fun type(char : Char) : TextField{
        when (char) {
            '\b' -> backspace()
            KeyEvent.VK_DELETE.toChar() -> deleteKey()
            else -> {
                val newText = textMatcher.addIfValid(text, char, caretIndex)
                if(text != newText){
                    text = newText
                    caretIndex++
                }
            }
        }
        initialize()
        return this
    }

    fun type(keyCode : Int) : TextField{
        when(keyCode){
            KeyEvent.VK_LEFT -> caretLeft()
            KeyEvent.VK_RIGHT -> caretRight()
            KeyEvent.VK_ENTER -> onEnterAction()
        }
        initialize()
        return this
    }

    fun type(e : KeyEvent) : TextField{
        return if(e.id == KeyEvent.KEY_TYPED) type(e.keyChar) else type(e.keyCode)
    }

    private fun backspace(){
        when(caretIndex){
            0 -> {}
            else -> {
                text.deleteCharAt(caretIndex - 1)
                caretIndex--
                initialize()
            }
        }
    }

    private fun deleteKey(){
        when(caretIndex){
            text.length -> {}
            else -> text.deleteCharAt(caretIndex)
        }
    }

    fun caretLeft() : TextField{
        when(caretIndex){
            0 -> {}
            else -> caretIndex--
        }
        initialize()
        return this
    }

    fun caretRight() : TextField{
        when(caretIndex){
            text.length -> {}
            else -> caretIndex++
        }
        initialize()
        return this
    }

    fun setBackground(background : GraphicAction) : TextField{
        core.addGraphicAction(background, BACKGROUND_KEY)
        return this
    }

    fun setFont(font : Font) : TextField{
        textFont = font
        initialize()
        return this
    }

    fun setTextColor(color : Color) : TextField{
        textColor = color
        initialize()
        return this
    }

    fun clear() : TextField{
        text.clear()
        caretIndex = 0
        caretPosition = TEXT_TO_BOUNDS_DISTANCE
        textDrawingPosition = TEXT_TO_BOUNDS_DISTANCE
        firstCharIndexOnScreen = 0
        return this
    }

    fun setOnEnterAction(action : Action) : TextField{
        onEnterAction = action
        return this
    }

    fun matchAnything() : TextField{
        textMatcher = TextMatcher.ANYTHING_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchAnyIntegerValue() : TextField{
        textMatcher = TextMatcher.ANY_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchAnyPositiveIntegerValue() : TextField{
        textMatcher = TextMatcher.ANY_POSITIVE_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchAnyRealNumber() : TextField{
        textMatcher = TextMatcher.ANY_REAL_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchAnyPositiveRealNumber() : TextField{
        textMatcher = TextMatcher.ANY_POSITIVE_REAL_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchInteger() : TextField{
        textMatcher = TextMatcher.INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchPositiveInteger() : TextField{
        textMatcher = TextMatcher.POSITIVE_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedInteger() : TextField{
        textMatcher = TextMatcher.UNSIGNED_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchDouble() : TextField{
        textMatcher = TextMatcher.DOUBLE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchPositiveDouble() : TextField{
        textMatcher = TextMatcher.POSITIVE_DOUBLE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchLong() : TextField{
        textMatcher = TextMatcher.LONG_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchPositiveLong() : TextField{
        textMatcher = TextMatcher.POSITIVE_LONG_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedLong() : TextField{
        textMatcher = TextMatcher.UNSIGNED_LONG_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchShort() : TextField{
        textMatcher = TextMatcher.SHORT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchPositiveShort() : TextField{
        textMatcher = TextMatcher.POSITIVE_SHORT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedShort() : TextField{
        textMatcher = TextMatcher.UNSIGNED_SHORT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchByte() : TextField{
        textMatcher = TextMatcher.BYTE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchPositiveByte() : TextField{
        textMatcher = TextMatcher.POSITIVE_BYTE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedByte() : TextField{
        textMatcher = TextMatcher.UNSIGNED_BYTE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchFloat() : TextField{
        textMatcher = TextMatcher.FLOAT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchPositiveFloat() : TextField{
        textMatcher = TextMatcher.POSITIVE_FLOAT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchSingleCharacter() : TextField{
        textMatcher = TextMatcher.SINGLE_CHAR_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchSingleDigit() : TextField{
        textMatcher = TextMatcher.SINGLE_DIGIT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchWord() : TextField{
        textMatcher = TextMatcher.ANY_WORD_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchNonWord() : TextField{
        textMatcher = TextMatcher.ANY_NON_WORD_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchAlphanumeric() : TextField{
        textMatcher = TextMatcher.ALPHANUMERIC_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchNonAlphanumeric() : TextField{
        textMatcher = TextMatcher.NON_ALPHANUMERIC_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchWordIncludingSpaces() : TextField{
        textMatcher = TextMatcher.SPACED_WORD_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchNoEnter() : TextField{
        textMatcher = TextMatcher.NO_ENTER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchWordOrDigit() : TextField{
        textMatcher = TextMatcher.WORD_OR_DIGIT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchNonWordAndDigit() : TextField{
        textMatcher = TextMatcher.NON_WORD_AND_DIGITS_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    fun matchCustom(validCharsRegex : String, validStringTest : (String) -> Boolean) : TextField{
        textMatcher.setCharMatcher(validCharsRegex).setStringMatcher(validStringTest)
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    //DANGER ZONE
    //FUTURE SELF, THE NEXT TIME YOU FEEL THE NEED TO REFACTOR THIS, BEWARE
    //YOU'VE BEEN WARNED
    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        val fm : FontMetrics = g.getFontMetrics(textFont)
        //DON'T
        core.setHeight(fm.maxAscent + fm.maxDescent + 2 * TEXT_TO_BOUNDS_DISTANCE)
        when {
            text.isEmpty() -> caretPosition = TEXT_TO_BOUNDS_DISTANCE
            fm.stringWidth(text()) <= width() -> {
                if(clicked){
                    firstCharIndexOnScreen = 0
                    textDrawingPosition = TEXT_TO_BOUNDS_DISTANCE
                    var positionWithoutChar : Int = TEXT_TO_BOUNDS_DISTANCE
                    var positionWithChar : Int = TEXT_TO_BOUNDS_DISTANCE
                    for(i : Int in 0 until text().length){
                        positionWithChar += fm.charWidth(text()[i])
                        if(clickAt() in positionWithoutChar until positionWithChar){
                            if(clickAt() - positionWithoutChar <= positionWithChar - clickAt()){
                                caretPosition = positionWithoutChar
                                caretIndex = i
                            }else{
                                caretPosition = positionWithChar
                                caretIndex = i + 1
                            }
                            break
                        }
                        positionWithoutChar += fm.charWidth(text()[i])
                    }
                    clicked = false
                }else{
                    firstCharIndexOnScreen = 0
                    textDrawingPosition = TEXT_TO_BOUNDS_DISTANCE
                    caretPosition = if(caretIndex == text().length){
                        fm.stringWidth(text()) + TEXT_TO_BOUNDS_DISTANCE
                    }else{
                        fm.stringWidth(text().substring(0, caretIndex)) + TEXT_TO_BOUNDS_DISTANCE
                    }
                }
            }
            else -> {
                //Now this is podracing
                if(clicked){
                    var positionWithoutChar : Int = textDrawingPosition + fm.stringWidth(text().substring(0, firstCharIndexOnScreen))
                    var positionWithChar : Int = positionWithoutChar
                    for(i : Int in firstCharIndexOnScreen until text().length){
                        positionWithChar += fm.charWidth(text()[i])
                        if(clickAt() in positionWithoutChar until positionWithChar){
                            if(clickAt() - positionWithoutChar <= positionWithChar - clickAt()){
                                caretPosition = positionWithoutChar
                                caretIndex = i
                            }else{
                                caretPosition = positionWithChar
                                caretIndex = i + 1
                            }
                            break
                        }
                        positionWithoutChar += fm.charWidth(text()[i])
                    }
                    clicked = false
                }else{
                    when {
                        //Caret too far left
                        textDrawingPosition + fm.stringWidth(text().substring(0, caretIndex)) < TEXT_TO_BOUNDS_DISTANCE -> {
                            firstCharIndexOnScreen = caretIndex
                            caretPosition = TEXT_TO_BOUNDS_DISTANCE
                            textDrawingPosition = TEXT_TO_BOUNDS_DISTANCE - fm.stringWidth(text().substring(0, caretIndex))
                        }
                        //Caret too far right
                        textDrawingPosition + fm.stringWidth(text().substring(0, caretIndex)) > width() - TEXT_TO_BOUNDS_DISTANCE -> {
                            caretPosition = width() - TEXT_TO_BOUNDS_DISTANCE
                            textDrawingPosition = width() - TEXT_TO_BOUNDS_DISTANCE - fm.stringWidth(text().substring(0, caretIndex))
                            var index : Int = caretIndex
                            var position : Int = caretPosition
                            while(position > TEXT_TO_BOUNDS_DISTANCE){
                                position -= fm.charWidth(text()[index - 1])
                                index--
                            }
                            firstCharIndexOnScreen = index
                        }
                        //Caret still inside
                        else -> {
                            caretPosition = fm.stringWidth(text().substring(0, caretIndex)) + textDrawingPosition
                        }
                    }
                }
            }
        }
    }

}