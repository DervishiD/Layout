package llayout4.displayers

import llayout4.Action
import llayout4.DEFAULT_COLOR
import llayout4.GraphicAction
import llayout4.utilities.LObservable
import java.awt.Color
import java.awt.Graphics

/**
 * A Switch that either is true or false.
 * @see ResizableDisplayer
 * @since LLayout 1
 */
class Switch : ResizableDisplayer(DEFAULT_SIZE, DEFAULT_SIZE) {

    private companion object{

        /**
         * The default background of this Switch.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lineThickness : Int = 2
            g.color = DEFAULT_COLOR
            g.fillRect(0, 0, lineThickness, h)
            g.fillRect(0, 0, w, lineThickness)
            g.fillRect(0, h - lineThickness, w, lineThickness)
            g.fillRect(w - lineThickness, 0, lineThickness, h)
        }}

        /**
         * The default background of this Switch when clicked.
         * @see GraphicAction
         * @since LLayout 1
         */
        private val DEFAULT_CLICKED_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lateralDelta : Int = 5
            DEFAULT_BACKGROUND.invoke(g, w, h)
            g.color = Color(0, 191, 255)
            g.fillRect(lateralDelta, lateralDelta, w - 2 * lateralDelta, h - 2 * lateralDelta)
        }}

        /**
         * The default size of a switch.
         * @since LLayout 1
         */
        private const val DEFAULT_SIZE : Int = 40

    }

    /**
     * The value selected by the Switch, true or false.
     * @see LObservable
     * @since LLayout 1
     */
    private val value : LObservable<Boolean> = LObservable(false)

    /**
     * The background of this Switch.
     * @see GraphicAction
     * @since LLayout 1
     */
    private var background : GraphicAction = DEFAULT_BACKGROUND

    /**
     * The default clicked background of this Switch.
     * @see GraphicAction
     * @since LLayout 1
     */
    private var clickedBackground : GraphicAction = DEFAULT_CLICKED_BACKGROUND

    init{
        setOnMouseReleasedAction { switch() }
        core.addGraphicAction(background, this)
        value.addListener { reloadImage() }
    }

    /**
     * Sets the value of the switch to true.
     * @return this
     * @since LLayout 1
     */
    fun setTrue() : Switch{
        value.value = true
        return this
    }

    /**
     * Sets the value of the Switch to false.
     * @return this
     * @since LLayout 1
     */
    fun setFalse() : Switch{
        value.value = false
        return this
    }

    /**
     * Sets the value of the switch to the given one.
     * @return this
     * @since LLayout 1
     */
    fun setValue(v : Boolean) : Switch{
        value.value = v
        return this
    }

    /**
     * Switches to the other value.
     * @return this
     * @since LLayout 1
     */
    fun switch() : Switch{
        value.value = !value.value
        return this
    }

    /**
     * True if the switch has the value 'true'.
     * @since LLayout 1
     */
    fun isOn() : Boolean = value()

    /**
     * True if the switch has the value 'false'.
     * @since LLayout 1
     */
    fun isOff() : Boolean = !value()

    /**
     * Adds a listener to the value of the Switch.
     * @param key The key associated to the given action.
     * @param action The action performed.
     * @return this
     * @since LLayout 1
     */
    fun addValueListener(key : Any?, action : Action) : Switch{
        value.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the value of the Switch.
     * @return this
     * @since LLayout 1
     */
    fun addValueListener(action : Action) : Switch{
        value.addListener(action)
        return this
    }

    /**
     * Returns the value of the Switch.
     * @since LLayout 1
     */
    fun value() : Boolean = value.value

    /**
     * Sets the background of this Switch that is shown when the selected value is 'false'.
     * @return this
     * @see GraphicAction
     * @since LLayout 1
     */
    fun setOffBackground(background : GraphicAction) : Switch{
        this.background = background
        return this
    }

    /**
     * Sets the background of this Switch that is shown when the selected value is 'true'.
     * @return this
     * @see GraphicAction
     * @since LLayout 1
     */
    fun setOnBackground(background : GraphicAction) : Switch{
        clickedBackground = background
        return this
    }

    /**
     * Changes the background image of this Switch.
     * @since LLayout 1
     */
    private fun reloadImage(){
        core.addGraphicAction(if(isOn()) clickedBackground else background, this)
    }

}