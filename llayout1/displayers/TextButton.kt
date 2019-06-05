package llayout1.displayers

import llayout1.Action
import llayout1.DEFAULT_COLOR
import llayout1.GraphicAction
import llayout1.utilities.StringDisplay
import llayout1.utilities.Text
import java.awt.Graphics

/**
 * A button that displays text.
 * @see TextDisplayer
 * @since LLayout 1
 */
class TextButton : TextDisplayer {

    private companion object{

        /**
         * The distance between the text and the bounds of the button.
         * @since LLayout 1
         */
        private const val LABEL_LATERAL_DISTANCE : Int = 4

        /**
         * The thickness of the lines of the default background.
         * @see DEFAULT_BUTTON_BACKGROUND
         * @since LLayout 1
         */
        private const val LINE_THICKNESS : Int = 2

        /**
         * The color of the lines of the default background.
         * @see DEFAULT_BUTTON_BACKGROUND
         * @since LLayout 1
         */
        private val LINE_COLOR = DEFAULT_COLOR

        /**
         * The default background of a button.
         * @see GraphicAction
         * @since LLayout 1
         */
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