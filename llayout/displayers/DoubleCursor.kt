package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.interfaces.StandardLContainer
import llayout.utilities.LProperty
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
    }

    private var minimalXValue : LProperty<Double> = LProperty(DEFAULT_MIN_VALUE)

    private var minimalYValue : LProperty<Double> = LProperty(DEFAULT_MIN_VALUE)

    private var maximalXValue : LProperty<Double> = LProperty(DEFAULT_MAX_VALUE)

    private var maximalYValue : LProperty<Double> = LProperty(DEFAULT_MAX_VALUE)

    private var xPrecision : LProperty<Double> = LProperty(DEFAULT_PRECISION)

    private var yPrecision : LProperty<Double> = LProperty(DEFAULT_PRECISION)

    private var xValue : LProperty<Double> = LProperty(0.0)

    private var yValue : LProperty<Double> = LProperty(0.0)

    private var background : GraphicAction = DEFAULT_BACKGROUND

    private val cursor : CanvasDisplayer = CanvasDisplayer(DEFAULT_CURSOR_SIDE_LENGTH, DEFAULT_CURSOR_SIDE_LENGTH)

    init{
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
        cursor.addXListener{ correctCursorXPosition() }
        cursor.addYListener{ correctCursorYPosition() }
        cursor.addXListener{ updateXValue() }
        cursor.addYListener{ updateYValue() }
        cursor.setOnMouseDraggedAction { e -> cursor.moveTo(cursor.leftSideX() + e.x, cursor.upSideY() + e.y) }
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

    fun xValue() : Double = xValue.value

    fun yValue() : Double = yValue.value

    fun setBackground(background : GraphicAction) : DoubleCursor{
        this.background = background
        return this
    }

    fun setCursorImage(image : GraphicAction) : DoubleCursor{
        cursor.addGraphicAction(image, this)
        return this
    }

    fun moveCursor(x : Int, y : Int) : DoubleCursor{
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

    private fun isTooFarUp() : Boolean = cursor.upSideY() < upSideY()

    private fun isTooFarDown() : Boolean = cursor.downSideY() > downSideY()

    private fun isTooFarLeft() : Boolean = cursor.leftSideX() < leftSideX()

    private fun isTooFarRight() : Boolean = cursor.rightSideX() > rightSideX()

    private fun correctUp(){
        cursor.setCenterY(upSideY() + cursor.height() / 2)
    }

    private fun correctDown(){
        cursor.setCenterY(downSideY() - cursor.height() / 2)
    }

    private fun correctLeft(){
        cursor.setCenterX(leftSideX() + cursor.width() / 2)
    }

    private fun correctRight(){
        cursor.setCenterX(rightSideX() - cursor.width() / 2)
    }

    private fun correctCursorXPosition(){
        if(cursor.width() < width()){
            if(isTooFarLeft()){
                correctLeft()
            }else if(isTooFarRight()){
                correctRight()
            }
        }else{
            cursor.setCenterX(centerX())
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
            cursor.setCenterY(centerY())
        }
    }

    private fun updateXValue(){
        if(cursor.width() >= width()){
            setXValue(minimalX())
        }else{
            setXValue(minimalX() + ( (cursor.leftSideX().toDouble() - leftSideX()) / (width() - cursor.width()) ) * xRange() )
        }
    }

    private fun updateYValue(){
        if(cursor.height() >= height()){
            setYValue(minimalY())
        }else{
            setYValue(minimalY() + ( (downSideY() - cursor.downSideY().toDouble()) / (height() - cursor.height()) ) * yRange() )
        }
    }

    private fun conserveCursorPositionOnResize(){
        val xProportion : Double = (xValue() - minimalX()) / xRange()
        val newX : Int = leftSideX() + (xProportion * width()).toInt()
        var previousValue : Double = xValue()
        cursor.setCenterX(newX)
        setXValue(previousValue)
        val yProportion : Double = (yValue() - minimalY()) / yRange()
        val newY : Int = downSideY() - (yProportion * height()).toInt()
        previousValue = yValue()
        cursor.setCenterY(newY)
        setYValue(previousValue)
    }

    override fun onAdd(container: StandardLContainer) {
        container.add(cursor)
    }

    override fun onRemove(container: StandardLContainer) {
        container.remove(cursor)
    }

    override fun loadParameters(g: Graphics) {
        cursor.setWidth(DEFAULT_CURSOR_SIDE_LENGTH)
        cursor.setHeight(DEFAULT_CURSOR_SIDE_LENGTH)
        cursor.setCenterX(centerX())
        cursor.setCenterY(centerY())
        conserveCursorPositionOnResize()
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): DoubleCursor {
        super.updateRelativeValues(frameWidth, frameHeight)
        conserveCursorPositionOnResize()
        return this
    }

    override fun drawDisplayer(g: Graphics) {
        background.invoke(g, width(), height())
    }

}