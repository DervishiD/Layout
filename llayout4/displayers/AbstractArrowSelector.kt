package llayout4.displayers

import llayout4.GraphicAction
import llayout4.interfaces.AbstractSelector
import llayout4.utilities.LObservable
import java.awt.Color
import java.awt.Graphics

/**
 * A Displayer that implements the AbstractSelector interface.
 * The Selector selects options from a given list and switches from one to another by using lateral arrows.
 * @see Displayer
 * @see AbstractSelector
 * @since LLayout 1
 */
open class AbstractArrowSelector<T> : Displayer, AbstractSelector<T> {

    protected companion object {

        /**
         * The width of a horizontal arrow.
         * @since LLayout 1
         */
        @JvmStatic protected val HORIZONTAL_ARROW_WIDTH : Int = 25

        /**
         * The height of a horizontal arrow.
         * @since LLayout 1
         */
        private const val HORIZONTAL_ARROW_HEIGHT : Int = 25

        /**
         * The width of a vertical arrow.
         * @since LLayout 1
         */
        private const val VERTICAL_ARROW_WIDTH : Int = 25

        /**
         * The height of a vertical arrow.
         * @since LLayout 1
         */
        @JvmStatic protected val VERTICAL_ARROW_HEIGHT : Int = 25

        /**
         * The default color of the arrows.
         * @since LLayout 1
         */
        private val DEFAULT_ARROW_COLOR : Color = Color(34, 139, 34)

        /**
         * Returns a GraphicAction that draws a default left arrow of the given color.
         * @param color The color of the arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private fun leftArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h/2, 0, h), 3)
        }}

        /**
         * Returns a GraphicAction that draws a default right arrow of the given color.
         * @param color The color of the arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private fun rightArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, 0, w), intArrayOf(0, h, h/2), 3)
        }}

        /**
         * Returns a GraphicAction that draws a default up arrow of the given color.
         * @param color The color of the arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private fun upArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(h, 0, h), 3)
        }}

        /**
         * Returns a GraphicAction that draws a default down arrow of the given color.
         * @param color The color of the arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private fun downArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(0, h, 0), 3)
        }}

        /**
         * The default GraphicAction to draw a left arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_LEFT_ARROW_DRAWER : GraphicAction = leftArrowDrawer()

        /**
         * The default GraphicAction to draw a right arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_RIGHT_ARROW_DRAWER : GraphicAction = rightArrowDrawer()

        /**
         * The default GraphicAction to draw an up arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_UP_ARROW_DRAWER : GraphicAction = upArrowDrawer()

        /**
         * The default GraphicAction to draw a down arrow.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_DOWN_ARROW_DRAWER : GraphicAction = downArrowDrawer()

        /**
         * The distance between the central box and the arrows.
         * @since LLayout 1
         */
        @JvmStatic protected val EXTERIOR_DELTA : Int = 5

    }

    /**
     * The 'previous' arrow.
     * @since LLayout 1
     */
    private val previousArrow : ImageButton

    /**
     * The 'next' arrow.
     * @since LLayout 1
     */
    private val nextArrow : ImageButton

    /**
     * A boolean that is true if the arrows are placed horizontally and false if they're placed vertically.
     * @since LLayout 1
     */
    private val isHorizontal : Boolean

    override var currentOptionIndex: LObservable<Int> = LObservable(0)

    override val options: MutableList<T> = mutableListOf()

