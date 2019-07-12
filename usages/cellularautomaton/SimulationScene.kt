package usages.cellularautomaton

import llayout6.displayers.RegularGrid
import llayout6.displayers.ResizableDisplayer
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import java.awt.Color
import java.awt.Graphics

internal object SimulationScene : LScene() {

    private class Cell(private val i : Int, private val j : Int, private var state : Boolean) : ResizableDisplayer(){

        companion object{

            private const val LINE_THICKNESS : Int = 2

        }

        init{
            core.addGraphicAction({ g : Graphics, w : Int, h : Int -> draw(g, w, h) })
        }

        internal fun setState(state : Boolean){
            this.state = state
        }

        private fun draw(g : Graphics, w : Int, h : Int){
            g.color = if(alive()) aliveColour() else deadColour()
            g.fillRect(0, 0, w, h)
            g.color = borderColour()
            g.fillRect(0, 0, w, LINE_THICKNESS)
            g.fillRect(0, 0, LINE_THICKNESS, h)
            g.fillRect(w - LINE_THICKNESS, 0, LINE_THICKNESS, h)
            g.fillRect(0, h - LINE_THICKNESS, w, LINE_THICKNESS)
        }

        private fun alive() : Boolean = state

    }

    private const val GRID_WIDTH : Double = 0.8

    private const val GRID_HEIGHT : Double = 1.0

    private lateinit var game : Game

    private lateinit var aliveColour : Color

    private lateinit var deadColour : Color

    private lateinit var borderColour : Color

    private var grid : RegularGrid = RegularGrid(1, 1, 1, 1)

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    init{
        setOnLoadAction { load() }
        setOnTimerTickAction { update() }
        addBackButton()
    }

    private fun load(){
        createGrid()
        addGrid()
    }

    private fun createGrid(){
        grid = RegularGrid(game.height(), game.width(), GRID_WIDTH, GRID_HEIGHT)
        fillGrid()
    }

    private fun fillGrid(){
        for(i : Int in 0 until grid.numberOfLines()){
            for(j : Int in 0 until grid.numberOfColumns()){
                grid[i, j] = Cell(i, j, game.stateAt(i, j))
            }
        }
    }

    private fun addGrid(){
        add(grid.alignRightTo(1.0).setY(0.5))
    }

    private fun addBackButton(){
        add(BACK_BUTTON.setX( (1 - GRID_WIDTH) / 2 ).setY(0.5))
    }

    private fun back(){
        frame.setScene(GridScene)
        setSelectionPeriod()
    }

    private fun aliveColour() : Color = aliveColour

    private fun deadColour() : Color = deadColour

    private fun borderColour() : Color = borderColour

    private fun update(){
        game.performNextStep()
        updateGrid(game.grid())
    }

    private fun updateGrid(grid : Array<Array<Boolean>>){
        this.grid.forEachCell { i, j ->
            (this.grid[i, j] as Cell).setState(grid[i][j])
        }
    }

    internal fun setParameters(game : Game, aliveColour : Color, deadColour : Color, borderColour : Color){
        this.game = game
        this.aliveColour = aliveColour
        this.deadColour = deadColour
        this.borderColour = borderColour
    }

}