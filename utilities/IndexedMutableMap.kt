package utilities

/**
 * A basic implementation of the IndexedMutableMapping interface with basic constructors
 */
open class IndexedMutableMap : IndexedMap, IndexedMutableMapping {

    constructor(entries : List<Pair<Any?, Any?>>) : super(entries)
    constructor(vararg entries : Pair<Any?, Any?>) : super(*entries)
    constructor(keys : List<Any?>, values : List<Any?>) : super(keys, values)
    constructor(map : Map<Any?, Any?>) : super(map)
    constructor(map : IndexedMapping) : super(map)

}