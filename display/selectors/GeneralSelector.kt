package display.selectors

import display.Displayer
import geometry.Point

/**
 * A class representing all kinds of selectors
 */
abstract class GeneralSelector<T> : Displayer {

    /**
     * The list of possible options
     */
    protected var options : ArrayList<T>

    /**
     * The index of the current option
     */
    protected var currentOption : Int = 0

    constructor(p : Point, options : ArrayList<T>) : super(p){
        if(options.size == 0) throw IllegalArgumentException("A GeneralSelector must select something")
        this.options = options
    }
    constructor(x : Double, y : Double, options: ArrayList<T>) : this(Point(x, y), options)
    constructor(x : Int, y : Double, options: ArrayList<T>) : this(Point(x, y), options)
    constructor(x : Double, y : Int, options: ArrayList<T>) : this(Point(x, y), options)
    constructor(x : Int, y : Int, options: ArrayList<T>) : this(Point(x, y), options)

    /**
     * Returns the current selected option
     */
    fun selectedOption() : T = options[currentOption]

}