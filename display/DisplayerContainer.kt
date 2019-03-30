package display

import geometry.Point
import java.awt.Graphics

class DisplayerContainer : AbstractDisplayerContainer {

    override val parts: MutableCollection<Displayer> = mutableListOf()

    constructor(p : Point, width : Int, height : Int, displayers : Collection<Displayer>) : super(p){
        w = width
        h = height
        parts.addAll(displayers)
    }
    constructor(x : Int, y : Int, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)
    constructor(x : Double, y : Int, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)
    constructor(x : Int, y : Double, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)
    constructor(x : Double, y : Double, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)
    constructor(p : Point, width : Int, height : Int, vararg displayers : Displayer) : super(p){
        w = width
        h = height
        parts.addAll(displayers)
    }
    constructor(x : Int, y : Int, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)
    constructor(x : Double, y : Int, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)
    constructor(x : Int, y : Double, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)
    constructor(x : Double, y : Double, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)

    fun addDisplayers(vararg displayers : Displayer){
        parts.addAll(displayers)
    }

    infix fun addDisplayers(displayers : Collection<Displayer>){
        parts.addAll(displayers)
    }

    override fun loadParameters(g: Graphics) {}

    override fun drawDisplayer(g: Graphics) {
        TODO("not implemented")
    }

}