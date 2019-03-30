package display

import main.Action
import main.FRAMEX
import main.FRAMEY
import main.MouseWheelAction
import java.awt.Component
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ESCAPE
import javax.swing.JPanel

/**
 * The general abstraction for a Screen. A Screen is a special kind of JPanel that is used in this Layout.
 * Every scene that appears in the Frame is a Screen.
 * @see CustomContainer
 * @see MouseInteractable
 * @see Displayer
 * @see ScreenManager
 * @see JPanel
 */
abstract class Screen : JPanel(), CustomContainer, MouseInteractable {

    /**
     * The Screen that comes before this one in the program architecture.
     */
    protected abstract var previousScreen : Screen

    /**
     * Loads the bounds of the Screen as fullscreen.
     */
    init{
        setBounds(0, 0, FRAMEX, FRAMEY)
    }

    /**
     * Returns the previous Screen
     * @see previousScreen
     * @return The previous Screen in the program architecture.
     */
    fun previousScreen() : Screen = previousScreen

    override var parts : MutableCollection<Displayer> = mutableListOf()

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
     * Draws the background image of this Screen.
     * @param g the Graphics environment that draws it.
     */
    open fun drawBackground(g : Graphics){}

    /**
     * Saves the state of the Screen when it's removed from the main frame.
     * @see load
     */
    open fun save(){}

    /**
     * Loads the Screen when it's added to the main frame.
     * @see save
     */
    open fun load(){}

    override fun releaseKey(key: Int) {
        if(key == VK_ESCAPE) escape()
    }

    /**
     * Goes back to the previous Screen designed by the previousScreen attribute.
     * @see previousScreen
     * @see ScreenManager.toPreviousScreen
     */
    open fun escape() = ScreenManager.toPreviousScreen()

    override fun mouseClick(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseClick()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseClick()
            is Displayer -> component.mouseClick()
        }
    }

    override fun mousePress(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mousePress()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mousePress()
            is Displayer -> component.mousePress()
        }
    }

    override fun mouseRelease(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseRelease()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseRelease()
            is Displayer -> component.mouseRelease()
        }
    }

    override fun mouseEnter(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseEnter()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseEnter()
            is Displayer -> component.mouseEnter()
        }
    }

    override fun mouseExit(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseExit()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseExit()
            is Displayer -> component.mouseExit()
        }
    }

    override fun mouseDrag(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseDrag()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseDrag()
            is Displayer -> component.mouseDrag()
        }
    }

    override fun mouseMoved(x : Int, y : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseMoved()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseMoved()
            is Displayer -> component.mouseMoved()
        }
    }

    override fun mouseWheelMoved(x : Int, y : Int, units : Int){
        val component : Component = getComponentAt(x, y)
        when(component){
            is Screen -> mouseWheelMoved(units)
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.lowestX(), y - component.lowestY()).mouseWheelMoved(units)
            is Displayer -> component.mouseWheelMoved(units)
        }
    }

}