package usages.tictactoe9

import llayout4.DEFAULT_COLOR
import llayout4.GraphicAction
import llayout4.displayers.CanvasDisplayer
import llayout4.displayers.RegularGrid
import llayout4.displayers.TextButton
import llayout4.frame.LScene
import llayout4.utilities.LObservable
import llayout4.utilities.montecarlotreesearch.MCTSState
import usages.tictactoe9.Type.*
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

object GameScene : LScene() {

    private val LINE_COLOR : Color = DEFAULT_COLOR
    private val CROSS : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = LINE_COLOR
        g.drawLine(0, 0, w, h)
        g.drawLine(0, h, w, 0)
    }
    private val ROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = LINE_COLOR
        g.drawOval(0, 0, w, h)
    }
    private const val THICK_BORDER_WIDTH : Int = 4

    private class Cell(private val i : Int, private val j : Int, private val k : Int, private val l : Int) : CanvasDisplayer(){

        private companion object{
            private const val KEY : Int = 0
        }

        private var backgroundColor : Color = CLICKABLE_COLOR

        private var type : LObservable<Type> = LObservable(EMPTY)

        init{
            addBackground()
            setOnMouseReleasedAction { click() }
            type.addListener { changeImage() }
        }

        fun type() : Type = type.value

        fun click(){
            if(isNotClicked() && game.isClickable(i, j)){
                type.value = game.playing
                game.tick(type(), i, j, k, l)
            }
        }

        fun forceType(forcedType : Type){
            type.value = forcedType
        }

        private fun backgroundColor() : Color = backgroundColor

        private fun isNotClicked() : Boolean = type() == EMPTY

        fun setBackgroundColor(color : Color){
            backgroundColor = color
        }

        private fun addBackground(){
            addGraphicAction({ g : Graphics, w : Int, h : Int ->
                g.color = backgroundColor()
                g.fillRect(0, 0, w, h)
            })
            if(k == 0){
                addGraphicAction({ g : Graphics, w : Int, _ : Int ->
                    g.color = LINE_COLOR
                    g.fillRect(0, 0, w, THICK_BORDER_WIDTH)
                })
            }else if(k == GRID_SIZE - 1){
                addGraphicAction({ g : Graphics, w : Int, h : Int ->
                    g.color = LINE_COLOR
                    g.fillRect(0, h - THICK_BORDER_WIDTH, w, THICK_BORDER_WIDTH)
                })
            }
            if(l == 0){
                addGraphicAction({ g : Graphics, _ : Int, h : Int ->
                    g.color = LINE_COLOR
                    g.fillRect(0, 0, THICK_BORDER_WIDTH, h)
                })
            }else if(l == GRID_SIZE - 1){
                addGraphicAction({ g : Graphics, w : Int, h : Int ->
                    g.color = LINE_COLOR
                    g.fillRect(w - THICK_BORDER_WIDTH, 0, THICK_BORDER_WIDTH, h)
                })
            }
            onePixelFrame()
        }

        private fun changeImage(){
            if(type() == X) addGraphicAction(CROSS, KEY) else addGraphicAction(ROUND, KEY)
        }

    }

    private class FilledCell(type : Type) : CanvasDisplayer(){
        init{
            fillBackground(Color.WHITE)
            if(type == X) addGraphicAction(CROSS) else addGraphicAction(ROUND)
        }
    }

    private object Algorithm{

        private const val ITERATIONS : Int = 1000

        private const val DEPTH : Int = 10

        fun performNextStep(){
            game = MCTSState.computeNextState(game, ITERATIONS, DEPTH) as GameState
            game.updateScene()
        }

    }

    private val CLICKABLE_COLOR : Color = Color.WHITE

    private val UNCLICKABLE_COLOR : Color = Color(230, 180, 180)

    private val BACK_BUTTON : TextButton = TextButton("Back") { backToSelection() }

    private var game : GameState = GameState()

    private val grid : RegularGrid = RegularGrid(GRID_SIZE, GRID_SIZE, 1.0, 1.0)

    private var isPlayer : Boolean = true

    private var algorithmTurn : LObservable<Boolean> = LObservable(false)

    init{
        addGrid()
        addBackButton()
        algorithmTurn.addListener {
            if(algorithmTurn()){
                game.playing = AI_PLAYING
                Algorithm.performNextStep()
                algorithmTurn.value = false
            }
        }
    }

    fun loadGame(isPlayer : Boolean){
        resetValues()
        resetGrid()
        loadAs(isPlayer)
    }

    private fun resetValues(){
        algorithmTurn.value = false
        game = GameState()
    }

    private fun resetGrid(){
        grid.forEachCell { i, j ->
            val smallerGrid = RegularGrid(GRID_SIZE, GRID_SIZE, 1, 1)
            grid[i, j] = smallerGrid
            smallerGrid.forEachCell { k, l ->
                smallerGrid[k, l] = Cell(i, j, k, l)
            }
        }
    }

    private fun loadAs(isPlayer : Boolean){
        this.isPlayer = isPlayer
        if(!isPlayer) chooseFirstPlayerRandomly()
    }

    private fun chooseFirstPlayerRandomly(){
        algorithmTurn.value = Random.nextInt(2) == 0
    }

    private fun algorithmTurn() : Boolean = algorithmTurn.value

    internal fun setCellsColors(){
        for(i : Int in 0 until GRID_SIZE){
            for(j : Int in 0 until GRID_SIZE){
                if(grid[i, j] !is FilledCell){
                    setCellsColors(i, j)
                }
            }
        }
    }

    private fun setCellsColors(i : Int, j : Int){
        val smallGrid : RegularGrid = grid[i, j] as RegularGrid
        for(k : Int in 0 until GRID_SIZE){
            for(l : Int in 0 until GRID_SIZE){
                (smallGrid[k, l] as Cell).setBackgroundColor(if(game.isClickable(i, j)) CLICKABLE_COLOR else UNCLICKABLE_COLOR)
            }
        }
    }

    internal fun fillCell(i : Int, j : Int, type : Type){
        grid[i, j] = FilledCell(type)
    }

    private fun backToSelection(){
        frame.setScene(SelectionScene)
    }

    private fun addBackButton(){
        add(BACK_BUTTON.alignLeftTo(0).alignTopTo(0))
    }

    private fun addGrid(){
        add(grid.alignTopTo(0).alignLeftTo(0))
    }

    internal fun forceType(i : Int, j : Int, k : Int, l : Int, type : Type) = ((grid[i, j] as RegularGrid)[k, l] as Cell).forceType(type)

    internal fun hasAI() : Boolean = !isPlayer

    internal fun runAlgorithm(){
        algorithmTurn.value = true
    }

}