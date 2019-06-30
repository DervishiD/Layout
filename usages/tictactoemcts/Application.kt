package usages.tictactoemcts

import llayout5.frame.LApplication
import llayout5.frame.LFrame

val ticTacToeMCTS : LApplication = LApplication { frame.run() }

internal enum class Type { O, X, EMPTY }

internal const val GRID_SIZE : Int = 3

internal val AI : Type = Type.O

internal val PLAYER : Type = Type.X

private const val CELL_DIMENSION : Int = 175

internal val frame : LFrame = LFrame(StartScene).setWidth(GRID_SIZE * CELL_DIMENSION).setHeight(GRID_SIZE * CELL_DIMENSION).setUnResizable()
