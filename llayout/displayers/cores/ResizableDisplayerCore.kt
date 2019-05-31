package llayout.displayers.cores

import llayout.utilities.LObservable

abstract class ResizableDisplayerCore : DisplayerCore {

    private var relativeW : LObservable<Double?> = LObservable(null)

    private var relativeH : LObservable<Double?> = LObservable(null)

    init{
        relativeW.addListener{requestUpdate()}
        relativeH.addListener{requestUpdate()}
    }

    protected constructor(width : Int, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Int, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Double, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Double, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor() : this(1, 1)

    fun setWidth(width : Int) : ResizableDisplayerCore {
        w.value = width
        return this
    }

    fun setWidth(width : Double) : ResizableDisplayerCore {
        relativeW.value = width
        return this
    }

    fun setWidth(width : Float) : ResizableDisplayerCore = setWidth(width.toDouble())

    fun setHeight(height : Int) : ResizableDisplayerCore {
        h.value = height
        return this
    }

    fun setHeight(height : Double) : ResizableDisplayerCore {
        relativeH.value = height
        return this
    }

    fun setHeight(height : Float) : ResizableDisplayerCore = setHeight(height.toDouble())

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        if(relativeW.value != null) w.value = (relativeW.value!! * frameWidth).toInt()
        if(relativeH.value != null) h.value = (relativeH.value!! * frameHeight).toInt()
        super.updateRelativeValues(frameWidth, frameHeight)
    }

}