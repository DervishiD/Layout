package display.screens.staticscreens

import display.Button
import display.StringDisplay
import display.TITLE
import display.texts.MenuText
import java.awt.Font

/**
 * The Main Menu Screen
 */
class MainMenuScreen : StaticScreen() {
    //TODO

    init{
        previousScreen = this

        val test : Button = Button(700, 300, "Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii") {println("Hi")}

        val a : MenuText = MenuText(600, 500, arrayListOf<StringDisplay>(
            StringDisplay("Hello there!\nGeneral Kenobi!\n"),
            StringDisplay("text string font font color color string display huh", Font("Arial", Font.PLAIN, 32))
        ))

        a setMaxLineLength 400
        test setMaxLineLength 100
        a alignUpTo 0
        a alignLeftTo 0

        this add a
        this add test
        this add TITLE
    }

    public override fun save() {
        //TODO
    }

}