package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.utilities.StringDisplay
import llayout.utilities.Text
import java.awt.Color
import java.awt.Graphics

/**
 * A TextButton is a Displayer that is constructed with an Action invoked when the mouse
 * is released on it.
 * @see Action
 * @see TextDisplayer
 * @see Displayer
 * @see ImageButton
 */
class TextButton : TextDisplayer {

    private companion object {

        /**
         * The thickness of the line in the default background of the TextButton.
         * @see DEFAULT_BUTTON_BACKGROUND
         * @see GraphicAction
         */
        private const val LINE_THICKNESS : Int = 2

        /**
         * The base side delta.
         * @see TextDisplayer.upDelta
         * @see TextDisplayer.downDelta
         * @see TextDisplayer.leftDelta
         * @see TextDisplayer.rightDelta
         * @see DEFAULT_BUTTON_BACKGROUND
         * @see GraphicAction
         */
        private const val DELTA : Int = LINE_THICKNESS + 2

        /**
         * The color of the line in the default background of the TextButton.
         * @see DEFAULT_BUTTON_BACKGROUND
         * @see GraphicAction
         */
        private val LINE_COLOR : Color = DEFAULT_COLOR

        /**
         * The default background of this TextButton.
         * @see GraphicAction
         */
        private val DEFAULT_BUTTON_BACKGROUND : GraphicAction =
            { g: Graphics, w: Int, h: Int -> run{
                g.color = LINE_COLOR
                g.fillRect(0, 0, LINE_THICKNESS, h)
                g.fillRect(0, 0, w, LINE_THICKNESS)
                g.fillRect(0, h - LINE_THICKNESS, w, LINE_THICKNESS)
                g.fillRect(w - LINE_THICKNESS, 0, w, h)
            }}

    }

    override var upDelta: Int = DELTA
    override var downDelta: Int = DELTA
    override var leftDelta: Int = DELTA
    override var rightDelta: Int = DELTA

    init{
        backgroundDrawer = DEFAULT_BUTTON_BACKGROUND
    }

    constructor(text : CharSequence, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : StringDisplay, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Collection<StringDisplay>, action : Action) : super(text){
        setOnMouseReleasedAction { action() }
    }

    constructor(text : Text, action : Action) : super(text){
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