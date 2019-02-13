package editor

import display.DEFAULT_COLOR
import display.Displayer
import gamepackage.gamegeometry.Cell
import gamepackage.gamegeometry.Grid
import geometry.Point
import java.awt.Graphics
import kotlin.math.floor

/**
 * The Displayer that displays the Grid in the editor
 */
class GridDisplayer(p: Point, width : Int, height : Int) : Displayer(p) {

    override var w: Int = width
    override var h: Int = height

    private var grid : Grid = Grid(10, 10)

    private var mesh : Int = 120 // TODO

    constructor(x : Int, y : Int, width : Int, height : Int) : this(Point(x, y), width, height)

    /**
     * Obtains the cell at the given position
     */
    private fun cellAt(line : Int, column : Int) = grid.cellAt(line, column)

    /**
     * The grid's origin
     */
    private fun origin() = grid.origin()

    /**
     * The leftmost x coordinate of a cell in a given column
     */
    private fun cellLeftX(column : Int) : Int = (origin().x + column * mesh).toInt()

    /**
     * The uppermost y coordinate of a cell in a given line
     */
    private fun cellUpY(line : Int) : Int = (origin().y + line * mesh).toInt()

    /**
     * The lowest column index currently displayed on the screen
     */
    private fun lowestColumnIndexOnScreen() : Int{
        val lowestColumnIndex : Int = floor((grid.origin().x - lowestX()) / mesh).toInt()
        return if(lowestColumnIndex <= 0) 0 else lowestColumnIndex
    }

    /**
     * The highest column index currently displayed on the screen
     */
    private fun highestColumnIndexOnScreen() : Int{
        val highestColumnIndex : Int = floor((highestX() - grid.origin().x) / mesh).toInt()
        return if(highestColumnIndex >= grid.columns) grid.columns - 1 else highestColumnIndex
    }

    /**
     * The lowest line index currently displayed on the screen
     */
    private fun lowestLineIndexOnScreen() : Int{
        val lowestLineIndex : Int = floor((grid.origin().y - lowestY()) / mesh).toInt()
        return if(lowestLineIndex <= 0) 0 else lowestLineIndex
    }

    /**
     * The highest line index currently displayed on the screen
     */
    private fun highestLineIndexOnScreen() : Int{
        val highestLineIndex : Int = floor((highestY() - grid.origin().y) / mesh).toInt()
        return if(highestLineIndex >= grid.lines) grid.lines - 1 else highestLineIndex
    }

    override fun loadParameters(g: Graphics) {
        //TODO
    }

    override fun drawDisplayer(g: Graphics) {
        //TODO -- CHECK FOR MODIFICATIONS IN SIZE, ETC
        for(line : Int in lowestLineIndexOnScreen()..highestLineIndexOnScreen()){
            for(column : Int in lowestColumnIndexOnScreen()..highestColumnIndexOnScreen()){
                drawCell(g, line, column)
            }
        }
    }

    /**
     * Draws a cell on the screen
     */
    private fun drawCell(g : Graphics, line : Int, column : Int){
        val x : Int = cellLeftX(column) - lowestX()
        val y : Int = cellUpY(line) - lowestY()
        g.drawImage(cellAt(line, column).image(), x, y, null)
        g.color = DEFAULT_COLOR
        g.drawRect(x, y, mesh, mesh)
    }

}