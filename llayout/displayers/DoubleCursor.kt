package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.utilities.LObservable
import java.awt.Color
import java.awt.Graphics
import kotlin.math.ceil

class DoubleCursor : ResizableDisplayer {

    private companion object{
        private const val DEFAULT_MIN_VALUE : Double = 0.0
        private const val DEFAULT_MAX_VALUE : Double = 5.0
        private const val DEFAULT_PRECISION : Double = 0.1
        private const val DEFAULT_CURSOR_SIDE_LENGTH : Int = 50
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lineThickness : Int = 2
            g.color = DEFAULT_COLOR
            g.fillRect(0, 0, lineThickness, h)
            g.fillRect(0, 0, w, lineThickness)
            g.fillRect(0, h - lineThickness, w, lineThickness)
            g.fillRect(w - lineThickness, 0, lineThickness, h)
        }}
        private val DEFAULT_CURSOR_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lineThickness : Int = 2
            g.color = Color(220, 220, 220)
            g.fillRect(0, 0, w, h)
            g.color = DEFAULT_COLOR
            g.fillRect(0, 0, lineThickness, h)
            g.fillRect(0, 0, w, lineThickness)
            g.fillRect(0, h - lineThickness, w, lineThickness)
            g.fillRect(w - lineThickness, 0, lineThickness, h)
        }}
        private const val CURSOR_BACKGROUND_KEY : String = "KEY GENERATED FOR THE CURSOR BACKGROUND"
    }

    private var minimalXValue : LObservable<Double> = LObservable(DEFAULT_MIN_VALUE)

    private var minimalYValue : LObservable<Double> = LObservable(DEFAULT_MIN_VALUE)

    private var maximalXValue : LObservable<Double> = LObservable(DEFAULT_MAX_VALUE)

    private var maximalYValue : LObservable<Double> = LObservable(DEFAULT_MAX_VALUE)

    private var xPrecision : LObservable<Double> = LObservable(DEFAULT_PRECISION)

    private var yPrecision : LObservable<Double> = LObservable(DEFAULT_PRECISION)

    private var xValue : LObservable<Double> = LObservable(0.0)

    private var yValue : LObservable<Double> = LObservable(0.0)

    private val cursor : CanvasDisplayer = CanvasDisplayer(DEFAULT_CURSOR_SIDE_LENGTH, DEFAULT_CURSOR_SIDE_LENGTH)

    init{
        setBackground(DEFAULT_BACKGROUND)
        minimalXValue.addListener{
            checkXBounds()
            if(minimalX() > xValue()) setXValue(minimalX())
        }
        minimalYValue.addListener{
            checkYBounds()
            if(minimalY() > yValue()) setYValue(minimalY())
        }
        maximalXValue.addListener{
            checkXBounds()
            if(maximalX() < xValue()) setXValue(maximalX())
        }
        maximalYValue.addListener{
            checkYBounds()
            if(maximalY() < yValue()) setYValue(maximalY())
        }
        xPrecision.addListener{ setXValue(xValue()) }
        yPrecision.addListener{ setYValue(yValue()) }
        setCursorImage(DEFAULT_CURSOR_BACKGROUND)
        addWidthListener{
            if(width() < cursor.width()){
                cursor.setWidth(width())
            }else{
                cursor.setWidth(DEFAULT_CURSOR_SIDE_LENGTH)
            }
        }
        addHeightListener{
            if(height() < cursor.height()){
                cursor.setHeight(height())
            }else{
                cursor.setHeight(DEFAULT_CURSOR_SIDE_LENGTH)
            }
        }
        cursor.setX(width() / 2)
        cursor.setY(height() / 2)
        cursor.addXListener{ correctCursorXPosition() }
        cursor.addYListener{ correctCursorYPosition() }
        cursor.addXListener{ updateXValue() }
        cursor.addYListener{ updateYValue() }
        cursor.setOnMouseDraggedAction { e -> cursor.moveTo(cursor.leftSideX() + e.x, cursor.upSideY() + e.y) }
        core.add(cursor)
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    fun setMinimalXValue(minimum : Double) : DoubleCursor{
        minimalXValue.value = minimum
        return this
    }

    fun setMinimalXValue(minimum : Int) : DoubleCursor = setMinimalXValue(minimum.toDouble())

    fun setMaximalXValue(maximum : Double) : DoubleCursor{
        maximalXValue.value = maximum
        return this
    }

    fun setMaximalXValue(maximum : Int) : DoubleCursor = setMaximalXValue(maximum.toDouble())

    fun setMinimalYValue(minimum : Double) : DoubleCursor{
        minimalYValue.value = minimum
        return this
    }

    fun setMinimalYValue(minimum : Int) : DoubleCursor = setMinimalYValue(minimum.toDouble())

    fun setMaximalYValue(maximum : Double) : DoubleCursor{
        maximalYValue.value = maximum
        return this
    }

    fun setMaximalYValue(maximum : Int) : DoubleCursor = setMaximalYValue(maximum.toDouble())

    fun setMinimalXYValue(minimum : Double) : DoubleCursor = setMinimalXValue(minimum).setMinimalYValue(minimum)

    fun setMinimalXYValue(minimum : Int) : DoubleCursor = setMinimalXYValue(minimum.toDouble())

    fun setMaximalXYValue(maximum : Double) : DoubleCursor = setMaximalXValue(maximum).setMaximalYValue(maximum)

    fun setMaximalXYValue(maximum : Int) : DoubleCursor = setMaximalXYValue(maximum.toDouble())

    fun setXPrecision(precision : Double) : DoubleCursor{
        if(precision < 0) throw IllegalArgumentException("Negative precision $precision in DoubleCursor.setXPrecision")
        xPrecision.value = if(precision > xRange()) xRange() else precision
        return this
    }

    fun setXPrecision(precision : Int) : DoubleCursor = setXPrecision(precision.toDouble())

    fun setYPrecision(precision : Double) : DoubleCursor{
        if(precision < 0) throw IllegalArgumentException("Negative precision $precision in DoubleCursor.setYPrecision")
        yPrecision.value = if(precision > yRange()) yRange() else precision
        return this
    }

    fun setYPrecision(precision : Int) : DoubleCursor = setYPrecision(precision.toDouble())

    fun setXYPrecision(precision : Double) : DoubleCursor = setXPrecision(precision).setYPrecision(precision)

    fun setXYPrecision(precision : Int) : DoubleCursor = setXYPrecision(precision.toDouble())

    fun addXValueListener(key : Any?, action : Action) : DoubleCursor{
        xValue.addListener(key, action)
        return this
    }

    fun addXValueListener(action : Action) : DoubleCursor{
        xValue.addListener(action)
        return this
    }

    fun addYValueListener(key : Any?, action : Action) : DoubleCursor{
        yValue.addListener(key, action)
        return this
    }

    fun addYValueListener(action : Action) : DoubleCursor{
        yValue.addListener(action)
        return this
    }

    fun removeXValueListener(key : Any?) : DoubleCursor{
        xValue.removeListener(key)
        return this
    }

    fun removeYValueListener(key : Any?) : DoubleCursor{
        yValue.removeListener(key)
        return this
    }

    fun setCursorImage(image : GraphicAction) : DoubleCursor{
        cursor.addGraphicAction(image, this)
        return this
    }

    fun setBackground(background : GraphicAction) : DoubleCursor{
        core.addGraphicAction(background, CURSOR_BACKGROUND_KEY)
        return this
    }

    fun xValue() : Double = xValue.value

    fun yValue() : Double = yValue.value

    fun moveCursorAlong(x : Int, y : Int) : DoubleCursor{
        cursor.moveAlong(x, y)
        return this
    }

    fun moveCursorAlongX(x : Int) : DoubleCursor{
        cursor.moveAlongX(x)
        return this
    }

    fun moveCursorAlongY(y : Int) : DoubleCursor{
        cursor.moveAlongY(y)
        return this
    }

    private fun setXValue(x : Double){
        xValue.value = roundedX(x)
    }

    private fun setYValue(y : Double){
        yValue.value = roundedY(y)
    }

    private fun minimalX() : Double = minimalXValue.value

    private fun maximalX() : Double = maximalXValue.value

    private fun minimalY() : Double = minimalYValue.value

    private fun maximalY() : Double = maximalYValue.value

    private fun xRange() : Double = maximalX() - minimalX()

    private fun yRange() : Double = maximalY() - minimalY()

    private fun xPrecision() : Double = xPrecision.value

    private fun yPrecision() : Double = yPrecision.value

    private fun invertedXBounds() : Boolean = minimalX() > maximalX()

    private fun invertedYBounds() : Boolean = minimalY() > maximalY()

    private fun finiteXPrecision() : Boolean = xPrecision() != 0.0

    private fun finiteYPrecision() : Boolean = yPrecision() != 0.0

    private fun swapXBounds(){
        val temporary : Double = maximalX()
        maximalXValue.value = minimalX()
        minimalXValue.value = temporary
    }

    private fun swapYBounds(){
        val temporary : Double = maximalY()
        maximalYValue.value = minimalY()
        minimalYValue.value = temporary
    }

    private fun checkXBounds(){
        if(invertedXBounds()) swapXBounds()
    }

    private fun checkYBounds(){
        if(invertedYBounds()) swapYBounds()
    }

    private fun checkBounds(){
        checkXBounds()
        checkYBounds()
    }

    private fun roundedX(x : Double) : Double{
        return if(finiteXPrecision()){
            minimalX() + xPrecision() * ceil( ( x - minimalX() - xPrecision() / 2 ) / xPrecision() )
        }else x
    }

    private fun roundedY(y : Double) : Double{
        return if(finiteYPrecision()){
            minimalY() + yPrecision() * ceil( ( y - minimalY() - yPrecision() / 2 ) / yPrecision() )
        }else y
    }

    private fun isTooFarUp() : Boolean = cursor.upSideY() < 0

    private fun isTooFarDown() : Boolean = cursor.downSideY() > height()

    private fun isTooFarLeft() : Boolean = cursor.leftSideX() < 0

    private fun isTooFarRight() : Boolean = cursor.rightSideX() > width()

    private fun correctUp(){
        cursor.setY(cursor.height() / 2)
    }

    private fun correctDown(){
        cursor.setY(height() - cursor.height() / 2)
    }

    private fun correctLeft(){
        cursor.setX(cursor.width() / 2)
    }

    private fun correctRight(){
        cursor.setX(width() - cursor.width() / 2)
    }

    private fun correctCursorXPosition(){
        if(cursor.width() < width()){
            if(isTooFarLeft()){
                correctLeft()
            }else if(isTooFarRight()){
                correctRight()
            }
        }else{
            cursor.setX(width() / 2)
        }
    }

    private fun correctCursorYPosition(){
        if(cursor.height() < height()){
            if(isTooFarUp()){
                correctUp()
            }else if(isTooFarDown()){
                correctDown()
            }
        }else{
            cursor.setY(height() / 2)
        }
    }

    private fun updateXValue(){
        if(cursor.width() >= width()){
            setXValue(minimalX())
        }else{
            setXValue(minimalX() + ( cursor.leftSideX().toDouble() / (width() - cursor.width()) ) * xRange() )
        }
    }

    private fun updateYValue(){
        if(cursor.height() >= height()){
            setYValue(minimalY())
        }else{
            setYValue(minimalY() + ( (height() - cursor.downSideY().toDouble()) / (height() - cursor.height()) ) * yRange() )
        }
    }

    private fun conserveCursorPositionOnResize(){
        val xProportion : Double = (xValue() - minimalX()) / xRange()
        val newX : Int = (xProportion * width()).toInt()
        var previousValue : Double = xValue()
        cursor.setX(newX)
        setXValue(previousValue)
        val yProportion : Double = (yValue() - minimalY()) / yRange()
        val newY : Int = height() - (yProportion * height()).toInt()
        previousValue = yValue()
        cursor.setY(newY)
        setYValue(previousValue)
        correctCursorXPosition()
        correctCursorYPosition()
    }

    override fun initializeDrawingParameters(g: Graphics) {
        cursor.setWidth(DEFAULT_CURSOR_SIDE_LENGTH)
        cursor.setHeight(DEFAULT_CURSOR_SIDE_LENGTH)
        conserveCursorPositionOnResize()
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        conserveCursorPositionOnResize()
    }

}