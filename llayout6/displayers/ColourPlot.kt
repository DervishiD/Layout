package llayout6.displayers

import llayout6.utilities.GraphicAction
import llayout6.utilities.LObservable
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * A [ResizableDisplayer] that can plot a real function of two variables, or, equivalently, a complex function, as a colour plot.
 * @see ResizableDisplayer
 * @since LLayout 6
 */
class ColourPlot : ResizableDisplayer {

    private companion object{

        /**
         * The default color of the drawn lines.
         * @since LLayout 6
         */
        private val DEFAULT_BACKGROUND_LINE_COLOR : Color = Color.BLACK

        /**
         * The thickness of the drawn lines.
         * @since LLAyout 6
         */
        private const val DEFAULT_BACKGROUND_LINE_THICKNESS : Int = 2

        /**
         * The default background.
         * @see GraphicAction
         * @since LLayout 6
         */
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = DEFAULT_BACKGROUND_LINE_COLOR
            g.fillRect(0, 0, w, DEFAULT_BACKGROUND_LINE_THICKNESS)
            g.fillRect(0, 0, DEFAULT_BACKGROUND_LINE_THICKNESS, h)
            g.fillRect(w - DEFAULT_BACKGROUND_LINE_THICKNESS, 0, DEFAULT_BACKGROUND_LINE_THICKNESS, h)
            g.fillRect(0, h - DEFAULT_BACKGROUND_LINE_THICKNESS, w, DEFAULT_BACKGROUND_LINE_THICKNESS)
        }

        /**
         * The key used to recognize the background.
         * @since LLayout 6
         */
        private const val BACKGROUND_KEY : String = "The key of this plot's background"

        /**
         * The thickness of the coordinate axes.
         * @since LLayout 6
         */
        private const val AXES_THICKNESS : Int = 4

        /**
         * The value that indicates that no mesh is drawn.
         * @since LLayout 6
         */
        private const val NO_MESH : Double = -1.0

        /**
         * The thickness of the drawn mesh.
         * @since LLayout 6
         */
        private const val MESH_THICKNESS : Int = 2

        /**
         * The default mesh size.
         * @since LLayout 6
         */
        private const val DEFAULT_MESH_SIZE : Double = 1.0

