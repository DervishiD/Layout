package usages.tictactoe

import llayout.GraphicAction
import llayout.TITLE_FONT
import llayout.displayers.CanvasDisplayer
import llayout.displayers.Displayer
import llayout.displayers.Label
import llayout.displayers.TextButton
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LFrameBuilder
import llayout.frame.LScene
import llayout.utilities.LProperty
import llayout.utilities.StringDisplay
import java.awt.Color.BLACK
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_ESCAPE

val TicTacToeApplication : LApplication = LApplication { frame.run() }

private class Cell(x: Double, y: Double, w: Double, h: Double) : CanvasDisplayer(x, y, w, h) {

    companion object{
        private val X : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = BLACK
            g.drawLine(0, 0, w, h)
            g.drawLine(0, h, w, 0)
        }}
        private val O : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = BLACK
            g.drawOval(0, 0, w, h)
        }}
        private val BOX : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = BLACK
            g.drawRect(0, 0, w - 1, h - 1)
        }}
        private const val KEY : Int = 12
    }

    init{
        addGraphicAction(BOX)
    }

    fun checked() : Boolean = graphics.containsKey(KEY)

    fun writeX(){
        if(!checked()) addGraphicAction(X, KEY)
    }

    fun writeO(){
        if(!checked()) addGraphicAction(O, KEY)
    }

    override fun mouseRelease(){
        if(!checked()){
            if(isFirst.value) writeX() else writeO()
            nextPlayer()
        }
    }

    fun unCkeck(){
        removeDrawing(KEY)
    }

}

private var isFirst : LProperty<Boolean> = LProperty(true)

private fun nextPlayer(){
    isFirst.value = !isFirst.value
}

private val scene : LScene = object : LScene(){

    private val FIRST : String = "x"
    private val SECOND : String = "o"

    private val title : Label = Label(0.25, 0.33, StringDisplay("Tic-Tac-Toe", TITLE_FONT))

    private val exitButton : Displayer = TextButton(0, 0, "X", {frame.close()}).alignLeftTo(0).alignUpTo(0)

    private val player : Label = Label(0.25, 0.5, FIRST)

    private val cells : MutableCollection<Cell> = mutableSetOf()

    private val resetButton : TextButton = TextButton(0.25, 0.8, "Reset", {reset()})

    override fun keyReleased(e: KeyEvent?){
        if(e!!.keyCode == VK_ESCAPE) frame.close()
    }

    init{
        addPlayerListener()
        add(title)
        add(exitButton)
        add(resetButton)
        add(player)
        for(i : Int in 0..2){
            for(j : Int in 0..2){
                add(Cell(
                        7.0/12 + i * 1.0/6,
                        1.0/6 + j * 1.0/3,
                        1.0/6,
                        1.0/3
                ))
            }
        }
    }

    private fun addPlayerListener(){
        isFirst.addListener{player.setDisplayedText(if(isFirst.value) FIRST else SECOND)}
    }

    private fun reset(){
        for(c : Cell in cells) c.unCkeck()
        isFirst.value = true
    }

}

private val frame : LFrame = LFrameBuilder(scene).setFullScreen(true).setDecorated(false).build()
