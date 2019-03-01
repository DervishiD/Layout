package utilities

import kotlin.reflect.KClass

/**
 * Returns a copy of this ArrayList
 */
fun <T> MutableList<T>.copy() : ArrayList<T>{
    val result : ArrayList<T> = ArrayList()
    result.addAll(this)
    return result
}

/**
 * Builds a GeneralizedMap like a standard Map
 */
fun <T> generalizedMapOf(vararg pairs : Pair<Any, T>) : GeneralizedMap<T>{
    val classes : MutableSet<KClass<out Any>> = mutableSetOf()
    for(pair : Pair<Any, T> in pairs){
        classes.add((pair.first)::class)
    }
    val result : GeneralizedMap<T> = GeneralizedMap(classes)
    for(pair : Pair<Any, T> in pairs){
        result[pair.first] = pair.second
    }
    return result
}
