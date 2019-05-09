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

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Int, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Int, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Int, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Int, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Int, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Int, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Int, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Int, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Double, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Double, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Double, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Double, height : Int, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Double, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Double, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Double, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Double, height : Int, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Int, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Int, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Int, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Int, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Int, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Int, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Int, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Int, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Double, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Double, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Double, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Double, height : Double, displayers : Collection<Displayer>) : super(x, y, width, height){
        addDisplayers(displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Int, width : Double, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Int, y : Double, width : Double, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Int, width : Double, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
    }

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param x The x coordinate of the center of this DisplayerContainer.
     * @param y The y coordinate of the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(x : Double, y : Double, width : Double, height : Double, vararg displayers: Displayer) : super(x, y, width, height){
        addDisplayers(*displayers)
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
    infix fun addDisplayers(displayers : Collection<Displayer>) : DisplayerContainer {
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