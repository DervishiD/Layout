package llayout7.displayers

import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage

/**
 * An [AbstractPlot] that can plot a real function of two variables, or, equivalently, a complex function, as a colour plot.
 * @see AbstractPlot
 * @since LLayout 6
 */
class ColourPlot : AbstractPlot {

    private companion object{

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

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    constructor() : super()

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

    override fun clearPlot(){
        plot(EMPTY_PLOT)
    }

    override fun redrawFunctions(){
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

}