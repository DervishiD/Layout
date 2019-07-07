package usages.springpendulum

import llayout6.frame.LApplication
import llayout6.frame.LFrame

val springPendulum : LApplication = LApplication { frame.run() }

internal const val TIMER_PERIOD : Int = 10

internal val frame : LFrame = LFrame(Scene).setTitle("Spring Pendulum").setWidth(1.0).setHeight(1.0).setTimerPeriod(TIMER_PERIOD)