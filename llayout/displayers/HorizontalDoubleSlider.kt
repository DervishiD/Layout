package llayout.displayers

import java.awt.Graphics

class HorizontalDoubleSlider : AbstractDoubleSlider {

    init{
        addYListener{ slider.setCenterY(centerY()) }
        addHeightListener{ slider.setHeight(height()) }
        addWidthListener{
            if(slider.width() > width()){
                slider.setWidth(width())
            }else{
                slider.setWidth(MINIMAL_SLIDER_SIZE)
            }
        }
        slider.setOnMouseDraggedAction { e -> slider.moveTo(slider.leftSideX() + e.x, slider.centerY()) }
        slider.addXListener{ updateValue() }
        slider.addXListener{ correctSliderPosition() }
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    private fun isTooFarLeft() : Boolean = slider.leftSideX() < leftSideX()

    private fun isTooFarRight() : Boolean = slider.rightSideX() > rightSideX()

    private fun correctLeft(){
        slider.setCenterX(leftSideX() + slider.width() / 2)
    }

    private fun correctRight(){
        slider.setCenterX(rightSideX() - slider.width() / 2)
    }

    override fun correctSliderPosition(){
        if(slider.width() < width()){
            if(isTooFarLeft()){
                correctLeft()
            }else if(isTooFarRight()){
                correctRight()
            }
        }else{
            slider.setCenterX(centerX())
        }
    }

    override fun updateValue(){
        if(slider.width() >= width()){
            setValue(minimalValue())
        }else{
            /*
             * set value to minimum + (x - w/2)/(W - w) * range()
             * left x bounded by 0 and W - w
             * proportion is left x / W - w
             * minimal value + proportion * range
             */
            setValue(minimalValue() + ( (slider.leftSideX().toDouble() - leftSideX()) / (width() - slider.width()) ) * range() )
        }
    }

    override fun conserveSliderPositionOnResize(){
        val proportion : Double = (value() - minimalValue()) / range()
        val newX : Int = leftSideX() + (proportion * width()).toInt()
        val previousValue : Double = value()
        slider.setCenterX(newX)
        setValue(previousValue)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): HorizontalDoubleSlider {
        super.updateRelativeValues(frameWidth, frameHeight)
        conserveSliderPositionOnResize()
        return this
    }

    override fun loadParameters(g: Graphics) {
        slider.setHeight(height())
        slider.setCenterY(centerY())
        slider.setWidth(MINIMAL_SLIDER_SIZE)
        conserveSliderPositionOnResize()
    }

}