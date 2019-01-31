package detection

import java.awt.Component

/**
 * A class that represents a mouse event
 */
class MouseAction(target : Component, behaviour: MouseBehaviour) {

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
    public fun target() : Component = target
    /**
     * returns the observed behaviour of the mouse.
     */
    public fun behaviour() : MouseBehaviour = behaviour

}