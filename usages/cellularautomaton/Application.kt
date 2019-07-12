package usages.cellularautomaton

import llayout6.frame.LApplication
import llayout6.frame.LFrame

val cellularAutomaton : LApplication = LApplication { frame.run() }

private const val SELECTION_TIMER_PERIOD : Int = 100

private const val IN_GAME_TIMER_PERIOD : Int = 500

internal val frame : LFrame = LFrame(MainMenuScene).setTitle("Cellular Automaton").setTimerPeriod(SELECTION_TIMER_PERIOD)

internal fun setSelectionPeriod(){
    frame.setTimerPeriod(SELECTION_TIMER_PERIOD)
}

internal fun setInGamePeriod(){
    frame.setTimerPeriod(IN_GAME_TIMER_PERIOD)
}