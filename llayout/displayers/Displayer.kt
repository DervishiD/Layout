package llayout.displayers

import llayout.Action
import llayout.displayers.cores.DisplayerContainerCore
import llayout.interfaces.Displayable
import llayout.interfaces.StandardLContainer
import llayout.utilities.LObservable
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

open class Displayer : Displayable {

    protected companion object{
        @JvmStatic protected fun core(displayer : Displayer) : DisplayerContainerCore = displayer.core
    }

    protected val core : DisplayerContainerCore = DisplayerContainerCore()

    private val needsAnUpdate : LObservable<Boolean> = LObservable(true)

    private var isInitializing : Boolean = true

    protected constructor(x : Int, y : Int){
        core.setCenterX(x).setCenterY(y)
    }

    protected constructor(x : Double, y : Int){
        core.setCenterX(x).setCenterY(y)
    }

    protected constructor(x : Int, y : Double){
        core.setCenterX(x).setCenterY(y)
    }

    protected constructor(x : Double, y : Double){
        core.setCenterX(x).setCenterY(y)
    }

    protected constructor()

    fun x() : Int = core.centerX()

    fun y() : Int = core.centerY()

    open fun leftSideX() : Int = core.leftSideX()

    open fun rightSideX() : Int = core.rightSideX()

    open fun upSideY() : Int = core.upSideY()

    open fun downSideY() : Int = core.downSideY()

    fun setX(x : Int) : Displayer{
        core.setCenterX(x)
        return this
    }

    fun setX(x : Double) : Displayer{
        core.setCenterX(x)
        return this
    }

    fun setY(y : Int) : Displayer{
        core.setCenterY(y)
        return this
    }

    fun setY(y : Double) : Displayer{
        core.setCenterY(y)
        return this
    }

    open fun alignLeftTo(x : Int) : Displayer{
        core.alignLeftTo(x)
        return this
    }

    fun alignLeftTo(x : Double) : Displayer{
        core.alignLeftTo(x)
        return this
    }

    fun alignLeftTo(x : Float) : Displayer{
        core.alignLeftTo(x)
        return this
    }

    open fun alignRightTo(x : Int) : Displayer{
        core.alignRightTo(x)
        return this
    }

    fun alignRightTo(x : Double) : Displayer{
        core.alignRightTo(x)
        return this
    }

    fun alignRightTo(x : Float) : Displayer{
        core.alignRightTo(x)
        return this
    }

    open fun alignUpTo(y : Int) : Displayer{
        core.alignUpTo(y)
        return this
    }

    fun alignUpTo(y : Double) : Displayer{
        core.alignUpTo(y)
        return this
    }

    fun alignUpTo(y : Float) : Displayer{
        core.alignUpTo(y)
        return this
    }

    open fun alignDownTo(y : Int) : Displayer{
        core.alignDownTo(y)
        return this
    }

    fun alignDownTo(y : Double) : Displayer{
        core.alignDownTo(y)
        return this
    }

    fun alignDownTo(y : Float) : Displayer{
        core.alignDownTo(y)
        return this
    }

    fun alignUpToUp(component : Displayer, delta : Int = 0) : Displayer{
        core.alignUpToUp(component.core, delta)
        return this
    }

    fun alignUpToDown(component : Displayer, delta : Int = 0) : Displayer{
        core.alignUpToDown(component.core, delta)
        return this
    }

    fun alignDownToDown(component : Displayer, delta : Int = 0) : Displayer{
        core.alignDownToDown(component.core, delta)
        return this
    }

    fun alignDownToUp(component : Displayer, delta : Int = 0) : Displayer{
        core.alignDownToUp(component.core, delta)
        return this
    }

    fun alignLeftToRight(component : Displayer, delta : Int = 0) : Displayer{
        core.alignLeftToRight(component.core, delta)
        return this
    }

    fun alignLeftToLeft(component : Displayer, delta : Int = 0) : Displayer{
        core.alignLeftToLeft(component.core, delta)
        return this
    }

    fun alignRightToLeft(component : Displayer, delta : Int = 0) : Displayer{
        core.alignRightToLeft(component.core, delta)
        return this
    }

    fun alignRightToRight(component : Displayer, delta : Int = 0) : Displayer{
        core.alignRightToRight(component.core, delta)
        return this
    }

    fun resetAlignment() : Displayer{
        core.resetAlignment()
        return this
    }

    fun resetHorizontalAlignment() : Displayer{
        core.resetHorizontalAlignment()
        return this
    }

    fun resetVerticalAlignment() : Displayer{
        core.resetVerticalAlignment()
        return this
    }

