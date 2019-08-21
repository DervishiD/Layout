package main

import llayout.utilities.numericaloptimizer.NumericaalOptimizer
import kotlin.math.cos

internal fun main(){
    println("Do something")
    val result = NumericaalOptimizer.findMaximum({ x : DoubleArray -> cos(x[0]) * x[1] - x[2] }, doubleArrayOf(0.0, 0.0, 0.0), 0.001, 100000L)
    println("${result[0]}, ${result[1]}, ${result[2]}")
}