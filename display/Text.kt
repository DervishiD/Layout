package display

import java.awt.Font
import geometry.Point
import javax.swing.JLabel

/**
 * Abstract JLabel extension created to display text as efficiently as possible
 */
public abstract class Text : JLabel {

    /**
     * Center Point
     */
    protected var p : Point

    /**
     * Displayed text
     */
    protected var txt : String

    /**
     * Font
     */
    protected var f : Font

    /**
     * Width
     */
    protected abstract var w : Int

    /**
     * Height
     */
    protected abstract var h : Int

    /**
     * The height of a line
     */
    protected abstract var lineHeight : Int

    /**
     * Height at which the text is drawn, corresponds to fm.maxAscent
     */
    protected abstract var drawHeight : Int

    init{
        setBounds(1, 1, 1, 1)
    }

    /**
     * Center point, text, font
     */
    constructor(p : Point, text : String, f : Font) : super(){
        this.p = p
        this.txt = text
        this.f = f
    }

    /**
     * Center x, center y, text, font
     */
    constructor(x : Double, y : Double, text : String, f : Font) : super(){
        this.p = Point(x, y)
        this.txt = text
        this.f = f
    }

    /**
     * Center x, center y, text, font
     */
    constructor(x : Int, y : Int, text : String, f : Font) : super(){
        this.p = Point(x, y)
        this.txt = text
        this.f = f
    }

}