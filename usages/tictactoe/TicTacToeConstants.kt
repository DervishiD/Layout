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

private class Cell(x: Double, y: Double, w: Double, h: Double, private val i : Int, private val j : Int) : CanvasDisplayer(x, y, w, h) {

    companion object{
        private val XDrawing : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = BLACK
            g.drawLine(0, 0, w, h)
            g.drawLine(0, h, w, 0)
        }}
        private val ODrawing : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = BLACK
            g.drawOval(0, 0, w, h)
        }}
        private val BOX : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = BLACK
            g.drawRect(0, 0, w - 1, h - 1)
        }}
        private const val KEY : Int = 12

        enum class Type{X, O, BLANK}

    }

    private var type : LProperty<Type> = LProperty(Companion.Type.BLANK)

    init{
        addGraphicAction(BOX)
        type.addListener{
            when(type.value){
                Companion.Type.X -> addGraphicAction(XDrawing, KEY)
                Companion.Type.O -> addGraphicAction(ODrawing, KEY)
                Companion.Type.BLANK -> removeDrawing(KEY)
            }
        }
    }

    private fun unChecked() : Boolean = type.value == Companion.Type.BLANK

    private fun writeX(){
        if(unChecked()) type.value = Companion.Type.X
    }

    private fun writeO(){
        if(unChecked()) type.value = Companion.Type.O
    }

    override fun mouseRelease(){
        if(unChecked() && running){
            if(isFirst.value) writeX() else writeO()
            testEnd()
        }
    }

    fun unCheck(){
        type.value = Companion.Type.BLANK
    }

    fun type() : Type = type.value

    private fun testEnd(){
        if(fullLine(i) || fullColumn(j) || diagonal(i, j)){
            running = false
        }else{
            nextPlayer()
        }
    }

}

private var running : Boolean = true

private fun fullLine(i : Int) : Boolean{
    return cells[i][0].type() == cells[i][1].type() && cells[i][1].type() == cells[i][2].type()
}

private fun fullColumn(j : Int) : Boolean{
    return cells[0][j].type() == cells[1][j].type() && cells[1][j].type() == cells[2][j].type()
}

private fun diagonal(i : Int, j : Int) : Boolean{
    return when {
        i == j -> cells[0][0].type() == cells[1][1].type() && cells[1][1].type() == cells[2][2].type()
        i + j == 2 -> cells[0][2].type() == cells[1][1].type() && cells[1][1].type() == cells[2][0].type()
        else -> false
    }
}

private const val STARTING_X : Double = 7.0/12
private const val STARTING_Y : Double = 1.0/6
private const val WIDTH : Double = 1.0/6
private const val HEIGHT : Double = 1.0/3
private const val DELTA_X : Double = WIDTH
private const val DELTA_Y : Double = HEIGHT

private val cells : Array<Array<Cell>> = Array(3) {
    i -> Array(3){
        j -> Cell(STARTING_X + i * DELTA_X, STARTING_Y + j * DELTA_Y, WIDTH, HEIGHT, i, j)
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
        for(a : Array<Cell> in cells) for(c : Cell in a) add(c)
    }

    private fun addPlayerListener(){
        isFirst.addListener{player.setDisplayedText(if(isFirst.value) FIRST else SECOND)}
    }

    private fun reset(){
        for(a : Array<Cell> in cells) for(c : Cell in a) c.unCheck()
        isFirst.value = true
        running = true
    }

}

private val frame : LFrame = LFrameBuilder(scene).setFullScreen(true).setDecorated(false).build()