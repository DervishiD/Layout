package utilities

import kotlin.IllegalArgumentException

/**
 * An interface representing a mutable indexed map (Any?, Any?)
 */
interface IndexedMutableMapping : IndexedMapping {

    /**
     * The set (map[key] = value) operator, implementing the putOrReplace method
     */
    operator fun set(key : Any?, value : Any?){
        putOrReplace(key, value)
    }

    /**
     * Adds the pair at the end of the list if it's not already present.
     * @return true if the pair is added
     */
    fun add(key : Any?, value : Any?) : Boolean {
        if(!containsKey(key)){
            keys.add(key)
            values.add(value)
            entries.add(Pair(key, value))
            size++
            return true
        }
        return false
    }

    /**
     * Adds the pair at the end of the list if it's not already present.
     * @return true if the pair is added
     */
    infix fun add(entry : Pair<Any?, Any?>) : Boolean = add(entry.first, entry.second)

    /**
     * Adds the pairs at the end of the list if they're not already present.
     * @return true if the pairs are all added
     */
    infix fun addAll(entries : Collection<Pair<Any?, Any?>>){
        for(entry : Pair<Any?, Any?> in entries){
            add(entry)
        }
    }

    /**
     * Adds the pairs at the end of the list if they're not already present.
     * @return true if the pairs are all added
     */
    fun addAll(keys : List<Any?>, values : List<Any?>){
        if(keys.size == values.size){
            for(i : Int in 0 until keys.size){
                add(keys[i], values[i])
            }
        }else throw IllegalArgumentException()
    }

    /**
     * Adds the pairs at the end of the list if they're not already present.
     * @return true if the pairs are all added
     */
    fun addAll(vararg pairs : Pair<Any?, Any?>){
        for(pair : Pair<Any?, Any?> in pairs){
            add(pair)
        }
    }

    /**
     * Adds the pairs at the end of the list if they're not already present.
     * @return true if the pairs are all added
     */
    fun addAll(pairs: Map<Any?, Any?>){
        for(pair in pairs){
            add(pair.key, pair.value)
        }
    }

    /**
     * Adds the pairs at the end of the list if they're not already present.
     * @return true if the pairs are all added
     */
    fun addAll(pairs : IndexedMap){
        for(pair in pairs){
            add(pair)
        }
    }

