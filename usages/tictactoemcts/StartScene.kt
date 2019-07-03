package usages.tictactoemcts

import llayout6.DEFAULT_LARGE_FONT
import llayout6.displayers.Label
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay

internal object StartScene : LScene() {

    private val START_BUTTON : TextButton = TextButton("Start") { start() }

    private val TITLE : Label = Label(StringDisplay("Tic-Tac-Toe", DEFAULT_LARGE_FONT))

    init{
        add(TITLE.setX(0.5).setY(0.3))
        add(START_BUTTON.setX(0.5).setY(0.6))
    }

    private fun start() = GameScene.startGame()

}