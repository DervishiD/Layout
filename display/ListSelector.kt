package display

import geometry.Point
import java.awt.Graphics

/**
 * A Selector that lets the User select an option in a list, HTML-style
 */
class ListSelector<T> : GeneralSelector<T> {

    override var w : Int = 0
    override var h : Int = 0

    constructor(p : Point, options : ArrayList<T>, width : Int, height : Int) : super(p, options, width, height)
    constructor(x : Double, y : Double, options: ArrayList<T>, width: Int, height: Int) : super(x, y, options, width, height)
    constructor(x : Int, y : Double, options: ArrayList<T>, width: Int, height: Int) : super(x, y, options, width, height)
    constructor(x : Double, y : Int, options: ArrayList<T>, width: Int, height: Int) : super(x, y, options, width, height)
    constructor(x : Int, y : Int, options: ArrayList<T>, width: Int, height: Int) : super(x, y, options, width, height)

    override fun loadParameters(g: Graphics) {
        //TODO
    }

    override fun drawDisplayer(g: Graphics) {
        //TODO
    }

}