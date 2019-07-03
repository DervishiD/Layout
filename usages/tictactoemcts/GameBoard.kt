package usages.tictactoemcts

import llayout6.utilities.montecarlotreesearch.MCTSState
import kotlin.random.Random

internal class GameBoard : MCTSState {

    private companion object{
        private const val ITERATIONS : Int = 10000
        private const val DEPTH : Int = 3
        private const val WIN_SCORE : Double = 40.0
        private const val LOSS_SCORE : Double = -45.0
        private const val DRAW_SCORE : Double = 25.0
        private const val ALMOST_AI_AI_SCORE : Double = 2.0
        private const val ALMOST_AI_PLAYER_SCORE : Double = 20.0
        private const val ALMOST_PLAYER_AI_SCORE : Double = -3.0
        private const val ALMOST_PLAYER_PLAYER_SCORE : Double = -25.0
    }

    private val grid : Array<Array<Type>> = Array(GRID_SIZE) { Array(GRID_SIZE) { Type.EMPTY } }

    private var playing : Type = Type.X

    constructor()

    private constructor(gameBoard: GameBoard){
        forEach { i : Int, j : Int -> grid[i][j] = gameBoard.cellAt(i, j) }
        playing = gameBoard.playing()
    }

    internal fun reset(){
        forEach { i : Int, j : Int -> grid[i][j] = Type.EMPTY }
        chooseRandomPlayer()
        if(playing == AI) runAlgorithm()
    }

    private fun chooseRandomPlayer(){
        playing = if(Random.nextInt(2) == 0) PLAYER else AI
    }

    internal fun playing() : Type = playing

    private fun forEach(action : (Int, Int) -> Unit){
        for(i : Int in 0 until GRID_SIZE) for(j : Int in 0 until GRID_SIZE) action(i, j)
    }

    private fun tickAt(i : Int, j : Int){
        grid[i][j] = playing
        switchPlayer()
    }

    internal fun playAt(i : Int, j : Int){
        tickAt(i, j)
        if(!detectAWin() && AITurn()) runAlgorithm()
    }

    private fun detectAWin() : Boolean{
        val winner : Type? = winningPlayer()
        return if(winner != null){
            EndScene.end(winner)
            true
        }else false
    }

    private fun switchPlayer(){
        playing = if(playing == AI) PLAYER else AI
    }

    private fun AITurn() : Boolean = playing == AI

    private fun winningPlayer() : Type?{
        val trueWinner : Type = strictWinner()
        return when {
            trueWinner != Type.EMPTY -> trueWinner
            gridIsFull() -> Type.EMPTY
            else -> null
        }
    }

    private fun strictWinner() : Type{
        var lineType : Type
        var columnType : Type
        val firstType : Type = cellAt(0, 0)
        val secondType : Type = cellAt(0, GRID_SIZE - 1)
        var firstFull : Boolean = firstType != Type.EMPTY
        var secondFull : Boolean = secondType != Type.EMPTY
        for(i : Int in 0 until GRID_SIZE){
            lineType = lineType(i)
            if(lineType != Type.EMPTY) return lineType
            columnType = columnType(i)
            if(columnType != Type.EMPTY) return columnType
            if(cellAt(i, i) != firstType) firstFull = false
            if(cellAt(i, GRID_SIZE - 1 - i) != secondType) secondFull = false
        }
        if(firstFull) return firstType
        if(secondFull) return secondType
        return Type.EMPTY
    }

    private fun lineType(index : Int) : Type{
        val type : Type = cellAt(index, 0)
        for(i : Int in 0 until GRID_SIZE){
            if(cellAt(index, i) != type) return Type.EMPTY
        }
        return type
    }

    private fun columnType(index : Int) : Type{
        val type : Type = cellAt(0, index)
        for(i : Int in 0 until GRID_SIZE){
            if(cellAt(i, index) != type) return Type.EMPTY
        }
        return type
    }

    private fun cellAt(i : Int, j : Int) : Type = grid[i][j]

    private fun gridIsFull() : Boolean{
        var full = true
        forEach { i : Int, j : Int -> if(cellAt(i, j) == Type.EMPTY) full = false }
        return full
    }

    private fun runAlgorithm(){
        val result : GameBoard = MCTSState.computeNextState(this, ITERATIONS, DEPTH) as GameBoard
        forEach { i : Int, j : Int ->
            grid[i][j] = result.cellAt(i, j)
            GameScene.paintCell(i, j, grid[i][j])
        }
        detectAWin()
        playing = PLAYER
    }

