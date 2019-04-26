package llayout.frame

import java.awt.Toolkit
import javax.swing.WindowConstants

/**
 * A builder class that builds a LFrame according to the given settings.
 * @param contentPane The content pane of the constructed LFrame
 * @see LFrame
 * @see LScene
 */
class LFrameBuilder(private var contentPane: LScene) {

    companion object {

        /**
         * The computer's screen width.
         */
        private val SCREEN_WIDTH : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()

        /**
         * The computer's screen height.
         */
        private val SCREEN_HEIGHT : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

        /**
         * The default period of the constructed LFrame's LTimer.
         */
        private const val DEFAULT_TIMER_PERIOD : Int = 30

    }

    /**
     * Encodes the action executed when the constructed LFrame is closed.
     */
    private var onClose : Int = WindowConstants.EXIT_ON_CLOSE

    /**
     * True if the constructed LFrame is fullscreen.
     * @see setFullScreen
     */
    private var isFullscreen : Boolean = false

    /**
     * True if the constructed LFrame is decorated.
     * @see setDecorated
     */
    private var isDecorated : Boolean = true

    /**
     * The x coordinate of the center of the constructed LFrame.
     * @see setCenterXCoordinate
     */
    private var x : Int = SCREEN_WIDTH / 2

    /**
     * The y coordinate of the center of the constructed LFrame.
     * @see setCenterYCoordinate
     */
    private var y : Int = SCREEN_HEIGHT / 2

    /**
     * The width of the constructed LFrame.
     * @see setWidth
     */
    private var width : Int = SCREEN_WIDTH

    /**
     * The height of the constructed LFrame.
     * @see setHeight
     */
    private var height : Int = SCREEN_HEIGHT

    /**
     * The default period of the constructed LFrame's LTimer.
     * @see setTimerPeriod
     */
    private var timerPeriod : Int = DEFAULT_TIMER_PERIOD

    private var runningIfHidden : Boolean = false

    /**
     * Exits the application when the constructed LFrame is closed.
     * @return This LFrameBuilder.
     * @see onClose
     */
    fun exitOnClose() : LFrameBuilder{
        this.onClose = WindowConstants.EXIT_ON_CLOSE
        return this
    }

    /**
     * Does nothing when the constructed LFrame is closed.
     * @return This LFrameBuilder.
     * @see onClose
     */
    fun nothingOnClose() : LFrameBuilder{
        this.onClose = WindowConstants.DO_NOTHING_ON_CLOSE
        return this
    }

    /**
     * Disposes the constructed LFrame when it is closed.
     * @return This LFrameBuilder.
     * @see onClose
     */
    fun disposeOnClose() : LFrameBuilder{
        this.onClose = WindowConstants.DISPOSE_ON_CLOSE
        return this
    }

    /**
     * Hides the constructed LFrame when it is closed.
     * @return This LFrameBuilder.
     * @see onClose
     */
    fun hideOnClose() : LFrameBuilder{
        this.onClose = WindowConstants.HIDE_ON_CLOSE
        return this
    }

    /**
     * Sets the value of the exitOnClose parameter.
     * True if the constructed LFrame is fullscreen, False otherwise.
     * The default value is false.
     * @param isFullscreen The value of the exitOnClose parameter.
     * @return This LFrameBuilder.
     * @see isFullscreen
     */
    infix fun setFullScreen(isFullscreen : Boolean) : LFrameBuilder{
        this.isFullscreen = isFullscreen
        return this
    }

    /**
     * Sets the value of the exitOnClose parameter.
     * TTrue if the constructed LFrame is decorated, False otherwise.
     * The default value is true.
     * @param isDecorated The value of the exitOnClose parameter.
     * @return This LFrameBuilder.
     * @see isDecorated
     */
    infix fun setDecorated(isDecorated : Boolean) : LFrameBuilder{
        this.isDecorated = isDecorated
        return this
    }

