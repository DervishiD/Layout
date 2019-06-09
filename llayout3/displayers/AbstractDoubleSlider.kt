package llayout3.displayers

import llayout3.Action
import llayout3.DEFAULT_COLOR
import llayout3.GraphicAction
import llayout3.utilities.LObservable
import java.awt.Color
import java.awt.Graphics
import kotlin.math.ceil

/**
 * An abstraction for sliders that take double values.
 * @since LLayout 1
 */
abstract class AbstractDoubleSlider : ResizableDisplayer {

    protected companion object{

        /**
         * The default precision of the slider.
         * @since LLayout 1
         */
        private const val DEFAULT_PRECISION : Double = 0.1

        /**
         * The size of the actual sliding piece.
         * @since LLayout 1
         */
        @JvmStatic protected val MINIMAL_SLIDER_SIZE : Int = 50

        /**
         * The default background of the object.
         * @see GraphicAction
         * @since LLayout 1
         */
        @JvmStatic protected val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lineThickness : Int = 2
            g.color = DEFAULT_COLOR
            g.fillRect(0, 0, lineThickness, h)
            g.fillRect(0, 0, w, lineThickness)
            g.fillRect(0, h - lineThickness, w, lineThickness)
            g.fillRect(w - lineThickness, 0, lineThickness, h)
        }}

        /**
         * The default background of the actual sliding thing.
         * @see GraphicAction
         * @since LLayout 1
         */
        @JvmStatic protected val DEFAULT_SLIDER_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
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
         * A key used to recognize the background image of the slider.
         * @since LLayout 1
         */
        private const val CORE_BACKGROUND_KEY : String = "DEFAULT CORE BACKGROUND"

    }

    /**
     * The minimal value that the slider can slide to.
     * @see LObservable
     * @since LLayout 1
     */
    private var minimalValue : LObservable<Double> = LObservable(0.0)

    /**
     * The maximal value that the slider can slide to.
     * @see LObservable
     * @since LLayout 1
     */
    private var maximalValue : LObservable<Double> = LObservable(10.0)

    /**
     * The current value of this slider.
     * @see LObservable
     * @since LLayout 1
     */
    private var currentValue : LObservable<Double> = LObservable(minimalValue.value)

    /**
     * The precision of this slider.
     * @see LObservable
     * @since LLayout 1
     */
    private var precision : LObservable<Double> = LObservable(DEFAULT_PRECISION)

    /**
     * The actual sliding thing.
     * @see CanvasDisplayer
     * @since LLayout 1
     */
    protected val slider : CanvasDisplayer = CanvasDisplayer()

    /*
     * Adds listeners to the LObservables declared above.
     */
    init{
        minimalValue.addListener{
            checkBounds()
            if(minimalValue() > value()) setValue(minimalValue())
        }
        maximalValue.addListener{
            checkBounds()
            if(maximalValue() < value()) setValue(maximalValue())
        }
        precision.addListener{ setValue(value()) }
        setSliderImage(DEFAULT_SLIDER_BACKGROUND)
        core.addGraphicAction(DEFAULT_BACKGROUND, CORE_BACKGROUND_KEY)
        core.add(slider)
    }

    protected constructor(width : Int, height : Int) : super(width, height)

    protected constructor(width : Double, height : Int) : super(width, height)

    protected constructor(width : Int, height : Double) : super(width, height)

    protected constructor(width : Double, height : Double) : super(width, height)

    /**
     * Sets the minimal value that this slider can slide to.
     * @return this
     * @since LLayout 1
     */
    fun setMinimum(minimum : Double) : AbstractDoubleSlider{
        minimalValue.value = minimum
        return this
    }

    /**
     * Sets the maximal value this slider can slide to.
     * @return this
     * @since LLayout 1
     */
    fun setMaximum(maximum : Double) : AbstractDoubleSlider{
        maximalValue.value = maximum
        return this
    }

    /**
     * Sets the minimal value that this slider can slide to.
     * @return this
     * @since LLayout 1
     */
    fun setMinimum(minimum : Int) : AbstractDoubleSlider = setMinimum(minimum.toDouble())

    /**
     * Sets the maximal value this slider can slide to.
     * @return this
     * @since LLayout 1
     */
    fun setMaximum(maximum : Int) : AbstractDoubleSlider = setMaximum(maximum.toDouble())

    /**
     * Sets the precision of this slider.
     * @return this
     * @since LLayout 1
     */
    fun setPrecision(precision : Double) : AbstractDoubleSlider{
        if(precision < 0) {
            throw IllegalArgumentException("Negative precision $precision in AbstractDoubleSlider.setPrecision")
        }
        this.precision.value = if(precision > range()) range() else precision
        return this
    }

    /**
     * Sets the precision of this slider.
     * @return this
     * @since LLayout 1
     */
    fun setPrecision(precision : Int) : AbstractDoubleSlider = setPrecision(precision.toDouble())

    /**
     * Adds a listener to the value of this slider.
     * @param key A key that identifies the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addValueListener(key : Any?, action : Action) : AbstractDoubleSlider{
        currentValue.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the value of this slider.
     * @param action The executed action.
     * @return this
     * @since LLayout 1
     */
    fun addValueListener(action : Action) : AbstractDoubleSlider{
        currentValue.addListener(action)
        return this
    }

    /**
     * Removes the listener associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeValueListener(key : Any?) : AbstractDoubleSlider{
        currentValue.removeListener(key)
        return this
    }

    /**
     * Sets a new background to the slider.
     * @return this
     * @since LLayout 1
     */
    fun setBackground(background : GraphicAction) : AbstractDoubleSlider{
        core.addGraphicAction(background, CORE_BACKGROUND_KEY)
        return this
    }

    /**
     * Sets an image to the the sliding thing.
     * @return this
     * @since LLayout 1
     */
    fun setSliderImage(image : GraphicAction) : AbstractDoubleSlider{
        slider.addGraphicAction(image, this)
        return this
    }

    /**
     * Returns the value of [minimalValue].
     * @since LLayout 1
     */
    protected fun minimalValue() : Double = minimalValue.value

    /**
     * Returns the value of [maximalValue]
     * @since LLayout 1
     */
    private fun maximalValue() : Double = maximalValue.value

    /**
     * Detects if the bounds of the slider value are inverted.
     * @since LLayout 1
     */
    private fun invertedBounds() : Boolean = minimalValue() > maximalValue()

    /**
     * Swaps the lower and upper bounds of the slider value.
     * @since LLayout 1
     */
    private fun swapBounds(){
        val temporary : Double = minimalValue()
        minimalValue.value = maximalValue()
        maximalValue.value = temporary
    }

    /**
     * Makes sure that the bounds are in increasing order.
     * @since LLayout 1
     */
    private fun checkBounds(){
        if(invertedBounds()) swapBounds()
    }

    /**
     * The range of the values of the slider.
     * @since LLayout 1
     */
    protected fun range() : Double = maximalValue() - minimalValue()

    /**
     * The precision of the slider.
     * @since LLayout 1
     */
    private fun precision() : Double = precision.value

    /**
     * Returns the given value rounded to the slider's precision.
     * @since LLayout 1
     */
    private fun rounded(value : Double) : Double{
        return if(finitePrecision()){
            minimalValue() + precision() * ceil( ( value - minimalValue() - precision() / 2 ) / precision() )
        }else{
            value
        }
    }

    /**
     * The precision is finite if it is not zero.
     * @since LLayout 1
     */
    private fun finitePrecision() : Boolean = precision() != 0.0

    /**
     * Returns the current value of this slider.
     * @since LLayout 1
     */
    fun value() : Double = currentValue.value

    /**
     * Sets the value of this slider to the given one.
     * @since LLayout 1
     */
    protected fun setValue(value : Double){
        currentValue.value = rounded(value)
    }

    /**
     * Corrects the position of the sliding thing such that it stays inside the object.
     * @since LLayout 1
     */
    protected abstract fun correctSliderPosition()

    /**
     * Updates the value when the sliding thing moves.
     * @since LLayout 1
     */
    protected abstract fun updateValue()

    /**
     * When the component is resized, the sliding thing must be moved such that it still indicates the same value.
     * @since LLayout 1
     */
    protected abstract fun conserveSliderPositionOnResize()

}