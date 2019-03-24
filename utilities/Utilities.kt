package utilities

import display.DEFAULT_COLOR
import display.DEFAULT_FONT
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import kotlin.reflect.KClass

/**
 * Returns a copy of this ArrayList
 */
fun <T> List<T>.copy() : List<T>{
    val result : MutableList<T> = mutableListOf()
    result.addAll(this)
    return result
}

fun <K, T> Collection<Pair<K, T>>.firsts() : MutableList<K>{
    val result : MutableList<K> = mutableListOf()
    for(pair : Pair<K, T> in this){
        result.add(pair.first)
    }
    return result
}

fun <K, T> Collection<Pair<K, T>>.seconds() : MutableList<T>{
    val result : MutableList<T> = mutableListOf()
    for(pair : Pair<K, T> in this){
        result.add(pair.second)
    }
    return result
}

fun <K, T> Map<K, T>.firsts() : MutableList<K>{
    val result : MutableList<K> = mutableListOf()
    for(key in keys){
        result.add(key)
    }
    return result
}

fun <K, T> Map<K, T>.seconds() : MutableList<T>{
    val result : MutableList<T> = mutableListOf()
    for(value in values){
        result.add(value)
    }
    return result
}

/**
 * Returns an IndexedMap built with the given arguments
 */
fun indexedMapOf(entries : List<Pair<Any?, Any?>>) : IndexedMap = IndexedMap(entries)

/**
 * Returns an IndexedMap built with the given arguments
 */
fun indexedMapOf(vararg entries : Pair<Any?, Any?>) : IndexedMap{
    val map = IndexedMap()
    map.entries.addAll(entries)
    for(entry : Pair<Any?, Any?> in entries){
        map.keys.add(entry.first)
        map.values.add(entry.second)
    }
    map.size = entries.size
    return map
}

/**
 * Returns an IndexedMap built with the given arguments
 */
fun indexedMapOf(keys : List<Any?>, values : List<Any?>) : IndexedMap = IndexedMap(keys, values)

/**
 * Returns an IndexedMap built with the given arguments
 */
fun indexedMapOf(map : Map<Any?, Any?>) : IndexedMap = IndexedMap(map)

/**
 * Returns an IndexedMap built with the given arguments
 */
fun indexedMapOf(map : IndexedMapping) : IndexedMap = IndexedMap(map)

/**
 * Returns an IndexedMap built with the given arguments
 */
fun indexedMutableMapOf(entries : List<Pair<Any?, Any?>>) : IndexedMutableMap = IndexedMutableMap(entries)

/**
 * Returns an IndexedMutableMap built with the given arguments
 */
fun indexedMutableMapOf(vararg entries : Pair<Any?, Any?>) : IndexedMutableMap{
    val map = IndexedMutableMap()
    map.entries.addAll(entries)
    for(entry : Pair<Any?, Any?> in entries){
        map.keys.add(entry.first)
        map.values.add(entry.second)
    }
    map.size = entries.size
    return map
}

/**
 * Returns an IndexedMutableMap built with the given arguments
 */
fun indexedMutableMapOf(keys : List<Any?>, values : List<Any?>) : IndexedMutableMap = IndexedMutableMap(keys, values)

/**
 * Returns an IndexedMutableMap built with the given arguments
 */
fun indexedMutableMapOf(map : Map<Any?, Any?>) : IndexedMutableMap = IndexedMutableMap(map)

/**
 * Returns an IndexedMutableMap built with the given arguments
 */
fun indexedMutableMapOf(map : IndexedMapping) : IndexedMutableMap = IndexedMutableMap(map)

/**
 * Returns a FilteredMap built with the given arguments
 */
fun filteredMapOf(entries : List<Pair<Any?, Any?>>) : FilteredMap {
    val result = FilteredMap()
    for(entry : Pair<Any?, Any?> in entries){
        if(entry.first != null) result.addKeyClasses(entry.first!!::class)
        if(entry.second != null) result.addValueClasses(entry.second!!::class)
        result.add(entry)
    }
    return result
}

