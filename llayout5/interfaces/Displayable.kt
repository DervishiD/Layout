package llayout5.interfaces

import llayout5.Action
import java.awt.Graphics

/**
 * The interface implemented by objects that appear on LContainers.
 * @see LTimerUpdatable
 * @see HavingDimension
 * @since LLayout 1
 */
interface Displayable : LTimerUpdatable, HavingDimension {

    /**
     * The action executed when this Displayable is added on a LContainer.
     * @since LLayout 1
     */
    fun onAdd(container : StandardLContainer){}

    /**
     * The action executed when this Displayable is removed from a LContainer.
     * @since LLayout 1
     */
    fun onRemove(container : StandardLContainer){}

    /**
     * Updates the values possessed by this Displayable that depend on the container's dimensions.
     * @since LLayout 1
     */
    fun updateRelativeValues(frameWidth : Int, frameHeight : Int)

    /**
     * Adds a listener that triggers when the Displayable needs an update from its container.
     * @param key The key associated to the given action
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addRequestUpdateListener(key : Any?, action : Action) : Displayable

    /**
     * Requests an update from the container.
     * @since LLayout 1
     */
    fun requestUpdate()

    /**
     * Adds a listener that triggers when the Displayable needs an update from its container.
     * @return this
     *
     * @since LLayout 1
     */
    fun addRequestUpdateListener(action : Action) : Displayable

    /**
     * Removes the update listener associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeRequestUpdateListener(key : Any?) : Displayable

    /**
     * Draws the Displayable on the given Graphics.
     * @since LLayout 1
     */
    fun drawDisplayable(g : Graphics)

    /**
     * Initializes the Displayable.
     * @since LLayout 1
     */
    fun initialize()

}