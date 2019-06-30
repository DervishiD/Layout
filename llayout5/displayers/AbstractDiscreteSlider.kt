package llayout5.displayers

import java.awt.Color
import llayout5.Action
import llayout5.DEFAULT_COLOR
import llayout5.GraphicAction
import llayout5.utilities.LObservable
import java.awt.Graphics

/**
 * An abstraction that represents sliders that only have discrete values.
 * The value of the slider is given by a positive integer.
 * @see ResizableDisplayer
 * @since LLayout 4
 */
abstract class AbstractDiscreteSlider : ResizableDisplayer {

    private companion object{

        /**
         * The color of the default background of the sliding thing.
         * @since LLAyout 4
         */
        private val DEFAULT_SLIDER_BACKGROUND_COLOR : Color = Color(220, 220, 220)

        /**
         * The color of the lines of the default background.
         * @since LLayout 4
         */
        private val DEFAULT_LINE_COLOR : Color = DEFAULT_COLOR

        /**
         * The thickness of the lines in the default background.
         * @since LLayout 4
         */
        private const val DEFAULT_LINE_THICKNESS : Int = 2

        /**
         * The key used to recognize the background of a slider.
         * @since LLayout 4
         */
        private const val CORE_BACKGROUND_KEY : String = "CORE BACKGROUND KEY"

        /**
         * The default background of the slider.
         * @see GraphicAction
         * @since LLayout 4
         */
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = DEFAULT_LINE_COLOR
            g.fillRect(0, 0, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(0, 0, DEFAULT_LINE_THICKNESS, h)
            g.fillRect(0, h - DEFAULT_LINE_THICKNESS, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(w - DEFAULT_LINE_THICKNESS, 0, DEFAULT_LINE_THICKNESS, h)
        }

        /**
         * The default background of the sliding thing.
         * @see GraphicAction
         * @since LLayout 4
         */
        private val DEFAULT_SLIDER_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
            g.color = DEFAULT_SLIDER_BACKGROUND_COLOR
            g.fillRect(0, 0, w, h)
            g.color = DEFAULT_LINE_COLOR
            g.fillRect(0, 0, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(0, 0, DEFAULT_LINE_THICKNESS, h)
            g.fillRect(0, h - DEFAULT_LINE_THICKNESS, w, DEFAULT_LINE_THICKNESS)
            g.fillRect(w - DEFAULT_LINE_THICKNESS, 0, DEFAULT_LINE_THICKNESS, h)
        }

    }

    /**
     * The number of options that this slider can show.
     * @since LLayout 4
     */
    private var numberOfOptions : Int

    /**
     * The value currently selected by the slider.
     * @see LObservable
     * @since LLayout 4
     */
    private val currentValue : LObservable<Int> = LObservable(0)

    /**
     * The sliding part.
     * @see CanvasDisplayer
     * @since LLayout 4
     */
    protected val slider : CanvasDisplayer = CanvasDisplayer()

    init{
        setBackground(DEFAULT_BACKGROUND)
        setSliderImage(DEFAULT_SLIDER_BACKGROUND)
        core.add(slider)
    }

    protected constructor(width : Int, height : Int, numberOfOptions : Int) : super(width, height){
        this.numberOfOptions = numberOfOptions
    }

    protected constructor(width : Double, height : Int, numberOfOptions : Int) : super(width, height){
        this.numberOfOptions = numberOfOptions
    }

    protected constructor(width : Int, height : Double, numberOfOptions : Int) : super(width, height){
        this.numberOfOptions = numberOfOptions
    }

    protected constructor(width : Double, height : Double, numberOfOptions : Int) : super(width, height){
        this.numberOfOptions = numberOfOptions
    }

    /**
     * Returns the value selected by this slider, as a positive integer.
     * The value is in the interval 0 (inclusive) to the number of options (given in the constructor) - 1 (inclusive).
     * @see currentValue
     * @since LLayout 4
     */
    fun value() : Int = currentValue.value

    /**
     * Sets the value of this slider to the given one.
     * @throws IllegalArgumentException If the value is not in the interval 0 (inclusive) to the number of options - 1 (inclusive).
     * @return this
     * @since LLayout 4
     */
    fun setValue(value : Int) : AbstractDiscreteSlider{
        currentValue.value = value
        return this
    }

    /**
     * Adds a listener to the value of this slider.
     * @param key A key that identifies the given action.
     * @param action The executed action.
     * @return this
     * @since LLayout 4
     */
    fun addValueListener(key : Any?, action : Action) : AbstractDiscreteSlider{
        currentValue.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the value of this slider.
     * @param action The executed action.
     * @return this
     * @since LLayout 4
     */
    fun addValueListener(action : Action) : AbstractDiscreteSlider{
        currentValue.addListener(action)
        return this
    }

    /**
     * Removes the listener associated to the given key.
     * @return this
     * @since LLayout 4
     */
    fun removeValueListener(key : Any?) : AbstractDiscreteSlider{
        currentValue.removeListener(key)
        return this
    }

    /**
     * Sets an image to the the sliding thing.
     * @return this
     * @since LLayout 4
     */
    fun setSliderImage(image : GraphicAction) : AbstractDiscreteSlider{
        slider.addGraphicAction(image, this)
        return this
    }

    /**
     * Sets a new background to the slider.
     * @return this
     * @since LLayout 1
     */
    fun setBackground(background : GraphicAction) : AbstractDiscreteSlider{
        core.addGraphicAction(background, CORE_BACKGROUND_KEY)
        return this
    }

    /**
     * Returns the number of options this slider can choose from.
     * @since LLayout 4
     */
    protected fun numberOfOptions() : Int = numberOfOptions

    /**
     * Returns true if the value can be taken by the slider.
     * @since LLayout 4
     */
    protected fun isInRange(value : Int) : Boolean = value in 0 until numberOfOptions

}