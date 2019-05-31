package llayout.displayers

import llayout.GraphicAction
import llayout.interfaces.AbstractSelector
import llayout.utilities.LObservable
import java.awt.Color
import java.awt.Graphics

open class AbstractArrowSelector<T> : Displayer, AbstractSelector<T> {

    protected companion object {

        @JvmStatic protected val HORIZONTAL_ARROW_WIDTH : Int = 25

        private const val HORIZONTAL_ARROW_HEIGHT : Int = 25

        private const val VERTICAL_ARROW_WIDTH : Int = 25

        @JvmStatic protected val VERTICAL_ARROW_HEIGHT : Int = 25

        private val DEFAULT_ARROW_COLOR : Color = Color(34, 139, 34)

        private fun leftArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h/2, 0, h), 3)
        }}

        private fun rightArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, 0, w), intArrayOf(0, h, h/2), 3)
        }}

        private fun upArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(h, 0, h), 3)
        }}

        private fun downArrowDrawer(color : Color = DEFAULT_ARROW_COLOR) : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            g.color = color
            g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(0, h, 0), 3)
        }}

        private val DEFAULT_LEFT_ARROW_DRAWER : GraphicAction = leftArrowDrawer()

        private val DEFAULT_RIGHT_ARROW_DRAWER : GraphicAction = rightArrowDrawer()

        private val DEFAULT_UP_ARROW_DRAWER : GraphicAction = upArrowDrawer()

        private val DEFAULT_DOWN_ARROW_DRAWER : GraphicAction = downArrowDrawer()

        @JvmStatic protected val EXTERIOR_DELTA : Int = 5

    }

    private val previousArrow : ImageButton

    private val nextArrow : ImageButton

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

    fun setArrowsColor(color : Color) : AbstractArrowSelector<T> {
        setPreviousArrowColor(color)
        setNextArrowColor(color)
        return this
    }

    /**
     * Sets the colour of the 'previous' arrow.
     * @see leftArrowDrawer
     * @see downArrowDrawer
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
     * Sets the colour of the 'next' arrow.
     * @see rightArrowDrawer
     * @see upArrowDrawer
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

    fun setPreviousArrowImage(image : GraphicAction) : AbstractArrowSelector<T>{
        previousArrow.setImage(image, previousArrow.width(), previousArrow.height())
        return this
    }

    fun setNextArrowImage(image : GraphicAction) : AbstractArrowSelector<T>{
        nextArrow.setImage(image, nextArrow.width(), nextArrow.height())
        return this
    }

    protected open fun next(){
        if(currentOptionIndex.value < optionsNumber() - 1){
            currentOptionIndex.value++
        }else{
            currentOptionIndex.value = 0
        }
        initialize()
    }

    protected open fun previous(){
        if(currentOptionIndex.value > 0){
            currentOptionIndex.value--
        }else{
            currentOptionIndex.value = optionsNumber() - 1
        }
        initialize()
    }

    protected fun isHorizontal() : Boolean = isHorizontal

    private fun initializePreviousArrow() : ImageButton{
        return if(isHorizontal){
            ImageButton(HORIZONTAL_ARROW_WIDTH, HORIZONTAL_ARROW_HEIGHT, DEFAULT_LEFT_ARROW_DRAWER){previous()}
        }else{
            ImageButton(VERTICAL_ARROW_WIDTH, VERTICAL_ARROW_HEIGHT, DEFAULT_DOWN_ARROW_DRAWER){previous()}
        }
    }

    private fun initializeNextArrow() : ImageButton{
        return if(isHorizontal){
            ImageButton(HORIZONTAL_ARROW_WIDTH, HORIZONTAL_ARROW_HEIGHT, DEFAULT_RIGHT_ARROW_DRAWER){next()}
        }else{
            ImageButton(VERTICAL_ARROW_WIDTH, VERTICAL_ARROW_HEIGHT, DEFAULT_UP_ARROW_DRAWER){next()}
        }
    }

    private fun alignArrows(){
        core.addDisplayers(previousArrow, nextArrow)
        if(isHorizontal){
            previousArrow.setY(0.5).alignLeftTo(0.0)
            nextArrow.setY(0.5).alignRightTo(1.0)
        }else{
            previousArrow.setX(0.5).alignDownTo(1.0)
            nextArrow.setX(0.5).alignUpTo(0.0)
        }
    }

}
