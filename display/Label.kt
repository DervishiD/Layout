package display

import geometry.Point
import main.GraphicAction
import utilities.StringDisplay
import utilities.Text
import utilities.ascent
import utilities.descent
import java.awt.Graphics

/**
 * A Label is a Displayer that displays only text.
 * @see TextDisplayer
 * @see Displayer
 * @see Text
 * @see StringDisplay
 */
class Label : TextDisplayer {

    /**
     * Constructs a Label with the given parameters.
     * @param p The center point of this Label.
     * @param text The displayed text, as a Collection of StringDisplays.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     * @see Point
     */
    constructor(p : Point, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)

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
    constructor(x : Double, y : Double, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Double, y : Int, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Int, y : Double, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Int, y : Int, text : Collection<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param p The center point of this Label.
     * @param text The displayed text, as a StringDisplay.
     * @param background The background of this Label, as a GraphicAction.
     * @see StringDisplay
     * @see GraphicAction
     * @see backgroundDrawer
     * @see Point
     */
    constructor(p : Point, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)

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
    constructor(x : Double, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Double, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Int, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Int, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param p The center point of this Label.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     * @see Point
     */
    constructor(p : Point, text : String, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Double, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param x The x coordinate of this Label's center Point.
     * @param y The y coordinate of this Label's center Point.
     * @param text The displayed text, as a String.
     * @param background The background of this Label, as a GraphicAction.
     * @see GraphicAction
     * @see backgroundDrawer
     */
    constructor(x : Int, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

    /**
     * Constructs a Label with the given parameters.
     * @param p The center point of this Label.
     * @param text The displayed text, as a Text.
     * @param background The background of this Label, as a GraphicAction.
     * @see Text
     * @see GraphicAction
     * @see backgroundDrawer
     * @see Point
     */
    constructor(p : Point, text : Text, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)

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
    constructor(x : Int, y : Int, text : Text, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Int, y : Double, text : Text, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Double, y : Int, text : Text, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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
    constructor(x : Double, y : Double, text : Text, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)


    override fun drawText(g : Graphics) {
        var currentX = 0
        var currentY = 0

        for(line : Collection<StringDisplay> in lines){
            currentY += line.ascent(g)
            for(s : StringDisplay in line){
                g.font = s.font
                g.color = s.color
                g.drawString(s.text, currentX, currentY)
                currentX += g.getFontMetrics(s.font).stringWidth(s.text)
            }
            currentX = 0
            currentY += line.descent(g)
        }

    }

    override fun loadParameters(g : Graphics){
        forceMaxLineLength(g, 0)
        computeTotalHeight(g, 0)
        computeMaxLength(g, 0)
    }

}