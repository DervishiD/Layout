package llayout5.frame

import llayout5.*
import llayout5.displayers.cores.DisplayerCore
import llayout5.interfaces.*
import java.awt.Graphics
import javax.swing.JPanel
import llayout5.utilities.LObservable
import java.awt.event.*

/**
 * The general abstraction for a background pane. A LSceneCore is a special kind of JPanel that is used in this Layout.
 * Every scene that appears in a LFrame is a LSceneCore.
 * @see StandardLContainer
 * @see DisplayerCore
 * @see LSceneManager
 * @see JPanel
 * @see LFrameCore
 * @since LLayout 1
 */
internal class LSceneCore : JPanel(), StandardLContainer, LTimerUpdatable, Canvas {

    /**
     * The width of this LSceneCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var w : LObservable<Int> = LObservable(0)

    /**
     * The height of this LSceneCore.
     * @see LObservable
     * @since LLayout 1
     */
    private var h : LObservable<Int> = LObservable(0)

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

    override var parts : MutableCollection<Displayable> = mutableListOf()

    /**
     * The action executed when the mouse clicks this LSceneCore.
     * @since LLayout 1
     */
    private var onMouseClickedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse presses this LSceneCore.
     * @since LLayout 1
     */
    private var onMousePressedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse releases this LSceneCore.
     * @since LLayout 1
     */
    private var onMouseReleasedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse enters this LSceneCore.
     * @since LLayout 1
     */
    private var onMouseEnteredAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse exits this LSceneCore.
     * @since LLayout 1
     */
    private var onMouseExitedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse moves over this LSceneCore.
     * @since LLayout 1
     */
    private var onMouseMovedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse drags this LSceneCore.
     * @since LLayout 1
     */
    private var onMouseDraggedAction : (e : MouseEvent) -> Unit = {}

    /**
     * The action executed when the mouse wheel moves over this LSceneCore.
     * @since LLayout 1
     */
    private var onMouseWheelMovedAction : (e : MouseWheelEvent) -> Unit = {}

    /**
     * The action executed when the user types a key while this LSceneCore has the input focus.
     * @since LLayout 1
     */
    private var onKeyTypedAction : (e : KeyEvent) -> Unit = {}

    /**
     * The action executed when the user presses a key while this LSceneCore has the input focus.
     * @since LLayout 1
     */
    private var onKeyPressedAction : (e : KeyEvent) -> Unit = {}

    /**
     * The action executed when the user releases a key while this LSceneCore has the input focus.
     * @since LLayout 1
     */
    private var onKeyReleasedAction : (e : KeyEvent) -> Unit = {}

    /**
     * The action executed when a new LSceneCore replaces this one on the screen.
     * @since LLayout 1
     */
    private var onSave : Action = {}

    /**
     * The action executed when this LSceneCore replaces another one on the screen.
     * @since LLayout 1
     */
    private var onLoad : Action = {}

