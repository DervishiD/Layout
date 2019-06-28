package usages.tictactoe9

import llayout4.utilities.montecarlotreesearch.MCTSState
import usages.tictactoe9.Type.*
import kotlin.random.Random

internal class GameState : MCTSState {

    internal companion object{
        internal const val ANY_CELL : Int = -1
    }

    private val gameGrid : Array<Array<TicTacToeGrid>> = Array(GRID_SIZE) { i -> Array(GRID_SIZE) { j -> TicTacToeGrid(this, i, j) } }

    private val resultGrid : Array<Array<Type>> = Array(GRID_SIZE) { Array(GRID_SIZE) { EMPTY } }

    internal var playing : Type = X

    private var clickableLine : Int = ANY_CELL

    private var clickableColumn : Int = ANY_CELL

    private var finished : Boolean = false

    internal constructor()

    private constructor(gameState : GameState){
        for(i : Int in 0 until GRID_SIZE){
            for(j : Int in 0 until GRID_SIZE){
                resultGrid[i][j] = gameState.resultGrid[i][j]
                gameGrid[i][j] = TicTacToeGrid(gameState.gameGrid[i][j])
            }
        }
        clickableLine = gameState.clickableLine
        clickableColumn = gameState.clickableColumn
        finished = gameState.finished
        playing = gameState.playing
    }

    override fun MCTSStateScore(): Double {
//        var score = 0.0
//        for(i : Int in 0 until GRID_SIZE){
//            for(j : Int in 0 until GRID_SIZE){
//                when(resultGrid[i][j]){
//                    AI_PLAYING -> score++
//                    EMPTY -> {}
//                    else -> score--
//                }
//            }
//        }
//        return score

        /*
         * The above one is a real algorithm but it is so first-order-ish that it is even worse than randomness
         */

        return Random.nextDouble()
    }

    override fun findNextMCTSStates(): Collection<MCTSState> {
        val result : MutableCollection<GameState> = mutableSetOf()
        if(!finished){
            if(clickableLine == ANY_CELL){
                for(i : Int in 0 until GRID_SIZE){
                    for(j : Int in 0 until GRID_SIZE){
                        if(resultGrid[i][j] == EMPTY){
                            gameGrid[i][j].availableCells().forEach {
                                result.add(GameState(this).virtualTick(playing, i, j, it.first, it.second))
                            }
                        }
                    }
                }
            }else{
                if(resultGrid[clickableLine][clickableColumn] == EMPTY){
                    gameGrid[clickableLine][clickableColumn].availableCells().forEach {
                        result.add(GameState(this).virtualTick(playing, clickableLine, clickableColumn, it.first, it.second))
                    }
                }
            }
        }
        return result
    }

    override fun generateRandomNextMCTSStates(): MCTSState {
        val possibleStates : List<MCTSState> = findNextMCTSStates().toList()
        if(possibleStates.isEmpty()) throw UnsupportedOperationException("no more MCTS states, should have been tested above")
        return possibleStates[Random.nextInt(possibleStates.size)]
    }

    override fun hasNextMCTSStates(): Boolean {
        if(finished){
            return false
        }else{
            if(clickableLine == ANY_CELL){
                for(i : Int in 0 until GRID_SIZE){
                    for(j : Int in 0 until GRID_SIZE){
                        if(gameGrid[i][j].availableCells().isNotEmpty()) return true
                    }
                }
            }else{
                return gameGrid[clickableLine][clickableColumn].availableCells().isNotEmpty()
            }
        }
        return false
    }

    fun isClickable(i : Int, j : Int) : Boolean{
        return if(clickableLine == ANY_CELL){
            true
        }else{
            i == clickableLine && j == clickableColumn
        }
    }

    private fun x(i : Int, j : Int, k : Int, l : Int){
        gameGrid[i][j].x(k, l)
        playing = O
        setNewGrid(k, l)
        GameScene.setCellsColors()
        if(GameScene.hasAI() && playing == AI_PLAYING){
            GameScene.runAlgorithm()
        }
    }

    private fun o(i : Int, j : Int, k : Int, l : Int){
        gameGrid[i][j].o(k, l)
        playing = X
        setNewGrid(k, l)
        GameScene.setCellsColors()
        if(GameScene.hasAI() && playing == AI_PLAYING){
            GameScene.runAlgorithm()
        }
    }

