package display.selectors

import geometry.Point
import java.awt.Graphics

/**
 * A Selector that lets the User select an option in a list, HTML-style
 */
class ListSelector<T> : GeneralSelector<T> {

    override var w : Int = 0
    override var h : Int = 0

    constructor(p : Point, options : ArrayList<T>) : super(p, options)
    constructor(x : Double, y : Double, options: ArrayList<T>) : super(x, y, options)
    constructor(x : Int, y : Double, options: ArrayList<T>) : super(x, y, options)
    constructor(x : Double, y : Int, options: ArrayList<T>) : super(x, y, options)
    constructor(x : Int, y : Int, options: ArrayList<T>) : super(x, y, options)

    override fun loadParameters(g: Graphics) {
        //TODO
    }

    override fun drawDisplayer(g: Graphics) {
        //TODO
    }

}