package usages.pendulum

import llayout6.displayers.PointPlot
import llayout6.frame.LScene

internal object PlotScene : LScene() {

    private val plot : PointPlot = PointPlot(1.0, 1.0)

    private var time : Double = 0.0

    private const val DELTA_T : Double = 0.1

    init {
        add(plot.mediumPoints().alignTopTo(0).alignLeftTo(0))
    }

    internal fun plot(theta : Double){
        plot.addPoint(time, theta)
        time += DELTA_T
    }

    internal fun clear(){
        time = 0.0
        plot.clearPlot()
    }

}