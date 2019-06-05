package llayout1.utilities

import llayout1.DEFAULT_COLOR
import llayout1.DEFAULT_SMALL_FONT
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

fun Collection<String>.toStringDisplayLines() : MutableList<MutableList<StringDisplay>> = toStringDisplays().toLinesList()

/**
 * Produces a list of the represented lines
 */
fun Collection<StringDisplay>.toLinesList() : MutableList<MutableList<StringDisplay>>{
    val result : MutableList<MutableList<StringDisplay>> = mutableListOf()
    var currentLine : MutableList<StringDisplay> = mutableListOf()
    for(s : StringDisplay in this){
        if(s.contains("\n")){
            val splitted : List<StringDisplay> = s.split("\n")
            currentLine.add(splitted[0])
            result.add(currentLine)
            for(i : Int in 1 until splitted.size - 1){
                result.add(mutableListOf(splitted[i]))
            }
            currentLine = mutableListOf(splitted.last())
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
fun Collection<String>.toStringDisplays(color : Color) : MutableList<StringDisplay> = toStringDisplays(DEFAULT_SMALL_FONT, color)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun Collection<String>.toStringDisplays(font : Font) : MutableList<StringDisplay> = toStringDisplays(font, DEFAULT_COLOR)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun Collection<String>.toStringDisplays() : MutableList<StringDisplay> = toStringDisplays(DEFAULT_SMALL_FONT, DEFAULT_COLOR)

fun Regex.matches(c : Char) : Boolean = matches(c.toString())

fun Regex.matches(s : StringDisplay) : Boolean = matches(s.text)

fun Regex.matches(t : Text) : Boolean = matches(t.asString())

fun FontMetrics.stringWidth(s : CharSequence) : Int = stringWidth(s.toString())

fun Collection<StringDisplay>.toLines(maxLength : Int, g : Graphics, trimSpaces : Boolean = true) : MutableList<MutableList<StringDisplay>>{
    if(maxLength <= 0)
        throw IllegalArgumentException("maxLength $maxLength in extension function Collection<StringDisplay>.toLines is invalid.")

    val result : MutableList<MutableList<StringDisplay>> = mutableListOf()

    val lines : MutableList<MutableList<StringDisplay>> = toLinesList()

    val temporaryLine : MutableList<StringDisplay> = mutableListOf()

    val temporaryStringDisplay : StringDisplay = StringDisplay()

    var fm : FontMetrics

    var words : MutableList<String>

    var word : String

    var chars : MutableList<String>

    fun lineFits(line : List<StringDisplay>) : Boolean = line.lineLength(g) <= maxLength

    fun stringDisplayAndTemporaryLineFit(sd : StringDisplay) : Boolean{
        return temporaryLine.lineLength(g) + g.getFontMetrics(sd.font).stringWidth(sd.text) <= maxLength
    }

    fun splitStringDisplayIntoWords(sd : StringDisplay) : MutableList<String> = sd.text.split(" ").toMutableList()

    fun wordAndTemporaryLineFit(word : String, fm : FontMetrics) : Boolean{
        return temporaryLine.lineLength(g) + fm.stringWidth(word) <= maxLength
    }

    fun temporaryLineSDAndCharFit(char : String, fm : FontMetrics) : Boolean{
        return temporaryLine.lineLength(g) + fm.stringWidth(temporaryStringDisplay.text) + fm.stringWidth(char) <= maxLength
    }

    fun wordFitsInNewLine(word : String, fm : FontMetrics) : Boolean = fm.stringWidth(word) <= maxLength

    for(line : MutableList<StringDisplay> in lines){ //For each line
        if(lineFits(line)){ //Line fits
            result.add(line)
        }else{ //Line doesn't fit
            for(stringDisplay : StringDisplay in line){ //For each StringDisplay in the line
                if(stringDisplayAndTemporaryLineFit(stringDisplay)){ //StringDisplay fits in new line
                    temporaryLine.add(stringDisplay)
                }else{ //StringDisplay doesn't fit in new line
                    temporaryStringDisplay.font = stringDisplay.font
                    temporaryStringDisplay.color = stringDisplay.color
                    words = splitStringDisplayIntoWords(stringDisplay)
                    fm = g.getFontMetrics(stringDisplay.font)

                    //A space is lost by split, but not for the first word
                    for(i : Int in 1 until words.size){
                        words[i] = " ${words[i]}"
                    }
                    for(i : Int in 0 until words.size){
                        word = words[i]
                        when {
                            temporaryLineSDAndCharFit(word, fm) -> temporaryStringDisplay.push(word)
                            wordFitsInNewLine(word, fm) -> {
                                temporaryLine.add(temporaryStringDisplay.copy())
                                result.add(temporaryLine.toMutableList())
                                temporaryStringDisplay.clear()
                                temporaryLine.clear()
                                if(i == 0 || !trimSpaces){
                                    temporaryStringDisplay.push(word)
                                }else{
                                    temporaryStringDisplay.push(word.substring(1, word.length))
                                }
                            }
                            else -> {
                                chars = word.split("").toMutableList()
                                while(chars.isNotEmpty()){
                                    if(temporaryLineSDAndCharFit(chars[0], fm)){
                                        temporaryStringDisplay.push(chars[0])
                                    }else{
                                        temporaryLine.add(temporaryStringDisplay.copy())
                                        result.add(temporaryLine.toMutableList())
                                        temporaryLine.clear()
                                        temporaryStringDisplay.text = chars[0]
                                    }
                                    chars.removeAt(0)
                                }
                            }
                        }
                    }
                }
                if(temporaryStringDisplay.text != ""){
                    temporaryLine.add(temporaryStringDisplay.copy())
                    temporaryStringDisplay.clear()
                }
            }
            if(temporaryLine.isNotEmpty()){
                result.add(temporaryLine.toMutableList())
                temporaryLine.clear()
            }
        }
    }

    return result
}

operator fun StringBuilder.plus(s : StringBuilder) : StringBuilder = append(s.toString())

operator fun StringBuilder.plus(s : String) : StringBuilder = append(s)

operator fun StringBuilder.plus(c : Char) : StringBuilder = append(c.toString())

fun StringBuilder.set(s : String) : StringBuilder = clear().append(s)
