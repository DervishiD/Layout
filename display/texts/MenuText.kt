package display.texts

import display.StringDisplay
import display.Text
import display.toLinesList
import geometry.Point
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * Text that has a fixed position on a Menu panel
 */
public class MenuText : Text {

    override var w : Int = 0
    override var h : Int = 0
    private val lines = txt.toLinesList()

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

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            loadParameters(g!!)
            setBounds(point.intx() - w / 2, point.inty() - h / 2, w, h)
            initphase = false
        }
        drawBackground(g!!)
        drawText(g)
    }

    public override fun loadParameters(g : Graphics){
        computeTotalHeight(g)
        computeMaxLength(g)
    }

    private fun computeTotalHeight(g : Graphics){
        for(line : ArrayList<StringDisplay> in lines){
            h += computeHeight(g, line)
        }
    }

    private fun computeHeight(g : Graphics, line : ArrayList<StringDisplay>) : Int{
        var maxAscent : Int = 0
        var maxDescent : Int = 0
        var fm : FontMetrics
        for(s : StringDisplay in line){
            fm = g.getFontMetrics(s.font)
            if(fm.maxAscent > maxAscent){
                maxAscent = fm.maxAscent
            }
            if(fm.maxDescent > maxDescent){
                maxDescent = fm.maxDescent
            }
        }
        return maxAscent + maxDescent
    }

    private fun ascent(g : Graphics, line : ArrayList<StringDisplay>) : Int{
        var maxAscent : Int = 0
        var fm : FontMetrics
        for(s : StringDisplay in line){
            fm = g.getFontMetrics(s.font)
            if(fm.maxAscent > maxAscent){
                maxAscent = fm.maxAscent
            }
        }
        return maxAscent
    }

    private fun descent(g : Graphics, line : ArrayList<StringDisplay>) : Int{
        var maxDescent : Int = 0
        var fm : FontMetrics
        for(s : StringDisplay in line){
            fm = g.getFontMetrics(s.font)
            if(fm.maxDescent > maxDescent){
                maxDescent = fm.maxDescent
            }
        }
        return maxDescent
    }

    private fun computeMaxLength(g : Graphics){
        var maxLength = 0
        var currentLength = 0
        var fm : FontMetrics
        for(s : StringDisplay in txt){
            fm = g.getFontMetrics(s.font)
            if(!(s.text.contains("\n"))){
                currentLength += fm.stringWidth(s.text)
            }else{
                val lines : List<String> = s.text.split("\n")
                currentLength += fm.stringWidth(lines[0])
                if(currentLength > maxLength){
                    maxLength = currentLength
                }
                for(i : Int in 1 until lines.size){
                    currentLength = fm.stringWidth(lines[i])
                    if(currentLength > maxLength){
                        maxLength = currentLength
                    }
                }
            }
        }
        w = if(maxLength > currentLength) maxLength else currentLength
    }

    protected override fun drawBackground(g: Graphics) {
        //TODO?
    }

    protected override fun drawText(g : Graphics) {
        //TODO

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