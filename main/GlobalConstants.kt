package main

import display.ScreenManager
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_SHIFT
import javax.swing.JFrame

typealias Action = () -> Unit
typealias GraphicAction = (Graphics, Int, Int) -> Unit

val FRAMEX : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()
val FRAMEY : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

val mainFrame : JFrame = JFrame()

const val DOUBLE_PRECISION : Double = 10e-2

val pressedKeys : HashSet<Int> = HashSet<Int>()

fun shiftPressed() : Boolean = pressedKeys.contains(VK_SHIFT)

internal val KEYBOARD : KeyAdapter = object : KeyAdapter() {

    override fun keyPressed(e: KeyEvent?) {
        pressedKeys.add(e!!.keyCode)
        ScreenManager.currentScreen().pressKey(e.keyCode)
    }

    override fun keyReleased(e: KeyEvent?) {
        pressedKeys.remove(e!!.keyCode)
        ScreenManager.currentScreen().releaseKey(e.keyCode)
    }

}

