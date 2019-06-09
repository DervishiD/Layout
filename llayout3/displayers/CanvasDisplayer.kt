package llayout3.displayers

import llayout3.GraphicAction
import llayout3.interfaces.Canvas
import java.awt.Graphics

/**
 * A CanvasDisplayer is a ResizableDisplayer that implements the Canvas interface.
 * @see ResizableDisplayer
 * @see Canvas
 * @since LLayout 1
 */
open class CanvasDisplayer : ResizableDisplayer, Canvas {

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    constructor() : super()

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    override fun addGraphicAction(graphicAction: GraphicAction, key: Any?): CanvasDisplayer {
        core.addGraphicAction(graphicAction, key)
        return this
    }

    override fun drawDisplayable(g: Graphics) {
        drawBackground(g)
    }

}