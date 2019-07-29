package llayout7.displayers

import llayout7.utilities.Action
import llayout7.DEFAULT_COLOR
import llayout7.DEFAULT_SMALL_FONT
import llayout7.utilities.GraphicAction
import llayout7.utilities.LObservable
import llayout7.utilities.matches
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.event.KeyEvent

/**
 * A TextField.
 * @since LLayout 1
 */
class TextField : Displayer {

    private companion object{

        /**
         * The default width of the TextField.
         * @since LLayout 1
         */
        private const val DEFAULT_WIDTH : Int = 200

        /**
         * The distance between the text and the bounds.
         * @since LLayout 1
         */
        private const val TEXT_TO_BOUNDS_DISTANCE : Int = 4

        /**
         * The default thickness of the lines of the default background.
         * @since LLayout 1
         */
        private const val DEFAULT_BORDER_LINE_THICKNESS : Int = 2

        /**
         * The default font of a TextField.
         * @since LLayout 1
         */
        private val DEFAULT_FONT : Font = DEFAULT_SMALL_FONT

        /**
         * The default color of a TextField.
         * @since LLayout 1
         */
        private val DEFAULT_TEXT_COLOR : Color = DEFAULT_COLOR

        /**
         * The default background of a TextField.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = DEFAULT_TEXT_COLOR
            g.fillRect(0, 0, w, DEFAULT_BORDER_LINE_THICKNESS)
            g.fillRect(0, 0, DEFAULT_BORDER_LINE_THICKNESS, h)
            g.fillRect(0, h - DEFAULT_BORDER_LINE_THICKNESS, w, DEFAULT_BORDER_LINE_THICKNESS)
            g.fillRect(w - DEFAULT_BORDER_LINE_THICKNESS, 0, DEFAULT_BORDER_LINE_THICKNESS, h)
        }

        /**
         * The key used to index this TextField's background.
         * @since LLayout 1
         */
        private const val BACKGROUND_KEY : String = "BACKGROUND FROM ITS WRAPPER"

        /**
         * A custom 'regex match' class.
         * @since LLayout 1
         */
        private class TextMatcher{

            companion object{

                /**
                 * A regex that matches any char.
                 * @since LLayout 1
                 */
                private const val ANYTHING_CHAR_REGEX           : String = "."

                /**
                 * A regex that matches any digit.
                 * @since LLayout 1
                 */
                private const val DIGIT_CHAR_REGEX              : String = "\\d"

                /**
                 * A regex that matches any character appearing in a representation of an integer.
                 * @since LLayout 1
                 */
                private const val INTEGER_CHAR_REGEX            : String = "[-]|[+]|$DIGIT_CHAR_REGEX"

                /**
                 * A regex that matches any character appearing in a representation of a positive real number.
                 * @since LLayout 1
                 */
                private const val POSITIVE_DOUBLE_CHAR_REGEX    : String = "\\d|[.]"

                /**
                 * A regex matching any character appearing in a representation of a real number.
                 * @since LLayout 1
                 */
                private const val DOUBLE_CHAR_REGEX             : String = "[-]|[+]|\\d|[.]"

                /**
                 * A regex matching any letter.
                 * @since LLayout 1
                 */
                private const val LETTERS_CHAR_REGEX            : String = "[a-zA-Z]"

                /**
                 * A regex matching any non letter character.
                 * @since LLayout 1
                 */
                private const val NON_LETTER_CHAR_REGEX         : String = "^[a-zA-Z]"

                /**
                 * A regex matching any alphanumeric character.
                 * @since LLayout 1
                 */
                private const val ALPHANUMERIC_CHAR_REGEX       : String = "\\w"

                /**
                 * A regex matching any non alphanumeric character.
                 * @since LLayout 1
                 */
                private const val NON_ALPHANUMERIC_CHAR_REGEX   : String = "\\W"

                /**
                 * A regex matching any character except for the \n character.
                 * @since LLayout 1
                 */
                private const val NO_ENTER_CHAR_REGEX           : String = "^\n"

                /**
                 * A regex matching letters or digits.
                 * @since LLayout 1
                 */
                private const val LETTER_OR_DIGIT_CHAR_REGEX    : String = "[a-zA-Z0-9]"

                /**
                 * A regex matching any character that is not a letter or a digit.
                 * @since LLayout 1
                 */
                private const val NON_LETTER_DIGIT_CHAR_REGEX   : String = "^[a-zA-Z0-9]"

                /**
                 * A regex matching a letter or a space.
                 * @since LLayout 1
                 */
                private const val LETTER_OR_SPACE_CHAR_REGEX    : String = " |[a-zA-Z]"

                /**
                 * A regex matching a positive integer.
                 * @since LLayout 1
                 */
                private const val POSITIVE_INTEGER_REGEX        : String = "\\d+"

                /**
                 * A regex matching an integer.
                 * @since LLayout 1
                 */
                private const val INTEGER_REGEX                 : String = "[-+]?\\d*"

                /**
                 * A regex matching a real number.
                 * @since LLayout 1
                 */
                private const val REAL_REGEX                    : String = "[\\-+]?\\d*[.]?\\d*"

                /**
                 * A regex matching a positive real number.
                 * @since LLayout 1
                 */
                private const val POSITIVE_REAL_REGEX           : String = "\\d*[.]?\\d*"

                /**
                 * A regex matching a word.
                 * @since LLayout 1
                 */
                private const val ANY_WORD_REGEX                : String = "[a-zA-Z]+"

                /**
                 * A regex matching something that does not contain letters.
                 * @since LLayout 1
                 */
                private const val ANY_NON_WORD_REGEX            : String = "^[a-zA-Z]+"

                /**
                 * A regex matching any string made of alphanumeric characters.
                 * @since LLayout 1
                 */
                private const val ALPHANUMERIC_REGEX            : String = "\\w+"

                /**
                 * A regex matching any string made of non alphanumeric characters.
                 * @since LLayout 1
                 */
                private const val NON_ALPHANUMERIC_REGEX        : String = "\\W+"

                /**
                 * A regex matching any string made of letters or digits.
                 * @since LLayout 1
                 */
                private const val WORD_AND_DIGITS_REGEX         : String = "[a-zA-Z0-9]+"

                /**
                 * A regex matching any string containing neither letters nor digits.
                 * @since LLayout 1
                 */
                private const val NON_WORD_OR_DIGITS_REGEX      : String = "^[a-zA-Z0-9]+"

                /**
                 * A regex matching any string containing letters and spaces.
                 * @since LLayout 1
                 */
                private const val SPACED_WORD_REGEX             : String = "[ a-zA-Z]+"

                /**
                 * A regex matching any string that does not contain the \n character.
                 * @since LLayout 1
                 */
                private const val NO_ENTER_REGEX                : String = "^\n*"

                /**
                 * Always true.
                 * @since LLayout 1
                 */
                private val ANYTHING_TEST : (String) -> Boolean = { true }

                /**
                 * True if the string is a positive integer.
                 * @since LLayout 1
                 */
                private val ANY_POSITIVE_INTEGER_TEST : (String) -> Boolean = { s -> Regex(POSITIVE_INTEGER_REGEX).matches(s) }

                /**
                 * True if the string is an integer.
                 * @since LLayout 1
                 */
                private val ANY_INTEGER_TEST : (String) -> Boolean = { s -> Regex(INTEGER_REGEX).matches(s) }

                /**
                 * True if the string is a real number.
                 * @since LLayout 1
                 */
                private val ANY_REAL_TEST : (String) -> Boolean = { s -> Regex(REAL_REGEX).matches(s) }

                /**
                 * True if the string is a positive real number.
                 * @since LLayout 1
                 */
                private val ANY_POSITIVE_REAL_TEST : (String) -> Boolean = { s -> Regex(POSITIVE_REAL_REGEX).matches(s) }

                /**
                 * True if the string is a word.
                 * @since LLayout 1
                 */
                private val ANY_WORD_TEST : (String) -> Boolean = { s -> Regex(ANY_WORD_REGEX).matches(s) }

                /**
                 * True if the string doesn't contain letters.
                 * @since LLayout 1
                 */
                private val ANY_NON_WORD_TEST : (String) -> Boolean = { s -> Regex(ANY_NON_WORD_REGEX).matches(s) }

                /**
                 * True if the string is composed of alphanumeric characters.
                 * @since LLayout 1
                 */
                private val ALPHANUMERIC_TEST : (String) -> Boolean = { s -> Regex(ALPHANUMERIC_REGEX).matches(s) }

                /**
                 * True if the string is composed of anything but alphanumeric characters.
                 * @since LLayout 1
                 */
                private val NON_ALPHANUMERIC_TEST : (String) -> Boolean = { s -> Regex(NON_ALPHANUMERIC_REGEX).matches(s) }

                /**
                 * True if the string is composed of letters and digits.
                 * @since LLayout 1
                 */
                private val WORD_OR_DIGITS_TEST : (String) -> Boolean = { s -> Regex(WORD_AND_DIGITS_REGEX).matches(s) }

                /**
                 * True if the string is composed of anything but letters or digits.
                 * @since LLayout 1
                 */
                private val NON_WORD_OR_DIGITS_TEST : (String) -> Boolean = { s -> Regex(NON_WORD_OR_DIGITS_REGEX).matches(s) }

                /**
                 * True if the string is composed of words and spaces.
                 * @since LLayout 1
                 */
                private val SPACED_WORD_TEST : (String) -> Boolean = { s -> Regex(SPACED_WORD_REGEX).matches(s) }

                /**
                 * True if the string doesn't contain the \n character.
                 * @since LLayout 1
                 */
                private val NO_ENTER_TEST : (String) -> Boolean = { s -> Regex(NO_ENTER_REGEX).matches(s) }

                /**
                 * True if the string is composed of a single character.
                 * @since LLayout 1
                 */
                private val SINGLE_CHAR_TEST : (String) -> Boolean = { s -> s.length == 1 }

                /**
                 * True if the string is composed of a single digit.
                 * @since LLayout 1
                 */
                private val SINGLE_DIGIT_TEST : (String) -> Boolean = { s -> Regex(DIGIT_CHAR_REGEX).matches(s) }

                /**
                 * True if the string is a 32-bit integer.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 32-bit unsigned integer.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 32-bit positive integer.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 64-bit double.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 64-bit positive double.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 32-bit float.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a positive 32-bit float.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 64-bit long.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 64-bit unsigned long.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 64-bit positive long.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 16-bit short.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 16-bit unsigned short.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 16-bit positive short.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 8-bit byte.
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 8-bit unsigned byte
                 * @since LLayout 1
                 */
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

                /**
                 * True if the string is a 8-bit positive byte.
                 * @since LLayout 1
                 */
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

                /**
                 * A TextMatcher that matches anything.
                 * @since LLayout 1
                 */
                val ANYTHING_MATCHER : TextMatcher = TextMatcher()

                /**
                 * A TextMatcher that matches any positive integer.
                 * @since LLayout 1
                 */
                val ANY_POSITIVE_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DIGIT_CHAR_REGEX)
                        .setStringMatcher(ANY_POSITIVE_INTEGER_TEST)

                /**
                 * A TextMatcher that matches any integer.
                 * @since LLayout 1
                 */
                val ANY_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(ANY_INTEGER_TEST)

                /**
                 * A TextMatcher that matches any real number.
                 * @since LLayout 1
                 */
                val ANY_REAL_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DOUBLE_CHAR_REGEX)
                        .setStringMatcher(ANY_REAL_TEST)

                /**
                 * A TextMatcher that matches any positive real number.
                 * @since LLayout 1
                 */
                val ANY_POSITIVE_REAL_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(POSITIVE_DOUBLE_CHAR_REGEX)
                        .setStringMatcher(ANY_POSITIVE_REAL_TEST)

                /**
                 * A TextMatcher that matches any word.
                 * @since LLayout 1
                 */
                val ANY_WORD_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(LETTERS_CHAR_REGEX)
                        .setStringMatcher(ANY_WORD_TEST)

                /**
                 * A TextMatcher that matches any string that doesn't contain letters.
                 * @since LLayout 1
                 */
                val ANY_NON_WORD_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NON_LETTER_CHAR_REGEX)
                        .setStringMatcher(ANY_NON_WORD_TEST)

