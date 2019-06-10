package llayout3.displayers

import java.awt.Graphics

/**
 * A Container that contains [ResizableDisplayer]s and arranges them in a grid.
 * The Grid has a variable number of columns and lines. For a grid with fixed dimensions, see [RegularGrid].
 * @see ResizableDisplayer
 * @see RegularGrid
 * @since LLayout 3
 */
class MutableRegularGrid : ResizableDisplayer {

    /**
     * The grid containing the added components in the correct order.
     * It contains nullable elements to signify empty cells.
     * @since LLayout 3
     */
    private val grid : MutableList<MutableList<ResizableDisplayer?>>

    init{
        addWidthListener { resizeAllComponents() }
        addHeightListener { resizeAllComponents() }
    }

    constructor(lines : Int, columns : Int, gridWidth : Int, gridHeight : Int) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = MutableList(lines) { MutableList<ResizableDisplayer?>(columns) { null } }
    }

    constructor(lines : Int, columns : Int, gridWidth : Double, gridHeight : Int) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = MutableList(lines) { MutableList<ResizableDisplayer?>(columns) { null } }
    }


    constructor(lines : Int, columns : Int, gridWidth : Int, gridHeight : Double) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = MutableList(lines) { MutableList<ResizableDisplayer?>(columns) { null } }
    }


    constructor(lines : Int, columns : Int, gridWidth : Double, gridHeight : Double) : super(gridWidth, gridHeight){
        if(columns <= 0) throw IllegalArgumentException("The number of columns must be at least 1")
        if(lines <= 0) throw IllegalArgumentException("The number of lines must be at least 1")
        grid = MutableList(lines) { MutableList<ResizableDisplayer?>(columns) { null } }
    }

    /**
     * Adds the given component at the given line and column.
     * @throws IndexOutOfBoundsException If the indices are invalid.
     * @since LLayout 3
     */
    operator fun set(line : Int, column : Int, component : ResizableDisplayer){
        checkIfValidIndices(line, column)
        grid[line][column] = component
        setComponentDimensions(component)
        setComponentPosition(line, column, component)
        core.add(component)
    }

    /**
     * Removes the component at the given line and column.
     * @throws IndexOutOfBoundsException If the indices are out of bounds.
     * @return this
     * @since LLayout 3
     */
    fun removeAt(line : Int, column : Int) : MutableRegularGrid{
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
    fun remove(component : ResizableDisplayer) : MutableRegularGrid{
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
     * Adds an empty line at the given index.
     * @throws IndexOutOfBoundsException If the index is invalid.
     * @return this
     * @since LLayout 3
     */
    fun addLine(index : Int) : MutableRegularGrid{
        if(index == numberOfLines()){
            grid.add(MutableList(numberOfColumns()) { null } )
        }else{
            validLine(index)
            grid.add(index, MutableList(numberOfColumns()) { null } )
        }
        resizeAllComponents()
        return this
    }

    /**
     * Adds a new empty line at the bottom of the grid.
     * @return this
     * @since LLayout 3
     */
    fun addLine() : MutableRegularGrid = addLine(numberOfLines())

    /**
     * Adds an empty column at the given index.
     * @throws IndexOutOfBoundsException If the index is invalid.
     * @return this
     * @since LLayout 3
     */
    fun addColumn(index : Int) : MutableRegularGrid{
        if(index == numberOfColumns()){
            for(line : MutableList<ResizableDisplayer?> in grid){
                line.add(null)
            }
        }else{
            validColumn(index)
            for(line : MutableList<ResizableDisplayer?> in grid){
                line.add(index, null)
            }
        }
        resizeAllComponents()
        return this
    }

    /**
     * Adds a new empty column at the right side of the grid.
     * @return this
     * @since LLayout 3
     */
    fun addColumn() : MutableRegularGrid = addColumn(numberOfColumns())

    /**
     * Removes the line with the given index.
     * @throws IndexOutOfBoundsException If the index is invalid.
     * @return this
     * @since LLayout 3
     */
    fun removeLine(index : Int) : MutableRegularGrid{
        validLine(index)
        for(component : ResizableDisplayer? in grid[index]){
            if(component != null) core.remove(component)
        }
        grid.removeAt(index)
        resizeAllComponents()
        return this
    }

    /**
     * Removes the first line of the grid.
     * @return this
     * @since LLayout 3
     */
    fun removeFirstLine() : MutableRegularGrid = removeLine(0)

    /**
     * Removes the last line of the grid.
     * @return this
     * @since LLayout 3
     */
    fun removeLastLine() : MutableRegularGrid = removeLine(numberOfLines() - 1)

    /**
     * Removes the column with the given index.
     * @throws IndexOutOfBoundsException If the index is invalid.
     * @return this
     * @since LLayout 3
     */
    fun removeColumn(index : Int) : MutableRegularGrid{
        validColumn(index)
        for(line : MutableList<ResizableDisplayer?> in grid){
            if(line[index] != null) core.remove(line[index]!!)
            line.removeAt(index)
        }
        resizeAllComponents()
        return this
    }

    /**
     * Removes the first column of the grid.
     * @return this
     * @since LLayout 3
     */
    fun removeFirstColumn() : MutableRegularGrid = removeColumn(0)

    /**
     * Removes the last column of the grid.
     * @return this
     * @since LLayout 3
     */
    fun removeLastColumn() : MutableRegularGrid = removeColumn(numberOfColumns() - 1)

    /**
     * The number of columns of the grid.
     * @since LLayout 3
     */
    fun numberOfColumns() : Int = grid[0].size

    /**
     * The number of lines of the grid.
     * @since LLayout 3
     */
    fun numberOfLines() : Int = grid.size

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
        for(i : Int in 0 until numberOfLines()){
            for(j : Int in 0 until numberOfColumns()){
                val component = grid[i][j]
                if(component != null){
                    setComponentDimensions(component)
                    setComponentPosition(i, j, component)
                }
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