package llayout7.displayers

import llayout7.utilities.Action
import llayout7.displayers.cores.DisplayerContainerCore
import llayout7.interfaces.Displayable
import llayout7.interfaces.StandardLContainer
import llayout7.utilities.LObservable
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

/**
 * The base class of the objects that appear on a LScene.
 * @see Displayable
 * @since LLayout 1
 */
open class Displayer : Displayable {

    protected companion object{

        /**
         * Returns the core of a Displayer. Indeed, the classes that inherit from Displayer, and the Displayer class itself,
         * are wrappers.
         * @param displayer A Displayer.
         * @return The core of the given Displayer
         * @see core
         * @see DisplayerContainerCore
         * @since LLayout 1
         */
        @JvmStatic protected fun core(displayer : Displayer) : DisplayerContainerCore = displayer.core

    }

    /**
     * The core of a Displayer. Very dangerous. Use with caution.
     * @since LLayout 1
     */
    protected val core : DisplayerContainerCore = DisplayerContainerCore()

    /**
     * A [LObservable] that asks the container for a coordinate update when needed.
     * @see LObservable
     * @since LLayout 1
     */
    private val needsAnUpdate : LObservable<Boolean> = LObservable(true)

    /**
     * True if the [Displayer] is initializing.
     * @since LLayout 1
     */
    private var isInitializing : Boolean = true

    protected constructor(x : Int, y : Int){
        core.setCenterX(x)
        core.setCenterY(y)
    }

    protected constructor(x : Double, y : Int){
        core.setCenterX(x)
        core.setCenterY(y)
    }

    protected constructor(x : Int, y : Double){
        core.setCenterX(x)
        core.setCenterY(y)
    }

    protected constructor(x : Double, y : Double){
        core.setCenterX(x)
        core.setCenterY(y)
    }

    protected constructor()

    /**
     * Returns the x coordinate of the center of this Displayer.
     * @since LLayout 1
     */
    fun x() : Int = core.centerX()

    /**
     * Returns the y coordinate of the center of this Displayer.
     * @since LLayout 1
     */
    fun y() : Int = core.centerY()

    /**
     * The x coordinate of the left side of this Displayer.
     * @since LLayout 1
     */
    fun leftSideX() : Int = core.leftSideX()

    /**
     * The x coordinate of the right side of this Displayer.
     * @since LLayout 1
     */
    fun rightSideX() : Int = core.rightSideX()

    /**
     * The y coordinate of the up side of this Displayer.
     * @since LLayout 1
     */
    fun upSideY() : Int = core.upSideY()

    /**
     * The y coordinate of the down side of the Displayer.
     * @since LLayout 1
     */
    fun downSideY() : Int = core.downSideY()

    /**
     * Sets the x coordinate of this Displayer, in pixels.
     * @return this
     * @since LLayout 1
     */
    fun setX(x : Int) : Displayer{
        core.setCenterX(x)
        return this
    }

    /**
     * Sets the x coordinate of this Displayer, as a proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun setX(x : Double) : Displayer{
        core.setCenterX(x)
        return this
    }

    /**
     * Sets the y coordinate of this Displayer, in pixels.
     * @return this
     * @since LLayout 1
     */
    fun setY(y : Int) : Displayer{
        core.setCenterY(y)
        return this
    }

    /**
     * Sets the y coordinate of this Displayer, as a proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun setY(y : Double) : Displayer{
        core.setCenterY(y)
        return this
    }

    /**
     * Aligns the left side of this Displayer to the given coordinate.
     * @return this
     * @since LLayout 1
     */
    fun alignLeftTo(x : Int) : Displayer{
        core.alignLeftTo(x)
        return this
    }

    /**
     * Aligns the left side of this Displayer to the given proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun alignLeftTo(x : Double) : Displayer{
        core.alignLeftTo(x)
        return this
    }

    /**
     * Aligns the left side of this Displayer to the given proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun alignLeftTo(x : Float) : Displayer{
        core.alignLeftTo(x)
        return this
    }

    /**
     * Aligns the right side of this Displayer to the given coordinate.
     * @return this
     * @since LLayout 1
     */
    fun alignRightTo(x : Int) : Displayer{
        core.alignRightTo(x)
        return this
    }

