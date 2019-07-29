package llayout.utilities

import llayout.DEFAULT_COLOR
import llayout.DEFAULT_SMALL_FONT
import java.awt.Color
import java.awt.Font

/**
 * Class that represents a displayed String, with a font and a color.
 * @since LLayout 1
 */
class StringDisplay : Iterable<Char> {

    companion object{

        /**
         * The iterator for a StringDisplay.
         * @since LLayout 1
         */
        private class StringDisplayIterator(private val s : String) : Iterator<Char>{

            /**
             * The index of the current char.
             * @since LLayout 1
             */
            var index : Int = 0

            override fun hasNext(): Boolean = index < s.length

            override fun next(): Char{
                val result = s[index]
                index++
                return result
            }
        }
    }

    /**
     * The displayed text
     * @since LLayout 1
     */
    var text : String

    /**
     * The text's colour.
     * @since LLayout 1
     */
    var color : Color

    /**
     * The text's font.
     * @since LLayout 1
     */
    var font : Font

    constructor(text : CharSequence, font : Font, color : Color){
        this.text = text.toString()
        this.font = font
        this.color = color
    }
    constructor(text : CharSequence, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : CharSequence, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : CharSequence) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor() : this("", DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Int, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Int, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Int, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Int) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Double, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Double, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Double, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Double) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Float, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Float, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Float, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Float) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Short, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Short, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Short, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Short) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Long, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Long, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Long, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Long) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Byte, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Byte, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Byte, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Byte) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Boolean, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Boolean, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Boolean, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Boolean) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(text : Char, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Char, color : Color) : this(text, DEFAULT_SMALL_FONT, color)
    constructor(text : Char, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Char) : this(text, DEFAULT_SMALL_FONT, DEFAULT_COLOR)
    constructor(s : StringDisplay){
        text = s.text
        color = s.color
        font = s.font
    }

    /**
     * String split function but for StringDisplay.
     * @since LLayout 1
     */
    fun split(separator : String) : MutableList<StringDisplay>{
        val result : MutableList<StringDisplay> = mutableListOf()
        val splittedText : Collection<String> = text.split(separator)
        for(s : String in splittedText){
            result.add(StringDisplay(s, font, color))
        }
        return result
    }

    /**
     * Splits the StringDisplay at \n.
     * @since LLayout 1
     */
    fun toLines() : MutableList<StringDisplay> = split("\n")

    /**
     * Adds a string to the StringDisplay
     * @since LLayout 1
     */
    fun push(s : CharSequence) : StringDisplay{
        text += s
        return this
    }

    /**
     * Adds a char to the StringDisplay
     * @since LLayout 1
     */
    fun push(c : Char) : StringDisplay = push(c.toString())

    /**
     * Adds an Int to the StringDisplay
     * @since LLayout 1
     */
    fun push(i : Int) : StringDisplay = push(i.toString())

    /**
     * Adds a Double to the StringDisplay
     * @since LLayout 1
     */
    fun push(d : Double) : StringDisplay = push(d.toString())

    /**
     * Adds a Float to the StringDisplay
     * @since LLayout 1
     */
    fun push(f : Float) : StringDisplay = push(f.toString())

    /**
     * Adds a Long to the StringDisplay
     * @since LLayout 1
     */
    fun push(l : Long) : StringDisplay = push(l.toString())

    /**
     * Adds a Short to the StringDisplay
     * @since LLayout 1
     */
    fun push(s : Short) : StringDisplay = push(s.toString())

    /**
     * Adds a Byte to the StringDisplay
     * @since LLayout 1
     */
    fun push(b : Byte) : StringDisplay = push(b.toString())

    /**
     * Adds a Boolean to the StringDisplay
     * @since LLayout 1
     */
    fun push(b : Boolean) : StringDisplay = push(b.toString())

    /**
     * Replaces the text with an empty String.
     * @since LLayout 1
     */
    fun clear(){
        text = ""
    }

    /**
     * Creates a copy of this StringDisplay.
     * @since LLayout 1
     */
    fun copy() : StringDisplay = StringDisplay(text, font, color)

    /**
     * Checks if this StringDisplay and the other one have the same font and color.
     * @since LLayout 1
     */
    fun parametersMatch(s : StringDisplay) : Boolean = font == s.font && color == s.color

    operator fun plus(other : StringDisplay) : String = this.text + other.text
    operator fun plus(other : String) : String = this.text + other

    operator fun contains(other : String) : Boolean = text.contains(other)

    override fun toString(): String = text

    override fun iterator(): Iterator<Char> = StringDisplayIterator(text)

}