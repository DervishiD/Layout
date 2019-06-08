package llayout2.interfaces

/**
 * The interface of objects that can react on a LFrame's timer tick.
 * @since LLayout 1
 */
interface LTimerUpdatable {

    /**
     * The action executed when the LFrame's timer ticks.
     * @since LLayout 1
     */
    fun onTimerTick()

}