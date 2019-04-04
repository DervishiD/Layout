package utilities

import main.Action

class LProperty<T>(value: T) {

    var value : T = value
        set(newValue) {
            field = newValue
            for(action : Action in listeners.values){
                action.invoke()
            }
        }

    private val listeners : MutableMap<Any?, Action> = mutableMapOf()

    fun addListener(key : Any?, action : Action) : LProperty<T>{
        listeners[key] = action
        return this
    }

    infix fun removeListener(key : Any?) : LProperty<T>{
        listeners.remove(key)
        return this
    }

}