package llayout.interfaces

import llayout.DEFAULT_COLOR
import llayout.utilities.GraphicAction
import llayout.utilities.StringDisplay
import llayout.utilities.Text
import llayout.utilities.displayedHeight
import llayout.utilities.displayedWidth
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB

interface CanvasCore : HavingDimension {

    private companion object{
        private var drawingIndex : Int = Int.MIN_VALUE
        private fun nextIndex() : Int = drawingIndex++
        private fun defaultKey() : String = "KEY GENERATED BY A METHOD DECLARED IN THE CANVAS INTERFACE. DRAWING CODE : ${nextIndex()}"
    }

    var graphics : MutableMap<Any?, GraphicAction>

    fun backgroundAsImage() : BufferedImage {
        val image = BufferedImage(width(), height(), TYPE_INT_ARGB)
        val g : Graphics = image.graphics
        for(drawing : GraphicAction in graphics.values){
            drawing(g, width(), height())
        }
        return image
    }

    fun drawBackground(g : Graphics) : CanvasCore {
        for(graphicAction : GraphicAction in graphics.values){
            graphicAction.invoke(g, width(), height())
        }
        return this
    }

    fun addGraphicAction(graphicAction : GraphicAction, key : Any? = defaultKey()) : CanvasCore {
        graphics[key] = graphicAction
        return this
    }

    fun removeDrawing(key : Any?) : CanvasCore {
        graphics.remove(key)
        return this
    }

    fun clearBackground() : CanvasCore{
        graphics.clear()
        return this
    }

