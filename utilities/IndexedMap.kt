package utilities

/**
 * A simple implementation of the IndexedMapping interface, with basic constructors.
 */
open class IndexedMap : IndexedMapping{

    override val keys: ArrayList<Any?> = ArrayList()
    override val values: ArrayList<Any?> = ArrayList()
    override val entries: ArrayList<Pair<Any?, Any?>> = ArrayList()
    override var size: Int = 0

    constructor(entries : List<Pair<Any?, Any?>>){
        this.entries.addAll(entries)
        for(entry : Pair<Any?, Any?> in entries){
            keys.add(entry.first)
            values.add(entry.second)
        }
        size = entries.size
    }

    constructor(vararg entries : Pair<Any?, Any?>){
        this.entries.addAll(entries)
        for(entry : Pair<Any?, Any?> in entries){
            keys.add(entry.first)
            values.add(entry.second)
        }
        size = entries.size
    }

    constructor(keys : List<Any?>, values : List<Any?>){
        if(keys.size == values.size){
            for(i in 0 until keys.size){
                this.keys.add(keys[i])
                this.values.add(values[i])
                this.entries.add(Pair(keys[i], values[i]))
            }
            size = keys.size
        }else throw IllegalArgumentException("Illegal number of arguments in the lists. " +
                "keys : ${keys.size}, values : ${values.size}.")
    }

    constructor(map : Map<Any?, Any?>){
        for(entry in map){
            keys.add(entry.key)
            values.add(entry.value)
            entries.add(Pair(entry.key, entry.value))
        }
        size = entries.size
    }

    constructor(map : IndexedMapping){
        for(entry in map){
            keys.add(entry.first)
            values.add(entry.second)
            entries.add(Pair(entry.first, entry.second))
        }
        size = entries.size
    }

}