    /**
     * Aligns the right side of this Displayer to the given proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun alignRightTo(x : Double) : Displayer{
        core.alignRightTo(x)
        return this
    }

    /**
     * Aligns the right side of this Displayer to the given proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun alignRightTo(x : Float) : Displayer{
        core.alignRightTo(x)
        return this
    }

    /**
     * Aligns the up side of this Displayer to the given coordinate.
     * @return this
     * @since LLayout 3
     */
    fun alignTopTo(y : Int) : Displayer{
        core.alignTopTo(y)
        return this
    }

    /**
     * Aligns the up side of this Displayer to the given proportion of its container's height.
     * @return this
     * @since LLayout 3
     */
    fun alignTopTo(y : Double) : Displayer{
        core.alignTopTo(y)
        return this
    }

    /**
     * Aligns the up side of this Displayer to the given proportion of its container's height.
     * @return this
     * @since LLayout 3
     */
    fun alignTopTo(y : Float) : Displayer{
        core.alignTopTo(y)
        return this
    }

    /**
     * Aligns the down side of this Displayer to the given coordinate.
     * @return this
     * @since LLayout 3
     */
    fun alignBottomTo(y : Int) : Displayer{
        core.alignBottomTo(y)
        return this
    }

    /**
     * Aligns the down side of this Displayer to the given proportion of its container's height.
     * @return this
     * @since LLayout 3
     */
    fun alignBottomTo(y : Double) : Displayer{
        core.alignBottomTo(y)
        return this
    }

    /**
     * Aligns the down side of this Displayer to the given proportion of its container's height.
     * @return this
     * @since LLayout 3
     */
    fun alignBottomTo(y : Float) : Displayer{
        core.alignBottomTo(y)
        return this
    }

    /**
     * Aligns the up side of this Displayer to the up side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 3
     */
    fun alignTopToTop(component : Displayer, delta : Int = 0) : Displayer{
        core.alignTopToTop(component.core, delta)
        return this
    }

    /**
     * Aligns the up side of this Displayer to the down side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 3
     */
    fun alignTopToBottom(component : Displayer, delta : Int = 0) : Displayer{
        core.alignTopToBottom(component.core, delta)
        return this
    }

    /**
     * Aligns the down side of this Displayer to the down side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 3
     */
    fun alignBottomToBottom(component : Displayer, delta : Int = 0) : Displayer{
        core.alignBottomToBottom(component.core, delta)
        return this
    }

    /**
     * Aligns the down side of this Displayer to the up side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 3
     */
    fun alignBottomToTop(component : Displayer, delta : Int = 0) : Displayer{
        core.alignBottomToTop(component.core, delta)
        return this
    }

    /**
     * Aligns the left side of this Displayer to the right side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 1
     */
    fun alignLeftToRight(component : Displayer, delta : Int = 0) : Displayer{
        core.alignLeftToRight(component.core, delta)
        return this
    }

    /**
     * Aligns the left side of this Displayer to the left side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 1
     */
    fun alignLeftToLeft(component : Displayer, delta : Int = 0) : Displayer{
        core.alignLeftToLeft(component.core, delta)
        return this
    }

    /**
     * Aligns the right side of this Displayer to the left side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 1
     */
    fun alignRightToLeft(component : Displayer, delta : Int = 0) : Displayer{
        core.alignRightToLeft(component.core, delta)
        return this
    }

    /**
     * Aligns the right side of this Displayer to the right side of the given one.
     * @param component The other Displayer
     * @param delta The signed distance between the aligned sides
     * @return this
     * @since LLayout 1
     */
    fun alignRightToRight(component : Displayer, delta : Int = 0) : Displayer{
        core.alignRightToRight(component.core, delta)
        return this
    }

