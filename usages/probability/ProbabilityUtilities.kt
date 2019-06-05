package usages.probability

import llayout1.RealFunction
import kotlin.math.*
import kotlin.random.Random

private const val DOUBLE_PRECISION : Double = 1e-3

fun mean(x : Double, y : Double) : Double = (x + y) / 2.0

internal fun RealFunction.inverseDichotomicSearch(p : Double, precision : Double = DOUBLE_PRECISION) : Double{
    val valueAtZero : Double = invoke(0.0)
    var lowerBound : Double
    var upperBound : Double
    when{
        abs(valueAtZero - p) < precision -> return 0.0
        valueAtZero < p -> run{
            var n = 1.0
            while(invoke(n) < p){
                n *= 2
            }
            lowerBound = 0.0
            upperBound = n
        }
        valueAtZero > p -> run{
            var n = -1.0
            while(invoke(n) > p){
                n *= 2
            }
            lowerBound = n
            upperBound = 0.0
        }
        else -> throw Exception("Broken inverse dichotomic search.")
    }

    var mean = mean(lowerBound, upperBound)
    var meanValue : Double = invoke(mean)

    while(abs(meanValue - p) > precision){
        if(meanValue > p) upperBound = mean else lowerBound = mean
        mean = mean(upperBound, lowerBound)
        meanValue = invoke(mean(lowerBound, upperBound))
    }

    return mean

}

internal fun RealFunction.inverseDichotomicSearch(p : Int, precision: Double = DOUBLE_PRECISION) : Double = inverseDichotomicSearch(p.toDouble(), precision)

fun randomHomogeneous(low : Double, high : Double) : Double{
    return when{
        low > high -> ({x : Double -> (x - high)/(low - high)}.inverseDichotomicSearch(Random.nextDouble()))
        low == high -> low
        else -> ({x : Double -> (x - low)/(high - low)}.inverseDichotomicSearch(Random.nextDouble()))
    }
}

fun randomHomogeneous(low : Int, high : Double) : Double = randomHomogeneous(low.toDouble(), high)
fun randomHomogeneous(low : Double, high : Int) : Double = randomHomogeneous(low, high.toDouble())
fun randomHomogeneous(low : Int, high : Int) : Double = randomHomogeneous(low.toDouble(), high.toDouble())

fun randomHomogeneous() : Double{
    return {x : Double -> x}.inverseDichotomicSearch(Random.nextDouble())
}

fun randomCosPlusX(low : Double, high : Double) : Double{
    TODO("Not implemented.")
}

fun randomCosPlusX(low : Int, high : Double) : Double = randomCosPlusX(low.toDouble(), high)
fun randomCosPlusX(low : Double, high : Int) : Double = randomCosPlusX(low, high.toDouble())
fun randomCosPlusX(low : Int, high : Int) : Double = randomCosPlusX(low.toDouble(), high.toDouble())

fun randomCosPlusX() : Double{
    //On the interval [0, 4.5], empirically
    //0.739 is cos's fixed point
    //Division by 3 to have a wider range ( [0, 4.5] )
    val inverseProportion : Double = {x : Double -> (cos(x - 0.739) + x - 0.739)/3}.inverseDichotomicSearch(Random.nextDouble()) / 4.5
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}

fun randomExponential() : Double{
    //On the interval [0, 2], empirically
    //0.157 is a constant
    val inverseProportion : Double = {x : Double -> 0.157 * (exp(x) - 1)}.inverseDichotomicSearch(Random.nextDouble()) / 2
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}

fun randomArcsin() : Double{
    //On the interval [0, 1], empirically
    //Constants for interval
    val inverseProportion : Double = {x : Double -> 0.5 + asin(2 * x - 1) / PI}.inverseDichotomicSearch(Random.nextDouble())
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}

fun randomRoot() : Double{
    //On the interval [0, 2]
    val inverseProportion : Double = {x : Double -> sqrt(x)/2}.inverseDichotomicSearch(Random.nextDouble()) / 4
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}

fun randomln() : Double{
    //On the interval [0, 1], empirically
    //1.72 is a constant
    val inverseProportion : Double = {x : Double -> log(1.72 * x + 1, E)}.inverseDichotomicSearch(Random.nextDouble())
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}

fun randomSquare() : Double{
    //On the interval [0, 1]
    val inverseProportion : Double = {x : Double -> x*x}.inverseDichotomicSearch(Random.nextDouble())
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}

fun randomCube() : Double{
    //On the interval [0, 1]
    val inverseProportion : Double = {x : Double -> x*x*x}.inverseDichotomicSearch(Random.nextDouble())
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}

fun randomFourth() : Double{
    //On the interval [0, 1]
    val inverseProportion : Double = {x : Double -> x*x*x*x}.inverseDichotomicSearch(Random.nextDouble())
    return when{
        inverseProportion < 0.0 -> 0.0
        inverseProportion > 1.0 -> 1.0
        else -> inverseProportion
    }
}
