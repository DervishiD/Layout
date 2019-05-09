package llayout.interfaces

import llayout.GraphicAction
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Graphics

interface Canvas : HavingDimension {

    var graphics : MutableMap<Any?, GraphicAction>

    fun drawBackground(g : Graphics) : Canvas {
        for(graphicAction : GraphicAction in graphics.values){
            graphicAction.invoke(g, width(), height())
        }
        return this
    }

    infix fun removeDrawing(key : Any?) : Canvas {
        graphics.remove(key)
        return this
    }

    fun clearBackground() : Canvas{
        graphics.clear()
        return this
    }

    fun drawPoint(x : Int, y : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawRect(x, y, 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Int, y : Int, color : Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.drawRect(x, y, 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint(x : Double, y : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Double, y : Int, color : Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint(x : Int, y : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Int, y : Double, color : Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint(x : Double, y : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), 1, 1)
        }}
        return this
    }

    fun drawPoint(x : Double, y : Double, color : Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), 1, 1)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun fillBackground(color : Color, key : Any?) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(0, 0, w, h)
        }}
        return this
    }

    infix fun fillBackground(color : Color) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(0, 0, w, h)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun addGraphicAction(graphicAction : GraphicAction, key : Any?) : Canvas {
        graphics[key] = graphicAction
        return this
    }

    infix fun addGraphicAction(graphicAction: GraphicAction) : Canvas {
        return addGraphicAction(graphicAction, graphicAction)
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, _ -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, _, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Int, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Int, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), toY)
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Double, key : Any?, color: Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        return this
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Double, color: Color = BLACK) : Canvas {
        val addedGraphicAction : GraphicAction = { g : Graphics, w, h -> run{
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }}
        graphics[addedGraphicAction] = addedGraphicAction
        return this
    }

    fun drawPoint2(x : Int, y : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.fillRect(x, y, 2, 2)
        }}
        return this
    }

    fun drawPoint2(x : Int, y : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), 2, 2)
        }}
        return this
    }

    fun drawPoint2(x : Double, y : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, 2, 2)
        }}
        return this
    }

    fun drawPoint2(x : Double, y : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), 2, 2)
        }}
        return this
    }

    fun drawPoint2(x : Int, y : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.fillRect(x, y, 2, 2)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawPoint2(x : Int, y : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), 2, 2)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawPoint2(x : Double, y : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, 2, 2)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawPoint2(x : Double, y : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), 2, 2)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawPoint3(x : Int, y : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.fillRect(x - 1, y - 1, 3, 3)
        }}
        return this
    }

    fun drawPoint3(x : Int, y : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x - 1, (y * h).toInt() - 1, 3, 3)
        }}
        return this
    }

    fun drawPoint3(x : Double, y : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt() - 1, y - 1, 3, 3)
        }}
        return this
    }

    fun drawPoint3(x : Double, y : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt() - 1, (y * h).toInt() - 1, 3, 3)
        }}
        return this
    }

    fun drawPoint3(x : Int, y : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.fillRect(x - 1, y - 1, 3, 3)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawPoint3(x : Int, y : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x - 1, (y * h).toInt() - 1, 3, 3)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawPoint3(x : Double, y : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt() - 1, y - 1, 3, 3)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawPoint3(x : Double, y : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt() - 1, (y * h).toInt() - 1, 3, 3)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawRect(x, y, width, height)
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, width, height)
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), width, height)
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect(x, y, (width * w).toInt(), height)
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, y, width, (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, width, (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.drawRect(x, y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect(x, y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Int, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawRectangle(x : Double, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.fillRect(x, y, width, height)
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, width, height)
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), width, height)
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect(x, y, (width * w).toInt(), height)
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, y, width, (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, width, (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.fillRect(x, y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect(x, y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Int, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillRectangle(x : Double, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.drawOval(x, y, width, height)
        }}
        return this
    }

    fun drawOval(x : Double, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, width, height)
        }}
        return this
    }

    fun drawOval(x : Int, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), width, height)
        }}
        return this
    }

    fun drawOval(x : Int, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawOval(x, y, (width * w).toInt(), height)
        }}
        return this
    }

    fun drawOval(x : Int, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawOval(x, y, width, (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Double, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        return this
    }

    fun drawOval(x : Double, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        return this
    }

    fun drawOval(x : Double, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, width, (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Int, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun drawOval(x : Int, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Int, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Int, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Double, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Double, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Double, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun drawOval(x : Double, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun drawOval(x : Int, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.drawOval(x, y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawOval(x, y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawOval(x, y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Int, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun drawOval(x : Double, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, _ -> run{
            g.color = color
            g.fillOval(x, y, width, height)
        }}
        return this
    }

    fun fillOval(x : Double, y : Int, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, width, height)
        }}
        return this
    }

    fun fillOval(x : Int, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), width, height)
        }}
        return this
    }

    fun fillOval(x : Int, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillOval(x, y, (width * w).toInt(), height)
        }}
        return this
    }

    fun fillOval(x : Int, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillOval(x, y, width, (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Double, y : Double, width : Int, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        return this
    }

    fun fillOval(x : Double, y : Int, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        return this
    }

    fun fillOval(x : Double, y : Int, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, width, (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Int, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun fillOval(x : Int, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Int, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Int, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Double, y : Int, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Double, y : Double, width : Int, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Double, y : Double, width : Double, height : Int, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        return this
    }

    fun fillOval(x : Double, y : Double, width : Double, height : Double, key : Any?, color : Color = BLACK) : Canvas {
        graphics[key] = {g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        return this
    }

    fun fillOval(x : Int, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, _ -> run{
            g.color = color
            g.fillOval(x, y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Int, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillOval(x, y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillOval(x, y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Double, width : Int, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), width, height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Int, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, _ -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Int, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, _, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval(x, y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Int, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Int, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Double, width : Int, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Double, width : Double, height : Int, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

    fun fillOval(x : Double, y : Double, width : Double, height : Double, color : Color = BLACK) : Canvas {
        val addedGraphics : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }}
        graphics[addedGraphics] = addedGraphics
        return this
    }

}