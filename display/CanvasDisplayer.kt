package display

import main.GraphicAction
import java.awt.Graphics

class CanvasDisplayer : Displayer, Canvas{

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    private var relativeW : Double? = null
    private var relativeH : Double? = null

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): Displayer {
        if(relativeW != null) w.value = (relativeW!! * frameWidth).toInt()
        if(relativeH != null) h.value = (relativeH!! * frameHeight).toInt()
        return super.updateRelativeValues(frameWidth, frameHeight)
    }

    override fun loadParameters(g: Graphics) {}

    override fun drawDisplayer(g: Graphics) {
        drawBackground(g)
    }

}