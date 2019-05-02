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