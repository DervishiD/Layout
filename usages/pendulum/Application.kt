package usages.pendulum

import llayout6.frame.LApplication
import llayout6.frame.LFrame

val pendulum : LApplication = LApplication {
    plotFrame.run()
    frame.run()
}

internal const val TIMER_PERIOD : Int = 10

internal val frame : LFrame = LFrame(Scene).setWidth(1.0).setHeight(1.0).setRunningIfHidden().setTimerPeriod(TIMER_PERIOD).setTitle("Pendulum")

internal val plotFrame : LFrame = LFrame(PlotScene).setWidth(1.0).setNotRunningIfHidden().setTimerPeriod(TIMER_PERIOD).setTitle("Theta Plot").hideOnClose()