package usages.tictactoe9

import llayout5.utilities.montecarlotreesearch.MCTSState
import usages.tictactoe9.Type.*
import java.util.*

internal class GameState : MCTSState {

    internal companion object{
        private val randomizer : SplittableRandom = SplittableRandom()
        internal const val ANY_CELL : Int = -1
        private const val AI_WINNING_SCORE : Double = 400.0
        private const val PLAYER_WINNING_SCORE : Double = -800.0
        private const val AI_BIG_ALMOST_SCORE : Double = 35.0
        private const val PLAYER_BIG_ALMOST_SCORE : Double = -70.0
        private const val AI_BIG_CELL_SCORE : Double = 20.0
        private const val PLAYER_BIG_CELL_SCORE : Double = -40.0
        private const val CLICKABLE_EVERYWHERE_SCORE : Double = -25.0
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

        fun finishedState() : Double{
            val type : Type = detectIfFull()
            return when(type){
                EMPTY -> 0.0
                AI_PLAYING -> AI_WINNING_SCORE
                else -> PLAYER_WINNING_SCORE
            }
        }

        fun bigLineAlmostType(index : Int) : Type{
            var type : Type = gameGrid[index][0].makeSureIsNotFull()
//            var type : Type = resultGrid[index][0]
            var holeEncountered = type == EMPTY
            for(i : Int in 1 until GRID_SIZE){
                val cellType : Type = gameGrid[index][i].makeSureIsNotFull()
//                val cellType : Type = resultGrid[index][i]
                if(cellType == EMPTY){
                    if(holeEncountered){
                        return EMPTY
                    }else{
                        holeEncountered = true
                    }
                }else if(cellType != type){
                    if(type != EMPTY){
                        return EMPTY
                    }else{
                        type = cellType
                    }
                }
            }
            return type
        }

        fun bigColumnAlmostType(index : Int) : Type{
            var type : Type = gameGrid[0][index].makeSureIsNotFull()
//            var type : Type = resultGrid[0][index]
            var holeEncountered = type == EMPTY
            for(i : Int in 1 until GRID_SIZE){
                val cellType : Type = gameGrid[i][index].makeSureIsNotFull()
//                val cellType : Type = resultGrid[i][index]
                if(cellType == EMPTY){
                    if(holeEncountered){
                        return EMPTY
                    }else{
                        holeEncountered = true
                    }
                }else if(cellType != type){
                    if(type != EMPTY){
                        return EMPTY
                    }else{
                        type = cellType
                    }
                }
            }
            return type
        }

        fun bigLineScore(index : Int) : Double{
            return when(bigLineAlmostType(index)){
                EMPTY -> 0.0
                AI_PLAYING -> AI_BIG_ALMOST_SCORE
                else -> PLAYER_BIG_ALMOST_SCORE
            }
        }

        fun bigColumnScore(index : Int) : Double{
            return when(bigColumnAlmostType(index)){
                EMPTY -> 0.0
                AI_PLAYING -> AI_BIG_ALMOST_SCORE
                else -> PLAYER_BIG_ALMOST_SCORE
            }
        }

        fun firstBigDiagonalType() : Type{
            var type : Type = gameGrid[0][0].makeSureIsNotFull()
//            var type : Type = resultGrid[0][0]
            var holeEncountered = type == EMPTY
            for(i : Int in 1 until GRID_SIZE){
                val cellType : Type = gameGrid[i][i].makeSureIsNotFull()
//                val cellType : Type = resultGrid[i][i]
                if(cellType == EMPTY){
                    if(holeEncountered){
                        return EMPTY
                    }else{
                        holeEncountered = true
                    }
                }else if(cellType != type){
                    if(type != EMPTY){
                        return EMPTY
                    }else{
                        type = cellType
                    }
                }
            }
            return type
        }

        fun firstBigDiagonalScore() : Double{
            return when(firstBigDiagonalType()){
                EMPTY -> 0.0
                AI_PLAYING -> AI_BIG_ALMOST_SCORE
                else -> PLAYER_BIG_ALMOST_SCORE
            }
        }

        fun secondBigDiagonalType() : Type{
            var type : Type = gameGrid[0][GRID_SIZE - 1].makeSureIsNotFull()
//            var type : Type = resultGrid[0][GRID_SIZE - 1]
            var holeEncountered = type == EMPTY
            for(i : Int in 1 until GRID_SIZE){
                val cellType : Type = gameGrid[i][GRID_SIZE - 1 - i].makeSureIsNotFull()
//                val cellType : Type = resultGrid[i][GRID_SIZE - 1 - i]
                if(cellType == EMPTY){
                    if(holeEncountered){
                        return EMPTY
                    }else{
                        holeEncountered = true
                    }
                }else if(cellType != type){
                    if(type != EMPTY){
                        return EMPTY
                    }else{
                        type = cellType
                    }
                }
            }
            return type
        }

        fun secondBigDiagonalScore() : Double{
            return when(secondBigDiagonalType()){
                EMPTY -> 0.0
                AI_PLAYING -> AI_BIG_ALMOST_SCORE
                else -> PLAYER_BIG_ALMOST_SCORE
            }
        }

        fun bigDiagonalsScore() : Double{
            return firstBigDiagonalScore() + secondBigDiagonalScore()
        }

        fun bigGridStateScore() : Double{
            var score = 0.0
            for(i : Int in 0 until GRID_SIZE){
                score += bigLineScore(i) + bigColumnScore(i)
                for(j : Int in 0 until GRID_SIZE){
                    score += when(gameGrid[i][j].makeSureIsNotFull()/*resultGrid[i][j]*/){
                        EMPTY -> 0.0
                        AI_PLAYING -> AI_BIG_CELL_SCORE
                        else -> PLAYER_BIG_CELL_SCORE
                    }
                }
            }
            score += bigDiagonalsScore()
            return score
        }

        fun smallGridsScores() : Double{
            var score = 0.0
            for(i : Int in 0 until GRID_SIZE){
                for(j : Int in 0 until GRID_SIZE){
                    score += gameGrid[i][j].MCTSScore()
                }
            }
            return score
        }

        fun scoreForPlayableCell() : Double{
            return if(clickableLine == ANY_CELL) CLICKABLE_EVERYWHERE_SCORE else 0.0
        }

        var score = 0.0
        score += finishedState()
        score += bigGridStateScore()
        score += smallGridsScores()
        score += scoreForPlayableCell()
        return score

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

    override fun generateRandomNextMCTSState(): MCTSState {
        val possibleStates : List<MCTSState> = findNextMCTSStates().toList()
        if(possibleStates.isEmpty()) throw UnsupportedOperationException("no more MCTS states, should have been tested above")
        return possibleStates[randomizer.nextInt(possibleStates.size)]
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
        println(MCTSStateScore())
    }

    private fun typeAt(i : Int, j: Int, k: Int, l: Int) : Type = gameGrid[i][j].typeAt(k, l)

}