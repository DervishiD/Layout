package llayout6.frame

import java.awt.Toolkit
import javax.swing.WindowConstants

/**
 * A Frame that displays scenes on the screen.
 * @param scene The first scene on this LFrame
 * @see LScene
 * @since LLayout 1
 */
class LFrame(scene : LScene) {

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

    }

    /**
     * The core object that really appears on the screen. This class is indeed a wrapper.
     * @see LFrameCore
     * @since LLayout 1
     */
    private val core : LFrameCore = LFrameCore(scene.core())

    /**
     * When this frame is closed, the program will exit.
     * @return this
     * @since LLayout 1
     */
    fun exitOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        return this
    }

    /**
     * When the frame is closed, it is instead hidden.
     * @return this
     * @since LLayout 1
     */
    fun hideOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.HIDE_ON_CLOSE
        return this
    }

    /**
     * When the frame is closed, it is disposed.
     * @return this
     * @since LLayout 1
     */
    fun disposeOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        return this
    }

    /**
     * When the frame is closed, nothing else happens.
     * @return this
     * @since LLayout 1
     */
    fun nothingOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        return this
    }

    /**
     * Sets the period of the internal timer of this LFrame.
     * @throws IllegalArgumentException if the period is not strictly positive.
     * @return this
     * @since LLayout 1
     */
    fun setTimerPeriod(period : Int) : LFrame{
        core.changeTimerPeriod(period)
        return this
    }

    /**
     * Shows the frame.
     * @return this
     * @since LLayout 1
     */
    fun setVisible() : LFrame{
        core.setVisible()
        return this
    }

    /**
     * Hides the frame.
     * @return this
     * @since LLayout 1
     */
    fun setInvisible() : LFrame{
        core.setInvisible()
        return this
    }

    /**
     * Closes the frame.
     * @return this
     * @since LLayout 1
     */
    fun close() : LFrame{
        core.close()
        return this
    }

    /**
     * True if the frame is visible.
     * @since LLayout 1
     */
    fun isVisible() : Boolean = core.isVisible

    /**
     * True if the frame is hidden.
     * @since LLayout 1
     */
    fun isHidden() : Boolean = core.isHidden()

    /**
     * Pauses this LFrame's internal timer.
     * @return this
     * @since LLayout 1
     */
    fun pause() : LFrame{
        core.pause()
        return this
    }

    /**
     * Resumes this LFrame's timer.
     * @return this
     * @since LLayout 1
     */
    fun resume() : LFrame{
        core.resume()
        return this
    }

    /**
     * Shows the frame and starts the timer.
     * @return this
     * @since LLayout 1
     */
    fun run() : LFrame{
        core.run()
        return this
    }

    /**
     * Sets the frame to fullscreen mode.
     * @return this
     * @since LLayout 1
     */
    fun setFullscreen() : LFrame{
        core.setFullscreen()
        return this
    }

    /**
     * Sets the frame to non fullscreen mode.
     * @return this
     * @since LLayout 1
     */
    fun setNotFullscreen() : LFrame{
        core.setNotFullScreen()
        return this
    }

    /**
     * Sets the x coordinate of the center of this LFrame.
     * @return this
     * @since LLayout 1
     */
    fun setX(x : Int) : LFrame{
        core.setCenterX(x)
        return this
    }

    /**
     * Sets the y coordinate of the center of this LFrame.
     * @return this
     * @since LLayout 1
     */
    fun setY(y : Int) : LFrame{
        core.setCenterY(y)
        return this
    }

    /**
     * Sets this LFrame's width.
     * @return this
     * @since LLayout 1
     */
    fun setWidth(width : Int) : LFrame{
        core.setW(width)
        return this
    }

    /**
     * Sets this LFrame's height.
     * @return this
     * @since LLayout 1
     */
    fun setHeight(height : Int) : LFrame{
        core.setH(height)
        return this
    }

    /**
     * Sets this LFrame's center's x, as a proportion of the screen's width.
     * @return this
     * @since LLayout 1
     */
    fun setX(x : Double) : LFrame = setX((x * SCREEN_WIDTH).toInt())

    /**
     * Sets this LFrame's center's y, as a proportion of the screen's height.
     * @since LLayout 1
     */
    fun setY(y : Double) : LFrame = setY((y * SCREEN_HEIGHT).toInt())

    /**
     * Sets the width of this LFrame, as a proportion of the screen's width.
     * @return this
     * @since LLayout 1
     */
    fun setWidth(width : Double) : LFrame = setWidth((width * SCREEN_WIDTH).toInt())

    /**
     * Sets this LFrame's height, as a proportion of the screen's height.
     * @since LLayout 1
     */
    fun setHeight(height : Double) : LFrame = setHeight((height * SCREEN_HEIGHT).toInt())

    /**
     * Activates the decoration of this LFrame.
     * @return this
     * @since LLayout 1
     */
    fun setDecorated() : LFrame{
        core.isUndecorated = false
        return this
    }

    /**
     * Deactivates the decoration of this LFrame.
     * @return this
     * @since LLayout 1
     */
    fun setUndecorated() : LFrame{
        core.isUndecorated = true
        return this
    }

    /**
     * If hidden, the timer won't stop.
     * @return this
     * @since LLayout 1
     */
    fun setRunningIfHidden() : LFrame{
        core.setRunningIfHidden()
        return this
    }

    /**
     * If hidden, the timer will stop.
     * @return this
     * @since LLayout 1
     */
    fun setNotRunningIfHidden() : LFrame{
        core.setNotRunningIfHidden()
        return this
    }

    /**
     * Gives this LFrame a title.
     * @return this
     * @since LLayout 1
     */
    fun setTitle(title : CharSequence) : LFrame{
        core.title = title.toString()
        return this
    }

    /**
     * The LFrame is now resizable.
     * @return this
     * @since LLayout 1
     */
    fun setResizable() : LFrame{
        core.isResizable = true
        return this
    }

    /**
     * The LFrame cam't be resized.
     * @return this
     * @since LLayout 1
     */
    fun setUnResizable() : LFrame{
        core.isResizable = false
        return this
    }

    /**
     * Sets the minimal width of this LFrame.
     * @throws IllegalArgumentException If the width is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalWidth(width : Int) : LFrame{
        if(width <= 0) throw IllegalArgumentException("Negative width $width in LFrame.setMinimalWidth")
        core.minimumSize.width = width
        return this
    }

    /**
     * Sets the minimal height of this LFrame.
     * @throws IllegalArgumentException If the height is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalHeight(height : Int) : LFrame{
        if(height <= 0) throw IllegalArgumentException("Negative height $height in LFrame.setMinimalHeight")
        core.minimumSize.height = height
        return this
    }

    /**
     * Sets the minimal width of this LFrame.
     * @throws IllegalArgumentException If the width is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalWidth(width : Double) : LFrame = setMinimalWidth((width * SCREEN_WIDTH).toInt())

    /**
     * Sets the minimal height of this LFrame.
     * @throws IllegalArgumentException If the height is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalHeight(height : Double) : LFrame = setMinimalHeight((height * SCREEN_HEIGHT).toInt())

    /**
     * Sets the minimal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalDimensions(width : Int, height : Int) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    /**
     * Sets the minimal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalDimensions(width : Int, height : Double) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    /**
     * Sets the minimal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalDimensions(width : Double, height : Int) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    /**
     * Sets the minimal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalDimensions(width : Double, height : Double) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    /**
     * Sets the maximal width of this LFrame.
     * @throws IllegalArgumentException If the width is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalWidth(width : Int) : LFrame{
        if(width <= 0) throw IllegalArgumentException("Negative width $width in LFrame.setMaximalWidth")
        core.maximumSize.width = width
        return this
    }

    /**
     * Sets the maximal height of this LFrame.
     * @throws IllegalArgumentException If the height is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalHeight(height : Int) : LFrame{
        if(height <= 0) throw IllegalArgumentException("Negative height $height in LFrame.setMaximalHeight")
        core.maximumSize.height = height
        return this
    }

    /**
     * Sets the maximal width of this LFrame.
     * @throws IllegalArgumentException If the width is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalWidth(width : Double) : LFrame = setMaximalWidth((width * SCREEN_WIDTH).toInt())

    /**
     * Sets the maximal height of this LFrame.
     * @throws IllegalArgumentException If the height is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalHeight(height : Double) : LFrame = setMaximalHeight((height * SCREEN_HEIGHT).toInt())

    /**
     * Sets the maximal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalDimensions(width : Int, height : Int) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    /**
     * Sets the maximal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalDimensions(width : Int, height : Double) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    /**
     * Sets the maximal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalDimensions(width : Double, height : Int) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    /**
     * Sets the maximal dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalDimensions(width : Double, height : Double) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    /**
     * Sets the preferred width of this LFrame.
     * @throws IllegalArgumentException If the width is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredWidth(width : Int) : LFrame{
        if(width <= 0) throw IllegalArgumentException("Negative width $width in LFrame.setPreferredWidth")
        core.preferredSize.width = width
        core.setW(width)
        return this
    }

    /**
     * Sets the preferred height of this LFrame.
     * @throws IllegalArgumentException If the height is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredHeight(height : Int) : LFrame{
        if(height <= 0) throw IllegalArgumentException("Negative height $height in LFrame.setPreferredHeight")
        core.preferredSize.height = height
        core.setH(height)
        return this
    }

    /**
     * Sets the preferred width of this LFrame.
     * @throws IllegalArgumentException If the width is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredWidth(width : Double) : LFrame = setPreferredWidth((width * SCREEN_WIDTH).toInt())

    /**
     * Sets the preferred height of this LFrame.
     * @throws IllegalArgumentException If the height is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredHeight(height : Double) : LFrame = setPreferredHeight((height * SCREEN_HEIGHT).toInt())

    /**
     * Sets the preferred dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredDimensions(width : Int, height : Int) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

    /**
     * Sets the preferred dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredDimensions(width : Int, height : Double) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

    /**
     * Sets the preferred dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredDimensions(width : Double, height : Int) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

    /**
     * Sets the preferred dimensions of this LFrame.
     * @throws IllegalArgumentException If any argument is not positive.
     * @return this
     * @since LLayout 1
     */
    fun setPreferredDimensions(width : Double, height : Double) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

    /**
     * Sets the displayed LScene.
     * @return this
     * @since LLayout 1
     */
    fun setScene(scene : LScene) : LFrame{
        core.setNextScene(scene)
        return this
    }

}