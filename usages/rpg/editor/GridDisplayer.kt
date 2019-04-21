package usages.rpg.editor

import llayout.*
import llayout.displayers.Displayer
import llayout.frame.LMouse
import usages.rpg.gamepackage.gamegeometry.Cell
import usages.rpg.gamepackage.gamegeometry.Grid
import llayout.geometry.Point
import usages.rpg.DEFAULT_GRID_MESH_SIZE
import usages.rpg.GRID_IMAGE_SIZE_DELTA
import usages.rpg.SMALLEST_GRID_IMAGE_SIZE
import java.awt.Graphics
import kotlin.math.floor
import java.awt.RenderingHints
import java.awt.image.BufferedImage

/**
 * The Displayer that displays the Grid in the usages.rpg.editor
 */
class GridDisplayer : Displayer {

    private companion object {
        private const val DEFAULT_MESH_SIZE : Int = DEFAULT_GRID_MESH_SIZE
        private const val MAX_ZOOM_MESH : Int = SMALLEST_GRID_IMAGE_SIZE
        private const val MESH_ZOOM_DELTA : Int = GRID_IMAGE_SIZE_DELTA
    }

    private var grid : Grid = Grid(10, 10)

    private var mesh : Int = DEFAULT_MESH_SIZE

    private var relativeW : Double? = null
    private var relativeH : Double? = null

    constructor(p : Point, width : Int, height : Int) : super(p){
        w.value = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): Displayer {
        if(relativeW != null) w.value = (frameWidth * relativeW!!).toInt()
        if(relativeH != null) h.value = (frameHeight * relativeH!!).toInt()
        return super.updateRelativeValues(frameWidth, frameHeight)
    }

    /**
     * Obtains the cell at the given position
     */
    private fun cellAt(line : Int, column : Int) = grid.cellAt(line, column)

    /**
     * The grid's origin
     */
    private var origin : Point = grid.origin()

    /**
     * The grid's number of columns
     */
    fun gridWidth() : Int = grid.columns

    /**
     * The grid's number of lines
     */
    fun gridHeight() : Int = grid.lines

    /**
     * Zooms in
     */
    private fun zoomIn(){
        if(mesh < DEFAULT_MESH_SIZE){
            performMeshVariation(MESH_ZOOM_DELTA)
        }
    }

    /**
     * Zooms out
     */
    private fun zoomOut(){
        if(mesh > MAX_ZOOM_MESH){
            performMeshVariation(-MESH_ZOOM_DELTA)
        }
    }

    /**
     * Abstracts the zoom operation as a variation in the mesh and a displacement of the displayed grid
     * @param meshVariation The absolute variation, in pixels, of the mesh
     */
    private fun performMeshVariation(meshVariation : Int){
        val correspondingGridPoint : Point = frameToGrid(LMouse.mousePosition())
        val line : Int = lineOf(correspondingGridPoint)
        val column : Int = columnOf(correspondingGridPoint)
        val proportionaldx : Double = correspondingGridPoint.x / mesh - column
        val proportionaldy : Double = correspondingGridPoint.y / mesh - line
        mesh += meshVariation
        val newGridPoint = Point((column + proportionaldx) * mesh, (line + proportionaldy) * mesh)
        origin += newGridPoint..correspondingGridPoint
    }

    /**
     * Converts a Point on the displayed Grid to one in the frame
     */
    private fun gridToFrame(p : Point) : Point = origin + p.toVector()

    /**
     * Converts a Point in the frame to one on the displayed Grid
     */
    private fun frameToGrid(p : Point) : Point = p - origin.toVector()

    /**
     * Returns the column index of a Point on the dispalyed Grid
     */
    private fun columnOf(pointInDisplayedGrid : Point) : Int = floor(pointInDisplayedGrid.x / mesh).toInt()

    /**
     * Returns the line index of a Point on the displayed Grid
     */
    private fun lineOf(pointInDisplayedGrid : Point) : Int = floor(pointInDisplayedGrid.y / mesh).toInt()

    /**
     * Modifies the width of the Grid
     */
    infix fun setGridWidth(newWidth : Int){
        if(newWidth != gridWidth()){
            grid.setColumnsNumber(newWidth)
        }
    }

    /**
     * Modifies the height of the Grid
     */
    infix fun setGridHeight(newHeight : Int){
        if(newHeight != gridHeight()){
            grid.setLinesNumber(newHeight)
        }
    }

    /**
     * Centers the displayed grid in the frame
     */
    fun resetOrigin(){
        origin setx (width() / 2) - (gridWidth() / 2) * mesh
        origin sety (height() / 2) - (gridHeight() / 2) * mesh
    }

    /**
     * Returns the line with given index
     */
    fun line(index : Int) : ArrayList<Cell> = grid.line(index)

    /**
     * Returns the column with given index
     */
    fun column(index : Int) : ArrayList<Cell> = grid.column(index)

    /**
     * Returns a list of lines starting at firstIncl and ending at endIncl
     */
    fun lines(firstIncl : Int, lastIncl : Int) : ArrayList<ArrayList<Cell>> = grid.lines(firstIncl, lastIncl)

    /**
     * Returns a list of columns starting at firstIncl and ending at endIncl
     */
    fun columns(firstIncl : Int, lastIncl : Int) : ArrayList<ArrayList<Cell>> = grid.columns(firstIncl, lastIncl)

    /**
     * The leftmost x coordinate of a cell in a given column
     */
    private fun cellLeftX(column : Int) : Int = (origin.x + column * mesh).toInt()

    /**
     * The uppermost y coordinate of a cell in a given line
     */
    private fun cellUpY(line : Int) : Int = (origin.y + line * mesh).toInt()

    /**
     * The lowest column index currently displayed on the screen
     */
    private fun lowestColumnIndexOnScreen() : Int{
        val lowestColumnIndex : Int = floor((lowestX() - origin.x) / mesh).toInt()
        return if(lowestColumnIndex <= 0) 0 else lowestColumnIndex
    }

    /**
     * The highest column index currently displayed on the screen
     */
    private fun highestColumnIndexOnScreen() : Int{
        val highestColumnIndex : Int = floor((highestX() - origin.x) / mesh).toInt()
        return if(highestColumnIndex >= grid.columns) grid.columns - 1 else highestColumnIndex
    }

    /**
     * The lowest line index currently displayed on the screen
     */
    private fun lowestLineIndexOnScreen() : Int{
        val lowestLineIndex : Int = floor((lowestY() - origin.y) / mesh).toInt()
        return if(lowestLineIndex <= 0) 0 else lowestLineIndex
    }

    /**
     * The highest line index currently displayed on the screen
     */
    private fun highestLineIndexOnScreen() : Int{
        val highestLineIndex : Int = floor((highestY() - origin.y) / mesh).toInt()
        return if(highestLineIndex >= grid.lines) grid.lines - 1 else highestLineIndex
    }

    override fun loadParameters(g: Graphics) {
        //TODO?
    }

    override fun drawDisplayer(g: Graphics) {
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
        g.drawImage(resizedImage(originalImage), x, y, null)
        g.color = DEFAULT_COLOR
        g.drawRect(x, y, mesh, mesh)
    }

    /**
     * Returns a resized version of a background image, according to the displayed gris mesh
     */
    private infix fun resizedImage(originalImage : BufferedImage) : BufferedImage{
        return if(mesh == originalImage.height){
            originalImage
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
            resized
        }
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

    override fun mouseDrag(){
        origin += LMouse.mouseDisplacement()
    }

}