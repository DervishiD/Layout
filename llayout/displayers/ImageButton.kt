package llayout.displayers

import llayout.utilities.Action
import llayout.utilities.GraphicAction
import java.awt.Graphics

/**
 * A button with an image.
 * @see ResizableDisplayer
 * @since LLayout 1
 */
class ImageButton : ResizableDisplayer {

    /**
     * It's image.
     * @since LLayout 1
     */
    private var image : GraphicAction

    constructor(width : Int, height : Int, image : GraphicAction, action : Action) : super(width, height){
        this.image = image
        setOnMouseReleasedAction { action() }
    }

    constructor(width : Double, height : Int, image : GraphicAction, action : Action) : super(width, height){
        this.image = image
        setOnMouseReleasedAction { action() }
    }

    constructor(width : Int, height : Double, image : GraphicAction, action : Action) : super(width, height){
        this.image = image
        setOnMouseReleasedAction { action() }
    }

    constructor(width : Double, height : Double, image : GraphicAction, action : Action) : super(width, height){
        this.image = image
        setOnMouseReleasedAction { action() }
    }

    /**
     * Sets a new image for the button.
     * @return this
     * @see GraphicAction
     * @since LLayout 1
     */
    fun setImage(image : GraphicAction, width : Int, height : Int) : ImageButton {
        setWidth(width)
        setHeight(height)
        this.image = image
        core.addGraphicAction(image, "IMAGE GIVEN BY ITS WRAPPER")
        return this
    }

    /**
     * Sets a new image for the button.
     * @return this
     * @see GraphicAction
     * @since LLayout 1
     */
    fun setImage(width : Int, height : Int, image : GraphicAction) : ImageButton = setImage(image, width, height)

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        core.addGraphicAction(image, "IMAGE GIVEN BY ITS WRAPPER")
    }

}