    /**
     * Sets the x coordinate of the constructed LFrame, if it is not on fullscreen.
     * The default value is the middle of the computer's screen.
     * @param x The x coordinate of the center of the constructed LFrame.
     * @return This LFrameBuilder.
     * @see x
     * @see setFullScreen
     */
    infix fun setCenterXCoordinate(x : Int) : LFrameBuilder{
        this.x = x
        return this
    }

    /**
     * Sets the x coordinate of the constructed LFrame, as a proportion of the computer's screen's
     * width, if it is not on fullscreen.
     * The default value is the middle of the computer's screen.
     * @param x The x coordinate of the center of the constructed LFrame, as a proportion of
     *          the computer's screen's width.
     * @return This LFrameBuilder.
     * @see x
     * @see setFullScreen
     */
    infix fun setCenterXCoordinate(x : Double) : LFrameBuilder{
        this.x = (SCREEN_WIDTH * x).toInt()
        return this
    }

    /**
     * Sets the x coordinate of the constructed LFrame, as a proportion of the computer's screen's
     * width, if it is not on fullscreen.
     * The default value is the middle of the computer's screen.
     * @param x The x coordinate of the center of the constructed LFrame, as a proportion of
     *          the computer's screen's width.
     * @return This LFrameBuilder.
     * @see x
     * @see setFullScreen
     */
    infix fun setCenterXCoordinate(x : Float) : LFrameBuilder{
        this.x = (SCREEN_WIDTH * x).toInt()
        return this
    }

    /**
     * Sets the y coordinate of the constructed LFrame, if it is not on fullscreen.
     * The default value is the middle of the computer's screen.
     * @param y The y coordinate of the center of the constructed LFrame.
     * @return This LFrameBuilder.
     * @see y
     * @see setFullScreen
     */
    infix fun setCenterYCoordinate(y : Int) : LFrameBuilder{
        this.y = y
        return this
    }

    /**
     * Sets the y coordinate of the constructed LFrame, as a proportion of the computer's screen's
     * height, if it is not on fullscreen.
     * The default value is the middle of the computer's screen.
     * @param y The y coordinate of the center of the constructed LFrame, as a proportion of
     *          the computer's screen's height.
     * @return This LFrameBuilder.
     * @see y
     * @see setFullScreen
     */
    infix fun setCenterYCoordinate(y : Double) : LFrameBuilder{
        this.y = (SCREEN_HEIGHT * y).toInt()
        return this
    }

    /**
     * Sets the y coordinate of the constructed LFrame, as a proportion of the computer's screen's
     * height, if it is not on fullscreen.
     * The default value is the middle of the computer's screen.
     * @param y The y coordinate of the center of the constructed LFrame, as a proportion of
     *          the computer's screen's height.
     * @return This LFrameBuilder.
     * @see y
     * @see setFullScreen
     */
    infix fun setCenterYCoordinate(y : Float) : LFrameBuilder{
        this.y = (SCREEN_HEIGHT * y).toInt()
        return this
    }

    /**
     * Sets the width of the constructed LFrame, if it is not on fullscreen.
     * The default value is the width of the computer's screen.
     * @param width The width of the constructed LFrame.
     * @return This LFrameBuilder.
     * @throws IllegalArgumentException If the given width is not positive.
     * @see width
     * @see setFullScreen
     */
    infix fun setWidth(width : Int) : LFrameBuilder{
        if(width >= 0) this.width = width
        else throw IllegalArgumentException("The width of a LFrame must be positive.")
        return this
    }

    /**
     * Sets the width of the constructed LFrame, as a proportion of the computer's screen's
     * width, if it is not on fullscreen.
     * The default value is the width of the computer's screen.
     * @param width The width of the constructed LFrame, as a proportion of the computer's screen's width.
     * @return This LFrameBuilder.
     * @throws IllegalArgumentException If the given width is not positive.
     * @see width
     * @see setFullScreen
     */
    infix fun setWidth(width : Double) : LFrameBuilder{
        if(width >= 0) this.width = (SCREEN_WIDTH * width).toInt()
        else throw IllegalArgumentException("The width of a LFrame must be positive.")
        return this
    }

