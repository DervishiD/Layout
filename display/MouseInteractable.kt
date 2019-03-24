package display

import main.Action
import main.MouseWheelAction

/**
 * An interface implemented by objects that interact with the mouse.
 */
interface MouseInteractable {

    /**
     * Action executed on a mouse click.
     * @see Action
     */
    var onClick : Action

    /**
     * Action executed on a mouse press.
     * @see Action
     */
    var onPress : Action

    /**
     * Action executed on a mouse release.
     * @see Action
     */
    var onRelease : Action

    /**
     * Action executed when the mouse enters the object.
     * @see Action
     */
    var onEnter : Action

    /**
     * Action executed when the mouse exits the object.
     * @see Action
     */
    var onExit : Action

    /**
     * Action executed on a mouse drag.
     * @see Action
     */
    var onDrag : Action

    /**
     * Action executed on a mouse movement on the object.
     * @see Action
     */
    var onMove : Action

    /**
     * Action executed when the mouse wheel moves on this object.
     * @see MouseWheelAction
     */
    var onWheelMoved : MouseWheelAction

    /**
     * Sets the Action invoked when this object is clicked.
     * @param onClickAction The Action that will be executed on a mouse click event.
     * @see onClick
     * @see Action
     */
    infix fun setOnClickAction(onClickAction : Action){
        onClick = onClickAction
    }

    /**
     * Sets the Action invoked when this object is pressed.
     * @param onPressAction The Action that will be executed on a mouse press event.
     * @see onPress
     * @see Action
     */
    infix fun setOnPressAction(onPressAction : Action){
        onPress = onPressAction
    }

    /**
     * Sets the Action invoked when this object is released.
     * @param onReleaseAction The Action that will be executed on a mouse release event.
     * @see onRelease
     * @see Action
     */
    infix fun setOnReleaseAction(onReleaseAction : Action){
        onRelease = onReleaseAction
    }

    /**
     * Sets the Action invoked when this object is entered.
     * @param onEnterAction The Action that will be executed on a mouse enter event.
     * @see onEnter
     * @see Action
     */
    infix fun setOnEnterAction(onEnterAction : Action){
        onEnter = onEnterAction
    }

    /**
     * Sets the Action invoked when this object is exited.
     * @param onExitAction The Action that will be executed on a mouse exit event.
     * @see onExit
     * @see Action
     */
    infix fun setOnExitAction(onExitAction : Action){
        onExit = onExitAction
    }

    /**
     * Sets the Action invoked when this object is dragged.
     * @param onDragAction The Action that will be executed on a mouse drag event.
     * @see onDrag
     * @see Action
     */
    infix fun setOnDragAction(onDragAction : Action){
        onDrag = onDragAction
    }

    /**
     * Sets the Action invoked when the mouse moves on this object.
     * @param onMoveAction The Action that will be executed on a mouse move event.
     * @see onMove
     * @see Action
     */
    infix fun setOnMoveAction(onMoveAction : Action){
        onMove = onMoveAction
    }

    /**
     * Sets the Action invoked when the mouse's wheel moves on this object.
     * @param onWheelMoveAction The MouseWheelAction that will be executed on a mouse wheel event.
     * @see onWheelMoved
     * @see MouseWheelAction
     */
    infix fun setOnWheelMoveAction(onWheelMoveAction : MouseWheelAction){
        onWheelMoved = onWheelMoveAction
    }

    /**
     * Reacts to a mouse click event.
     * @see onClick
     */
    fun mouseClick() = onClick.invoke()

    /**
     * Reacts to a mouse press event.
     * @see onPress
     */
    fun mousePress() = onPress.invoke()

    /**
     * Reacts to a mouse release event.
     * @see onRelease
     */
    fun mouseRelease() = onRelease.invoke()

    /**
     * Reacts to the mouse entering event.
     * @see onEnter
     */
    fun mouseEnter() = onEnter.invoke()

    /**
     * Reacts to the mouse exiting event.
     * @see onExit
     */
    fun mouseExit() = onExit.invoke()

    /**
     * Reacts to a mouse drag event.
     * @see onDrag
     */
    fun mouseDrag() = onDrag.invoke()

    /**
     * Reacts to a mouse movement event.
     * @see onMove
     */
    fun mouseMoved() = onMove.invoke()

    /**
     * Reacts to a mouse wheel movement event.
     * @param units The number of scroll units.
     * @see onWheelMoved
     */
    fun mouseWheelMoved(units : Int) = onWheelMoved.invoke(units)

}