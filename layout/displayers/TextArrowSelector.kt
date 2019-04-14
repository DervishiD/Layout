package layout.displayers

import layout.GraphicAction
import layout.geometry.Point
import layout.utilities.*
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.FontMetrics
import java.awt.Graphics
import java.lang.IllegalArgumentException

/**
 * An arrow Selector that displays text to let the user know what they're selecting.
 * The type parameter T is the type of the selected objects.
 * @see AbstractArrowSelector
 */
class TextArrowSelector<T> : AbstractArrowSelector<T> {

    companion object {

        /**
         * The default thickness of the lines drawn by the default background.
         * @see DEFAULT_BACKGROUND
         */
        private const val DEFAULT_LINE_THICKNESS : Int = 5

        /**
         * The default color of the lines drawn by the default background.
         * @see DEFAULT_BACKGROUND
         */
        private val DEFAULT_LINE_COLOR : Color = BLACK

        /**
         * The GraphicAction that draws the default background of this Selector.
         * That is, a rectangle.
         * @see GraphicAction
         * @see DEFAULT_LINE_THICKNESS
         * @see DEFAULT_LINE_COLOR
         */
        private val DEFAULT_BACKGROUND : GraphicAction = { g: Graphics, w: Int, h: Int -> run{
            g.color = DEFAULT_LINE_COLOR
            g.fillRect(0, 0, DEFAULT_LINE_THICKNESS, h)
            g.fillRect(0, 0, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(0, h - DEFAULT_LINE_THICKNESS, w,
                    DEFAULT_LINE_THICKNESS
            )
            g.fillRect(w - DEFAULT_LINE_THICKNESS, 0, w, h)
        }}

        /**
         * The distance, in pixels, between the text and the bounds of the Displayer.
         */
        private const val SIDE_DELTA : Int = 7

    }

    /**
     * To display text efficiently, this list contains an entry for each option value.
     * Each entry is made of a Triple. The first parameter of the Triple is the list of the
     * lines of the text that must be displayed. The second parameter is the width of the
     * resulting component, and the third is its height.
     * @see toLinesList
     */
    private var linesList : MutableList<Triple<Collection<Collection<StringDisplay>>, Int, Int>> = mutableListOf()

    /**
     * The background of this Displayer, as a GraphicAction.
     * @see GraphicAction
     */
    private var backgroundDrawer : GraphicAction = DEFAULT_BACKGROUND

    /**
     * The maximal allowed length of the Selector, including its arrows.
     * @see forceMaxLineLength
     * @see setMaxLineLength
     */
    private var maxLineLength : Int? = null

    /**
     * True if each option's display's dimensions have been computed.
     * @see computeDimensions
     * @see reloadDimensions
     */
    private var dimensionsComputed : Boolean = false

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Int,
            options : Collection<Pair<Text, T>>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.seconds(), isHorizontal){
        fillLinesList(options.firsts())
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Double,
            options : Collection<Pair<Text, T>>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.seconds(), isHorizontal){
        fillLinesList(options.firsts())
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Int,
            options : Collection<Pair<Text, T>>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.seconds(), isHorizontal){
        fillLinesList(options.firsts())
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Double,
            options : Collection<Pair<Text, T>>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.seconds(), isHorizontal){
        fillLinesList(options.firsts())
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param p The center Point of this TextArrowSelector.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            p : Point,
            options : Collection<Pair<Text, T>>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND)
        : this(p.intx(), p.inty(), options, isHorizontal, background)

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Int,
            keys : Collection<Text>,
            values : Collection<T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, values, isHorizontal){
        if(keys.size != values.size) throw IllegalArgumentException("Asymetric lists of keys (${keys.size}) and values (${values.size})")
        fillLinesList(keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Double,
            keys : Collection<Text>,
            values : Collection<T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, values, isHorizontal){
        if(keys.size != values.size) throw IllegalArgumentException("Asymetric lists of keys (${keys.size}) and values (${values.size})")
        fillLinesList(keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Int,
            keys : Collection<Text>,
            values : Collection<T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, values, isHorizontal){
        if(keys.size != values.size) throw IllegalArgumentException("Asymetric lists of keys (${keys.size}) and values (${values.size})")
        fillLinesList(keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Double,
            keys : Collection<Text>,
            values : Collection<T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, values, isHorizontal){
        if(keys.size != values.size) throw IllegalArgumentException("Asymetric lists of keys (${keys.size}) and values (${values.size})")
        fillLinesList(keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param p The center Point of this TextArrowSelector.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            p : Point,
            keys : Collection<Text>,
            values : Collection<T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND)
        : this(p.intx(), p.inty(), keys, values, isHorizontal, background)

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Int,
            vararg options : Pair<Text, T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : this(x, y, options.asList(), isHorizontal, background)

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Double,
            vararg options : Pair<Text, T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : this(x, y, options.asList(), isHorizontal, background)

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Int,
            vararg options : Pair<Text, T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : this(x, y, options.asList(), isHorizontal, background)

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Double,
            vararg options : Pair<Text, T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : this(x, y, options.asList(), isHorizontal, background)

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param p The center Point of this TextArrowSelector.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            p : Point,
            vararg options : Pair<Text, T>,
            isHorizontal : Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : this(p, options.asList(), isHorizontal, background)

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Int,
            options : Map<Text, T>,
            isHorizontal: Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.values, isHorizontal){
        fillLinesList(options.keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Int,
            y : Double,
            options : Map<Text, T>,
            isHorizontal: Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.values, isHorizontal){
        fillLinesList(options.keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Int,
            options : Map<Text, T>,
            isHorizontal: Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.values, isHorizontal){
        fillLinesList(options.keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param x The x coordinate of this TextArrowSelector's center.
     * @param y The y coordinate of this TextArrowSelector's center.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            x : Double,
            y : Double,
            options : Map<Text, T>,
            isHorizontal: Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : super(x, y, options.values, isHorizontal){
        fillLinesList(options.keys)
        backgroundDrawer = background
    }

    /**
     * Constructs a TextArrowSelector with the given parameters.
     * @param p The center Point of this TextArrowSelector.
     * @param options The options of this TextArrowSelector.
     * @param isHorizontal True if this TextArrowSelector has horizontal arrows.
     * @param background The background of this TextArrowSelector.
     * @see Point
     * @see GraphicAction
     * @see options
     */
    constructor(
            p : Point,
            options : Map<Text, T>,
            isHorizontal: Boolean = true,
            background : GraphicAction = DEFAULT_BACKGROUND) : this(p.intx(), p.inty(), options, isHorizontal, background)

    /**
     * Fills the list of lines according to the entered options.
     * @param text The text related to the options.
     * @see linesList
     */
    private infix fun fillLinesList(text : Collection<Text>){
        for(t : Text in text){
            linesList.add(Triple(t.asLines(), 0, 0))
        }
    }

    /**
     * Changes the background of this Displayer
     * @param background The new background to be drawn.
     * @see GraphicAction
     */
    infix fun setBackground(background : GraphicAction) : TextArrowSelector<T> {
        backgroundDrawer = background
        return this
    }

    /**
     * Sets the maximal length for this Component
     * @param length The maximal length of this component.
     * @see maxLineLength
     * @see forceMaxLineLength
     */
    infix fun setMaxLineLength(length : Int) : TextArrowSelector<T> {
        maxLineLength = length
        initphase = true
        dimensionsComputed = false
        return this
    }

    override fun next(){
        super.next()
        reloadDimensions()
    }

    override fun previous(){
        super.previous()
        reloadDimensions()
    }

    /**
     * Changes the dimensions of this Selector to fit the new selected option.
     * @see linesList
     */
    private fun reloadDimensions(){
        w.value = linesList[currentOption].second
        h.value = linesList[currentOption].third
    }

    /**
     * Forces the max line length constraint, i.e. The displayer must fit in the given width.
     * @param g The Graphics context of the Selector.
     * @see maxLineLength
     */
    private infix fun forceMaxLineLength(g : Graphics){
        if(maxLineLength != null){
            val finalResult : MutableList<Triple<Collection<Collection<StringDisplay>>, Int, Int>> = mutableListOf()
            val currentLine : MutableList<StringDisplay> = mutableListOf()
            var currentDisplay : StringDisplay
            var fm : FontMetrics
            var currentLineLength : Int = 2 * SIDE_DELTA
            var currentWord : String
            var currentWordAndSpace : String
            var charLength : Int
            var wordLength : Int
            var wordAndSpaceLength : Int
            var words : Collection<String>
            var chars : Collection<String>

            for(triple : Triple<Collection<Collection<StringDisplay>>, Int, Int> in linesList){
                val result : MutableCollection<MutableCollection<StringDisplay>> = mutableListOf()
                for(line : Collection<StringDisplay> in triple.first){
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
                                    result.add(currentLine.toMutableCollection())
                                    currentLine.clear()
                                    currentDisplay.clear()
                                    currentLineLength = 2 * SIDE_DELTA
                                }else if(wordLength <= maxLineLength!!){
                                    currentLine.add(currentDisplay.copy())
                                    result.add(currentLine.toMutableCollection())
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
                                            result.add(currentLine.toMutableCollection())
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
                                            result.add(currentLine.toMutableCollection())
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
                    result.add(currentLine.toMutableCollection())
                    currentLine.clear()
                    currentLineLength = 2 * SIDE_DELTA
                }
                finalResult.add(Triple(result, 0, 0))
            }
            linesList = finalResult
        }
    }

    /**
     * Loads the dimensions associated with the text displayed with each option.
     * @param g The Graphics context of this Selector.
     * @see linesList
     */
    private fun computeDimensions(g : Graphics){
        val result : MutableList<Triple<Collection<Collection<StringDisplay>>, Int, Int>> = mutableListOf()
        for(triple : Triple<Collection<Collection<StringDisplay>>, Int, Int> in linesList){
            var width = 0
            var height : Int = 2 * SIDE_DELTA
            for(line : Collection<StringDisplay> in triple.first){
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
     * Draws the background of the displayer.
     * @see backgroundDrawer
     * @see GraphicAction
     */
    private fun drawBackground(g : Graphics) = backgroundDrawer.invoke(g, width(), height())

    /**
     * Draws the text of the displayer.
     * @see linesList
     */
    private fun drawText(g : Graphics){
        var currentX : Int = SIDE_DELTA
        var currentY : Int = SIDE_DELTA

        for(line : Collection<StringDisplay> in linesList[currentOption].first){
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