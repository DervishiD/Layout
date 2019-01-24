package display

import java.awt.Font
import geometry.Point
import javax.swing.JLabel

public abstract class Text : JLabel {

    protected var p : Point
    protected var txt : String
    protected var f : Font
    protected abstract var w : Int
    protected abstract var h : Int

    init{
        setBounds(1, 1, 1, 1)
    }

    constructor(p : Point, text : String, f : Font) : super(){
        this.p = p
        this.txt = text
        this.f = f
    }

    constructor(x : Double, y : Double, text : String, f : Font) : super(){
        this.p = Point(x, y)
        this.txt = text
        this.f = f
    }

    constructor(x : Int, y : Int, text : String, f : Font) : super(){
        this.p = Point(x, y)
        this.txt = text
        this.f = f
    }

}