package display.screens

import display.*
import main.Action
import main.FRAMEX
import main.FRAMEY
import main.MouseWheelAction
import java.awt.Component
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ESCAPE
import javax.swing.JPanel

/**
 * The general abstraction for a Screen
 */
abstract class Screen : JPanel(), CustomContainer, MouseInteractable {

    /**
     * The previous Screen in the Screen tree
     */
    protected abstract var previousScreen : Screen

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
    override var parts : ArrayList<Displayer> = ArrayList()

    override var onClick : Action = {}
    override var onPress : Action = {}
    override var onRelease : Action = {}
    override var onEnter : Action = {}
    override var onExit : Action = {}
    override var onDrag : Action = {}
    override var onMove : Action = {}
    override var onWheelMoved : MouseWheelAction = {_ -> }

    public override fun paintComponent(g: Graphics?) {
        for(part : Displayer in parts){
            part.paintComponent(g)
        }
        g!!.clearRect(0, 0, width, height)
        drawBackground(g)
    }

    /**
     * Draws the background of this Screen
     */
    open fun drawBackground(g : Graphics){}

    /**
     * To save the current state of the Screen
     */
    open fun save(){}

    /**
     * To load this screen
     */
    open fun load(){}

    override fun releaseKey(key: Int) {
        if(key == VK_ESCAPE) escape()
    }

    /**
     * Back to the previous screen
     */
    open fun escape() = ScreenManager.toPreviousScreen()

    override fun mouseClick(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseClick()
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseClick()
            is Displayer -> component.mouseClick()
        }
    }

    override fun mousePress(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mousePress()
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mousePress()
            is Displayer -> component.mousePress()
        }
    }

    override fun mouseRelease(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseRelease()
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseRelease()
            is Displayer -> component.mouseRelease()
        }
    }

    override fun mouseEnter(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseEnter()
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseEnter()
            is Displayer -> component.mouseEnter()
        }
    }

    override fun mouseExit(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseExit()
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseExit()
            is Displayer -> component.mouseExit()
        }
    }

    override fun mouseDrag(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseDrag()
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseDrag()
            is Displayer -> component.mouseDrag()
        }
    }

    override fun mouseMoved(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseMoved()
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseMoved()
            is Displayer -> component.mouseMoved()
        }
    }

    override fun mouseWheelMoved(x : Int, y : Int, units : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseWheelMoved(units)
            is DisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseWheelMoved(units)
            is Displayer -> component.mouseWheelMoved(units)
        }
    }

}