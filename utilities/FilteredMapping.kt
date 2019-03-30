package utilities

import kotlin.reflect.KClass

/**
 * An extension of the MutableMap Interface that allows the implementer to limit the
 * keys and values classes, e.g. create a map that takes Ints and Doubles as keys and
 * Strings, Booleans and Colors as values.
 */
interface FilteredMapping : IndexedMutableMapping {

    /**
     * The set of authorized key classes
     */
    var authorizedKeys : MutableSet<KClass<out Any>>
    /**
     * The set of authorized value classes
     */
    var authorizedValues : MutableSet<KClass<out Any>>

    /**
     * Detects if the given key is valid, i.e. can be accepted in the map.
     * @return true if the key is null, if the set of authorized keys is empty or if the class of the given
     * key is member of the said set. False otherwise.
     */
    infix fun keyIsValid(key: Any?): Boolean = (authorizedKeys.isEmpty() || key == null || key::class in authorizedKeys)

    /**
     * Detects if the given value is valid, i.e. can be accepted in the map.
     * @return true if the value is null, if the set of authorized values is empty or if the class of the given
     * value is member of the said set. False otherwise.
     */
    infix fun valueIsValid(value: Any?): Boolean = (authorizedValues.isEmpty() || value == null || value::class in authorizedValues)

    /**
     * Detects if the given pair is valid, i.e. can be accepted in the map.
     * @return true if the key and the value are valid.
     * @see keyIsValid
     * @see valueIsValid
     */
    fun pairIsValid(key : Any?, value : Any?) : Boolean = keyIsValid(key) && valueIsValid(value)

    /**
     * Detects if the given pair is valid, i.e. can be accepted in the map.
     * @return true if the key and the value are valid.
     * @see keyIsValid
     * @see valueIsValid
     */
    infix fun pairIsValid(pair : Pair<Any?, Any?>) : Boolean = pairIsValid(pair.first, pair.second)

    override fun add(key: Any?, value: Any?): Boolean {
        if(!containsKey(key) && pairIsValid(key, value)){
            keys.add(key)
            values.add(value)
            entries.add(Pair(key, value))
            size++
            return true
        }
        return false
    }

    override fun replace(key: Any?, value: Any?): Boolean {
        return if(pairIsValid(key, value)) super.replace(key, value) else false
    }

    override fun putOrReplace(key: Any?, value: Any?) : Boolean{
        return if(pairIsValid(key, value)) super.putOrReplace(key, value) else false
    }

    override fun eraseAndAdd(key : Any?, value : Any?) {
        if(pairIsValid(key, value)) super.eraseAndAdd(key, value)
    }

    /**
     * Adds the given classes to the set of valid key classes
     */
    fun addKeyClasses(vararg classes : KClass<out Any>){
        for(c : KClass<out Any> in classes){
            authorizedKeys.add(c)
        }
    }

    /**
     * Adds the given classes to the set of valid key classes
     */
    fun addKeyClasses(classes : Collection<KClass<out Any>>){
        for(c : KClass<out Any> in classes){
            authorizedKeys.add(c)
        }
    }

    /**
     * Adds the given classes to the set of valid value classes
     */
    fun addValueClasses(vararg classes : KClass<out Any>){
        for(c : KClass<out Any> in classes){
            authorizedValues.add(c)
        }
    }

    /**
     * Adds the given classes to the set of valid value classes
     */
    fun addValueClasses(classes : Collection<KClass<out Any>>){
        for(c : KClass<out Any> in classes){
            authorizedValues.add(c)
        }
    }

}