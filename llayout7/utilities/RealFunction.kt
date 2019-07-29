package llayout7.utilities

/**
 * @since LLayout 1
 */
typealias RealFunction = (Double) -> Double

/**
 * Adds the two functions.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.plus(other : RealFunction) : RealFunction = { x -> invoke(x) + other(x) }

/**
 * Multiplies the two functions.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.times(other : RealFunction) : RealFunction = { x -> invoke(x) * other(x) }

/**
 * Subtracts the two functions.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.minus(other: RealFunction) : RealFunction = { x -> invoke(x) - other(x) }

/**
 * Returns the opposite of this function.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.unaryMinus() : RealFunction = { x -> - invoke(x) }

/**
 * Divides the two functions.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.div(other : RealFunction) : RealFunction = { x -> invoke(x) / other(x) }

/**
 * Evaluates the function on an integer.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.invoke(value : Int) : Double = invoke(value.toDouble())

/**
 * Composes the two functions.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.invoke(other : RealFunction) : RealFunction = { x -> invoke(other(x)) }

/**
 * Adds a constant function to this one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.plus(other : Double) : RealFunction = { x -> invoke(x) + other }

/**
 * Adds a constant function to this one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.plus(other : Int) : RealFunction = { x -> invoke(x) + other }

/**
 * Subtracts a constant function from this one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.minus(other : Double) : RealFunction = { x -> invoke(x) - other }

/**
 * Subtracts a constant function from this one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.minus(other : Int) : RealFunction = { x -> invoke(x) - other }

/**
 * Multiplies a constant function and this one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.times(other : Double) : RealFunction = { x -> invoke(x) * other }

/**
 * Multiplies a constant function and this one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.times(other : Int) : RealFunction = { x -> invoke(x) * other }

/**
 * Divides this function by a constant function.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.div(other : Double) : RealFunction{
    if(other == 0.0) throw IllegalArgumentException("Division of a function by zero")
    return { x -> invoke(x) / other }
}

/**
 * Divides this function by a constant function.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun RealFunction.div(other : Int) : RealFunction{
    if(other == 0) throw IllegalArgumentException("Division of a function by zero")
    return { x -> invoke(x) / other }
}

/**
 * Adds a constant function to the given one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Double.plus(function : RealFunction) : RealFunction = function + this

/**
 * Subtracts the given function from this constant.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Double.minus(function : RealFunction) : RealFunction = -function + this

/**
 * Multiplies the given function by this constant one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Double.times(function : RealFunction) : RealFunction = function * this

/**
 * Divides this constant by the given function.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Double.div(function : RealFunction) : RealFunction = { x -> this / function(x) }

/**
 * Adds a constant function to the given one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Int.plus(function : RealFunction) : RealFunction = function + this

/**
 * Subtracts the given function from this constant.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Int.minus(function : RealFunction) : RealFunction = -function + this

/**
 * Multiplies the given function by this constant one.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Int.times(function : RealFunction) : RealFunction = function * this

/**
 * Divides this constant by the given function.
 * @see RealFunction
 * @since LLayout 5
 */
operator fun Int.div(function : RealFunction) : RealFunction = { x -> this / function(x) }
