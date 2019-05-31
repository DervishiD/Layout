package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.utilities.StringDisplay
import llayout.utilities.Text
import java.awt.Graphics

class TextButton : TextDisplayer {

    private companion object{
        private const val LABEL_LATERAL_DISTANCE : Int = 4
        private const val LINE_THICKNESS : Int = 2
        private val LINE_COLOR = DEFAULT_COLOR
        private val DEFAULT_BUTTON_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = LINE_COLOR
            g.fillRect(0, 0, LINE_THICKNESS, h)
            g.fillRect(0, 0, w, LINE_THICKNESS)
            g.fillRect(0, h - LINE_THICKNESS, w, LINE_THICKNESS)
            g.fillRect(w - LINE_THICKNESS, 0, w, h)
        }
    }

    override var lateralAdditionalDistance: Int = LABEL_LATERAL_DISTANCE

    init{
        core.addGraphicAction(DEFAULT_BUTTON_BACKGROUND)
    }

    constructor(text : Collection<StringDisplay>, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Text, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : StringDisplay, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : CharSequence, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(action : Action) : super(){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Int, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Double, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Long, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Float, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Short, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Byte, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Boolean, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Char, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

}