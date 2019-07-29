package llayout7.displayers

import java.awt.Graphics

/**
 * A Container that contains [ResizableDisplayer]s and arranges them in a grid.
 * The Grid has a fixed number of columns and lines. For a grid with variable dimensions, see [MutableRegularGrid].
 * @see ResizableDisplayer
 * @see MutableRegularGrid
 * @since LLayout 3
 */
class RegularGrid : ResizableDisplayer {

    /**
     * The grid containing the added components in the correct order.
     * It contains nullable elements to signify empty cells.
     * @since LLayout 3
     */
    private val grid : Array<Array<ResizableDisplayer?>>

    init{
        addWidthListener { resizeAllComponents() }
        addHeightListener { resizeAllComponents() }
    }

    constructor(lines : Int, columns : Int, gridWidth : Int, gridHeight : Int) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = Array(lines){Array<ResizableDisplayer?>(columns){null}}
    }

    constructor(lines : Int, columns : Int, gridWidth : Double, gridHeight : Int) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = Array(lines){Array<ResizableDisplayer?>(columns){null}}
    }


    constructor(lines : Int, columns : Int, gridWidth : Int, gridHeight : Double) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = Array(lines){Array<ResizableDisplayer?>(columns){null}}
    }


    constructor(lines : Int, columns : Int, gridWidth : Double, gridHeight : Double) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = Array(lines){Array<ResizableDisplayer?>(columns){null}}
    }

    /**
     * Adds a component at the given line and column of the grid.
     * @throws IndexOutOfBoundsException If the indices are out of bounds.
     * @since LLayout 3
     */
    operator fun set(line : Int, column : Int, component : ResizableDisplayer){
        checkIfValidIndices(line, column)
        if(grid[line][column] != null) core.remove(grid[line][column]!!)
        grid[line][column] = component
        setComponentDimensions(component)
        setComponentPosition(line, column, component)
        core.add(component)
    }

    /**
     * Returns the component at the given line and column, or null if the cell is empty.
     * @throws IndexOutOfBoundsException If th eindices are out of bounds
     * @since LLayout 3
     */
    operator fun get(line : Int, column : Int) : ResizableDisplayer?{
        checkIfValidIndices(line, column)
        return grid[line][column]
    }

    /**
     * Removes the component at the given line and column.
     * @throws IndexOutOfBoundsException If the indices are out of bounds.
     * @return this
     * @since LLayout 3
     */
    fun removeAt(line : Int, column : Int) : RegularGrid{
        checkIfValidIndices(line, column)
        if(grid[line][column] != null){
            core.remove(grid[line][column]!!)
            grid[line][column] = null
        }
        return this
    }

    /**
     * Removes the given component from the grid.
     * @return this
     * @since LLayout 3
     */
    fun remove(component : ResizableDisplayer) : RegularGrid{
        for(i : Int in 0 until numberOfLines()){
            if(component in grid[i]){
                core.remove(component)
                grid[i][grid[i].indexOf(component)] = null
                return this
            }
        }
        return this
    }

    /**
     * The number of columns of the grid.
     * @see grid
     * @since LLayout 3
     */
    fun numberOfColumns() : Int = grid[0].size

    /**
     * The number of lines of the grid.
     * @see grid
     * @since LLayout 3
     */
    fun numberOfLines() : Int = grid.size

    /**
     * Perform the given action on each component of the grid.
     * @return this
     * @since LLayout 3
     */
    fun forEach(action : (ResizableDisplayer) -> Unit) : RegularGrid{
        for(line in grid){
            for(component in line){
                if(component != null) action(component)
            }
        }
        return this
    }

    /**
     * Performs the given action on each cell of the grid.
     * @return this
     * @since LLayout 3
     */
    fun forEachCell(action : (Int, Int) -> Unit) : RegularGrid{
        for(line : Int in 0 until numberOfLines()){
            for(column : Int in 0 until numberOfColumns()){
                action(line, column)
            }
        }
        return this
    }

    /**
     * Performs the given action on each nonempty cell of the grid.
     * @return this
     * @since LLayout 3
     */
    fun forEachComponent(action : (Int, Int) -> Unit) : RegularGrid{
        for(line : Int in 0 until numberOfLines()){
            for(column : Int in 0 until numberOfColumns()){
                if(grid[line][column] != null) action(line, column)
            }
        }
        return this
    }

    /**
     * Returns the index of the line of the given component, or -1 if it was not found.
     * @since LLayout 3
     */
    fun findLine(component : ResizableDisplayer) : Int{
        for(i : Int in 0 until numberOfLines()){
            if(grid[i].contains(component)) return i
        }
        return -1
    }

    /**
     * Returns the index of the column of the given component, or -1 if it was not found.
     * @since LLayout 3
     */
    fun findColumn(component : ResizableDisplayer) : Int{
        for(i : Int in 0 until numberOfLines()){
            if(grid[i].contains(component)) return grid[i].indexOf(component)
        }
        return -1
    }

    /**
     * Returns the indices of the line and the column of the given component, or a pair of -1 if it was not found.
     * @since LLayout 3
     */
    fun find(component: ResizableDisplayer) : Pair<Int, Int> = Pair(findLine(component), findColumn(component))

    /**
     * Returns true if the grid contains the given component, false otherwise.
     * @since LLayout 3
     */
    operator fun contains(component : ResizableDisplayer) : Boolean = findLine(component) != -1

    /**
     * Returns true if the grid doesn't contain any ResizableDisplayer, false otherwise.
     * @since LLayout 3
     */
    fun isEmpty() : Boolean{
        for(line in grid){
            for(component in line){
                if(component != null) return false
            }
        }
        return true
    }

    /**
     * Returns false if the grid doesn't contain any ResizableDisplayer, true otherwise.
     * @since LLayout 3
     */
    fun isNotEmpty() : Boolean = !isEmpty()

    /**
     * Returns true if the given indices are those of an empty cell, false otherwise.
     * @throws IndexOutOfBoundsException If the indices are out of bounds
     * @since LLayout 3
     */
    fun cellIsEmpty(line : Int, column : Int) : Boolean{
        checkIfValidIndices(line, column)
        return grid[line][column] == null
    }

    /**
     * Returns false if the given indices are those of an empty cell, true otherwise.
     * @throws IndexOutOfBoundsException If the indices are out of bounds
     * @since LLayout 3
     */
    fun cellIsNotEmpty(line : Int, column : Int) : Boolean = !cellIsEmpty(line, column)

    /**
     * Returns true if the given line doesn't contain any component, false otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun lineIsEmpty(line : Int) : Boolean{
        validLine(line)
        for(component : ResizableDisplayer? in grid[line]){
            if(component != null) return false
        }
        return true
    }

    /**
     * Returns false if the given line doesn't contain any component, true otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun lineIsNotEmpty(line : Int) : Boolean = !lineIsEmpty(line)

    /**
     * Returns true if the given column doesn't contain any component, false otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun columnIsEmpty(column : Int) : Boolean{
        validColumn(column)
        for(line in grid){
            if(line[column] != null) return false
        }
        return true
    }

    /**
     * Returns false if the given column doesn't contain any component, true otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun columnIsNotEmpty(column : Int) : Boolean = !columnIsEmpty(column)

    /**
     * Returns true if the given line doesn't contain any empty cell, false otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun lineIsFull(line : Int) : Boolean{
        validLine(line)
        for(component : ResizableDisplayer? in grid[line]){
            if(component == null) return false
        }
        return true
    }

    /**
     * Returns false if the given line doesn't contain any empty cell, true otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun lineIsNotFull(line : Int) : Boolean = !lineIsFull(line)

    /**
     * Returns true if the given column doesn't contain any empty cell, false otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun columnIsFull(column : Int) : Boolean{
        validColumn(column)
        for(line in grid){
            if(line[column] == null) return false
        }
        return true
    }

    /**
     * Returns false if the given column doesn't contain any empty cell, true otherwise.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 3
     */
    fun columnIsNotFull(column : Int) : Boolean = !columnIsFull(column)

    /**
     * Returns the indices of the line and column of an empty cell if there is one, or a pair of -1 otherwise.
     * @since LLayout 3
     */
    fun findAnEmptyCell() : Pair<Int, Int>{
        for(line : Int in 0 until numberOfLines()){
            if(grid[line].contains(null)) return Pair(line, grid[line].indexOf(null))
        }
        return Pair(-1, -1)
    }

    /**
     * Returns true if each cell of the grid contains a ResizableDisplayer, false otherwise.
     * @since LLayout 3
     */
    fun isFull() : Boolean{
        for(line in grid){
            for(component in line){
                if(component == null) return false
            }
        }
        return true
    }

    /**
     * Returns false if each cell of the grid contains a ResizableDisplayer, true otherwise.
     * @since LLayout 3
     */
    fun isNotFull() : Boolean = !isFull()

    /**
     * The width of a cell.
     * @since LLayout 3
     */
    private fun cellWidth() : Int = width() / numberOfColumns()

    /**
     * The height of a cell.
     * @since LLayout 3
     */
    private fun cellHeight() : Int = height() / numberOfLines()

    /**
     * Throws [IndexOutOfBoundsException]s if the given indices are out of bounds.
     * This method is used by other ones to check the validity of their arguments.
     * @since LLayout 3
     */
    private fun checkIfValidIndices(lineIndex : Int, columnIndex : Int){
        validLine(lineIndex)
        validColumn(columnIndex)
    }

    /**
     * Throws [IndexOutOfBoundsException] if the given line index is out of bounds.
     * This method is used by other ones to check the validity of their arguments.
     * @since LLayout 3
     */
    private fun validLine(lineIndex : Int){
        if(lineIndex < 0 || lineIndex >= numberOfLines())
            throw IndexOutOfBoundsException("Index $lineIndex out of bounds for the number of lines ${numberOfLines()}")
    }

    /**
     * Throws [IndexOutOfBoundsException] if the given column index is out of bounds.
     * This method is used by other ones to check the validity of their arguments.
     * @since LLayout 3
     */
    private fun validColumn(columnIndex: Int){
        if(columnIndex < 0 || columnIndex >= numberOfColumns())
            throw IndexOutOfBoundsException("Index $columnIndex out of bounds for the number of columns ${numberOfColumns()}")
    }

    /**
     * Sets the dimensions of the given component to those of a cell.
     * @since LLayout 3
     */
    private fun setComponentDimensions(component: ResizableDisplayer){
        component.setWidth(cellWidth()).setHeight(cellHeight())
    }

    /**
     * Sets the position of the given component such that it fits in the given cell.
     * @since LLayout 3
     */
    private fun setComponentPosition(line : Int, column : Int, component : ResizableDisplayer){
        component.setX(1.0/(2 * numberOfColumns()) + column.toDouble() / numberOfColumns())
        component.setY(1.0/(2 * numberOfLines()) + line.toDouble() / numberOfLines())
    }

    /**
     * Sets the dimensions of all the contained components.
     * @see setComponentDimensions
     * @since LLayout 3
     */
    private fun resizeAllComponents(){
        for(line : Array<ResizableDisplayer?> in grid){
            for(component : ResizableDisplayer? in line){
                if(component != null) setComponentDimensions(component)
            }
        }
    }

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        resizeAllComponents()
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        resizeAllComponents()
    }

}