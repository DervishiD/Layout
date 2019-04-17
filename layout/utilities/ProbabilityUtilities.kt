package layout.utilities

import layout.DOUBLE_PRECISION
import layout.RealFunction
import kotlin.math.abs
import kotlin.random.Random

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
        low > high -> randomHomogeneous(high, low)
        low == high -> low
        else -> ({x : Double -> (x - low)/(high - low)}.inverseDichotomicSearch(Random.nextDouble()))
    }
}

fun randomHomogeneous() : Double{
    return {x : Double -> x}.inverseDichotomicSearch(Random.nextDouble())
}
