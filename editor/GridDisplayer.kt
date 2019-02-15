package editor

import display.DEFAULT_COLOR
import display.Displayer
import gamepackage.gamegeometry.Grid
import geometry.Point
import main.mouseDisplacement
import java.awt.Graphics
import kotlin.math.floor
import java.awt.RenderingHints
import java.awt.image.BufferedImage



/**
 * The Displayer that displays the Grid in the editor
 */
class GridDisplayer(p: Point, width : Int, height : Int) : Displayer(p) {

    private companion object {
        private const val DEFAULT_MESH_SIZE : Int = 120
        private const val MAX_ZOOM_MESH : Int = 20
        private const val MESH_ZOOM_DELTA : Int = 10
    }

    override var w: Int = width
    override var h: Int = height

    private var grid : Grid = Grid(10, 10)

    private var mesh : Int = DEFAULT_MESH_SIZE

    constructor(x : Int, y : Int, width : Int, height : Int) : this(Point(x, y), width, height)

    /**
     * Obtains the cell at the given position
     */
    private fun cellAt(line : Int, column : Int) = grid.cellAt(line, column)

    /**
     * The grid's origin
     */
    private fun origin() : Point = grid.origin()

    /**
     * Zooms in
     */
    private fun zoomIn(){
        if(mesh < DEFAULT_MESH_SIZE) mesh += MESH_ZOOM_DELTA
    }

    /**
     * Zooms out
     */
    private fun zoomOut(){
        if(mesh > MAX_ZOOM_MESH) mesh -= MESH_ZOOM_DELTA
    }

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
        val lowestColumnIndex : Int = floor((lowestX() - origin().x) / mesh).toInt()
        return if(lowestColumnIndex <= 0) 0 else lowestColumnIndex
    }

    /**
     * The highest column index currently displayed on the screen
     */
    private fun highestColumnIndexOnScreen() : Int{
        val highestColumnIndex : Int = floor((highestX() - origin().x) / mesh).toInt()
        return if(highestColumnIndex >= grid.columns) grid.columns - 1 else highestColumnIndex
    }

    /**
     * The lowest line index currently displayed on the screen
     */
    private fun lowestLineIndexOnScreen() : Int{
        val lowestLineIndex : Int = floor((lowestY() - origin().y) / mesh).toInt()
        return if(lowestLineIndex <= 0) 0 else lowestLineIndex
    }

    /**
     * The highest line index currently displayed on the screen
     */
    private fun highestLineIndexOnScreen() : Int{
        val highestLineIndex : Int = floor((highestY() - origin().y) / mesh).toInt()
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

        val originalImage : BufferedImage = cellAt(line, column).image()
        if(mesh == originalImage.height){
            g.drawImage(originalImage, x, y, null)
        }else{
            val resized = BufferedImage(mesh, mesh, originalImage.type)
            val gr = resized.createGraphics()
            gr.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
            )
            gr.drawImage(
                originalImage, 0, 0, mesh, mesh, 0, 0, originalImage.width,
                originalImage.height, null
            )
            gr.dispose()
            g.drawImage(resized, x, y, null)
        }
        g.color = DEFAULT_COLOR
        g.drawRect(x, y, mesh, mesh)
    }

    override fun mouseWheelMoved(units: Int) {
        if(units < 0){
            for(i : Int in 1..-units){
                zoomIn()
            }
        }else{
            for(i : Int in 1..units){
                zoomOut()
            }
        }
    }

    override fun mouseDrag() = grid moveAlong mouseDisplacement()

}