    /**
     * Resets all the alignment constraints on this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun resetAlignment() : Displayer{
        core.resetAlignment()
        return this
    }

    /**
     * Resets the horizontal alignment constraints on this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun resetHorizontalAlignment() : Displayer{
        core.resetHorizontalAlignment()
        return this
    }

    /**
     * Resets the vertical alignment constraints on this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun resetVerticalAlignment() : Displayer{
        core.resetVerticalAlignment()
        return this
    }

    /**
     * Sets the action executed when the mouses clicks this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseClickedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouses presses this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMousePressedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouses releases this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseReleasedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouses enters this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseEnteredAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouses exits this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseExitedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouses moves over this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseMovedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouses drags this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseDraggedAction(action)
        return this
    }

    /**
     * Sets the action executed when the mouses wheels acts over this Displayer.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : Displayer{
        core.setOnMouseWheelMovedAction(action)
        return this
    }

    /**
     * Sets the action executed when the users presses a key while this Displayer has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : Displayer{
        core.setOnKeyPressedAction(action)
        return this
    }

    /**
     * Sets the action executed when the users releases a key while this Displayer has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : Displayer{
        core.setOnKeyReleasedAction(action)
        return this
    }

    /**
     * Sets the action executed when the users types a key while this Displayer has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : Displayer{
        core.setOnKeyTypedAction(action)
        return this
    }

    /**
     * Moves this Displayer to the given position.
     * @param x In pixels
     * @param y In pixels
     * @return this
     * @since LLayout 1
     */
    fun moveTo(x : Int, y : Int) : Displayer{
        core.moveTo(x, y)
        return this
    }

    /**
     * Moves this Displayer to the given position.
     * @param x As a proportion of its component's width
     * @param y In pixels
     * @return this
     * @since LLayout 1
     */
    fun moveTo(x : Double, y : Int) : Displayer{
        core.moveTo(x, y)
        return this
    }

    /**
     * Moves this Displayer to the given position.
     * @param x In pixels
     * @param y As a proportion of its component's height
     * @return this
     * @since LLayout 1
     */
    fun moveTo(x : Int, y : Double) : Displayer{
        core.moveTo(x, y)
        return this
    }

    /**
     * Moves this Displayer to the given position.
     * @param x As a proportion of its component's width
     * @param y As a proportion of its container's height.
     * @return this
     * @since LLayout 1
     */
    fun moveTo(x : Double, y : Double) : Displayer{
        core.moveTo(x, y)
        return this
    }

    /**
     * Moves along the given direction.
     * @return this
     * @since LLayout 1
     */
    fun moveAlong(x : Int, y : Int) : Displayer{
        core.moveAlong(x, y)
        return this
    }

    /**
     * Moves along the x direction by the given amount.
     * @return this
     * @since LLayout 1
     */
    fun moveAlongX(x : Int) : Displayer{
        core.moveAlongX(x)
        return this
    }

    /**
     * Moves along the y direction by the given amount.
     * @return this
     * @since LLayout 1
     */
    fun moveAlongY(y : Int) : Displayer{
        core.moveAlongY(y)
        return this
    }

