package llayout7.displayers

import llayout7.utilities.GraphicAction
import llayout7.interfaces.CanvasCore
import java.awt.Graphics

/**
 * A Canvas is a ResizableDisplayer that implements the CanvasCore interface.
 * @see ResizableDisplayer
 * @see CanvasCore
 * @since LLayout 1
 */
open class Canvas : ResizableDisplayer, CanvasCore {

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    constructor() : super()

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    override fun addGraphicAction(graphicAction: GraphicAction, key: Any?): Canvas {
        core.addGraphicAction(graphicAction, key)
        return this
    }

    override fun drawDisplayable(g: Graphics) {
        drawBackground(g)
    }

}