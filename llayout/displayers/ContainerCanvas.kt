package llayout.displayers

import llayout.interfaces.CanvasCore
import llayout.utilities.GraphicAction
import java.awt.Graphics

/**
 * A [Displayer] that acts like a [Canvas] and a [DisplayerContainer]. It can contain other [Displayer]s and draw on itself.
 * @since LLayout 7
 */
open class ContainerCanvas : DisplayerContainer, CanvasCore {

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    constructor() : super()

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    override fun addGraphicAction(graphicAction: GraphicAction, key: Any?): ContainerCanvas {
        core.addGraphicAction(graphicAction, key)
        return this
    }

    override fun drawDisplayable(g: Graphics) {
        drawBackground(g)
    }

}