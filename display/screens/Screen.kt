package display.screens

import display.CustomContainer
import display.Displayer
import display.ScreenManager
import main.FRAMEX
import main.FRAMEY
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ESCAPE
import javax.swing.JPanel

/**
 * The general abstraction for a Screen
 */
abstract class Screen : JPanel(), CustomContainer {

    /**
     * The previous Screen in the Screen tree
     */
    protected abstract var previousScreen : Screen

    init{
        setBounds(0, 0, FRAMEX, FRAMEY)
    }

    /**
     * Returns the previous Screen
     */
    fun previousScreen() : Screen = previousScreen

    /**
     * The list of the components of this Screen
     */
    override var parts : ArrayList<Displayer> = ArrayList()

    public override fun paintComponent(g: Graphics?) {
        for(part : Displayer in parts){
            part.paintComponent(g)
        }
        g!!.clearRect(0, 0, width, height)
        drawBackground(g)
    }

    /**
     * Draws the background of this Screen
     */
    open fun drawBackground(g : Graphics){}

    /**
     * To save the current state of the Screen
     */
    open fun save(){}

    /**
     * To load this screen
     */
    open fun load(){}

    override fun releaseKey(key: Int) {
        if(key == VK_ESCAPE) escape()
    }

    /**
     * Back to the previous screen
     */
    open fun escape() = ScreenManager.toPreviousScreen()

}