package usages.tictactoemcts

import llayout5.DEFAULT_LARGE_FONT
import llayout5.DEFAULT_MEDIUM_FONT
import llayout5.displayers.Label
import llayout5.displayers.TextButton
import llayout5.frame.LScene
import llayout5.utilities.StringDisplay

internal object EndScene : LScene(){

    private val MENU : TextButton = TextButton("Back to main menu") { frame.setScene(StartScene) }

    private val LABEL : Label = Label()

    private val RESTART : TextButton = TextButton(StringDisplay("Restart", DEFAULT_MEDIUM_FONT)) { GameScene.startGame() }

    init{
        add(MENU.alignTopTo(0).alignLeftTo(0))
        add(LABEL.setX(0.5).setY(0.4))
        add(RESTART.setX(0.5).setY(0.7))
    }

    internal fun end(winner : Type){
        frame.setScene(this)
        LABEL.setText(StringDisplay(when(winner){
            Type.O -> "Player O has won"
            Type.X -> "Player X has won"
            Type.EMPTY -> "Draw"
        }, DEFAULT_LARGE_FONT))
    }

}