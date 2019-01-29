package display.screens.staticscreens

import display.Displayed
import display.screens.Screen
import main.FRAMEX
import main.FRAMEY
import java.awt.Color.WHITE
import java.awt.Graphics
import javax.swing.JPanel

/**
 * The general class for a Screen that has no moving part
 */
public abstract class StaticScreen : Screen() {

    /**
     * The list of the components of this Screen
     */
    private var parts : ArrayList<Displayed> = ArrayList<Displayed>()

    /**
     * Adds a Displayed component to the Screen
     */
    public infix fun add(d : Displayed){
        parts.add(d)
        (this as JPanel).add(d)
    }

    /**
     * Removes a Displayed component from the Screen
     */
    public infix fun remove(d : Displayed){
        parts.remove(d)
        (this as JPanel).remove(d)
    }

    public override fun paintComponent(g: Graphics?) {
        g!!.color = WHITE
        g.fillRect(0, 0, FRAMEX, FRAMEY)
        for(part : Displayed in parts){
            part.paintComponent(g)
        }
        super.paintComponent(g)
    }

}