    /*
    fun addAt(index : Int, key : Any?, value: Any?) : Boolean{
        if(!containsKey(key) && index in 0 until size){
            keys.add(index, key)
            values.add(index, value)
            entries.add(index, Pair(key, value))
            size++
            return true
        }
        return false
    }

    fun addAt(index : Int, entry : Pair<Any?, Any?>) : Boolean{
        if(!containsKey(entry.first) && index in 0 until size){
            keys.add(index, entry.first)
            values.add(index, entry.second)
            entries.add(index, Pair(entry.first, entry.second))
            size++
            return true
        }
        return false
    }

    fun addAt(entry : Pair<Any?, Any?>, index : Int) : Boolean = addAt(index, entry)

    fun addAllAt(index : Int, entries : Collection<Pair<Any?, Any?>>) : Boolean{
        var successes = 0
        if(index in 0 until size){
            for(entry : Pair<Any?, Any?> in entries){
                if(!containsKey(entry.first)){
                    keys.add(index + successes, entry.first)
                    values.add(index + successes, entry.second)
                    this.entries.add(index + successes, Pair(entry.first, entry.second))
                    successes++
                    size++
                }
            }
        }
        return successes == entries.size
    }

    fun addAllAt(entries : Collection<Pair<Any?, Any?>>, index : Int) : Boolean = addAllAt(index, entries)

    fun addAllAt(index : Int, keys : List<Any?>, values : List<Any?>) : Boolean{
        if(keys.size == values.size){
            var successes = 0
            if(index in 0 until size){
                for(i : Int in 0 until keys.size){
                    if(!containsKey(keys[i])){
                        this.keys.add(index + successes, keys[i])
                        this.values.add(index + successes, values[i])
                        entries.add(index + successes, Pair(keys[i], values[i]))
                        ++successes
                        ++size
                    }
                }
            }
            return successes == keys.size
        }else throw IllegalArgumentException()
    }

    fun addAllAt(keys : List<Any?>, values : List<Any?>, index : Int) : Boolean = addAllAt(index, keys, values)

    fun addAllAt(index : Int, vararg entries : Pair<Any?, Any?>) : Boolean{
        var successes = 0
        if(index in 0 until size){
            for(entry : Pair<Any?, Any?> in entries){
                if(!containsKey(entry.first)){
                    keys.add(index + successes, entry.first)
                    values.add(index + successes, entry.second)
                    this.entries.add(index + successes, Pair(entry.first, entry.second))
                    successes++
                    size++
                }
            }
        }
        return successes == entries.size
    }

    fun addAllAt(vararg entries : Pair<Any?, Any?>, index : Int) : Boolean{
        var successes = 0
        if(index in 0 until size){
            for(entry : Pair<Any?, Any?> in entries){
                if(!containsKey(entry.first)){
                    keys.add(index + successes, entry.first)
                    values.add(index + successes, entry.second)
                    this.entries.add(index + successes, Pair(entry.first, entry.second))
                    successes++
                    size++
                }
            }
        }
        return successes == entries.size
    }

    fun addAllAt(index : Int, map : Map<Any?, Any?>) : Boolean{
        var successes = 0
        if(index in 0 until size){
            for(entry in map){
                if(!containsKey(entry.key)){
                    keys.add(index + successes, entry.key)
                    values.add(index + successes, entry.value)
                    entries.add(index + successes, Pair(entry.key, entry.value))
                    successes++
                    size++
                }
            }
        }
        return successes == map.size
    }

    fun addAllAt(map : Map<Any?, Any?>, index : Int) : Boolean = addAllAt(index, map)

    fun addAllAt(index : Int, map : IndexedMapping) : Boolean{
        var successes = 0
        if(index in 0 until size){
            for(entry in map){
                if(!containsKey(entry.first)){
                    keys.add(index + successes, entry.first)
                    values.add(index + successes, entry.second)
                    entries.add(index + successes, Pair(entry.first, entry.second))
                    successes++
                    size++
                }
            }
        }
        return successes == map.size
    }

    fun addAllAt(map : IndexedMapping, index : Int) : Boolean = addAllAt(index, map)
    */

    /**
     * If the map contains the given key, replaces the associated value with the given one.
     * @return true if the element has been replaced.
     */
    fun replace(key : Any?, value : Any?) : Boolean{
        val keyIndex : Int = indexOfKey(key)
        if(keyIndex != -1){
            values[keyIndex] = value
            entries[keyIndex] = Pair(key, value)
            return true
        }
        return false
    }

    /**
     * If the map contains the given key, replaces the associated value with the given one.
     * @return true if the element has been replaced.
     */
    infix fun replace(pair : Pair<Any?, Any?>) : Boolean = replace(pair.first, pair.second)

    /**
     * For each given pair, if the map contains the key, replaces its associated value with the given one
     * @return true if every element could be replaced
     */
    infix fun replaceAll(entries : Collection<Pair<Any?, Any?>>) : Boolean{
        var success = true
        for(entry : Pair<Any?, Any?> in entries){
            if(!replace(entry)) success = false
        }
        return success
    }

    /**
     * For each given pair, if the map contains the key, replaces its associated value with the given one
     * @return true if every element could be replaced
     */
    fun replaceAll(keys : List<Any?>, values : List<Any?>) : Boolean{
        if(keys.size == values.size){
            var success = true
            for(i : Int in 0 until keys.size){
                if(!replace(keys[i], values[i])) success = false
            }
            return success
        }else throw IllegalArgumentException()
    }

    /**
     * For each given pair, if the map contains the key, replaces its associated value with the given one
     * @return true if every element could be replaced
     */
    fun replaceAll(vararg pairs : Pair<Any?, Any?>) : Boolean{
        var success = true
        for(pair : Pair<Any?, Any?> in pairs){
            if(!replace(pair)) success = false
        }
        return success
    }

