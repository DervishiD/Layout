package llayout.displayers

import llayout.interfaces.StandardLContainer
import llayout.interfaces.Displayable

/**
 * An abstract Displayer that implements the StandardLContainer interface.
 * @see Displayer
 * @see StandardLContainer
 */
abstract class AbstractDisplayerContainer : ResizableDisplayer, StandardLContainer {

    override val parts: MutableCollection<Displayable> = mutableListOf()

    protected constructor(width : Int, height : Int) : super(width, height)

    protected constructor(width : Double, height : Int) : super(width, height)

    protected constructor(width : Int, height : Double) : super(width, height)

    protected constructor(width : Double, height : Double) : super(width, height)

    protected constructor() : super()

}