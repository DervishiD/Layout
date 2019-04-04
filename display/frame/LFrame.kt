package display.frame

import display.Screen
import java.awt.Frame
import java.awt.event.*
import javax.swing.JFrame

/**
 * A JFrame equipped with the awesome layout I'm designing.
 * @see LScreenManager
 * @see LTimer
 * @see LFrameBuilder
 */
class LFrame : JFrame {

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
     * @see Screen
     * @see setDefaultCloseOperation
     * @see timer
     */
    internal constructor(
            contentPane : Screen,
            x : Int,
            y : Int,
            width : Int,
            height : Int,
            onClose : Int,
            isFullScreen : Boolean,
            isDecorated : Boolean,
            timerPeriod : Long
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

        addKeyListener(object : KeyAdapter() {

            override fun keyPressed(e: KeyEvent?) = screenManager.pressKey(e!!.keyCode)

            override fun keyReleased(e: KeyEvent?) = screenManager.releaseKey(e!!.keyCode)

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
        return this
    }

    /**
     * Hides this LFrame.
     */
    fun setInvisible() : LFrame{
        isVisible = false
        transferFocus()
        return this
    }

    /**
     * Closes this LFrame.
     */
    fun close() : LFrame{
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
        timer.start()
    }

}