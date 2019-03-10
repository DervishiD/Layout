package display.selectors

import utilities.FilteredMapping
import utilities.IndexedMapping

/**
 * An interface implemented by discrete selector classes that select an option : T by showing
 * the user a key : K. For example, a selector that shows the user the name of a color (K, StringDisplay)
 * and selects the described color (T, java.awt.Color).
 */
interface AbstractSelector : FilteredMapping {

    /**
     * The index of the current option
     */
    var currentOption : Int

    /**
     * Returns the current selected option
     */
    fun selectedOption() : Any? = values[currentOption]

    /**
     * Returns the current selected key
     */
    fun selectedKey() : Any? = keys[currentOption]

    /**
     * Adds the given list of options to the current one
     */
    infix fun addOptionsList(options : List<Pair<Any?, Any?>>){
        for(option : Pair<Any?, Any?> in options){
            if(option.first != null) addKeyClasses(option.first!!::class)
            if(option.second != null) addValueClasses(option.second!!::class)
            add(option)
        }
    }

    /**
     * Adds the given list of options to the current one
     */
    fun addOptionsList(keys : List<Any?>, values : List<Any?>){
        if(keys.size == values.size){
            for(i : Int in 0 until keys.size){
                if(keys[i] != null) addKeyClasses(keys[i]!!::class)
                if(values[i] != null) addValueClasses(values[i]!!::class)
                add(keys[i], values[i])
            }
        }else throw IllegalArgumentException()
    }

    /**
     * Adds the given list of options to the current one
     */
    infix fun addOptionsList(options : Map<Any?, Any?>){
        for(option in options){
            if(option.key != null) addKeyClasses(option.key!!::class)
            if(option.value != null) addValueClasses(option.value!!::class)
            add(option.key, option.value)
        }
    }

    /**
     * Adds the given list of options to the current one
     */
    infix fun addOptionsList(options : IndexedMapping){
        for(option : Pair<Any?, Any?> in options){
            if(option.first != null) addKeyClasses(option.first!!::class)
            if(option.second != null) addValueClasses(option.second!!::class)
            add(option)
        }
    }

}