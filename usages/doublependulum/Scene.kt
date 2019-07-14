package usages.doublependulum

import llayout6.frame.LScene

internal object Scene : LScene() {

    private const val SIMULATION_WIDTH : Double = 0.75

    private const val PARAMETERS_WIDTH : Double = 1 - SIMULATION_WIDTH

    private const val SLIDER_WIDTH : Double = 0.8 * PARAMETERS_WIDTH

    private const val SLIDER_HEIGHT : Double = 0.03

    private const val CENTRAL_X : Double = PARAMETERS_WIDTH / 2

    private const val GRAPH_HEIGHT : Double = 0.25

    private const val SIMULATION_PANE_HEIGHT : Double = 1 - 2 * GRAPH_HEIGHT

    init{
        
    }

}