    /**
     * The action executed when the timer ticks.
     * @since LLayout 1
     */
    private var onTimerTick : Action = {}

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
        addWidthListener {
            for(d : Displayable in parts){
                d.updateRelativeValues(width(), height())
            }
        }
        addHeightListener {
            for(d : Displayable in parts){
                d.updateRelativeValues(width(), height())
            }
        }
    }

    /**
     * Sets the action executed when the mouse clicks this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseClickedAction = action
        return this
    }

    /**
     * Sets the action executed when the mouse presses this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMousePressedAction = action
        return this
    }

    /**
     * Sets the action executed when the mouse releases this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseReleasedAction = action
        return this
    }

    /**
     * Sets the action executed when the mouse enters this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseEnteredAction = action
        return this
    }

    /**
     * Sets the action executed when the mouse exits this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseExitedAction = action
        return this
    }

    /**
     * Sets the action executed when the mouse moves over this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseMovedAction = action
        return this
    }

    /**
     * Sets the action executed when the mouse drags this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseDraggedAction = action
        return this
    }

    /**
     * Sets the action executed when the mouse wheel moves over this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : LSceneCore{
        onMouseWheelMovedAction = action
        return this
    }

    /**
     * Sets the action executed when the user presses a key while this LSceneCore has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : LSceneCore{
        onKeyPressedAction = action
        return this
    }

    /**
     * Sets the action executed when the user releases a key while this LSceneCore has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : LSceneCore{
        onKeyReleasedAction = action
        return this
    }

    /**
     * Sets the action executed when the user types a key while this LSceneCore has the input focus.
     * @return this
     * @since LLayout 1
     */
    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : LSceneCore{
        onKeyTypedAction = action
        return this
    }

    /**
     * Sets the action executed when the timer ticks.
     * @since LLayout 1
     */
    fun setOnTimerTickAction(action : Action){
        onTimerTick = action
    }

    public override fun paintComponent(g: Graphics?) {
        for(part : Displayable in parts){
            part.drawDisplayable(g!!)
        }
        g!!.clearRect(0, 0, width, height)
        drawBackground(g)
    }

    /**
     * Saves the state of the LSceneCore when it's removed from the main frame.
     * @see load
     * @since LLayout 1
     */
    internal fun save() = onSave()

    /**
     * Loads the LSceneCore when it's added to the main frame.
     * @see save
     * @since LLayout 1
     */
    internal fun load(){
        onLoad()
        initialization()
    }

    /**
     * Sets the action executed when this LSceneCore is replaced by another one on the screen.
     * @since LLayout 1
     */
    internal fun setOnSaveAction(action : Action){
        onSave = action
    }

    /**
     * Sets the action executed when this LSceneCore replaces another one on the screen.
     * @since LLayout 1
     */
    internal fun setOnLoadAction(action : Action){
        onLoad = action
    }

    /**
     * Adds a listener to the width of this LSceneCore.
     * @param key The key associated to the given action
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addWidthListener(key : Any?, action : Action) : LSceneCore{
        w.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the width of this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun addWidthListener(action : Action) : LSceneCore{
        w.addListener(action)
        return this
    }

    /**
     * Adds a listener to the height of this LSceneCore.
     * @param key The key associated to the given action
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addHeightListener(key : Any?, action : Action) : LSceneCore{
        h.addListener(key, action)
        return this
    }

    /**
     * Adds a listener to the height of this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun addHeightListener(action : Action) : LSceneCore{
        h.addListener(action)
        return this
    }

    /**
     * Adds a listener to the width and the height of this LSceneCore.
     * @param key The key associated to the given action
     * @param action The executed action
     * @return this
     * @since LLayout 1
     */
    fun addDimensionListener(key : Any?, action : Action) : LSceneCore = addWidthListener(key, action).addHeightListener(key, action)

    /**
     * Adds a listener to the width and the height of this LSceneCore.
     * @return this
     * @since LLayout 1
     */
    fun addDimensionListener(action : Action) : LSceneCore = addWidthListener(action).addHeightListener(action)

    /**
     * Removes the width listener associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeWidthListener(key : Any?) : LSceneCore{
        w.removeListener(key)
        return this
    }

    /**
     * Removes the height listener associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeHeightListener(key : Any?) : LSceneCore{
        h.removeListener(key)
        return this
    }

    /**
     * removes the dimension listener associated to the given key.
     * @return this
     * @since LLayout 1
     */
    fun removeDimensionListener(key : Any?) : LSceneCore = removeWidthListener(key).removeHeightListener(key)

    /**
     * Sets the bounds of this LSceneCore.
     * @since LLayout 1
     */
    fun setBounds(width : Int, height : Int) = setBounds(0, 0, width, height)

    override fun setBounds(x: Int, y: Int, width: Int, height: Int) {
        super.setBounds(x, y, width, height)
        w.value = width
        h.value = height
        for(d : Displayable in parts){
            d.updateRelativeValues(width(), height())
        }
    }

    override fun onTimerTick() {
        onTimerTick.invoke()
        for(d : Displayable in parts){
            d.onTimerTick()
        }
    }

    override fun width(): Int = w.value

    override fun height() : Int = h.value

}