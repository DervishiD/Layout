package llayout.displayers

import llayout.Action
import llayout.GraphicAction
import java.awt.Graphics

/**
 * A Button with an image.
 * @see TextButton
 * @see Displayer
 */
class ImageButton : ResizableDisplayer {

    /**
     * The image of this button, as a GraphicAction.
     * @see GraphicAction
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
     * Sets a new image for this ImageButton.
     * @param image The new image of this Button, as a GraphicAction.
     * @param width The new width of this Button, in pixels.
     * @param height The new height of this Button, in pixels.
     * @see image
     * @see GraphicAction
     */
    fun setImage(image : GraphicAction, width : Int, height : Int) : ImageButton {
        w.value = width
        h.value = height
        this.image = image
        return this
    }

    /**
     * Sets a new image for this ImageButton.
     * @param image The new image of this Button, as a GraphicAction.
     * @param width The new width of this Button, in pixels.
     * @param height The new height of this Button, in pixels.
     * @see image
     * @see GraphicAction
     */
    fun setImage(width : Int, height : Int, image : GraphicAction) : ImageButton = setImage(image, width, height)

    override fun loadParameters(g: Graphics) {}

    override fun drawDisplayer(g: Graphics) = image.invoke(g, width(), height())

}