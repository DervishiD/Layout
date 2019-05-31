package llayout.displayers

import llayout.GraphicAction
import llayout.utilities.*
import java.awt.Color
import java.awt.Graphics

class TextArrowSelector<T> : AbstractArrowSelector<T> {

    private companion object {

        private const val DEFAULT_LINE_THICKNESS : Int = 2

        private val DEFAULT_LINE_COLOR : Color = Color.BLACK

        private val DEFAULT_BACKGROUND : GraphicAction = { g: Graphics, w: Int, h: Int -> run{
            g.color = DEFAULT_LINE_COLOR
            g.fillRect(0, 0, DEFAULT_LINE_THICKNESS, h)
            g.fillRect(0, 0, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(0, h - DEFAULT_LINE_THICKNESS, w,
                    DEFAULT_LINE_THICKNESS
            )
            g.fillRect(w - DEFAULT_LINE_THICKNESS, 0, w, h)
        }}

        private const val TEXT_TO_BOUNDS_DISTANCE : Int = 4

    }

    private var textDisplayer : Label = Label()

    private var text : MutableList<Collection<StringDisplay>> = mutableListOf()

    private var maxLineLength : Int? = null

    private var relativeMaxLineLength : Double? = null

    init{
        textDisplayer.setLateralGap(TEXT_TO_BOUNDS_DISTANCE)
        textDisplayer.addWidthListener { adaptWidth() }
        textDisplayer.addHeightListener { adaptHeight() }
        textDisplayer.setX(0.5)
        textDisplayer.setY(0.5)
        core(textDisplayer).addGraphicAction(DEFAULT_BACKGROUND)
        core.add(textDisplayer)
    }

    constructor(options : Map<Text, T>, isHorizontal: Boolean = true) : super(options.values, isHorizontal){
        fillLinesList(options.keys)
        textDisplayer.setText(text[currentOptionIndex.value])
    }

    constructor(options : Collection<Pair<Text, T>>, isHorizontal: Boolean = true) : super(options.seconds(), isHorizontal){
        fillLinesList(options.firsts())
        textDisplayer.setText(text[currentOptionIndex.value])
    }

    constructor(vararg options : Pair<Text, T>, isHorizontal: Boolean = true) : this(options.asList(), isHorizontal)

    constructor(keys : Collection<Text>, values : Collection<T>, isHorizontal: Boolean = true) : super(values, isHorizontal){
        if(keys.size != values.size) throw IllegalArgumentException("The number of keys (${keys.size}) of a TextArrowSelector" +
                " must equal its number of values (${values.size})")
        fillLinesList(keys)
        textDisplayer.setText(text[currentOptionIndex.value])
    }

    private fun fillLinesList(text : Collection<Text>){
        for(t : Text in text){
            this.text.add(t.asCollection())
        }
    }

    override fun addOption(option: T): TextArrowSelector<T> {
        super.addOption(option)
        initialize()
        return this
    }

    fun setBackground(background : GraphicAction) : TextArrowSelector<T> {
        core(textDisplayer).addGraphicAction(background)
        return this
    }

    fun setMaxLineLength(length : Int) : TextArrowSelector<T> {
        maxLineLength = length
        initialize()
        return this
    }

    fun setMaxLineLength(length : Double) : TextArrowSelector<T> {
        relativeMaxLineLength = length
        requestUpdate()
        initialize()
        return this
    }

    override fun next(){
        super.next()
        textDisplayer.setText(text[currentOptionIndex.value])
    }

    override fun previous(){
        super.previous()
        textDisplayer.setText(text[currentOptionIndex.value])
    }

    private fun adaptWidth(){
        if(isHorizontal()){
            //DON'T
            core.setWidth(textDisplayer.width() + 2 * (EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH))
        }else{
            //DON'T
            core.setWidth(textDisplayer.width())
        }
    }

    private fun adaptHeight(){
        if(isHorizontal()){
            //DON'T
            core.setHeight(textDisplayer.height())
        }else{
            //DON'T
            core.setHeight(textDisplayer.height() + 2 * (EXTERIOR_DELTA + VERTICAL_ARROW_HEIGHT))
        }
    }

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        if(maxLineLength != null){
            if(isHorizontal()){
                textDisplayer.setMaxLineLength(maxLineLength!! - 2 * (EXTERIOR_DELTA + HORIZONTAL_ARROW_WIDTH))
            }else{
                textDisplayer.setMaxLineLength(maxLineLength!!)
            }
        }
        computeAndSetLabelWidth(g)
    }

    private fun computeAndSetLabelWidth(g : Graphics){
        var preferredWidth : Int = 0
        var preferredHeight : Int = 0
        for(optionLabel : Collection<StringDisplay> in text){
            val optionLines : Collection<Collection<StringDisplay>> = optionLabel.toLinesList()
            for(line : Collection<StringDisplay> in optionLines){
                val lineWidth : Int = line.lineLength(g)
                preferredHeight += line.lineHeight(g)
                if(lineWidth > preferredWidth){
                    preferredWidth = lineWidth
                }
            }
        }
        if(maxLineLength != null && preferredWidth > maxLineLength!!){
            textDisplayer.setPreferredWidth(maxLineLength!!)
        }else{
            textDisplayer.setPreferredWidth(preferredWidth + 2 * TEXT_TO_BOUNDS_DISTANCE)
        }
        textDisplayer.setPreferredHeight(preferredHeight - 2 * TEXT_TO_BOUNDS_DISTANCE)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        if(relativeMaxLineLength != null) maxLineLength = (relativeMaxLineLength!! * frameWidth).toInt()
    }

}