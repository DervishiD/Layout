package display.frame

import main.Action
import java.util.*

internal class LTimer {

    companion object {

        private const val DEFAULT_PERIOD : Long = 30

        private class Task(private val action : Action) : TimerTask(){
            override fun run(){
                action.invoke()
            }
        }

    }

    private val frame : LFrame

    private var period : Long

    private val timer : Timer = Timer(false)

    private val task : Task

    constructor(frame : LFrame, period : Long = DEFAULT_PERIOD){
        this.frame = frame
        this.period = period
        task = Task{frame.repaint()}
    }

    fun start() = timer.scheduleAtFixedRate(task, 0, period)

}