package llayout.displayers

import llayout.GraphicAction
import llayout.geometry.Point
import llayout.utilities.*
import java.awt.Graphics

/**
 * An abstract class representing the Displayers that display text.
 * @see Displayer
 * @see StringDisplay
 * @see Text
 */
abstract class TextDisplayer : Displayer {

    companion object {

        /**
         * The default background of a TextDisplayer object, i.e. nothing.
         * @see GraphicAction
         * @see backgroundDrawer
         */
        @JvmStatic val NO_BACKGROUND : GraphicAction = { _, _, _ ->  }

    }

    /**
     * The displayed Text, as a list of StringDisplays.
     * @see StringDisplay
     */
    private var txt : MutableCollection<StringDisplay> = mutableListOf()

    /**
     * The displayed text, as a collection of lines.
     * @see txt
     * @see toLinesList
     * @see StringDisplay
     */
    private var lines : MutableCollection<MutableList<StringDisplay>> = mutableListOf()

    /**
     * The maximal allowed line length of this TextDisplayer.
     * @see setMaxLineLength
     * @see forceMaxLineLength
     */
    private var maxLineLength : Int? = null

    private var relativeMaxLineLength : Double? = null

    /**
     * The GraphicAction that draws the background of this TextDisplayer.
     * @see GraphicAction
     * @see drawBackground
     */
    protected var backgroundDrawer : GraphicAction

    /**
     * The distance between the top of the text and the top of this TextDisplayer.
     */
    protected abstract var upDelta : Int

    /**
     * The distance between the bottom of the text and the bottom of this TextDisplayer.
     */
    protected abstract var downDelta : Int

    /**
     * The distance between the left side of the text and the left side of this TextDisplayer.
     */
    protected abstract var leftDelta : Int