    /**
     * Adds a listener of the x coordinate of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addXListener(key : Any?, action : Action) : Displayer{
        core.addXListener(key, action)
        return this
    }

    /**
     * Adds a listener of the y coordinate of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addYListener(key : Any?, action : Action) : Displayer{
        core.addYListener(key, action)
        return this
    }

    /**
     * Adds a listener of the width of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addWidthListener(key : Any?, action : Action) : Displayer{
        core.addWidthListener(key, action)
        return this
    }

    /**
     * Adds a listener of the height of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addHeightListener(key : Any?, action : Action) : Displayer{
        core.addHeightListener(key, action)
        return this
    }

    /**
     * Adds a listener of the x coordinate of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addXListener(action : Action) : Displayer{
        core.addXListener(action)
        return this
    }

    /**
     * Adds a listener of the y coordinate of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addYListener(action : Action) : Displayer{
        core.addYListener(action)
        return this
    }

    /**
     * Adds a listener of the width of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addWidthListener(action : Action) : Displayer{
        core.addWidthListener(action)
        return this
    }

    /**
     * Adds a listener of the height of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addHeightListener(action : Action) : Displayer{
        core.addHeightListener(action)
        return this
    }

    /**
     * Removes a listener of the x coordinate of this Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeXListener(key : Any?) : Displayer{
        core.removeXListener(key)
        return this
    }

    /**
     * Removes a listener of the y coordinate of this Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeYListener(key : Any?) : Displayer{
        core.removeYListener(key)
        return this
    }

    /**
     * Removes a listener of the width of this Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeWidthListener(key : Any?) : Displayer{
        core.removeWidthListener(key)
        return this
    }

    /**
     * Removes a listener of the height of this Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeHeightListener(key : Any?) : Displayer{
        core.removeHeightListener(key)
        return this
    }

    /**
     * Adds a listener of the left side coordinate of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addLeftSideListener(key : Any?, action : Action) : Displayer{
        core.addLeftSideListener(key, action)
        return this
    }

    /**
     * Adds a listener of the right side coordinate of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addRightSideListener(key : Any?, action : Action) : Displayer{
        core.addRightSideListener(key, action)
        return this
    }

    /**
     * Adds a listener of the up side coordinate of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addUpSideListener(key : Any?, action : Action) : Displayer{
        core.addUpSideListener(key, action)
        return this
    }

    /**
     * Adds a listener of the down side coordinate of this Displayer.
     * @param key The key associated to the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addDownSideListener(key : Any?, action : Action) : Displayer{
        core.addDownSideListener(key, action)
        return this
    }

    /**
     * Adds a listener of the left side coordinate of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addLeftSideListener(action : Action) : Displayer{
        core.addLeftSideListener(action)
        return this
    }

    /**
     * Adds a listener of the right side coordinate of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addRightSideListener(action : Action) : Displayer{
        core.addRightSideListener(action)
        return this
    }

    /**
     * Adds a listener of the up side coordinate of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addUpSideListener(action : Action) : Displayer{
        core.addUpSideListener(action)
        return this
    }

    /**
     * Adds a listener of the down side coordinate of this Displayer.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addDownSideListener(action : Action) : Displayer{
        core.addDownSideListener(action)
        return this
    }

    /**
     * Removes the listener of the left side coordinate of the Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeLeftSideListener(key : Any?) : Displayer{
        core.removeLeftSideListener(key)
        return this
    }

    /**
     * Removes the listener of the right side coordinate of the Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeRightSideListener(key : Any?) : Displayer{
        core.removeRightSideListener(key)
        return this
    }

    /**
     * Removes the listener of the up side coordinate of the Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeUpSideListener(key : Any?) : Displayer{
        core.removeUpSideListener(key)
        return this
    }

    /**
     * Removes the listener of the down side coordinate of the Displayer associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeDownSideListener(key : Any?) : Displayer{
        core.removeDownSideListener(key)
        return this
    }

    /**
     * Initializes the parameters that are required to draw the Displayer.
     * @since LLayout 1
     */
    protected open fun initializeDrawingParameters(g : Graphics){}

    override fun drawDisplayable(g: Graphics) {
        if(isInitializing){
            initializeDrawingParameters(g)
            isInitializing = false
        }
        core.drawDisplayable(g)
    }

    override fun initialize() {
        isInitializing = true
    }

    override fun onAdd(container: StandardLContainer) {
        container.add(core)
        super.onAdd(container)
    }

    override fun onRemove(container: StandardLContainer) {
        container.remove(core)
        super.onRemove(container)
    }

    override fun width() : Int = core.width()

    override fun height() : Int = core.height()

    override fun addRequestUpdateListener(key: Any?, action: Action): Displayer {
        needsAnUpdate.addListener(key, action)
        return this
    }

    override fun requestUpdate(){
        needsAnUpdate.value = true
    }

    override fun addRequestUpdateListener(action: Action): Displayer {
        needsAnUpdate.addListener(action)
        return this
    }

    override fun removeRequestUpdateListener(key: Any?): Displayer {
        needsAnUpdate.removeListener(key)
        return this
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        core.updateRelativeValues(frameWidth, frameHeight)
    }

    override fun onTimerTick(){}

}