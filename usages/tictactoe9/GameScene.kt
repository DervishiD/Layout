package usages.tictactoe9

import llayout3.displayers.RegularGrid
import llayout3.displayers.TextButton
import llayout3.frame.LScene

object GameScene : LScene() {

    private const val NUMBER_OF_LINES : Int = 3

    private const val NUMBER_OF_COLUMNS : Int = 3

    private val grid : RegularGrid = RegularGrid(NUMBER_OF_LINES, NUMBER_OF_COLUMNS, 1.0, 1.0)

    private val BACK_BUTTON : TextButton = TextButton("Back") { backToSelection() }

    fun loadGame(isPlayer : Boolean){
        TODO("Not implemented.")
    }

    private fun backToSelection(){
        frame.setScene(SelectionScene)
    }

}