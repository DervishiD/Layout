package geometry

import main.DOUBLE_PRECISION
import java.lang.ArithmeticException
import kotlin.math.*

/*
* -- IMPORTANT NOTE --
*
* EX IS IN THE RIGHT DIRECTION, EY IS IN THE DOWN DIRECTION
* THE ANGLES ARE COUNTED BACKWARDS
*
* */

/**
 * 2D Vectors.
 * */
class Vector {

    /**
     * The x coordinate.
     * */
    public var x: Double
    /**
     * The y coordinate.
     * */
    public var y: Double

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
     * Mixed constructor.
     */
    constructor(x : Double, y : Int) : this(x, y.toDouble())

    /**
     * Mixed constructor.
     */
    constructor(x : Int, y : Double) : this(x.toDouble(), y)

    /**
     * Empty constructor, creates a null Vector.
     */
    constructor() : this(0, 0)

    /**
     * Creates a Vector from a Point.
     */
    constructor(p : Point) : this(p.x, p.y)

    /**
     * Creates a Vector from another Vector.
     */
    constructor(v : Vector) : this(v.x, v.y)

    /**
     * Creates a Vector that goes from the first Point to the second one.
     * @param from The starting Point
     * @param to The tip Point
     */
    constructor(from : Point, to : Point) : this(to.x - from.x, to.y - from.y)

    /**
     * Returns the norm of this Vector.
     */
    public fun norm() : Double = sqrt(this * this)

    /**
     * Returns the acute angle between this Vector ant the other one.
     */
    public infix fun angle(v : Vector) : Double = acos(abs((this * v) / (norm() * v.norm())))

    /**
     * Stretches this Vector to the given length
     */
    public infix fun toLength(length : Double){
        unit()
        x *= length
        y *= length
    }

    /**
     * Stretches this Vector to the given length
     */
    public infix fun toLength(length : Int){
        unit()
        x *= length
        y *= length
    }

    /**
     * Creates a vector with this one's direction but with length one.
     */
    public fun unit() = this / norm()

    /**
     * Set this Vector's length to one.
     */
    public fun toUnit() = this toLength 1

    /**
     * Returns the complex-style argument of this Vector.
     */
    public fun argument() : Double{
        return atan2(y, x)
    }

    /**
     * Dot product.
     */
    public infix fun dot(v : Vector) : Double = this * v

    /**
     * Norm of the cross product aka determinant
     */
    public infix fun cross(v : Vector) : Double = x * v.y - y * v.x

    /**
     * Rotates this vector by the given angle.
     * @param angle The angle of rotation.
     */
    public infix fun rotate(angle : Double){
        val newX = x * cos(angle) - y * sin(angle)
        val newY = x * sin(angle) + y * cos(angle)
        x = newX
        y = newY
    }

    /**
     * Sets the x coordinate
     */
    public infix fun setx(x : Double){
        this.x = x
    }

    /**
     * Sets the x coordinate
     */
    public infix fun setx(x : Int) = setx(x.toDouble())

    /**
     * Sets the y coordinate
     */
    public infix fun sety(y : Double){
        this.y = y
    }

    /**
     * Sets the y coordinate
     */
    public infix fun sety(y : Int) = sety(y.toDouble())

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
     * Rotation
     */
    public operator fun rem(d : Double) = rotate(d)

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
                abs(x - other.x) <= DOUBLE_PRECISION &&
                abs(y - other.y) <= DOUBLE_PRECISION
            }
            else -> throw IllegalArgumentException("Can't compare to a Point.")
        }
    }

    /**
     * Returns a Point with the same coordinates.
     */
    public fun toPoint() : Point = Point(x, y)

    /**
     * Returns a copy of this Vector.
     */
    public fun copy() : Vector = Vector(x, y)

}