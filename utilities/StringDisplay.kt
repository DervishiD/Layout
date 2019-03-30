package utilities

import display.DEFAULT_COLOR
import display.DEFAULT_FONT
import java.awt.Color
import java.awt.Font

/**
 * Class that represents a displayed String
 */
class StringDisplay {

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
    constructor(text : String, color : Color, font : Font) : this(text, font, color)
    constructor(color : Color, font : Font, text : String) : this(text, font, color)
    constructor(color : Color, text : String, font : Font) : this(text, font, color)
    constructor(font : Font, text : String, color : Color) : this(text, font, color)
    constructor(font : Font, color : Color, text : String) : this(text, font, color)
    constructor(text : String, color : Color) : this(text, DEFAULT_FONT, color)
    constructor(color : Color, text : String) : this(text, DEFAULT_FONT, color)
    constructor(text : String, font : Font) : this(text, font, DEFAULT_COLOR)
    constructor(font : Font, text : String) : this(text, font, DEFAULT_COLOR)
    constructor(text : String) : this(text, DEFAULT_FONT, DEFAULT_COLOR)
    constructor(font : Font, color : Color) : this("", font, color)
    constructor(color : Color, font : Font) : this("", font, color)
    constructor(font : Font) : this("", font, DEFAULT_COLOR)
    constructor(color : Color) : this("", DEFAULT_FONT, color)
    constructor() : this("", DEFAULT_FONT, DEFAULT_COLOR)

    /**
     * String split function but for StringDisplay
     */
    infix fun split(separator : String) : MutableList<StringDisplay>{
        val result : MutableList<StringDisplay> = mutableListOf()
        val splittedText : Collection<String> = text.split(separator)
        for(s : String in splittedText){
            result.add(StringDisplay(s, color, font))
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
    fun copy() : StringDisplay = StringDisplay(text, color, font)

    operator fun plus(other : StringDisplay) : String = this.text + other.text
    operator fun plus(other : String) : String = this.text + other

    operator fun contains(other : String) : Boolean = text.contains(other)

    override fun toString(): String = text

}