    /**
     * For each given pair, if the map contains the key, replaces its associated value with the given one
     * @return true if every element could be replaced
     */
    fun replaceAll(pairs: Map<Any?, Any?>) : Boolean{
        var success = true
        for(pair in pairs){
            if(!replace(pair.key, pair.value)) success = false
        }
        return success
    }

    /**
     * For each given pair, if the map contains the key, replaces its associated value with the given one
     * @return true if every element could be replaced
     */
    fun replaceAll(pairs : IndexedMap) : Boolean{
        var success = true
        for(pair in pairs){
            if(!replace(pair)) success = false
        }
        return success
    }

    /*
    fun replaceAt(index : Int, key : Any?, value : Any?) : Boolean{
        if(index in 0 until size){
            keys[index] = key
            values[index] = value
            entries[index] = Pair(key, value)
            return true
        }
        return false
    }

    fun replaceAt(index : Int, entry : Pair<Any?, Any?>) : Boolean{
        if(index in 0 until size){
            keys[index] = entry.first
            values[index] = entry.second
            entries[index] = entry
            return true
        }
        return false
    }

    fun replaceAt(entry : Pair<Any?, Any?>, index : Int) : Boolean = replaceAt(index, entry)

    fun replaceAllAt(index : Int, entries : List<Pair<Any?, Any?>>) : Boolean{
        if(index in 0 until size && index + entries.size <= size){
            for(i : Int in index until index + entries.size){
                keys[i] = entries[i - index].first
                values[i] = entries[i - index].second
                this.entries[i] = entries[i - index]
            }
            return true
        }
        return false
    }

    fun replaceAllAt(entries : List<Pair<Any?, Any?>>, index : Int) : Boolean = replaceAllAt(index, entries)

    fun replaceAllAt(index : Int, keys : List<Any?>, values : List<Any?>) : Boolean{
        if(index in 0 until size && keys.size == values.size && index + keys.size <= size){
            for(i : Int in 0 until keys.size){
                this.keys[i + index] = keys[i]
                this.values[i + index] = values[i]
                entries[i + index] = Pair(keys[i], values[i])
            }
            return true
        }
        return false
    }

    fun replaceAllAt(keys : List<Any?>, values : List<Any?>, index : Int) : Boolean = replaceAllAt(index, keys, values)

    fun replaceAllAt(index : Int, vararg entries : Pair<Any?, Any?>) : Boolean{
        if(index in 0 until size && index + entries.size <= size){
            for(i : Int in index until index + entries.size){
                keys[i] = entries[i - index].first
                values[i] = entries[i - index].second
                this.entries[i] = entries[i - index]
            }
            return true
        }
        return false
    }

    fun replaceAllAt(vararg entries : Pair<Any?, Any?>, index : Int) : Boolean{
        if(index in 0 until size && index + entries.size <= size){
            for(i : Int in index until index + entries.size){
                keys[i] = entries[i - index].first
                values[i] = entries[i - index].second
                this.entries[i] = entries[i - index]
            }
            return true
        }
        return false
    }

    fun replaceAllAt(index : Int, map : Map<Any?, Any?>) : Boolean{
        if(index in 0 until size && index + entries.size <= size){
            var counter = 0
            for(entry in map){
                keys[index + counter] = entry.key
                values[index + counter] = entry.value
                entries[index + counter] = Pair(entry.key, entry.value)
                counter++
            }
            return true
        }
        return false
    }

    fun replaceAllAt(map : Map<Any?, Any?>, index : Int) : Boolean = replaceAllAt(index, map)

    fun replaceAllAt(index : Int, map : IndexedMapping) : Boolean{
        if(index in 0 until size && index + entries.size <= size){
            var counter = 0
            for(entry in map){
                keys[index + counter] = entry.first
                values[index + counter] = entry.second
                entries[index + counter] = Pair(entry.first, entry.second)
                counter++
            }
            return true
        }
        return false
    }

    fun replaceAllAt(map : IndexedMapping, index : Int) : Boolean = replaceAllAt(index, map)
    */

