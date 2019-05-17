package usages.juliasets

import llayout.displayers.DoubleCursor
import llayout.displayers.Label
import llayout.displayers.TextButton
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LScene
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent.*

private const val PLOT_SIZE : Int = 800
private const val ITERATIONS : Int = 100
private const val RELOAD_PERIOD : Int = 1500
private const val ARROW_MOVEMENT : Int = 2

/*
 * 0.3852 + 0.2017 i
 * 0.3730 + 0.0855 i
 * 0.3862 + 0.2818 i
 */

/*
 * Zoom (drag)
 */

val juliaSetsApplication : LApplication = LApplication{
    mainFrame.run()
    graphFrame.run()
}

private val mainScreen = object : LScene(){

    private val cursor : DoubleCursor = DoubleCursor(0, 0, 0.8, 1.0)
            .setMinimalYValue(-0.5)
            .setMinimalXValue(-0.5)
            .setMaximalXValue(0.5)
            .setMaximalYValue(0.5)
            .setXYPrecision(0)
            .alignLeftTo(0)
            .alignUpTo(0) as DoubleCursor

    private val xLabel : Label = Label(0, 0, "")
            .alignUpToUp(cursor)
            .alignLeftToRight(cursor) as Label

    private val yLabel : Label = Label(0, 0, "")
            .alignUpToDown(xLabel)
            .alignLeftToRight(cursor) as Label

    private val reloadButton : TextButton = TextButton(0, 0.5, "Reload", {reload()})
            .alignRightTo(1.0) as TextButton

    init{
        cursor.addXValueListener{ xLabel.setDisplayedText("x : ${cursor.xValue()}") }
        cursor.addYValueListener{ yLabel.setDisplayedText("y : ${cursor.yValue()}") }
        add(yLabel)
        add(xLabel)
        add(cursor)
        add(reloadButton)
        cursor.setOnKeyPressedAction { e ->
            when(e.keyCode){
                VK_LEFT -> moveLeft()
                VK_RIGHT -> moveRight()
                VK_UP -> moveUp()
                VK_DOWN -> moveDown()
                VK_ENTER -> reload()
            }
        }
        setOnMouseClickedAction { cursor.requestFocusInWindow() }
        xLabel.setOnMouseClickedAction { cursor.requestFocusInWindow() }
        yLabel.setOnMouseClickedAction { cursor.requestFocusInWindow() }
    }

    private fun selectedValue() : ComplexNumber = ComplexNumber(cursor.xValue(), cursor.yValue())

    private fun reload() = graphScene.reload(selectedValue())

    private fun moveLeft(){
        cursor.moveCursorAlongX(-ARROW_MOVEMENT)
    }

    private fun moveRight(){
        cursor.moveCursorAlongX(ARROW_MOVEMENT)
    }

    private fun moveUp(){
        cursor.moveCursorAlongY(-ARROW_MOVEMENT)
    }

    private fun moveDown(){
        cursor.moveCursorAlongY(ARROW_MOVEMENT)
    }

}

private val graphScene = object : LScene(){

    private var c : ComplexNumber = ComplexNumber()

    private var minX : Double = -1.5
    private var minY : Double = -1.5
    private var maxX : Double = 1.5
    private var maxY : Double = 1.5

    init{
        addPoints()
        w.addListener{ reset() }
        h.addListener{ reset() }
    }

    fun reload(c : ComplexNumber){
        this.c = c
        reset()
    }

    private fun c() : ComplexNumber = c

    private fun minX() : Double = minX
    private fun minY() : Double = minY
    private fun maxX() : Double = maxX
    private fun maxY() : Double = maxY

    private fun xRange() : Double = maxX() - minX()
    private fun yRange() : Double = maxY() - minY()

    private fun xOfPixel(i : Int) : Double = (i * xRange() / width()) + minX()
    private fun yOfPixel(j : Int) : Double = maxY() - (j * yRange() / height())

    private fun numberOfIterationsOf(x : Double, y : Double) : Int{
        var number : ComplexNumber = ComplexNumber(x, y)
        for(i : Int in 0..ITERATIONS){
            if(number.modulus() >= 2) return i
            number = number * number + c()
        }
        return ITERATIONS
    }

    private fun colorOf(iterations : Int) : Color{
        val level : Int = (255 * iterations.toDouble() / ITERATIONS).toInt()
        return Color(level, level, level)
    }

    private fun iterateOn(x : Double, y : Double) : Color = colorOf(numberOfIterationsOf(x, y))

    private fun addPoints(){
        for(i : Int in 0..width()){
            for(j : Int in 0..height()){
                val color : Color = iterateOn(xOfPixel(i), yOfPixel(j))
                addGraphicAction{ g : Graphics, _, _ -> run{
                    g.color = color
                    g.fillRect(i, j, 1, 1)
                }}
            }
        }
    }

    private fun reset(){
        clearBackground()
        addPoints()
    }

}

private val graphFrame : LFrame = LFrame(graphScene).setWidth(PLOT_SIZE).setHeight(PLOT_SIZE).setUnResizable().setTimerPeriod(RELOAD_PERIOD)

private val mainFrame : LFrame = LFrame(mainScreen)
