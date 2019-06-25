package usages.tictactoe9

import llayout4.DEFAULT_COLOR
import llayout4.GraphicAction
import llayout4.displayers.CanvasDisplayer
import llayout4.displayers.RegularGrid
import llayout4.displayers.TextButton
import llayout4.frame.LScene
import llayout4.utilities.LObservable
import usages.tictactoe9.GameScene.Type.*
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

object GameScene : LScene() {

    private enum class Type { O, X, EMPTY }

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
            if(inGame && isNotClicked() && isClickable()){
                type.value = currentPlayer
                handleNewClick(checkGrid(i, j), i, j, k, l)
                switchPlayer()
            }
        }

        private fun isClickable() : Boolean{
            return if(clickableLine == ANY_CELL && clickableColumn == ANY_CELL){
                true
            }else{
                i == clickableLine && j == clickableColumn
            }
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

        fun performNextStep(){
            TODO("Not implemented.")
        }

    }

    private const val GRID_SIZE : Int = 3

    private const val ANY_CELL : Int = -1

    private val CLICKABLE_COLOR : Color = Color.WHITE

    private val UNCLICKABLE_COLOR : Color = Color(200, 140, 130)

    private val BACK_BUTTON : TextButton = TextButton("Back") { backToSelection() }

    private val grid : RegularGrid = RegularGrid(GRID_SIZE, GRID_SIZE, 1.0, 1.0)

    private val resultGrid : Array<Array<Type>> = Array(GRID_SIZE) { Array(GRID_SIZE) { EMPTY } }

    private var currentPlayer : Type = X

    private var isPlayer : Boolean = true

    private var algorithmTurn : LObservable<Boolean> = LObservable(false)

    private var clickableLine : Int = ANY_CELL

    private var clickableColumn : Int = ANY_CELL

    private var inGame : Boolean = false

    init{
        addGrid()
        addBackButton()
        algorithmTurn.addListener {
            if(algorithmTurn()){
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
        currentPlayer = X
        clickableLine = ANY_CELL
        clickableColumn = ANY_CELL
        inGame = true
        algorithmTurn.value = false
        for(i : Int in 0 until GRID_SIZE){
            for(j : Int in 0 until GRID_SIZE){
                resultGrid[i][j] = EMPTY
            }
        }
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

    private fun switchPlayer(){
        currentPlayer = if(currentPlayer == X) O else X
        if(!isPlayer && !algorithmTurn()) algorithmTurn.value = true
    }

    private fun isFull(i : Int, j: Int) : Boolean = resultGrid[i][j] != EMPTY

    private fun handleNewClick(type : Type, i : Int, j : Int, k : Int, l : Int){
        if(type != EMPTY){
            fillSmallGrid(type, i, j)
        }
        setNextGrid(k, l)
        setCellsColors()
        checkGrid()
    }

    private fun fillSmallGrid(type : Type, i : Int, j : Int){
        resultGrid[i][j] = type
        grid[i, j] = FilledCell(type)
    }

    private fun setNextGrid(i : Int, j : Int){
        if(isFull(i, j)){
            clickableLine = ANY_CELL
            clickableColumn = ANY_CELL
        }else{
            clickableLine = i
            clickableColumn = j
        }
    }

    private fun setCellsColors(){
        for(i : Int in 0 until GRID_SIZE){
            for(j : Int in 0 until GRID_SIZE){
                if(!isFull(i, j)){
                    setCellsColors(i, j)
                }
            }
        }
    }

    private fun setCellsColors(i : Int, j : Int){
        val smallGrid : RegularGrid = grid[i, j] as RegularGrid
        for(k : Int in 0 until GRID_SIZE){
            for(l : Int in 0 until GRID_SIZE){
                (smallGrid[k, l] as Cell).setBackgroundColor(if(isClickable(i, j)) CLICKABLE_COLOR else UNCLICKABLE_COLOR)
            }
        }
    }

    private fun isClickable(i : Int, j : Int) : Boolean{
        return if(clickableLine == ANY_CELL && clickableColumn == ANY_CELL){
            true
        }else{
            i == clickableLine && j == clickableColumn
        }
    }

    private fun checkGrid(){
        var finished = false
        for(i : Int in 0 until GRID_SIZE){
            if(isBigLineFull(i)) finished = true
            if(isBigColumnFull(i)) finished = true
        }
        if(finished || checkBigDiagonals()) endGame()
    }

    private fun isBigLineFull(i : Int) : Boolean{
        val type : Type = resultGrid[i][0]
        if(type == EMPTY){
            return false
        }else{
            for(j : Int in 1 until GRID_SIZE){
                if(resultGrid[i][j] != type) return false
            }
            return true
        }
    }

    private fun isBigColumnFull(i : Int) : Boolean{
        val type : Type = resultGrid[0][i]
        if(type == EMPTY){
            return false
        }else{
            for(j : Int in 1 until GRID_SIZE){
                if(resultGrid[j][i] != type) return false
            }
            return true
        }
    }

    private fun checkBigDiagonals() : Boolean{
        val firstType : Type = resultGrid[0][0]
        val secondType : Type = resultGrid[0][GRID_SIZE - 1]
        var firstFull = true
        var secondFull = true
        return if(firstType == EMPTY && secondType == EMPTY){
            false
        }else{
            for(i : Int in 1 until GRID_SIZE){
                if(resultGrid[i][i] != firstType){
                    firstFull = false
                }
                if(resultGrid[i][GRID_SIZE - 1 - i] != secondType){
                    secondFull = false
                }
            }
            ( firstFull && firstType != EMPTY ) || ( secondFull && secondType != EMPTY )
        }
    }

    private fun endGame(){
        inGame = false
    }

    private fun gridIsFinished(i : Int, j : Int) : Boolean = resultGrid[i][j] != EMPTY

    private fun checkGrid(i : Int, j : Int) : Type{
        if(!gridIsFinished(i, j)){
            val smallGrid : RegularGrid = grid[i, j] as RegularGrid
            //lines
            for(k : Int in 0 until GRID_SIZE){
                val type : Type = (smallGrid[k, 0] as Cell).type()
                if(type != EMPTY){
                    var full = true
                    for(l : Int in 1 until GRID_SIZE){
                        if((smallGrid[k, l] as Cell).type() != type) full = false
                    }
                    if(full) return type
                }
            }
            //columns
            for(k : Int in 0 until GRID_SIZE){
                val type : Type = (smallGrid[0, k] as Cell).type()
                if(type != EMPTY){
                    var full = true
                    for(l : Int in 1 until GRID_SIZE){
                        if((smallGrid[l, k] as Cell).type() != type) full = false
                    }
                    if(full) return type
                }
            }
            //first diagonal
            var type : Type = (smallGrid[0, 0] as Cell).type()
            var full = true
            if(type != EMPTY){
                for(m : Int in 1 until GRID_SIZE){
                    if((smallGrid[m, m] as Cell).type() != type) full = false
                }
                if(full) return type
            }
            //second diagonal
            type = (smallGrid[0, GRID_SIZE - 1] as Cell).type()
            if(type != EMPTY){
                full = true
                for(n : Int in 1 until GRID_SIZE){
                    if((smallGrid[n, GRID_SIZE - 1 - n] as Cell).type() != type) full = false
                }
                if(full) return type
            }
        }
        return EMPTY
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

}