package usages.tictactoe

import llayout3.GraphicAction
import llayout3.DEFAULT_LARGE_FONT
import llayout3.displayers.*
import llayout3.frame.*
import llayout3.utilities.LObservable
import llayout3.utilities.StringDisplay
import java.awt.Color.BLACK
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ESCAPE

val TicTacToeApplication : LApplication = LApplication { frame.run() }

private class Cell(x: Double, y: Double, w: Double, h: Double, private val i : Int, private val j : Int) : CanvasDisplayer(w, h) {

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

    private var type : LObservable<Type> = LObservable(Companion.Type.BLANK)

    init{
        setX(x)
        setY(y)
        addGraphicAction(BOX)
        type.addListener{
            when(type.value){
                Companion.Type.X -> addGraphicAction(XDrawing, KEY)
                Companion.Type.O -> addGraphicAction(ODrawing, KEY)
                Companion.Type.BLANK -> removeDrawing(KEY)
            }
        }
        setOnMouseReleasedAction {
            if(unChecked() && running){
                if(isFirst.value) writeX() else writeO()
                testEnd()
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

private var isFirst : LObservable<Boolean> = LObservable(true)

private fun nextPlayer(){
    isFirst.value = !isFirst.value
}

object scene : LScene(){

    private val FIRST : String = "x"
    private val SECOND : String = "o"

    private val title : Label = Label(StringDisplay("Tic-Tac-Toe", DEFAULT_LARGE_FONT))
            .setX(0.25).setY(0.33) as Label

    private val exitButton = TextButton("X") {frame.close()}.alignLeftTo(0).alignTopTo(0)

    private val player : Label = Label(FIRST).setX(0.25).setY(0.5) as Label

    private val resetButton = TextButton("Reset") {reset()}.setX(0.25).setY(0.8)

    init{
        addPlayerListener()
        add(title)
        add(exitButton)
        add(resetButton)
        add(player)
        for(a : Array<Cell> in cells) for(c : Cell in a) add(c)
        setOnKeyReleasedAction { e -> if(e.keyCode == VK_ESCAPE) frame.close() }
    }

    private fun addPlayerListener(){
        isFirst.addListener{player.setText(if(isFirst.value) FIRST else SECOND)}
    }

    private fun reset(){
        for(a : Array<Cell> in cells) for(c : Cell in a) c.unCheck()
        isFirst.value = true
        running = true
    }

}

private val frame : LFrame = LFrame(scene).setFullscreen()
