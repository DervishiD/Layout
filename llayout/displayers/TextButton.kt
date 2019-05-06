package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.geometry.Point
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

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a list of StringDisplays.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Int,
            y : Int,
            text : Collection<StringDisplay>,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a list of StringDisplays.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Int,
            y : Double,
            text : Collection<StringDisplay>,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a list of StringDisplays.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Double,
            y : Int,
            text : Collection<StringDisplay>,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a list of StringDisplays.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Double,
            y : Double,
            text : Collection<StringDisplay>,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param p The position of this Button, as a Point.
     * @param text The displayed text, as a list of StringDisplays.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     * @see Point
     */
    constructor(
            p : Point,
            text : Collection<StringDisplay>,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(p.intx(), p.inty(), text, action, background)

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a StringDisplay.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Int,
            y : Int,
            text : StringDisplay,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a StringDisplay.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Int,
            y : Double,
            text : StringDisplay,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a StringDisplay.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Double,
            y : Int,
            text : StringDisplay,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a StringDisplay.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     */
    constructor(
            x : Double,
            y : Double,
            text : StringDisplay,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param p The position of this Button, as a Point.
     * @param text The displayed text, as a StringDisplay.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see StringDisplay
     * @see Point
     */
    constructor(
            p : Point,
            text : StringDisplay,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(p.intx(), p.inty(), text, action, background)

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a String.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     */
    constructor(
            x : Int,
            y : Int,
            text : String,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a String.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     */
    constructor(
            x : Int,
            y : Double,
            text : String,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a String.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     */
    constructor(
            x : Double,
            y : Int,
            text : String,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a String.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     */
    constructor(
            x : Double,
            y : Double,
            text : String,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param p The position of this Button, as a Point.
     * @param text The displayed text, as a String.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see Point
     */
    constructor(
            p : Point,
            text : String,
            action : Action,
            background : GraphicAction = DEFAULT_BUTTON_BACKGROUND) : this(p.intx(), p.inty(), text, action, background)

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a Text object.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see Text
     */
    constructor(
            x : Int,
            y : Int,
            text : Text,
            action : Action,
            background: GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a Text object.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see Text
     */
    constructor(
            x : Int,
            y : Double,
            text : Text,
            action : Action,
            background: GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a Text object.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see Text
     */
    constructor(
            x : Double,
            y : Int,
            text : Text,
            action : Action,
            background: GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param x The x coordinate of this Button.
     * @param y The y coordinate of this Button.
     * @param text The displayed text, as a Text object.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see Text
     */
    constructor(
            x : Double,
            y : Double,
            text : Text,
            action : Action,
            background: GraphicAction = DEFAULT_BUTTON_BACKGROUND) : super(x, y, text, background){
        this setOnMouseReleaseAction action
    }

    /**
     * Constructs a TextButton with the given text at the given position with the given background
     * with the given action.
     * @param p The position of this Button, as a Point.
     * @param text The displayed text, as a Text object.
     * @param action The Action that is executed when this Button is clicked.
     * @param background The background image of this component, as a GraphicAction.
     * @see Action
     * @see GraphicAction
     * @see Text
     */
    constructor(
            p : Point,
            text : Text,
            action : Action,
            background: GraphicAction = DEFAULT_BUTTON_BACKGROUND)
        : this(p.intx(), p.inty(), text, action, background)

}