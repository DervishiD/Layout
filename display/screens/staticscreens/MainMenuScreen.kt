package display.screens.staticscreens

import display.Button
import display.TITLE

/**
 * The Main Menu Screen
 */
class MainMenuScreen : StaticScreen() {
    //TODO

    init{
        previousScreen = this

        val test : Button = Button(300, 300, "Hi") {println("Hi")}

        this add test
        this add TITLE
    }

    public override fun save() {
        //TODO
    }

}