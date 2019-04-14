package layout.displayers

import layout.GraphicAction
import layout.MouseWheelAction
import layout.geometry.Point
import java.awt.Graphics
import kotlin.math.abs

/**
 * An object in the Scroll pane. It is composed of a Displayer (i.e. the real displayed object),
 * and two integers.
 * The first one is called a directional delta, it's the distance, in pixels, between
 * the Displayer and the previous one in the direction of scrolling.
 * The second one is called a perpendicular delta, it's the distance, in pixels, between
 * the Displayer and the scrolling axis, i.e. the axis that is parallel to the direction
 * of scrolling and that goes through the center of the DisplayerScrollPane.
 * @see Displayer
 * @see DisplayerScrollPane
 * @see DisplayerScrollPane.Companion.ScrollType
 */
private typealias ScrollPaneObject = Triple<Displayer, Int, Int>

/**
 * A Displayer that acts as a scroll pane, containing other Displayers.
 * @see ScrollPaneObject
 * @see Displayer
 * @see AbstractDisplayerContainer
 */
class DisplayerScrollPane : AbstractDisplayerContainer {

    companion object {

        /**
         * This enum contains two values. One represents a vertical scrolling, and the other
         * a horizontal scrolling.
         */
        enum class ScrollType{VERTICAL, HORIZONTAL}

        /**
         * The distance, in pixels, by which the positions of the contained Displayers vary
         * for each scrolled unit.
         * @see Displayer
         * @see ScrollPaneObject
         * @see onWheelMoved
         */
        private const val SCROLL_DELTA : Int = 50

        /**
         * The default background of a DisplayerScrollPane object, i.e. nothing.
         * @see GraphicAction
         * @see backgroundDrawer
         */
        @JvmStatic val NO_BACKGROUND : GraphicAction = { _, _, _ ->  }
    }

    override val parts: MutableCollection<Displayer> = mutableListOf()

    /**
     * Encodes the direction of the Scrolling.
     * @see ScrollType
     */
    private val scrollDirection : ScrollType

    /**
     * The ScrollPaneObjects present on this DisplayerScrollPane.
     * @see ScrollPaneObject
     * @see Displayer
     */
    private val scrollPaneObjects : MutableList<ScrollPaneObject> = mutableListOf()

    private var startingX : Int

    private var startingY : Int

    /**
     * Draws the background of this DisplayerScrollPane.
     * @see GraphicAction
     */
    private val backgroundDrawer : GraphicAction

    override var onWheelMoved : MouseWheelAction = { units : Int -> run{
        if(isVertical()){
            startingY -= units * SCROLL_DELTA
        }else{
            startingX -= units * SCROLL_DELTA
        }

        verifyPosition()
    }}

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Int,
            width : Int,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Double,
            width : Int,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Int,
            width : Int,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Double,
            width : Int,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param p The center point of this DisplayerScrollPane.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            p : Point,
            width : Int,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : this(p.intx(), p.inty(), width, height, scrollDirection, parts, background)

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Int,
            width : Double,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Double,
            width : Double,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Int,
            width : Double,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Double,
            width : Double,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param p The center point of this DisplayerScrollPane.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            p : Point,
            width : Double,
            height : Int,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : this(p.intx(), p.inty(), width, height, scrollDirection, parts, background)

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Int,
            width : Int,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Double,
            width : Int,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Int,
            width : Int,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Double,
            width : Int,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param p The center point of this DisplayerScrollPane.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            p : Point,
            width : Int,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : this(p.intx(), p.inty(), width, height, scrollDirection, parts, background)

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Int,
            width : Double,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Int,
            y : Double,
            width : Double,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Int,
            width : Double,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param x The center point's x coordinate.
     * @param y The center point's y coordinate.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            x : Double,
            y : Double,
            width : Double,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : super(x, y, width, height){
        this.scrollDirection = scrollDirection
        if(isVertical()){
            startingX = width() / 2
            startingY = 0
        }else{
            startingX = 0
            startingY = height() / 2
        }
        for(part : Displayer in parts){
            this.scrollPaneObjects.add(ScrollPaneObject(part, 0, 0))
        }
        this.backgroundDrawer = background
    }

