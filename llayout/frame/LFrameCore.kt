package llayout.frame

import llayout.interfaces.LTimerUpdatable
import java.awt.Frame
import java.awt.Toolkit
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.WindowConstants

internal class LFrameCore : JFrame, LTimerUpdatable {

    private companion object{

        /**
         * The computer's screen width.
         */
        private val SCREEN_WIDTH : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()

        /**
         * The computer's screen height.
         */
        private val SCREEN_HEIGHT : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

        private val DEFAULT_X : Int = SCREEN_WIDTH / 2

        private val DEFAULT_Y : Int = SCREEN_HEIGHT / 2

        private val DEFAULT_WIDTH : Int = SCREEN_WIDTH * 2 / 3

        private val DEFAULT_HEIGHT : Int = SCREEN_HEIGHT * 2 / 3

    }

    /**
     * This LFrame's LSceneManager.
     * @see LSceneManager
     */
    private val screenManager : LSceneManager

    /**
     * This LFrame's LTimer.
     * @see LTimer
     */
    private val timer : LTimer

    private var runningIfHidden : Boolean = false

    private var centerX : Int = DEFAULT_X

    private var centerY : Int = DEFAULT_Y

    private var w : Int = DEFAULT_WIDTH

    private var h : Int = DEFAULT_HEIGHT

    init{
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isUndecorated = false
        isResizable = true
        setBounds()
    }

    constructor(scene : LSceneCore) : super(){
        screenManager = LSceneManager(this, scene)
        timer = LTimer(this)
        addResizeListener()
    }

    private fun addResizeListener(){
        addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                screenManager.resize()
            }
        })
    }

    private fun setBounds() = setBounds(centerX - w / 2, centerY - h / 2, w, h)

    /**
     * Shows this LFrame.
     */
    fun setVisible() : LFrameCore{
        isVisible = true
        requestFocus()
        timer.start()
        return this
    }

    /**
     * Hides this LFrame.
     */
    fun setInvisible() : LFrameCore{
        isVisible = false
        transferFocus()
        if(!runningIfHidden) timer.pause()
        return this
    }

    fun isHidden() : Boolean = !isVisible

    fun pause() : LFrameCore{
        timer.pause()
        return this
    }

    fun resume() : LFrameCore{
        timer.start()
        return this
    }

    /**
     * Closes this LFrame.
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
     */
    fun run(){
        screenManager.start()
        setVisible()
    }

    fun changeTimerPeriod(period : Int) : LFrameCore {
        if(period <= 0) throw IllegalArgumentException("Negative timer period")
        timer.setPeriod(period)
        return this
    }

    override fun onTimerTick() {
        screenManager.onTimerTick()
    }

    fun setCenterX(x : Int){
        centerX = x
        setBounds()
    }

    fun setCenterY(y : Int){
        centerY = y
        setBounds()
    }

    fun setW(width : Int){
        if(width <= 0) throw IllegalArgumentException("A LFrameCore can't have a negative width.")
        w = width
        setBounds()
    }

    fun setH(height : Int){
        if(height <= 0) throw IllegalArgumentException("A LFrameCore can't have a negative height.")
        h = height
        setBounds()
    }

    fun setFullscreen(){
        extendedState = Frame.MAXIMIZED_BOTH
        isUndecorated = true
    }

    fun setNotFullScreen(){
        extendedState = Frame.NORMAL
        isUndecorated = false
    }

    fun setRunningIfHidden(){
        runningIfHidden = true
    }

    fun setNotRunningIfHidden(){
        runningIfHidden = false
    }

    fun setNextScene(scene : LScene){
        screenManager.setScene(scene.core())
    }

}