package llayout.interfaces

import llayout.Action
import llayout.utilities.LProperty
import java.awt.Graphics

interface Displayable : LTimerUpdatable {

    override fun onTimerTick(): Displayable = this

    var requestUpdate : LProperty<Boolean>

    fun onAdd(container : LContainer){}

    fun onRemove(container : LContainer){}

    fun updateRelativeValues(){}

    fun addRequestUpdateListener(key : Any?, action : Action) : Displayable{
        requestUpdate.addListener(key, action)
        return this
    }

    fun addRequestUpdateListener(action : Action) : Displayable = addRequestUpdateListener(action, action)

    fun removeRequestUpdateListener(key : Any?) : Displayable{
        requestUpdate.removeListener(key)
        return this
    }

    fun drawDisplayable(g : Graphics)

    fun initialize()

}