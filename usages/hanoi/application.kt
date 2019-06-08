package usages.hanoi

import llayout2.frame.LApplication
import llayout2.frame.LFrame

internal val frame : LFrame = LFrame(SelectionScene).setTitle("Tower of Hanoi").setTimerPeriod(100).setUnResizable()

val towerOfHanoi : LApplication = LApplication{ frame.run() }
