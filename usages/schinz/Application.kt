package usages.schinz

import llayout6.frame.LApplication
import llayout6.frame.LFrame

val schinz : LApplication = LApplication{ frame.run() }

internal val frame : LFrame = LFrame(MenuScene).setTitle("Schinz").setTimerPeriod(200)
