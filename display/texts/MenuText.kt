package display.texts

import display.StringDisplay
import geometry.Point
import java.awt.Graphics

/**
 * Text that has a fixed position on a Menu panel
 */
public class MenuText : Text {

    constructor(p : Point, text : ArrayList<StringDisplay>) : super(p, text)
    constructor(x : Double, y : Double, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(x : Double, y : Int, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(x : Int, y : Double, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(x : Int, y : Int, text : ArrayList<StringDisplay>) : super(Point(x, y), text)
    constructor(p : Point, text : StringDisplay) : super(p, text)
    constructor(x : Double, y : Double, text : StringDisplay) : super(Point(x, y), text)
    constructor(x : Double, y : Int, text : StringDisplay) : super(Point(x, y), text)
    constructor(x : Int, y : Double, text : StringDisplay) : super(Point(x, y), text)
    constructor(x : Int, y : Int, text : StringDisplay) : super(Point(x, y), text)
    constructor(p : Point, text : String) : super(p, text)
    constructor(x : Double, y : Double, text : String) : super(Point(x, y), text)
    constructor(x : Double, y : Int, text : String) : super(Point(x, y), text)
    constructor(x : Int, y : Double, text : String) : super(Point(x, y), text)
    constructor(x : Int, y : Int, text : String) : super(Point(x, y), text)

    protected override fun drawBackground(g: Graphics) {}

    protected override fun drawText(g : Graphics) {
        var currentX : Int = 0
        var currentY : Int = 0

        for(line : ArrayList<StringDisplay> in lines){
            currentY += ascent(g, line)
            for(s : StringDisplay in line){
                g.font = s.font
                g.color = s.color
                g.drawString(s.text, currentX, currentY)
                currentX += g.getFontMetrics(s.font).stringWidth(s.text)
            }
            currentX = 0
            currentY += descent(g, line)
        }

    }

}