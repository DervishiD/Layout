package llayout2.utilities

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