    private fun virtualX(i: Int, j: Int, k: Int, l: Int){
        gameGrid[i][j].virtualX(k, l)
        playing = O
        setNewGrid(k, l)
    }

    private fun virtualO(i: Int, j: Int, k: Int, l: Int){
        gameGrid[i][j].virtualO(k, l)
        playing = X
        setNewGrid(k, l)
    }

    private fun virtualTick(type : Type, i: Int, j: Int, k: Int, l: Int) : GameState{
        if(type == X) virtualX(i, j, k, l) else if(type == O) virtualO(i, j, k, l)
        return this
    }

    private fun setNewGrid(k : Int, l : Int){
        if(k != ANY_CELL){
            if(!gameGrid[k][l].isFull()){
                clickableLine = k
                clickableColumn = l
            }else{
                clickableLine = ANY_CELL
                clickableColumn = ANY_CELL
            }
        }
    }

    internal fun tick(type : Type, i: Int, j: Int, k: Int, l: Int) : GameState{
        if(type == X) x(i, j, k, l) else if(type == O) o(i, j, k, l)
        return this
    }

    internal fun virtualFillGrid(i : Int, j : Int, type : Type){
        resultGrid[i][j] = type
        val full : Type = detectIfFull()
        if(full != EMPTY) finished = true
    }

    internal fun fillGrid(i : Int, j : Int, type : Type){
        resultGrid[i][j] = type
        GameScene.fillCell(i, j, type)
        val full : Type = detectIfFull()
        if(full != EMPTY) win(full)
    }

    private fun detectIfFull() : Type{
        var lineType : Type
        var columnType : Type
        for(i : Int in 0 until GRID_SIZE){
            lineType = lineIsFull(i)
            if(lineType != EMPTY) return lineType
            columnType = columnIsFull(i)
            if(columnType != EMPTY) return columnType
        }
        return aDiagonalIsFull()
    }

    private fun lineIsFull(index : Int) : Type{
        val type : Type = gameGrid[index][0].makeSureIsNotFull()
        for(i : Int in 1 until GRID_SIZE){
            if(gameGrid[index][i].makeSureIsNotFull() != type) return EMPTY
        }
        return type
    }

    private fun columnIsFull(index : Int) : Type{
        val type : Type = gameGrid[0][index].makeSureIsNotFull()
        for(i : Int in 1 until GRID_SIZE){
            if(gameGrid[i][index].makeSureIsNotFull() != type) return EMPTY
        }
        return type
    }

    private fun aDiagonalIsFull() : Type{
        val firstType : Type = gameGrid[0][0].makeSureIsNotFull()
        val secondType : Type = gameGrid[0][GRID_SIZE - 1].makeSureIsNotFull()
        var firstFull = true
        var secondFull = true
        for(i : Int in 1 until GRID_SIZE){
            if(gameGrid[i][i].makeSureIsNotFull() != firstType) firstFull = false
            if(gameGrid[i][GRID_SIZE - 1 - i].makeSureIsNotFull() != secondType) secondFull = false
        }
        return when{
            firstFull  && firstType != EMPTY -> firstType
            secondFull && secondType != EMPTY -> secondType
            else -> EMPTY
        }
    }

    private fun win(type : Type){
        finished = true
        EndScene.load(type)
    }

    internal fun updateScene(){
        val fullType : Type = detectIfFull()
        if(fullType != EMPTY){
            win(fullType)
        }else{
            for(i : Int in 0 until GRID_SIZE){
                for(j : Int in 0 until GRID_SIZE){
                    val smallType : Type = gameGrid[i][j].makeSureIsNotFull()
                    if(smallType != EMPTY){
                        GameScene.fillCell(i, j, smallType)
                    }else{
                        for(k : Int in 0 until GRID_SIZE){
                            for(l : Int in 0 until GRID_SIZE){
                                val cellType : Type = typeAt(i, j, k,l)
                                if(cellType != EMPTY) GameScene.forceType(i, j, k, l, cellType)
                            }
                        }
                    }
                }
            }
        }
        setNewGrid(clickableLine, clickableColumn)
        GameScene.setCellsColors()
    }

    private fun typeAt(i : Int, j: Int, k: Int, l: Int) : Type = gameGrid[i][j].typeAt(k, l)

}