package display

import java.awt.Component
import java.awt.Container

/**
 * A custom container class, just to get some sweet, sweet methods
 */
interface CustomContainer {

    /**
     * The Displayers contained in this Container
     */
    val parts : ArrayList<Displayer>

    /**
     * Adds a TextDisplayer component to the Screen
     */
    infix fun add(d : Displayer){
        parts.add(d)
        (this as Container).add(d)
        d.onAdd(this)
    }

    /**
     * Removes a TextDisplayer component from the Screen
     */
    infix fun remove(d : Displayer){
        parts.remove(d)
        (this as Container).remove(d)
        d.onRemove(this)
    }

    /**
     * Forces the initialization of the parts of this Screen
     */
    fun initialization(){
        for(part : Displayer in parts){
            part.initialize()
        }
    }

    /**
     * Reacts to a key press event
     */
    fun pressKey(key : Int){}

    /**
     * Reacts to a key release event
     */
    fun releaseKey(key : Int){}

    /**
     * Reacts to a mouse click
     */
    fun mouseClick(source : Component){
        if(source is Displayer) source.mouseClick()
    }

    /**
     * Reacts to a mouse press
     */
    fun mousePress(source : Component){
        if(source is Displayer) source.mousePress()
    }

    /**
     * Reacts to a mouse release
     */
    fun mouseRelease(source : Component){
        if(source is Displayer) source.mouseRelease()
    }

    /**
     * Reacts to the mouse entering
     */
    fun mouseEnter(source : Component){
        if(source is Displayer) source.mouseEnter()
    }

    /**
     * Reacts to the mouse exiting
     */
    fun mouseExit(source : Component){
        if(source is Displayer) source.mouseExit()
    }

    /**
     * Reacts to a mouse drag
     */
    fun mouseDrag(source : Component){
        if(source is Displayer) source.mouseDrag()
    }

    /**
     * Reacts to a mouse movement
     */
    fun mouseMoved(source : Component){
        if(source is Displayer) source.mouseMoved()
    }

    /**
     * Reacts to a mouse wheel movement
     */
    fun mouseWheelMoved(source : Component, units : Int){
        if(source is Displayer) source.mouseWheelMoved(units)
    }

}