package display.texts

import display.Displayed
import display.StringDisplay
import geometry.Point
import java.awt.Graphics

/**
 * Abstract JLabel extension created to display text as efficiently as possible
 */
public abstract class Text : Displayed {

    override var w : Int = 0
    override var h : Int = 0

    constructor(p : Point, text : ArrayList<StringDisplay>) : super(p, text)
    constructor(x : Double, y : Double, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(x : Double, y : Int, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(x : Int, y : Double, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(x : Int, y : Int, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(p : Point, text : StringDisplay) : super(p, text)
    constructor(x : Double, y : Double, text : StringDisplay) : super(Point(x, y), text)
    constructor(x : Double, y : Int, text : StringDisplay) : super(Point(x, y), text)
    constructor(x : Int, y : Double, text : StringDisplay) : super(Point(x, y), text)
    constructor(x : Int, y : Int, text : StringDisplay) : super(Point(x, y), text)
    constructor(p : Point, text : String) : super(p, text)
    constructor(x : Double, y : Double, text : String) : super(Point(x, y), text)
    constructor(x : Double, y : Int, text : String) : super(Point(x, y), text)
    constructor(x : Int, y : Double, text : String) : super(Point(x, y), text)
    constructor(x : Int, y : Int, text : String) : super(Point(x, y), text)

    protected override fun loadParameters(g : Graphics){
        forceMaxLineLength(g, 0)
        computeTotalHeight(g, 0)
        computeMaxLength(g, 0)
    }

}