    protected constructor(vararg options : T, isHorizontal: Boolean = true) : super(){
        addOptionsList(*options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
        alignArrows()
    }

    protected constructor(options : Collection<T>, isHorizontal: Boolean = true) : super(){
        addOptionsList(options)
        this.isHorizontal = isHorizontal
        previousArrow = initializePreviousArrow()
        nextArrow = initializeNextArrow()
        alignArrows()
    }

    /**
     * Sets the color of both arrows to the given one, and sets the images to the default ones.
     * @return this
     * @since LLayout 1
     */
    fun setArrowsColor(color : Color) : AbstractArrowSelector<T> {
        setPreviousArrowColor(color)
        setNextArrowColor(color)
        return this
    }

    /**
     * Sets the color of the 'previous' arrow to the given one, and sets its image to the default one.
     * @return this
     * @since LLayout 1
     */
    fun setPreviousArrowColor(color : Color) : AbstractArrowSelector<T> {
        if(isHorizontal){
            previousArrow.setImage(
                    leftArrowDrawer(color),
                    HORIZONTAL_ARROW_WIDTH,
                    HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            previousArrow.setImage(
                    downArrowDrawer(color),
                    VERTICAL_ARROW_WIDTH,
                    VERTICAL_ARROW_HEIGHT
            )
        }
        return this
    }

    /**
     * Sets the color of the 'next' arrow to the given one, and sets its image to the default one.
     * @return this
     * @since LLayout 1
     */
    fun setNextArrowColor(color: Color) : AbstractArrowSelector<T> {
        if(isHorizontal){
            nextArrow.setImage(
                    rightArrowDrawer(color),
                    HORIZONTAL_ARROW_WIDTH,
                    HORIZONTAL_ARROW_HEIGHT
            )
        }else{
            nextArrow.setImage(
                    upArrowDrawer(color),
                    VERTICAL_ARROW_WIDTH,
                    VERTICAL_ARROW_HEIGHT
            )
        }
        return this
    }

    /**
     * Sets a new image to the 'previous' arrow.
     * @param image The new image.
     * @return this
     * @since LLayout 1
     */
    fun setPreviousArrowImage(image : GraphicAction) : AbstractArrowSelector<T>{
        previousArrow.setImage(image, previousArrow.width(), previousArrow.height())
        return this
    }

    /**
     * Sets a new image to the 'next' arrow.
     * @param image The new image.
     * @return this
     * @since LLayout 1
     */
    fun setNextArrowImage(image : GraphicAction) : AbstractArrowSelector<T>{
        nextArrow.setImage(image, nextArrow.width(), nextArrow.height())
        return this
    }

    /**
     * Selects the next option.
     * @since LLayout 1
     */
    protected open fun next(){
        if(currentOptionIndex.value < optionsNumber() - 1){
            currentOptionIndex.value++
        }else{
            currentOptionIndex.value = 0
        }
        initialize()
    }

    /**
     * Selects the previous option.
     * @since LLayout 1
     */
    protected open fun previous(){
        if(currentOptionIndex.value > 0){
            currentOptionIndex.value--
        }else{
            currentOptionIndex.value = optionsNumber() - 1
        }
        initialize()
    }

    /**
     * Returns the isHorizontal parameter.
     * @see isHorizontal
     * @since LLayout 1
     */
    protected fun isHorizontal() : Boolean = isHorizontal

    /**
     * Initializes the 'previous' arrow to a default one.
     * @return A default 'previous' arrow.
     * @since LLayout 1
     */
    private fun initializePreviousArrow() : ImageButton{
        return if(isHorizontal){
            ImageButton(HORIZONTAL_ARROW_WIDTH, HORIZONTAL_ARROW_HEIGHT, DEFAULT_LEFT_ARROW_DRAWER){previous()}
        }else{
            ImageButton(VERTICAL_ARROW_WIDTH, VERTICAL_ARROW_HEIGHT, DEFAULT_DOWN_ARROW_DRAWER){previous()}
        }
    }

    /**
     * Initializes the 'next' arrow to a default one.
     * @return A default 'next' arrow.
     * @since LLayout 1
     */
    private fun initializeNextArrow() : ImageButton{
        return if(isHorizontal){
            ImageButton(HORIZONTAL_ARROW_WIDTH, HORIZONTAL_ARROW_HEIGHT, DEFAULT_RIGHT_ARROW_DRAWER){next()}
        }else{
            ImageButton(VERTICAL_ARROW_WIDTH, VERTICAL_ARROW_HEIGHT, DEFAULT_UP_ARROW_DRAWER){next()}
        }
    }

    /**
     * Sets the positions of the arrows.
     * @since LLayout 1
     */
    private fun alignArrows(){
        core.addDisplayers(previousArrow, nextArrow)
        if(isHorizontal){
            previousArrow.setY(0.5).alignLeftTo(0.0)
            nextArrow.setY(0.5).alignRightTo(1.0)
        }else{
            previousArrow.setX(0.5).alignBottomTo(1.0)
            nextArrow.setX(0.5).alignTopTo(0.0)
        }
    }

}
