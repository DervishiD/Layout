package display

import geometry.Point
import geometry.Vector
import java.awt.Graphics
import java.lang.IllegalArgumentException
import kotlin.math.abs

private typealias ScrollPaneObject = Triple<Displayer, Int, Int>

class DisplayerScrollPane : Displayer {

    companion object {
        const val SCROLLBAR_RIGHT : Int = 0
        const val SCROLLBAR_DOWN : Int = 10
        const val SCROLLBAR_LEFT : Int = 100
        const val SCROLLBAR_UP : Int = 1000
        const val SCROLL_DELTA : Int = 50 //TODO?
    }

    override var w : Int = 0
    override var h : Int = 0

    private val scrollbarPosition : Int

    private val parts : ArrayList<ScrollPaneObject> = ArrayList()

    private var startingPoint : Point

    constructor(p : Point, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList()) : super(p){
        w = width
        h = height
        this.scrollbarPosition = scrollbarPosition
        startingPoint = if(isVertical()) Point(point.x, point.y - h/2) else Point(point.x - w/2, point.y)
        for(part : Displayer in parts){
            this.parts.add(ScrollPaneObject(part, 0, 0))
        }
    }
    constructor(x : Int, y : Int, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList()) : this(Point(x, y), width, height, scrollbarPosition, parts)
    constructor(x : Int, y : Double, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList()) : this(Point(x, y), width, height, scrollbarPosition, parts)
    constructor(x : Double, y : Int, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList()) : this(Point(x, y), width, height, scrollbarPosition, parts)
    constructor(x : Double, y : Double, width : Int, height : Int, scrollbarPosition : Int = SCROLLBAR_RIGHT, parts : List<Displayer> = ArrayList()) : this(Point(x, y), width, height, scrollbarPosition, parts)

    fun add(part : Displayer, directionalDelta : Int = 0, perpendicularDelta : Int = 0){
        parts.add(ScrollPaneObject(part, abs(directionalDelta), perpendicularDelta))
    }

    override fun loadParameters(g: Graphics) {
        if(parts.size > 0){
            var last : Displayer = parts[0].first
            if(isVertical()){
                parts[0].first.moveTo(startingPoint.x + parts[0].third, startingPoint.y + parts[0].second + parts[0].first.height()/2)
                for(i : Int in 1 until parts.size){
                    parts[i].first setx startingPoint.x + parts[i].third
                    parts[i].first.alignUpToDown(last, parts[i].second)
                    last = parts[i].first
                }
            }else{
                parts[0].first.moveTo(startingPoint.x + parts[0].second + parts[0].first.width()/2, startingPoint.y + parts[0].third)
                for(i : Int in 1 until parts.size){
                    parts[i].first sety startingPoint.y + parts[i].third
                    parts[i].first.alignLeftToRight(last, parts[i].second)
                    last = parts[i].first
                }
            }
        }
    }

    override fun drawDisplayer(g: Graphics) {
        if(parts.size > 0){
            if(isVertical()){
                parts[0].first.moveTo(startingPoint.x + parts[0].third, startingPoint.y + parts[0].second + parts[0].first.height()/2)
            }else{
                parts[0].first.moveTo(startingPoint.x + parts[0].second + parts[0].first.width()/2, startingPoint.y + parts[0].third)
            }
        }
        g.drawRect(2, 2, w - 3, h - 3)
    }

    override fun mouseWheelMoved(units: Int) {
        startingPoint += if(isVertical()){
            Vector(0, -units * SCROLL_DELTA)
        }else{
            Vector(-units * SCROLL_DELTA, 0)
        }
    }

    private fun isVertical() : Boolean = when(scrollbarPosition){
        SCROLLBAR_RIGHT, SCROLLBAR_LEFT -> true
        SCROLLBAR_UP, SCROLLBAR_DOWN -> false
        else -> throw IllegalArgumentException("Undefined scrollbar position")
    }

}