package usages.springpendulum

import llayout6.displayers.PointPlot

internal object RhoPlot : PointPlot() {

    private const val DELTA_T : Double = 0.1

    private var time : Double = 0.0

    internal fun plot(rho : Double){
        addPoint(time, rho)
        time += DELTA_T
    }

    internal fun reset(){
        clearPlot()
        time = 0.0
    }

}