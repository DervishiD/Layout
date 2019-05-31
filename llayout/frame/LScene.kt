package llayout.frame

import llayout.Action
import llayout.GraphicAction
import llayout.interfaces.Canvas
import llayout.interfaces.Displayable
import llayout.interfaces.HavingDimension
import llayout.interfaces.LTimerUpdatable
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

open class LScene : HavingDimension, LTimerUpdatable, Canvas {

    private val core : LSceneCore = LSceneCore()

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    fun setNextScene(nextScene : LScene) : LScene{
        core.setNextScreen(nextScene.core)
        return this
    }

    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseClickedAction(action)
        return this
    }

    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMousePressedAction(action)
        return this
    }

    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseReleasedAction(action)
        return this
    }

    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseEnteredAction(action)
        return this
    }

    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseExitedAction(action)
        return this
    }

    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseMovedAction(action)
        return this
    }

    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseDraggedAction(action)
        return this
    }

    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : LScene{
        core.setOnMouseWheelMovedAction(action)
        return this
    }

    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : LScene{
        core.setOnKeyPressedAction(action)
        return this
    }

    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : LScene{
        core.setOnKeyReleasedAction(action)
        return this
    }

    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : LScene{
        core.setOnKeyTypedAction(action)
        return this
    }

    fun addWidthListener(key : Any?, action : Action) : LScene{
        core.addWidthListener(key, action)
        return this
    }

    fun addWidthListener(action : Action) : LScene{
        core.addWidthListener(action)
        return this
    }

    fun addHeightListener(key : Any?, action : Action) : LScene{
        core.addHeightListener(key, action)
        return this
    }

    fun addHeightListener(action : Action) : LScene{
        core.addHeightListener(action)
        return this
    }

    fun addDimensionListener(key : Any?, action : Action) : LScene = addWidthListener(key, action).addHeightListener(key, action)

    fun addDimensionListener(action : Action) : LScene = addWidthListener(action).addHeightListener(action)

    fun removeWidthListener(key : Any?) : LScene{
        core.removeWidthListener(key)
        return this
    }

    fun removeHeightListener(key : Any?) : LScene{
        core.removeHeightListener(key)
        return this
    }

    fun removeDimensionListener(key : Any?) : LScene = removeWidthListener(key).removeHeightListener(key)

    fun add(vararg displayables : Displayable) : LScene{
        for(d : Displayable in displayables){
            core.add(d)
        }
        return this
    }

    fun remove(vararg displayables : Displayable) : LScene{
        for(d : Displayable in displayables){
            core.remove(d)
        }
        return this
    }

    fun setOnSaveAction(action : Action) : LScene{
        core.setOnSaveAction(action)
        return this
    }

    fun setOnLoadAction(action : Action) : LScene{
        core.setOnLoadAction(action)
        return this
    }

    override fun addGraphicAction(graphicAction: GraphicAction, key: Any?): LScene {
        core.addGraphicAction(graphicAction, key)
        return this
    }

    override fun width() : Int = core.width()

    override fun height(): Int = core.height()

    override fun onTimerTick() {
        super.onTimerTick()
        core.onTimerTick()
    }

    internal fun core() : LSceneCore = core

}