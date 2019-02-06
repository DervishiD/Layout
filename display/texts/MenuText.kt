package display.texts

import display.StringDisplay
import geometry.Point
import main.GraphicAction
import java.awt.Graphics

/**
 * Text that has a fixed position on a Menu panel
 */
public class MenuText : Text {

    constructor(p : Point, text : ArrayList<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)
    constructor(x : Double, y : Double, text : ArrayList<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Double, y : Int, text : ArrayList<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Double, text : ArrayList<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Int, text : ArrayList<StringDisplay>, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(p : Point, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)
    constructor(x : Double, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Double, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Double, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Int, text : StringDisplay, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(p : Point, text : String, background: GraphicAction = NO_BACKGROUND) : super(p, text, background)
    constructor(x : Double, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Double, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Double, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)
    constructor(x : Int, y : Int, text : String, background: GraphicAction = NO_BACKGROUND) : super(Point(x, y), text, background)

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