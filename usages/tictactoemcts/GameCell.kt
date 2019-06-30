package usages.tictactoemcts

import llayout5.GraphicAction
import llayout5.displayers.CanvasDisplayer
import java.awt.Color
import java.awt.Graphics

internal class GameCell(private val i : Int, private val j : Int) : CanvasDisplayer(){

    private companion object{
        private const val KEY : Int = 0
        private val DRAWING_COLOR : Color = Color.BLACK
        private val CROSS : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = DRAWING_COLOR
            g.drawLine(0, 0, w, h)
            g.drawLine(0, h, w, 0)
        }
        private val OVAL : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = DRAWING_COLOR
            g.drawOval(0, 0, w, h)
        }
    }

    private var type : Type = Type.EMPTY

    init{
        setOnMouseReleasedAction { if(notClicked()) GameScene.handleClick(i, j) }
        twoPixelFrame(DRAWING_COLOR)
    }

    internal fun setType(type : Type){
        this.type = type
        when(type){
            Type.O -> addGraphicAction(OVAL, KEY)
            Type.X -> addGraphicAction(CROSS, KEY)
            Type.EMPTY -> addGraphicAction({_, _ ,_ ->}, KEY)
        }
    }

    private fun notClicked() : Boolean = type == Type.EMPTY

}