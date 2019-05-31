package llayout.displayers

import llayout.Action
import llayout.utilities.*
import java.awt.Graphics

abstract class TextDisplayer : Displayer {

    private var text : LObservable<MutableCollection<StringDisplay>> = LObservable(mutableSetOf())

    private var lines : MutableCollection<MutableList<StringDisplay>> = mutableSetOf()

    private var maxLineLength : Int? = null

    private var relativeMaxLineLength : Double? = null

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

    fun text() : String = text.value.collapse()

    fun setText(text : Collection<StringDisplay>) : TextDisplayer {
        this.text.value = text.toMutableCollection()
        lines = text.toLinesList()
        initialize()
        return this
    }

    fun setText(text : StringDisplay) : TextDisplayer = setText(setOf(text))

    fun setText(text : CharSequence) : TextDisplayer = setText(StringDisplay(text))

    fun setText(text : Text) : TextDisplayer {
        this.text.value = mutableListOf()
        for(line in text.asLines()){
            this.text.value.addAll(line)
        }
        lines = text.asLines().toMutableCollection()
        initialize()
        return this
    }

    fun setText(text : Int) : TextDisplayer = setText(text.toString())

    fun setText(text : Long) : TextDisplayer = setText(text.toString())

    fun setText(text : Double) : TextDisplayer = setText(text.toString())

    fun setText(text : Float) : TextDisplayer = setText(text.toString())

    fun setText(text : Short) : TextDisplayer = setText(text.toString())

    fun setText(text : Byte) : TextDisplayer = setText(text.toString())

    fun setText(text : Char) : TextDisplayer = setText(text.toString())

    fun setText(text : Boolean) : TextDisplayer = setText(text.toString())

    fun setMaxLineLength(length : Int) : TextDisplayer {
        if(length <= 0) throw IllegalArgumentException("length $length in TextDisplayer.setMaxLineLength is invalid.")
        maxLineLength = length
        initialize()
        return this
    }

    fun setMaxLineLength(length : Double) : TextDisplayer{
        if(length <= 0) throw IllegalArgumentException("length $length in TextDisplayer.setMaxLineLength is invalid.")
        relativeMaxLineLength = length
        requestUpdate()
        initialize()
        return this
    }

    fun setLateralGap(gap : Int) : TextDisplayer{
        if(gap < 0) throw IllegalArgumentException("The gap $gap given to TextDisplayer.setAdditionalLateralGap must be non-negative.")
        lateralAdditionalDistance = gap
        return this
    }

    fun addTextListener(key : Any?, action : Action) : TextDisplayer{
        text.addListener(key, action)
        return this
    }

    fun addTextListener(action : Action) : TextDisplayer{
        text.addListener(action)
        return this
    }

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

    private fun lines() : Collection<Collection<StringDisplay>> = lines

    private fun lateralAdditionalDistance() : Int = lateralAdditionalDistance

}