                /**
                 * A TextMatcher that matches any alphanumeric string.
                 * @since LLayout 1
                 */
                val ALPHANUMERIC_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(ALPHANUMERIC_CHAR_REGEX)
                        .setStringMatcher(ALPHANUMERIC_TEST)

                /**
                 * A TextMatcher that matches any string that does not contain alphanumeric characters.
                 * @since LLayout 1
                 */
                val NON_ALPHANUMERIC_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NON_ALPHANUMERIC_CHAR_REGEX)
                        .setStringMatcher(NON_ALPHANUMERIC_TEST)

                /**
                 * A TextMatcher that matches a string that contains letters or digits.
                 * @since LLayout 1
                 */
                val WORD_OR_DIGIT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(LETTER_OR_DIGIT_CHAR_REGEX)
                        .setStringMatcher(WORD_OR_DIGITS_TEST)

                /**
                 * A TextMatcher that matches any string that does not contain letters or digits.
                 * @since LLayout 1
                 */
                val NON_WORD_AND_DIGITS_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NON_LETTER_DIGIT_CHAR_REGEX)
                        .setStringMatcher(NON_WORD_OR_DIGITS_TEST)

                /**
                 * A TextMatcher that matches a string containing words and spaces.
                 * @since LLayout 1
                 */
                val SPACED_WORD_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(LETTER_OR_SPACE_CHAR_REGEX)
                        .setStringMatcher(SPACED_WORD_TEST)

                /**
                 * A TextMatcher that matches a string that does not contain the \n character.
                 * @since LLayout 1
                 */
                val NO_ENTER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(NO_ENTER_CHAR_REGEX)
                        .setStringMatcher(NO_ENTER_TEST)

                /**
                 * A TextMatcher that matches a string composed of a single char.
                 * @since LLayout 1
                 */
                val SINGLE_CHAR_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(ANYTHING_CHAR_REGEX)
                        .setStringMatcher(SINGLE_CHAR_TEST)

                /**
                 * A TextMatcher that matches a string that is composed of a single digit.
                 * @since LLayout 1
                 */
                val SINGLE_DIGIT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DIGIT_CHAR_REGEX)
                        .setStringMatcher(SINGLE_DIGIT_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 32-bit integer.
                 * @since LLayout 1
                 */
                val INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(INTEGER_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 32-bit unsigned integer.
                 * @since LLayout 1
                 */
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DIGIT_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_INTEGER_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 64-bit double.
                 * @since LLayout 1
                 */
                val DOUBLE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DOUBLE_CHAR_REGEX)
                        .setStringMatcher(DOUBLE_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 64-bit positive double.
                 * @since LLayout 1
                 */
                val POSITIVE_DOUBLE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(POSITIVE_DOUBLE_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_DOUBLE_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 32-bit float.
                 * @since LLayout 1
                 */
                val FLOAT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(DOUBLE_CHAR_REGEX)
                        .setStringMatcher(FLOAT_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 32-bit positive float.
                 * @since LLayout 1
                 */
                val POSITIVE_FLOAT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(POSITIVE_DOUBLE_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_FLOAT_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 64-bit long.
                 * @since LLayout 1
                 */
                val LONG_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(LONG_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 64-bit unsigned long.
                 * @since LLayout 1
                 */
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_LONG_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_LONG_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 16-bit short.
                 * @since LLayout 1
                 */
                val SHORT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(SHORT_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 16-bit unsigned short.
                 * @since LLayout 1
                 */
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_SHORT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_SHORT_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 8-bit byte.
                 * @since LLayout 1
                 */
                val BYTE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(BYTE_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 8-bit unsigned byte.
                 * @since LLayout 1
                 */
                @kotlin.ExperimentalUnsignedTypes
                val UNSIGNED_BYTE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(UNSIGNED_BYTE_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 32-bit positive integer.
                 * @since LLayout 1
                 */
                val POSITIVE_INTEGER_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_INTEGER_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 64-bit positive long.
                 * @since LLayout 1
                 */
                val POSITIVE_LONG_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_LONG_TEST)

                /**
                 * A TextMatcher that matches a string representation of a 16-bit positive short.
                 * @since LLayout 1
                 */
                val POSITIVE_SHORT_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_SHORT_TEST)

                /**
                 * A TextMatcher that matches a atring representation of a 8-bit positive byte.
                 * @since LLayout 1
                 */
                val POSITIVE_BYTE_MATCHER : TextMatcher = TextMatcher()
                        .setCharMatcher(INTEGER_CHAR_REGEX)
                        .setStringMatcher(POSITIVE_BYTE_TEST)

            }

            /**
             * The object that verifies that a string is valid.
             * @since LLayout 1
             */
            private var stringMatcher : (String) -> Boolean = ANYTHING_TEST

            /**
             * The regex that verifies that a char is valid.
             * @since LLayout 1
             */
            private var charMatcher : Regex = Regex(ANYTHING_CHAR_REGEX)

            /**
             * Sets the string matcher.
             * @return this
             * @see stringMatcher
             * @since LLayout 1
             */
            fun setStringMatcher(matcher : (String) -> Boolean) : TextMatcher{
                stringMatcher = matcher
                return this
            }

            /**
             * Sets the char matcher.
             * @return this
             * @see charMatcher
             * @since LLayout 1
             */
            fun setCharMatcher(matcher : String) : TextMatcher{
                charMatcher = Regex(matcher)
                return this
            }

            /**
             * If [addition] is a valid char, adds it to [text] at the index [index], and returns the result.
             * If [addition] is not a valid char, returns [text] without modifications.
             * @since LLayout 1
             */
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

            /**
             * True if [text] is a valid string.
             * @since LLayout 1
             */
            fun matches(text : StringBuilder) : Boolean = isAValidString(text)

            /**
             * True if the given char is valid in the sense of the [charMatcher] regex.
             * @since LLayout 1
             */
            private fun isAValidChar(char : Char) : Boolean = charMatcher.matches(char)

            /**
             * True if the given CharSequence is valid in the sense of the [stringMatcher].
             * @since LLayout 1
             */
            private fun isAValidString(string : CharSequence) : Boolean = stringMatcher(string.toString())

        }

    }

    /**
     * The font of the text.
     * @since LLayout 1
     */
    private var textFont : Font = DEFAULT_FONT

    /**
     * The color of the text.
     * @since LLayout 1
     */
    private var textColor : Color = DEFAULT_TEXT_COLOR

    /**
     * The written text.
     * @since LLayout 1
     */
    private var text : StringBuilder = StringBuilder()

    /**
     * The position at which the mouse clicked the TextField.
     * @see LObservable
     * @since LLayout 1
     */
    private var clickAt : LObservable<Int> = LObservable(0)

    /**
     * True if the mouse clicked the textField.
     * @since LLayout 1
     */
    private var clicked : Boolean = false

    /**
     * The position of the caret on the TextField.
     * @since LLayout 1
     */
    private var caretPosition : Int = 0

    /**
     * The index at which the caret is placed.
     * @since LLayout 1
     */
    private var caretIndex : Int = 0

    /**
     * The TextMatcher that verifies the text.
     * @since LLayout 1
     */
    private var textMatcher : TextMatcher = TextMatcher.ANYTHING_MATCHER

    /**
     * The position from which the text is drawn.
     * @since LLayout 1
     */
    private var textDrawingPosition : Int = 0

    /**
     * The index of the first character on the screen.
     * @since LLayout 1
     */
    private var firstCharIndexOnScreen : Int = 0

    /**
     * The action executed when the user presses the enter key while this TextField has the input oocus.
     * @since LLayout 1
     */
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

    /**
     * The font of the text.
     * @since LLayout 1
     */
    private fun textFont() : Font = textFont

    /**
     * The color of the text.
     * @since LLayout 1
     */
    private fun textColor() : Color = textColor

    /**
     * The position of the caret.
     * @since LLayout 1
     */
    private fun caretPosition() : Int = caretPosition

    /**
     * The position at which the mouse clicked the TextField.
     * @since LLayout 1
     */
    private fun clickAt() : Int = clickAt.value

    /**
     * The written text.
     * @since LLayout 1
     */
    fun text() : String = text.toString()

    /**
     * Types a char at the caret index.
     * @return this
     * @since LLayout 1
     */
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

    /**
     * Moves the caret or calls the enter action.
     * @return this
     * @since LLayout 1
     */
    fun type(keyCode : Int) : TextField{
        when(keyCode){
            KeyEvent.VK_LEFT -> caretLeft()
            KeyEvent.VK_RIGHT -> caretRight()
            KeyEvent.VK_ENTER -> onEnterAction()
        }
        initialize()
        return this
    }

    /**
     * A simpler way to type.
     * @return this
     * @since LLayout 1
     */
    fun type(e : KeyEvent) : TextField{
        return if(e.id == KeyEvent.KEY_TYPED) type(e.keyChar) else type(e.keyCode)
    }

    /**
     * Performs a backspace.
     * @since LLayout 1
     */
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

    /**
     * Performs a 'delete'.
     * @since LLayout 1
     */
    private fun deleteKey(){
        when(caretIndex){
            text.length -> {}
            else -> text.deleteCharAt(caretIndex)
        }
    }

    /**
     * Moves the caret to the left.
     * @return this
     * @since LLayout 1
     */
    fun caretLeft() : TextField{
        when(caretIndex){
            0 -> {}
            else -> caretIndex--
        }
        initialize()
        return this
    }

    /**
     * Moves the caret to the right.
     * @return this
     * @since LLayout 1
     */
    fun caretRight() : TextField{
        when(caretIndex){
            text.length -> {}
            else -> caretIndex++
        }
        initialize()
        return this
    }

    /**
     * Changes the background of this textField.
     * @return this
     * @see GraphicAction
     * @since LLayout 1
     */
    fun setBackground(background : GraphicAction) : TextField{
        core.addGraphicAction(background, BACKGROUND_KEY)
        return this
    }

    /**
     * Changes the text font.
     * @return this
     * @since LLayout 1
     */
    fun setFont(font : Font) : TextField{
        textFont = font
        initialize()
        return this
    }

    /**
     * Changes the text color.
     * @return this
     * @since LLayout 1
     */
    fun setTextColor(color : Color) : TextField{
        textColor = color
        initialize()
        return this
    }

    /**
     * Clears the TextField.
     * @return this
     * @since LLayout 1
     */
    fun clear() : TextField{
        text.clear()
        caretIndex = 0
        caretPosition = TEXT_TO_BOUNDS_DISTANCE
        textDrawingPosition = TEXT_TO_BOUNDS_DISTANCE
        firstCharIndexOnScreen = 0
        return this
    }

    /**
     * Sets the action executed when the enter key is pressed while this TextField has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnEnterAction(action : Action) : TextField{
        onEnterAction = action
        return this
    }

    /**
     * The TextField now accepts anything.
     * @return this
     * @since LLayout 1
     */
    fun matchAnything() : TextField{
        textMatcher = TextMatcher.ANYTHING_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts any integer.
     * @return this
     * @since LLayout 1
     */
    fun matchAnyIntegerValue() : TextField{
        textMatcher = TextMatcher.ANY_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts any positive integer.
     * @return this
     * @since LLayout 1
     */
    fun matchAnyPositiveIntegerValue() : TextField{
        textMatcher = TextMatcher.ANY_POSITIVE_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts any real number.
     * @return this
     * @since LLayout 1
     */
    fun matchAnyRealNumber() : TextField{
        textMatcher = TextMatcher.ANY_REAL_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts any positive real number.
     * @return this
     * @since LLayout 1
     */
    fun matchAnyPositiveRealNumber() : TextField{
        textMatcher = TextMatcher.ANY_POSITIVE_REAL_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 32-bit integers.
     * @return this
     * @since LLayout 1
     */
    fun matchInteger() : TextField{
        textMatcher = TextMatcher.INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 32-bit positive integers.
     * @return this
     * @since LLayout 1
     */
    fun matchPositiveInteger() : TextField{
        textMatcher = TextMatcher.POSITIVE_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 32-bit unsigned integers.
     * @return this
     * @since LLayout 1
     */
    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedInteger() : TextField{
        textMatcher = TextMatcher.UNSIGNED_INTEGER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 64-bit doubles.
     * @return this
     * @since LLayout 1
     */
    fun matchDouble() : TextField{
        textMatcher = TextMatcher.DOUBLE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 64-bit positive doubles.
     * @return this
     * @since LLayout 1
     */
    fun matchPositiveDouble() : TextField{
        textMatcher = TextMatcher.POSITIVE_DOUBLE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 64-bit longs.
     * @return this
     * @since LLayout 1
     */
    fun matchLong() : TextField{
        textMatcher = TextMatcher.LONG_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 64-bit positive longs.
     * @return this
     * @since LLayout 1
     */
    fun matchPositiveLong() : TextField{
        textMatcher = TextMatcher.POSITIVE_LONG_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 64-bit unsigned longs.
     * @return this
     * @since LLayout 1
     */
    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedLong() : TextField{
        textMatcher = TextMatcher.UNSIGNED_LONG_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 16-bit shorts.
     * @return this
     * @since LLayout 1
     */
    fun matchShort() : TextField{
        textMatcher = TextMatcher.SHORT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 16-bit positive shorts.
     * @return this
     * @since LLayout 1
     */
    fun matchPositiveShort() : TextField{
        textMatcher = TextMatcher.POSITIVE_SHORT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 16-bit unsigned shorts.
     * @return this
     * @since LLayout 1
     */
    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedShort() : TextField{
        textMatcher = TextMatcher.UNSIGNED_SHORT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 8-bit bytes.
     * @return this
     * @since LLayout 1
     */
    fun matchByte() : TextField{
        textMatcher = TextMatcher.BYTE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 8-bit positive bytes.
     * @return this
     * @since LLayout 1
     */
    fun matchPositiveByte() : TextField{
        textMatcher = TextMatcher.POSITIVE_BYTE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 8-bit unsigned bytes.
     * @return this
     * @since LLayout 1
     */
    @kotlin.ExperimentalUnsignedTypes
    fun matchUnsignedByte() : TextField{
        textMatcher = TextMatcher.UNSIGNED_BYTE_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 32-bit floats.
     * @return this
     * @since LLayout 1
     */
    fun matchFloat() : TextField{
        textMatcher = TextMatcher.FLOAT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 32-bit positive floats.
     * @return this
     * @since LLayout 1
     */
    fun matchPositiveFloat() : TextField{
        textMatcher = TextMatcher.POSITIVE_FLOAT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts 32-bit unsigned floats.
     * @return this
     * @since LLayout 1
     */
    fun matchSingleCharacter() : TextField{
        textMatcher = TextMatcher.SINGLE_CHAR_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts single digits.
     * @return this
     * @since LLayout 1
     */
    fun matchSingleDigit() : TextField{
        textMatcher = TextMatcher.SINGLE_DIGIT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts words.
     * @return this
     * @since LLayout 1
     */
    fun matchWord() : TextField{
        textMatcher = TextMatcher.ANY_WORD_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now doesn't accept letters.
     * @return this
     * @since LLayout 1
     */
    fun matchNonWord() : TextField{
        textMatcher = TextMatcher.ANY_NON_WORD_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts alphanumeric characters.
     * @return this
     * @since LLayout 1
     */
    fun matchAlphanumeric() : TextField{
        textMatcher = TextMatcher.ALPHANUMERIC_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now doesn't accept alphanumeric characters..
     * @return this
     * @since LLayout 1
     */
    fun matchNonAlphanumeric() : TextField{
        textMatcher = TextMatcher.NON_ALPHANUMERIC_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts letters and spaces.
     * @return this
     * @since LLayout 1
     */
    fun matchWordIncludingSpaces() : TextField{
        textMatcher = TextMatcher.SPACED_WORD_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now accepts any char that isn't \n.
     * @return this
     * @since LLayout 1
     */
    fun matchNoEnter() : TextField{
        textMatcher = TextMatcher.NO_ENTER_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now only accepts letters or digits.
     * @return this
     * @since LLayout 1
     */
    fun matchWordOrDigit() : TextField{
        textMatcher = TextMatcher.WORD_OR_DIGIT_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now doesn't accept letters and digits.
     * @return this
     * @since LLayout 1
     */
    fun matchNonWordAndDigit() : TextField{
        textMatcher = TextMatcher.NON_WORD_AND_DIGITS_MATCHER
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    /**
     * The TextField now accepts a custom selection of chars and results.
     * @return this
     * @since LLayout 1
     */
    fun matchCustom(validCharsRegex : String, validStringTest : (String) -> Boolean) : TextField{
        textMatcher.setCharMatcher(validCharsRegex).setStringMatcher(validStringTest)
        if(!textMatcher.matches(text)) text.clear()
        return this
    }

    //DANGER ZONE
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