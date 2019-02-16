package geometry

import main.DOUBLE_PRECISION
import kotlin.math.abs

/**
 * A geometric Point.
 * */
class Point {

    /**
     * The x coordinate.
     * */
    var x : Double
    /**
     * The y coordinate.
     * */
    var y : Double

    /**
     * Basic constructor
     */
    constructor(x : Double, y : Double) {
        this.x = x
        this.y = y
    }

    /**
     * Int constructor.
     */
    constructor(x : Int, y : Int) : this(x.toDouble(), y.toDouble())

    /**
     * Mixed constructor.
     */
    constructor(x : Double, y : Int) : this(x, y.toDouble())

    /**
     * Mixed constructor.
     */
    constructor(x : Int, y : Double) : this(x.toDouble(), y)

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
    constructor(v : Vector) : this(v.x, v.y)

    /**
     * Returns x rounded to an integer.
     */
    fun intx() : Int = x.toInt()

    /**
     * Returns y rounded to an integer.
     */
    fun inty() : Int = y.toInt()

    /**
     * Sets the x coordinate
     */
    infix fun setx(x : Double){
        this.x = x
    }

    /**
     * Sets the x coordinate
     */
    infix fun setx(x : Int) = setx(x.toDouble())

    /**
     * Sets the y coordinate
     */
    infix fun sety(y : Double){
        this.y = y
    }

    /**
     * Sets the y coordinate
     */
    infix fun sety(y : Int) = sety(y.toDouble())

    /**
     * Addition of a Vector to a Point, corresponds to moving the Point along the Vector.
     * @param v The direction Vector.
     * @return The moved Point.
     * */
    operator fun plus(v : Vector) : Point = Point(x + v.x, y + v.y)

    /**
     * Subtraction of a Vector to a Point, corresponds to moving the Point along the opposite Vector.
     * @param v The direction Vector.
     * @return The moved Point.
     * */
    operator fun minus(v : Vector) : Point = Point(x - v.x, y - v.y)

    /**
     * Creates a Vector from this Point to the other one.
     * @param p The other Point
     */
    operator fun rangeTo(p : Point) : Vector = Vector(this, p)

    /**
     * Returns the i coordinate of this Point.
     */
    operator fun get(i : Int) : Double =
        when (i) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException("A Vector only has two coordinates")
        }

    /**
     * Sets the i coordinate of this Point to value
     * @param i The index to change
     * @param value The new value of the coordinate
     */
    operator fun set(i : Int, value : Double) {
        when (i) {
            0 -> x = value
            1 -> y = value
            else -> throw IndexOutOfBoundsException("A Vector only has two coordinates")
        }
    }

    /**
     * Sets the i coordinate of this Point to value
     * @param i The index to change
     * @param value The new value of the coordinate
     */
    operator fun set(i : Int, value : Int) = set(i, value.toDouble())

    /**
     * Checks whether this Point is the same as the compared stuff.
     */
    override operator fun equals(other : Any?) : Boolean {
        return when(other){
            is Point -> {
                abs(x - other.x) <= DOUBLE_PRECISION &&
                abs(y - other.y) <= DOUBLE_PRECISION
            }
            is Vector -> {
                abs(x - other.x) <= DOUBLE_PRECISION &&
                abs(y - other.y) <= DOUBLE_PRECISION
            }
            else -> throw IllegalArgumentException("Can't compare to a Point.")
        }
    }

    /**
     * Returns a Vector with the same coordinates.
     */
    fun toVector() : Vector = Vector(x, y)

    /**
     * Returns a copy of this Point.
     */
    fun copy() : Point = Point(x, y)

    override fun toString() : String = "( $x , $y )"

}