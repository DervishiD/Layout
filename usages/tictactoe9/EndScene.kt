package usages.tictactoe9

import llayout5.DEFAULT_LARGE_FONT
import llayout5.displayers.Label
import llayout5.displayers.TextButton
import llayout5.frame.LScene
import llayout5.utilities.StringDisplay
import java.awt.Color

internal object EndScene : LScene() {

    private val BACK_BUTTON : TextButton = TextButton("Back To Main Menu") { frame.setScene(SelectionScene) }

    private val LABEL : Label = Label()

    init{
        fillBackground(Color(250, 170, 150))
        add(BACK_BUTTON.alignLeftTo(0).alignTopTo(0))
        add(LABEL.setX(0.5).setY(0.5))
    }

    internal fun load(type : Type){
        when(type){
            Type.O -> LABEL.setText(StringDisplay("Player O Won", DEFAULT_LARGE_FONT))
            else -> LABEL.setText(StringDisplay("Player X Won", DEFAULT_LARGE_FONT))
        }
        frame.setScene(this)
    }

}