    /**
     * The distance between the right side of the text and the right side of this TextDisplayer.
     */
    protected abstract var rightDelta : Int

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : Collection<StringDisplay>, background : GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(s : StringDisplay in text){
            txt.add(s)
        }
        lines = txt.toLinesList()
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : Collection<StringDisplay>, background : GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(s : StringDisplay in text){
            txt.add(s)
        }
        lines = txt.toLinesList()
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : Collection<StringDisplay>, background : GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(s : StringDisplay in text){
            txt.add(s)
        }
        lines = txt.toLinesList()
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : Collection<StringDisplay>, background : GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(s : StringDisplay in text){
            txt.add(s)
        }
        lines = txt.toLinesList()
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : Collection<StringDisplay>, background : GraphicAction = NO_BACKGROUND)
            : this(p.intx(), p.inty(), text, background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND)
            : this(x, y, listOf<StringDisplay>(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND)
            : this(x, y, listOf<StringDisplay>(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND)
            : this(x, y, listOf<StringDisplay>(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND)
            : this(x, y, listOf<StringDisplay>(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : StringDisplay, background: GraphicAction = NO_BACKGROUND)
            : this(p, listOf<StringDisplay>(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a String.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : this(x, y, StringDisplay(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a String.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : this(x, y, StringDisplay(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a String.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : this(x, y, StringDisplay(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a String.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : this(x, y, StringDisplay(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a String.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : String, background: GraphicAction = NO_BACKGROUND) : this(p, StringDisplay(text), background)

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Text object.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(line in text.asLines()){
            lines.add(line)
            for(sd in line){
                txt.add(sd)
            }
        }
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Text object.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(line in text.asLines()){
            lines.add(line)
            for(sd in line){
                txt.add(sd)
            }
        }
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Text object.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(line in text.asLines()){
            lines.add(line)
            for(sd in line){
                txt.add(sd)
            }
        }
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Text object.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y){
        if(text.isEmpty()) throw Exception("A text displayer must display text.")
        for(line in text.asLines()){
            lines.add(line)
            for(sd in line){
                txt.add(sd)
            }
        }
        backgroundDrawer = background
    }

    /**
     * Constructs a TextDisplayer with the given parameters.
     * @param p The center Point of this TextDisplayer.
     * @param text The displayed text, as a Text object.
     * @param background The background of this TextDisplayer, as a GraphicAction.
     * @see Point
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(p : Point, text : Text, background: GraphicAction = NO_BACKGROUND) : this(p.intx(), p.inty(), text, background)

    /**
     * Sets the maximal line length for this TextDisplayer.
     * @see maxLineLength
     * @see forceMaxLineLength
     */
    infix fun setMaxLineLength(length : Int) : TextDisplayer {
        if(length <= 0) throw IllegalArgumentException("length $length in Textdisplayer.setMaxLineLength is invalid.")
        maxLineLength = length
        initialize()
        return this
    }

    /**
     * Sets the maximal line length for this TextDisplayer.
     * @see maxLineLength
     * @see forceMaxLineLength
     */
    infix fun setMaxLineLength(length : Double) : TextDisplayer{
        relativeMaxLineLength = length
        requestUpdate()
        initialize()
        return this
    }

    /**
     * Sets the side deltas to the given value.
     * @param delta The new delta, in pixels.
     * @see upDelta
     * @see downDelta
     * @see leftDelta
     * @see rightDelta
     */
    infix fun setSideDistance(delta : Int) : TextDisplayer {
        if(delta >= 0){
            upDelta = delta
            downDelta = delta
            leftDelta = delta
            rightDelta = delta
            return this
        }else throw IllegalArgumentException("Negative sides delta : $delta")
    }

    /**
     * Sets the horizontal side deltas to the given value.
     * @param delta The new delta, in pixels.
     * @see leftDelta
     * @see rightDelta
     */
    infix fun setHorizontalDistance(delta : Int) : TextDisplayer {
        if(delta >= 0){
            leftDelta = delta
            rightDelta = delta
            return this
        }else throw IllegalArgumentException("Negative horizontal sides delta : $delta")
    }

    /**
     * Sets the vertical side deltas to the given value.
     * @param delta The new delta, in pixels.
     * @see upDelta
     * @see downDelta
     */
    infix fun setVerticalDistance(delta: Int) : TextDisplayer {
        if(delta >= 0){
            upDelta = delta
            downDelta = delta
            return this
        }else throw IllegalArgumentException("Negative vertical sides delta : $delta")
    }

    /**
     * Sets the left side delta to the given value.
     * @param delta The new delta, in pixels.
     * @see leftDelta
     */
    infix fun setLeftDistance(delta : Int) : TextDisplayer {
        if(delta >= 0){
            upDelta = delta
            return this
        }else throw IllegalArgumentException("Negative left side delta : $delta")
    }

    /**
     * Sets the right side delta to the given value.
     * @param delta The new delta, in pixels.
     * @see rightDelta
     */
    infix fun setRightDistance(delta : Int) : TextDisplayer {
        if(delta >= 0){
            rightDelta = delta
            return this
        }else throw IllegalArgumentException("Negative right side delta : $delta")
    }

    /**
     * Sets the top side delta to the given value.
     * @param delta The new delta, in pixels.
     * @see upDelta
     */
    infix fun setTopDistance(delta : Int) : TextDisplayer {
        if(delta >= 0){
            upDelta = delta
            return this
        }else throw IllegalArgumentException("Negative up side delta : $delta")
    }

    /**
     * Sets the bottom side delta to the given value.
     * @param delta The new delta, in pixels.
     * @see downDelta
     */
    infix fun setBottomDistance(delta : Int) : TextDisplayer {
        if(delta >= 0){
            downDelta = delta
            return this
        }else throw IllegalArgumentException("Negative down side delta : $delta")
    }

    /**
     * Returns the displayed text as a String.
     * @return The displayed text, as a String.
     * @see txt
     */
    fun text() : String = txt.collapse()

    /**
     * Sets the displayed text.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @see StringDisplay
     */
    infix fun setDisplayedText(text : Collection<StringDisplay>) : TextDisplayer {
        txt = if(text is MutableCollection<StringDisplay>) text else text.toMutableCollection()
        lines = txt.toLinesList()
        initphase = true
        return this
    }

    /**
     * Sets the displayed text.
     * @param text The displayed text, as a StringDisplay.
     * @see StringDisplay
     */
    infix fun setDisplayedText(text : StringDisplay) : TextDisplayer = this setDisplayedText arrayListOf(text)

    /**
     * Sets the displayed text.
     * @param text The displayed text, as a String.
     */
    infix fun setDisplayedText(text : String) : TextDisplayer = this setDisplayedText StringDisplay(text)

    /**
     * Sets the displayed text.
     * @param text The displayed text, as a Text object.
     * @see Text
     */
    infix fun setDisplayedText(text : Text) : TextDisplayer {
        txt = mutableListOf()
        for(line in text.asLines()){
            txt.addAll(line)
        }
        lines = text.asLines().toMutableCollection()
        initphase = true
        return this
    }

    /**
     * Computes the total height of the TextDisplayer.
     * @param g The Graphics context.
     */
    private infix fun computeTotalHeight(g : Graphics){
        var computedH = upDelta + downDelta
        for(line : List<StringDisplay> in lines){
            computedH += line.lineHeight(g)
        }
        h.value = computedH
    }

    /**
     * Computes the maximal length of the StringDisplays' lines.
     * @param g The Graphics context.
     */
    private fun computeMaxLength(g : Graphics){
        var maxLength = 0
        for(line : List<StringDisplay> in lines){
            val lineLength : Int = line.lineLength(g)
            if(lineLength > maxLength){
                maxLength = lineLength
            }
        }
        w.value = maxLength + leftDelta + rightDelta
    }

    /**
     * Draws the background of the component.
     * @param g The Graphics context.
     * @see backgroundDrawer
     */
    private fun drawBackground(g : Graphics) = backgroundDrawer.invoke(g, width(), height())

    override fun drawDisplayer(g : Graphics){
        drawBackground(g)
        drawText(g)
    }

    /**
     * Draws the text of the TextDisplayer.
     * @param g The Graphics context.
     */
    private fun drawText(g : Graphics){
        var currentX : Int = leftDelta
        var currentY : Int = upDelta

        for(line : Collection<StringDisplay> in lines){
            currentY += line.ascent(g)
            for(s : StringDisplay in line){
                g.font = s.font
                g.color = s.color
                g.drawString(s.text, currentX, currentY)
                currentX += g.getFontMetrics(s.font).stringWidth(s.text)
            }
            currentX = leftDelta
            currentY += line.descent(g)
        }
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): TextDisplayer {
        super.updateRelativeValues(frameWidth, frameHeight)
        if(relativeMaxLineLength != null) maxLineLength = (relativeMaxLineLength!! * frameWidth).toInt()
        return this
    }

    override fun loadParameters(g : Graphics){
        lines = if(maxLineLength != null) txt.toLines(maxLineLength!!, g) else txt.toLinesList()
        computeTotalHeight(g)
        computeMaxLength(g)
    }

}