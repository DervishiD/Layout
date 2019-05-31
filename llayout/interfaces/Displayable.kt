package llayout.interfaces

import llayout.Action
import java.awt.Graphics

interface Displayable : LTimerUpdatable, HavingDimension {

    fun onAdd(container : StandardLContainer){}

    fun onRemove(container : StandardLContainer){}

    fun updateRelativeValues(frameWidth : Int, frameHeight : Int)

    fun addRequestUpdateListener(key : Any?, action : Action) : Displayable

    fun requestUpdate()

    fun addRequestUpdateListener(action : Action) : Displayable

    fun removeRequestUpdateListener(key : Any?) : Displayable

    fun drawDisplayable(g : Graphics)

    fun initialize()

}