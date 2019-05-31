package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.utilities.LObservable
import java.awt.Color
import java.awt.Graphics
import kotlin.math.ceil

abstract class AbstractDoubleSlider : ResizableDisplayer {

    protected companion object{
        private const val DEFAULT_PRECISION : Double = 0.1
        @JvmStatic protected val MINIMAL_SLIDER_SIZE : Int = 50
        @JvmStatic protected val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lineThickness : Int = 2
            g.color = DEFAULT_COLOR
            g.fillRect(0, 0, lineThickness, h)
            g.fillRect(0, 0, w, lineThickness)
            g.fillRect(0, h - lineThickness, w, lineThickness)
            g.fillRect(w - lineThickness, 0, lineThickness, h)
        }}
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
        private const val CORE_BACKGROUND_KEY : String = "DEFAULT CORE BACKGROUND"
    }

    private var minimalValue : LObservable<Double> = LObservable(0.0)

    private var maximalValue : LObservable<Double> = LObservable(10.0)

    private var currentValue : LObservable<Double> = LObservable(minimalValue.value)

    private var precision : LObservable<Double> = LObservable(DEFAULT_PRECISION)

    protected val slider : CanvasDisplayer = CanvasDisplayer()

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

    fun setMinimum(minimum : Double) : AbstractDoubleSlider{
        minimalValue.value = minimum
        return this
    }

    fun setMaximum(maximum : Double) : AbstractDoubleSlider{
        maximalValue.value = maximum
        return this
    }

    fun setMinimum(minimum : Int) : AbstractDoubleSlider = setMinimum(minimum.toDouble())

    fun setMaximum(maximum : Int) : AbstractDoubleSlider = setMaximum(maximum.toDouble())

    fun setPrecision(precision : Double) : AbstractDoubleSlider{
        if(precision < 0) {
            throw IllegalArgumentException("Negative precision $precision in AbstractDoubleSlider.setPrecision")
        }
        this.precision.value = if(precision > range()) range() else precision
        return this
    }

    fun setPrecision(precision : Int) : AbstractDoubleSlider = setPrecision(precision.toDouble())

    fun addValueListener(key : Any?, action : Action) : AbstractDoubleSlider{
        currentValue.addListener(key, action)
        return this
    }

    fun addValueListener(action : Action) : AbstractDoubleSlider{
        currentValue.addListener(action)
        return this
    }

    fun removeValueListener(key : Any?) : AbstractDoubleSlider{
        currentValue.removeListener(key)
        return this
    }

    fun setBackground(background : GraphicAction) : AbstractDoubleSlider{
        core.addGraphicAction(background, CORE_BACKGROUND_KEY)
        return this
    }

    fun setSliderImage(image : GraphicAction) : AbstractDoubleSlider{
        slider.addGraphicAction(image, this)
        return this
    }

    protected fun minimalValue() : Double = minimalValue.value

    private fun maximalValue() : Double = maximalValue.value

    private fun invertedBounds() : Boolean = minimalValue() > maximalValue()

    private fun swapBounds(){
        val temporary : Double = minimalValue()
        minimalValue.value = maximalValue()
        maximalValue.value = temporary
    }

    private fun checkBounds(){
        if(invertedBounds()) swapBounds()
    }

    protected fun range() : Double = maximalValue() - minimalValue()

    private fun precision() : Double = precision.value

    private fun rounded(value : Double) : Double{
        return if(finitePrecision()){
            minimalValue() + precision() * ceil( ( value - minimalValue() - precision() / 2 ) / precision() )
        }else{
            value
        }
    }

    private fun finitePrecision() : Boolean = precision() != 0.0

    fun value() : Double = currentValue.value

    protected fun setValue(value : Double){
        currentValue.value = rounded(value)
    }

    protected abstract fun correctSliderPosition()

    protected abstract fun updateValue()

    protected abstract fun conserveSliderPositionOnResize()

}