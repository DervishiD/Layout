package display.screens

import main.FRAMEX
import main.FRAMEY
import javax.swing.JPanel

/**
 * The general abstraction for a Screen
 */
public abstract class Screen : JPanel() {

    /**
     * The previous Screen in the Screen tree
     */
    protected lateinit var previousScreen : Screen

    init{
        setBounds(0, 0, FRAMEX, FRAMEY)
    }

    /**
     * Returns the previous Screen
     */
    public fun previousScreen() : Screen = previousScreen

    /**
     * To save the current state of the Screen
     */
    public abstract fun save()

    /**
     * To load this screen
     */
    public abstract fun load()

}