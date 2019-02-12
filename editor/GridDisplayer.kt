package editor

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

    private var grid : Grid = Grid(1, 1)

    private var mesh : Int = 50 // TODO

    private fun cellAt(line : Int, column : Int) = grid.cellAt(line, column)

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
        val highestColumnIndex : Int = floor((grid.origin().x - highestX()) / mesh).toInt()
        return if(highestColumnIndex >= grid.columns) grid.columns else highestColumnIndex
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
        val highestLineIndex : Int = floor((grid.origin().y - highestY()) / mesh).toInt()
        return if(highestLineIndex >= grid.lines) grid.lines else highestLineIndex
    }

    override fun loadParameters(g: Graphics) {
        //TODO
    }

    override fun drawDisplayer(g: Graphics) {
        //TODO -- CHECK FOR MODIFICATIONS IN SIZE, ETC
        for(line : Int in lowestLineIndexOnScreen()..highestLineIndexOnScreen()){
            for(column : Int in lowestColumnIndexOnScreen()..highestColumnIndexOnScreen()){
                drawCell(g, cellAt(line, column))
            }
        }
    }

    private fun drawCell(g : Graphics, cell : Cell){
        //TODO
    }

}