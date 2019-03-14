package display

import geometry.Point
import java.awt.Component

abstract class DisplayerContainer(p : Point) : Displayer(p), CustomContainer{

    override fun mouseClick(x : Int, y : Int){}
    override fun mousePress(x : Int, y : Int){}
    override fun mouseRelease(x : Int, y : Int){}
    override fun mouseEnter(x : Int, y : Int){}
    override fun mouseExit(x : Int, y : Int){}
    override fun mouseDrag(x : Int, y : Int){}
    override fun mouseMoved(x : Int, y : Int){}
    override fun mouseWheelMoved(x : Int, y : Int, units : Int){}

    fun displayerAt(x : Int, y : Int) : Displayer{
        val component : Component = getComponentAt(x, y)
        return when(component){
            is DisplayerContainer -> {
                if(component == this){
                    this
                }else component.displayerAt(x - component.lowestX(), y - component.lowestY())
            }
            is Displayer -> component
            else -> throw Exception("Something is really wrong in DisplayerContainer. The event handling" +
                    " does some weird shit.")
        }
    }

}