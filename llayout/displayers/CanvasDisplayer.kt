package llayout.displayers

import llayout.interfaces.Canvas
import llayout.GraphicAction
import java.awt.Graphics

/**
 * A Displayer that implements the Canvas interface.
 * @see Displayer
 * @see Canvas
 */
open class CanvasDisplayer : ResizableDisplayer, Canvas {

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    constructor() : super()

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    override fun loadParameters(g: Graphics) {}

    override fun drawDisplayer(g: Graphics) {
        drawBackground(g)
    }

}