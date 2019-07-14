package usages.doublependulum

import llayout6.displayers.PointPlot

internal object PsiPlot: PointPlot() {

    private const val DELTA_T : Double = 0.1

    private var time : Double = 0.0

    internal fun plot(psi : Double){
        addPoint(time, psi)
        time += DELTA_T
    }

    internal fun reset(){
        clearPlot()
        time = 0.0
    }

}