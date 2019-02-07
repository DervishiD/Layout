package detection

import java.awt.Component

/**
 * A class that represents a mouse event
 */
internal class MouseAction(target : Component, behaviour: MouseBehaviour) {

    /**
     * The Component on which the action has been detected.
     */
    private val target : Component = target
    /**
     * The observed behaviour of the mouse.
     */
    private val behaviour : MouseBehaviour = behaviour

    /**
     * Returns the Component on which the action has been detected.
     */
    internal fun target() : Component = target
    /**
     * returns the observed behaviour of the mouse.
     */
    internal fun behaviour() : MouseBehaviour = behaviour

}