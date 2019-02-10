package detection

import display.ScreenManager
import main.pressedKeys
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

/**
 * An implementation of KeyAdapter.
 */
internal class Keyboard : KeyAdapter() {

    override fun keyPressed(e: KeyEvent?) {
        pressedKeys.add(e!!.keyCode)
        ScreenManager.currentScreen().pressKey(e.keyCode)
    }

    override fun keyReleased(e: KeyEvent?) {
        pressedKeys.remove(e!!.keyCode)
        ScreenManager.currentScreen().releaseKey(e.keyCode)
    }

}