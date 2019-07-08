package usages.cellularautomaton

import llayout6.displayers.HorizontalDiscreteSlider
import llayout6.displayers.Label
import llayout6.frame.LScene
import java.awt.Color

internal object FirstSelectionScene : LScene() {

    private const val MINIMAL_WIDTH : Int = 5

    private const val MINIMAL_HEIGHT : Int = 5

    private const val MAXIMAL_WIDTH : Int = 100

    private const val MAXIMAL_HEIGHT : Int = 100

    private const val SLIDER_WIDTH : Double = 0.5

    private const val SLIDER_HEIGHT : Int = 40

    private val DEFAULT_ALIVE_CELL_COLOUR : Color = Color.BLACK

    private val DEFAULT_DEAD_CELL_COLOUR : Color = Color.WHITE

    private val DEFAULT_BORDER_COLOUR : Color = Color.LIGHT_GRAY

    private val WIDTH_LABEL : Label = Label("Width")

    private val WIDTH_SLIDER : HorizontalDiscreteSlider =
            HorizontalDiscreteSlider(SLIDER_WIDTH, SLIDER_HEIGHT, MAXIMAL_WIDTH - MINIMAL_WIDTH + 1)

    private val WIDTH_INDICATOR : Label = Label()

    private val HEIGHT_LABEL : Label = Label("Height")

    private val HEIGHT_SLIDER : HorizontalDiscreteSlider =
            HorizontalDiscreteSlider(SLIDER_WIDTH, SLIDER_HEIGHT, MAXIMAL_HEIGHT - MINIMAL_HEIGHT + 1)

    private val HEIGHT_INDICATOR : Label = Label()

    /*
     * Implement the colour selection.
     */

    init{
        addWidthParameters()
        addHeightParameters()
    }

    private fun addWidthParameters(){
        addWidthLabel()
        addWidthSlider()
        addWidthIndicator()
    }

    private fun addWidthLabel(){
        TODO("Not implemented.")
    }

    private fun addWidthSlider(){
        TODO("Not implemented.")
    }

    private fun addWidthIndicator(){
        TODO("Not implemented.")
    }

    private fun addHeightParameters(){
        addHeightLabel()
        addHeightSlider()
        addHeightIndicator()
    }

    private fun addHeightLabel(){
        TODO("Not implemented.")
    }

    private fun addHeightSlider(){
        TODO("Not implemented.")
    }

    private fun addHeightIndicator(){
        TODO("Not implemented.")
    }

}