package display.selectors

/**
 * An interface implemented by discrete selector classes that select an option : T by showing
 * the user a key : K. For example, a selector that shows the user the name of a color (K, StringDisplay)
 * and selects the described color (T, java.awt.Color).
 */
internal interface AbstractSelector<K, T> {

    /**
     * The options to choose from
     */
    var options : ArrayList<Pair<K, T>>

    /**
     * The index of the current option
     */
    var currentOption : Int

    /**
     * Returns the current selected option
     */
    fun selectedOption() : T = options[currentOption].second

    /**
     * Returns the current selected key
     */
    fun selectedKey() : K = options[currentOption].first

    /**
     * Sets the options of this Selector
     */
    infix fun setOptionsList(list : Collection<Pair<K, T>>){
        if(list.isNotEmpty()){
            if(options.isNotEmpty()) options.clear()
            for(pair : Pair<K, T> in list){
                addOption(pair)
            }
        }else throw IllegalArgumentException("Options must exist")
    }

    /**
     * Sets the options of this Selector
     */
    fun setOptionsList(keys : List<K>, values : List<T>){
        if((keys.size == values.size) && keys.isNotEmpty()){
            if(options.isNotEmpty()) options.clear()
            for(i : Int in 0 until keys.size){
                addOption(keys[i], values[i])
            }
        }else throw IllegalArgumentException("The lists are incompatible : The keys have size ${keys.size}, the values ${values.size}")
    }

    /**
     * Adds an option to the list
     */
    infix fun addOption(pair : Pair<K, T>) = options.add(pair)

    /**
     * Adds an option to the list
     */
    fun addOption(key : K, value : T) = addOption(Pair(key, value))

    /**
     * Adds a list of options to the current list
     */
    infix fun addOptionsList(list : List<Pair<K, T>>){
        for(pair : Pair<K, T> in list){
            addOption(pair)
        }
    }

    /**
     * Adds a list of options to the current list
     */
    fun addOptionsList(keys : List<K>, values : List<T>){
        if(keys.size == values.size && keys.isNotEmpty()){
            for(i : Int in 0 until keys.size){
                addOption(keys[i], values[i])
            }
        }else throw IllegalArgumentException("The lists are incompatible : The keys have size ${keys.size}, the values ${values.size}")
    }

}