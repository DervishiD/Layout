package display.screens

import display.EDITOR_BUTTON
import display.TITLE
import javax.swing.JPanel

/**
 * The Main Menu Screen
 */
class MainMenuScreen : Screen() {
    //TODO

    init{
        previousScreen = this

        this add EDITOR_BUTTON
        this add TITLE
    }

}