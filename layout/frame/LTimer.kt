package layout.frame

import java.util.*

/**
 * A decorator for the java.util.Timer, used by the LFrame class.
 * @param frame This LTimer's LFrame.
 * @param period This LTimer's period.
 * @see java.util.Timer
 * @see LFrame
 */
internal class LTimer(private val frame: LFrame, private val period: Long = DEFAULT_PERIOD) {

    companion object {

        /**
         * The default period of a LTimer.
         */
        private const val DEFAULT_PERIOD : Long = 30

    }

    /**
     * The inner java.util.Timer of this LTimer.
     * @see java.util.Timer
     */
    private val timer : Timer = Timer(false)

    /**
     * Starts this LTimer.
     * @see timer
     * @see period
     */
    fun start() = timer.scheduleAtFixedRate(object : TimerTask(){
        override fun run() {
            frame.onTimerTick()
            frame.repaint()
        }
    }, 0, period)

}