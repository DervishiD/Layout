package main

import java.awt.Frame.MAXIMIZED_BOTH
import javax.swing.JFrame.EXIT_ON_CLOSE

typealias Action = () -> Unit



public fun main(args : Array<String>){
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

    //EXAMPLE
    /*
    mainFrame.contentPane.addMouseListener(GameMouseListener())
    val p : Text = Text()
    p.setBounds(220, 220, 500, 500)
    mainFrame.contentPane.add(p)
    */

}

private fun launchProgram(){
    startTimer()
}




