package display.selectors

import display.*
import geometry.Point
import main.GraphicAction
import utilities.IndexedMapping
import utilities.copy
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.FontMetrics
import java.awt.Graphics
import kotlin.IllegalArgumentException
import kotlin.reflect.KClass

/**
 * An arrow Selector that displays text
 */
class TextArrowSelector : AbstractArrowSelector {

    companion object {
        private const val DEFAULT_LINE_THICKNESS : Int = 5
        private val DEFAULT_LINE_COLOR : Color = BLACK
        private val DEFAULT_BACKGROUND : GraphicAction = { g: Graphics, w: Int, h: Int -> run{
            g.color = DEFAULT_LINE_COLOR
            g.fillRect(0, 0, DEFAULT_LINE_THICKNESS, h)
            g.fillRect(0, 0, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(0, h - DEFAULT_LINE_THICKNESS, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(w - DEFAULT_LINE_THICKNESS, 0, w, h)
        }}
        private const val SIDE_DELTA : Int = 7
    }

    override var authorizedKeys: MutableSet<KClass<out Any>> = mutableSetOf(String::class, StringDisplay::class, List::class)

    /**
     * The list of the list of lines that constitute the final displaying information.
     * Those are the displayed lines in the component, its width and its height
     */
    private var linesList : MutableList<Triple<List<List<StringDisplay>>, Int, Int>> = mutableListOf()

    /**
     * The background
     */
    private var backgroundDrawer : GraphicAction = DEFAULT_BACKGROUND

    /**
     * The maximal allowed line length
     */
    private var maxLineLength : Int? = null

    /**
     * True if each option's display's dimensions have been computed
     */
    private var dimensionsComputed : Boolean = false

    constructor(p : Point, options : List<Pair<Any?, Any?>>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : super(p, options, isHorizontal){
        if(optionsAreInvalid(options)) throw IllegalArgumentException()
        fillLinesList()
        backgroundDrawer = background
    }
    constructor(x : Int, y : Int, options : List<Pair<Any?, Any?>>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Double, y : Int, options : List<Pair<Any?, Any?>>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Int, y : Double, options : List<Pair<Any?, Any?>>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Double, y : Double, options : List<Pair<Any?, Any?>>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(p : Point, keys : List<Any?>, values : List<Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : super(p, keys, values, isHorizontal){
        if(optionsAreInvalid(keys, values)) throw IllegalArgumentException()
        fillLinesList()
        backgroundDrawer = background
    }
    constructor(x : Int, y : Int, keys : List<Any?>, values : List<Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), keys, values, isHorizontal, background)
    constructor(x : Double, y : Int, keys : List<Any?>, values : List<Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), keys, values, isHorizontal, background)
    constructor(x : Int, y : Double, keys : List<Any?>, values : List<Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), keys, values, isHorizontal, background)
    constructor(x : Double, y : Double, keys : List<Any?>, values : List<Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), keys, values, isHorizontal, background)
    constructor(p : Point, vararg options : Pair<Any?, Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : super(p, options.asList(), isHorizontal){
        if(optionsAreInvalid(*options)) throw IllegalArgumentException()
        fillLinesList()
        backgroundDrawer = background
    }
    constructor(x : Int, y : Int, vararg options : Pair<Any?, Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), *options, isHorizontal = isHorizontal, background = background)
    constructor(x : Double, y : Int, vararg options : Pair<Any?, Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), *options, isHorizontal = isHorizontal, background = background)
    constructor(x : Int, y : Double, vararg options : Pair<Any?, Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), *options, isHorizontal = isHorizontal, background = background)
    constructor(x : Double, y : Double, vararg options : Pair<Any?, Any?>, isHorizontal : Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), *options, isHorizontal = isHorizontal, background = background)
    constructor(p : Point, options : Map<Any?, Any?>, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : super(p, options, isHorizontal){
        if(optionsAreInvalid(options)) throw IllegalArgumentException()
        fillLinesList()
        backgroundDrawer = background
    }
    constructor(x : Int, y : Int, options : Map<Any?, Any?>, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Double, y : Int, options : Map<Any?, Any?>, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Int, y : Double, options : Map<Any?, Any?>, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Double, y : Double, options : Map<Any?, Any?>, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(p : Point, options : IndexedMapping, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : super(p, options, isHorizontal){
        if(optionsAreInvalid(options)) throw IllegalArgumentException()
        fillLinesList()
        backgroundDrawer = background
    }
    constructor(x : Int, y : Int, options : IndexedMapping, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Double, y : Int, options : IndexedMapping, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Int, y : Double, options : IndexedMapping, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)
    constructor(x : Double, y : Double, options : IndexedMapping, isHorizontal: Boolean = true, background : GraphicAction = DEFAULT_BACKGROUND) : this(Point(x, y), options, isHorizontal, background)

    /**
     * Fills the list of lines with the values corresponding to the options the constructor received
     */
    private fun fillLinesList(){
        for(key : Any? in keys){
            when{
                key == null -> linesList.add(Triple(listOf(listOf(StringDisplay("null"))), 0, 0))
                isString(key) -> {
                    val lines : List<StringDisplay> = (StringDisplay(key as String)).toLines()
                    val result : MutableList<List<StringDisplay>> = mutableListOf()
                    for(sd : StringDisplay in lines){
                        result.add(listOf(sd))
                    }
                    linesList.add(Triple(result, 0, 0))
                }
                isStringDisplay(key) -> {
                    val lines : List<StringDisplay> = (key as StringDisplay).toLines()
                    val result : MutableList<List<StringDisplay>> = mutableListOf()
                    for(sd : StringDisplay in lines){
                        result.add(listOf(sd))
                    }
                    linesList.add(Triple(result, 0, 0))
                }
                isStringDisplayList(key) -> @Suppress("UNCHECKED_CAST") linesList.add(Triple((key as List<StringDisplay>).toLinesList(), 0, 0))
            }
        }
    }

    /**
     * Changes the background of this Displayer
     */
    infix fun setBackground(background : GraphicAction){
        backgroundDrawer = background
    }

    /**
     * Sets the maximal line length for this Component
     */
    infix fun setMaxLineLength(length : Int){
        maxLineLength = length
        initphase = true
        dimensionsComputed = false
    }

    /**
     * Sets the maximal line length for this Component.
     */
    infix fun setMaxLineLength(length : Double) = setMaxLineLength(length.toInt())

    override fun next(){
        super.next()
        reloadDimensions()
    }

    override fun previous(){
        super.previous()
        reloadDimensions()
    }

    /**
     * Changes the dimensions of this Selector
     */
    private fun reloadDimensions(){
        w = linesList[currentOption].second
        h = linesList[currentOption].third
    }

    /**
     * Forces the max line length constraint
     */
    private infix fun forceMaxLineLength(g : Graphics){
        if(maxLineLength != null){
            val finalResult : ArrayList<Triple<List<List<StringDisplay>>, Int, Int>> = ArrayList()
            val currentLine : MutableList<StringDisplay> = mutableListOf()
            var currentDisplay : StringDisplay
            var fm : FontMetrics
            var currentLineLength : Int = 2 * SIDE_DELTA
            var currentWord : String
            var currentWordAndSpace : String
            var charLength : Int
            var wordLength : Int
            var wordAndSpaceLength : Int
            var words : List<String>
            var chars : List<String>

            for(triple : Triple<List<List<StringDisplay>>, Int, Int> in linesList){
                val result : ArrayList<ArrayList<StringDisplay>> = ArrayList()
                for(line : List<StringDisplay> in triple.first){
                    for(s : StringDisplay in line){
                        fm = g.getFontMetrics(s.font)
                        words = s.text.split(" ")
                        currentDisplay = StringDisplay("", s.font, s.color)
                        for(i : Int in 0 until words.size){
                            currentWord = words[i]
                            currentWordAndSpace = " $currentWord"
                            wordLength = fm.stringWidth(currentWord)
                            wordAndSpaceLength = fm.stringWidth(currentWordAndSpace)
                            if(currentLineLength + wordAndSpaceLength <= maxLineLength!!){
                                if(i == 0){
                                    currentDisplay.push(currentWord)
                                    currentLineLength += wordLength
                                }else{
                                    currentDisplay.push(currentWordAndSpace)
                                    currentLineLength += wordAndSpaceLength
                                }
                            }else{
                                if(i == 0 && currentLineLength + wordLength <= maxLineLength!!){
                                    currentDisplay.push(currentWord)
                                    currentLine.add(currentDisplay.copy())
                                    result.add(currentLine.copy())
                                    currentLine.clear()
                                    currentDisplay.clear()
                                    currentLineLength = 2 * SIDE_DELTA
                                }else if(wordLength <= maxLineLength!!){
                                    currentLine.add(currentDisplay.copy())
                                    result.add(currentLine.copy())
                                    currentLine.clear()
                                    currentDisplay.clear()
                                    currentDisplay.push(currentWord)
                                    currentLineLength = 2 * SIDE_DELTA + wordLength
                                }else{
                                    chars = currentWord.split("")
                                    if(i != 0){
                                        val spaceLength = fm.stringWidth(" ")
                                        if(currentLineLength + spaceLength <= maxLineLength!!){
                                            currentDisplay.push(" ")
                                            currentLineLength += spaceLength
                                        }else{
                                            currentLine.add(currentDisplay.copy())
                                            result.add(currentLine.copy())
                                            currentLine.clear()
                                            currentDisplay.clear()
                                            currentLineLength = 2 * SIDE_DELTA
                                        }
                                    }
                                    for(c : String in chars){
                                        charLength = fm.stringWidth(c)
                                        if(currentLineLength + charLength <= maxLineLength!!){
                                            currentDisplay.push(c)
                                            currentLineLength += charLength
                                        }else{
                                            currentLine.add(currentDisplay.copy())
                                            result.add(currentLine.copy())
                                            currentLine.clear()
                                            currentDisplay.clear()
                                            currentDisplay.push(c)
                                            currentLineLength = 2 * SIDE_DELTA + charLength
                                        }
                                    }
                                }
                            }
                        }
                        currentLine.add(currentDisplay.copy())
                    }
                    result.add(currentLine.copy())
                    currentLine.clear()
                    currentLineLength = 2 * SIDE_DELTA
                }
                finalResult.add(Triple(result, 0, 0))
            }
            linesList = finalResult
        }
    }

    /**
     * Loads the dimensions associated with each value
     */
    private fun computeDimensions(g : Graphics){
        val result : ArrayList<Triple<List<List<StringDisplay>>, Int, Int>> = ArrayList()
        for(triple : Triple<List<List<StringDisplay>>, Int, Int> in linesList){
            var width : Int = 0
            var height : Int = 2 * SIDE_DELTA
            for(line : List<StringDisplay> in triple.first){
                val lineLength : Int = line.lineLength(g)
                if(lineLength > width){
                    width = lineLength
                }
                height += line.lineHeight(g)
            }
            width += 2 * SIDE_DELTA
            if(preferredWidth != null && width < preferredWidth!!) width = preferredWidth!!
            if(preferredHeight != null && height < preferredHeight!!) height = preferredHeight!!
            result.add(Triple(triple.first, width, height))
        }
        linesList = result
        reloadDimensions()
    }

    /**
     * Returns true if the given options fit the map, i.e. if the keys are String, StringDisplays
     * or List of StringDisplays
     */
    private fun optionsAreInvalid(vararg options : Pair<Any?, Any?>) : Boolean{
        for(option in options){
            if(!(isString(option.first) || isStringDisplay(option.first) || isStringDisplayList(option.first))){
                return true
            }
        }
        return false
    }

    /**
     * Returns true if the given options fit the map, i.e. if the keys are String, StringDisplays
     * or List of StringDisplays
     */
    private fun optionsAreInvalid(options : List<Pair<Any?, Any?>>) : Boolean{
        for(option in options){
            if(!(isString(option.first) || isStringDisplay(option.first) || isStringDisplayList(option.first))){
                return true
            }
        }
        return false
    }

    /**
     * Returns true if the given options fit the map, i.e. if the keys are String, StringDisplays
     * or List of StringDisplays
     */
    private fun optionsAreInvalid(keys : List<Any?>, values: List<Any?>) : Boolean{
        if(keys.size == values.size){
            for(i : Int in 0 until keys.size){
                if(!(isString(keys[i]) || isStringDisplay(keys[i]) || isStringDisplayList(keys[i]))){
                    return true
                }
            }
        }
        return false
    }

    /**
     * Returns true if the given options fit the map, i.e. if the keys are String, StringDisplays
     * or List of StringDisplays
     */
    private fun optionsAreInvalid(options : Map<Any?, Any?>) : Boolean{
        for(option in options){
            if(!(isString(option.key) || isStringDisplay(option.key) || isStringDisplayList(option.key))){
                return true
            }
        }
        return false
    }

    /**
     * Returns true if the given options fit the map, i.e. if the keys are String, StringDisplays
     * or List of StringDisplays
     */
    private fun optionsAreInvalid(options : IndexedMapping) : Boolean{
        for(option in options){
            if(!(isString(option.first) || isStringDisplay(option.first) || isStringDisplayList(option.first))){
                return true
            }
        }
        return false
    }

    /**
     * Returns true f the given parameter is a String
     */
    private fun isString(key : Any?) : Boolean = key is String

    /**
     * Returns true if the given parameter is a StringDisplay
     */
    private fun isStringDisplay(key : Any?) : Boolean = key is StringDisplay

    /**
     * Returns true if the given parameter is a List of StringDisplays
     */
    private fun isStringDisplayList(key : Any?) : Boolean = key is List<*> && key.filterIsInstance<StringDisplay>().size == key.size

    override fun loadParameters(g: Graphics) {
        if(!dimensionsComputed){
            forceMaxLineLength(g)
            computeDimensions(g)
            dimensionsComputed = true
        }
        align()
        reloadDimensions()
        setArrowsPosition()
    }

    override fun drawDisplayer(g: Graphics) {
        drawBackground(g)
        drawText(g)
            setArrowsPosition()
    }

    /**
     * Draws the background of the displayer
     */
    private fun drawBackground(g : Graphics) = backgroundDrawer.invoke(g, w, h)

    /**
     * Draws the text of the displayer
     */
    private fun drawText(g : Graphics){
        var currentX : Int = SIDE_DELTA
        var currentY : Int = SIDE_DELTA

        for(line : List<StringDisplay> in linesList[currentOption].first){
            currentY += line.ascent(g)
            for(s : StringDisplay in line){
                g.font = s.font
                g.color = s.color
                g.drawString(s.text, currentX, currentY)
                currentX += g.getFontMetrics(s.font).stringWidth(s.text)
            }
            currentX = SIDE_DELTA
            currentY += line.descent(g)
        }
    }

}