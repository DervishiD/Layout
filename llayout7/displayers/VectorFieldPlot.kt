package llayout7.displayers

import java.awt.Color
import java.awt.Graphics
import java.lang.Exception
import kotlin.math.*

/**
 * An [AbstractPlot] that plots vector fields in two dimensions.
 * @see AbstractPlot
 * @since LLayout 6
 */
class VectorFieldPlot : AbstractPlot {

    private companion object{

        /**
         * The mesh used if no mesh is provided.
         * @since LLayout 6
         */
        private const val DEFAULT_MESH : Double = 1.0

        /**
         * A constant field that is always (0.0, 0.0).
         * @since LLayout 6
         */
        private val ZERO_FIELD : (Double, Double) -> Pair<Double, Double> = { _, _ -> Pair(0.0, 0.0) }

        /**
         * A function that returns [Color.BLACK] no matter what the argument is.
         * @since LLayout 6
         */
        private val MONOCHROMATIC_FUNCTION : (Double) -> Color = { _ -> Color.BLACK }

        /**
         * A constant that indicates the minimal norm at which a vector is drawn.
         * @since LLayout 6
         */
        private const val MINIMAL_NORM : Double = 0.01

        /**
         * The default mesh of the vector plot.
         * @since LLayout 6
         */
        private const val DEFAULT_VECTOR_MESH : Double = 1.0

    }

    /**
     * The plotted field.
     * @since LLayout 6
     */
    private var field : (Double, Double) -> Pair<Double, Double> = ZERO_FIELD

    /**
     * The function that attributes colours to the vectors.
     * @since LLayout 6
     */
    private var colourFunction : (Double) -> Color = MONOCHROMATIC_FUNCTION

    /**
     * The mesh of the vector grid.
     * @since LLayout 6
     */
    private var vectorMesh : Double = DEFAULT_VECTOR_MESH

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    constructor() : super()

    /**
     * Plots the given field.
     * @return this
     * @since LLayout 6
     */
    fun plot(field : (Double, Double) -> Pair<Double, Double>) : VectorFieldPlot{
        this.field = field
        updatePlot()
        return this
    }

    /**
     * Gives this plot a function that associates a [Color] to the norm of a vector.
     * The given norm is strictly positive (almost-null vectors are not drawn).
     * @return this
     * @since LLayout 6
     */
    fun setColourFunction(function : (Double) -> Color) : VectorFieldPlot{
        colourFunction = function
        updatePlot()
        return this
    }

    /**
     * Determines the mesh of the vector field.
     * @since LLayout 6
     */
    fun setMeshSize(mesh : Double) : VectorFieldPlot{
        vectorMesh = if(mesh > 0) mesh else DEFAULT_VECTOR_MESH
        updatePlot()
        return this
    }

    /**
     * Returns the mesh of the field.
     * @since LLayout 6
     */
    private fun vectorMesh() : Double = vectorMesh

    override fun clearPlot() {
        field = ZERO_FIELD
        updatePlot()
    }

    override fun redrawFunctions() {
        val nly : Int
        val nhy : Int
        when{
            yIsInRange(0.0) -> {
                nly = floor(lowerYBound() / vectorMesh()).toInt()
                nhy = ceil(upperYBound() / vectorMesh()).toInt()
            }
            0.0 < lowerYBound() -> {
                nly = floor(lowerYBound() / vectorMesh()).toInt()
                nhy = ceil(upperYBound() / vectorMesh()).toInt()
            }
            else -> {
                nly = floor(lowerYBound() / vectorMesh()).toInt()
                nhy = floor(upperYBound() / vectorMesh()).toInt()
            }
        }
        val nlx : Int
        val nhx : Int
        when{
            xIsInRange(0.0) -> {
                nlx = floor(lowerXBound() / vectorMesh()).toInt()
                nhx = ceil(upperXBound() / vectorMesh()).toInt()
            }
            0.0 < lowerXBound() -> {
                nlx = floor(lowerXBound() / vectorMesh()).toInt()
                nhx = ceil(upperXBound() / vectorMesh()).toInt()
            }
            else -> {
                nlx = floor(lowerXBound() / vectorMesh()).toInt()
                nhx = floor(upperXBound() / vectorMesh()).toInt()
            }
        }

        var x : Double
        var y : Double
        val vectorsToDraw : MutableMap<Pair<Int, Int>, Pair<Double, Double>> = mutableMapOf()

        for(i : Int in nlx..nhx){
            for(j : Int in nly..nhy){
                x = i * vectorMesh()
                y = j * vectorMesh()
                try{
                    vectorsToDraw[Pair(pixelOfX(x), pixelOfY(y))] = field(x, y)
                }catch(e : Exception){
                    //Field undefined at the point
                }
            }
        }

        fun drawVector(i : Int, j : Int, x : Double, y : Double, g : Graphics){

            fun arrowLength() : Int{
                return if(width() == 0 || height() == 0){
                    1
                }else{
                    ( ( vectorMesh() / min(xRange(), yRange()) ) * min(width(), height()) ).toInt()
                }
            }

            val norm : Double = sqrt(x * x + y * y)
            if(vectorHasSufficientNorm(norm)){
                val angle : Double = atan2(y, x)
                g.color = colourFunction(norm)
                val arrowLength : Int = arrowLength()
                val tipHalfWidth : Int = arrowLength / 8
                val nextPixelX : Int = (i + arrowLength * cos(angle)).toInt()
                val nextPixelY : Int = (j - arrowLength * sin(angle)).toInt()
                val middleX : Int = (i + arrowLength * cos(angle) / 2).toInt()
                val middleY : Int = (j - arrowLength * sin(angle) / 2).toInt()
                val leftX : Int = (middleX - tipHalfWidth * sin(angle)).toInt()
                val leftY : Int = (middleY - tipHalfWidth * cos(angle)).toInt()
                val rightX : Int = (middleX + tipHalfWidth * sin(angle)).toInt()
                val rightY : Int = (middleY + tipHalfWidth * cos(angle)).toInt()
                g.drawLine(i, j, middleX, middleY)
                g.fillPolygon(intArrayOf(leftX, nextPixelX, rightX), intArrayOf(leftY, nextPixelY, rightY), 3)
            }
        }

        core.addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            for(vector in vectorsToDraw.entries){
                drawVector(vector.key.first, vector.key.second, vector.value.first, vector.value.second, g)
            }
        })

    }

    /**
     * True if the given norm is sufficient to let the vector be drawn.
     * @since LLayout 6
     */
    private fun vectorHasSufficientNorm(norm : Double) : Boolean = norm >= MINIMAL_NORM

}