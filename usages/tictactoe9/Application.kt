package usages.tictactoe9

import llayout3.frame.LApplication
import llayout3.frame.LFrame

val ticTacToe9 : LApplication = LApplication { frame.run() }

val frame : LFrame = LFrame(SelectionScene).setTitle("Tic Tac Toe 9x9")
