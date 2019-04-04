package display.frame

import display.Screen
import main.Action

/**
 * A class that manages the transition between screens.
 * @see Screen
 */
internal class LScreenManager {

    companion object {
        private const val SCREEN_CHANGE_LISTENER_KEY : String = "ScreenManager::SCREEN_CHANGE_LISTENER_KEY"
    }

    private val screenChangeAction : Action = {if(currentScreen.nextScreen.value != null) setScreen(currentScreen.nextScreen.value!!)}

    /**
     * The Screen that is currently displayed.
     * @see Screen
     */
    private var currentScreen : Screen

    private val frame : LFrame

    /**
     * The set of pressed keys.
     * @see pressKey
     * @see releaseKey
     */
    private val pressedKeys : MutableSet<Int> = mutableSetOf()

    internal constructor(frame : LFrame, firstScreen : Screen){
        this.frame = frame
        currentScreen = firstScreen
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
     * Goes to the previous Screen.
     * @see Screen
     * @see Screen.previousScreen
     * @see setScreen
     */
    fun toPreviousScreen(){
        setScreen(currentScreen.previousScreen())
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
    infix fun setScreen(screen : Screen){
        removeScreenChangeListener(currentScreen)
        currentScreen.save()
        currentScreen = screen
        addScreenChangeListener(currentScreen)
        currentScreen.load()
        frame.contentPane = currentScreen
        currentScreen.initialization()
    }

    /**
     * Escapes the current screen.
     * @see currentScreen
     * @see Screen
     * @see Screen.escape
     */
    fun escape() = currentScreen.escape()

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

    private infix fun addScreenChangeListener(screen : Screen) = screen.nextScreen.addListener(SCREEN_CHANGE_LISTENER_KEY, screenChangeAction)

    private infix fun removeScreenChangeListener(screen : Screen) = screen.nextScreen.removeListener(SCREEN_CHANGE_LISTENER_KEY)

}
