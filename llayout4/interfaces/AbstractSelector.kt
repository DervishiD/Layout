package llayout4.interfaces

import llayout4.Action
import llayout4.utilities.LObservable

/**
 * An interface implemented by discrete selector classes that let the user select something.
 * The type parameter T is the type of the selected objects.
 * @since LLayout 1
 */
interface AbstractSelector<T> {

    /**
     * The index of the current option.
     * @see options
     * @see LObservable
     * @since LLayout 1
     */
    var currentOptionIndex : LObservable<Int>

    /**
     * The list of options that the selector chooses from.
     * @since LLayout 1
     */
    val options : MutableList<T>

    /**
     * Returns the current selected option.
     * @since LLayout 1
     */
    fun selectedOption() : T = options[currentOptionIndex.value]

    /**
     * Adds a listener to the current selection.
     * @param key The key associated to the given action
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addSelectionListener(key : Any?, action : Action) :AbstractSelector<T>{
        currentOptionIndex.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the current selection.
     * @return this
     * @since LLayout 1
     */
    fun addSelectionListener(action : Action) : AbstractSelector<T>{
        currentOptionIndex.addListener(action)
        return this
    }

    /**
     * Removes the selection listener associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeSelectionListener(key : Any?) : AbstractSelector<T>{
        currentOptionIndex.removeListener(key)
        return this
    }

    /**
     * Returns the number of options that this Selector can select.
     * @see options
     * @since LLayout 1
     */
    fun optionsNumber() : Int = options.size

    /**
     * Adds an option to the list.
     * @see options
     * @since LLayout 1
     */
    fun addOption(option : T) : AbstractSelector<T> {
        options.add(option)
        return this
    }

    /**
     * Adds a Collection of options to the list.
     * @see options
     * @since LLayout 1
     */
    fun addOptionsList(options : Collection<T>) : AbstractSelector<T> {
        for(option : T in options){
            addOption(option)
        }
        return this
    }

    /**
     * Adds a vararg Collection of options to the list.
     * @see options
     * @since LLayout 1
     */
    fun addOptionsList(vararg options : T) : AbstractSelector<T> {
        for(option : T in options){
            addOption(option)
        }
        return this
    }

}