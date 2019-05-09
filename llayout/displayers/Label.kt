package llayout.displayers

import llayout.GraphicAction
import llayout.utilities.StringDisplay
import llayout.utilities.Text

/**
 * A Label is a Displayer that displays only text.
 * @see TextDisplayer
 * @see Displayer
 * @see Text
 * @see StringDisplay
 */
class Label : TextDisplayer {

    override var upDelta: Int = 0
    override var downDelta: Int = 0
    override var leftDelta: Int = 0
    override var rightDelta: Int = 0

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Text.
     * @param background The background of this Label, as a GraphicAction.
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Text.
     * @param background The background of this Label, as a GraphicAction.
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Text.
     * @param background The background of this Label, as a GraphicAction.
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a Text.
     * @param background The background of this Label, as a GraphicAction.
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : Text, background: GraphicAction = NO_BACKGROUND) : super(x, y, text, background)

}