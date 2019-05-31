package llayout.displayers

import java.awt.Graphics

class HorizontalDoubleSlider : AbstractDoubleSlider {

    init{
        addHeightListener{ slider.setHeight(height()) }
        addWidthListener{
            if(slider.width() > width()){
                slider.setWidth(width())
            }else{
                slider.setWidth(MINIMAL_SLIDER_SIZE)
            }
        }
        slider.setY(0.5)
        slider.setOnMouseDraggedAction { e -> slider.setX(slider.leftSideX() + e.x) }
        slider.addXListener{ correctSliderPosition() }
        slider.addXListener{ updateValue() }
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    private fun isTooFarLeft() : Boolean = slider.leftSideX() < 0

    private fun isTooFarRight() : Boolean = slider.rightSideX() > width()

    private fun correctLeft(){
        slider.setX(slider.width() / 2)
    }

    private fun correctRight(){
        slider.setX(width() - slider.width() / 2)
    }

    override fun correctSliderPosition(){
        if(slider.width() < width()){
            if(isTooFarLeft()){
                correctLeft()
            }else if(isTooFarRight()){
                correctRight()
            }
        }else{
            slider.setX(width() / 2)
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
            setValue(minimalValue() + ( slider.leftSideX().toDouble() / (width() - slider.width()) ) * range() )
        }
    }

    override fun conserveSliderPositionOnResize(){
        val proportion : Double = (value() - minimalValue()) / range()
        val newX : Int = (proportion * width()).toInt()
        val previousValue : Double = value()
        slider.setX(newX)
        setValue(previousValue)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        conserveSliderPositionOnResize()
    }

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        slider.setHeight(height())
        slider.setWidth(MINIMAL_SLIDER_SIZE)
        conserveSliderPositionOnResize()
        correctSliderPosition()
    }

}