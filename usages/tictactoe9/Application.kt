package usages.tictactoe9

import llayout4.frame.LApplication
import llayout4.frame.LFrame

val ticTacToe9 : LApplication = LApplication { frame.run() }

val frame : LFrame = LFrame(SelectionScene).setTitle("Tic Tac Toe 9x9")

internal const val GRID_SIZE : Int = 3

internal enum class Type { O, X, EMPTY }

internal val AI_PLAYING : Type = Type.O
