package detection

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

/**
 * An implementation of KeyAdapter.
 */
internal class Keyboard : KeyAdapter() {

    public override fun keyPressed(e: KeyEvent?) {
        EventHandler.press(e!!.keyCode)
    }

    public override fun keyReleased(e: KeyEvent?) {
        EventHandler.release(e!!.keyCode)
    }

}