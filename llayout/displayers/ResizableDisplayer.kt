package llayout.displayers

import llayout.geometry.Point
import llayout.utilities.LProperty

abstract class ResizableDisplayer : Displayer {

    private var relativeW : LProperty<Double?> = LProperty(null)

    private var relativeH : LProperty<Double?> = LProperty(null)

    init{
        relativeW.addListener{requestUpdate()}
        relativeH.addListener{requestUpdate()}
    }

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(p : Point, width : Int, height : Int) : super(p){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(p : Point, width : Double, height : Int) : super(p){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(p : Point, width : Double, height : Double) : super(p){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y){
        setWidth(width)
        setHeight(height)
    }

    constructor(p : Point, width : Int, height : Double) : super(p){
        setWidth(width)
        setHeight(height)
    }

    fun setWidth(width : Int) : ResizableDisplayer{
        w.value = width
        return this
    }

    fun setWidth(width : Double) : ResizableDisplayer{
        relativeW.value = width
        return this
    }

    fun setWidth(width : Float) : ResizableDisplayer = setWidth(width.toDouble())

    fun setHeight(height : Int) : ResizableDisplayer{
        h.value = height
        return this
    }

    fun setHeight(height : Double) : ResizableDisplayer{
        relativeH.value = height
        return this
    }

    fun setHeight(height : Float) : ResizableDisplayer = setHeight(height.toDouble())

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): Displayer {
        if(relativeW.value != null) w.value = (relativeW.value!! * frameWidth).toInt()
        if(relativeH.value != null) h.value = (relativeH.value!! * frameHeight).toInt()
        return super.updateRelativeValues(frameWidth, frameHeight)
    }

}