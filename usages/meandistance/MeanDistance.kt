package usages.meandistance

import llayout1.frame.LApplication
import kotlin.math.sqrt
import kotlin.random.Random

private const val N : Int = 5000

val meanDistanceApplication : LApplication = LApplication { Simulation.simulateForN(N) }

class Simulation(n : Int){

    companion object{
        private const val iterationsPerM : Int = 10000
        private const val maxM : Int = 1000
        infix fun simulateForN(n : Int){
            val s = Simulation(n)
            for(m : Int in 1..maxM){
                println()
                println("---  SIMULATION FOR N = $n, M = $m  ---")
                println("MEAN DISTANCE IS ${s.simulateForM(m)}")
                println("       ROOT M IS ${sqrt(m)}")
                println()
            }
        }
    }

    private var position : Array<Int> = Array(n) {0}

    private var distances : Array<Double> = Array(iterationsPerM){0.0}

    infix fun simulateForM(m : Int) : Double{
        for(i : Int in 0 until iterationsPerM){
            randomPosition(m)
            distances[i] = position.norm()
        }
        return distances.mean()
    }

    private infix fun randomPosition(m : Int){
        resetPosition()
        for(i : Int in 1..m){
            randomStep()
        }
    }

    private fun randomStep(){
        val coordinate : Int = Random.nextInt(0, position.size)
        val step : Int = if(Random.nextInt(0, 2) == 0) 1 else -1
        position[coordinate] += step
    }

    private fun resetPosition(){
        for(i : Int in 0 until position.size){
            position[i] = 0
        }
    }

}

fun Array<Int>.norm2() : Double{
    var result = 0.0
    for(i : Int in this){
        result += i * i
    }
    return result
}

fun Array<Int>.norm() : Double = sqrt(norm2())

fun Array<Double>.mean() : Double{
    return if(size == 0) 0.0 else{
        var result = 0.0
        for(i : Double in this){
            result += i
        }
        result / size
    }
}

fun sqrt(i : Int) : Double = sqrt(i.toDouble())