        /**
         * The function that represents an empty plot.
         * @since LLayout 6
         */
        private val EMPTY_PLOT : (Double, Double) -> Color = { _, _ -> Color.WHITE }

    }

    /**
     * The plotted function.
     * @since LLayout 6
     */
    private var plottedFunction : (Double, Double) -> Color = EMPTY_PLOT
    
    /**
     * The minimal x value displayed.
     * @see LObservable
     * @since LLayout 6
     */
    private val minimalX : LObservable<Double> = LObservable(0.0)

    /**
     * The maximal x value displayed.
     * @see LObservable
     * @since LLayout 6
     */
    private val maximalX : LObservable<Double> = LObservable(0.0)

    /**
     * The minimal y value displayed.
     * @see LObservable
     * @since LLayout 6
     */
    private val minimalY : LObservable<Double> = LObservable(0.0)

    /**
     * The maximal y value displayed.
     * @see LObservable
     * @since LLayout 6
     */
    private val maximalY : LObservable<Double> = LObservable(0.0)

    /**
     * True if the coordinate axes are drawn.
     * @since LLayout 6
     */
    private var drawingAxes : Boolean = true

    /**
     * The color of the coordinate axes.
     * @since LLayout 6
     */
    private var axesColor : Color = DEFAULT_BACKGROUND_LINE_COLOR

    /**
     * The size of the plot's mesh.
     * @since LLayout 6
     */
    private var gridMesh : Double = DEFAULT_MESH_SIZE

    /**
     * The color of the mesh.
     * @since LLayout 6
     */
    private var meshColor : Color = DEFAULT_BACKGROUND_LINE_COLOR

    /**
     * The background of the plot.
     * @see GraphicAction
     * @since LLAyout 6
     */
    private var background : GraphicAction = DEFAULT_BACKGROUND

    init{
        minimalX.addListener {
            if(minimalX.value > maximalX.value) swapXBounds()
            updatePlot()
        }
        minimalY.addListener {
            if(minimalY.value > maximalY.value) swapYBounds()
            updatePlot()
        }
        maximalX.addListener {
            if(minimalX.value > maximalX.value) swapXBounds()
            updatePlot()
        }
        maximalY.addListener {
            if(minimalY.value > maximalY.value) swapYBounds()
            updatePlot()
        }
        core.addGraphicAction(DEFAULT_BACKGROUND, BACKGROUND_KEY)
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    constructor() : super()

    /**
     * Sets the minimal x displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMinimalX(x : Double) : ColourPlot{
        minimalX.value = x
        return this
    }

    /**
     * Sets the minimal x displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMinimalX(x : Int) : ColourPlot = setMinimalX(x.toDouble())

    /**
     * Sets the maximal x displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMaximalX(x : Double) : ColourPlot{
        maximalX.value = x
        return this
    }

    /**
     * Sets the maximal x displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMaximalX(x : Int) : ColourPlot = setMaximalX(x.toDouble())

    /**
     * Sets the minimal y displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMinimalY(y : Double) : ColourPlot{
        minimalY.value = y
        return this
    }

    /**
     * Sets the minimal y displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMinimalY(y : Int) : ColourPlot = setMinimalY(y.toDouble())

    /**
     * Sets the maximal y displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMaximalY(y : Double) : ColourPlot{
        maximalY.value = y
        return this
    }

    /**
     * Sets the maximal y displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setMaximalY(y : Int) : ColourPlot = setMaximalY(y.toDouble())

    /**
     * Sets the range of the x values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setXRange(minimum : Double, maximum : Double) : ColourPlot = setMinimalX(min(minimum, maximum)).setMaximalX(max(minimum, maximum))

    /**
     * Sets the range of the x values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setXRange(minimum : Int, maximum : Double) : ColourPlot = setMinimalX(min(minimum.toDouble(), maximum)).setMaximalX(max(minimum.toDouble(), maximum))

    /**
     * Sets the range of the x values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setXRange(minimum : Double, maximum : Int) : ColourPlot = setMinimalX(min(minimum, maximum.toDouble())).setMaximalX(max(minimum, maximum.toDouble()))

    /**
     * Sets the range of the x values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setXRange(minimum : Int, maximum : Int) : ColourPlot = setMinimalX(min(minimum, maximum)).setMaximalX(max(minimum, maximum))

    /**
     * Sets the range of the y values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setYRange(minimum : Double, maximum : Double) : ColourPlot = setMinimalY(min(minimum, maximum)).setMaximalY(max(minimum, maximum))

    /**
     * Sets the range of the y values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setYRange(minimum : Int, maximum : Double) : ColourPlot = setMinimalY(min(minimum.toDouble(), maximum)).setMaximalY(max(minimum.toDouble(), maximum))

    /**
     * Sets the range of the y values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setYRange(minimum : Double, maximum : Int) : ColourPlot = setMinimalY(min(minimum, maximum.toDouble())).setMaximalY(max(minimum, maximum.toDouble()))

    /**
     * Sets the range of the y values displayed by this plot.
     * @return this
     * @since LLayout 6
     */
    fun setYRange(minimum : Int, maximum : Int) : ColourPlot = setMinimalY(min(minimum, maximum)).setMaximalY(max(minimum, maximum))

    /**
     * Plots the given function.
     * @return this
     * @since LLayout 6
     */
    fun plot(function : (Double, Double) -> Color) : ColourPlot{
        plottedFunction = function
        updatePlot()
        return this
    }

    /**
     * Clears the plot.
     * @return this
     * @since LLayout 6
     */
    fun clearPlot() : ColourPlot = plot(EMPTY_PLOT)

    /**
     * Sets a new background for this plot.
     * @return this
     * @see GraphicAction
     * @since LLayout 6
     */
    private fun setBackground(background : GraphicAction) : ColourPlot{
        this.background = background
        core.addGraphicAction(background, BACKGROUND_KEY)
        return this
    }

    /**
     * Returns the minimal x that this plot displays.
     * @since LLayout 6
     */
    fun lowerXBound() : Double = minimalX.value

    /**
     * Returns the maximal x that this plot displays.
     * @since LLayout 6
     */
    fun upperXBound() : Double = maximalX.value

    /**
     * Returns the minimal y that this plot displays.
     * @since LLayout 6
     */
    fun lowerYBound() : Double = minimalY.value

    /**
     * Returns the maximal y that this plot displays.
     * @since LLayout 6
     */
    fun upperYBound() : Double = maximalY.value

    /**
     * Draws the coordinate axes.
     * @since LLayout 6
     */
    fun drawAxes(color : Color = DEFAULT_BACKGROUND_LINE_COLOR) : ColourPlot{
        drawingAxes = true
        axesColor = color
        updatePlot()
        return this
    }

    /**
     * Doesn't draw the coordinate axes.
     * @since LLayout 6
     */
    fun noAxes() : ColourPlot{
        drawingAxes = false
        updatePlot()
        return this
    }

    /**
     * Draws a mesh on the plot.
     * @since LLayout 6
     */
    fun drawMesh(meshSize : Double = DEFAULT_MESH_SIZE, color : Color = DEFAULT_BACKGROUND_LINE_COLOR) : ColourPlot{
        return if(meshSize <= 0){
            noMesh()
        }else{
            gridMesh = meshSize
            meshColor = color
            updatePlot()
            this
        }
    }

    /**
     * Doesn't draw a mesh on the plot.
     * @since LLayout 6
     */
    fun noMesh() : ColourPlot{
        gridMesh = NO_MESH
        updatePlot()
        return this
    }

    /**
     * Swap the bounds of the x values displayed by this plot.
     * @since LLayout 6
     */
    private fun swapXBounds(){
        val temporary : Double = minimalX.value
        minimalX.value = maximalX.value
        maximalX.value = temporary
    }

    /**
     * Swap the bounds of the y values displayed by this plot.
     * @since LLayout 6
     */
    private fun swapYBounds(){
        val temporary : Double = minimalY.value
        minimalY.value = maximalY.value
        maximalY.value = temporary
    }

    /**
     * True if a mesh is drawn.
     * @since LLayout 6
     */
    private fun drawingMesh() : Boolean = gridMesh != NO_MESH

    /**
     * Redraws the plot.
     * @sinceLLayout 6
     */
    private fun updatePlot(){
        core.clearBackground()
        setBackground(background)
        redrawFunction()
        if(drawingAxes) redrawAxes()
        if(drawingMesh()) redrawMesh()
    }

    /**
     * Draws both coordinate axes.
     * @since LLayout 6
     */
    private fun redrawAxes(){
        if(yIsInRange(0.0)) drawXAxis()
        if(xIsInRange(0.0)) drawYAxis()
    }

    /**
     * Draws the x axis.
     * @since LLayout 6
     */
    private fun drawXAxis(){
        core.addGraphicAction({ g : Graphics, w : Int, _ : Int ->
            g.color = axesColor
            val pixelY = pixelOfY(0.0)
            g.fillRect(0, pixelY - AXES_THICKNESS / 2, w, AXES_THICKNESS)
        })
    }

    /**
     * Draws the y axis.
     * @since LLayout 6
     */
    private fun drawYAxis(){
        core.addGraphicAction({ g : Graphics, _ : Int, h : Int ->
            g.color = axesColor
            val pixelX = pixelOfX(0.0)
            g.fillRect(pixelX - AXES_THICKNESS / 2, 0, AXES_THICKNESS, h)
        })
    }

    /**
     * Draws a mesh on the plot.
     * @since LLayout 6
     */
    private fun redrawMesh(){
        redrawHorizontalLines()
        redrawVerticalLines()
    }

    /**
     * Draws the horizontal lines of the mesh.
     * @since LLayout 6
     */
    private fun redrawHorizontalLines(){
        val nl : Int
        val nh : Int
        when{
            yIsInRange(0.0) -> {
                nl = floor(lowerYBound() / gridMesh).toInt()
                nh = ceil(upperYBound() / gridMesh).toInt()
            }
            0.0 < lowerYBound() -> {
                nl = floor(lowerYBound() / gridMesh).toInt()
                nh = ceil(upperYBound() / gridMesh).toInt()
            }
            else -> {
                nl = floor(lowerYBound() / gridMesh).toInt()
                nh = floor(upperYBound() / gridMesh).toInt()
            }
        }
        core.addGraphicAction({ g : Graphics, w : Int, _ : Int ->
            g.color = meshColor
            for(i : Int in nl..nh){
                g.fillRect(0, pixelOfY(gridMesh * i) - MESH_THICKNESS / 2, w, MESH_THICKNESS)
            }
        })
    }

    /**
     * Draws the vertical lines of the mesh.
     * @since LLayout 6
     */
    private fun redrawVerticalLines(){
        val nl : Int
        val nh : Int
        when{
            xIsInRange(0.0) -> {
                nl = floor(lowerXBound() / gridMesh).toInt()
                nh = ceil(upperXBound() / gridMesh).toInt()
            }
            0.0 < lowerXBound() -> {
                nl = floor(lowerXBound() / gridMesh).toInt()
                nh = ceil(upperXBound() / gridMesh).toInt()
            }
            else -> {
                nl = floor(lowerXBound() / gridMesh).toInt()
                nh = floor(upperXBound() / gridMesh).toInt()
            }
        }
        core.addGraphicAction({ g : Graphics, _ : Int, h : Int ->
            g.color = meshColor
            for(i : Int in nl..nh){
                g.fillRect(pixelOfX(gridMesh * i) - MESH_THICKNESS / 2, 0, MESH_THICKNESS, h)
            }
        })
    }

    /**
     * Returns the x value corresponding to the given pixel.
     * @since LLayout 6
     */
    private fun xOfPixel(pixelX : Int) : Double{
        val t : Double = pixelX.toDouble() / width()
        return (1 - t) * lowerXBound() + t * upperXBound()
    }

    /**
     * Returns the y value corresponding to the given pixel.
     * @since LLayout 6
     */
    private fun yOfPixel(pixelY : Int) : Double{
        val t : Double = pixelY.toDouble() / height()
        return (1 - t) * upperYBound() + t * lowerYBound()
    }

    /**
     * Returns the pixel of the given y value.
     * @since LLayout 6
     */
    private fun pixelOfY(y : Double) : Int{
        return if(lowerYBound() == upperYBound()){
            0
        }else{
            ( ( 1 - (y - lowerYBound()) / (upperYBound() - lowerYBound()) ) * height() ).toInt()
        }
    }

    /**
     * Returns the pixel of the given x value.
     * @since LLayout 6
     */
    private fun pixelOfX(x : Double) : Int{
        return if(lowerXBound() == upperXBound()){
            0
        }else{
            ( ( (x - lowerXBound()) / (upperXBound() - lowerXBound()) ) * width() ).toInt()
        }
    }

    /**
     * Redraws the functions on this plot.
     * @since LLayout 6
     */
    private fun redrawFunction(){
        val image = if(width() != 0 && height() != 0)
            BufferedImage(width(), height(), BufferedImage.TYPE_INT_RGB) else
            BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
        for(i : Int in 0..image.width){
            for(j : Int in 0..image.height){
                val g : Graphics = image.graphics
                g.color = plottedFunction(xOfPixel(i), yOfPixel(j))
                g.fillRect(i, j, 1, 1)
            }
        }
        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            g.drawImage(image, 0, 0, null)
        })
    }

    /**
     * Returns true if the given y value is on this plot's range.
     * @since LLayout 6
     */
    private fun yIsInRange(y : Double) : Boolean = y >= lowerYBound() && y <= upperYBound()

    /**
     * Returns true if the given x value is on this plot's range.
     * @since LLayout 6
     */
    private fun xIsInRange(x : Double) : Boolean = x >= lowerXBound() && x <= upperXBound()

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        updatePlot()
    }

}