package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.utilities.LObservable
import java.awt.Color
import java.awt.Graphics
import kotlin.math.ceil

/**
 * A cursor that takey pairs of values in closed intervals over the reals.
 * @see ResizableDisplayer
 * @since LLayout 1
 */
class DoubleCursor : ResizableDisplayer {

    private companion object{

        /**
         * The default minimal value of both intervals.
         * @since LLayout 1
         */
        private const val DEFAULT_MIN_VALUE : Double = 0.0

        /**
         * The default maximal value of both intervals.
         * @since LLayout 1
         */
        private const val DEFAULT_MAX_VALUE : Double = 5.0

        /**
         * The default precision of both intervals.
         * @since LLayout 1
         */
        private const val DEFAULT_PRECISION : Double = 0.1

        /**
         * The side length of the moving thing.
         * @since LLayout 1
         */
        private const val DEFAULT_CURSOR_SIDE_LENGTH : Int = 50

        /**
         * The default background of the cursor.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lineThickness : Int = 2
            g.color = DEFAULT_COLOR
            g.fillRect(0, 0, lineThickness, h)
            g.fillRect(0, 0, w, lineThickness)
            g.fillRect(0, h - lineThickness, w, lineThickness)
            g.fillRect(w - lineThickness, 0, lineThickness, h)
        }}

        /**
         * The default background of the sliding thing.
         * @see GraphicAction
         * @since LLayout 1
         */
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

        /**
         * A key used to set the cursor's background.
         * @since LLayout 1
         */
        private const val CURSOR_BACKGROUND_KEY : String = "KEY GENERATED FOR THE CURSOR BACKGROUND"

    }

    /**
     * The minimal value selected by the x coordinate of the cursor.
     * @see LObservable
     * @since LLayout 1
     */
    private var minimalXValue : LObservable<Double> = LObservable(DEFAULT_MIN_VALUE)

    /**
     * The minimal value selected by the y coordinate of the cursoe.
     * @see LObservable
     * @since LLayout 1
     */
    private var minimalYValue : LObservable<Double> = LObservable(DEFAULT_MIN_VALUE)

    /**
     * The maximal value selected by the x coordinate of the cursor.
     * @see LObservable
     * @since LLayout 1
     */
    private var maximalXValue : LObservable<Double> = LObservable(DEFAULT_MAX_VALUE)

    /**
     * The maximal value selected by the y coordinate of the cursor.
     * @see LObservable
     * @since LLayout 1
     */
    private var maximalYValue : LObservable<Double> = LObservable(DEFAULT_MAX_VALUE)

    /**
     * The precision of the x value.
     * @see LObservable
     * @since LLayout 1
     */
    private var xPrecision : LObservable<Double> = LObservable(DEFAULT_PRECISION)

    /**
     * The precision of the y value.
     * @see LObservable
     * @since LLayout 1
     */
    private var yPrecision : LObservable<Double> = LObservable(DEFAULT_PRECISION)

    /**
     * The value selected by the x coordinate of the cursor.
     * @see LObservable
     * @since LLayout 1
     */
    private var xValue : LObservable<Double> = LObservable(0.0)

    /**
     * The value selected by the y coordinate of the cursor.
     * @see LObservable
     * @since LLayout 1
     */
    private var yValue : LObservable<Double> = LObservable(0.0)

    /**
     * The moving thing.
     * @see CanvasDisplayer
     * @since LLayout 1
     */
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

