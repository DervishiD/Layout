package utilities

import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

/**
 * A generalization of the HashMap class that allows it to support a given number of keys classes
 */
class GeneralizedMap<T> : HashMap<Any?, T>{

    /**
     * The authorized classes for the keys
     */
    private val authorizedClasses : HashSet<KClass<out Any>> = HashSet()

    constructor(vararg classes : KClass<out Any>){
        if(classes.isEmpty()) throw IllegalArgumentException("No classes were given to this map's keys")
        authorizedClasses.addAll(classes)
    }

    constructor(classes : Collection<KClass<out Any>>){
        if(classes.isEmpty()) throw IllegalArgumentException("No classes were given to this map's keys")
        authorizedClasses.addAll(classes)
    }

    override fun put(key : Any?, value : T) : T?{
        if(keyIsValid(key)){
            return super.put(key, value)
        }else throw IllegalArgumentException("Invalid key ${key.toString()}")
    }

    /**
     * Tests if the key can be inserted in the map
     */
    private fun keyIsValid(key : Any?) : Boolean{
        for(c : KClass<out Any> in authorizedClasses){
            if(c.isInstance(key)){
                return true
            }
        }
        return false
    }

}