package llayout.interfaces

import llayout.Action
import llayout.utilities.LProperty
import java.awt.Graphics

interface Displayable : LTimerUpdatable, HavingDimension {

    var requestUpdate : LProperty<Boolean>

    fun onAdd(container : StandardLContainer){}

    fun onRemove(container : StandardLContainer){}

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

    override fun onTimerTick(){}

    fun drawDisplayable(g : Graphics)

    fun initialize()

}