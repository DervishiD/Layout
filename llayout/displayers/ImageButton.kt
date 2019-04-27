package llayout.displayers

import llayout.Action
import llayout.GraphicAction
import llayout.geometry.Point
import java.awt.Graphics

/**
 * A Button with an image.
 * @see TextButton
 * @see Displayer
 */
class ImageButton : Displayer {

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
    constructor(x : Int, y : Int, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y){
        w.value = width
        h.value = height
        setOnMouseReleaseAction(onClick)
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
    constructor(x : Int, y : Double, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y){
        w.value = width
        h.value = height
        setOnMouseReleaseAction(onClick)
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
    constructor(x : Double, y : Int, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y){
        w.value = width
        h.value = height
        setOnMouseReleaseAction(onClick)
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
    constructor(x : Double, y : Double, width : Int, height : Int, image : GraphicAction, onClick: Action) : super(x, y){
        w.value = width
        h.value = height
        setOnMouseReleaseAction(onClick)
        this.image = image
    }

    /**
     * Creates a button from the given parameters.
     * @param p The center point of this ImageButton
     * @param width the width of this ImageButton, in pixels.
     * @param height The height of this ImageButton, in pixels.
     * @param image The image of this ImageButton, as a GraphicAction.
     * @param onClick The Action executed on a mouse release.
     * @see Action
     * @see Point
     * @see GraphicAction
     * @see MouseInteractable
     */
    constructor(p : Point, width : Int, height : Int, image : GraphicAction, onClick : Action) : this(p.intx(), p.inty(), width, height, image, onClick)

    /**
     * Creates a button from the given parameters.
     * @param p The center point of this ImageButton
     * @param width the width of this ImageButton, in pixels.
     * @param height The height of this ImageButton, in pixels.
     * @param image The image of this ImageButton, as a GraphicAction.
     * @param onClick The Action executed on a mouse release.
     * @see Action
     * @see Point
     * @see GraphicAction
     * @see MouseInteractable
     */
    constructor(p : Point, width : Int, height : Int, onClick : Action, image : GraphicAction) : this(p, width, height, image, onClick)

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
    constructor(x : Int, y : Int, width : Int, height : Int, onClick : Action, image : GraphicAction) : this(Point(x, y), width, height, image, onClick)

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
    constructor(x : Int, y : Double, width : Int, height : Int, onClick : Action, image : GraphicAction) : this(Point(x, y), width, height, image, onClick)

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
    constructor(x : Double, y : Int, width : Int, height : Int, onClick : Action, image : GraphicAction) : this(Point(x, y), width, height, image, onClick)

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
    constructor(x : Double, y : Double, width : Int, height : Int, onClick : Action, image : GraphicAction) : this(Point(x, y), width, height, image, onClick)

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