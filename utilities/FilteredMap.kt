package utilities

import kotlin.reflect.KClass

/**
 * A basic implementation of the FilteredMapping interface with basic constructors
 */
open class FilteredMap : IndexedMutableMap, FilteredMapping {

    override var authorizedKeys : MutableSet<KClass<out Any>> = mutableSetOf()
    override var authorizedValues : MutableSet<KClass<out Any>> = mutableSetOf()

    constructor(keysClasses : Collection<KClass<out Any>>, valuesClasses : Collection<KClass<out Any>>){
        authorizedKeys.addAll(keysClasses)
        authorizedValues.addAll(valuesClasses)
    }

    constructor(keysClass : KClass<out Any>, valuesClass : KClass<out Any>){
        authorizedKeys.add(keysClass)
        authorizedValues.add(valuesClass)
    }

    constructor(keysClasses : Collection<KClass<out Any>>, valuesClass : KClass<out Any>){
        authorizedKeys.addAll(keysClasses)
        authorizedValues.add(valuesClass)
    }

    constructor(keysClass : KClass<out Any>, valuesClasses : Collection<KClass<out Any>>){
        authorizedKeys.add(keysClass)
        authorizedValues.addAll(valuesClasses)
    }

    constructor()

}