package display

import geometry.Point
import main.Action
import main.GraphicAction
import java.awt.Color
import java.awt.Graphics

class Button : Displayed {

    companion object {
        private const val LINE_THICKNESS : Int = 5
        private const val DELTA : Int = 2
        private val LINE_COLOR : Color = Color.BLACK
        private val DEFAULT_BUTTON_BACKGROUND : GraphicAction =
            { g: Graphics, w: Int, h: Int -> run{
                g.color = LINE_COLOR
                g.fillRect(0, 0, LINE_THICKNESS, h)
                g.fillRect(0, 0, w, LINE_THICKNESS)
                g.fillRect(0, h - LINE_THICKNESS, w, LINE_THICKNESS)
                g.fillRect(w - LINE_THICKNESS, 0, w, h)
            }}
    }

    override var w : Int = 0
    override var h : Int = 0
    private var action : Action

    constructor(p : Point, text : ArrayList<StringDisplay>, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(p, text, background){
        this.action = action
    }
    constructor(x : Double, y : Double, text : ArrayList<StringDisplay>, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Double, y : Int, text : ArrayList<StringDisplay>, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Int, y : Double, text : ArrayList<StringDisplay>, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Int, y : Int, text : ArrayList<StringDisplay>, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(p : Point, text : StringDisplay, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(p, text, background){
        this.action = action
    }
    constructor(x : Double, y : Double, text : StringDisplay, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Double, y : Int, text : StringDisplay, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Int, y : Double, text : StringDisplay, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Int, y : Int, text : StringDisplay, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(p : Point, text : String, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(p, text, background){
        this.action = action
    }
    constructor(x : Double, y : Double, text : String, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Double, y : Int, text : String, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Int, y : Double, text : String, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)
    constructor(x : Int, y : Int, text : String, action : Action, background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(Point(x, y), text, action, background)

    public fun click() = action.invoke()

    protected override fun loadParameters(g : Graphics){
        forceMaxLineLength(g, LINE_THICKNESS + DELTA)
        computeTotalHeight(g, LINE_THICKNESS + DELTA)
        computeMaxLength(g, LINE_THICKNESS + DELTA)
    }

    protected override fun drawText(g : Graphics) {
        var currentX : Int = LINE_THICKNESS + DELTA
        var currentY : Int = LINE_THICKNESS + DELTA

        for(line : ArrayList<StringDisplay> in lines){
            currentY += ascent(g, line)
            for(s : StringDisplay in line){
                g.font = s.font
                g.color = s.color
                g.drawString(s.text, currentX, currentY)
                currentX += g.getFontMetrics(s.font).stringWidth(s.text)
            }
            currentX = LINE_THICKNESS + DELTA
            currentY += descent(g, line)
        }

    }

}