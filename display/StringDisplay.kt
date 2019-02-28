package display

import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * Produces the total text of a list of StringDisplays
 */
fun List<StringDisplay>.collapse() : String{
    var result : String = ""
    for(s : StringDisplay in this){
        result += s.text
    }
    return result
}

/**
 * Produces a list of the represented lines
 */
fun List<StringDisplay>.toLinesList() : ArrayList<ArrayList<StringDisplay>>{
    val result : ArrayList<ArrayList<StringDisplay>> = ArrayList()
    var currentLine : ArrayList<StringDisplay> = ArrayList()
    for(s : StringDisplay in this){
        if(s.contains("\n")){
            val splitted : ArrayList<StringDisplay> = s.split("\n")
            currentLine.add(splitted[0])
            result.add(currentLine)
            for(i : Int in 1 until splitted.size - 1){
                result.add(arrayListOf(splitted[i]))
            }
            currentLine = arrayListOf(splitted.last())
        }else{
            currentLine.add(s)
        }
    }
    result.add(currentLine)
    return result
}

/**
 * Returns a copy of this ArrayList
 */
fun MutableList<StringDisplay>.copy() : ArrayList<StringDisplay>{
    val result : ArrayList<StringDisplay> = ArrayList()
    result.addAll(this)
    return result
}

/**
 * Computes the height of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.lineHeight(g : Graphics) : Int{
    var maxAscent : Int = 0
    var maxDescent : Int = 0
    var fm : FontMetrics
    for(s : StringDisplay in this){
        fm = g.getFontMetrics(s.font)
        if(fm.maxAscent > maxAscent){
            maxAscent = fm.maxAscent
        }
        if(fm.maxDescent > maxDescent){
            maxDescent = fm.maxDescent
        }
    }
    return maxAscent + maxDescent
}

/**
 * Computes the length of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.lineLength(g : Graphics) : Int{
    var result : Int = 0
    for(s : StringDisplay in this){
        result +=  g.getFontMetrics(s.font).stringWidth(s.text)
    }
    return result
}

/**
 * Computes the ascent of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.ascent(g : Graphics) : Int{
    var maxAscent : Int = 0
    var fm : FontMetrics
    for(s : StringDisplay in this){
        fm = g.getFontMetrics(s.font)
        if(fm.maxAscent > maxAscent){
            maxAscent = fm.maxAscent
        }
    }
    return maxAscent
}

/**
 * Computes the descent of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.descent(g : Graphics) : Int{
    var maxDescent : Int = 0
    var fm : FontMetrics
    for(s : StringDisplay in this){
        fm = g.getFontMetrics(s.font)
        if(fm.maxDescent > maxDescent){
            maxDescent = fm.maxDescent
        }
    }
    return maxDescent
}

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(font : Font, color : Color) : StringDisplay = StringDisplay(this, font, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(color : Color, font : Font) : StringDisplay = StringDisplay(this, font, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(font : Font) : StringDisplay = StringDisplay(this, font, DEFAULT_COLOR)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(color : Color) : StringDisplay = StringDisplay(this, DEFAULT_FONT, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay() : StringDisplay = StringDisplay(this, DEFAULT_FONT, DEFAULT_COLOR)

fun List<String>.toStringDisplays(font : Font, color : Color) : MutableList<StringDisplay>{
    val result : MutableList<StringDisplay> = mutableListOf()
    for(s in this){
        result.add(s.toStringDisplay(font, color))
    }
    return result
}

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays(color : Color, font : Font) : MutableList<StringDisplay> = toStringDisplays(font, color)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays(color : Color) : MutableList<StringDisplay> = toStringDisplays(DEFAULT_FONT, color)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays(font : Font) : MutableList<StringDisplay> = toStringDisplays(font, DEFAULT_COLOR)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays() : MutableList<StringDisplay> = toStringDisplays(DEFAULT_FONT, DEFAULT_COLOR)

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
    infix fun split(separator : String) : ArrayList<StringDisplay>{
        val result : ArrayList<StringDisplay> = ArrayList()
        val splittedText : List<String> = text.split(separator)
        for(s : String in splittedText){
            result.add(StringDisplay(s, color, font))
        }
        return result
    }

    fun toLines() : ArrayList<StringDisplay> = split("\n")

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

}