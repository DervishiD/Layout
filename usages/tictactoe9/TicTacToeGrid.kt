package usages.tictactoe9

import usages.tictactoe9.Type.*

internal class TicTacToeGrid(private val gameState : GameState, private val i : Int, private val j : Int) {

    private val resultGrid : Array<Array<Type>> = Array(GRID_SIZE) { Array(GRID_SIZE) { EMPTY } }

    private var full : Boolean = false

    internal constructor(grid : TicTacToeGrid) : this(grid.gameState, grid.i, grid.j){
        for(k : Int in 0 until GRID_SIZE){
            for(l : Int in 0 until GRID_SIZE){
                resultGrid[k][l] = grid.resultGrid[k][l]
            }
        }
        full = grid.full
    }

    internal fun isFull() : Boolean = full

    internal fun x(k : Int, l : Int){
        resultGrid[k][l] = X
        val full = makeSureIsNotFull()
        if(full != EMPTY){
            lock()
            gameState.fillGrid(i, j, full)
        }
    }

    internal fun o(k : Int, l : Int){
        resultGrid[k][l] = O
        val full = makeSureIsNotFull()
        if(full != EMPTY){
            lock()
            gameState.fillGrid(i, j, full)
        }
    }

    internal fun virtualX(k : Int, l : Int){
        resultGrid[k][l] = X
        val full = makeSureIsNotFull()
        if(full != EMPTY){
            lock()
            gameState.virtualFillGrid(i, j, full)
        }
    }

    internal fun virtualO(k : Int, l : Int){
        resultGrid[k][l] = O
        val full = makeSureIsNotFull()
        if(full != EMPTY){
            lock()
            gameState.virtualFillGrid(i, j, full)
        }
    }

    fun availableCells() : Collection<Pair<Int, Int>>{
        val result : MutableCollection<Pair<Int, Int>> = mutableSetOf()
        if(!full){
            for(k : Int in 0 until GRID_SIZE){
                for(l : Int in 0 until GRID_SIZE){
                    if(resultGrid[k][l] == EMPTY) result.add(Pair(k, l))
                }
            }
        }
        return result
    }

    internal fun typeAt(k : Int, l : Int) : Type = resultGrid[k][l]

    internal fun makeSureIsNotFull() : Type{
        var lineType : Type
        var columnType : Type
        for(i : Int in 0 until GRID_SIZE){
            lineType = lineType(i)
            if(lineType != EMPTY) return lineType
            columnType = columnType(i)
            if(columnType != EMPTY) return columnType
        }
        return diagonalsType()
    }

    private fun lineType(index : Int) : Type{
        val type : Type = resultGrid[index][0]
        for(i : Int in 1 until GRID_SIZE){
            if(resultGrid[index][i] != type) return EMPTY
        }
        return type
    }

    private fun columnType(index : Int) : Type{
        val type : Type = resultGrid[0][index]
        for(i : Int in 1 until GRID_SIZE){
            if(resultGrid[i][index] != type) return EMPTY
        }
        return type
    }

    private fun diagonalsType() : Type{
        val firstType : Type = resultGrid[0][0]
        val secondType : Type = resultGrid[0][GRID_SIZE - 1]
        var firstFull = true
        var secondFull = true
        for(i : Int in 1 until GRID_SIZE){
            if(resultGrid[i][i] != firstType) firstFull = false
            if(resultGrid[i][GRID_SIZE - 1 - i] != secondType) secondFull = false
        }
        return when{
            firstFull && firstType != EMPTY -> firstType
            secondFull && secondType != EMPTY -> secondType
            else -> EMPTY
        }
    }

    internal fun lock(){
        full = true
    }

}