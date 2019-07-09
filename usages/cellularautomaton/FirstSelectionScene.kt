package usages.cellularautomaton

import llayout6.DEFAULT_MEDIUM_FONT
import llayout6.displayers.*
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay
import java.awt.Color
import java.awt.Graphics

internal object FirstSelectionScene : LScene() {

    private class ColourSquare : Canvas(){

        private val unLinkedAlpha : Int = 128

        private var colour : Color = Color.BLACK

        private var linked : Boolean = false

        init{
            setOnMouseReleasedAction { link() }
            addGraphicAction({ g : Graphics, w : Int, h : Int -> draw(g, w, h) })
        }

        internal fun setColour(colour : Color){
            this.colour = colour
        }

        internal fun setLinked(){
            linked = true
        }

        internal fun setUnLinked(){
            linked = false
        }

        internal fun colour() : Color = colour

        private fun link() = ColourSelector.link(this, colour)

        private fun draw(g : Graphics, w : Int, h : Int){
            g.color = if(linked) colour else unLinkedColour()
            g.fillRect(0, 0, w, h)
        }

        private fun unLinkedColour() : Color = Color(colour.red, colour.green, colour.blue, unLinkedAlpha)

    }

    private object ColourSelector : ResizableDisplayer(){

        private const val SLIDER_WIDTH : Int = 40

        private const val SLIDER_HEIGHT : Double = 0.75

        private const val SLIDER_LABEL_GAP : Int = 10

        private val FRAME_COLOUR : Color = Color.BLACK

        private var colourSquare : ColourSquare = ALIVE_COLOUR_SQUARE

        private val RED_LABEL : Label = Label(StringDisplay("Red", DEFAULT_MEDIUM_FONT, Color.RED))

        private val GREEN_LABEL : Label = Label(StringDisplay("Green", DEFAULT_MEDIUM_FONT, Color.GREEN))

        private val BLUE_LABEL : Label = Label(StringDisplay("Blue", DEFAULT_MEDIUM_FONT, Color.BLUE))

        private val RED_SLIDER : VerticalDoubleSlider = VerticalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

        private val GREEN_SLIDER : VerticalDoubleSlider = VerticalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

        private val BLUE_SLIDER : VerticalDoubleSlider = VerticalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

        init{
            colourSquare.setLinked()
            core.twoPixelFrame(FRAME_COLOUR)
            addRed()
            addGreen()
            addBlue()
        }

        private fun addRed(){
            addRedLabel()
            addRedSlider()
        }

        private fun addRedLabel(){
            core.add(RED_LABEL.setX(0.16).setY(0.16))
        }

        private fun addRedSlider(){
            RED_SLIDER.setRange(0, 255).setPrecision(1)
            RED_SLIDER.addValueListener { update() }
            core.add(RED_SLIDER.setX(0.16).alignTopToBottom(RED_LABEL, SLIDER_LABEL_GAP))
        }

        private fun addGreen(){
            addGreenLabel()
            addGreenSlider()
        }

        private fun addGreenLabel(){
            core.add(GREEN_LABEL.setX(0.5).setY(0.16))
        }

        private fun addGreenSlider(){
            GREEN_SLIDER.setRange(0, 255).setPrecision(1)
            GREEN_SLIDER.addValueListener { update() }
            core.add(GREEN_SLIDER.setX(0.5).alignTopToBottom(GREEN_LABEL, SLIDER_LABEL_GAP))
        }

        private fun addBlue(){
            addBlueLabel()
            addBlueSlider()
        }

        private fun addBlueLabel(){
            core.add(BLUE_LABEL.setX(0.84).setY(0.16))
        }

        private fun addBlueSlider(){
            BLUE_SLIDER.setRange(0, 255).setPrecision(1)
            BLUE_SLIDER.addValueListener { update() }
            core.add(BLUE_SLIDER.setX(0.84).alignTopToBottom(BLUE_LABEL, SLIDER_LABEL_GAP))
        }

        internal fun link(colourSquare : ColourSquare, colour : Color){
            this.colourSquare.setUnLinked()
            this.colourSquare = colourSquare
            this.colourSquare.setLinked()
            setSliderColour(colour)
        }

