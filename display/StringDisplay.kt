package display

import java.awt.Color
import java.awt.Font

/**
 * Produces the total text of a list of StringDisplays
 */
public fun ArrayList<StringDisplay>.collapse() : String{
    var result : String = ""
    for(s : StringDisplay in this){
        result += s.text
    }
    return result
}

/**
 * Produces a list of the represented lines
 */
public fun ArrayList<StringDisplay>.toLinesList() : ArrayList<ArrayList<StringDisplay>>{
    var result : ArrayList<ArrayList<StringDisplay>> = ArrayList<ArrayList<StringDisplay>>()
    var currentLine : ArrayList<StringDisplay> = ArrayList<StringDisplay>()
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
public fun ArrayList<StringDisplay>.copy() : ArrayList<StringDisplay>{
    var result : ArrayList<StringDisplay> = ArrayList<StringDisplay>()
    result.addAll(this)
    return result
}

/**
 * Class that represents a displayed String
 */
class StringDisplay {

    /**
     * The displayed text
     */
    public var text : String

    /**
     * Its colour
     */
    public var color : Color

    /**
     * Its font
     */
    public var font : Font

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

    /**
     * String split function but for StringDisplay
     */
    public fun split(separator : String) : ArrayList<StringDisplay>{
        val result : ArrayList<StringDisplay> = ArrayList<StringDisplay>()
        val splittedText : List<String> = text.split(separator)
        for(s : String in splittedText){
            result.add(StringDisplay(s, color, font))
        }
        return result
    }

    /**
     * Adds a string to the StringDisplay
     */
    public infix fun push(s : String){
        text += s
    }

    /**
     * Adds a char to the StringDisplay
     */
    public infix fun push(c : Char){
        text += c
    }

    /**
     * Adds an Int to the StringDisplay
     */
    public infix fun push(i : Int){
        text += i.toString()
    }

    /**
     * Adds a Double to the StringDisplay
     */
    public infix fun push(d : Double){
        text += d.toString()
    }

    /**
     * Replaces the text with an empty String
     */
    public fun clear(){
        text = ""
    }

    /**
     * Creates a copy of this StringDisplay
     */
    public fun copy() : StringDisplay = StringDisplay(text, color, font)

    public operator fun plus(other : StringDisplay) : String = this.text + other.text
    public operator fun plus(other : String) : String = this.text + other

    public operator fun contains(other : String) : Boolean = text.contains(other)

}