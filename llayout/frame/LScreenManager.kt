package llayout.frame

import llayout.Action
import llayout.interfaces.LTimerUpdatable

/**
 * A class that manages the transition between screens.
 * @see LScene
 */
internal class LScreenManager : LTimerUpdatable {

    /**
     * The Action executed to change a LScene.
     * @see Action
     * @see LScene
     */
    private val screenChangeAction : Action = {if(currentLScene.nextScreen() != null) setScreen(currentLScene.nextScreen()!!)}

    /**
     * The LScene that is currently displayed.
     * @see LScene
     */
    private var currentLScene : LScene

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
     * Constructs a LScreenManager for the given LFrame with the given starting LScene.
     * @param frame This LScreenManager's LFrame.
     * @param firstLScene The first LScene.
     * @see LFrame
     * @see LScene
     */
    internal constructor(frame : LFrame, firstLScene : LScene){
        this.frame = frame
        currentLScene = firstLScene
        setCurrentScreenBounds()
        addScreenChangeListener(currentLScene)
    }

    /**
     * Displays the starting LScene.
     * @see LScene
     */
    fun start(){
        currentLScene.load()
        frame.contentPane = currentLScene
    }

    /**
     * Changes the current LScene.
     * The methods starts by saving the old one, then loads the new one, and
     * finally initializes it.
     * @param LScene The new displayed LScene.
     * @see LScene
     * @see LScene.save
     * @see LScene.load
     * @see LScene.initialization
     * @see currentLScene
     */
    private infix fun setScreen(LScene : LScene){
        removeScreenChangeListener(currentLScene)
        currentLScene.save()
        currentLScene = LScene
        setCurrentScreenBounds()
        addScreenChangeListener(currentLScene)
        currentLScene.load()
        frame.contentPane = currentLScene
        currentLScene.initialization()
    }

    /**
     * Sets the current LScene's bounds to fit the frame's.
     * @see currentLScene
     * @see frame
     * @see LScene
     * @see LFrame
     */
    private fun setCurrentScreenBounds() = currentLScene.setBounds(frame.width, frame.height)

    internal fun resize() = setCurrentScreenBounds()

    /**
     * Transmits the event to the current LScene.
     * @param key The code of the pressed key.
     * @see currentLScene
     * @see LScene
     * @see LScene.pressKey
     */
    infix fun pressKey(key : Int){
        pressedKeys.add(key)
        currentLScene.pressKey(key)
    }

    /**
     * Transmits the event to the current LScene.
     * @param key The code of the released key.
     * @see currentLScene
     * @see LScene
     * @see LScene.releaseKey
     */
    infix fun releaseKey(key : Int){
        pressedKeys.remove(key)
        currentLScene.releaseKey(key)
    }

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentLScene
     * @see LScene
     * @see LScene.mouseClick
     */
    fun mouseClick(x : Int, y : Int) = currentLScene.mouseClick(x, y)

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentLScene
     * @see LScene
     * @see LScene.mousePress
     */
    fun mousePress(x : Int, y : Int) = currentLScene.mousePress(x, y)

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentLScene
     * @see LScene
     * @see LScene.mouseRelease
     */
    fun mouseRelease(x : Int, y : Int) = currentLScene.mouseRelease(x, y)

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentLScene
     * @see LScene
     * @see LScene.mouseEnter
     */
    fun mouseEnter(x : Int, y : Int) = currentLScene.mouseEnter(x, y)

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentLScene
     * @see LScene
     * @see LScene.mouseExit
     */
    fun mouseExit(x : Int, y : Int) = currentLScene.mouseExit(x, y)

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentLScene
     * @see LScene
     * @see LScene.mouseMoved
     */
    fun mouseMoved(x : Int, y : Int) = currentLScene.mouseMoved(x, y)

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @see currentLScene
     * @see LScene
     * @see LScene.mouseDrag
     */
    fun mouseDrag(x : Int, y : Int) = currentLScene.mouseDrag(x, y)

    /**
     * Transmits the event to the current LScene.
     * @param x The x coordinate of the event.
     * @param y The y coordinate of the event.
     * @param units The number of units scrolled.
     * @see currentLScene
     * @see LScene
     * @see LScene.mouseClick
     */
    fun mouseWheelMoved(x : Int, y : Int, units : Int) = currentLScene.mouseWheelMoved(x, y, units)

    /**
     * Adds a listener to the given LScene's nextScreen property.
     * @see currentLScene
     * @see LScene
     * @see LScene.nextScreen
     */
    private infix fun addScreenChangeListener(LScene : LScene) = LScene.addScreenChangeListener(this, screenChangeAction)

    /**
     * Removes a listener from the given LScene's nextScreen property.
     * @see currentLScene
     * @see LScene
     * @see LScene.nextScreen
     */
    private infix fun removeScreenChangeListener(LScene : LScene) = LScene.removeScreenChangeListener(this)

    override fun onTimerTick(): LTimerUpdatable {
        currentLScene.onTimerTick()
        return super.onTimerTick()
    }

}