        private fun setSliderColour(colour : Color){
            RED_SLIDER.setStartingValue(colour.red)
            GREEN_SLIDER.setStartingValue(colour.green)
            BLUE_SLIDER.setStartingValue(colour.blue)
        }

        private fun colour() : Color = Color(RED_SLIDER.value().toInt(), GREEN_SLIDER.value().toInt(), BLUE_SLIDER.value().toInt())

        private fun update() = colourSquare.setColour(colour())

    }

    private const val MINIMAL_WIDTH : Int = 10

    private const val MINIMAL_HEIGHT : Int = 10

    private const val MAXIMAL_WIDTH : Int = 100

    private const val MAXIMAL_HEIGHT : Int = 100

    private const val SLIDER_WIDTH : Double = 0.4

    private const val SLIDER_HEIGHT : Int = 40

    private const val LABEL_X : Double = 0.05

    private const val SLIDER_LABEL_GAP : Int = 10

    private const val LABEL_INDICATOR_GAP : Int = 10

    private const val WIDTH_Y : Double = 0.1

    private const val HEIGHT_Y : Double = 0.3

    private const val COLOUR_LABEL_Y : Double = 0.45

    private const val LABEL_SQUARE_GAP : Int = 10

    private const val COLOUR_SELECTOR_WIDTH : Double = 0.4

    private const val COLOUR_SELECTOR_HEIGHT : Double = 0.6

    private const val SQUARE_SIDE_LENGTH : Int = 40

    private val DEFAULT_ALIVE_CELL_COLOUR : Color = Color.BLACK

    private val DEFAULT_DEAD_CELL_COLOUR : Color = Color.WHITE

    private val DEFAULT_BORDER_COLOUR : Color = Color.LIGHT_GRAY

    private val WIDTH_LABEL : Label = Label("Width")

    private val WIDTH_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val WIDTH_INDICATOR : Label = Label()

    private val HEIGHT_LABEL : Label = Label("Height")

    private val HEIGHT_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val HEIGHT_INDICATOR : Label = Label()

    private val ALIVE_COLOUR_LABEL : Label = Label("Alive cell colour")

    private val ALIVE_COLOUR_SQUARE : ColourSquare = ColourSquare()

    private val DEAD_COLOUR_LABEL : Label = Label("Dead cell colour")

    private val DEAD_COLOUR_SQUARE : ColourSquare = ColourSquare()

    private val BORDER_COLOUR_LABEL : Label = Label("Border colour")

    private val BORDER_COLOUR_SQUARE : ColourSquare = ColourSquare()

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    private val NEXT_BUTTON : TextButton = TextButton("Next") { next() }

    init{
        addWidthParameters()
        addHeightParameters()
        addColour()
        addBackButton()
        addNextButton()
    }

    private fun addWidthParameters(){
        addWidthLabel()
        addWidthSlider()
        addWidthIndicator()
    }

    private fun addWidthLabel(){
        add(WIDTH_LABEL.setX(LABEL_X).setY(WIDTH_Y))
    }

    private fun addWidthSlider(){
        WIDTH_SLIDER.setRange(MINIMAL_WIDTH, MAXIMAL_WIDTH).setPrecision(1)
        add(WIDTH_SLIDER.alignLeftToRight(WIDTH_LABEL, SLIDER_LABEL_GAP).setY(WIDTH_Y))
    }

    private fun addWidthIndicator(){
        WIDTH_SLIDER.addValueListener { WIDTH_INDICATOR.setText(WIDTH_SLIDER.value().toInt()) }
        add(WIDTH_INDICATOR.alignLeftToLeft(WIDTH_LABEL).alignTopToBottom(WIDTH_LABEL, LABEL_INDICATOR_GAP))
    }

    private fun addHeightParameters(){
        addHeightLabel()
        addHeightSlider()
        addHeightIndicator()
    }

    private fun addHeightLabel(){
        add(HEIGHT_LABEL.setX(LABEL_X).setY(HEIGHT_Y))
    }

