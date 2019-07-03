package usages.pendulum

import llayout6.frame.LApplication
import llayout6.frame.LFrame

val pendulum : LApplication = LApplication { frame.run() }

internal val frame : LFrame = LFrame(Scene).setWidth(1.0).setHeight(1.0).setRunningIfHidden().setTimerPeriod(10)