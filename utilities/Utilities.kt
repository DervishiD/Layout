package utilities

/**
 * Returns a copy of this ArrayList
 */
fun <T> List<T>.copy() : ArrayList<T>{
    val result : ArrayList<T> = ArrayList()
    result.addAll(this)
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
