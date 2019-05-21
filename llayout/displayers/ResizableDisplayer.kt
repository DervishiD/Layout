package llayout.displayers

import llayout.utilities.LProperty

abstract class ResizableDisplayer : Displayer {

    private var relativeW : LProperty<Double?> = LProperty(null)

    private var relativeH : LProperty<Double?> = LProperty(null)

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