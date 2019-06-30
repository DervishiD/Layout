package usages.schinz

import llayout5.DEFAULT_LARGE_FONT
import llayout5.displayers.DoubleCursor
import llayout5.displayers.Label
import llayout5.displayers.Switch
import llayout5.displayers.TextButton
import llayout5.frame.LScene
import llayout5.utilities.StringDisplay

object MenuScene : LScene() {

    private const val MINIMAL_DIMENSION : Int = 2

    private const val MAXIMAL_DIMENSION : Int = 6

    private val TITLE : Label = Label(StringDisplay("Schinz", DEFAULT_LARGE_FONT))

    private val START_BUTTON : TextButton = TextButton("Start"){ start() }

    private val DIMENSIONS_CURSOR : DoubleCursor = DoubleCursor(0.25, 0.4)

    private val WIDTH_INDICATOR : Label = Label()

    private val HEIGHT_INDICATOR : Label = Label()

    private val PLAYER_SWITCH : Switch = Switch()

    private val PLAYER_LABEL : Label = Label("Player")

    init{
        addTitle()
        addDimensionCursor()
        addWidthIndicator()
        addHeightIndicator()
        addStartButton()
        addPlayerSwitch()
        addPlayerLabel()
    }

    private fun addTitle(){
        add(TITLE.setX(0.5).setY(0.1))
    }

    private fun addDimensionCursor(){
        DIMENSIONS_CURSOR.addXValueListener { updateWidthIndicator() }
                .addYValueListener { updateHeightIndicator() }
                .setMinimalXValue(MINIMAL_DIMENSION).setMaximalXValue(MAXIMAL_DIMENSION)
                .setMinimalYValue(MINIMAL_DIMENSION).setMaximalYValue(MAXIMAL_DIMENSION)
                .setXPrecision(1).setYPrecision(1).setX(0.5).setY(0.4)
        add(DIMENSIONS_CURSOR)
    }

    private fun addWidthIndicator(){
        WIDTH_INDICATOR.alignLeftToLeft(DIMENSIONS_CURSOR).alignTopToBottom(DIMENSIONS_CURSOR)
        add(WIDTH_INDICATOR)
    }

    private fun addHeightIndicator(){
        add(HEIGHT_INDICATOR.alignLeftToLeft(WIDTH_INDICATOR).alignTopToBottom(WIDTH_INDICATOR))
    }

    private fun addStartButton(){
        add(START_BUTTON.setX(0.5).setY(0.9))
    }

    private fun addPlayerSwitch(){
        add(PLAYER_SWITCH.addValueListener { updatePlayerLabel() }.setX(0.5).setY(0.8))
    }

    private fun addPlayerLabel(){
        add(PLAYER_LABEL.alignLeftToRight(PLAYER_SWITCH, 5).setY(0.8))
    }

    private fun updateWidthIndicator(){
        WIDTH_INDICATOR.setText("Width : ${DIMENSIONS_CURSOR.xValue().toInt()}")
    }

    private fun updateHeightIndicator(){
        HEIGHT_INDICATOR.setText("Height : ${DIMENSIONS_CURSOR.yValue().toInt()}")
    }

    private fun updatePlayerLabel(){
        PLAYER_LABEL.setText(if(PLAYER_SWITCH.value()) "Computer" else "Player")
    }

    private fun selectedWidth() : Int = DIMENSIONS_CURSOR.xValue().toInt()

    private fun selectedHeight() : Int = DIMENSIONS_CURSOR.yValue().toInt()

    private fun isHuman() : Boolean = !PLAYER_SWITCH.value()

    private fun start(){
        GameScene.reload(selectedWidth(), selectedHeight(), isHuman())
    }

}