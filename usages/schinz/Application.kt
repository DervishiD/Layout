package usages.schinz

import llayout4.frame.LApplication
import llayout4.frame.LFrame

val schinz : LApplication = LApplication{ frame.run() }

internal val frame : LFrame = LFrame(MenuScene).setTitle("Schinz").setTimerPeriod(200)
