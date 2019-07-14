package usages.doublependulum

import llayout6.frame.LApplication
import llayout6.frame.LFrame

val doublePendulum : LApplication = LApplication { frame.run() }

internal const val TIMER_PERIOD : Int = 10

internal val frame : LFrame = LFrame(Scene).setTimerPeriod(TIMER_PERIOD).setWidth(1.0).setHeight(1.0).setTitle("Double Pendulum")