package debug

import llayout6.displayers.ColourPlot
import llayout6.frame.*
import java.awt.Color
import kotlin.math.sin
import kotlin.math.sqrt

private object TestScreen : LScene(){

    private val plot : ColourPlot = ColourPlot(1.0, 1.0)

    init{

        plot.setXRange(-15, 15).setYRange(-15, 15)
        plot.plot { x, y ->
            val t : Double = ( sin( sqrt( x * x + y * y ) ) + 1 ) / 2
            Color(( 255 * t ).toInt(), ( 255 * t ).toInt(), ( 255 * t ).toInt())
        }
        add(plot.alignLeftTo(0).alignTopTo(0))

    }

}

private val frame : LFrame = LFrame(TestScreen)

val testApplication : LApplication = LApplication{ frame.run() }
