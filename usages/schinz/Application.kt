package usages.schinz

import llayout5.frame.LApplication
import llayout5.frame.LFrame

val schinz : LApplication = LApplication{ frame.run() }

internal val frame : LFrame = LFrame(MenuScene).setTitle("Schinz").setTimerPeriod(200)
