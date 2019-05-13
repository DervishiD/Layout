package llayout.frame

import llayout.*
import llayout.displayers.AbstractDisplayerContainer
import llayout.displayers.Displayer
import llayout.interfaces.*
import java.awt.Component
import java.awt.Graphics
import javax.swing.JPanel
import llayout.utilities.LProperty
import java.awt.event.*

/**
 * The general abstraction for a background pane. A LScene is a special kind of JPanel that is used in this Layout.
 * Every scene that appears in a LFrame is a LScene.
 * @see StandardLContainer
 * @see MouseInteractable
 * @see Displayer
 * @see LScreenManager
 * @see JPanel
 * @see LFrameCore
 */
open class LScene : JPanel(), StandardLContainer, MouseInteractable, LTimerUpdatable, Canvas, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    override var w : LProperty<Int> = LProperty(0)

    override var h : LProperty<Int> = LProperty(0)

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    /**
     * The next LScene, as a LProperty.
     * @see setNextScreen
     * @see LProperty
     */
    private var nextLScene : LProperty<LScene?> = LProperty(null)

    override var parts : MutableCollection<Displayable> = mutableListOf()

    override var onMouseClick : Action = {}
    override var onMousePress : Action = {}
    override var onMouseRelease : Action = {}
    override var onMouseEnter : Action = {}
    override var onMouseExit : Action = {}
    override var onMouseDrag : Action = {}
    override var onMouseMove : Action = {}
    override var onMouseWheelMoved : MouseWheelAction = { _ -> }

    init{
        addKeyListener(this)
        addMouseListener(this)
        addMouseMotionListener(this)
        addMouseWheelListener(this)
    }

    /**
     * Sets the next LScene to the given value.
     * @param nextLScene The LScene that appears on the core LFrame after this one.
     * @see nextLScene
     * @see LFrameCore
     */
    protected infix fun setNextScreen(nextLScene : LScene){
        this.nextLScene.value = nextLScene
    }

    public override fun paintComponent(g: Graphics?) {
        for(part : Displayable in parts){
            part.drawDisplayable(g!!)
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
        for(d : Displayable in parts){
            d.updateRelativeValues(width(), height())
        }
    }

    /**
     * The next LScene.
     * @see nextLScene
     */
    fun nextScreen() : LScene? = nextLScene.value

    override fun onTimerTick(): LScene {
        for(d : Displayable in parts){
            d.onTimerTick()
        }
        return this
    }

    override fun keyPressed(e: KeyEvent?){}

    override fun keyReleased(e: KeyEvent?){}

    override fun keyTyped(e: KeyEvent?){}

    override fun mouseMoved(e: MouseEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mouseMoved()
            is AbstractDisplayerContainer ->
                component.displayerAt(e.x - component.leftSideX(), e.y - component.upSideY()).mouseMoved()
            is Displayer -> component.mouseMoved()
        }
        LMouse.setNewMousePosition(e.x, e.y)
        requestFocusInWindow()
    }

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mouseWheelMoved(e.unitsToScroll)
            is AbstractDisplayerContainer ->
                component.displayerAt(x - component.leftSideX(), y - component.upSideY()).mouseWheelMoved(e.unitsToScroll)
            is Displayer -> component.mouseWheelMoved(e.unitsToScroll)
        }
    }

    override fun mouseDragged(e: MouseEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mouseDrag()
            is AbstractDisplayerContainer ->
                component.displayerAt(e.x - component.leftSideX(), e.y - component.upSideY()).mouseDrag()
            is Displayer -> component.mouseDrag()
        }
        LMouse.setNewMousePosition(e.x, e.y)
    }

    override fun mouseReleased(e: MouseEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mouseRelease()
            is AbstractDisplayerContainer ->
                component.displayerAt(e.x - component.leftSideX(), e.y - component.upSideY()).mouseRelease()
            is Displayer -> component.mouseRelease()
        }
    }

    override fun mouseEntered(e: MouseEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mouseEnter()
            is AbstractDisplayerContainer ->
                component.displayerAt(e.x - component.leftSideX(), e.y - component.upSideY()).mouseEnter()
            is Displayer -> component.mouseEnter()
        }
    }

    override fun mouseClicked(e: MouseEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mouseClick()
            is AbstractDisplayerContainer ->
                component.displayerAt(e.x - component.leftSideX(), e.y - component.upSideY()).mouseClick()
            is Displayer -> component.mouseClick()
        }
        requestFocusInWindow()
    }

    override fun mouseExited(e: MouseEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mouseExit()
            is AbstractDisplayerContainer ->
                component.displayerAt(e.x - component.leftSideX(), e.y - component.upSideY()).mouseExit()
            is Displayer -> component.mouseExit()
        }
    }

    override fun mousePressed(e: MouseEvent?) {
        when(val component : Component? = getComponentAt(e!!.x, e.y)){
            is LScene -> mousePress()
            is AbstractDisplayerContainer ->
                component.displayerAt(e.x - component.leftSideX(), e.y - component.upSideY()).mousePress()
            is Displayer -> component.mousePress()
        }
    }

}