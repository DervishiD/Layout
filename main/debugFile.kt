package main

import utilities.*

private fun <T> Collection<T>.print(){
    var result = ""
    for(item in this){
        result += "(${item.toString()}) | "
    }
    println(result)
}

internal fun debugText(){
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

internal fun debugCollectionCast(){
    val a : ArrayList<MutableList<Int>> = arrayListOf(mutableListOf(1, 2), mutableListOf(3, 4))
    val b = a.toCollectionOf<List<Int>, MutableList<Int>>()
    b.print()
}
