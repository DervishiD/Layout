package llayout.frame

import llayout.*
import llayout.displayers.AbstractDisplayerContainer
import llayout.displayers.Displayer
import llayout.interfaces.Canvas
import llayout.interfaces.LContainer
import llayout.interfaces.LTimerUpdatable
import llayout.interfaces.MouseInteractable
import java.awt.Component
import java.awt.Graphics
import javax.swing.JPanel
import llayout.utilities.LProperty

/**
 * The general abstraction for a LScene. A LScene is a special kind of JPanel that is used in this Layout.
 * Every scene that appears in a LFrame is a LScene.
 * @see LContainer
 * @see MouseInteractable
 * @see Displayer
 * @see LScreenManager
 * @see JPanel
 * @see LFrame
 */
abstract class LScene : JPanel(), LContainer, MouseInteractable, LTimerUpdatable, Canvas {

    override var w : LProperty<Int> = LProperty(0)

    override var h : LProperty<Int> = LProperty(0)

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    /**
     * The next LScene, as a LProperty.
     * @see setNextScreen
     * @see LProperty
     */
    private var nextLScene : LProperty<LScene?> = LProperty(null)

    override var parts : MutableCollection<Displayer> = mutableListOf()

    override var onClick : Action = {}
    override var onPress : Action = {}
    override var onRelease : Action = {}
    override var onEnter : Action = {}
    override var onExit : Action = {}
    override var onDrag : Action = {}
    override var onMove : Action = {}
    override var onWheelMoved : MouseWheelAction = { _ -> }

    /**
     * Sets the next LScene to the given value.
     * @param nextLScene The LScene that appears on the core LFrame after this one.
     * @see nextLScene
     * @see LFrame
     */
    protected infix fun setNextScreen(nextLScene : LScene){
        this.nextLScene.value = nextLScene
    }

    public override fun paintComponent(g: Graphics?) {
        for(part : Displayer in parts){
            part.paintComponent(g)
        }
        g!!.clearRect(0, 0, width, height)
        drawBackground(g)
    }

    /**
     * Saves the state of the LScene when it's removed from the main frame.
     * @see load
     */
    open fun save(){}

    /**
     * Loads the LScene when it's added to the main frame.
     * @see save
     */
    open fun load(){}

    /**
     * Adds a LScene change listener to the nextLScene LProperty.
     * @param key The key of the added listener.
     * @param action The Action executed by the listener.
     * @see nextLScene
     * @see LProperty
     * @see Action
     */
    fun addScreenChangeListener(key : Any?, action : Action) = nextLScene.addListener(key, action)

    /**
     * Removes a LScene change listener from the nextLScene LProperty.
     * @param key The key of the listener to remove.
     * @see LProperty
     * @see nextLScene
     */
    fun removeScreenChangeListener(key : Any?) = nextLScene.removeListener(key)

    fun setBounds(width : Int, height : Int) = setBounds(0, 0, width, height)

    override fun setBounds(x: Int, y: Int, width: Int, height: Int) {
        super.setBounds(x, y, width, height)
        w.value = width
        h.value = height
        for(d : Displayer in parts){
            d.updateRelativeValues(width(), height())
        }
    }

    /**
     * The next LScene.
     * @see nextLScene
     */
    fun nextScreen() : LScene? = nextLScene.value

    override fun mouseClick(x : Int, y : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mouseClick()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseClick()
            is Displayer -> component.mouseClick()
        }
    }

    override fun mousePress(x : Int, y : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mousePress()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mousePress()
            is Displayer -> component.mousePress()
        }
    }

    override fun mouseRelease(x : Int, y : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mouseRelease()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseRelease()
            is Displayer -> component.mouseRelease()
        }
    }

    override fun mouseEnter(x : Int, y : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mouseEnter()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseEnter()
            is Displayer -> component.mouseEnter()
        }
    }

    override fun mouseExit(x : Int, y : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mouseExit()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseExit()
            is Displayer -> component.mouseExit()
        }
    }

    override fun mouseDrag(x : Int, y : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mouseDrag()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseDrag()
            is Displayer -> component.mouseDrag()
        }
    }

    override fun mouseMoved(x : Int, y : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mouseMoved()
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseMoved()
            is Displayer -> component.mouseMoved()
        }
        requestFocus()
    }

    override fun mouseWheelMoved(x : Int, y : Int, units : Int){
        when(val component : Component? = getComponentAt(x, y)){
            is LScene -> mouseWheelMoved(units)
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseWheelMoved(units)
            is Displayer -> component.mouseWheelMoved(units)
        }
    }

    override fun onTimerTick(): LScene {
        for(d : Displayer in parts){
            d.onTimerTick()
        }
        return this
    }

}