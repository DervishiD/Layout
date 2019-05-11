package llayout.frame

import javax.swing.Timer

/**
 * A decorator for the java.util.Timer, used by the LFrame class.
 * @param frame This LTimer's LFrame.
 * @param period This LTimer's period.
 * @see java.util.Timer
 * @see LFrameCore
 */
internal class LTimer(private val frame: LFrameCore, private var period: Int = DEFAULT_PERIOD) {

    companion object {

        /**
         * The default period of a LTimer.
         */
        private const val DEFAULT_PERIOD : Int = 30

    }

    /**
     * The inner java.util.Timer of this LTimer.
     * @see java.util.Timer
     */
    private val timer : Timer = Timer(period){
        frame.onTimerTick()
        frame.repaint()
    }

    /**
     * Starts this LTimer.
     * @see timer
     * @see period
     */
    internal fun start(){
        timer.start()
    }

    internal infix fun setPeriod(period : Int){
        timer.delay = period
    }

    internal fun pause(){
        timer.stop()
    }

}