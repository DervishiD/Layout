package llayout.utilities

import llayout.DEFAULT_COLOR
import llayout.DEFAULT_FONT
import java.awt.Color
import java.awt.Font

/**
 * Class that represents a displayed String
 */
class StringDisplay : Iterable<Char> {

    companion object{
        private class StringDisplayIterator(private val s : String) : Iterator<Char>{

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
     */
    var text : String

    /**
     * Its colour
     */
    var color : Color

    /**
     * Its font
     */
    var font : Font

    constructor(text : String, font : Font, color : Color){
        this.text = text
        this.font = font
        this.color = color
    }
    constructor(text : String, color : Color) : this(text, DEFAULT_FONT, color)
    constructor(text : String, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : String) : this(text, DEFAULT_FONT, DEFAULT_COLOR)
    constructor() : this("", DEFAULT_FONT, DEFAULT_COLOR)
    constructor(text : Int, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Int, color : Color) : this(text, DEFAULT_FONT, color)
    constructor(text : Int, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Int) : this(text, DEFAULT_FONT, DEFAULT_COLOR)
    constructor(text : Double, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : Double, color : Color) : this(text, DEFAULT_FONT, color)
    constructor(text : Double, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : Double) : this(text, DEFAULT_FONT, DEFAULT_COLOR)
    constructor(text : StringBuilder, font : Font, color : Color) : this(text.toString(), font, color)
    constructor(text : StringBuilder, color : Color) : this(text, DEFAULT_FONT, color)
    constructor(text : StringBuilder, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(text : StringBuilder) : this(text, DEFAULT_FONT, DEFAULT_COLOR)
    constructor(s : StringDisplay){
        text = s.text
        color = s.color
        font = s.font
    }

    /**
     * String split function but for StringDisplay
     */
    infix fun split(separator : String) : MutableList<StringDisplay>{
        val result : MutableList<StringDisplay> = mutableListOf()
        val splittedText : Collection<String> = text.split(separator)
        for(s : String in splittedText){
            result.add(StringDisplay(s, font, color))
        }
        return result
    }

    fun toLines() : MutableList<StringDisplay> = split("\n")

    /**
     * Adds a string to the StringDisplay
     */
    infix fun push(s : String){
        text += s
    }

    /**
     * Adds a char to the StringDisplay
     */
    infix fun push(c : Char){
        text += c
    }

    /**
     * Adds an Int to the StringDisplay
     */
    infix fun push(i : Int){
        text += i.toString()
    }

    /**
     * Adds a Double to the StringDisplay
     */
    infix fun push(d : Double){
        text += d.toString()
    }

    /**
     * Replaces the text with an empty String
     */
    fun clear(){
        text = ""
    }

    /**
     * Creates a copy of this StringDisplay
     */
    fun copy() : StringDisplay = StringDisplay(text, font, color)

    operator fun plus(other : StringDisplay) : String = this.text + other.text
    operator fun plus(other : String) : String = this.text + other

    operator fun contains(other : String) : Boolean = text.contains(other)

    override fun toString(): String = text

    override fun iterator(): Iterator<Char> = StringDisplayIterator(text)

}