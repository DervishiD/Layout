package layout.utilities

import layout.DEFAULT_COLOR
import layout.DEFAULT_FONT
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * Produces the total text of a list of StringDisplays
 */
fun Collection<StringDisplay>.collapse() : String{
    var result = ""
    for(s : StringDisplay in this){
        result += s.text
    }
    return result
}

/**
 * Produces a list of the represented lines
 */
fun Collection<StringDisplay>.toLinesList() : MutableList<List<StringDisplay>>{
    val result : MutableList<List<StringDisplay>> = mutableListOf()
    var currentLine : MutableList<StringDisplay> = mutableListOf()
    for(s : StringDisplay in this){
        if(s.contains("\n")){
            val splitted : List<StringDisplay> = s.split("\n")
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
 * Computes the height of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.lineHeight(g : Graphics) : Int{
    var maxAscent = 0
    var maxDescent = 0
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
    var result = 0
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
    var maxDescent = 0
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
fun String.toStringDisplay(font : Font, color : Color) : StringDisplay =
        StringDisplay(this, font, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(color : Color, font : Font) : StringDisplay =
        StringDisplay(this, font, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(font : Font) : StringDisplay = StringDisplay(this, font)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(color : Color) : StringDisplay = StringDisplay(this, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay() : StringDisplay = StringDisplay(this)

fun Collection<String>.toStringDisplays(font : Font, color : Color) : MutableList<StringDisplay>{
    val result : MutableList<StringDisplay> = mutableListOf()
    for(s in this){
        result.add(s.toStringDisplay(font, color))
    }
    return result
}

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun Collection<String>.toStringDisplays(color : Color, font : Font) : MutableList<StringDisplay> = toStringDisplays(font, color)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun Collection<String>.toStringDisplays(color : Color) : MutableList<StringDisplay> = toStringDisplays(DEFAULT_FONT, color)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun Collection<String>.toStringDisplays(font : Font) : MutableList<StringDisplay> = toStringDisplays(font, DEFAULT_COLOR)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun Collection<String>.toStringDisplays() : MutableList<StringDisplay> = toStringDisplays(DEFAULT_FONT, DEFAULT_COLOR)
