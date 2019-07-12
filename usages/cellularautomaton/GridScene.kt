package usages.cellularautomaton

import llayout6.displayers.RegularGrid
import llayout6.displayers.ResizableDisplayer
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import java.awt.Graphics

internal object GridScene : LScene() {

    private class Cell : ResizableDisplayer(){

        private val borderThickness : Int = 2

        private var state : Boolean = false

        init{
            setOnMousePressedAction { toggleState() }
            core.addGraphicAction({ g : Graphics, w : Int, h : Int -> draw(g, w, h) })
        }

        private fun toggleState(){
            state = !state
        }

        private fun draw(g : Graphics, w : Int, h : Int){
            g.color = if(state()) Parameters.aliveColour() else Parameters.deadColour()
            g.fillRect(0, 0, w, h)
            g.color = Parameters.borderColour()
            g.fillRect(0, 0, w, borderThickness)
            g.fillRect(0, 0, borderThickness, h)
            g.fillRect(w - borderThickness, 0, borderThickness, h)
            g.fillRect(0, h - borderThickness, w, borderThickness)
        }

        internal fun state() : Boolean = state

    }

    private const val GRID_WIDTH : Double = 1.0

    private const val GRID_HEIGHT : Double = 0.8

    private var grid : RegularGrid = RegularGrid(1, 1, 1, 1)

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    private val SIMULATION_BUTTON : TextButton = TextButton("Simulate") { simulate() }

    init{
        setOnLoadAction { load() }
        addBackButton()
        addSimulateButton()
    }

    private fun load(){
        grid = RegularGrid(Parameters.width(), Parameters.height(), GRID_WIDTH, GRID_HEIGHT)
        fillGrid()
        addGrid()
    }

    private fun addGrid(){
        add(grid.alignLeftTo(0).alignTopTo(0))
    }

    private fun addBackButton(){
        add(BACK_BUTTON.setX(0.33).setY(0.9))
    }

    private fun addSimulateButton(){
        add(SIMULATION_BUTTON.setX(0.66).setY(0.9))
    }

    private fun fillGrid(){
        grid.forEachCell { i, j -> grid[i, j] = Cell() }
    }

    private fun back(){
        frame.setScene(NeighbourhoodSelectionScene)
    }

    private fun selectedGrid() : Array<Array<Boolean>>{
        val result : Array<Array<Boolean>> = Array(grid.numberOfLines()) { Array(grid.numberOfColumns()) { false } }
        grid.forEachCell { i, j -> result[i][j] = (grid[i, j] as Cell).state() }
        return result
    }

    private fun simulate(){
        Parameters.setGrid(selectedGrid())
        SimulationScene.setParameters(Parameters.createGame(), Parameters.aliveColour(), Parameters.deadColour(), Parameters.borderColour())
        setInGamePeriod()
        frame.setScene(SimulationScene)
    }

}