/**
 * Returns a FilteredMap built with the given arguments
 */
fun filteredMapOf(vararg entries : Pair<Any?, Any?>) : FilteredMap{
    val result = FilteredMap()
    for(entry : Pair<Any?, Any?> in entries){
        if(entry.first != null) result.addKeyClasses(entry.first!!::class)
        if(entry.second != null) result.addValueClasses(entry.second!!::class)
        result.add(entry)
    }
    return result
}

/**
 * Returns a FilteredMap built with the given arguments
 */
fun filteredMapOf(keys : List<Any?>, values : List<Any?>) : FilteredMap{
    if(keys.size == values.size){
        val result = FilteredMap()
        for(i : Int in 0 until keys.size){
            if(keys[i] != null) result.addKeyClasses(keys[i]!!::class)
            if(values[i] != null) result.addValueClasses(values[i]!!::class)
            result.add(keys[i], values[i])
        }
        return result
    }else throw IllegalArgumentException()
}

/**
 * Returns a FilteredMap built with the given arguments
 */
fun filteredMapOf(map : Map<Any?, Any?>) : FilteredMap{
    val result = FilteredMap()
    for(entry in map){
        if(entry.key != null) result.addKeyClasses(entry.key!!::class)
        if(entry.value != null) result.addValueClasses(entry.value!!::class)
        result.add(entry.key, entry.value)
    }
    return result
}

/**
 * Returns a FilteredMap built with the given arguments
 */
fun filteredMapOf(map : IndexedMapping) : FilteredMap{
    val result = FilteredMap()
    for(entry : Pair<Any?, Any?> in map){
        if(entry.first != null) result.addKeyClasses(entry.first!!::class)
        if(entry.second != null) result.addValueClasses(entry.second!!::class)
        result.add(entry)
    }
    return result
}

/**
 * Produces the total text of a list of StringDisplays
 */
fun Collection<StringDisplay>.collapse() : String{
    var result : String = ""
    for(s : StringDisplay in this){
        result += s.text
    }
    return result
}

/**
 * Produces a list of the represented lines
 */
fun Collection<StringDisplay>.toLinesList() : MutableList<List<StringDisplay>>{
    val result : MutableList<List<StringDisplay>> = mutableListOf()
    var currentLine : ArrayList<StringDisplay> = ArrayList()
    for(s : StringDisplay in this){
        if(s.contains("\n")){
            val splitted : List<StringDisplay> = s.split("\n")
            currentLine.add(splitted[0])
            result.add(currentLine)
            for(i : Int in 1 until splitted.size - 1){
                result.add(arrayListOf(splitted[i]))
            }
            currentLine = arrayListOf(splitted.last())
        }else{
            currentLine.add(s)
        }
    }
    result.add(currentLine)
    return result
}

/**
 * Computes the height of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.lineHeight(g : Graphics) : Int{
    var maxAscent : Int = 0
    var maxDescent : Int = 0
    var fm : FontMetrics
    for(s : StringDisplay in this){
        fm = g.getFontMetrics(s.font)
        if(fm.maxAscent > maxAscent){
            maxAscent = fm.maxAscent
        }
        if(fm.maxDescent > maxDescent){
            maxDescent = fm.maxDescent
        }
    }
    return maxAscent + maxDescent
}

/**
 * Computes the length of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.lineLength(g : Graphics) : Int{
    var result : Int = 0
    for(s : StringDisplay in this){
        result +=  g.getFontMetrics(s.font).stringWidth(s.text)
    }
    return result
}

/**
 * Computes the ascent of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.ascent(g : Graphics) : Int{
    var maxAscent : Int = 0
    var fm : FontMetrics
    for(s : StringDisplay in this){
        fm = g.getFontMetrics(s.font)
        if(fm.maxAscent > maxAscent){
            maxAscent = fm.maxAscent
        }
    }
    return maxAscent
}

/**
 * Computes the descent of this as a line of text in the given Graphics context
 */
