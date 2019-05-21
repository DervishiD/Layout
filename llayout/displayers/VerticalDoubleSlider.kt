package llayout.displayers

import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class VerticalDoubleSlider : AbstractDoubleSlider {

    init{
        addXListener{ slider.setCenterX(centerX()) }
        addWidthListener{ slider.setWidth(width()) }
        addHeightListener{
            if(slider.height() > height()){
                slider.setHeight(height())
            }else{
                slider.setHeight(MINIMAL_SLIDER_SIZE)
            }
        }
        slider.setOnMouseDraggedAction { e -> slider.moveTo(slider.centerX(), slider.upSideY() + e.y)}
        slider.addYListener{ updateValue() }
        slider.addYListener{ correctSliderPosition() }
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    private fun isTooFarUp() : Boolean = slider.upSideY() < upSideY()

    private fun isTooFarDown() : Boolean = slider.downSideY() > downSideY()

    private fun correctUp(){
        slider.setCenterY(upSideY() + slider.height() / 2)
    }

    private fun correctDown(){
        slider.setCenterY(downSideY() - slider.height() / 2)
    }

    override fun correctSliderPosition(){
        if(slider.height() < height()){
            if(isTooFarDown()){
                correctDown()
            }else if(isTooFarUp()){
                correctUp()
            }
        }else{
            slider.setCenterY(centerY())
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
            setValue(minimalValue() + ( (downSideY() - slider.downSideY().toDouble()) / (height() - slider.height()) ) * range() )
        }
    }

    override fun conserveSliderPositionOnResize(){
        val proportion : Double = (value() - minimalValue()) / range()
        val newY : Int = downSideY() - (proportion * height()).toInt()
        val previousValue : Double = value()
        slider.setCenterY(newY)
        setValue(previousValue)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): VerticalDoubleSlider {
        super.updateRelativeValues(frameWidth, frameHeight)
        conserveSliderPositionOnResize()
        return this
    }

    override fun loadParameters(g: Graphics) {
        slider.setWidth(width())
        slider.setCenterX(centerX())
        slider.setHeight(MINIMAL_SLIDER_SIZE)
        conserveSliderPositionOnResize()
    }

}