    /**
     * If the map doesn't contain the given key, appends the key-value pair at the end of it.
     * Otherwise replaces the associated value with the given one.
     * @return true if the element has been replaced, false if it has been added
     */
    fun putOrReplace(key : Any?, value : Any?) : Boolean{
        val keyIndex : Int = indexOfKey(key)
        return if(keyIndex == -1){
            keys.add(key)
            values.add(value)
            entries.add(Pair(key, value))
            size++
            false
        }else{
            values[keyIndex] = value
            entries[keyIndex] = Pair(key, value)
            true
        }
    }

    /**
     * If the map doesn't contain the given key, appends the key-value pair at the end of it.
     * Otherwise replaces the associated value with the given one.
     * @return true if the element has been replaced, false if it has been added
     */
    infix fun putOrReplace(pair : Pair<Any?, Any?>) = putOrReplace(pair.first, pair.second)

    /**
     * For each entry, if the map doesn't contain the given key, appends the key-value pair at the end of it.
     * Otherwise replaces the associated value with the given one.
     * @return true if every element has been replaced, false otherwise
     */
    infix fun putOrReplaceAll(entries : Collection<Pair<Any?, Any?>>) : Boolean{
        var replacement = true
        for(entry : Pair<Any?, Any?> in entries){
            if(!putOrReplace(entry)) replacement = false
        }
        return replacement
    }

    /**
     * For each entry, if the map doesn't contain the given key, appends the key-value pair at the end of it.
     * Otherwise replaces the associated value with the given one.
     * @return true if every element has been replaced, false otherwise
     */
    fun putOrReplaceAll(keys : List<Any?>, values : List<Any?>) : Boolean{
        if(keys.size == values.size){
            var replacement = true
            for(i : Int in 0 until keys.size){
                if(!putOrReplace(keys[i], values[i])) replacement = false
            }
            return replacement
        }else throw IllegalArgumentException()
    }

    /**
     * For each entry, if the map doesn't contain the given key, appends the key-value pair at the end of it.
     * Otherwise replaces the associated value with the given one.
     * @return true if every element has been replaced, false otherwise
     */
    fun putOrReplaceAll(vararg pairs : Pair<Any?, Any?>) : Boolean{
        var replacement = true
        for(pair : Pair<Any?, Any?> in pairs){
            if(!putOrReplace(pair)) replacement = false
        }
        return replacement
    }

    /**
     * For each entry, if the map doesn't contain the given key, appends the key-value pair at the end of it.
     * Otherwise replaces the associated value with the given one.
     * @return true if every element has been replaced, false otherwise
     */
    fun putOrReplaceAll(pairs: Map<Any?, Any?>) : Boolean{
        var replacement = true
        for(pair in pairs){
            if(!putOrReplace(pair.key, pair.value)) replacement = false
        }
        return replacement
    }

    /**
     * For each entry, if the map doesn't contain the given key, appends the key-value pair at the end of it.
     * Otherwise replaces the associated value with the given one.
     * @return true if every element has been replaced, false otherwise
     */
    fun putOrReplaceAll(pairs : IndexedMap) : Boolean{
        var replacement = true
        for(pair in pairs){
            if(!putOrReplace(pair)) replacement = false
        }
        return replacement
    }
/*
    fun putAtOrReplace(index : Int, key : Any?, value : Any?) : Boolean{
        if(index in 0 until size){
            val keyIndex : Int = indexOfKey(key)
            return if(keyIndex == -1){
                keys.add(index, key)
                values.add(index, value)
                entries.add(index, Pair(key, value))
                size++
                false
            }else{
                values[keyIndex] = value
                entries[keyIndex] = Pair(key, value)
                true
            }
        }else throw IndexOutOfBoundsException()
    }

    fun putAtOrReplace(index : Int, entry : Pair<Any?, Any?>) : Boolean = putAtOrReplace(index, entry.first, entry.second)

    fun putAtOrReplace(entry : Pair<Any?, Any?>, index : Int) : Boolean = putAtOrReplace(index, entry.first, entry.second)
    */

