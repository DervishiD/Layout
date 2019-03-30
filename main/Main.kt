package main

import display.ScreenManager
import java.awt.Frame.MAXIMIZED_BOTH
import javax.swing.JFrame.EXIT_ON_CLOSE

fun main(){
    init()
}

private fun init(){
    initMainFrame()
    launchProgram()
}

private fun initMainFrame(){
    mainFrame.defaultCloseOperation = EXIT_ON_CLOSE
    /*
    * TRUE ON LINUX, FALSE ON WINDOWS. WHATEVER.
    * */
    mainFrame.isResizable = true
    mainFrame.layout = null
    mainFrame.extendedState = MAXIMIZED_BOTH
    mainFrame.isUndecorated = true
    mainFrame.isVisible = true
    mainFrame.requestFocus()
    mainFrame.addMouseListener(Mouse())
    mainFrame.addKeyListener(KEYBOARD)
    mainFrame.addMouseWheelListener(mouseWheel)
    mainFrame.addMouseMotionListener(mouseMotionListener)

}

private fun launchProgram(){
    ScreenManager.start()
    startTimer()
}