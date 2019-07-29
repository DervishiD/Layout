package llayout.displayers

import kotlin.math.round

/**
 * A slider that represents discrete values and that appears vertically on the screen.
 * @see AbstractDiscreteSlider
 * @since LLayout 4
 */
class VerticalDiscreteSlider : AbstractDiscreteSlider {

    init{
        slider.setX(0.5)
        slider.alignBottomTo(1.0)
        addWidthListener { slider.setWidth(width()) }
        slider.setOnMouseDraggedAction { e -> tryToMoveSlider(e.y) }
    }

    constructor(width : Int, height : Int, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setHeight(height() / numberOfOptions)
        slider.setWidth(width())
        addHeightListener { slider.setHeight(height() / numberOfOptions) }
    }

    constructor(width : Double, height : Int, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setHeight(height() / numberOfOptions)
        slider.setWidth(width())
        addHeightListener { slider.setHeight(height() / numberOfOptions) }
    }

    constructor(width : Int, height : Double, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setHeight(height() / numberOfOptions)
        slider.setWidth(width())
        addHeightListener { slider.setHeight(height() / numberOfOptions) }
    }

    constructor(width : Double, height : Double, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setHeight(height() / numberOfOptions)
        slider.setWidth(width())
        addHeightListener { slider.setHeight(height() / numberOfOptions) }
    }

    /**
     * Returns the value corresponding to the given down side position of the sliding thing.
     * @since LLayout 4
     */
    private fun valueCorrespondingTo(downSide : Int) : Int = round(numberOfOptions() * (height() - downSide.toDouble()) / height()).toInt()

    /**
     * Moves the slider from the given distance.
     * @since LLayout 4
     */
    private fun moveSliderToValue(newValue : Int) = slider.setY(positionCorrespondingToValue(newValue))

    /**
     * Tries to move the slider from the given distance.
     * @since LLayout 4
     */
    private fun tryToMoveSlider(deltaY : Int){
        val newValue : Int = valueCorrespondingTo(newDownSideY(deltaY))
        if(newValue != value() && isInRange(newValue)){
            moveSliderToValue(newValue)
            setValue(newValue)
        }
    }

    /**
     * Returns the position of the sliding thing if it was moved by the given delta.
     * @since LLayout 4
     */
    private fun newDownSideY(deltaY : Int) : Int{
        return slider.downSideY() + deltaY
    }

    /**
     * Returns the position of the sliding thing at which the slider value would be the given one.
     * @since LLayout 4
     */
    private fun positionCorrespondingToValue(newValue : Int) : Int = height() - slider.height() / 2 - newValue * slider.height()

}