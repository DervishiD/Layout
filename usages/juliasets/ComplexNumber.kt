package usages.juliasets

import java.lang.IllegalArgumentException
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class ComplexNumber {

    companion object{
        private val I : ComplexNumber = ComplexNumber(0, 1)
        fun i() : ComplexNumber = I
        fun polar(r : Double, theta : Double) : ComplexNumber = ComplexNumber(r * cos(theta), r * sin(theta))
    }

    var x : Double

    var y : Double

    constructor(x : Double, y : Double){
        this.x = x
        this.y = y
    }
    constructor(x : Double, y : Int) : this(x, y.toDouble())
    constructor(x : Int, y : Double) : this(x.toDouble(), y)
    constructor(x : Int, y : Int) : this(x.toDouble(), y.toDouble())

    fun realPart() : Double = x

    fun imaginaryPart() : Double = y

    fun x() : Double = x

    fun y() : Double = y

    fun modulus() : Double = sqrt(x * x + y * y)

    fun argument() : Double = atan2(y, x)

    fun inverse() : ComplexNumber{
        if(!equals(0)){
            return ComplexNumber(x() / (x() * x() + y() * y()), - y() / (x() * x() + y() + y()))
        }else throw IllegalArgumentException("Zero has no inverse")
    }

    operator fun plus(other : ComplexNumber) : ComplexNumber = ComplexNumber(x + other.realPart(), y + other.imaginaryPart())

    operator fun minus(other : ComplexNumber) : ComplexNumber = ComplexNumber(x - other.realPart(), y - other.imaginaryPart())

    operator fun times(other : ComplexNumber) : ComplexNumber = ComplexNumber(x * other.x() - y * other.y(), x * other.y() + y * other.x())

    operator fun div(other : ComplexNumber) : ComplexNumber{
        if(!other.equals(0)){
            return ComplexNumber(x * other.x() + y * other.y(), x * other.y() - y * other.x()) / (other.x() * other.x() + other.y() * other.y())
        }else throw IllegalArgumentException("Dividing a complex number by zero")
    }

    operator fun unaryMinus() : ComplexNumber = ComplexNumber(-x, -y)

    operator fun plus(other : Double) : ComplexNumber = ComplexNumber(x + other, y)

    operator fun minus(other : Double) : ComplexNumber = ComplexNumber(x, y + other)

    operator fun times(other : Double) : ComplexNumber = ComplexNumber(x * other, y * other)

    operator fun div(other : Double) : ComplexNumber{
        if(other != 0.0){
            return ComplexNumber(x / other, y / other)
        }else throw IllegalArgumentException("Dividing a complex number by zero")
    }
    operator fun plus(other : Int) : ComplexNumber = ComplexNumber(x + other, y)

    operator fun minus(other : Int) : ComplexNumber = ComplexNumber(x, y + other)

    operator fun times(other : Int) : ComplexNumber = ComplexNumber(x * other, y * other)

    operator fun div(other : Int) : ComplexNumber{
        if(other != 0){
            return ComplexNumber(x / other, y / other)
        }else throw IllegalArgumentException("Dividing a complex number by zero")
    }

    override operator fun equals(other : Any?) : Boolean{
        return when (other) {
            is ComplexNumber -> x == other.x() && y == other.y()
            is Int -> x == other && y == 0.0
            is Double -> x == other && y == 0.0
            is Float -> x == other && y == 0.0
            is Short -> x == other && y == 0.0
            is Long -> x == other && y == 0.0
            is Byte -> x == other && y == 0.0
            else -> false
        }
    }

}

operator fun Int.plus(other : ComplexNumber) : ComplexNumber = other + this

operator fun Int.minus(other : ComplexNumber) : ComplexNumber = ComplexNumber(this - other.x(), - other.y())

operator fun Int.times(other : ComplexNumber) : ComplexNumber = other * this

operator fun Int.div(other : ComplexNumber) : ComplexNumber = other.inverse() * this

operator fun Double.plus(other : ComplexNumber) : ComplexNumber = other + this

operator fun Double.minus(other : ComplexNumber) : ComplexNumber = ComplexNumber(this - other.x(), - other.y())

operator fun Double.times(other : ComplexNumber) : ComplexNumber = other * this

operator fun Double.div(other : ComplexNumber) : ComplexNumber = other.inverse() * this

fun ln(c : ComplexNumber) : ComplexNumber = kotlin.math.ln(c.modulus()) + c.argument() * ComplexNumber.i()

fun exp(c : ComplexNumber) : ComplexNumber = c.modulus() * ComplexNumber(cos(c.argument()), sin(c.argument()))
