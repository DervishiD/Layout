package usages.juliasets

import llayout3.displayers.*
import llayout3.frame.LApplication
import llayout3.frame.LFrame
import llayout3.frame.LScene
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent.*

private const val PLOT_SIZE : Int = 800
private const val ITERATIONS : Int = 200
private const val RELOAD_PERIOD : Int = 2000
private const val ARROW_MOVEMENT : Int = 2
private enum class FractalType{JULIA, MANDELBROT}

/*
 * 0.3821 + 0.2001 i
 * 0.3730 + 0.0855 i
 * 0.3862 + 0.2818 i
 * 0.3883 + 0.2378 i
 */

val juliaSetsApplication : LApplication = LApplication{
    mainFrame.run()
    graphFrame.run()
}

private object mainScreen : LScene(){

    private val cursor : DoubleCursor = DoubleCursor(0.8, 1.0)
            .setMinimalYValue(-0.5)
            .setMinimalXValue(-0.5)
            .setMaximalXValue(0.5)
            .setMaximalYValue(0.5)
            .setXYPrecision(0)
            .alignLeftTo(0)
            .alignUpTo(0) as DoubleCursor

    private val xLabel : Label = Label()
            .alignUpToUp(cursor)
            .alignLeftToRight(cursor) as Label

    private val yLabel : Label = Label()
            .alignUpToDown(xLabel)
            .alignLeftToRight(cursor) as Label

    private val reloadButton : TextButton = TextButton("Reload") {reload()}
            .setY(0.3).alignRightTo(1.0) as TextButton

    private val switch : Switch = Switch()
            .setWidth(50).setHeight(50).setY(0.6).alignLeftToRight(cursor) as Switch

    private val typeLabel : Label = Label("Julia").alignLeftToRight(cursor).alignUpToDown(switch) as Label

    init{
        cursor.addXValueListener{ xLabel.setText("x : ${cursor.xValue()}") }
        cursor.addYValueListener{ yLabel.setText("y : ${cursor.yValue()}") }
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
        add(typeLabel)
        add(switch)
        switch.addValueListener {
            if(switch.value()){
                typeLabel.setText("Mandelbrot")
                graphScene.mandelbrot()
            }else{
                typeLabel.setText("Julia")
                graphScene.julia()
            }
        }
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

private object graphScene : LScene(){

    private val DEFAULT_JULIA_MIN_X : Double = -1.5
    private val DEFAULT_JULIA_MIN_Y : Double = -1.5
    private val DEFAULT_JULIA_MAX_X : Double = 1.5
    private val DEFAULT_JULIA_MAX_Y : Double = 1.5

    private val DEFAULT_MANDELBROT_MIN_X : Double = -2.0
    private val DEFAULT_MANDELBROT_MIN_Y : Double = -2.0
    private val DEFAULT_MANDELBROT_MAX_X : Double = 2.0
    private val DEFAULT_MANDELBROT_MAX_Y : Double = 2.0

    private var c : ComplexNumber = ComplexNumber()

    private var minX : Double = -1.5
    private var minY : Double = -1.5
    private var maxX : Double = 1.5
    private var maxY : Double = 1.5

    private var type : FractalType = FractalType.JULIA

    init{
        addDimensionListener { reset() }
        setOnMouseWheelMovedAction { e -> zoom(e.x, e.y, e.unitsToScroll) }
        setOnLoadAction { addPoints() }
    }

    fun reload(c : ComplexNumber){
        this.c = c
        reset()
    }

    fun julia(){
        type = FractalType.JULIA
        minX = DEFAULT_JULIA_MIN_X
        maxX = DEFAULT_JULIA_MAX_X
        minY = DEFAULT_JULIA_MIN_Y
        maxY = DEFAULT_JULIA_MAX_Y
        reset()
    }

    fun mandelbrot(){
        type = FractalType.MANDELBROT
        minX = DEFAULT_MANDELBROT_MIN_X
        maxX = DEFAULT_MANDELBROT_MAX_X
        minY = DEFAULT_MANDELBROT_MIN_Y
        maxY = DEFAULT_MANDELBROT_MAX_Y
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

    private fun juliaIterations(x : Double, y : Double) : Int{
        var number : ComplexNumber = ComplexNumber(x, y)
        for(i : Int in 0..ITERATIONS){
            if(number.modulus() >= 2) return i
            number = number * number + c()
        }
        return ITERATIONS
    }

    private fun mandelbrotIterations(x : Double, y : Double) : Int{
        val z : ComplexNumber = ComplexNumber(x, y)
        var number : ComplexNumber = ComplexNumber()
        for(i : Int in 0..ITERATIONS){
            if(number.modulus() >= 2) return i
            number = number * number + z
        }
        return ITERATIONS
    }

    private fun colorOf(iterations : Int) : Color{
        val level : Int = (255 * iterations.toDouble() / ITERATIONS).toInt()
        return Color(level, level, level)
    }

    private fun iterateOn(x : Double, y : Double) : Color = if(type == FractalType.JULIA) colorOf(juliaIterations(x, y)) else colorOf(mandelbrotIterations(x, y))

    private fun addPoints(){
        for(i : Int in 0..width()){
            for(j : Int in 0..height()){
                addGraphicAction({ g : Graphics, _, _ ->
                    g.color = iterateOn(xOfPixel(i), yOfPixel(j))
                    g.fillRect(i, j, 1, 1)
                })
            }
        }
    }

    private fun reset(){
        clearBackground()
        addPoints()
    }

    private fun zoom(i : Int, j : Int, units : Int){
        val x : Double = xOfPixel(i)
        val y : Double = yOfPixel(j)
        val nextHalfXRange : Double
        val nextHalfYRange : Double
        if(units > 0){
            nextHalfXRange = xRange()
            nextHalfYRange = yRange()
        }else{
            nextHalfXRange = xRange() / 4
            nextHalfYRange = yRange() / 4
        }
        minX = x - nextHalfXRange
        maxX = x + nextHalfXRange
        minY = y - nextHalfYRange
        maxY = y + nextHalfYRange
        reset()
    }

}

private val graphFrame : LFrame = LFrame(graphScene).setWidth(PLOT_SIZE).setHeight(PLOT_SIZE).setUnResizable().setTimerPeriod(RELOAD_PERIOD)

private val mainFrame : LFrame = LFrame(mainScreen)
