package llayout.displayers.cores

import llayout.utilities.LObservable

/**
 * A DisplayerCore that possesses methods to modify its dimensions.
 * @since LLayout 1
 */
abstract class ResizableDisplayerCore : DisplayerCore {

    /**
     * The width of this DisplayerCore, as a proportion of its container's width.
     * @see LObservable
     * @since LLayout 1
     */
    private var relativeW : LObservable<Double?> = LObservable(null)

    /**
     * The height of this DisplayerCore, as a proportion of its container's height.
     * @see LObservable
     * @since LLayout 1
     */
    private var relativeH : LObservable<Double?> = LObservable(null)

    /*
     * Adds listeners to the relative width and height
     */
    init{
        relativeW.addListener{requestUpdate()}
        relativeH.addListener{requestUpdate()}
    }

    /**
     * Constructs a ResizableDisplayerCore with an absolute width and an absolute height.
     * @since LLayout 1
     */
    protected constructor(width : Int, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    /**
     * Constructs a ResizableDisplayerCore with an absolute width and a relative height.
     * @since LLayout 1
     */
    protected constructor(width : Int, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    /**
     * Constructs a ResizableDisplayerCore with a relative width and an absolute height.
     * @since LLayout 1
     */
    protected constructor(width : Double, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    /**
     * Constructs a ResizableDisplayerCore with a relative width and a relative height.
     * @since LLayout 1
     */
    protected constructor(width : Double, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    /**
     * Constructs a ResizableDisplayerCore with default width and height fixed at 1.
     * @since LLayout 1
     */
    protected constructor() : this(1, 1)

    /**
     * Sets the width of this DisplayerCore to an absolute value.
     * @param width The new width of this DisplayerCore, in pixels.
     * @return this
     * @since LLayout 1
     */
    fun setWidth(width : Int) : ResizableDisplayerCore {
        w.value = width
        return this
    }

    /**
     * Sets the width of this DisplayerCore to a relative value.
     * @param width The new width of this DisplayerCore, as a proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun setWidth(width : Double) : ResizableDisplayerCore {
        relativeW.value = width
        return this
    }

    /**
     * Sets the width of this DisplayerCore to a relative value.
     * @param width The new width of this DisplayerCore, as a proportion of its container's width.
     * @return this
     * @since LLayout 1
     */
    fun setWidth(width : Float) : ResizableDisplayerCore = setWidth(width.toDouble())

    /**
     * Sets the height of this DisplayerCore to an absolute value.
     * @param height The new height of this DisplayerCore, in pixels.
     * @return this
     * @since LLayout 1
     */
    fun setHeight(height : Int) : ResizableDisplayerCore {
        h.value = height
        return this
    }

    /**
     * Sets the height of this DisplayerCore to a relative value.
     * @param height The new height of this DisplayerCore, as a proportion of its container's height.
     * @return this
     * @since LLayout 1
     */
    fun setHeight(height : Double) : ResizableDisplayerCore {
        relativeH.value = height
        return this
    }

    /**
     * Sets the height of this DisplayerCore to a relative value.
     * @param height The new height of this DisplayerCore, as a proportion of its container's height.
     * @return this
     * @since LLayout 1
     */
    fun setHeight(height : Float) : ResizableDisplayerCore = setHeight(height.toDouble())

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        if(relativeW.value != null) w.value = (relativeW.value!! * frameWidth).toInt()
        if(relativeH.value != null) h.value = (relativeH.value!! * frameHeight).toInt()
        super.updateRelativeValues(frameWidth, frameHeight)
    }

}