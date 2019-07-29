package llayout7.displayers

import llayout7.utilities.RealFunction
import java.awt.Color
import java.awt.Graphics
import java.lang.Exception

/**
 * An [AbstractPlot] that can plot real functions.
 * @see AbstractPlot
 * @see RealFunction
 * @since LLayout 6
 */
class RealPlot : AbstractPlot {

    private companion object{

        /**
         * An internal index used to index functions.
         * @since LLayout 6
         */
        private var functionIndex : Int = Integer.MIN_VALUE

        /**
         * Returns a key used to index functions.
         * @since LLayout 6
         */
        private fun nextFunctionKey() : String = "The function with the internal index ${functionIndex++}"

        /**
         * The default color of the plotted functions.
         * @since LLayout 6
         */
        private val DEFAULT_FUNCTION_COLOR : Color = Color.BLACK

        /**
         * Contains enough information about a function to plot it
         * @param function The plotted function
         * @param color The color of the plot
         * @see RealFunction
         * @since LLayout 6
         */
        class PlottedFunction(private val function : RealFunction, private val color : Color){

            /**
             * Returns the represented function.
             * @see RealFunction
             * @since LLayout 6
             */
            fun function() : RealFunction = function

            /**
             * Returns the color of the function.
             * @since LLayout 6
             */
            fun color() : Color = color

        }

    }

    /**
     * The functions that this plot displays.
     * @see PlottedFunction
     * @since LLAyout 6
     */
    private val plottedFunctions : MutableMap<Any?, PlottedFunction> = mutableMapOf()

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    constructor() : super()

    /**
     * Adds a new [function] to the plot. The [key] is used if the user needs to remove it from the plot.
     * @return this
     * @see RealFunction
     * @since LLayout 6
     */
    fun plot(function : RealFunction, color : Color, key : Any? = nextFunctionKey()) : RealPlot{
        plottedFunctions[key] = PlottedFunction(function, color)
        updatePlot()
        return this
    }

    /**
     * Adds a new [function] to the plot. The [key] is used if the user needs to remove it from the plot.
     * @return this
     * @see RealFunction
     * @since LLayout 6
     */
    fun plot(function : RealFunction, key : Any? = nextFunctionKey()) : RealPlot = plot(function, DEFAULT_FUNCTION_COLOR, key)

    /**
     * Removes the function associated to the given key from the plot.
     * @return this
     * @since LLayout 6
     */
    fun removeFunction(key : Any?) : RealPlot{
        plottedFunctions.remove(key)
        updatePlot()
        return this
    }

    override fun clearPlot() {
        plottedFunctions.clear()
        updatePlot()
    }

    override fun redrawFunctions(){
        for(function : PlottedFunction in plottedFunctions.values){
            val points : MutableCollection<Pair<Int, Int>> = mutableSetOf()
            for(pixelX : Int in 0..width()){
                try{
                    val y : Double = function.function()(xOfPixel(pixelX))
                    if(yIsInRange(y)) points.add(Pair(pixelX, pixelOfY(y)))
                }catch(e : Exception){
                    //An invalid value
                }
            }
            core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
                g.color = function.color()
                for(point : Pair<Int, Int> in points) g.fillRect(point.first, point.second, 1, 1)
            })
        }
    }

}