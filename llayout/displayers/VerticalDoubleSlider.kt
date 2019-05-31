package llayout.displayers

import java.awt.Graphics

class VerticalDoubleSlider : AbstractDoubleSlider {

    init{
        addWidthListener{ slider.setWidth(width()) }
        addHeightListener{
            if(slider.height() > height()){
                slider.setHeight(height())
            }else{
                slider.setHeight(MINIMAL_SLIDER_SIZE)
            }
        }
        slider.setX(0.5)
        slider.setOnMouseDraggedAction { e -> slider.setY(slider.upSideY() + e.y)}
        slider.addYListener{ correctSliderPosition() }
        slider.addYListener{ updateValue() }
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    private fun isTooFarUp() : Boolean = slider.upSideY() < 0

    private fun isTooFarDown() : Boolean = slider.downSideY() > height()

    private fun correctUp(){
        slider.setY(slider.height() / 2)
    }

    private fun correctDown(){
        slider.setY(height() - slider.height() / 2)
    }

    override fun correctSliderPosition(){
        if(slider.height() < height()){
            if(isTooFarDown()){
                correctDown()
            }else if(isTooFarUp()){
                correctUp()
            }
        }else{
            slider.setY(height() / 2)
        }
    }

    override fun updateValue(){
        if(slider.height() >= height()){
            setValue(minimalValue())
        }else{
            /*
             * set value to minimum + (x - w/2)/(W - w) * range()
             * left x bounded by 0 and W - w
             * proportion is left x / W - w
             * minimal value + proportion * range
             */
            setValue(minimalValue() + ( (height() - slider.downSideY().toDouble()) / (height() - slider.height()) ) * range() )
        }
    }

    override fun conserveSliderPositionOnResize(){
        val proportion : Double = (value() - minimalValue()) / range()
        val newY : Int = (height() * (1 - proportion)).toInt()
        val previousValue : Double = value()
        slider.setY(newY)
        setValue(previousValue)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        conserveSliderPositionOnResize()
    }

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        slider.setWidth(width())
        slider.setHeight(MINIMAL_SLIDER_SIZE)
        conserveSliderPositionOnResize()
        correctSliderPosition()
    }

}