    override fun MCTSStateScore(): Double {

        fun finishedScore() : Double{
            return when(winningPlayer()){
                null -> 0.0
                AI -> WIN_SCORE
                PLAYER -> LOSS_SCORE
                else -> DRAW_SCORE
            }
        }

        fun almostAI() : Double = if(playing == AI) ALMOST_AI_AI_SCORE else ALMOST_AI_PLAYER_SCORE

        fun almostPlayer() : Double = if(playing == AI) ALMOST_PLAYER_AI_SCORE else ALMOST_PLAYER_PLAYER_SCORE

        fun almostLineScore(index : Int) : Double{
            var start = 1
            var type : Type = cellAt(index, 0)
            var hasAHole = false
            if(type == Type.EMPTY){
                hasAHole = true
                type = cellAt(index, 1)
                start = 2
                if(type == Type.EMPTY) return 0.0
            }
            var cell : Type
            for(i : Int in start until GRID_SIZE){
                cell = cellAt(index, i)
                if(cell == Type.EMPTY){
                    if(hasAHole){
                        return 0.0
                    }else{
                        hasAHole = true
                    }
                }else if(cell != type) return 0.0
            }
            return if(type == AI) almostAI() else almostPlayer()
        }

        fun almostColumnScore(index : Int) : Double{
            var start = 1
            var type : Type = cellAt(0, index)
            var hasAHole = false
            if(type == Type.EMPTY){
                hasAHole = true
                type = cellAt(1, index)
                start = 2
                if(type == Type.EMPTY) return 0.0
            }
            var cell : Type
            for(i : Int in start until GRID_SIZE){
                cell = cellAt(i, index)
                if(cell == Type.EMPTY){
                    if(hasAHole){
                        return 0.0
                    }else{
                        hasAHole = true
                    }
                }else if(cell != type) return 0.0
            }
            return if(type == AI) almostAI() else almostPlayer()
        }

        fun firstDiagonalScore() : Double{
            var start = 1
            var type : Type = cellAt(0, 0)
            var hasAHole = false
            if(type == Type.EMPTY){
                hasAHole = true
                type = cellAt(1, 1)
                start = 2
                if(type == Type.EMPTY) return 0.0
            }
            var cell : Type
            for(i : Int in start until GRID_SIZE){
                cell = cellAt(i, i)
                if(cell == Type.EMPTY){
                    if(hasAHole){
                        return 0.0
                    }else{
                        hasAHole = true
                    }
                }else if(cell != type) return 0.0
            }
            return if(type == AI) almostAI() else almostPlayer()
        }

        fun secondDiagonalScore() : Double{
            var start = 1
            var type : Type = cellAt(0, GRID_SIZE - 1)
            var hasAHole = false
            if(type == Type.EMPTY){
                hasAHole = true
                type = cellAt(1, GRID_SIZE - 2)
                start = 2
                if(type == Type.EMPTY) return 0.0
            }
            var cell : Type
            for(i : Int in start until GRID_SIZE){
                cell = cellAt(i, GRID_SIZE - 1 - i)
                if(cell == Type.EMPTY){
                    if(hasAHole){
                        return 0.0
                    }else{
                        hasAHole = true
                    }
                }else if(cell != type) return 0.0
            }
            return if(type == AI) almostAI() else almostPlayer()
        }

        fun diagonalScore() : Double = firstDiagonalScore() + secondDiagonalScore()

        fun almostScore() : Double{
            var score = 0.0
            for(i : Int in 0 until GRID_SIZE){
                score += almostLineScore(i) + almostColumnScore(i)
            }
            return score + diagonalScore()
        }

        val score : Double = finishedScore()
        return if(score != 0.0) score else almostScore()
    }

    override fun findNextMCTSStates(): Collection<MCTSState> {
        val result : MutableCollection<GameBoard> = mutableSetOf()
        if(!detectAWin()){
            forEach { i : Int, j : Int -> if(cellAt(i, j) == Type.EMPTY) result.add(copyWithTickAt(i, j)) }
        }
        return result
    }

    private fun copyWithTickAt(i : Int, j : Int) : GameBoard{
        val result = GameBoard(this)
        result.tickAt(i, j)
        return result
    }

    override fun generateRandomNextMCTSState(): MCTSState {
        val next : List<MCTSState> = findNextMCTSStates().toList()
        return next[Random.nextInt(next.size)]
    }

    override fun hasNextMCTSStates(): Boolean = winningPlayer() == null

}