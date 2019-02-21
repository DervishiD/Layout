package display

import main.Action
import main.MouseWheelAction

/**
 * An interface implemented by objects that interact with the mouse
 */
interface MouseInteractable {

    /**
     * Action executed on a mouse click
     */
    var onClick : Action
    /**
     * Action executed on a mouse press
     */
    var onPress : Action
    /**
     * Action executed on a mouse release
     */
    var onRelease : Action
    /**
     * Action executed when the mouse enters the object
     */
    var onEnter : Action
    /**
     * Action executed when the mouse exits the object
     */
    var onExit : Action
    /**
     * Action executed on a mouse drag
     */
    var onDrag : Action
    /**
     * Action executed on a mouse movement on the object
     */
    var onMove : Action
    /**
     * Action executed when the mouse wheel moves on this object
     */
    var onWheelMoved : MouseWheelAction

    /**
     * Sets the Action invoked when this object is clicked
     */
    infix fun setOnClickAction(onClickAction : Action){
        onClick = onClickAction
    }

    /**
     * Sets the Action invoked when this object is pressed
     */
    infix fun setOnPressAction(onPressAction : Action){
        onPress = onPressAction
    }

    /**
     * Sets the Action invoked when this object is released
     */
    infix fun setOnReleaseAction(onReleaseAction : Action){
        onRelease = onReleaseAction
    }

    /**
     * Sets the Action invoked when this object is entered
     */
    infix fun setOnEnterAction(onEnterAction : Action){
        onEnter = onEnterAction
    }

    /**
     * Sets the Action invoked when this object is exited
     */
    infix fun setOnExitAction(onExitAction : Action){
        onExit = onExitAction
    }

    /**
     * Sets the Action invoked when this object is dragged
     */
    infix fun setOnDragAction(onDragAction : Action){
        onDrag = onDragAction
    }

    /**
     * Sets the Action invoked when the mouse moves on this object
     */
    infix fun setOnMoveAction(onMoveAction : Action){
        onMove = onMoveAction
    }

    /**
     * Sets the Action invoked when the mouse's wheel moves on this object
     */
    infix fun setOnWheelMoveAction(onWheelMoveAction : MouseWheelAction){
        onWheelMoved = onWheelMoveAction
    }

    /**
     * Reacts to a mouse click
     */
    fun mouseClick() = onClick.invoke()

    /**
     * Reacts to a mouse press
     */
    fun mousePress() = onPress.invoke()

    /**
     * Reacts to a mouse release
     */
    fun mouseRelease() = onRelease.invoke()

    /**
     * Reacts to the mouse entering
     */
    fun mouseEnter() = onEnter.invoke()

    /**
     * Reacts to the mouse exiting
     */
    fun mouseExit() = onExit.invoke()

    /**
     * Reacts to a mouse drag
     */
    fun mouseDrag() = onDrag.invoke()

    /**
     * Reacts to a mouse movement
     */
    fun mouseMoved() = onMove.invoke()

    /**
     * Reacts to a mouse wheel movement
     */
    fun mouseWheelMoved(units : Int) = onWheelMoved.invoke(units)

}