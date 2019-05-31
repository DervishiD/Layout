package llayout.frame

import llayout.Action
import llayout.interfaces.LTimerUpdatable

/**
 * A class that manages the transition between screens.
 * @see LSceneCore
 */
internal class LSceneManager : LTimerUpdatable {

    /**
     * The Action executed to change a LSceneCore.
     * @see Action
     * @see LSceneCore
     */
    private val screenChangeAction : Action = {if(currentLScene.nextScreen() != null) setScreen(currentLScene.nextScreen()!!)}

    /**
     * The LSceneCore that is currently displayed.
     * @see LSceneCore
     */
    private var currentLScene : LSceneCore

    /**
     * This LSceneManager's LFrame.
     * @see LFrameCore
     */
    private val frame : LFrameCore

    /**
     * The set of pressed keys.
     * @see pressKey
     * @see releaseKey
     */
    private val pressedKeys : MutableSet<Int> = mutableSetOf()

    /**
     * Constructs a LSceneManager for the given LFrame with the given starting LSceneCore.
     * @param frame This LSceneManager's LFrame.
     * @param firstLScene The first LSceneCore.
     * @see LFrameCore
     * @see LSceneCore
     */
    internal constructor(frame : LFrameCore, firstLScene : LSceneCore){
        this.frame = frame
        currentLScene = firstLScene
        setCurrentScreenBounds()
        addScreenChangeListener(currentLScene)
    }

    /**
     * Displays the starting LSceneCore.
     * @see LSceneCore
     */
    fun start(){
        frame.contentPane = currentLScene
        currentLScene.load()
    }

    /**
     * Changes the current LSceneCore.
     * The methods starts by saving the old one, then loads the new one, and
     * finally initializes it.
     * @param LScene The new displayed LSceneCore.
     * @see LScene
     * @see LScene.save
     * @see LScene.load
     * @see LScene.initialization
     * @see currentLScene
     */
    private infix fun setScreen(LScene : LSceneCore){
        removeScreenChangeListener(currentLScene)
        currentLScene.save()
        currentLScene = LScene
        setCurrentScreenBounds()
        addScreenChangeListener(currentLScene)
        frame.contentPane = currentLScene
        currentLScene.load()
        currentLScene.initialization()
    }

    /**
     * Sets the current LSceneCore's bounds to fit the frame's.
     * @see currentLScene
     * @see frame
     * @see LSceneCore
     * @see LFrameCore
     */
    private fun setCurrentScreenBounds() = currentLScene.setBounds(frame.rootPane.width, frame.rootPane.height)

    internal fun resize() = setCurrentScreenBounds()

    /**
     * Adds a listener to the given LSceneCore's nextScreen property.
     * @see currentLScene
     * @see LScene
     * @see LScene.nextScreen
     */
    private infix fun addScreenChangeListener(LScene : LSceneCore) = LScene.addScreenChangeListener(this, screenChangeAction)

    /**
     * Removes a listener from the given LSceneCore's nextScreen property.
     * @see currentLScene
     * @see LScene
     * @see LScene.nextScreen
     */
    private infix fun removeScreenChangeListener(LScene : LSceneCore) = LScene.removeScreenChangeListener(this)

    override fun onTimerTick() {
        currentLScene.onTimerTick()
    }

}