    private fun addHeightSlider(){
        HEIGHT_SLIDER.setRange(MINIMAL_HEIGHT, MAXIMAL_HEIGHT).setPrecision(1)
        add(HEIGHT_SLIDER.alignLeftToRight(HEIGHT_LABEL, SLIDER_LABEL_GAP).setY(HEIGHT_Y))
    }

    private fun addHeightIndicator(){
        HEIGHT_SLIDER.addValueListener { HEIGHT_INDICATOR.setText(HEIGHT_SLIDER.value().toInt()) }
        add(HEIGHT_INDICATOR.alignLeftToLeft(HEIGHT_LABEL).alignTopToBottom(HEIGHT_LABEL, LABEL_INDICATOR_GAP))
    }

    private fun addColour(){
        addColorSquares()
        addColourSelector()
    }

    private fun addColorSquares(){
        addAlive()
        addDead()
        addBorder()
    }

    private fun addAlive(){
        addAliveLabel()
        addAliveSquare()
    }

    private fun addAliveLabel(){
        add(ALIVE_COLOUR_LABEL.setX(0.1).setY(COLOUR_LABEL_Y))
    }

    private fun addAliveSquare(){
        ALIVE_COLOUR_SQUARE.setWidth(SQUARE_SIDE_LENGTH).setHeight(SQUARE_SIDE_LENGTH)
        ALIVE_COLOUR_SQUARE.setColour(DEFAULT_ALIVE_CELL_COLOUR)
        add(ALIVE_COLOUR_SQUARE.setX(0.1).alignTopToBottom(ALIVE_COLOUR_LABEL, LABEL_SQUARE_GAP))
    }

    private fun addDead(){
        addDeadLabel()
        addDeadSquare()
    }

    private fun addDeadLabel(){
        add(DEAD_COLOUR_LABEL.setX(0.3).setY(COLOUR_LABEL_Y))
    }

    private fun addDeadSquare(){
        DEAD_COLOUR_SQUARE.setWidth(SQUARE_SIDE_LENGTH).setHeight(SQUARE_SIDE_LENGTH)
        DEAD_COLOUR_SQUARE.setColour(DEFAULT_DEAD_CELL_COLOUR)
        add(DEAD_COLOUR_SQUARE.setX(0.3).alignTopToBottom(DEAD_COLOUR_LABEL, LABEL_SQUARE_GAP))
    }

    private fun addBorder(){
        addBorderLabel()
        addBorderSquare()
    }

    private fun addBorderLabel(){
        add(BORDER_COLOUR_LABEL.setX(0.5).setY(COLOUR_LABEL_Y))
    }

    private fun addBorderSquare(){
        BORDER_COLOUR_SQUARE.setWidth(SQUARE_SIDE_LENGTH).setHeight(SQUARE_SIDE_LENGTH)
        BORDER_COLOUR_SQUARE.setColour(DEFAULT_BORDER_COLOUR)
        add(BORDER_COLOUR_SQUARE.setX(0.5).alignTopToBottom(BORDER_COLOUR_LABEL, LABEL_SQUARE_GAP))
    }

    private fun addColourSelector(){
        add(ColourSelector.setWidth(COLOUR_SELECTOR_WIDTH).setHeight(COLOUR_SELECTOR_HEIGHT).alignTopTo(0).alignRightTo(1.0))
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

    private fun selectedWidth() : Int = WIDTH_SLIDER.value().toInt()

    private fun selectedHeight() : Int = HEIGHT_SLIDER.value().toInt()

    private fun aliveColour() : Color = ALIVE_COLOUR_SQUARE.colour()

    private fun deadColour() : Color = DEAD_COLOUR_SQUARE.colour()

    private fun borderColour() : Color = BORDER_COLOUR_SQUARE.colour()

    private fun next(){
        updateParameters()
        frame.setScene(NeighbourhoodSelectionScene)
    }

    private fun updateParameters(){
        Parameters.setWidth(selectedWidth())
        Parameters.setHeight(selectedHeight())
        Parameters.setAliveCellColour(aliveColour())
        Parameters.setDeadCellColour(deadColour())
        Parameters.setBorderColour(borderColour())
    }

}