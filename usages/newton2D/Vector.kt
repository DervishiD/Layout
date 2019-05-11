package usages.newton2D

import kotlin.math.sqrt

/**
 * A class used in the newton2D project to represent geometric 2D vectors.
 */
class Vector {

    /**
     * The x coordinate of this Vector.
     */
    private var x : Double

    /**
     * The y coordinate of this Vector.
     */
    private var y : Double

    constructor(x : Double, y : Double){
        this.x = x
        this.y = y
    }
    constructor(x : Double, y : Int) : this(x, y.toDouble())
    constructor(x : Int, y : Double) : this(x.toDouble(), y)
    constructor(x : Int, y : Int) : this(x.toDouble(), y.toDouble())
    constructor() : this(0.0, 0.0)

    /**
     * @return The x coordinate of this Vector.
     */
    fun x() : Double = x

    /**
     * @return The y coordinate of this Vector.
     */
    fun y() : Double = y

    /**
     * @return The x coordinate of this Vector, rounded to the nearest integer.
     */
    fun intX() : Int = x.toInt()

    /**
     * @return The y coordinate of this Vector, rounded to the nearest integer.
     */
    fun intY() : Int = y.toInt()

    /**
     * @return The square of this Vector's norm.
     */
    fun normSquared() : Double = this * this

    /**
     * @return This Vector's norm.
     */
    fun norm() : Double = sqrt(normSquared())

    operator fun plus(other : Vector) : Vector = Vector(x + other.x(), y + other.y())

    operator fun times(scalar : Double) : Vector = Vector(x * scalar, y * scalar)

    operator fun Double.times(vector : Vector) : Vector = vector * this

    operator fun times(scalar : Int) : Vector = Vector(x * scalar, y * scalar)

    operator fun Int.times(vector : Vector) : Vector = vector * this

    operator fun times(other : Vector) : Double = x * other.x() + y * other.y()

}