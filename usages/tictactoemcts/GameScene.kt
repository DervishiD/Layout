package usages.tictactoemcts

import llayout6.displayers.RegularGrid
import llayout6.displayers.TextButton
import llayout6.frame.LScene

internal object GameScene : LScene() {

    private val gameBoard : GameBoard = GameBoard()

    private val displayedGrid : RegularGrid = RegularGrid(GRID_SIZE, GRID_SIZE, 1.0, 1.0)

    private val BACK : TextButton = TextButton("Back") { frame.setScene(StartScene) }

    init{
        add(displayedGrid.alignLeftTo(0).alignTopTo(0))
        add(BACK.alignTopTo(0).alignLeftTo(0))
    }

    internal fun startGame(){
        reload()
        frame.setScene(this)
    }

    private fun reload(){
        resetDisplayedGrid()
        gameBoard.reset()
    }

    private fun resetDisplayedGrid(){
        for(i : Int in 0 until GRID_SIZE){
            for(j : Int in 0 until GRID_SIZE){
                displayedGrid[i, j] = GameCell(i, j)
            }
        }
    }

    internal fun handleClick(i : Int, j : Int){
        paintCell(i, j, gameBoard.playing())
        gameBoard.playAt(i, j)
    }

    internal fun paintCell(i : Int, j : Int, type : Type) = cellAt(i, j).setType(type)

    private fun cellAt(i : Int, j : Int) : GameCell = displayedGrid[i, j] as GameCell

}