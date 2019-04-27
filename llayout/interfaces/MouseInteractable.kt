package llayout.interfaces

import llayout.Action
import llayout.MouseWheelAction

/**
 * An interface implemented by objects that interact with the mouse.
 */
interface MouseInteractable {

    /**
     * Action executed on a mouse click.
     * @see Action
     */
    var onMouseClick : Action

    /**
     * Action executed on a mouse press.
     * @see Action
     */
    var onMousePress : Action

    /**
     * Action executed on a mouse release.
     * @see Action
     */
    var onMouseRelease : Action

    /**
     * Action executed when the mouse enters the object.
     * @see Action
     */
    var onMouseEnter : Action

    /**
     * Action executed when the mouse exits the object.
     * @see Action
     */
    var onMouseExit : Action

    /**
     * Action executed on a mouse drag.
     * @see Action
     */
    var onMouseDrag : Action

    /**
     * Action executed on a mouse movement on the object.
     * @see Action
     */
    var onMouseMove : Action

    /**
     * Action executed when the mouse wheel moves on this object.
     * @see MouseWheelAction
     */
    var onMouseWheelMoved : MouseWheelAction

    /**
     * Sets the Action invoked when this object is clicked.
     * @param onClickAction The Action that will be executed on a mouse click event.
     * @see onClick
     * @see Action
     */
    infix fun setOnMouseClickAction(onClickAction : Action){
        onMouseClick = onClickAction
    }

    /**
     * Sets the Action invoked when this object is pressed.
     * @param onPressAction The Action that will be executed on a mouse press event.
     * @see onPress
     * @see Action
     */
    infix fun setOnMousePressAction(onPressAction : Action){
        onMousePress = onPressAction
    }

    /**
     * Sets the Action invoked when this object is released.
     * @param onReleaseAction The Action that will be executed on a mouse release event.
     * @see onRelease
     * @see Action
     */
    infix fun setOnMouseReleaseAction(onReleaseAction : Action){
        onMouseRelease = onReleaseAction
    }

    /**
     * Sets the Action invoked when this object is entered.
     * @param onEnterAction The Action that will be executed on a mouse enter event.
     * @see onEnter
     * @see Action
     */
    infix fun setOnMouseEnterAction(onEnterAction : Action){
        onMouseEnter = onEnterAction
    }

    /**
     * Sets the Action invoked when this object is exited.
     * @param onExitAction The Action that will be executed on a mouse exit event.
     * @see onExit
     * @see Action
     */
    infix fun setOnMouseExitAction(onExitAction : Action){
        onMouseExit = onExitAction
    }

    /**
     * Sets the Action invoked when this object is dragged.
     * @param onDragAction The Action that will be executed on a mouse drag event.
     * @see onDrag
     * @see Action
     */
    infix fun setOnMouseDragAction(onDragAction : Action){
        onMouseDrag = onDragAction
    }

    /**
     * Sets the Action invoked when the mouse moves on this object.
     * @param onMoveAction The Action that will be executed on a mouse move event.
     * @see onMove
     * @see Action
     */
    infix fun setOnMouseMoveAction(onMoveAction : Action){
        onMouseMove = onMoveAction
    }

    /**
     * Sets the Action invoked when the mouse's wheel moves on this object.
     * @param onWheelMoveAction The MouseWheelAction that will be executed on a mouse wheel event.
     * @see onWheelMoved
     * @see MouseWheelAction
     */
    infix fun setOnMouseWheelMoveAction(onWheelMoveAction : MouseWheelAction){
        onMouseWheelMoved = onWheelMoveAction
    }

    /**
     * Reacts to a mouse click event.
     * @see onClick
     */
    fun mouseClick() = onMouseClick.invoke()

    /**
     * Reacts to a mouse press event.
     * @see onPress
     */
    fun mousePress() = onMousePress.invoke()

    /**
     * Reacts to a mouse release event.
     * @see onRelease
     */
    fun mouseRelease() = onMouseRelease.invoke()

    /**
     * Reacts to the mouse entering event.
     * @see onEnter
     */
    fun mouseEnter() = onMouseEnter.invoke()

    /**
     * Reacts to the mouse exiting event.
     * @see onExit
     */
    fun mouseExit() = onMouseExit.invoke()

    /**
     * Reacts to a mouse drag event.
     * @see onDrag
     */
    fun mouseDrag() = onMouseDrag.invoke()

    /**
     * Reacts to a mouse movement event.
     * @see onMove
     */
    fun mouseMoved() = onMouseMove.invoke()

    /**
     * Reacts to a mouse wheel movement event.
     * @param units The number of scroll units.
     * @see onWheelMoved
     */
    infix fun mouseWheelMoved(units : Int) = onMouseWheelMoved.invoke(units)

}