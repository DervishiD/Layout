package llayout7.displayers

import llayout7.utilities.Action
import llayout7.utilities.GraphicAction
import llayout7.utilities.LObservable
import java.awt.Color
import java.awt.Graphics
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A plot on which the user can add Points and Vectors one by one. The plot resizes itself at each addition.
 * @see ResizableDisplayer
 * @since LLayout 6
 */
open class PointPlot : ResizableDisplayer {

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
         * The dimension of the plot if only one point is plotted.
         * @since LLayout 6
         */
        private const val SINGLE_POINT_DIMENSION : Double = 0.1

        /**
         * The size of small points.
         * @since LLayout 6
         */
        private const val SMALL_POINT_SIZE : Int = 2

        /**
         * The size of medium points.
         * @since LLayout 6
         */
        private const val MEDIUM_POINT_SIZE : Int = 3

        /**
         * The size of large points.
         * @since LLayout 6
         */
        private const val LARGE_POINT_SIZE : Int = 4

        /**
         * The default colour of the drawn points.
         * @since LLayout 6
         */
        private val DEFAULT_POINT_COLOUR : Color = Color.BLACK

        /**
         * The default colour of the drawn vectors.
         * @since LLayout 6
         */
        private val DEFAULT_VECTOR_COLOUR_FUNCTION : (Double) -> Color = { _ -> Color.BLACK }

        /**
         * The minimal norm that a vector must have to be drawn.
         * @since LLayout 6
         */
        private const val MINIMAL_DRAWING_VECTOR_NORM : Double = 0.1

        /**
         * The length of a drawn arrow.
         * @since LLayout 6
         */
        private const val ARROW_LENGTH : Int = 20

