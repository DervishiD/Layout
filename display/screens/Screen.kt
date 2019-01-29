package display.screens

import main.FRAMEX
import main.FRAMEY
import javax.swing.JPanel

public abstract class Screen : JPanel() {

    init{
        setBounds(0, 0, FRAMEX, FRAMEY)
    }

}