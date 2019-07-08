package usages.cellularautomaton

import llayout6.DEFAULT_LARGE_FONT
import llayout6.DEFAULT_MEDIUM_FONT
import llayout6.displayers.Label
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay

internal object MainMenuScene : LScene() {

    private val TITLE : Label = Label(StringDisplay("Cellular Automaton", DEFAULT_LARGE_FONT))

    private val START_BUTTON : TextButton = TextButton(StringDisplay("Start", DEFAULT_MEDIUM_FONT)) { start() }

    init{
        addTitle()
        addStartButton()
    }

    private fun addTitle(){
        add(TITLE.setX(0.5).setY(0.4))
    }

    private fun addStartButton(){
        add(START_BUTTON.setX(0.5).setY(0.7))
    }

    private fun start(){
        frame.setScene(FirstSelectionScene)
    }

}