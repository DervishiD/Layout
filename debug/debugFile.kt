package debug

import llayout4.Action
import llayout4.RealFunction
import llayout4.utilities.*
import usages.probability.inverseDichotomicSearch
import usages.probability.randomHomogeneous
import kotlin.math.floor

fun <T> Collection<T>.print(){
    var result = ""
    for(item in this){
        result += "(${item.toString()}) | "
    }
    println(result)
}

fun IntArray.print(){
    var result = ""
    for(item in this){
        result += "($item) | "
    }
    println(result)
}

fun debugText(){
    val t1 = Text(1)
    println(t1)
    println(t1.asString())
    println("---")
    println()
    for(line in t1.asLines()){
        for(sd in line){
            print(sd.text)
        }
        println()
    }
}

fun debugCollectionCast(){
    val a : ArrayList<MutableList<Int>> = arrayListOf(mutableListOf(1, 2), mutableListOf(3, 4))
    val b = a.toCollectionOf<List<Int>, MutableList<Int>>()
    b.print()
}

fun debugInverseDichotomicSearch(){
    val f : RealFunction = {x -> x*x}
    println(f.inverseDichotomicSearch(3))
}

fun timeTest(method : Action){
    println("-----  TimeTest running  -----")
    val before : Long = System.currentTimeMillis()
    method.invoke()
    val after : Long = System.currentTimeMillis()
    println("-----  ${after - before} milliseconds  -----")
    println("-----  TimeTest finished -----")
}

fun debugHomogeneousDistribution(){
    val array = IntArray(11)
    val iterations = 10000000L // Ten millions
    println("$iterations iterations of homogeneous distribution")
    for(i : Long in 1L..iterations){
        ++array[floor(randomHomogeneous(0.0, 10.0)).toInt()]
    }
    array.print()
}

fun SDIteratorDebug(){
    val sd : StringDisplay = StringDisplay("abcdefghijklmnopqrstuvwxyz")
    for(c : Char in sd){
        println(c)
    }
}

fun MutableList<List<StringDisplay>>.print(){
    for(l : List<StringDisplay> in this){
        l.print()
    }
}
