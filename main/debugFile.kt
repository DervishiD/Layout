package main

import utilities.IndexedMapping
import utilities.filteredMapOf
import utilities.indexedMapOf
import utilities.indexedMutableMapOf

private fun IndexedMapping.print(){
    var result = ""
    for(entry : Pair<Any?, Any?> in entries){
        result += "(${entry.first.toString()} | ${entry.second.toString()}) "
    }
    println(result)
}

internal fun debugMapStuff(){
    println("DEBUGGING MAP STUFF =============================")
    println()

    val a = indexedMapOf("" to 0, "1" to 2, "4" to 3.1415926535)
    a.print()

    println()

    val b = indexedMutableMapOf(0 to "", "" to 0, 2.7 to "e", 3.24 to 4.22)
    b.print()
    b[22] = 22
    b.print()
    b[22] = 24
    b.print()
    b.remove(0)
    b.print()
    b.add(1024, true)
    b.print()
    b.putOrReplaceAll(22 to false, 2048 to "hey")
    b.print()
    b.eraseAndAdd(1024, false)
    b.print()

    println()

    val c = filteredMapOf("" to 1, "w" to 2, 3.33 to 3)
    c.print()
    c.add(1, 1)
    c.print()
    c.addKeyClasses(Int::class)
    c.add(1, 1)
    c.print()

    println()
    println("END DEBUG MAP STUFF =============================")
}
