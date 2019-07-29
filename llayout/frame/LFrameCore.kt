package llayout.frame

import llayout.interfaces.LTimerUpdatable
import java.awt.Frame
import java.awt.Toolkit
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.WindowConstants

/**
 * A LFrame's core, extending JFrame.
 * @see LTimerUpdatable
 * @see JFrame
 * @since LLayout 1
 */
internal class LFrameCore : JFrame, LTimerUpdatable {

    private companion object{

        /**
         * The computer's screen width.
         * @since LLayout 1
         */
        private val SCREEN_WIDTH : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()

        /**
         * The computer's screen height.
         * @since LLayout 1
         */
        private val SCREEN_HEIGHT : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

        /**
         * The default x position of a LFrameCore.
         * @since LLayout 1
         */
        private val DEFAULT_X : Int = SCREEN_WIDTH / 2

        /**
         * The default y position of a LFrameCore.
         * @since LLayout 1
         */
        private val DEFAULT_Y : Int = SCREEN_HEIGHT / 2

        /**
         * The default width of a LFrameCore.
         * @since LLayout 1
         */
        private val DEFAULT_WIDTH : Int = SCREEN_WIDTH * 2 / 3

        /**
         * The default height of a LFrameCore.
         * @since LLayout 1
         */
        private val DEFAULT_HEIGHT : Int = SCREEN_HEIGHT * 2 / 3

    }

    /**
     * This LFrame's LSceneManager.
     * @see LSceneManager
     * @since LLayout 1
     */
    private val screenManager : LSceneManager

    /**
     * This LFrame's LTimer.
     * @see LTimer
     * @since LLayout 1
     */
    private val timer : LTimer

    /**
     * True if the timer still runs when the frame is hidden.
     * @since LLayout 1
     */
    private var runningIfHidden : Boolean = false

    /**
     * The center's x coordinate.
     * @since LLayout 1
     */
    private var centerX : Int = DEFAULT_X

    /**
     * The center's y coordinate.
     * @since LLayout 1
     */
    private var centerY : Int = DEFAULT_Y

    /**
     * The width of this LFrameCore.
     * @since LLayout 1
     */
    private var w : Int = DEFAULT_WIDTH

    /**
     * This LFrameCore's height.
     * @since LLayout 1
     */
    private var h : Int = DEFAULT_HEIGHT

    init{
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isUndecorated = false
        isResizable = true
        setBounds()
    }

    internal constructor(scene : LSceneCore) : super(){
        screenManager = LSceneManager(this, scene)
        timer = LTimer(this)
        addResizeListener()
    }

    /**
     * Adds a ComponentListener that reacts on a resize.
     * @since LLayout 1
     */
    private fun addResizeListener(){
        addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                screenManager.resize()
            }
        })
    }

    /**
     * Sets the bounds of this LFrameCore.
     * @since LLayout 1
     */
    private fun setBounds() = setBounds(centerX - w / 2, centerY - h / 2, w, h)

    /**
     * Shows this LFrame.
     * @return this
     * @since LLayout 1
     */
    fun setVisible() : LFrameCore{
        isVisible = true
        requestFocus()
        timer.start()
        return this
    }

    /**
     * Hides this LFrame.
     * @return this
     * @since LLayout 1
     */
    fun setInvisible() : LFrameCore{
        isVisible = false
        transferFocus()
        if(!runningIfHidden) timer.pause()
        return this
    }

    /**
     * True if the frame is hidden.
     * @since LLayout 1
     */
    fun isHidden() : Boolean = !isVisible

    /**
     * Pauses the timer.
     * @return this
     * @since LLayout 1
     */
    fun pause() : LFrameCore{
        timer.pause()
        return this
    }

    /**
     * Resumes the timer.
     * @return this
     * @since LLayout 1
     */
    fun resume() : LFrameCore{
        timer.start()
        return this
    }

    /**
     * Closes this LFrame.
     * @return this
     * @since LLayout 1
     */
    fun close() : LFrameCore{
        setInvisible()
        dispatchEvent(WindowEvent(this, WindowEvent.WINDOW_CLOSING))
        return this
    }

    /**
     * Starts the LTimer of this LFrame and calls the setVisible() method.
     * @see setVisible
     * @see LSceneManager.start
     * @see LTimer.start
     * @since LLayout 1
     */
    fun run(){
        screenManager.start()
        setVisible()
    }

    /**
     * Changes the timer period.
     * @return this
     * @throws IllegalArgumentException If the period is not strictly positive.
     * @since LLayout 1
     */
    fun changeTimerPeriod(period : Int) : LFrameCore {
        if(period <= 0) throw IllegalArgumentException("Negative timer period")
        timer.setPeriod(period)
        return this
    }

    override fun onTimerTick() {
        screenManager.onTimerTick()
    }

    /**
     * Sets the center's x coordinate.
     * @since LLayout 1
     */
    fun setCenterX(x : Int){
        centerX = x
        setBounds()
    }

    /**
     * Sets the center's y coordinate.
     * @since LLayout 1
     */
    fun setCenterY(y : Int){
        centerY = y
        setBounds()
    }

    /**
     * Sets this LFrameCore's width.
     * @throws IllegalArgumentException If the width is not strictly positive.
     * @since LLayout 1
     */
    fun setW(width : Int){
        if(width <= 0) throw IllegalArgumentException("A LFrameCore can't have a negative width.")
        w = width
        setBounds()
    }

    /**
     * Sets this LFrameCore's height.
     * @throws IllegalArgumentException If the height is not strictly positive.
     * @since LLayout 1
     */
    fun setH(height : Int){
        if(height <= 0) throw IllegalArgumentException("A LFrameCore can't have a negative height.")
        h = height
        setBounds()
    }

    /**
     * Sets the frame to fullscreen.
     * @since LLayout 1
     */
    fun setFullscreen(){
        extendedState = Frame.MAXIMIZED_BOTH
        isUndecorated = true
    }

    /**
     * Sets the frame to non fullscreen.
     * @since LLayout 1
     */
    fun setNotFullScreen(){
        extendedState = Frame.NORMAL
        isUndecorated = false
    }

    /**
     * The timer now still runs if the frame is hidden.
     * @since LLayout 1
     */
    fun setRunningIfHidden(){
        runningIfHidden = true
    }

    /**
     * The timer now stops while the frame is hidden.
     * @since LLayout 1
     */
    fun setNotRunningIfHidden(){
        runningIfHidden = false
    }

    /**
     * Sets a new scene.
     * @since LLayout 1
     */
    fun setNextScene(scene : LScene){
        screenManager.setScene(scene.core())
    }

}