package usages.doublependulum

import llayout6.displayers.Canvas

internal object SimulationPane : Canvas() {

    private const val ITERATIONS_PER_TICK : Int = 1000

    private const val INTEGRATION_STEP : Double = TIMER_PERIOD * 1e-3 / ITERATIONS_PER_TICK

}