package main

import detection.Mouse
import display.ScreenManager
import java.awt.Frame.MAXIMIZED_BOTH
import javax.swing.JFrame.EXIT_ON_CLOSE

typealias Action = () -> Unit



public fun main(){
    init()
}

private fun init(){
    initMainFrame()
    launchProgram()
}

private fun initMainFrame(){
    mainFrame.defaultCloseOperation = EXIT_ON_CLOSE
    mainFrame.isResizable = false
    mainFrame.layout = null
    mainFrame.extendedState = MAXIMIZED_BOTH
    mainFrame.isUndecorated = true
    mainFrame.isVisible = true
    mainFrame.requestFocus()
    mainFrame.addMouseListener(Mouse())

    //EXAMPLE
    /*
    mainFrame.contentPane.addMouseListener(Mouse())
    val p : Text = Text()
    p.setBounds(220, 220, 500, 500)
    mainFrame.contentPane.add(p)
    */

}

private fun launchProgram(){
    ScreenManager.start()
    startTimer()
}




