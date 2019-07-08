package usages.cellularautomaton

import llayout6.frame.LApplication
import llayout6.frame.LFrame

val cellularAutomaton : LApplication = LApplication { frame.run() }

private const val TIMER_PERIOD : Int = 25

internal val frame : LFrame = LFrame(MainMenuScene).setTitle("Cellular Automaton").setTimerPeriod(TIMER_PERIOD)