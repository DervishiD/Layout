package llayout2.displayers

import llayout2.Action
import llayout2.utilities.*
import java.awt.Graphics

/**
 * An abstraction for the Displayers that display text.
 * @see Label
 * @see TextButton
 * @since LLayout 1
 */
abstract class TextDisplayer : Displayer {

    /**
     * The text that is displayed.
     * @since LLayout 1
     */
    private var text : LObservable<MutableCollection<StringDisplay>> = LObservable(mutableSetOf())

    /**
     * The displayed text, cut in lines.
     * @since LLayout 1
     */
    private var lines : MutableCollection<MutableList<StringDisplay>> = mutableSetOf()

    /**
     * The maximal length of the TextDisplayer, in pixels.
     * @since LLayout 1
     */
    private var maxLineLength : Int? = null

    /**
     * The maximal length of this TextDisplayer, as a proportion of its container's width.
     * @since LLayout 1
     */
    private var relativeMaxLineLength : Double? = null

    /**
     * The distance between the bounds of the TextDisplayer and the written text.
     * @since LLayout 1
     */
    protected abstract var lateralAdditionalDistance : Int

    init{
        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            var currentX : Int = lateralAdditionalDistance()
            var currentY : Int = lateralAdditionalDistance()

            for(line : Collection<StringDisplay> in lines()){
                currentY += line.ascent(g)
                for(s : StringDisplay in line){
                    g.font = s.font
                    g.color = s.color
                    g.drawString(s.text, currentX, currentY)
                    currentX += g.getFontMetrics(s.font).stringWidth(s.text)
                }
                currentX = lateralAdditionalDistance()
                currentY += line.descent(g)
            }
        })
    }

    constructor(text : Collection<StringDisplay>){
        this.text.value = text.toMutableCollectionOf()
        lines = text.toLinesList()
    }

    constructor(text : Text){
        for(line in text.asLines()){
            lines.add(line)
            for(sd in line){
                this.text.value.add(sd)
            }
        }
    }

    constructor(text : StringDisplay) : this(setOf(text))

    constructor(text : CharSequence) : this(StringDisplay(text))

    constructor()

    constructor(text : Int) : this(text.toString())

    constructor(text : Double) : this(text.toString())

    constructor(text : Long) : this(text.toString())

    constructor(text : Float) : this(text.toString())

    constructor(text : Short) : this(text.toString())

    constructor(text : Byte) : this(text.toString())

    constructor(text : Boolean) : this(text.toString())

    constructor(text : Char) : this(text.toString())

    /**
     * Returns the displayed text as a string.
     * @since LLayout 1
     */
    fun text() : String = text.value.collapse()

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Collection<StringDisplay>) : TextDisplayer {
        this.text.value = text.toMutableCollection()
        lines = text.toLinesList()
        initialize()
        return this
    }

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : StringDisplay) : TextDisplayer = setText(setOf(text))

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : CharSequence) : TextDisplayer = setText(StringDisplay(text))

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Text) : TextDisplayer {
        this.text.value = mutableListOf()
        for(line in text.asLines()){
            this.text.value.addAll(line)
        }
        lines = text.asLines().toMutableCollection()
        initialize()
        return this
    }

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Int) : TextDisplayer = setText(text.toString())

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Long) : TextDisplayer = setText(text.toString())

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Double) : TextDisplayer = setText(text.toString())

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Float) : TextDisplayer = setText(text.toString())

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Short) : TextDisplayer = setText(text.toString())

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Byte) : TextDisplayer = setText(text.toString())

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Char) : TextDisplayer = setText(text.toString())

    /**
     * Sets the displayed text.
     * @return this
     * @since LLayout 1
     */
    fun setText(text : Boolean) : TextDisplayer = setText(text.toString())

    /**
     * Sets the maximal length of this TextDisplayer, in pixels.
     * @return this
     * @since LLayout 1
     */
    fun setMaxLineLength(length : Int) : TextDisplayer {
        if(length <= 0) throw IllegalArgumentException("length $length in TextDisplayer.setMaxLineLength is invalid.")
        maxLineLength = length
        initialize()
        return this
    }

    /**
     * Sets the maximal length of this TextDisplayer, as a proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun setMaxLineLength(length : Double) : TextDisplayer{
        if(length <= 0) throw IllegalArgumentException("length $length in TextDisplayer.setMaxLineLength is invalid.")
        relativeMaxLineLength = length
        requestUpdate()
        initialize()
        return this
    }

    /**
     * Sets the distance between the written text and the bounds of this TextDisplayer.
     * @return this
     * @throws IllegalArgumentException If the gap is negative.
     * @since LLayout 1
     */
    fun setLateralGap(gap : Int) : TextDisplayer{
        if(gap < 0) throw IllegalArgumentException("The gap $gap given to TextDisplayer.setAdditionalLateralGap must be non-negative.")
        lateralAdditionalDistance = gap
        return this
    }

    /**
     * Adds a listener to the text.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addTextListener(key : Any?, action : Action) : TextDisplayer{
        text.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the text.
     * @return this
     * @since LLayout 1
     */
    fun addTextListener(action : Action) : TextDisplayer{
        text.addListener(action)
        return this
    }

    /**
     * Removes the listener of the text associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeTextListener(key : Any?) : TextDisplayer{
        text.removeListener(key)
        return this
    }

    override fun initializeDrawingParameters(g: Graphics) {
        lines = if(maxLineLength != null) {
            text.value.toLines(maxLineLength!! - 2 * lateralAdditionalDistance, g)
        }else {
            text.value.toLinesList()
        }
        setCoreDimensions(g)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        if(relativeMaxLineLength != null) maxLineLength = (relativeMaxLineLength!! * frameWidth).toInt()
        super.updateRelativeValues(frameWidth, frameHeight)
    }

    /**
     * Sets the dimensions of the core so that it fits the needed dimensions.
     * @since LLayout 1
     */
    private fun setCoreDimensions(g : Graphics){
        var maximalWidth : Int = 0
        var height : Int = 2 * lateralAdditionalDistance
        for(line : Collection<StringDisplay> in lines){
            height += line.lineHeight(g)
            val lineLength : Int = line.lineLength(g)
            if(lineLength > maximalWidth){
                maximalWidth = lineLength
            }
        }
        core.setWidth(maximalWidth + 2 * lateralAdditionalDistance)
        core.setHeight(height)
    }

    /**
     * Returns the written text as a collection of lines.
     * @since LLayout 1
     */
    private fun lines() : Collection<Collection<StringDisplay>> = lines

    /**
     * Returns the distance between the text and the bounds.
     * @since LLayout 1
     */
    private fun lateralAdditionalDistance() : Int = lateralAdditionalDistance

}