    /**
     * If the key is already present in the map, erases the corresponding data. Then adds
     * the key-value pair at the end of the map.
     */
    fun eraseAndAdd(key : Any?, value : Any?){
        val keyIndex : Int = indexOfKey(key)
        if(keyIndex != -1){
            keys.removeAt(keyIndex)
            values.removeAt(keyIndex)
            entries.removeAt(keyIndex)
            size--
        }
        keys.add(key)
        values.add(value)
        entries.add(Pair(key, value))
        size++
    }

    /**
     * If the key is already present in the map, erases the corresponding data. Then adds
     * the key-value pair at the end of the map.
     */
    fun eraseAndAdd(entry : Pair<Any?, Any?>) = eraseAndAdd(entry.first, entry.second)

    /**
     * For each given key-value pair, if the key is already present in the map, erases the corresponding data.
     * Then adds the key-value pair at the end of the map.
     */
    infix fun eraseAndAddAll(entries : Collection<Pair<Any?, Any?>>){
        for(entry : Pair<Any?, Any?> in entries){
            eraseAndAdd(entry)
        }
    }

    /**
     * For each given key-value pair, if the key is already present in the map, erases the corresponding data.
     * Then adds the key-value pair at the end of the map.
     */
    fun eraseAndAddAll(keys : List<Any?>, values : List<Any?>){
        if(keys.size == values.size){
            for(i : Int in 0 until keys.size){
                eraseAndAdd(keys[i], values[i])
            }
        }else throw IllegalArgumentException()
    }

    /**
     * For each given key-value pair, if the key is already present in the map, erases the corresponding data.
     * Then adds the key-value pair at the end of the map.
     */
    fun eraseAndAddAll(vararg pairs : Pair<Any?, Any?>){
        for(pair : Pair<Any?, Any?> in pairs){
            eraseAndAdd(pair)
        }
    }

    /**
     * For each given key-value pair, if the key is already present in the map, erases the corresponding data.
     * Then adds the key-value pair at the end of the map.
     */
    fun eraseAndAddAll(pairs: Map<Any?, Any?>){
        for(pair in pairs){
            eraseAndAdd(pair.key, pair.value)
        }
    }

    /**
     * For each given key-value pair, if the key is already present in the map, erases the corresponding data.
     * Then adds the key-value pair at the end of the map.
     */
    fun eraseAndAddAll(pairs : IndexedMap){
        for(pair in pairs){
            eraseAndAdd(pair)
        }
    }

    /**
     * Removes the key-value pair associated with the given key, if it exists.
     * @return true if the pair could be removed, false otherwise
     */
    infix fun remove(key : Any?) : Boolean{
        val keyIndex = indexOfKey(key)
        return if(keyIndex == -1){
            false
        }else{
            keys.removeAt(keyIndex)
            values.removeAt(keyIndex)
            entries.removeAt(keyIndex)
            --size
            true
        }
    }

    /**
     * For each of the given keys, removes the key-value pair associated with the given key, if it exists.
     * @return true if each pair could be removed, false otherwise
     */
    infix fun removeAll(keys : Collection<Any?>) : Boolean{
        var success = true
        for(key : Any? in keys){
            if(!remove(key)) success = false
        }
        return success
    }

    /**
     * For each of the given keys, removes the key-value pair associated with the given key, if it exists.
     * @return true if each pair could be removed, false otherwise
     */
    fun removeAll(vararg keys : Any?) : Boolean{
        var success = true
        for(key : Any? in keys){
            if(!remove(key)) success = false
        }
        return success
    }

    /**
     * Removes the key-value pair at the given index.
     * @throws IndexOutOfBoundsException if the given index is invalid.
     */
    infix fun removeAt(index : Int){
        if(index in 0 until size){
            keys.removeAt(index)
            values.removeAt(index)
            entries.removeAt(index)
            size--
        }else throw IndexOutOfBoundsException()
    }

}