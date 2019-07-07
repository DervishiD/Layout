package usages.springpendulum

import llayout6.displayers.PointPlot

internal object ThetaPlot : PointPlot() {

    private const val DELTA_T : Double = 0.1

    private var time : Double = 0.0

    internal fun plot(theta : Double){
        addPoint(time, theta)
        time += DELTA_T
    }

    internal fun reset(){
        clearPlot()
        time = 0.0
    }

}