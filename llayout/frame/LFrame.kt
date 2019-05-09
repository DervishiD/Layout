package llayout.frame

import llayout.Action
import llayout.interfaces.LTimerUpdatable
import java.awt.Frame
import java.awt.event.*
import java.lang.IllegalArgumentException
import javax.swing.JFrame

/**
 * A JFrame equipped with the awesome layout I'm designing.
 * @see LScreenManager
 * @see LTimer
 * @see LFrameBuilder
 */
class LFrame : JFrame, LTimerUpdatable {

    /**
     * This LFrame's LScreenManager.
     * @see LScreenManager
     */
    private val screenManager : LScreenManager

    /**
     * This LFrame's LTimer.
     * @see LTimer
     */
    private val timer : LTimer

    private var onCloseAdditionalAction : Action = {}

    private val runningIfHidden : Boolean

    /**
     * An internal constructor, only used by LFrameBuilder.
     * @param contentPane The content pane of this LFrame.
     * @param x The x coordinate of this LFrame's center.
     * @param y The y coordinate of this LFrame's center.
     * @param width The width of this LFrame.
     * @param height The height of this LFrame.
     * @param onClose The action executed when this LFrame is closed.
     * @param isFullScreen True if this LFrame is on fullscreen.
     * @param isDecorated True if this LFrame is decorated.
     * @param timerPeriod The period of this LFrame's LTimer.
     * @see LScene
     * @see setDefaultCloseOperation
     * @see timer
     */
    internal constructor(
            contentPane : LScene,
            x : Int,
            y : Int,
            width : Int,
            height : Int,
            onClose : Int,
            isFullScreen : Boolean,
            isDecorated : Boolean,
            runningIfHidden : Boolean,
            timerPeriod : Int
    ) : super(){
        defaultCloseOperation = onClose
        isUndecorated = !isDecorated
        isResizable = true
        if(isFullScreen){
            extendedState = Frame.MAXIMIZED_BOTH
        }else{
            setBounds(x - width / 2, y - height / 2, width, height)
        }
        screenManager = LScreenManager(this, contentPane)
        timer = LTimer(this, timerPeriod)
        this.runningIfHidden = runningIfHidden
        addResizeListener()
    }

    private fun addResizeListener(){
        addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                screenManager.resize()
            }
        })
    }

    /**
     * Shows this LFrame.
     */
    fun setVisible() : LFrame{
        isVisible = true
        requestFocus()
        timer.start()
        return this
    }

    /**
     * Hides this LFrame.
     */
    fun setInvisible() : LFrame{
        isVisible = false
        transferFocus()
        if(!runningIfHidden) timer.pause()
        return this
    }

    fun isHidden() : Boolean = !isVisible

    fun pause() : LFrame{
        timer.pause()
        return this
    }

    fun resume() : LFrame{
        timer.start()
        return this
    }

    /**
     * Closes this LFrame.
     */
    fun close() : LFrame{
        onCloseAdditionalAction.invoke()
        setInvisible()
        dispatchEvent(WindowEvent(this, WindowEvent.WINDOW_CLOSING))
        return this
    }

    /**
     * Starts the LTimer of this LFrame and calls the setVisible() method.
     * @see setVisible
     * @see LScreenManager.start
     * @see LTimer.start
     */
    fun run(){
        screenManager.start()
        setVisible()
    }

    infix fun changeTimerPeriod(period : Int) : LFrame {
        if(period <= 0) throw IllegalArgumentException("Negative timer period")
        timer.setPeriod(period)
        return this
    }

    override fun onTimerTick(): LFrame {
        screenManager.onTimerTick()
        return this
    }

}