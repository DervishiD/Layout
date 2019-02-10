package display.selectors

import display.Displayer
import geometry.Point

/**
 * A class representing all kinds of selectors
 */
abstract class GeneralSelector<T> : Displayer {

    protected var options : ArrayList<T>
    protected var currentOption : Int = 0

    constructor(p : Point, options : ArrayList<T>) : super(p){
        if(options.size == 0) throw IllegalArgumentException("A GeneralSelector must select something")
        this.options = options
    }
    constructor(x : Double, y : Double, options: ArrayList<T>) : this(Point(x, y), options)
    constructor(x : Int, y : Double, options: ArrayList<T>) : this(Point(x, y), options)
    constructor(x : Double, y : Int, options: ArrayList<T>) : this(Point(x, y), options)
    constructor(x : Int, y : Int, options: ArrayList<T>) : this(Point(x, y), options)

    fun selectedOption() : T = options[currentOption]

}