package display.texts

import display.TextDisplayer
import display.StringDisplay
import geometry.Point
import main.GraphicAction
import java.awt.Graphics

/**
 * Abstract JLabel extension created to display text as efficiently as possible
 */
public abstract class Text : TextDisplayer {

    constructor(p : Point, text : List<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)
    constructor(x : Double, y : Double, text : List<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Double, y : Int, text : List<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Double, text : List<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Int, text : List<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(p : Point, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)
    constructor(x : Double, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Double, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(p : Point, text : String, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)
    constructor(x : Double, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Double, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

    protected override fun loadParameters(g : Graphics){
        forceMaxLineLength(g, 0)
        computeTotalHeight(g, 0)
        computeMaxLength(g, 0)
    }

}