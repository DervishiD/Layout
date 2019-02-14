package display

import geometry.Point
import geometry.Vector
import main.GraphicAction
import java.awt.Graphics
import java.lang.IllegalArgumentException
import javax.swing.JLabel
import kotlin.math.abs

/**
 * An object in the Scroll pane
 */
private typealias ScrollPaneObject = Triple<Displayer, Int, Int>

/**
 * A scroll pane that scrolls Displayers
 */
class DisplayerScrollPane : Displayer, CustomContainer {

    companion object {
        const val SCROLLBAR_RIGHT : Int = 0
        const val SCROLLBAR_DOWN : Int = 10
        const val SCROLLBAR_LEFT : Int = 100
        const val SCROLLBAR_UP : Int = 1000
        const val SCROLL_DELTA : Int = 50 //TODO?
    }

    override var w : Int = 0
    override var h : Int = 0
    override val parts: ArrayList<Displayer> = ArrayList()

    /**
     * Encodes the position of the scroll bar
     */
    private val scrollbarPosition : Int

    /**
     * The objects displayed on this Pane
     */
    private val scrollPaneObjects : ArrayList<ScrollPaneObject> = ArrayList()

    /**
     * The zero point of the computation of the Displayers' positions
     */
    private var startingPoint : Point

    private val backgroundDrawer : GraphicAction

    constructor(p : Point, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList(), background : GraphicAction = NO_BACKGROUND) : super(p){
        w = width
        h = height
        this.scrollbarPosition = scrollbarPosition
        startingPoint = Point(w/2, 0)
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }
    constructor(x : Int, y : Int, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList(), background : GraphicAction = NO_BACKGROUND) : this(Point(x, y), width, height, scrollbarPosition, parts, background)
    constructor(x : Int, y : Double, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList(), background : GraphicAction = NO_BACKGROUND) : this(Point(x, y), width, height, scrollbarPosition, parts, background)
    constructor(x : Double, y : Int, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList(), background : GraphicAction = NO_BACKGROUND) : this(Point(x, y), width, height, scrollbarPosition, parts, background)
    constructor(x : Double, y : Double, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList(), background : GraphicAction = NO_BACKGROUND) : this(Point(x, y), width, height, scrollbarPosition, parts, background)

    /**
     * Adds a Displayer to the structure of the ScrollPane
     * @param directionalDelta The distance between this Displayer and the last one
     *                          in the direction of the scrollig
     * @param perpendicularDelta The distance between this Displayer and the center of the ScrollPane
     */
    fun addToScrollPane(part : Displayer, directionalDelta : Int = 0, perpendicularDelta : Int = 0){
        scrollPaneObjects.add(ScrollPaneObject(part, abs(directionalDelta), perpendicularDelta))
        (this as JLabel).add(part)
    }

    override fun loadParameters(g: Graphics) {
        if(scrollPaneObjects.size > 0){
            var last : Displayer = scrollPaneObjects[0].first
            if(isVertical()){
                scrollPaneObjects[0].first.moveTo(startingPoint.x + scrollPaneObjects[0].third, startingPoint.y + scrollPaneObjects[0].second + scrollPaneObjects[0].first.height()/2)
                for(i : Int in 1 until scrollPaneObjects.size){
                    scrollPaneObjects[i].first setx startingPoint.x + scrollPaneObjects[i].third
                    scrollPaneObjects[i].first.alignUpToDown(last, scrollPaneObjects[i].second)
                    last = scrollPaneObjects[i].first
                }
            }else{
                scrollPaneObjects[0].first.moveTo(startingPoint.x + scrollPaneObjects[0].second + scrollPaneObjects[0].first.width()/2, startingPoint.y + scrollPaneObjects[0].third)
                for(i : Int in 1 until parts.size){
                    scrollPaneObjects[i].first sety startingPoint.y + scrollPaneObjects[i].third
                    scrollPaneObjects[i].first.alignLeftToRight(last, scrollPaneObjects[i].second)
                    last = scrollPaneObjects[i].first
                }
            }
        }
    }

    override fun drawDisplayer(g: Graphics) {
        if(scrollPaneObjects.size > 0){
            if(isVertical()){
                scrollPaneObjects[0].first.moveTo(startingPoint.x + scrollPaneObjects[0].third, startingPoint.y + scrollPaneObjects[0].second + scrollPaneObjects[0].first.height()/2)
            }else{
                scrollPaneObjects[0].first.moveTo(startingPoint.x + scrollPaneObjects[0].second + scrollPaneObjects[0].first.width()/2, startingPoint.y + scrollPaneObjects[0].third)
            }
        }
        for(part : ScrollPaneObject in scrollPaneObjects){
            part.first.paintComponent(g)
        }
        g.clearRect(0, 0, w, h)
        backgroundDrawer.invoke(g, w, h)
    }

    override fun mouseWheelMoved(units: Int) {
        startingPoint += if(isVertical()){
            Vector(0, -units * SCROLL_DELTA)
        }else{
            Vector(-units * SCROLL_DELTA, 0)
        }
    }

    /**
     * Detects if the scrolling is vertical or horizontal
     */
    private fun isVertical() : Boolean = when(scrollbarPosition){
        SCROLLBAR_RIGHT, SCROLLBAR_LEFT -> true
        SCROLLBAR_UP, SCROLLBAR_DOWN -> false
        else -> throw IllegalArgumentException("Undefined scrollbar position")
    }

}