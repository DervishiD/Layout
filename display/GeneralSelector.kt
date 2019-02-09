package display

import geometry.Point

/**
 * A class representing all kinds of selectors
 */
abstract class GeneralSelector<T> : Displayer {

    protected var options : ArrayList<T>
    protected var currentOption : Int = 0

    constructor(p : Point, options : ArrayList<T>, width : Int, height : Int) : super(p){
        if(options.size == 0) throw IllegalArgumentException("A GeneralSelector must select something")
        this.options = options
        this.w = width
        this.h = height
    }
    constructor(x : Double, y : Double, options: ArrayList<T>, width: Int, height: Int) : this(Point(x, y), options, width, height)
    constructor(x : Int, y : Double, options: ArrayList<T>, width: Int, height: Int) : this(Point(x, y), options, width, height)
    constructor(x : Double, y : Int, options: ArrayList<T>, width: Int, height: Int) : this(Point(x, y), options, width, height)
    constructor(x : Int, y : Int, options: ArrayList<T>, width: Int, height: Int) : this(Point(x, y), options, width, height)

    fun selectedoption() : T = options[currentOption]

}