package layout.frame

import java.util.*

/**
 * A decorator for the java.util.Timer, used by the LFrame class.
 * @param frame This LTimer's LFrame.
 * @param period This LTimer's period.
 * @see java.util.Timer
 * @see LFrame
 */
internal class LTimer(private val frame: LFrame, private var period: Long = DEFAULT_PERIOD) {

    companion object {

        /**
         * The default period of a LTimer.
         */
        private const val DEFAULT_PERIOD : Long = 30

    }

    private val task : TimerTask = object : TimerTask(){
        override fun run() {
            frame.onTimerTick()
            frame.repaint()
        }
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
    internal fun start() = timer.scheduleAtFixedRate(task, 0, period)

    internal infix fun setPeriod(period : Long){
        this.period = period
        timer.cancel()
        timer.scheduleAtFixedRate(task, 0, period)
    }

}