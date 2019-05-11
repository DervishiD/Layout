package usages.console

import llayout.displayers.ConsoleScrollPane
import llayout.displayers.TextField
import llayout.frame.*
import llayout.utilities.StringDisplay
import java.awt.Color.RED
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_ENTER

val consoleApplication : LApplication = LApplication{ frame.run() }

private val scene : LScene = object : LScene(){

    private val field : TextField = TextField(0, 0, 0.5).alignLeftTo(0).alignDownTo(1.0) as TextField

    private val console : ConsoleScrollPane = ConsoleScrollPane(0, 0, 0.5, 1.0)
            .setPrompt(StringDisplay(">> ", RED))
            .alignUpTo(0)
            .alignRightTo(1.0) as ConsoleScrollPane

    init{
        add(field)
        add(console)
    }

    override fun keyTyped(e: KeyEvent?) {
        field.type(e!!.keyChar)
    }

    override fun keyPressed(e: KeyEvent?) {
        when(e!!.keyCode){
            VK_ENTER -> {
                console.writeln(field.typedText())
                handleInput()
                field.clear()
            }
            else -> field.type(e.keyCode)
        }
    }

    private fun handleInput(){
        //TODO
    }

}

private val frame : LFrame = LFrame(scene)
