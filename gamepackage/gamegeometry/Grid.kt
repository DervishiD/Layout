package gamepackage.gamegeometry

import geometry.Point
import geometry.Vector

/**
 * The class that represents a game grid
 */
class Grid {

    private companion object {
        private const val DEFAULT_MESH : Int = 120 //TODO
    }

    /**
     * The number of lines
     */
    var lines : Int
    /**
     * The number of columns
     */
    var columns : Int
    /**
     * The mesh size
     */
    private var mesh : Int = DEFAULT_MESH

    /**
     * The origin of the Grid
     */
    private var origin : Point = Point(0, 0)

    /**
     * The actual grid, a 2D array
     */
    private var grid : ArrayList<ArrayList<Cell>> = ArrayList(1)

    constructor(lines : Int, columns : Int){
        this.lines = lines
        this.columns = columns
        refillGrid()
    }

    /**
     * The origin of the grid
     */
    fun origin() : Point = origin

    /**
     * Returns a given line
     */
    infix fun line(index : Int) : ArrayList<Cell>{
        if(index >= lines || index < 0){
            throw IndexOutOfBoundsException("There is no line with index $index in this Grid")
        }else{
            return grid[index]
        }
    }

    /**
     * Returns a given column
     */
    infix fun column(index : Int) : ArrayList<Cell>{
        if(index >= columns || index < 0){
            throw IndexOutOfBoundsException("There is no column with index $index in this Grid")
        }else{
            val result : ArrayList<Cell> = ArrayList()
            for(line : ArrayList<Cell> in grid){
                result.add(line[index])
            }
            return result
        }
    }

    /**
     * Returns a list of lines starting at firstIncl and ending at endIncl
     */
    fun lines(firstIncl : Int, lastIncl : Int) : ArrayList<ArrayList<Cell>>{
        var startIndex : Int = firstIncl
        var endIndex : Int = lastIncl
        if(startIndex > endIndex){
            val temporary : Int = startIndex
            startIndex = endIndex
            endIndex = temporary
        }
        when{
            startIndex < 0 -> startIndex = 0
            startIndex >= lines -> startIndex = lines - 1
            endIndex < 0 -> endIndex = 0
            endIndex >= lines -> endIndex = lines - 1
        }
        val result : ArrayList<ArrayList<Cell>> = ArrayList()
        for(index : Int in startIndex..endIndex){
            result.add(line(index))
        }
        return result
    }

    /**
     * Returns a list of columns starting at firstIncl and ending at endIncl
     */
    fun columns(firstIncl : Int, lastIncl : Int) : ArrayList<ArrayList<Cell>>{
        var startIndex : Int = firstIncl
        var endIndex : Int = lastIncl
        if(startIndex > endIndex){
            val temporary : Int = startIndex
            startIndex = endIndex
            endIndex = temporary
        }
        when{
            startIndex < 0 -> startIndex = 0
            startIndex >= columns -> startIndex = columns - 1
            endIndex < 0 -> endIndex = 0
            endIndex >= columns -> endIndex = columns - 1
        }
        val result : ArrayList<ArrayList<Cell>> = ArrayList()
        for(index : Int in startIndex..endIndex){
            result.add(column(index))
        }
        return result
    }

    fun cellAt(line : Int, column : Int) : Cell{
        if(line < lines && column < columns && line >= 0 && column >= 0){
            return grid[line][column]
        }else throw IndexOutOfBoundsException("The cell you're looking for at ($line, $column) doesn't exist \n" +
                                              "This Grid has $lines lines and $columns columns")
    }

    /**
     * Fills the grid with empty Cells
     */
    private fun refillGrid(){
        grid = ArrayList()
        for(i : Int in 0 until lines){
            grid.add(ArrayList())
            for(j : Int in 0 until columns){
                grid[i].add(Cell())
            }
        }
    }

    /**
     * Moves the origin along the given vector
     */
    infix fun moveAlong(v : Vector){
        origin += v
    }

    /**
     * Changes the number of columns
     */
    infix fun setColumnsNumber(newColumnsNumber : Int){
        if(newColumnsNumber > columns){
            for(i : Int in 1..newColumnsNumber - columns){
                addColumn(columns)
            }
        }else if(newColumnsNumber < columns){
            for(i : Int in 1..columns - newColumnsNumber){
                removeColumn(columns - 1)
            }
        }
    }

    /**
     * Changes the number of lines
     */
    infix fun setLinesNumber(newLinesNumber : Int){
        if(newLinesNumber > lines){
            for(i : Int in 0 until newLinesNumber - lines){
                addLine(lines)
            }
        }else if(newLinesNumber < lines){
            for(i : Int in 0 until lines - newLinesNumber){
                removeLine(lines - 1)
            }
        }
    }

    /**
     * Adds a line at the given index
     */
    tailrec infix fun addLine(index : Int){
        when {
            index > lines -> addLine(lines)
            index < 0 -> addLine(0)
            else -> {
                grid.add(index, ArrayList())
                for(i : Int in 0 until columns){
                    grid[index].add(Cell())
                }
                lines++
            }
        }
    }

    /**
     * Removes the line at the given index
     */
    tailrec infix fun removeLine(index : Int){
        when{
            index < 0 -> removeLine(0)
            index >= lines -> removeLine(lines - 1)
            else -> {
                grid.removeAt(index)
                lines--
            }
        }
    }

    /**
     * Adds the column at the given index
     */
    tailrec infix fun addColumn(index : Int){
        when{
            index < 0 -> addColumn(0)
            index > columns -> addColumn(columns)
            else -> {
                for(line : ArrayList<Cell> in grid){
                    line.add(index, Cell())
                }
                columns++
            }
        }
    }

    /**
     * Removes the column at the given index
     */
    tailrec infix fun removeColumn(index : Int){
        when{
            index < 0 -> removeColumn(index)
            index >= columns -> removeColumn(columns - 1)
            else -> {
                for(i : Int in 0 until lines){
                    grid[i].removeAt(index)
                }
                columns--
            }
        }
    }

    /**
     * Converts a Point on the grid to a point in the frame
     */
    fun gridToFrame(p : Point) : Point = origin + p.toVector()

    /**
     * Converts a Point on the grid to a point in the frame
     */
    fun gridToFrame(x : Int, y : Double) : Point = gridToFrame(Point(x, y))

    /**
     * Converts a Point on the grid to a point in the frame
     */
    fun gridToFrame(x : Double, y : Int) : Point = gridToFrame(Point(x, y))

    /**
     * Converts a Point on the grid to a point in the frame
     */
    fun gridToFrame(x : Double, y : Double) : Point = gridToFrame(Point(x, y))

    /**
     * Converts a Point on the grid to a point in the frame
     */
    fun gridToFrame(x : Int, y : Int) : Point = gridToFrame(Point(x, y))

    /**
     * Converts a Point in the frame to a Point on the Grid
     */
    fun frameToGrid(p : Point) : Point = p - origin.toVector()

    /**
     * Converts a Point in the frame to a Point on the Grid
     */
    fun frameToGrid(x : Int, y : Int) : Point = frameToGrid(Point(x, y))

    /**
     * Converts a Point in the frame to a Point on the Grid
     */
    fun frameToGrid(x : Double, y : Int) : Point = frameToGrid(Point(x, y))

    /**
     * Converts a Point in the frame to a Point on the Grid
     */
    fun frameToGrid(x : Int, y : Double) : Point = frameToGrid(Point(x, y))

    /**
     * Converts a Point in the frame to a Point on the Grid
     */
    fun frameToGrid(x : Double, y : Double) : Point = frameToGrid(Point(x, y))

}