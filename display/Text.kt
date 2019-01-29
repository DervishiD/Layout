package display

import geometry.Point

/**
 * Abstract JLabel extension created to display text as efficiently as possible
 */
public abstract class Text : Displayed {

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

}