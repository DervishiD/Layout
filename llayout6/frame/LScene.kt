package llayout6.frame

import llayout6.utilities.Action
import llayout6.utilities.GraphicAction
import llayout6.interfaces.CanvasCore
import llayout6.interfaces.Displayable
import llayout6.interfaces.HavingDimension
import llayout6.interfaces.LTimerUpdatable
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

/**
 * A LScene is the object on which components (Displayables) are added.
 * @see Displayable
 * @see LTimerUpdatable
 * @see CanvasCore
 * @see HavingDimension
 * @since LLayout 1
 */
open class LScene : HavingDimension, LTimerUpdatable, CanvasCore {

    /**
     * This class is a wrapper.
     * @see LSceneCore
     * @since LLayout 1
     */
    private val core : LSceneCore = LSceneCore()

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    /**
     * Sets the action executed when the mouse clicks this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseClickedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouse presses this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMousePressedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouse releases this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseReleasedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouse enters this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseEnteredAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouse exits this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseExitedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouse moves over this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseMovedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouse drags this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : LScene{
        core.setOnMouseDraggedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouse wheel moves over this LScene
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : LScene{
        core.setOnMouseWheelMovedAction(action)
        return this
    }

    /**
     * Sets the action executed when the user presses a key while this LScene has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : LScene{
        core.setOnKeyPressedAction(action)
        return this
    }

    /**
     * Sets the action executed when the user releases a key while this LScene has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : LScene{
        core.setOnKeyReleasedAction(action)
        return this
    }

    /**
     * Sets the action executed when the user types a key while this LScene has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : LScene{
        core.setOnKeyTypedAction(action)
        return this
    }

    /**
     * Sets the action executed when the frame's timer ticks.
     * @return this
     * @since LLayout 1
     */
    fun setOnTimerTickAction(action : Action) : LScene{
        core.setOnTimerTickAction(action)
        return this
    }

    /**
     * Adds a listener to the width of this LScene.
     * @param key the key associated to the given action
     * @param action The performed action
     * @return this
     * @since LLayout 1
     */
    fun addWidthListener(key : Any?, action : Action) : LScene{
        core.addWidthListener(key, action)
        return this
    }

    /**
     * Adds a listener to the width of this LScene.
     * @return this
     * @since LLayout 1
     */
    fun addWidthListener(action : Action) : LScene{
        core.addWidthListener(action)
        return this
    }

    /**
     * Adds a listener to the height of this LScene.
     * @param key the key associated to the given action
     * @param action The performed action
     * @return this
     * @since LLayout 1
     */
    fun addHeightListener(key : Any?, action : Action) : LScene{
        core.addHeightListener(key, action)
        return this
    }

    /**
     * Adds a listener to the height of this LScene.
     * @return this
     * @since LLayout 1
     */
    fun addHeightListener(action : Action) : LScene{
        core.addHeightListener(action)
        return this
    }

    /**
     * Adds a listener to the width and the height of this LScene.
     * @param key the key associated to the given action
     * @param action The performed action
     * @return this
     * @since LLayout 1
     */
    fun addDimensionListener(key : Any?, action : Action) : LScene = addWidthListener(key, action).addHeightListener(key, action)

    /**
     * Adds a listener to the width and the height of this LScene.
     * @param action The performed action
     * @return this
     * @since LLayout 1
     */
    fun addDimensionListener(action : Action) : LScene = addWidthListener(action).addHeightListener(action)

    /**
     * Removes the listener of this LScene's width associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeWidthListener(key : Any?) : LScene{
        core.removeWidthListener(key)
        return this
    }

    /**
     * Removes the listener of this LScene's height associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeHeightListener(key : Any?) : LScene{
        core.removeHeightListener(key)
        return this
    }

    /**
     * Removes the listener of this LScene's dimensions associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeDimensionListener(key : Any?) : LScene = removeWidthListener(key).removeHeightListener(key)

    /**
     * Adds displayable objects on this LScene.
     * @return this
     * @see Displayable
     * @since LLayout 1
     */
    fun add(vararg displayables : Displayable) : LScene{
        for(d : Displayable in displayables){
            core.add(d)
        }
        return this
    }

    /**
     * Adds displayable objects on this LScene.
     * @return this
     * @see Displayable
     * @since LLayout 1
     */
    fun add(displayables : Collection<Displayable>) : LScene{
        for(d : Displayable in displayables){
            core.add(d)
        }
        return this
    }

    /**
     * Removes the giben Displayables from this LScene
     * @return this
     * @since LLayout 1
     */
    fun remove(vararg displayables : Displayable) : LScene{
        for(d : Displayable in displayables){
            core.remove(d)
        }
        return this
    }

    /**
     * Removes the giben Displayables from this LScene
     * @return this
     * @since LLayout 1
     */
    fun remove(displayables : Collection<Displayable>) : LScene{
        for(d : Displayable in displayables){
            core.remove(d)
        }
        return this
    }

    /**
     * Sets the action executed when this scene is replaced by another.
     * @return this
     * @since LLayout 1
     */
    fun setOnSaveAction(action : Action) : LScene{
        core.setOnSaveAction(action)
        return this
    }

    /**
     * Sets the action executrd when this scene replaces another.
     * @return this
     * @since LLayout 1
     */
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
        core.onTimerTick()
    }

    /**
     * The core of this LScene. Use with caution.
     * @return This LScene's core.
     * @see LSceneCore
     * @since LLayout 1
     */
    internal fun core() : LSceneCore = core

}