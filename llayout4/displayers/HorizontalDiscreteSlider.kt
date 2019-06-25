package llayout4.displayers

import kotlin.math.round

/**
 * A slider that represents discrete values and that appears horizontally on the screen.
 * @see AbstractDiscreteSlider
 * @since LLayout 4
 */
class HorizontalDiscreteSlider : AbstractDiscreteSlider {

    init{
        slider.setY(0.5)
        slider.alignLeftTo(0)
        addHeightListener { slider.setHeight(height()) }
        slider.setOnMouseDraggedAction { e -> tryToMoveSlider(e.x) }
    }

    constructor(width : Int, height : Int, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setWidth(width() / numberOfOptions)
        slider.setHeight(height())
        addWidthListener { slider.setWidth(width() / numberOfOptions) }
    }

    constructor(width : Double, height : Int, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setWidth(width() / numberOfOptions)
        slider.setHeight(height())
        addWidthListener { slider.setWidth(width() / numberOfOptions) }
    }

    constructor(width : Int, height : Double, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setWidth(width() / numberOfOptions)
        slider.setHeight(height())
        addWidthListener { slider.setWidth(width() / numberOfOptions) }
    }

    constructor(width : Double, height : Double, numberOfOptions : Int) : super(width, height, numberOfOptions){
        slider.setWidth(width() / numberOfOptions)
        slider.setHeight(height())
        addWidthListener { slider.setWidth(width() / numberOfOptions) }
    }

    /**
     * Returns the value corresponding to the given left side position of the sliding thing.
     * @since LLayout 4
     */
    private fun valueCorrespondingTo(leftSide : Int) : Int = round(numberOfOptions() * leftSide.toDouble() / width()).toInt()

    /**
     * Moves the slider from the given distance.
     * @since LLayout 4
     */
    private fun moveSliderToValue(newValue : Int) = slider.setX(positionCorrespondingToValue(newValue))

    /**
     * Tries to move the slider from the given distance.
     * @since LLayout 4
     */
    private fun tryToMoveSlider(deltaX : Int){
        val newValue : Int = valueCorrespondingTo(newLeftSideX(deltaX))
        if(newValue != value() && isInRange(newValue)){
            moveSliderToValue(newValue)
            setValue(newValue)
        }
    }

    /**
     * Returns the position of the sliding thing if it was moved by the given delta.
     * @since LLayout 4
     */
    private fun newLeftSideX(deltaX : Int) : Int{
        return slider.leftSideX() + deltaX
    }

    /**
     * Returns the position of the sliding thing at which the slider value would be the given one.
     * @since LLayout 4
     */
    private fun positionCorrespondingToValue(newValue : Int) : Int = slider.width() / 2 + newValue * slider.width()

}