    /**
     * Creates a DisplayerScrollPane with the given parameters.
     * @param p The center point of this DisplayerScrollPane.
     * @param width The width of this DisplayerScrollPane, in pixels.
     * @param height The height of this DisplayerScrollPane, in pixels.
     * @param scrollDirection The direction of scrolling
     * @param parts Displayers that will be added on the DisplayerScrollPane with default alignment.
     * @param background The GraphicAction that draws the background of this DisplayerScrollPane.
     * @see Point
     * @see Displayer
     * @see ScrollPaneObject
     * @see ScrollType
     * @see parts
     */
    constructor(
            p : Point,
            width : Double,
            height : Double,
            scrollDirection : ScrollType = ScrollType.VERTICAL,
            parts : Collection<Displayer> = listOf(),
            background : GraphicAction = NO_BACKGROUND) : this(p.intx(), p.inty(), width, height, scrollDirection, parts, background)

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int): Displayer {
        super.updateRelativeValues(frameWidth, frameHeight)
        if(isVertical()){
            startingX = width() / 2
        }else{
            startingY = height() / 2
        }
        return this
    }

    /**
     * Adds a Displayer to the structure of the ScrollPane
     * @param directionalDelta The distance between this Displayer and the last one
     *                          in the direction of the scrolling
     * @param perpendicularDelta The distance between this Displayer and the center of the ScrollPane
     *                          in the direction perpendicular to the scrolling
     */
    fun addToScrollPane(part : Displayer, directionalDelta : Int = 0, perpendicularDelta : Int = 0) : DisplayerScrollPane {
        add(part)
        scrollPaneObjects.add(ScrollPaneObject(part, abs(directionalDelta), perpendicularDelta))
        return this
    }

    override fun loadParameters(g: Graphics) {
        if(scrollPaneObjects.size > 0){
            var last : Displayer = scrollPaneObjects[0].first
            if(isVertical()){
                scrollPaneObjects[0].first.moveTo(startingX + scrollPaneObjects[0].third, startingY + scrollPaneObjects[0].second + scrollPaneObjects[0].first.height()/2)
                for(i : Int in 1 until scrollPaneObjects.size){
                    scrollPaneObjects[i].first setx startingX + scrollPaneObjects[i].third
                    scrollPaneObjects[i].first.alignUpToDown(last, scrollPaneObjects[i].second)
                    last = scrollPaneObjects[i].first
                }
            }else{
                scrollPaneObjects[0].first.moveTo(startingX + scrollPaneObjects[0].second + scrollPaneObjects[0].first.width()/2, startingY + scrollPaneObjects[0].third)
                for(i : Int in 1 until parts.size){
                    scrollPaneObjects[i].first sety startingY + scrollPaneObjects[i].third
                    scrollPaneObjects[i].first.alignLeftToRight(last, scrollPaneObjects[i].second)
                    last = scrollPaneObjects[i].first
                }
            }
        }
    }

    override fun drawDisplayer(g: Graphics) {
        if(scrollPaneObjects.size > 0){
            if(isVertical()){
                scrollPaneObjects[0].first.moveTo(startingX + scrollPaneObjects[0].third, startingY + scrollPaneObjects[0].second + scrollPaneObjects[0].first.height()/2)
            }else{
                scrollPaneObjects[0].first.moveTo(startingX + scrollPaneObjects[0].second + scrollPaneObjects[0].first.width()/2, startingY + scrollPaneObjects[0].third)
            }
        }
        for(part : ScrollPaneObject in scrollPaneObjects){
            part.first.paintComponent(g)
        }
        g.clearRect(0, 0, width(), height())
        backgroundDrawer.invoke(g, width(), height())
    }

    /**
     * Prevents the ScrollPane from scrolling too far in the scrolling direction.
     * @see startingPoint
     */
    private fun verifyPosition(){
        if(isVertical()){
            if(startingY < 0){
                startingY = 0
            }else if(startingY > maxScroll()){
                startingY = maxScroll()
            }
        }else{
            if(startingX < 0){
                startingX = 0
            }else if(startingX > maxScroll()){
                startingX = maxScroll()
            }
        }
    }

    /**
     * Returns the maximal scroll of this panel.
     */
    private fun maxScroll() : Int{
        var result : Int = scrollPaneObjects[0].second
        if(isVertical()){
            for(part : ScrollPaneObject in scrollPaneObjects){
                result += part.second + part.first.height()
            }
            if(result < height()) result = 0
        }else{
            for(part : ScrollPaneObject in scrollPaneObjects){
                result += part.second + part.first.width()
            }
            if(result < width()) result = 0
        }
        return result
    }

    /**
     * Detects if the scrolling is vertical or horizontal.
     * @see scrollDirection
     * @see ScrollType
     */
    private fun isVertical() : Boolean = scrollDirection == ScrollType.VERTICAL

}