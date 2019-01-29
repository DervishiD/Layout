package main

import display.Button
import java.awt.Color
import display.StringDisplay
import display.texts.MenuText
import java.awt.Font
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
    val a : MenuText = MenuText(400, 400,
        arrayListOf(
            StringDisplay("Hello "),
            StringDisplay("There", Font("Arial", Font.BOLD, 32), Color.RED),
            StringDisplay("!\n", Color.BLUE),
            StringDisplay("Oh hi Mark!", Font("Monospaced", Font.ITALIC, 40), Color.ORANGE)
        ))
    a alignRightTo FRAMEX
    a alignDownTo FRAMEY
    mainFrame.contentPane.add(a)

    //EXAMPLE
    val b : Button = Button(400, 400,
        arrayListOf(
            StringDisplay("Hello "),
            StringDisplay("There", Font("Arial", Font.BOLD, 32), Color.RED),
            StringDisplay("!\n", Color.BLUE),
            StringDisplay("Oh hi Mark!", Font("Monospaced", Font.ITALIC, 40), Color.ORANGE)
        )
    ) {}
    b alignLeftTo 200
    mainFrame.contentPane.add(b)

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




