package main

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class GameMouseListener : MouseAdapter() {
    //TODO

    //EXAMPLE
    public override fun mouseReleased(e: MouseEvent?) {
        println(mainFrame.contentPane.getComponentAt(e!!.x, e.y).javaClass.toGenericString())
    }

}