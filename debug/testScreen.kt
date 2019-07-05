package debug

import llayout6.displayers.PointPlot
import llayout6.frame.*
import kotlin.math.sin

private object TestScreen : LScene(){

    private val plot : PointPlot = PointPlot(1.0, 1.0)

    private var time : Double = 0.1

    private const val DELTA_T : Double = 0.05

    init{

        plot.mediumPoints()
        add(plot.alignLeftTo(0).alignTopTo(0))

        setOnTimerTickAction {
            plot.addPoint(time, sin(time) / time)
            time += DELTA_T
        }

    }

}

private val frame : LFrame = LFrame(TestScreen)

val testApplication : LApplication = LApplication{ frame.run() }
