package llayout.frame

import llayout.interfaces.LTimerUpdatable

/**
 * A class that manages the transition between screens.
 * @see LSceneCore
 */
internal class LSceneManager : LTimerUpdatable {

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
    fun setScene(LScene : LSceneCore){
        currentLScene.save()
        currentLScene = LScene
        setCurrentScreenBounds()
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

    override fun onTimerTick() {
        currentLScene.onTimerTick()
    }

}
