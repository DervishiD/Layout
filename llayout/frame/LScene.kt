package llayout.frame

import llayout.*
import llayout.displayers.Displayer
import llayout.interfaces.*
import java.awt.Graphics
import javax.swing.JPanel
import llayout.utilities.LProperty
import java.awt.event.*

/**
 * The general abstraction for a background pane. A LScene is a special kind of JPanel that is used in this Layout.
 * Every scene that appears in a LFrame is a LScene.
 * @see StandardLContainer
 * @see Displayer
 * @see LScreenManager
 * @see JPanel
 * @see LFrameCore
 */
open class LScene : JPanel(), StandardLContainer, LTimerUpdatable, Canvas {

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

    private var onMouseClickedAction : (e : MouseEvent) -> Unit = {}
    private var onMousePressedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseReleasedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseEnteredAction : (e : MouseEvent) -> Unit = {}
    private var onMouseExitedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseMovedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseDraggedAction : (e : MouseEvent) -> Unit = {}
    private var onMouseWheelMovedAction : (e : MouseWheelEvent) -> Unit = {}
    private var onKeyTypedAction : (e : KeyEvent) -> Unit = {}
    private var onKeyPressedAction : (e : KeyEvent) -> Unit = {}
    private var onKeyReleasedAction : (e : KeyEvent) -> Unit = {}

    init{
        addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?){
                requestFocusInWindow()
                onMouseClickedAction(e!!)
            }
            override fun mousePressed(e: MouseEvent?) = onMousePressedAction(e!!)
            override fun mouseReleased(e: MouseEvent?) = onMouseReleasedAction(e!!)
            override fun mouseEntered(e: MouseEvent?) = onMouseEnteredAction(e!!)
            override fun mouseExited(e: MouseEvent?) = onMouseExitedAction(e!!)
        })
        addMouseMotionListener(object : MouseMotionListener{
            override fun mouseMoved(e: MouseEvent?) = onMouseMovedAction(e!!)
            override fun mouseDragged(e: MouseEvent?) = onMouseDraggedAction(e!!)
        })
        addMouseWheelListener { e -> onMouseWheelMovedAction(e!!) }
        addKeyListener(object : KeyListener{
            override fun keyTyped(e: KeyEvent?) = onKeyTypedAction(e!!)
            override fun keyPressed(e: KeyEvent?) = onKeyPressedAction(e!!)
            override fun keyReleased(e: KeyEvent?) = onKeyReleasedAction(e!!)
        })
    }

    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : LScene{
        onMouseClickedAction = action
        return this
    }

    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : LScene{
        onMousePressedAction = action
        return this
    }

    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : LScene{
        onMouseReleasedAction = action
        return this
    }

    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : LScene{
        onMouseEnteredAction = action
        return this
    }

    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : LScene{
        onMouseExitedAction = action
        return this
    }

    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : LScene{
        onMouseMovedAction = action
        return this
    }

    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : LScene{
        onMouseDraggedAction = action
        return this
    }

    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : LScene{
        onMouseWheelMovedAction = action
        return this
    }

    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : LScene{
        onKeyPressedAction = action
        return this
    }

    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : LScene{
        onKeyReleasedAction = action
        return this
    }

    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : LScene{
        onKeyTypedAction = action
        return this
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

}