package display.screens

import display.Button
import display.StringDisplay
import display.TITLE
import display.screens.Screen
import display.texts.MenuText
import geometry.Point
import geometry.Vector
import main.FRAMEY
import java.awt.Font

/**
 * The Main Menu Screen
 */
class MainMenuScreen : Screen() {
    //TODO

    var b : Button = Button(0, 0, "", {})

    init{
        previousScreen = this

        val test : Button = Button(700, 300, "Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii", {println("Hi")})

        val a : MenuText = MenuText(600, 500, arrayListOf<StringDisplay>(
            StringDisplay("Hello there!\nGeneral Kenobi!\n"),
            StringDisplay("text string font font color color string display huh", Font("Arial", Font.PLAIN, 32))
        ))

        b = Button(1400, 100, "b", {b moveAlong Vector(-12, 12) })

        test setMaxLineLength 100
        a setMaxLineLength 400
        a alignUpTo 0
        a alignLeftTo 0

        this add b
        this add a
        this add test
        this add TITLE
    }

    public override fun save() {
        //TODO
    }

    public override fun load(){
        //TODO
    }

}