    fun setPreferredWidth(width : Int) : Displayer{
        core.setPreferredWidth(width)
        return this
    }

    fun setPreferredHeight(height : Int) : Displayer{
        core.setPreferredHeight(height)
        return this
    }

    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseClickedAction(action)
        return this
    }

    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMousePressedAction(action)
        return this
    }

    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseReleasedAction(action)
        return this
    }

    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseEnteredAction(action)
        return this
    }

    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseExitedAction(action)
        return this
    }

    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseMovedAction(action)
        return this
    }

    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : Displayer{
        core.setOnMouseDraggedAction(action)
        return this
    }

    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : Displayer{
        core.setOnMouseWheelMovedAction(action)
        return this
    }

    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : Displayer{
        core.setOnKeyPressedAction(action)
        return this
    }

    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : Displayer{
        core.setOnKeyReleasedAction(action)
        return this
    }

    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : Displayer{
        core.setOnKeyTypedAction(action)
        return this
    }

    fun moveTo(x : Int, y : Int) : Displayer{
        core.moveTo(x, y)
        return this
    }

    fun moveTo(x : Double, y : Int) : Displayer{
        core.moveTo(x, y)
        return this
    }

    fun moveTo(x : Int, y : Double) : Displayer{
        core.moveTo(x, y)
        return this
    }

    fun moveTo(x : Double, y : Double) : Displayer{
        core.moveTo(x, y)
        return this
    }

    fun moveAlong(x : Int, y : Int) : Displayer{
        core.moveAlong(x, y)
        return this
    }

    fun moveAlongX(x : Int) : Displayer{
        core.moveAlongX(x)
        return this
    }

    fun moveAlongY(y : Int) : Displayer{
        core.moveAlongY(y)
        return this
    }

    fun addXListener(key : Any?, action : Action) : Displayer{
        core.addXListener(key, action)
        return this
    }

    fun addYListener(key : Any?, action : Action) : Displayer{
        core.addYListener(key, action)
        return this
    }

    fun addWidthListener(key : Any?, action : Action) : Displayer{
        core.addWidthListener(key, action)
        return this
    }

    fun addHeightListener(key : Any?, action : Action) : Displayer{
        core.addHeightListener(key, action)
        return this
    }

    fun addXListener(action : Action) : Displayer{
        core.addXListener(action)
        return this
    }

    fun addYListener(action : Action) : Displayer{
        core.addYListener(action)
        return this
    }

    fun addWidthListener(action : Action) : Displayer{
        core.addWidthListener(action)
        return this
    }

    fun addHeightListener(action : Action) : Displayer{
        core.addHeightListener(action)
        return this
    }

    fun removeXListener(key : Any?) : Displayer{
        core.removeXListener(key)
        return this
    }

    fun removeYListener(key : Any?) : Displayer{
        core.removeYListener(key)
        return this
    }

    fun removeWidthListener(key : Any?) : Displayer{
        core.removeWidthListener(key)
        return this
    }

    fun removeHeightListener(key : Any?) : Displayer{
        core.removeHeightListener(key)
        return this
    }

    fun addLeftSideListener(key : Any?, action : Action) : Displayer{
        core.addLeftSideListener(key, action)
        return this
    }

    fun addRightSideListener(key : Any?, action : Action) : Displayer{
        core.addRightSideListener(key, action)
        return this
    }

    fun addUpSideListener(key : Any?, action : Action) : Displayer{
        core.addUpSideListener(key, action)
        return this
    }

    fun addDownSideListener(key : Any?, action : Action) : Displayer{
        core.addDownSideListener(key, action)
        return this
    }

    fun addLeftSideListener(action : Action) : Displayer{
        core.addLeftSideListener(action)
        return this
    }

    fun addRightSideListener(action : Action) : Displayer{
        core.addRightSideListener(action)
        return this
    }

    fun addUpSideListener(action : Action) : Displayer{
        core.addUpSideListener(action)
        return this
    }

    fun addDownSideListener(action : Action) : Displayer{
        core.addDownSideListener(action)
        return this
    }

    fun removeLeftSideListener(key : Any?) : Displayer{
        core.removeLeftSideListener(key)
        return this
    }

    fun removeRightSideListener(key : Any?) : Displayer{
        core.removeRightSideListener(key)
        return this
    }

    fun removeUpSideListener(key : Any?) : Displayer{
        core.removeUpSideListener(key)
        return this
    }

    fun removeDownSideListener(key : Any?) : Displayer{
        core.removeDownSideListener(key)
        return this
    }

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

}