package debug

import llayout6.displayers.VectorFieldPlot
import llayout6.frame.*
import java.awt.Color
import kotlin.math.sin

private object TestScreen : LScene(){

    private val plot : VectorFieldPlot = VectorFieldPlot(1.0, 1.0)

    init{

        plot.setXRange(-5, 5).setYRange(-5, 5).drawMesh(0.5)
        plot.plot { x : Double, y : Double -> Pair(sin(y), -sin(x)) }
        val maxNorm = 5.0
        plot.setColourFunction { x -> if(x > maxNorm) Color.RED else Color( ( 255 * x / maxNorm ).toInt() , 0, 0) }
        add(plot.alignTopTo(0).alignLeftTo(0))

    }

}

private val frame : LFrame = LFrame(TestScreen)

val testApplication : LApplication = LApplication{ frame.run() }
