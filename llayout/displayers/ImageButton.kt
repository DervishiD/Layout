package llayout.displayers

import llayout.Action
import llayout.GraphicAction
import java.awt.Graphics

class ImageButton : ResizableDisplayer {

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

    fun setImage(image : GraphicAction, width : Int, height : Int) : ImageButton {
        setWidth(width)
        setHeight(height)
        this.image = image
        core.addGraphicAction(image, "IMAGE GIVEN BY ITS WRAPPER")
        return this
    }

    fun setImage(width : Int, height : Int, image : GraphicAction) : ImageButton = setImage(image, width, height)

    override fun initializeDrawingParameters(g: Graphics) {
        super.initializeDrawingParameters(g)
        core.addGraphicAction(image, "IMAGE GIVEN BY ITS WRAPPER")
    }

}