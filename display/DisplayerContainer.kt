package display

import geometry.Point
import java.awt.Graphics

/**
 * A simple Displayer Container, nothing special.
 * @see Displayer
 * @see AbstractDisplayerContainer
 */
class DisplayerContainer : AbstractDisplayerContainer {

    override val parts: MutableCollection<Displayer> = mutableListOf()

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param p The Point at the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(p : Point, width : Int, height : Int, displayers : Collection<Displayer>) : super(p){
        w = width
        h = height
        parts.addAll(displayers)
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
    constructor(x : Int, y : Int, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)

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
    constructor(x : Double, y : Int, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)

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
    constructor(x : Int, y : Double, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)

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
    constructor(x : Double, y : Double, width : Int, height : Int, displayers : Collection<Displayer>) : this(Point(x, y), width, height, displayers)

    /**
     * Constructs a DisplayerContainer with the given parameters.
     * @param p The Point at the center of this DisplayerContainer.
     * @param width The width of this DisplayerContainer.
     * @param height The height of this DisplayerContainer.
     * @param displayers The Displayers contained in this DisplayerContainer.
     * @see Point
     * @see Displayer
     * @see addDisplayers
     */
    constructor(p : Point, width : Int, height : Int, vararg displayers : Displayer) : super(p){
        w = width
        h = height
        parts.addAll(displayers)
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
    constructor(x : Int, y : Int, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)

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
    constructor(x : Double, y : Int, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)

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
    constructor(x : Int, y : Double, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)

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
    constructor(x : Double, y : Double, width : Int, height : Int, vararg displayers: Displayer) : this(Point(x, y), width, height, *displayers)

    /**
     * Adds the given displayers to this DisplayerContainer.
     * @param displayers The added Displayers.
     * @see Displayer
     */
    fun addDisplayers(vararg displayers : Displayer){
        parts.addAll(displayers)
    }

    /**
     * Adds the given displayers to this DisplayerContainer.
     * @param displayers The added Displayers.
     * @see Displayer
     */
    infix fun addDisplayers(displayers : Collection<Displayer>){
        parts.addAll(displayers)
    }

    override fun loadParameters(g: Graphics) {}

    override fun drawDisplayer(g: Graphics) {
        TODO("not implemented")
    }

}