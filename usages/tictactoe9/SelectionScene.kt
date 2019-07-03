package usages.tictactoe9

import llayout6.DEFAULT_LARGE_FONT
import llayout6.displayers.Label
import llayout6.displayers.Switch
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay

object SelectionScene : LScene() {

    private val TITLE : Label = Label(StringDisplay("Tic Tac Toe", DEFAULT_LARGE_FONT))

    private val START_BUTTON : TextButton = TextButton("Start") { loadGame() }

    private val PLAYER_SWITCH : Switch = Switch()

    private val COMPUTER_SWITCH : Switch = Switch()

    private val PLAYER_LABEL : Label = Label("  Player")

    private val COMPUTER_LABEL : Label = Label("  Computer")

    init{
        addTitle()
        addStartButton()
        addPlayerSwitch()
        addComputerSwitch()
        addPlayerLabel()
        addComputerLabel()
    }

    private fun addTitle() = add(TITLE.setX(0.5).setY(0.2))

    private fun addStartButton() = add(START_BUTTON.setX(0.5).setY(0.8))

    private fun addPlayerSwitch(){
        PLAYER_SWITCH.setX(0.5).setY(0.4)
        PLAYER_SWITCH.addValueListener {
            if(PLAYER_SWITCH.value() && COMPUTER_SWITCH.value()){
                COMPUTER_SWITCH.setFalse()
            }
        }
        PLAYER_SWITCH.setTrue()
        add(PLAYER_SWITCH)
    }

    private fun addComputerSwitch(){
        COMPUTER_SWITCH.setX(0.5).setY(0.6)
        COMPUTER_SWITCH.addValueListener {
            if(COMPUTER_SWITCH.value() && PLAYER_SWITCH.value()){
                PLAYER_SWITCH.setFalse()
            }
        }
        COMPUTER_SWITCH.setFalse()
        add(COMPUTER_SWITCH)
    }

    private fun addPlayerLabel(){
        add(PLAYER_LABEL.alignLeftToRight(PLAYER_SWITCH).setY(0.4))
    }

    private fun addComputerLabel(){
        add(COMPUTER_LABEL.alignLeftToRight(COMPUTER_SWITCH).setY(0.6))
    }

    private fun isPlayer() : Boolean = PLAYER_SWITCH.value()

    private fun loadGame(){
        GameScene.loadGame(isPlayer())
        frame.setScene(GameScene)
    }

}