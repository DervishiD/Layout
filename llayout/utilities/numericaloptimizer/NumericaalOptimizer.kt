package llayout.utilities.numericaloptimizer

/**
 * A tool that optimizes numerically the value of a function.
 * @since LLayout 8
 */
object NumericaalOptimizer {

    /**
     * A boundary function that indicates that the function is defined on R^n.
     * @since LLayout 8
     */
    private val RN : (DoubleArray) -> Boolean = { true }

    /**
     * Using a numerical method, finds an approximation of a local minimum of a [function] with an [initialPosition]. The algorithm performs
     * [iterations] iterations, and tries to give a local minimum to the given [precision]. The function is defined on R^n-
     * @since LLayout 8
     */
    fun findMinimum(function : (DoubleArray) -> Double, initialPosition: DoubleArray, precision : Double, iterations : Long) : DoubleArray{
        return findMinimumWithinBounds(function, initialPosition, precision, iterations, RN)
    }

    /**
     * Using a numerical method, finds an approximation of a local maximum of a [function] with an [initialPosition]. The algorithm performs
     * [iterations] iterations, and tries to give a local maximum to the given [precision]. The function is defined on R^n.
     * @since LLayout 8
     */
    fun findMaximum(function : (DoubleArray) -> Double, initialPosition: DoubleArray, precision : Double, iterations : Long) : DoubleArray{
        return findMaximumWithinBounds(function, initialPosition, precision, iterations, RN)
    }

    /**
     * Using a numerical method, finds an approximation of a local minimum of a [function] with an [initialPosition]. The algorithm performs
     * [iterations] iterations, and tries to give a local minimum to the given [precision]. The function is defined inside the bounds defined by the [boundsTest].
     * @since LLayout 8
     */
    fun findMinimumWithinBounds(function : (DoubleArray) -> Double, initialPosition: DoubleArray, precision : Double, iterations : Long, boundsTest : (DoubleArray) -> Boolean) : DoubleArray{
        checkDataIsValid(initialPosition, precision, iterations, boundsTest)
        return findSafeMinimum(function, initialPosition, precision, iterations, boundsTest)
    }

    /**
     * Using a numerical method, finds an approximation of a local maximum of a [function] with an [initialPosition]. The algorithm performs
     * [iterations] iterations, and tries to give a local maximum to the given [precision]. The function is defined inside the bounds defined by the [boundsTest].
     * @since LLayout 8
     */
    fun findMaximumWithinBounds(function : (DoubleArray) -> Double, initialPosition: DoubleArray, precision : Double, iterations : Long, boundsTest : (DoubleArray) -> Boolean) : DoubleArray{
        checkDataIsValid(initialPosition, precision, iterations, boundsTest)
        return findSafeMaximum(function, initialPosition, precision, iterations, boundsTest)
    }

    /**
     * Verifies that the given parameters make sense.
     * @since LLayout 8
     */
    private fun checkDataIsValid(initialPosition: DoubleArray, precision: Double, iterations: Long, boundsTest: (DoubleArray) -> Boolean){
        if(initialPosition.isEmpty()) throw IllegalArgumentException("The given initial position is empty.")
        if(precision <= 0) throw IllegalArgumentException("The precision must be strictly greater than zero. The given value was $precision.")
        if(iterations <= 0) throw IllegalArgumentException("The number of iterations must be strictly greater than zero. The given value was $iterations-")
        if(!boundsTest(initialPosition)) throw IllegalArgumentException("The initial position is not contained inside the given bounds.")
    }

    /**
     * Finds the next step towards the minimum of the given [function] at the given [position] in an algorithm of [iterations] iterations with the given
     * [precision] and within the given [boundsTest].
     * @since LLayout 8
     */
    private tailrec fun findSafeMinimum(function : (DoubleArray) -> Double, position: DoubleArray, precision : Double, iterations : Long, boundsTest : (DoubleArray) -> Boolean) : DoubleArray{
        return if(iterations == 0L){
            position
        }else{
            val index : Int = (iterations % position.size).toInt()
            val upperPosition = DoubleArray(position.size) { i -> if(i == index) position[i] + precision else position[i] }
            val lowerPosition = DoubleArray(position.size) { i -> if(i == index) position[i] - precision else position[i] }
            val upperF : Double = if(boundsTest(upperPosition)) function(upperPosition) else Double.MAX_VALUE
            val f : Double = function(position)
            val lowerF : Double = if(boundsTest(lowerPosition)) function(lowerPosition) else Double.MAX_VALUE
            when{
                upperF <= f && upperF <= lowerF ->
                    findSafeMinimum(function, upperPosition, precision, iterations - 1L, boundsTest)
                lowerF <= f && lowerF <= upperF ->
                    findSafeMinimum(function, lowerPosition, precision, iterations - 1L, boundsTest)
                else -> findSafeMinimum(function, position, precision, iterations - 1L, boundsTest)
            }
        }
    }

    /**
     * Finds the next step towards the maximum of the given [function] at the given [position] in an algorithm of [iterations] iterations with the given
     * [precision] and within the given [boundsTest].
     * @since LLayout 8
     */
    private tailrec fun findSafeMaximum(function : (DoubleArray) -> Double, position: DoubleArray, precision : Double, iterations : Long, boundsTest : (DoubleArray) -> Boolean) : DoubleArray{
        return if(iterations == 0L){
            position
        }else{
            val index : Int = (iterations % position.size).toInt()
            val upperPosition = DoubleArray(position.size) { i -> if(i == index) position[i] + precision else position[i] }
            val lowerPosition = DoubleArray(position.size) { i -> if(i == index) position[i] - precision else position[i] }
            val upperF : Double = if(boundsTest(upperPosition)) function(upperPosition) else Double.MIN_VALUE
            val f : Double = function(position)
            val lowerF : Double = if(boundsTest(lowerPosition)) function(lowerPosition) else Double.MIN_VALUE
            when{
                upperF >= f && upperF >= lowerF ->
                    findSafeMaximum(function, upperPosition, precision, iterations - 1L, boundsTest)
                lowerF >= f && lowerF >= upperF ->
                    findSafeMaximum(function, lowerPosition, precision, iterations - 1L, boundsTest)
                else -> findSafeMaximum(function, position, precision, iterations - 1L, boundsTest)
            }
        }
    }

}