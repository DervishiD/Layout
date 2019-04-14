package layout.interfaces

/**
 * An interface implemented by discrete selector classes that let the user select something.
 * The type parameter T is the type of the selected objects.
 */
interface AbstractSelector<T> {

    /**
     * The index of the current option.
     * @see options
     */
    var currentOption : Int

    /**
     * The list of options that the selector chooses from.
     */
    val options : MutableList<T>

    /**
     * Returns the current selected option
     */
    fun selectedOption() : T = options[currentOption]

    /**
     * Returns the number of options that this Selector can select.
     * @see options
     */
    fun optionsNumber() : Int = options.size

    /**
     * Adds an option to the list.
     * @see options
     */
    infix fun addOption(option : T) : AbstractSelector<T> {
        options.add(option)
        return this
    }

    /**
     * Adds a Collection of options to the list.
     * @see options
     */
    infix fun addOptionsList(options : Collection<T>) : AbstractSelector<T> {
        this.options.addAll(options)
        return this
    }

    /**
     * Adds a vararg Collection of options to the list.
     * @see options
     */
    fun addOptionsList(vararg options : T) : AbstractSelector<T> {
        this.options.addAll(options)
        return this
    }

}