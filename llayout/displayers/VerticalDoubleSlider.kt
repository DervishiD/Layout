package llayout.displayers

import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class VerticalDoubleSlider : AbstractDoubleSlider {

    init{
        addXListener{ slider.setx(centerX()) }
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

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y, width, height)

    private fun isTooFarUp() : Boolean = slider.upSideY() < upSideY()

    private fun isTooFarDown() : Boolean = slider.downSideY() > downSideY()

    private fun correctUp(){
        slider.sety(upSideY() + slider.height() / 2)
    }

    private fun correctDown(){
        slider.sety(downSideY() - slider.height() / 2)
    }

    override fun correctSliderPosition(){
        if(slider.height() < height()){
            if(isTooFarDown()){
                correctDown()
            }else if(isTooFarUp()){
                correctUp()
            }
        }else{
            slider.sety(centerY())
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
        slider.sety(newY)
        setValue(previousValue)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): VerticalDoubleSlider {
        super.updateRelativeValues(frameWidth, frameHeight)
        conserveSliderPositionOnResize()
        return this
    }

    override fun loadParameters(g: Graphics) {
        slider.setWidth(width())
        slider.setx(centerX())
        slider.setHeight(MINIMAL_SLIDER_SIZE)
        conserveSliderPositionOnResize()
    }

}