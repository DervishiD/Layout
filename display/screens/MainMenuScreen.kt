package display.screens

import display.EDITOR_BUTTON
import display.TITLE

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

    public override fun save() {
        //TODO
    }

    public override fun load(){
        //TODO
    }

}