infix fun Collection<StringDisplay>.descent(g : Graphics) : Int{
    var maxDescent : Int = 0
    var fm : FontMetrics
    for(s : StringDisplay in this){
        fm = g.getFontMetrics(s.font)
        if(fm.maxDescent > maxDescent){
            maxDescent = fm.maxDescent
        }
    }
    return maxDescent
}

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(font : Font, color : Color) : StringDisplay =
    StringDisplay(this, font, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(color : Color, font : Font) : StringDisplay =
    StringDisplay(this, font, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(font : Font) : StringDisplay = StringDisplay(this, font)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay(color : Color) : StringDisplay = StringDisplay(this, color)

/**
 * Returns a StringDisplay version of this String
 */
fun String.toStringDisplay() : StringDisplay = StringDisplay(this)

fun List<String>.toStringDisplays(font : Font, color : Color) : MutableList<StringDisplay>{
    val result : MutableList<StringDisplay> = mutableListOf()
    for(s in this){
        result.add(s.toStringDisplay(font, color))
    }
    return result
}

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays(color : Color, font : Font) : MutableList<StringDisplay> = toStringDisplays(font, color)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays(color : Color) : MutableList<StringDisplay> = toStringDisplays(DEFAULT_FONT, color)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays(font : Font) : MutableList<StringDisplay> = toStringDisplays(font, DEFAULT_COLOR)

/**
 * Converts a List of Strings to a List of StringDisplays
 */
fun List<String>.toStringDisplays() : MutableList<StringDisplay> = toStringDisplays(DEFAULT_FONT, DEFAULT_COLOR)

fun <V, T : V> Collection<T>.toCollectionOf() : Collection<V>{
    val result : MutableCollection<V> = mutableListOf()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <V, T : V> Collection<T>.toMutableCollectionOf() : MutableCollection<V>{
    val result : MutableCollection<V> = mutableListOf()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <V, T : V> Collection<T>.toSetOf() : Set<V>{
    val result : MutableSet<V> = mutableSetOf()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <V, T : V> Collection<T>.toMutableSetOf() : MutableSet<V>{
    val result : MutableSet<V> = mutableSetOf()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <V, T : V> Collection<T>.toHashSetOf() : HashSet<V>{
    val result : HashSet<V> = HashSet()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <V, T : V> Collection<T>.toListOf() : List<V>{
    val result : MutableList<V> = mutableListOf()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <V, T : V> Collection<T>.toMutableListOf() : MutableList<V>{
    val result : MutableList<V> = mutableListOf()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <V, T : V> Collection<T>.toArrayListOf() : ArrayList<V>{
    val result : ArrayList<V> = ArrayList()
    for(item : T in this){
        result.add(item as V)
    }
    return result
}

fun <T> Collection<T>.toCollection() : Collection<T>{
    val result : MutableCollection<T> = mutableListOf()
    result.addAll(this)
    return result
}

fun <T> Collection<T>.toMutableCollection() : MutableCollection<T>{
    val result : MutableCollection<T> = mutableListOf()
    result.addAll(this)
    return result
}

fun <T> Collection<T>.toSet() : Set<T>{
    val result : MutableSet<T> = mutableSetOf()
    result.addAll(this)
    return result
}

fun <T> Collection<T>.toMutableSet() : MutableSet<T>{
    val result : MutableSet<T> = mutableSetOf()
    result.addAll(this)
    return result
}

fun <T> Collection<T>.toHashSet() : HashSet<T>{
    val result : HashSet<T> = HashSet()
    result.addAll(this)
    return result
}

fun <T> Collection<T>.toList() : List<T>{
    val result : MutableList<T> = mutableListOf()
    result.addAll(this)
    return result
}

fun <T> Collection<T>.toMutableList() : MutableList<T>{
    val result : MutableList<T> = mutableListOf()
    result.addAll(this)
    return result
}

fun <T> Collection<T>.toArrayList() : ArrayList<T>{
    val result : ArrayList<T> = ArrayList()
    result.addAll(this)
    return result
}
