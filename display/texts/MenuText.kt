package display.texts

import display.DEFAULT_FONT
import display.Text
import geometry.Point
import geometry.Vector
import java.awt.Color.BLACK
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics

/**
 * Text that has a fixed position on a Menu panel
 */
class MenuText : Text {

    override var w : Int = 1
    override var h : Int = 1
    override var drawHeight : Int = 1
    override var lineHeight: Int = 1

    private lateinit var lines : List<String>

    /**
     * For a left alignment
     */
    private var alignLeftTo : Int? = null

    /**
     * For a right alignment
     */
    private var alignRightTo : Int? = null

    /**
     * For an up alignment
     */
    private var alignUpTo : Int? = null

    /**
     * For a down alignment
     */
    private var alignDownTo : Int? = null

    /**
     * Encodes if this MenuText is drawn for the first time
     */
    private var initphase : Boolean = true

    /**
     * Center x, center y, text, font
     */
    constructor(x : Int, y : Int, text : String, f : Font = DEFAULT_FONT) : super(x, y, text, f)

    /**
     * Center x, center y, text, font
     */
    constructor(x : Double, y : Double, text : String, f : Font = DEFAULT_FONT) : super(x, y, text, f)

    /**
     * Center Point, text, font
     */
    constructor(p : Point, text : String, f : Font = DEFAULT_FONT) : super(p, text, f)

    /**
     * Function to perform a left alignment
     */
    public fun alignLeftTo(l : Int){
        alignLeftTo = l
        alignRightTo = null
    }

    /**
     * Function to perform a right alignment
     */
    public fun alignRightTo(r : Int){
        alignRightTo = r
        alignLeftTo = null
    }

    /**
     * Function to perform an up alignment
     */
    public fun alignUpTo(u : Int){
        alignUpTo = u
        alignDownTo = null
    }

    /**
     * Function to perform a down alignment
     */
    public fun alignDownTo(d : Int){
        alignDownTo = d
        alignUpTo = null
    }

    /**
     * Function to perform a left alignment
     */
    public fun alignLeftTo(l : Double) = alignLeftTo(l.toInt())

    /**
     * Function to perform a right alignment
     */
    public fun alignRightTo(r : Double) = alignRightTo(r.toInt())

    /**
     * Function to perform an up alignment
     */
    public fun alignUpTo(u : Double) = alignUpTo(u.toInt())

    /**
     * Function to perform a down alignment
     */
    public fun alignDownTo(d : Double) = alignDownTo(d.toInt())

    public override fun paintComponent(g: Graphics?) {
        if(initphase){
            initPhase()
            initphase = false
        }
        g!!.color = BLACK
        g.font = f
        for(i : Int in 0 until lines.size){
            g.drawString(lines[i], 0, drawHeight + i * lineHeight)
        }
        g.drawString(txt, 0, drawHeight)
    }

    private fun initPhase(){
        lines = txt.split("\n")
        computeParameters()
        align()
        setBounds(p.intx() - w / 2, p.inty() - (h * lines.size)/2, w, h)
    }

    private fun computeParameters(){
        val fm : FontMetrics = parent.graphics.getFontMetrics(f)
        for(line : String in lines){
            val lineWidth = fm.stringWidth(line)
            if(lineWidth > w){
                w = lineWidth
            }
        }

        lineHeight = fm.maxAscent + fm.maxDescent
        h = lineHeight * lines.size

        drawHeight = fm.maxAscent
    }

    private fun align(){
        if(alignLeftTo != null){
            p = Point(alignLeftTo!! + w / 2, p.y())
        }else if(alignRightTo != null){
            p = Point(alignRightTo!! - w/2, p.y())
        }
        if(alignUpTo != null){
            p = Point(p.x(), alignUpTo!! + (h * lines.size)/2)
        }else if(alignDownTo != null){
            p = Point(p.x(), alignDownTo!! - h/2)
        }
    }

}