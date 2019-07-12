package usages.cellularautomaton

internal class Game internal constructor(
        private var grid: Array<Array<Boolean>>,
        private val survivingParameters: Collection<Int>,
        private val birthParameters: Collection<Int>) {

    internal fun performNextStep(){
        grid = computeNextStep()
    }

    private fun computeNextStep() : Array<Array<Boolean>>{
        val result : Array<Array<Boolean>> = Array(height()) { Array(width()) { true } }
        for(i : Int in 0 until height()){
            for(j : Int in 0 until width()){
                result[i][j] = nextState(i, j)
            }
        }
        return result
    }

    private fun nextState(i : Int, j : Int) : Boolean{
        return if(grid[i][j]){
            nextStateAlive(i, j)
        }else{
            nextStateDead(i, j)
        }
    }

    private fun nextStateAlive(i : Int, j : Int) : Boolean = countAliveNeighbours(i, j) in survivingParameters

    private fun nextStateDead(i : Int, j : Int) : Boolean = countAliveNeighbours(i, j) in birthParameters

    private fun countAliveNeighbours(i : Int, j : Int) : Int{
        var result = 0
        for(line : Int in i - 1 .. i + 1){
            for(column : Int in j - 1 .. j + 1){
                if(cellExists(line, column) && (line != i || column != j) && grid[line][column]) result ++
            }
        }
        return result
    }

    private fun cellExists(i : Int, j : Int) : Boolean = i >= 0 && j >= 0 && i < height() && j < width()

    internal fun grid() : Array<Array<Boolean>> = grid

    internal fun width() : Int = grid[0].size

    internal fun height() : Int = grid.size

    internal fun stateAt(i : Int, j : Int) = grid[i][j]

}