    fun drawPoint(x : Int, y : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.drawRect(x, y, 1, 1)
        }, key)
    }


    fun drawPoint(x : Double, y : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.drawRect((x * w).toInt(), y, 1, 1)
        }, key)
    }

    fun drawPoint(x : Int, y : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.drawRect(x, (y * h).toInt(), 1, 1)
        }, key)
    }

    fun drawPoint(x : Double, y : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), 1, 1)
        }, key)
    }

    fun fillBackground(color : Color, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect(0, 0, w, h)
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.drawLine(fromX, fromY, toX, toY)
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, _ ->
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, toY)
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h ->
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, toY)
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, _ ->
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), toY)
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Int, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h ->
            g.color = color
            g.drawLine(fromX, fromY, toX, (toY * h).toInt())
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, toY)
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, _ ->
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), toY)
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Int, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, toX, (toY * h).toInt())
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), toY)
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Int, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h ->
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), toX, (toY * h).toInt())
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Int, toX : Double, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine(fromX, fromY, (toX * w).toInt(), (toY * h).toInt())
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Int, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), toY)
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Int, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), toX, (toY * h).toInt())
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Int, toX : Double, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine((fromX * w).toInt(), fromY, (toX * w).toInt(), (toY * h).toInt())
        }, key)
    }

    fun drawLine(fromX : Int, fromY : Double, toX : Double, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine(fromX, (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }, key)
    }

    fun drawLine(fromX : Double, fromY : Double, toX : Double, toY : Double, color: Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w, h ->
            g.color = color
            g.drawLine((fromX * w).toInt(), (fromY * h).toInt(), (toX * w).toInt(), (toY * h).toInt())
        }, key)
    }

    fun drawPoint2(x : Int, y : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.fillRect(x, y, 2, 2)
        }, key)
    }

    fun drawPoint2(x : Int, y : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillRect(x, (y * h).toInt(), 2, 2)
        }, key)
    }

    fun drawPoint2(x : Double, y : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.fillRect((x * w).toInt(), y, 2, 2)
        }, key)
    }

    fun drawPoint2(x : Double, y : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), 2, 2)
        }, key)
    }

    fun drawPoint3(x : Int, y : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.fillRect(x - 1, y - 1, 3, 3)
        }, key)
    }

    fun drawPoint3(x : Int, y : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillRect(x - 1, (y * h).toInt() - 1, 3, 3)
        }, key)
    }

    fun drawPoint3(x : Double, y : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.fillRect((x * w).toInt() - 1, y - 1, 3, 3)
        }, key)
    }

    fun drawPoint3(x : Double, y : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt() - 1, (y * h).toInt() - 1, 3, 3)
        }, key)
    }

    fun drawRectangle(x : Int, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.drawRect(x, y, width, height)
        }, key)
    }

    fun drawRectangle(x : Double, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({ g : Graphics, w : Int, _ ->
            g.color = color
            g.drawRect((x * w).toInt(), y, width, height)
        }, key)
    }

    fun drawRectangle(x : Int, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.drawRect(x, (y * h).toInt(), width, height)
        }, key)
    }

    fun drawRectangle(x : Int, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.drawRect(x, y, (width * w).toInt(), height)
        }, key)
    }

    fun drawRectangle(x : Int, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.drawRect(x, y, width, (height * h).toInt())
        }, key)
    }

    fun drawRectangle(x : Double, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), width, height)
        }, key)
    }

    fun drawRectangle(x : Double, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.drawRect((x * w).toInt(), y, (width * w).toInt(), height)
        }, key)
    }

    fun drawRectangle(x : Double, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect((x * w).toInt(), y, width, (height * h).toInt())
        }, key)
    }

    fun drawRectangle(x : Int, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect(x, (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun drawRectangle(x : Int, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.drawRect(x, (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun drawRectangle(x : Int, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect(x, y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun drawRectangle(x : Int, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun drawRectangle(x : Double, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun drawRectangle(x : Double, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun drawRectangle(x : Double, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun drawRectangle(x : Double, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Int, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.fillRect(x, y, width, height)
        }, key)
    }

    fun fillRectangle(x : Double, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({ g : Graphics, w : Int, _ ->
            g.color = color
            g.fillRect((x * w).toInt(), y, width, height)
        }, key)
    }

    fun fillRectangle(x : Int, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillRect(x, (y * h).toInt(), width, height)
        }, key)
    }

    fun fillRectangle(x : Int, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.fillRect(x, y, (width * w).toInt(), height)
        }, key)
    }

    fun fillRectangle(x : Int, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillRect(x, y, width, (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Double, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), width, height)
        }, key)
    }

    fun fillRectangle(x : Double, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.fillRect((x * w).toInt(), y, (width * w).toInt(), height)
        }, key)
    }

    fun fillRectangle(x : Double, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt(), y, width, (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Int, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect(x, (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun fillRectangle(x : Int, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillRect(x, (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Int, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect(x, y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Int, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Double, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Double, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun fillRectangle(x : Double, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun fillRectangle(x : Double, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Int, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.drawOval(x, y, width, height)
        }, key)
    }

    fun drawOval(x : Double, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({ g : Graphics, w : Int, _ ->
            g.color = color
            g.drawOval((x * w).toInt(), y, width, height)
        }, key)
    }

    fun drawOval(x : Int, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.drawOval(x, (y * h).toInt(), width, height)
        }, key)
    }

    fun drawOval(x : Int, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.drawOval(x, y, (width * w).toInt(), height)
        }, key)
    }

    fun drawOval(x : Int, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.drawOval(x, y, width, (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Double, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), width, height)
        }, key)
    }

    fun drawOval(x : Double, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.drawOval((x * w).toInt(), y, (width * w).toInt(), height)
        }, key)
    }

    fun drawOval(x : Double, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval((x * w).toInt(), y, width, (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Int, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval(x, (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun drawOval(x : Int, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.drawOval(x, (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Int, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval(x, y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Int, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Double, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Double, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun drawOval(x : Double, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun drawOval(x : Double, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.drawOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Int, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, _ ->
            g.color = color
            g.fillOval(x, y, width, height)
        }, key)
    }

    fun fillOval(x : Double, y : Int, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({ g : Graphics, w : Int, _ ->
            g.color = color
            g.fillOval((x * w).toInt(), y, width, height)
        }, key)
    }

    fun fillOval(x : Int, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillOval(x, (y * h).toInt(), width, height)
        }, key)
    }

    fun fillOval(x : Int, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.fillOval(x, y, (width * w).toInt(), height)
        }, key)
    }

    fun fillOval(x : Int, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillOval(x, y, width, (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Double, y : Double, width : Int, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), width, height)
        }, key)
    }

    fun fillOval(x : Double, y : Int, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, _ ->
            g.color = color
            g.fillOval((x * w).toInt(), y, (width * w).toInt(), height)
        }, key)
    }

    fun fillOval(x : Double, y : Int, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval((x * w).toInt(), y, width, (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Int, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval(x, (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun fillOval(x : Int, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, _, h : Int ->
            g.color = color
            g.fillOval(x, (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Int, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval(x, y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Int, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval(x, (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Double, y : Int, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval((x * w).toInt(), y, (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Double, y : Double, width : Int, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), width, (height * h).toInt())
        }, key)
    }

    fun fillOval(x : Double, y : Double, width : Double, height : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), height)
        }, key)
    }

    fun fillOval(x : Double, y : Double, width : Double, height : Double, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore {
        return addGraphicAction({g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillOval((x * w).toInt(), (y * h).toInt(), (width * w).toInt(), (height * h).toInt())
        }, key)
    }

    fun writeCentered(text : StringDisplay, key : Any? = defaultKey()) : CanvasCore{
        return addGraphicAction({ g : Graphics, w : Int, h : Int ->
            g.color = text.color
            g.font = text.font
            g.drawString(text.text, (w - displayedWidth(text, g)) / 2, (h - displayedHeight(text, g)) / 2)
        }, key)
    }

    fun writeCentered(text : CharSequence, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Int, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Double, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Float, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Long, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Short, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Byte, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Boolean, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Char, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeCentered(text : Text, key : Any? = defaultKey()) : CanvasCore = writeCentered(text.asString(), key)

    fun writeAtTop(text : StringDisplay, key : Any? = defaultKey()) : CanvasCore{
        return addGraphicAction({ g : Graphics, _ : Int, _ : Int ->
            g.color = text.color
            g.font = text.font
            g.drawString(text.text, 0, g.getFontMetrics(text.font).maxAscent)
        }, key)
    }

    fun writeAtTop(text : CharSequence, key : Any? = defaultKey()) : CanvasCore = writeAtTop(StringDisplay(text), key)

    fun writeAtTop(text : Int, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Double, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Float, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Long, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Short, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Byte, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Boolean, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Char, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtTop(text : Text, key : Any? = defaultKey()) : CanvasCore = writeAtTop(text.asString(), key)

    fun writeAtBottom(text : StringDisplay, key : Any? = defaultKey()) : CanvasCore{
        return addGraphicAction({ g : Graphics, _ : Int, h : Int ->
            g.color = text.color
            g.font = text.font
            g.drawString(text.text, 0, h - displayedHeight(text, g) + g.getFontMetrics(text.font).maxAscent)
        }, key)
    }

    fun writeAtBottom(text : CharSequence, key : Any? = defaultKey()) : CanvasCore = writeAtBottom(StringDisplay(text), key)

    fun writeAtBottom(text : Int, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Double, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Float, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Long, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Short, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Byte, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Boolean, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Char, key : Any? = defaultKey()) : CanvasCore = writeCentered(StringDisplay(text), key)

    fun writeAtBottom(text : Text, key : Any? = defaultKey()) : CanvasCore = writeAtBottom(text.asString(), key)

    fun nPixelFrame(n : Int, color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore{
        if(n < 0) throw IllegalArgumentException("The width $n of the framing must be positive.")
        if(n == 0) return this
        return addGraphicAction({ g : Graphics, w : Int, h : Int ->
            g.color = color
            g.fillRect(0, 0, n, h)
            g.fillRect(0, 0, w, n)
            g.fillRect(0, h - n, w, n)
            g.fillRect(w - n, 0, n, h)
        }, key)
    }

    fun onePixelFrame(color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore = nPixelFrame(1, color, key)

    fun twoPixelFrame(color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore = nPixelFrame(2, color, key)

    fun threePixelFrame(color : Color = DEFAULT_COLOR, key : Any? = defaultKey()) : CanvasCore = nPixelFrame(3, color, key)

}