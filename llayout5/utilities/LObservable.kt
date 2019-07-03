package llayout5.utilities

import llayout5.utilities.Action

/**
 * A class containing a single value that supports update listeners.
 * @since LLayout 1
 */
class LObservable<T>(value: T) {

    /**
     * The contained value.
     * When the field is updated, it triggers the listeners attached to it.
     * @since LLayout 1
     */
    var value : T = value
        set(newValue) {
            field = newValue
            for(action : Action in listeners.values){
                action.invoke()
            }
        }

    /**
     * The listeners attached to the field.
     * @since LLayout 1
     */
    private val listeners : MutableMap<Any?, Action> = mutableMapOf()

    /**
     * Adds a listener that is triggered when the field is updated.
     * @param key The key associated to the given action. It is recommended to use strings or primitive types
     *            for debugging reasons, but it is only used to retrieve a particular listener and remove it.
     * @param action The action executed when the value is updated.
     * @return this
     * @since LLayout 1
     */
    fun addListener(key : Any?, action : Action) : LObservable<T>{
        listeners[key] = action
        return this
    }

    /**
     * Removes the listener associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeListener(key : Any?) : LObservable<T>{
        listeners.remove(key)
        return this
    }

    /**
     * Adds a listener that is triggered when the field is updated, without associating a key. The user won't be able
     * to override or remove the given listener.
     * @param action The action executed when the value is updated.
     * @return this
     * @since LLayout 1
     */
    fun addListener(action : Action) : LObservable<T> = addListener(action, action)

}