    /**
     * Sets the minimal value that can be selected by the x coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalXValue(minimum : Double) : DoubleCursor{
        minimalXValue.value = minimum
        return this
    }

    /**
     * Sets the minimal value that can be selected by the x coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalXValue(minimum : Int) : DoubleCursor = setMinimalXValue(minimum.toDouble())

    /**
     * Sets the maximal value that can be selected by the x coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalXValue(maximum : Double) : DoubleCursor{
        maximalXValue.value = maximum
        return this
    }

    /**
     * Sets the maximal value that can be selected by the x coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalXValue(maximum : Int) : DoubleCursor = setMaximalXValue(maximum.toDouble())

    /**
     * Sets the minimal value that can be selected by the y coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalYValue(minimum : Double) : DoubleCursor{
        minimalYValue.value = minimum
        return this
    }

    /**
     * Sets the minimal value that can be selected by the y coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalYValue(minimum : Int) : DoubleCursor = setMinimalYValue(minimum.toDouble())

    /**
     * Sets the maximal value that can be selected by the y coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalYValue(maximum : Double) : DoubleCursor{
        maximalYValue.value = maximum
        return this
    }

    /**
     * Sets the maximal value that can be selected by the y coordinate of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalYValue(maximum : Int) : DoubleCursor = setMaximalYValue(maximum.toDouble())

    /**
     * Sets the minimal value that can be selected by both coordinates of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalXYValue(minimum : Double) : DoubleCursor = setMinimalXValue(minimum).setMinimalYValue(minimum)

    /**
     * Sets the minimal value that can be selected by both coordinates of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMinimalXYValue(minimum : Int) : DoubleCursor = setMinimalXYValue(minimum.toDouble())

    /**
     * Sets the maximal value that can be selected by both coordinates of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalXYValue(maximum : Double) : DoubleCursor = setMaximalXValue(maximum).setMaximalYValue(maximum)

    /**
     * Sets the maximal value that can be selected by both coordinates of the cursor.
     * @return this
     * @since LLayout 1
     */
    fun setMaximalXYValue(maximum : Int) : DoubleCursor = setMaximalXYValue(maximum.toDouble())

    /**
     * Sets the precision of the x value.
     * @throws IllegalArgumentException If the given precision is negative.
     * @return this
     * @since LLayout 1
     */
    fun setXPrecision(precision : Double) : DoubleCursor{
        if(precision < 0) throw IllegalArgumentException("Negative precision $precision in DoubleCursor.setXPrecision")
        xPrecision.value = if(precision > xRange()) xRange() else precision
        return this
    }

    /**
     * Sets the precision of the x value.
     * @throws IllegalArgumentException If the given precision is negative.
     * @return this
     * @since LLayout 1
     */
    fun setXPrecision(precision : Int) : DoubleCursor = setXPrecision(precision.toDouble())

    /**
     * Sets the precision of the y value.
     * @throws IllegalArgumentException If the given precision is negative.
     * @return this
     * @since LLayout 1
     */
    fun setYPrecision(precision : Double) : DoubleCursor{
        if(precision < 0) throw IllegalArgumentException("Negative precision $precision in DoubleCursor.setYPrecision")
        yPrecision.value = if(precision > yRange()) yRange() else precision
        return this
    }

    /**
     * Sets the precision of the y value.
     * @throws IllegalArgumentException If the given precision is negative.
     * @return this
     * @since LLayout 1
     */
    fun setYPrecision(precision : Int) : DoubleCursor = setYPrecision(precision.toDouble())

    /**
     * Sets the precision of both values.
     * @throws IllegalArgumentException If the given precision is negative.
     * @return this
     * @since LLayout 1
     */
    fun setXYPrecision(precision : Double) : DoubleCursor = setXPrecision(precision).setYPrecision(precision)

    /**
     * Sets the precision of both values.
     * @throws IllegalArgumentException If the given precision is negative.
     * @return this
     * @since LLayout 1
     */
    fun setXYPrecision(precision : Int) : DoubleCursor = setXYPrecision(precision.toDouble())

