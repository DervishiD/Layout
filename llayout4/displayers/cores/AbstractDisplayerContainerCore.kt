package llayout4.displayers.cores

import llayout4.interfaces.StandardLContainer
import llayout4.interfaces.Displayable

/**
 * An abstract DisplayerCore that implements the StandardLContainer interface.
 * @see DisplayerCore
 * @see StandardLContainer
 * @since LLayout 1
 */
abstract class AbstractDisplayerContainerCore : ResizableDisplayerCore, StandardLContainer {

    init{
        w.addListener {
            for(d : Displayable in parts){
                d.updateRelativeValues(width(), height())
            }
        }
        h.addListener {
            for(d : Displayable in parts){
                d.updateRelativeValues(width(), height())
            }
        }
    }

    override val parts: MutableCollection<Displayable> = mutableListOf()

    protected constructor(width : Int, height : Int) : super(width, height)

    protected constructor(width : Double, height : Int) : super(width, height)

    protected constructor(width : Int, height : Double) : super(width, height)

    protected constructor(width : Double, height : Double) : super(width, height)

    protected constructor() : super()

}