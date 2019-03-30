package utilities

/**
 * An interface implemented by objects that act like an indexed immutable Map with
 * Any? key and Any? value
 */
interface IndexedMapping : Iterable<Pair<Any?, Any?>> {

    /**
     * The keys of the Map
     */
    val keys : ArrayList<Any?>
    /**
     * The values of the Map
     */
    val values : ArrayList<Any?>
    /**
     * The entries of the Map
     */
    val entries : ArrayList<Pair<Any?, Any?>>
    /**
     * The number of key-value pairs present in this Map
     */
    var size : Int

    /**
     * Returns the value associated with the given key, or null if it doesn't exist
     */
    operator fun get(key : Any?) : Any?{
        val index : Int = keys.indexOf(key)
        return if(index < size && index != -1) values[index] else null
    }

    /**
     * Returns the value associated with the given key if it exists, or the given default
     * value otherwise
     */
    fun getOrDefault(key : Any?, defaultValue : Any?) : Any? = this[key] ?: defaultValue

    /**
     * Returns the key associated with the given index
     */
    infix fun keyAt(index : Int) : Any?{
        if(index < 0 || index >= size) throw IndexOutOfBoundsException() else return keys[index]
    }

    /**
     * Returns the value associated with the given index
     */
    infix fun valueAt(index : Int) : Any?{
        if(index < 0 || index >= size) throw IndexOutOfBoundsException() else return values[index]
    }

    /**
     * Returns the key-value pair associated with the given index
     */
    infix fun entryAt(index : Int) : Pair<Any?, Any?>{
        if(index < 0 || index >= size) throw IndexOutOfBoundsException() else return entries[index]
    }

    /**
     * Returns true if this Map is empty
     */
    fun isEmpty() : Boolean = size == 0

    /**
     * Returns true if the map is not empty
     */
    fun isNotEmpty() : Boolean = size != 0

    /**
     * Return true is the map contains the given value
     */
    operator fun contains(value : Any?) : Boolean = value in values

    /**
     * Return true is the map contains the given key
     */
    infix fun containsKey(key : Any?) : Boolean = key in keys

    /**
     * Return true is the map contains the given value
     */
    infix fun containsValue(value : Any?) : Boolean = value in values

    /**
     * Return a list of the entries of this Map
     */
    fun toList() : List<Pair<Any?, Any?>> = entries.copy()

    /**
     * Returns the index of the given key, or -1 if it doesn't exist
     */
    infix fun indexOfKey(key : Any?) : Int = keys.indexOf(key)

    /**
     * Returns the index of the given key, or -1 if it doesn't exist
     */
    infix fun indexOf(key : Any?) : Int = indexOfKey(key)

    /**
     * Returns the index of the given value, or -1 if it doesn't exist
     */
    infix fun indexOfValue(value : Any?) : Int = values.indexOf(value)

    /**
     * Returns the index of the given key-value pair, or -1 if it doesn't exist
     */
    infix fun indexOfEntry(entry : Pair<Any?, Any?>) : Int = entries.indexOf(entry)

    /**
     * Returns the index of the given key-value pair, or -1 if it doesn't exist
     */
    fun indexOfEntry(key : Any?, value : Any?) : Int = indexOfEntry(Pair(key, value))

    /**
     * Clears the map
     */
    fun clear(){
        size = 0
        keys.clear()
        values.clear()
        entries.clear()
    }

    override fun iterator(): Iterator<Pair<Any?, Any?>> = MapIterator(this)

    /**
     * An implementation of the Iterator interface, used to iterate on this Map
     */
    private class MapIterator(private val iterable: IndexedMapping) : Iterator<Pair<Any?, Any?>>{

        /**
         * The current index of the iterator
         */
        private var index : Int = -1
        /**
         * The size of the list on which it iterates
         */
        private val size : Int = iterable.size

        override fun hasNext(): Boolean = (index + 1 < size)

        override fun next(): Pair<Any?, Any?> {
            if(hasNext()){
                ++index
                return iterable.entries[index]
            }else throw NoSuchElementException()
        }

    }

}