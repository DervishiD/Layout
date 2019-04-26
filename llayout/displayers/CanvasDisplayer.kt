package llayout.displayers

import llayout.interfaces.Canvas
import llayout.GraphicAction
import llayout.geometry.Point
import java.awt.Graphics

/**
 * A Displayer that implements the Canvas interface.
 * @see Displayer
 * @see Canvas
 */
class CanvasDisplayer : Displayer, Canvas {

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    /**
     * The width of this CanvasDisplayer, as a proportion of its container's width.
     */
    private var relativeW : Double? = null

    /**
     * The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    private var relativeH : Double? = null

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y){
        w.value = width
        h.value = height
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, in pixels.
     */
    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y){
        relativeW = width
        h.value = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y){
        w.value = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, in pixels.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, in pixels.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param x The x coordinate of the center of this CanvasDisplayer, as a proportion of its container's width.
     * @param y The y coordinate of the center of this CanvasDisplayer, as a proportion of its container's height.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     */
    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y){
        relativeW = width
        relativeH = height
        requestCoordinateUpdate()
    }

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param p The center of this CanvasDisplayer, as pixel coordinates.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, in pixels.
     * @see Point
     */
    constructor(p : Point, width : Int, height : Int) : this(p.intx(), p.inty(), width, height)

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param p The center of this CanvasDisplayer, as pixel coordinates.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, in pixels.
     * @see Point
     */
    constructor(p : Point, width : Double, height : Int) : this(p.intx(), p.inty(), width, height)

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param p The center of this CanvasDisplayer, as pixel coordinates.
     * @param width The width of this CanvasDisplayer, in pixels.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     * @see Point
     */
    constructor(p : Point, width : Int, height : Double) : this(p.intx(), p.inty(), width, height)

    /**
     * Constructs a CanvasDisplayer with the given parameters.
     * @param p The center of this CanvasDisplayer, as pixel coordinates.
     * @param width The width of this CanvasDisplayer, as a proportion of its container's width.
     * @param height The height of this CanvasDisplayer, as a proportion of its container's height.
     * @see Point
     */
    constructor(p : Point, width : Double, height : Double) : this(p.intx(), p.inty(), width, height)

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