    /**
     * Sets the width of the constructed LFrame, as a proportion of the computer's screen's
     * width, if it is not on fullscreen.
     * The default value is the width of the computer's screen.
     * @param width The width of the constructed LFrame, as a proportion of the computer's screen's width.
     * @return This LFrameBuilder.
     * @throws IllegalArgumentException If the given width is not positive.
     * @see width
     * @see setFullScreen
     */
    infix fun setWidth(width : Float) : LFrameBuilder{
        if(width >= 0) this.width = (SCREEN_WIDTH * width).toInt()
        else throw IllegalArgumentException("The width of a LFrame must be positive.")
        return this
    }

    /**
     * Sets the height of the constructed LFrame, if it is not on fullscreen.
     * The default value is the height of the computer's screen.
     * @param height The height of the constructed LFrame.
     * @return This LFrameBuilder.
     * @throws IllegalArgumentException If the given height is not positive.
     * @see height
     * @see setFullScreen
     */
    infix fun setHeight(height : Int) : LFrameBuilder{
        if(height >= 0) this.height = height
        else throw IllegalArgumentException("The height of a LFrame must be positive.")
        return this
    }

    /**
     * Sets the height of the constructed LFrame, as a proportion of the computer's screen's
     * height, if it is not on fullscreen.
     * The default value is the height of the computer's screen.
     * @param height The height of the constructed LFrame, as a proportion of the computer's screen's height.
     * @return This LFrameBuilder.
     * @throws IllegalArgumentException If the given height is not positive.
     * @see height
     * @see setFullScreen
     */
    infix fun setHeight(height : Double) : LFrameBuilder{
        if(height >= 0) this.height = (SCREEN_HEIGHT * height).toInt()
        else throw IllegalArgumentException("The height of a LFrame must be positive.")
        return this
    }

    /**
     * Sets the height of the constructed LFrame, as a proportion of the computer's screen's
     * height, if it is not on fullscreen.
     * The default value is the height of the computer's screen.
     * @param height The height of the constructed LFrame, as a proportion of the computer's screen's height.
     * @return This LFrameBuilder.
     * @throws IllegalArgumentException If the given height is not positive.
     * @see height
     * @see setFullScreen
     */
    infix fun setHeight(height : Float) : LFrameBuilder{
        if(height >= 0) this.height = (SCREEN_WIDTH * height).toInt()
        else throw IllegalArgumentException("The height of a LFrame must be positive.")
        return this
    }

    /**
     * Sets the content pane of the constructed LFrame.
     * @param contentPane The content pane of the constructed LFrame.
     * @return This LFrameBuilder.
     * @see LScene
     */
    infix fun setContentPane(contentPane : LScene) : LFrameBuilder{
        this.contentPane = contentPane
        return this
    }

    /**
     * Sets the period of the constructed LFrame's LTimer, in milliseconds.
     * The default value is the constant DEFAULT_TIMER_PERIOD, fixed at 30 milliseconds.
     * @param period The period of the constructed LFrame's LTimer, in milliseconds.
     * @return This LFrameBuilder.
     * @throws IllegalArgumentException If the given period is not strictly positive.
     * @see timerPeriod
     */
    infix fun setTimerPeriod(period : Int) : LFrameBuilder{
        if(period > 0) timerPeriod = period else throw IllegalArgumentException("The period of a timer must be positive.")
        return this
    }

    infix fun isRunningIfHidden(runningIfHidden : Boolean) : LFrameBuilder{
        this.runningIfHidden = runningIfHidden
        return this
    }

    /**
     * Builds the LFrame corresponding to the given options.
     * @see LFrame
     */
    fun build() : LFrame{
        return LFrame(contentPane, x, y, width, height, onClose, isFullscreen, isDecorated, runningIfHidden, timerPeriod)
    }

}