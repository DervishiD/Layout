package llayout.utilities

import llayout.Action

class LObservable<T>(value: T) {

    var value : T = value
        set(newValue) {
            field = newValue
            for(action : Action in listeners.values){
                action.invoke()
            }
        }

    private val listeners : MutableMap<Any?, Action> = mutableMapOf()

    fun addListener(key : Any?, action : Action) : LObservable<T>{
        listeners[key] = action
        return this
    }

    fun removeListener(key : Any?) : LObservable<T>{
        listeners.remove(key)
        return this
    }

    fun addListener(action : Action) : LObservable<T> = addListener(action, action)

}