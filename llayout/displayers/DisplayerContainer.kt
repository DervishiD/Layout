package llayout.displayers

import llayout.GraphicAction
import llayout.interfaces.Canvas
import java.awt.Graphics

/**
 * A simple Displayer Container, nothing special.
 * @see Displayer
 * @see AbstractDisplayerContainer
 */
class DisplayerContainer : AbstractDisplayerContainer, Canvas {

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    constructor(width : Int, height : Int, vararg displayers: Displayer) : super(width, height){
        addDisplayers(*displayers)
    }

    constructor(width : Double, height : Int, vararg displayers: Displayer) : super(width, height){
        addDisplayers(*displayers)
    }

    constructor(width : Int, height : Double, vararg displayers: Displayer) : super(width, height){
        addDisplayers(*displayers)
    }

    constructor(width : Double, height : Double, vararg displayers: Displayer) : super(width, height){
        addDisplayers(*displayers)
    }

    constructor(width : Int, height : Int, displayers: Collection<Displayer>) : super(width, height){
        addDisplayers(displayers)
    }

    constructor(width : Double, height : Int, displayers: Collection<Displayer>) : super(width, height){
        addDisplayers(displayers)
    }

    constructor(width : Int, height : Double, displayers: Collection<Displayer>) : super(width, height){
        addDisplayers(displayers)
    }

    constructor(width : Double, height : Double, displayers: Collection<Displayer>) : super(width, height){
        addDisplayers(displayers)
    }

    constructor(vararg displayers: Displayer) : super(){
        addDisplayers(*displayers)
    }

    constructor(displayers : Collection<Displayer>) : super(){
        addDisplayers(displayers)
    }

    /**
     * Adds the given displayers to this DisplayerContainer.
     * @param displayers The added Displayers.
     * @see Displayer
     */
    fun addDisplayers(vararg displayers : Displayer) : DisplayerContainer {
        for(d : Displayer in displayers){
            super.add(d)
        }
        return this
    }

    /**
     * Adds the given displayers to this DisplayerContainer.
     * @param displayers The added Displayers.
     * @see Displayer
     */
    fun addDisplayers(displayers : Collection<Displayer>) : DisplayerContainer {
        for(d : Displayer in displayers){
            super.add(d)
        }
        return this
    }

    override fun loadParameters(g: Graphics) {}

    override fun drawDisplayer(g: Graphics) {
        drawBackground(g)
    }

}