package display.screens

import display.Displayer
import display.ScreenManager
import main.FRAMEX
import main.FRAMEY
import java.awt.Color.WHITE
import java.awt.Component
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ESCAPE
import javax.swing.JPanel

/**
 * The general abstraction for a Screen
 */
abstract class Screen : JPanel() {

    /**
     * The previous Screen in the Screen tree
     */
    protected lateinit var previousScreen : Screen

    init{
        setBounds(0, 0, FRAMEX, FRAMEY)
    }

    /**
     * Returns the previous Screen
     */
    fun previousScreen() : Screen = previousScreen

    /**
     * The list of the components of this Screen
     */
    private var parts : ArrayList<Displayer> = ArrayList<Displayer>()

    /**
     * Adds a TextDisplayer component to the Screen
     */
    infix fun add(d : Displayer){
        parts.add(d)
        (this as JPanel).add(d)
        d.onAdd(this)
    }

    /**
     * Removes a TextDisplayer component from the Screen
     */
    infix fun remove(d : Displayer){
        parts.remove(d)
        (this as JPanel).remove(d)
        d.onRemove(this)
    }

    /**
     * Forces the initialization of the parts of this Screen
     */
    fun initialize(){
        for(part : Displayer in parts){
            part.initialize()
        }
    }

    public override fun paintComponent(g: Graphics?) {
        g!!.color = WHITE
        g.fillRect(0, 0, FRAMEX, FRAMEY)
        for(part : Displayer in parts){
            part.paintComponent(g)
        }
        super.paintComponent(g)
    }

    /**
     * Reacts to a key press event
     */
    open fun pressKey(key : Int){}

    /**
     * Reacts to a key release event
     */
    open fun releaseKey(key : Int){
        if(key == VK_ESCAPE) escape()
    }

    /**
     * To save the current state of the Screen
     */
    open fun save(){}

    /**
     * To load this screen
     */
    open fun load(){}

    /**
     * Back to the previous screen
     */
    open fun escape() = ScreenManager.toPreviousScreen()

    /**
     * Reacts to a mouse click
     */
    open fun mouseClick(source : Component){
        if(source is Displayer) source.mouseClick()
    }

    /**
     * Reacts to a mouse press
     */
    open fun mousePress(source : Component){
        if(source is Displayer) source.mousePress()
    }

    /**
     * Reacts to a mouse release
     */
    open fun mouseRelease(source : Component){
        if(source is Displayer) source.mouseRelease()
    }

    /**
     * Reacts to the mouse entering
     */
    open fun mouseEnter(source : Component){
        if(source is Displayer) source.mouseEnter()
    }

    /**
     * Reacts to the mouse exiting
     */
    open fun mouseExit(source : Component){
        if(source is Displayer) source.mouseExit()
    }

    /**
     * Reacts to a mouse drag
     */
    open fun mouseDrag(source : Component){
        if(source is Displayer) source.mouseDrag()
    }

}