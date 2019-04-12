package display

import display.frame.LFrame
import display.frame.LScreenManager
import java.awt.Component
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ESCAPE
import javax.swing.JPanel
import main.Action
import main.MouseWheelAction
import utilities.LProperty

/**
 * The general abstraction for a Screen. A Screen is a special kind of JPanel that is used in this Layout.
 * Every scene that appears in a LFrame is a Screen.
 * @see CustomContainer
 * @see MouseInteractable
 * @see Displayer
 * @see LScreenManager
 * @see JPanel
 * @see LFrame
 */
abstract class Screen : JPanel(), CustomContainer, MouseInteractable, LTimerUpdatable {

    override var w : LProperty<Int> = LProperty(0)

    override var h : LProperty<Int> = LProperty(0)

    /**
     * The Screen that comes before this one in the program architecture.
     */
    protected abstract var previousScreen : Screen

    /**
     * The next Screen, as a LProperty.
     * @see setNextScreen
     * @see LProperty
     */
    private var nextScreen : LProperty<Screen?> = LProperty(null)

    /**
     * Returns the previous Screen
     * @return The previous Screen in the program architecture.
     * @see previousScreen
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

    /**
     * Sets the next Screen to the given value.
     * @param nextScreen The Screen that appears on the core LFrame after this one.
     * @see nextScreen
     * @see LFrame
     */
    protected infix fun setNextScreen(nextScreen : Screen){
        this.nextScreen.value = nextScreen
    }

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
     * @see setNextScreen
     */
    open fun escape() = setNextScreen(previousScreen)

    /**
     * Adds a Screen change listener to the nextScreen LProperty.
     * @param key The key of the added listener.
     * @param action The Action executed by the listener.
     * @see nextScreen
     * @see LProperty
     * @see Action
     */
    fun addScreenChangeListener(key : Any?, action : Action) = nextScreen.addListener(key, action)

    /**
     * Removes a Screen change listener from the nextScreen LProperty.
     * @param key The key of the listener to remove.
     * @see LProperty
     * @see nextScreen
     */
    fun removeScreenChangeListener(key : Any?) = nextScreen.removeListener(key)

    fun setBounds(width : Int, height : Int) = setBounds(0, 0, width, height)

    override fun setBounds(x: Int, y: Int, width: Int, height: Int) {
        super.setBounds(x, y, width, height)
        w.value = width
        h.value = height
    }

    /**
     * The next Screen.
     * @see nextScreen
     */
    fun nextScreen() : Screen? = nextScreen.value

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

    override fun onTimerTick(): LTimerUpdatable {
        for(d : Displayer in parts){
            d.onTimerTick()
        }
        return super.onTimerTick()
    }

}