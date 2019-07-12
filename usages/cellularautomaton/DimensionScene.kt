package usages.cellularautomaton

import llayout6.displayers.*
import llayout6.frame.LScene

internal object DimensionScene : LScene() {

    private const val MINIMAL_WIDTH : Int = 10

    private const val MINIMAL_HEIGHT : Int = 10

    private const val MAXIMAL_WIDTH : Int = 60

    private const val MAXIMAL_HEIGHT : Int = 60

    private const val SLIDER_WIDTH : Double = 0.4

    private const val SLIDER_HEIGHT : Int = 40

    private const val LABEL_X : Double = 0.05

    private const val SLIDER_LABEL_GAP : Int = 10

    private const val LABEL_INDICATOR_GAP : Int = 10

    private const val WIDTH_Y : Double = 0.1

    private const val HEIGHT_Y : Double = 0.3

    private val HEIGHT_LABEL : Label = Label("Height")

    private val HEIGHT_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val HEIGHT_INDICATOR : Label = Label()

    private val WIDTH_LABEL : Label = Label("Width")

    private val WIDTH_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val WIDTH_INDICATOR : Label = Label()

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    private val NEXT_BUTTON : TextButton = TextButton("Next") { next() }

    init{
        addWidthParameters()
        addHeightParameters()
        addBackButton()
        addNextButton()
    }

    private fun addWidthParameters(){
        addWidthLabel()
        addWidthSlider()
        addWidthIndicator()
    }

    private fun addWidthLabel(){
        add(HEIGHT_LABEL.setX(LABEL_X).setY(WIDTH_Y))
    }

    private fun addWidthSlider(){
        HEIGHT_SLIDER.setRange(MINIMAL_WIDTH, MAXIMAL_WIDTH).setPrecision(1)
        add(HEIGHT_SLIDER.alignLeftToRight(HEIGHT_LABEL, SLIDER_LABEL_GAP).setY(WIDTH_Y))
    }

    private fun addWidthIndicator(){
        HEIGHT_SLIDER.addValueListener { HEIGHT_INDICATOR.setText(HEIGHT_SLIDER.value().toInt()) }
        add(HEIGHT_INDICATOR.alignLeftToLeft(HEIGHT_LABEL).alignTopToBottom(HEIGHT_LABEL, LABEL_INDICATOR_GAP))
    }

    private fun addHeightParameters(){
        addHeightLabel()
        addHeightSlider()
        addHeightIndicator()
    }

    private fun addHeightLabel(){
        add(WIDTH_LABEL.setX(LABEL_X).setY(HEIGHT_Y))
    }

    private fun addHeightSlider(){
        WIDTH_SLIDER.setRange(MINIMAL_HEIGHT, MAXIMAL_HEIGHT).setPrecision(1)
        add(WIDTH_SLIDER.alignLeftToRight(WIDTH_LABEL, SLIDER_LABEL_GAP).setY(HEIGHT_Y))
    }

    private fun addHeightIndicator(){
        WIDTH_SLIDER.addValueListener { WIDTH_INDICATOR.setText(WIDTH_SLIDER.value().toInt()) }
        add(WIDTH_INDICATOR.alignLeftToLeft(WIDTH_LABEL).alignTopToBottom(WIDTH_LABEL, LABEL_INDICATOR_GAP))
    }

    private fun addBackButton(){
        add(BACK_BUTTON.setX(0.33).setY(0.8))
    }

    private fun addNextButton(){
        add(NEXT_BUTTON.setX(0.66).setY(0.8))
    }

    private fun back(){
        frame.setScene(MainMenuScene)
    }

    private fun selectedHeight() : Int = HEIGHT_SLIDER.value().toInt()

    private fun selectedWidth() : Int = WIDTH_SLIDER.value().toInt()

    private fun next(){
        updateParameters()
        frame.setScene(ColourScene)
    }

    private fun updateParameters(){
        Parameters.setWidth(selectedHeight())
        Parameters.setHeight(selectedWidth())
    }

}