    /**
     * Adds a listener of the x value.
     * @param key The key associated to the given action
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addXValueListener(key : Any?, action : Action) : DoubleCursor{
        xValue.addListener(key, action)
        return this
    }

    /**
     * Adds a listener of the x value.
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addXValueListener(action : Action) : DoubleCursor{
        xValue.addListener(action)
        return this
    }

    /**
     * Adds a listener of the y value.
     * @param key The key associated to the given action
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addYValueListener(key : Any?, action : Action) : DoubleCursor{
        yValue.addListener(key, action)
        return this
    }

    /**
     * Adds a listener of the y value.
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addYValueListener(action : Action) : DoubleCursor{
        yValue.addListener(action)
        return this
    }

    /**
     * Removes the listener ox the x value associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeXValueListener(key : Any?) : DoubleCursor{
        xValue.removeListener(key)
        return this
    }

    /**
     * Removes the listener ox the y value associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeYValueListener(key : Any?) : DoubleCursor{
        yValue.removeListener(key)
        return this
    }

    /**
     * Sets the image of the cursor
     * @return this
     * @see GraphicAction
     * @since LLayout 1
     */
    fun setCursorImage(image : GraphicAction) : DoubleCursor{
        cursor.addGraphicAction(image, this)
        return this
    }

    /**
     * Sets the background image.
     * @return this
     * @see GraphicAction
     * @since LLayout 1
     */
    fun setBackground(background : GraphicAction) : DoubleCursor{
        core.addGraphicAction(background, CURSOR_BACKGROUND_KEY)
        return this
    }

    /**
     * The selected x value.
     * @since LLayout 1
     */
    fun xValue() : Double = xValue.value

    /**
     * The selected y value.
     * @since LLayout 1
     */
    fun yValue() : Double = yValue.value

    /**
     * Moves the cursor along the given direction.
     * @return this
     * @since LLayout 1
     */
    fun moveCursorAlong(x : Int, y : Int) : DoubleCursor{
        cursor.moveAlong(x, y)
        return this
    }

    /**
     * Moves the cursor in the x direction by the given amount.
     * @return thir
     * @since LLayout 1
     */
    fun moveCursorAlongX(x : Int) : DoubleCursor{
        cursor.moveAlongX(x)
        return this
    }

    /**
     * Moves the cursor along the y direction by the given amount.
     * @return this
     * @since LLayout 1
     */
    fun moveCursorAlongY(y : Int) : DoubleCursor{
        cursor.moveAlongY(y)
        return this
    }

    /**
     * Sets the x value.
     * @since LLayout 1
     */
    private fun setXValue(x : Double){
        xValue.value = roundedX(x)
    }

    /**
     * Sets the y value.
     * @since LLayout 1
     */
    private fun setYValue(y : Double){
        yValue.value = roundedY(y)
    }

    /**
     * The minimal x value.
     * @since LLayout 1
     */
    private fun minimalX() : Double = minimalXValue.value

    /**
     * The maximal x value.
     * @since LLayout 1
     */
    private fun maximalX() : Double = maximalXValue.value

    /**
     * The minimal y value.
     * @since LLayout 1
     */
    private fun minimalY() : Double = minimalYValue.value

    /**
     * The maximal y value.
     * @since LLayout 1
     */
    private fun maximalY() : Double = maximalYValue.value

    /**
     * The x range.
     * @since LLayout 1
     */
    private fun xRange() : Double = maximalX() - minimalX()

    /**
     * The y range.
     * @since LLayout 1
     */
    private fun yRange() : Double = maximalY() - minimalY()

    /**
     * The x precision.
     * @since LLayout 1
     */
    private fun xPrecision() : Double = xPrecision.value

    /**
     * The y precisiom.
     * @since LLayout 1
     */
    private fun yPrecision() : Double = yPrecision.value

    /**
     * True if the x bounds are inverted.
     * @since LLayout 1
     */
    private fun invertedXBounds() : Boolean = minimalX() > maximalX()

    /**
     * True if the y bounds are inverted.
     * @since LLayout 1
     */
    private fun invertedYBounds() : Boolean = minimalY() > maximalY()

    /**
     * True of the x precision is not zero
     * @since LLayout 1
     */
    private fun finiteXPrecision() : Boolean = xPrecision() != 0.0

