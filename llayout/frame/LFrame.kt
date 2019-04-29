package llayout.frame

import llayout.Action
import llayout.interfaces.LTimerUpdatable
import java.awt.Frame
import java.awt.event.*
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
        addListeners()
    }

    /**
     * Adds a MouseAdapter, a KeyListener, a MouseWheelListener and a MouseMotionListener
     * to this LFrame.
     * @see addMouseListener
     * @see addMouseMotionListener
     * @see addMouseWheelListener
     * @see addKeyListener
     * @see MouseAdapter
     * @see MouseWheelListener
     * @see MouseMotionListener
     * @see KeyAdapter
     */
    private fun addListeners(){
        addMouseListener(object : MouseAdapter(){

            override fun mouseClicked(e: MouseEvent?) = screenManager.mouseClick(e!!.x, e.y)

            override fun mousePressed(e: MouseEvent?) = screenManager.mousePress(e!!.x, e.y)

            override fun mouseReleased(e: MouseEvent?) = screenManager.mouseRelease(e!!.x, e.y)

            override fun mouseEntered(e: MouseEvent?) = screenManager.mouseEnter(e!!.x, e.y)

            override fun mouseExited(e: MouseEvent?) = screenManager.mouseExit(e!!.x, e.y)

        })

        addMouseWheelListener { e -> screenManager.mouseWheelMoved(e!!.x, e.y, e.wheelRotation) }

        addMouseMotionListener(object : MouseMotionListener{

            override fun mouseMoved(e: MouseEvent?) {
                LMouse.setNewMousePosition(e!!.x, e.y)
                screenManager.mouseMoved(e.x, e.y)
            }

            override fun mouseDragged(e: MouseEvent?) {
                LMouse.setNewMousePosition(e!!.x, e.y)
                screenManager.mouseDrag(e.x, e.y)
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

    infix fun setOnCloseAction(onCloseAction : Action) : LFrame{
        onCloseAdditionalAction = onCloseAction
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

    infix fun setTimerPeriod(period : Int) : LFrame {
        timer.setPeriod(period)
        return this
    }

    fun resizeAtFixedUpperLeft(width : Int, height : Int) : LFrame{
        setBounds(x, y, width, height)
        screenManager.resize()
        return this
    }

    fun resizeAtFixedCenter(width : Int, height : Int) : LFrame{
        setBounds(x + this.width/2 - width/2, y + this.height/2 - height/2, width, height)
        screenManager.resize()
        return this
    }

    fun resizeAtFixedUpperRight(width : Int, height : Int) : LFrame{
        setBounds(x + this.width - width, y, width, height)
        screenManager.resize()
        return this
    }

    fun resizeAtFixedLowerLeft(width : Int, height : Int) : LFrame{
        setBounds(x, y + this.height - height, width, height)
        screenManager.resize()
        return this
    }

    fun resizeAtFixedLowerRight(width : Int, height : Int) : LFrame{
        setBounds(x + this.width - width, y + this.height - height, width, height)
        screenManager.resize()
        return this
    }

    infix fun moveByX(delta : Int) : LFrame{
        setBounds(x + delta, y, width, height)
        return this
    }

    infix fun moveByY(delta : Int) : LFrame{
        setBounds(x, y + delta, width, height)
        return this
    }

    infix fun moveToX(x : Int) : LFrame{
        setBounds(x, y, width, height)
        return this
    }

    infix fun moveToY(y : Int) : LFrame{
        setBounds(x, y, width, height)
        return this
    }

    fun moveBy(deltaX : Int, deltaY : Int) : LFrame{
        setBounds(x + deltaX, y + deltaY, width, height)
        return this
    }

    fun moveCenterTo(x : Int, y : Int) : LFrame{
        setBounds(x - width / 2, y - height / 2, width, height)
        return this
    }

    fun moveLowerLeftTo(x : Int, y : Int) : LFrame{
        setBounds(x, y - height, width, height)
        return this
    }

    fun moveLowerRightTo(x : Int, y : Int) : LFrame{
        setBounds(x - width, y - height, width, height)
        return this
    }

    fun moveUpperLeftTo(x : Int, y : Int) : LFrame{
        setBounds(x, y, width, height)
        return this
    }

    fun moveUpperRightTo(x : Int, y : Int) : LFrame{
        setBounds(x - width, y, width, height)
        return this
    }

    infix fun resizeHeightAtFixedBottom(height : Int) : LFrame{
        setBounds(x, y + this.height - height, width, height)
        screenManager.resize()
        return this
    }

    infix fun resizeHeightAtFixedTop(height : Int) : LFrame{
        setBounds(x, y, width, height)
        screenManager.resize()
        return this
    }

    infix fun resizeWidthAtFixedLeft(width : Int) : LFrame{
        setBounds(x, y, width, height)
        screenManager.resize()
        return this
    }

    infix fun resizeWidthAtFixedRight(width : Int) : LFrame{
        setBounds(x + this.width - width, y, width, height)
        screenManager.resize()
        return this
    }

    override fun onTimerTick(): LFrame {
        screenManager.onTimerTick()
        return this
    }

}