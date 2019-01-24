package display.texts

import display.DEFAULT_FONT
import display.Text
import geometry.Point
import java.awt.Color.BLACK
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics

class MenuText : Text {

    override var w : Int = 200
    override var h : Int = 200

    private var initphase : Boolean = true

    constructor(x : Int, y : Int, text : String, f : Font = DEFAULT_FONT) : super(x, y, text, f)

    constructor(x : Double, y : Double, text : String, f : Font = DEFAULT_FONT) : super(x, y, text, f)

    constructor(p : Point, text : String, f : Font = DEFAULT_FONT) : super(p, text, f)

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            val fm : FontMetrics = parent.graphics.getFontMetrics(f)
            w = fm.stringWidth(txt)
            h = fm.maxAscent + fm.maxDescent
            setBounds(p.intx() - w / 2, p.inty() - h/2, w, h)
            initphase = false
        }
        g!!.color = BLACK
        g.font = f
        g.drawString(txt, 0, h)
    }

}