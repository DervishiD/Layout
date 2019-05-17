package llayout.displayers

import llayout.Action
import llayout.GraphicAction
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

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

    /**
     * Creates a button from the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width the width of this ImageButton, in pixels.
     * @param height The height of this ImageButton, in pixels.
     * @param image The image of this ImageButton, as a GraphicAction.
     * @param onClick The Action executed on a mouse release.
     * @see Action
     * @see Point
     * @see GraphicAction
     * @see MouseInteractable
     */
    constructor(x : Int, y : Int, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y, width, height){
        setOnMouseReleasedAction { onClick() }
        this.image = image
    }

    /**
     * Creates a button from the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width the width of this ImageButton, in pixels.
     * @param height The height of this ImageButton, in pixels.
     * @param image The image of this ImageButton, as a GraphicAction.
     * @param onClick The Action executed on a mouse release.
     * @see Action
     * @see Point
     * @see GraphicAction
     * @see MouseInteractable
     */
    constructor(x : Int, y : Double, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y, width, height){
        setOnMouseReleasedAction { onClick() }
        this.image = image
    }

    /**
     * Creates a button from the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width the width of this ImageButton, in pixels.
     * @param height The height of this ImageButton, in pixels.
     * @param image The image of this ImageButton, as a GraphicAction.
     * @param onClick The Action executed on a mouse release.
     * @see Action
     * @see Point
     * @see GraphicAction
     * @see MouseInteractable
     */
    constructor(x : Double, y : Int, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y, width, height){
        setOnMouseReleasedAction { onClick() }
        this.image = image
    }

    /**
     * Creates a button from the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width the width of this ImageButton, in pixels.
     * @param height The height of this ImageButton, in pixels.
     * @param image The image of this ImageButton, as a GraphicAction.
     * @param onClick The Action executed on a mouse release.
     * @see Action
     * @see Point
     * @see GraphicAction
     * @see MouseInteractable
     */
    constructor(x : Double, y : Double, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y, width, height){
        setOnMouseReleasedAction { onClick() }
        this.image = image
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