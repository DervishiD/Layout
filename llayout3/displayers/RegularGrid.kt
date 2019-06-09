package llayout3.displayers

import java.awt.Graphics

/**
 * A Container that contains [ResizableDisplayer]s and arranges them in a grid.
 * The Grid has a fixed number of columns and lines. For a grid with variable dimensions, see TODO.
 * @see ResizableDisplayer
 * @see TODO
 * @since LLayout 3
 */
class RegularGrid : ResizableDisplayer {

    /**
     * The grid containing the added components in the correct order.
     * It contains nullable elements to signify empty cells.
     * @since LLayout 3
     */
    private val grid : Array<Array<ResizableDisplayer?>>

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
    private fun numberOfColumns() : Int = grid[0].size

    /**
     * The number of lines of the grid.
     * @see grid
     * @since LLayout 3
     */
    private fun numberOfLines() : Int = grid.size

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