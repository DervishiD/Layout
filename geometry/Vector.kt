package geometry

import main.DOUBLE_PRECISION
import java.lang.ArithmeticException
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sqrt

/**
 * 2D Vectors.
 * */
class Vector {

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
     * Empty constructor, creates a null Vector.
     */
    constructor() : this(0, 0)

    /**
     * Creates a Vector from a Point.
     */
    constructor(p : Point) : this(p.x(), p.y())

    /**
     * Creates a Vector from another Vector.
     */
    constructor(v : Vector) : this(v.x, v.y)

    /**
     * Creates a Vector that goes from the first Point to the second one.
     * @param from The starting Point
     * @param to The tip Point
     */
    constructor(from : Point, to : Point) : this(to.x() - from.x(), to.y() - from.y())

    /**
     * Returns x.
     * */
    public fun x() : Double = x
    /**
     * Returns y.
     */
    public fun y() : Double = y

    /**
     * Returns the norm of this Vector.
     */
    public fun norm() : Double = sqrt(this * this)

    /**
     * Returns the acute angle between this Vector ant the other one.
     */
    public fun angle(v : Vector) : Double = acos(abs((this * v) / (norm() * v.norm())))

    /**
     * Vector addition.
     * @param v The added Vector.
     * @return The sum of the two Vectors.
     * */
    public operator fun plus(v : Vector) : Vector = Vector(x + v.x, y + v.y)

    /**
     * Vector subtraction.
     * @param v The subtracted Vector.
     * @return The subtraction of the two Vectors.
     * */
    public operator fun minus(v : Vector) : Vector = Vector(x - v.x, y - v.y)

    /**
     * Opposes this Vector.
     */
    public operator fun unaryMinus() : Vector = Vector(-x, -y)

    /**
     * Dot product.
     * @param v The other Vector.
     * @return The dot product of the two Vectors.
     * */
    public operator fun times(v : Vector) : Double = x * v.x + y * v.y

    /**
     * Scalar multiplication.
     * @param d Scalar.
     * @return Scaled Vector.
     */
    public operator fun times(d : Double) : Vector = Vector(d * x, d * y)

    /**
     * Scalar multiplication.
     * @param d Scalar.
     * @return Scaled Vector.
     */
    public operator fun times(d : Int) : Vector = Vector(d * x, d * y)

    /**
     * Scalar multiplication.
     * @param v Vector.
     * @return Scaled Vector.
     */
    public operator fun Double.times(v : Vector) : Vector = v * this

    /**
     * Scalar multiplication.
     * @param v Vector.
     * @return Scaled Vector.
     */
    public operator fun Int.times(v : Vector) : Vector = v * this

    /**
     * Scalar division
     * @param d Scalar.
     * @return Scaled Vector.
     * @throws ArithmeticException in case of a division by zero.
     */
    public operator fun div(d : Int) : Vector = this / d.toDouble()

    /**
     * Scalar division
     * @param d Scalar.
     * @return Scaled Vector.
     * @throws ArithmeticException in case of a division by zero.
     */
    public operator fun div(d : Double) : Vector =
        if(d != 0.0)
            this * (1/d)
        else throw ArithmeticException("Division by zero.")

    /**
     * Returns the i coordinate of this Vector.
     */
    public operator fun get(i : Int) : Double =
        when(i){
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException("A Vector only has two coordinates")
        }

    /**
     * Sets the i coordinate of this Vector to value
     * @param i The index to change
     * @param value The new value of the coordinate
     */
    public operator fun set(i : Int, value : Double){
        when(i){
            0 -> x = value
            1 -> y = value
            else -> throw IndexOutOfBoundsException("A Vector only has two coordinates")
        }
    }

    /**
     * Sets the i coordinate of this Vector to value
     * @param i The index to change
     * @param value The new value of the coordinate
     */
    public operator fun set(i : Int, value : Int) = set(i, value.toDouble())

    /**
     * Checks whether this Vector is the same as the compared stuff.
     */
    public override operator fun equals(other: Any?): Boolean {
        return when(other){
            is Vector -> {
                abs(x - other.x) <= DOUBLE_PRECISION &&
                abs(y - other.y) <= DOUBLE_PRECISION
            }
            is Point -> {
                abs(x - other.x()) <= DOUBLE_PRECISION &&
                abs(y - other.y()) <= DOUBLE_PRECISION
            }
            else -> throw IllegalArgumentException("Can't compare to a Point.")
        }
    }

    /**
     * Returns a Point with the same coordinates.
     */
    public fun toPoint() : Point = Point(x, y)

}