package display.screens

import display.TextDisplayer
import main.FRAMEX
import main.FRAMEY
import java.awt.Color.WHITE
import java.awt.Graphics
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
     * The list of the components of this Screen
     */
    private var parts : ArrayList<TextDisplayer> = ArrayList<TextDisplayer>()

    /**
     * Adds a TextDisplayer component to the Screen
     */
    public infix fun add(d : TextDisplayer){
        parts.add(d)
        (this as JPanel).add(d)
    }

    /**
     * Removes a TextDisplayer component from the Screen
     */
    public infix fun remove(d : TextDisplayer){
        parts.remove(d)
        (this as JPanel).remove(d)
    }

    public override fun paintComponent(g: Graphics?) {
        g!!.color = WHITE
        g.fillRect(0, 0, FRAMEX, FRAMEY)
        for(part : TextDisplayer in parts){
            part.paintComponent(g)
        }
        super.paintComponent(g)
    }

    /**
     * To save the current state of the Screen
     */
    public abstract fun save()

    /**
     * To load this screen
     */
    public abstract fun load()

}