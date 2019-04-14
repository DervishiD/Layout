package layout.frame

import layout.Action
import layout.interfaces.LTimerUpdatable

/**
 * A class that manages the transition between screens.
 * @see Screen
 */
internal class LScreenManager : LTimerUpdatable {

    /**
     * The Action executed to change a Screen.
     * @see Action
     * @see Screen
     */
    private val screenChangeAction : Action = {if(currentScreen.nextScreen() != null) setScreen(currentScreen.nextScreen()!!)}

    /**
     * The Screen that is currently displayed.
     * @see Screen
     */
    private var currentScreen : Screen

    /**
     * This LScreenManager's LFrame.
     * @see LFrame
     */
    private val frame : LFrame

    /**
     * The set of pressed keys.
     * @see pressKey
     * @see releaseKey
     */
    private val pressedKeys : MutableSet<Int> = mutableSetOf()

    /**
     * Constructs a LScreenManager for the given LFrame with the given starting Screen.
     * @param frame This LScreenManager's LFrame.
     * @param firstScreen The first Screen.
     * @see LFrame
     * @see Screen
     */
    internal constructor(frame : LFrame, firstScreen : Screen){
        this.frame = frame
        currentScreen = firstScreen
        setCurrentScreenBounds()
        addScreenChangeListener(currentScreen)
    }

    /**
     * Displays the starting Screen.
     * @see Screen
     */
    fun start(){
        currentScreen.load()
        frame.contentPane = currentScreen
    }

    /**
     * Changes the current Screen.
     * The methods starts by saving the old one, then loads the new one, and
     * finally initializes it.
     * @param screen The new displayed Screen.
     * @see Screen
     * @see Screen.save
     * @see Screen.load
     * @see Screen.initialization
     * @see currentScreen
     */
    private infix fun setScreen(screen : Screen){
        removeScreenChangeListener(currentScreen)
        currentScreen.save()
        currentScreen = screen
        setCurrentScreenBounds()
        addScreenChangeListener(currentScreen)
        currentScreen.load()
        frame.contentPane = currentScreen
        currentScreen.initialization()
    }

    /**
     * Sets the current Screen's bounds to fit the frame's.
     * @see currentScreen
     * @see frame
     * @see Screen
     * @see LFrame
     */
    internal fun setCurrentScreenBounds() = currentScreen.setBounds(frame.width, frame.height)

    /**
     * Transmits the event to the current Screen.
     * @param key The code of the pressed key.
     * @see currentScreen
     * @see Screen
     * @see Screen.pressKey
     */
    infix fun pressKey(key : Int){
        pressedKeys.add(key)
        currentScreen.pressKey(key)
    }

    /**
     * Transmits the event to the current Screen.
     * @param key The code of the released key.
     * @see currentScreen
     * @see Screen
     * @see Screen.releaseKey
     */
    infix fun releaseKey(key : Int){
        pressedKeys.remove(key)
        currentScreen.releaseKey(key)
    }

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentScreen
     * @see Screen
     * @see Screen.mouseClick
     */
    fun mouseClick(x : Int, y : Int) = currentScreen.mouseClick(x, y)

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentScreen
     * @see Screen
     * @see Screen.mousePress
     */
    fun mousePress(x : Int, y : Int) = currentScreen.mousePress(x, y)

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentScreen
     * @see Screen
     * @see Screen.mouseRelease
     */
    fun mouseRelease(x : Int, y : Int) = currentScreen.mouseRelease(x, y)

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentScreen
     * @see Screen
     * @see Screen.mouseEnter
     */
    fun mouseEnter(x : Int, y : Int) = currentScreen.mouseEnter(x, y)

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentScreen
     * @see Screen
     * @see Screen.mouseExit
     */
    fun mouseExit(x : Int, y : Int) = currentScreen.mouseExit(x, y)

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentScreen
     * @see Screen
     * @see Screen.mouseMoved
     */
    fun mouseMoved(x : Int, y : Int) = currentScreen.mouseMoved(x, y)

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentScreen
     * @see Screen
     * @see Screen.mouseDrag
     */
    fun mouseDrag(x : Int, y : Int) = currentScreen.mouseDrag(x, y)

    /**
     * Transmits the event to the current Screen.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @param units The number of units scrolled.
     * @see currentScreen
     * @see Screen
     * @see Screen.mouseClick
     */
    fun mouseWheelMoved(x : Int, y : Int, units : Int) = currentScreen.mouseWheelMoved(x, y, units)

    /**
     * Adds a listener to the given Screen's nextScreen property.
     * @see currentScreen
     * @see Screen
     * @see Screen.nextScreen
     */
    private infix fun addScreenChangeListener(screen : Screen) = screen.addScreenChangeListener(this, screenChangeAction)

    /**
     * Removes a listener from the given Screen's nextScreen property.
     * @see currentScreen
     * @see Screen
     * @see Screen.nextScreen
     */
    private infix fun removeScreenChangeListener(screen : Screen) = screen.removeScreenChangeListener(this)

    override fun onTimerTick(): LTimerUpdatable {
        currentScreen.onTimerTick()
        return super.onTimerTick()
    }

}
