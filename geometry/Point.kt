package geometry

/**
 * A geometric Point.
 * */
class Point {

    /**
     * The x coordinate.
     * */
    private var x : Double
    /**
     * The y coordinate.
     * */
    private var y : Double

    /**
     * Basic constructor
     */
    constructor(x : Double, y : Double){
        this.x = x
        this.y = y
    }

    /**
     * Int constructor.
     */
    constructor(x : Int, y : Int) : this(x.toDouble(), y.toDouble())

    /**
     * Empty constructor, creates a Point at coordinates (0,0).
     */
    constructor() : this(0, 0)

    /**
     * Creates a Point from another Point.
     */
    constructor(p : Point) : this(p.x, p.y)

    /**
     * Creates a Point from a vector.
     */
    constructor(v : Vector) : this(v.x(), v.y())

    /**
     * Returns x.
     * */
    public fun x() : Double = x
    /**
     * Returns y.
     */
    public fun y() : Double = y

    /**
     * Returns x rounded to an integer.
     */
    public fun intx() : Int = x.toInt()
    /**
     * Returns y rounded to an integer.
     */
    public fun inty() : Int = y.toInt()

    /**
     * Addition of a Vector to a Point, corresponds to moving the Point along the Vector.
     * @param v The direction Vector.
     * @return The moved Point.
     * */
    public operator fun plus(v : Vector) : Point = Point(x + v.x(), y + v.y())

    /**
     * Subtraction of a Vector to a Point, corresponds to moving the Point along the opposite Vector.
     * @param v The direction Vector.
     * @return The moved Point.
     * */
    public operator fun minus(v : Vector) : Point = Point(x - v.x(), y - v.y())

    /**
     * Returns a Vector with the same coordinates.
     */
    public fun toVector() : Vector = Vector(x, y)

}