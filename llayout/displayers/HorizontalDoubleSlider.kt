package llayout.displayers

import llayout.frame.LMouse
import java.awt.Graphics

class HorizontalDoubleSlider : AbstractDoubleSlider {

    init{
        addYListener{ slider.sety(centerY()) }
        addHeightListener{ slider.setHeight(height()) }
        addWidthListener{
            if(slider.width() > width()){
                slider.setWidth(width())
            }else{
                slider.setWidth(MINIMAL_SLIDER_SIZE)
            }
            conserveSliderPositionOnResize()
        }
        slider.onMouseDrag = { slider.moveAlongX(LMouse.mouseDisplacementX()) }
        slider.addXListener{ updateValue() }
        slider.addXListener{ correctSliderPosition() }
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

    private fun isTooFarLeft() : Boolean = slider.leftSideX() < leftSideX()

    private fun isTooFarRight() : Boolean = slider.rightSideX() > rightSideX()

    private fun correctLeft(){
        slider.setx(leftSideX() + slider.width() / 2)
    }

    private fun correctRight(){
        slider.setx(rightSideX() - slider.width() / 2)
    }

    override fun correctSliderPosition(){
        if(slider.width() < width()){
            if(isTooFarLeft()){
                correctLeft()
            }else if(isTooFarRight()){
                correctRight()
            }
        }else{
            slider.setx(centerX())
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
        val proportion : Double = value() / range()
        val newX : Int = leftSideX() + (proportion * width()).toInt()
        slider.setx(newX)
    }

    override fun loadParameters(g: Graphics) {
        slider.setHeight(height())
        slider.sety(centerY())
        slider.setWidth(MINIMAL_SLIDER_SIZE)
        conserveSliderPositionOnResize()
    }

}