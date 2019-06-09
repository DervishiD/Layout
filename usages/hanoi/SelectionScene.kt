package usages.hanoi

import llayout3.DEFAULT_LARGE_FONT
import llayout3.displayers.HorizontalDoubleSlider
import llayout3.displayers.Label
import llayout3.displayers.Switch
import llayout3.displayers.TextButton
import llayout3.frame.LScene
import llayout3.utilities.StringDisplay

internal object SelectionScene : LScene() {

    private const val PLAYER_STRING : String = "Player"

    private const val COMPUTER_STRING : String = "Computer"

    private val title : Label = Label(StringDisplay("Tower of Hanoi", DEFAULT_LARGE_FONT))

    private val playerSwitch : Switch = Switch()

    private val playerIndicator : Label = Label(PLAYER_STRING)

    private val heightSlider : HorizontalDoubleSlider = HorizontalDoubleSlider(0.3, 40)

    private val heightLabel : Label = Label("Height")

    private val heightIndicator : Label = Label()

    private val startButton : TextButton = TextButton("Start"){start()}

    init{
        addTitle()
        addSwitch()
        addPlayerIndicator()
        addHeightSlider()
        addHeightLabel()
        addHeightIndicator()
        addStartButton()
    }

    private fun addTitle(){
        title.setX(0.5).setY(0.2)
        add(title)
    }

    private fun addSwitch(){
        playerSwitch.setX(0.5).setY(0.4)
        add(playerSwitch)
    }

    private fun addPlayerIndicator(){
        playerIndicator.setX(0.5).alignDownToUp(playerSwitch)
        playerSwitch.addValueListener {
            playerIndicator.setText(if(playerSwitch.value()) COMPUTER_STRING else PLAYER_STRING)
        }
        add(playerIndicator)
    }

    private fun addHeightSlider(){
        heightSlider.setMinimum(1).setMaximum(10).setPrecision(1).setX(0.5).setY(0.6)
        add(heightSlider)
    }

    private fun addHeightLabel(){
        heightLabel.setX(0.5).alignDownToUp(heightSlider)
        add(heightLabel)
    }

    private fun addHeightIndicator(){
        heightIndicator.setText(heightSlider.value().toInt()).setX(0.5).alignUpToDown(heightSlider)
        heightSlider.addValueListener { heightIndicator.setText(heightSlider.value().toInt()) }
        add(heightIndicator)
    }

    private fun addStartButton(){
        startButton.setX(0.5).setY(0.8)
        add(startButton)
    }

    private fun start(){
        frame.setScene(GameScene)
        GameScene.reset(enteredHeight(), enteredIsHuman())
    }

    private fun enteredHeight() : Int = heightSlider.value().toInt()

    private fun enteredIsHuman() : Boolean = !playerSwitch.value()

}