        /**
         * Th ehalf width of an arrow.
         * @since LLayout 6
         */
        private const val ARROW_HALF_WIDTH : Int = ARROW_LENGTH / 8

    }

    /**
     * The plotted points.
     * @since LLayout 6
     */
    private var points : MutableCollection<Pair<Double, Double>> = mutableSetOf()

    /**
     * The plotted vectors.
     * @since LLayout 6
     */
    private var vectors : MutableMap<Pair<Double, Double>, Pair<Double, Double>> = mutableMapOf()

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
     * The plot's background.
     * @see GraphicAction
     * @since LLayout 6
     */
    private var background : GraphicAction = DEFAULT_BACKGROUND

    /**
     * The size of the drawn points.
     * @since LLayout 6
     */
    private var pointSize : Int = SMALL_POINT_SIZE

    /**
     * The colour of the points.
     * @since LLayout 6
     */
    private var pointColour : Color = DEFAULT_POINT_COLOUR

    /**
     * The colour of the vectors.
     * @since LLayout 6
     */
    private var vectorColourFunction : (Double) -> Color = DEFAULT_VECTOR_COLOUR_FUNCTION

    init{
        minimalX.addListener { updatePlot() }
        minimalY.addListener { updatePlot() }
        maximalX.addListener { updatePlot() }
        maximalY.addListener { updatePlot() }
        core.addGraphicAction(DEFAULT_BACKGROUND, BACKGROUND_KEY)
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    constructor() : super()

    /**
     * Adds a new point to the plot.
     * @return this
     * @since LLayout 6
     */
    fun addPoint(x : Double, y : Double) : PointPlot{
        if(plotIsEmpty()) loadFirst(x, y) else updateView(x, y)
        points.add(Pair(x, y))
        updatePlot()
        return this
    }

    /**
     * Adds a new vector to the plot.
     * @return this
     * @since LLayout 6
     */
    fun addVector(x : Double, y : Double, vectorX : Double, vectorY : Double) : PointPlot{
        if(plotIsEmpty()) loadFirst(x, y) else updateView(x, y)
        vectors[Pair(x, y)] = Pair(vectorX, vectorY)
        updatePlot()
        return this
    }

    /**
     * Clears the plot.
     * @return this
     * @since LLayout 6
     */
    fun clearPlot() : PointPlot{
        minimalX.value = 0.0
        maximalX.value = 0.0
        minimalY.value = 0.0
        maximalY.value = 0.0
        points.clear()
        vectors.clear()
        updatePlot()
        return this
    }

    /**
     * Adds a listener to the maximal plotted x of this plot, associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun addMaximalXListener(key : Any?, action : Action) : PointPlot{
        maximalX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the maximal plotted x of this plot.
     * @return this
     * @since LLayout 6
     */
    fun addMaximalXListener(action : Action) : PointPlot{
        maximalX.addListener(action)
        return this
    }

    /**
     * Removes the listener of the maximal x of this plot associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun removeMaximalXListener(key : Any?) : PointPlot{
        maximalX.removeListener(key)
        return this
    }

    /**
     * Adds a listener to the minimal plotted x of this plot, associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun addMinimalXListener(key : Any?, action : Action) : PointPlot{
        minimalX.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the minimal plotted x of this plot.
     * @return this
     * @since LLayout 6
     */
    fun addMinimalXListener(action : Action) : PointPlot{
        minimalX.addListener(action)
        return this
    }

    /**
     * Removes the listener of the minimal x of this plot associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun removeMinimalXListener(key : Any?) : PointPlot{
        minimalX.removeListener(key)
        return this
    }

    /**
     * Adds a listener to the maximal plotted y of this plot, associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun addMaximalYListener(key : Any?, action : Action) : PointPlot{
        maximalY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the maximal plotted y of this plot.
     * @return this
     * @since LLayout 6
     */
    fun addMaximalYListener(action : Action) : PointPlot{
        maximalY.addListener(action)
        return this
    }

    /**
     * Removes the listener of the maximal y of this plot associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun removeMaximalYListener(key : Any?) : PointPlot{
        maximalY.removeListener(key)
        return this
    }

    /**
     * Adds a listener to the minimal plotted y of this plot, associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun addMinimalYListener(key : Any?, action : Action) : PointPlot{
        minimalY.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the minimal plotted y of this plot.
     * @return this
     * @since LLayout 6
     */
    fun addMinimalYListener(action : Action) : PointPlot{
        minimalY.addListener(action)
        return this
    }

    /**
     * Removes the listener of the minimal y of this plot associated to the given key.
     * @return this
     * @since LLayout 6
     */
    fun removeMinimalYListener(key : Any?) : PointPlot{
        minimalY.removeListener(key)
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
     * Returns the length of the plot in the x direction.
     * @since LLayout 6
     */
    fun xRange() : Double = upperXBound() - lowerXBound()

    /**
     * Returns the length of the plot in the y direction.
     * @since LLayout 6
     */
    fun yRange() : Double = upperYBound() - lowerYBound()

    /**
     * Sets a new background for this plot.
     * @return this
     * @see GraphicAction
     * @since LLayout 6
     */
    fun setBackground(background : GraphicAction) : PointPlot{
        this.background = background
        core.addGraphicAction(background, BACKGROUND_KEY)
        return this
    }

    /**
     * Draws small points.
     * @return this
     * @since LLayout 6
     */
    fun smallPoints() : PointPlot{
        pointSize = SMALL_POINT_SIZE
        updatePlot()
        return this
    }

    /**
     * Draws medium points.
     * @return this
     * @since LLayout 6
     */
    fun mediumPoints() : PointPlot{
        pointSize = MEDIUM_POINT_SIZE
        updatePlot()
        return this
    }

    /**
     * Draws large points.
     * @return this
     * @since LLayout 6
     */
    fun largePoints() : PointPlot{
        pointSize = LARGE_POINT_SIZE
        updatePlot()
        return this
    }

    /**
     * Changes the colour of the drawn points.
     * @return this
     * @since LLayout 6
     */
    fun setPointsColour(colour : Color) : PointPlot{
        pointColour = colour
        updatePlot()
        return this
    }

    /**
     * Changes the function that associates a colour to the vectors in function of the norm.
     * @return this
     * @since LLayout 6
     */
    fun setVectorsColour(colourFunction : (Double) -> Color) : PointPlot{
        vectorColourFunction = colourFunction
        updatePlot()
        return this
    }

    /**
     * Returns true if the plot doesn't plot anything.
     * @since LLayout 6
     */
    private fun plotIsEmpty() : Boolean = points.isEmpty() && vectors.isEmpty()

    /**
     * Loads the view for the first added object.
     * @since LLayout 6
     */
    private fun loadFirst(x : Double, y : Double){
        minimalX.value = x - SINGLE_POINT_DIMENSION / 2
        maximalX.value = x + SINGLE_POINT_DIMENSION / 2
        minimalY.value = y - SINGLE_POINT_DIMENSION / 2
        maximalY.value = y + SINGLE_POINT_DIMENSION / 2
    }

    /**
     * Updates the view when a new object is added.
     * @since LLayout 6
     */
    private fun updateView(x : Double, y : Double){
        if(x > upperXBound()){
            maximalX.value = x
        }else if(x < lowerXBound()){
            minimalX.value = x
        }
        if(y > upperYBound()){
            maximalY.value = y
        }else if(y < lowerYBound()){
            minimalY.value = y
        }
    }

    /**
     * Updates the drawn plot.
     * @since LLayout 6
     */
    private fun updatePlot(){
        core.clearBackground()
        setBackground(background)
        drawPoints()
        drawVectors()
    }

    /**
     * Returns the pixel of the given point.
     * @since LLayout 6
     */
    private fun pixelOfX(x : Double) : Int = ( width() * ( x - lowerXBound() ) / xRange() ).toInt()

    /**
     * Returns the pixel of the given point.
     * @since LLayout 6
     */
    private fun pixelOfY(y : Double) : Int = ( height() * ( 1 - ( y - lowerYBound() ) / yRange() ) ).toInt()

    /**
     * Draws the plotted points.
     * @since LLayout 6
     */
    private fun drawPoints(){
        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            g.color = pointColour
            for(point : Pair<Double, Double> in points){
                g.fillRect(pixelOfX(point.first) - pointSize / 2, pixelOfY(point.second) - pointSize / 2, pointSize, pointSize)
            }
        })
    }

    /**
     * Draws the plotted vectors.
     * @since LLayout 6
     */
    private fun drawVectors(){
        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            for(vector in vectors.entries){
                drawVector(pixelOfX(vector.key.first), pixelOfY(vector.key.second), vector.value.first, vector.value.second, g)
            }
        })
    }

    /**
     * Draws a single vector on this plot.
     * @since LLayout 6
     */
    private fun drawVector(i : Int, j : Int, x : Double, y : Double, g : Graphics){
        val norm : Double = sqrt(x * x + y * y)
        if(norm >= MINIMAL_DRAWING_VECTOR_NORM){
            g.color = vectorColourFunction(norm)
            val angle : Double = atan2(y, x)
            val nextPixelX : Int = (i + ARROW_LENGTH * cos(angle)).toInt()
            val nextPixelY : Int = (j - ARROW_LENGTH * sin(angle)).toInt()
            val middleX : Int = (i + ARROW_LENGTH * cos(angle) / 2).toInt()
            val middleY : Int = (j - ARROW_LENGTH * sin(angle) / 2).toInt()
            val leftX : Int = (middleX - ARROW_HALF_WIDTH * sin(angle)).toInt()
            val leftY : Int = (middleY - ARROW_HALF_WIDTH * cos(angle)).toInt()
            val rightX : Int = (middleX + ARROW_HALF_WIDTH * sin(angle)).toInt()
            val rightY : Int = (middleY + ARROW_HALF_WIDTH * cos(angle)).toInt()
            g.drawLine(i, j, middleX, middleY)
            g.fillPolygon(intArrayOf(leftX, nextPixelX, rightX), intArrayOf(leftY, nextPixelY, rightY), 3)
        }
    }

}