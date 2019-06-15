package usages.schinz

import llayout3.frame.LApplication
import llayout3.frame.LFrame

val schinz : LApplication = LApplication{ frame.run() }

internal val frame : LFrame = LFrame(MenuScene).setTitle("Schinz").setTimerPeriod(200)
