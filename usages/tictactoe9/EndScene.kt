package usages.tictactoe9

import llayout6.DEFAULT_LARGE_FONT
import llayout6.displayers.Label
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay
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