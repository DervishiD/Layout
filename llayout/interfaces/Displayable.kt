package llayout.interfaces

import llayout.Action
import llayout.utilities.LProperty
import java.awt.Graphics

interface Displayable : LTimerUpdatable {

    var requestUpdate : LProperty<Boolean>

    fun onAdd(container : LContainer){}

    fun onRemove(container : LContainer){}

    fun updateRelativeValues(frameWidth : Int, frameHeight : Int) : Displayable = this

    fun addRequestUpdateListener(key : Any?, action : Action) : Displayable{
        requestUpdate.addListener(key, action)
        return this
    }

    fun requestUpdate(){
        requestUpdate.value = true
    }

    fun addRequestUpdateListener(action : Action) : Displayable = addRequestUpdateListener(action, action)

    fun removeRequestUpdateListener(key : Any?) : Displayable{
        requestUpdate.removeListener(key)
        return this
    }

    override fun onTimerTick(): Displayable = this

    fun drawDisplayable(g : Graphics)

    fun initialize()

}