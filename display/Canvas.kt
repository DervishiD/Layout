package display

import geometry.Point
import main.GraphicAction
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Graphics

interface Canvas : HavingDimension {

    var graphics : MutableMap<Any?, GraphicAction>

    fun draw(g : Graphics) : Canvas{
        for(graphicAction : GraphicAction in graphics.values){
            graphicAction.invoke(g, width(), height())
        }
        return this
    }

    infix fun removeDrawing(key : Any?) : Canvas{
        graphics.remove(key)
        return this
    }

    fun drawPoint(x : Int, y : Int, key : Any?, color : Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawRect(x, y, 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Int, y : Int, color : Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawRect(x, y, 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint(x : Double, y : Int, key : Any?, color : Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Double, y : Int, color : Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint(x : Int, y : Double, key : Any?, color : Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Int, y : Double, color : Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint(x : Double, y : Double, key : Any?, color : Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Double, y : Double, color : Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint(p : Point, key : Any?, color: Color = BLACK) : Canvas = drawPoint(p.intx(), p.inty(), key, color)

    fun drawPoint(p : Point, color : Color = BLACK) : Canvas = drawPoint(p.intx(), p.inty(), color)

    fun fillBackground(color : Color, key : Any?) : Canvas{
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(0, 0, w, h)
        }}
        return this
    }

    infix fun fillBackground(color : Color) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(0, 0, w, h)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun addGraphicAction(graphicAction : GraphicAction, key : Any?) : Canvas{
        graphics[key] = graphicAction
        return this
    }

    infix fun addGraphicAction(graphicAction: GraphicAction) : Canvas{
        return addGraphicAction(graphicAction, graphicAction)
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Int, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas{
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Double, color: Color = BLACK) : Canvas{
        val addedGraphicAction : GraphicAction = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromPoint : Point, toX : Int, toY : Int, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, key, color)

    fun drawLine(fromPoint : Point, toX : Double, toY : Int, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, key, color)

    fun drawLine(fromPoint : Point, toX : Int, toY : Double, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, key, color)

    fun drawLine(fromPoint : Point, toX : Double, toY : Double, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, key, color)

    fun drawLine(fromPoint : Point, toX : Int, toY : Int, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, color)

    fun drawLine(fromPoint : Point, toX : Double, toY : Int, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, color)

    fun drawLine(fromPoint : Point, toX : Int, toY : Double, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, color)

    fun drawLine(fromPoint : Point, toX : Double, toY : Double, color : Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toX, toY, color)

    fun drawLine(fromX : Int, fromY : Int, toPoint : Point, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), key, color)

    fun drawLine(fromX : Double, fromY : Int, toPoint : Point, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), key, color)

    fun drawLine(fromX : Int, fromY : Double, toPoint : Point, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), key, color)

    fun drawLine(fromX : Double, fromY : Double, toPoint : Point, key : Any?, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), key, color)

    fun drawLine(fromX : Int, fromY : Int, toPoint : Point, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), color)

    fun drawLine(fromX : Double, fromY : Int, toPoint : Point, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), color)

    fun drawLine(fromX : Int, fromY : Double, toPoint : Point, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), color)

    fun drawLine(fromX : Double, fromY : Double, toPoint : Point, color : Color = BLACK) : Canvas =
            drawLine(fromX, fromY, toPoint.intx(), toPoint.inty(), color)

    fun drawLine(fromPoint : Point, toPoint : Point, key : Any?, color: Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toPoint.intx(), toPoint.inty(), key, color)

    fun drawLine(fromPoint : Point, toPoint : Point, color: Color = BLACK) : Canvas =
            drawLine(fromPoint.intx(), fromPoint.inty(), toPoint.intx(), toPoint.inty(), color)

}