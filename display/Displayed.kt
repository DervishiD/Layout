package display

import geometry.Point
import java.awt.Graphics
import java.lang.IllegalArgumentException
import javax.swing.JLabel

/**
 * General class for displayed texts and buttons
 */
public abstract class Displayed : JLabel {

    /**
     * The central Point of the object
     */
    protected var point : Point

    /**
     * The width of the component
     */
    protected abstract var w : Int

    /**
     * The height of the component
     */
    protected abstract var h : Int

    /**
     * The displayed Text, as a list of StringDisplays.
     * @see StringDisplay
     */
    protected var txt : ArrayList<StringDisplay>

    /**
     * Detects if the component is being initialized
     */
    protected var initphase : Boolean = true

    /**
     * Left alignment
     */
    protected var alignLeftTo : Int? = null
    /**
     * Right alignment
     */
    protected var alignRightTo : Int? = null
    /**
     * Up alignment
     */
    protected var alignUpTo : Int? = null
    /**
     * Down alignment
     */
    protected var alignDownTo : Int? = null

    init{
        setBounds(1, 1, 1, 1)
    }

    constructor(p : Point, text : ArrayList<StringDisplay>) : super(){
        if(text.size == 0){
            throw IllegalArgumentException("A text displayer must display text.")
        }
        point = p
        txt = text
    }
    constructor(p : Point, text : StringDisplay) : this(p, arrayListOf<StringDisplay>(text))
    constructor(p : Point, text : String) : this(p, StringDisplay(text))

    /**
     * Aligns left to the given position
     */
    protected fun alignLeftTo(position : Int){
        alignLeftTo = position
        alignRightTo = null
    }

    /**
     * Aligns right to the given position
     */
    protected fun alignRightTo(position : Int){
        alignRightTo = position
        alignLeftTo = null
    }

    /**
     * Aligns up to the given position
     */
    protected fun alignUpTo(position : Int){
        alignUpTo = position
        alignDownTo = null
    }

    /**
     * Aligns down to the given position
     */
    protected fun alignDownTo(position : Int){
        alignDownTo = position
        alignUpTo = null
    }

    /**
     * Loads the necessary parameters during the initialization phase.
     */
    protected abstract fun loadParameters(g : Graphics)

    /**
     * Draws the background of the component.
     */
    protected abstract fun drawBackground(g : Graphics)

    /**
     * Draws Text on the component.
     */
    protected abstract fun drawText(g : Graphics)

}