    /**
     * True if the y precision is not zero.
     * @since LLayout 1
     */
    private fun finiteYPrecision() : Boolean = yPrecision() != 0.0

    /**
     * Swap the x bounds.
     * @since LLayout 1
     */
    private fun swapXBounds(){
        val temporary : Double = maximalX()
        maximalXValue.value = minimalX()
        minimalXValue.value = temporary
    }

    /**
     * Swaps the y bounds.
     * @since LLayout 1
     */
    private fun swapYBounds(){
        val temporary : Double = maximalY()
        maximalYValue.value = minimalY()
        minimalYValue.value = temporary
    }

    /**
     * Check that the x bounds are well ordrered.
     * @since LLayout 1
     */
    private fun checkXBounds(){
        if(invertedXBounds()) swapXBounds()
    }

    /**
     * Checks that the y bounds are well ordered.
     * @since LLayout 1
     */
    private fun checkYBounds(){
        if(invertedYBounds()) swapYBounds()
    }

    /**
     * Returns the given value rounded to the x precision.
     * @since LLayout 1
     */
    private fun roundedX(x : Double) : Double{
        return if(finiteXPrecision()){
            minimalX() + xPrecision() * ceil( ( x - minimalX() - xPrecision() / 2 ) / xPrecision() )
        }else x
    }

    /**
     * Returns the given value rounded to the y precision.
     * @since LLayout 1
     */
    private fun roundedY(y : Double) : Double{
        return if(finiteYPrecision()){
            minimalY() + yPrecision() * ceil( ( y - minimalY() - yPrecision() / 2 ) / yPrecision() )
        }else y
    }

    /**
     * True if the cursor is too far up.
     * @since LLayout 1
     */
    private fun isTooFarUp() : Boolean = cursor.upSideY() < 0

    /**
     * True if the cursor is too far down.
     * @since LLayout 1
     */
    private fun isTooFarDown() : Boolean = cursor.downSideY() > height()

    /**
     * True if the cursor is too far left.
     * @since LLayout 1
     */
    private fun isTooFarLeft() : Boolean = cursor.leftSideX() < 0

    /**
     * True if the cursor is too far right.
     * @since LLayout 1
     */
    private fun isTooFarRight() : Boolean = cursor.rightSideX() > width()

    /**
     * Corrects the cursor position if it's too far up.
     * @since LLayout 1
     */
    private fun correctUp(){
        cursor.setY(cursor.height() / 2)
    }

    /**
     * Corrects the cursor position if it's too far down.
     * @since LLayout 1
     */
    private fun correctDown(){
        cursor.setY(height() - cursor.height() / 2)
    }

    /**
     * Corrects the cursor position if it's too far left.
     * @since LLayout 1
     */
    private fun correctLeft(){
        cursor.setX(cursor.width() / 2)
    }

    /**
     * Corrects the cursor position if it's too far right.
     * @since LLayout 1
     */
    private fun correctRight(){
        cursor.setX(width() - cursor.width() / 2)
    }

    /**
     * Corrects the x position of the cursor.
     * @since LLayout 1
     */
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

    /**
     * Corrects the y position of the cursor.
     * @since LLayout 1
     */
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

    /**
     * Updates the x value with the x position of the cursor.
     * @since LLayout 1
     */
    private fun updateXValue(){
        if(cursor.width() >= width()){
            setXValue(minimalX())
        }else{
            setXValue(minimalX() + ( cursor.leftSideX().toDouble() / (width() - cursor.width()) ) * xRange() )
        }
    }

    /**
     * Updates the y value with the y position of the cursor.
     * @since LLayout 1
     */
    private fun updateYValue(){
        if(cursor.height() >= height()){
            setYValue(minimalY())
        }else{
            setYValue(minimalY() + ( (height() - cursor.downSideY().toDouble()) / (height() - cursor.height()) ) * yRange() )
        }
    }

    /**
     * When the component is resized, the cursor is moved such that it conserves the values.
     * @since LLayout 1
     */
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