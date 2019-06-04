package llayout.frame

import llayout.*
import llayout.displayers.cores.DisplayerCore
import llayout.interfaces.*
import java.awt.Graphics
import javax.swing.JPanel
import llayout.utilities.LObservable
import java.awt.event.*

/**
 * The general abstraction for a background pane. A LSceneCore is a special kind of JPanel that is used in this Layout.
 * Every scene that appears in a LFrame is a LSceneCore.
 * @see StandardLContainer
 * @see DisplayerCore
 * @see LSceneManager
 * @see JPanel
 * @see LFrameCore
 */
internal class LSceneCore : JPanel(), StandardLContainer, LTimerUpdatable, Canvas {

    private var w : LObservable<Int> = LObservable(0)

    private var h : LObservable<Int> = LObservable(0)

    override var graphics: MutableMap<Any?, GraphicAction> = mutableMapOf()

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

    private var onSave : Action = {}
    private var onLoad : Action = {}

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

    fun setOnMouseClickedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseClickedAction = action
        return this
    }

    fun setOnMousePressedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMousePressedAction = action
        return this
    }

    fun setOnMouseReleasedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseReleasedAction = action
        return this
    }

    fun setOnMouseEnteredAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseEnteredAction = action
        return this
    }

    fun setOnMouseExitedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseExitedAction = action
        return this
    }

    fun setOnMouseMovedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseMovedAction = action
        return this
    }

    fun setOnMouseDraggedAction(action : (e : MouseEvent) -> Unit) : LSceneCore{
        onMouseDraggedAction = action
        return this
    }

    fun setOnMouseWheelMovedAction(action : (e : MouseWheelEvent) -> Unit) : LSceneCore{
        onMouseWheelMovedAction = action
        return this
    }

    fun setOnKeyPressedAction(action : (e : KeyEvent) -> Unit) : LSceneCore{
        onKeyPressedAction = action
        return this
    }

    fun setOnKeyReleasedAction(action : (e : KeyEvent) -> Unit) : LSceneCore{
        onKeyReleasedAction = action
        return this
    }

    fun setOnKeyTypedAction(action : (e : KeyEvent) -> Unit) : LSceneCore{
        onKeyTypedAction = action
        return this
    }

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
     */
    internal fun save() = onSave()

    /**
     * Loads the LSceneCore when it's added to the main frame.
     * @see save
     */
    internal fun load(){
        onLoad()
        initialization()
    }

    internal fun setOnSaveAction(action : Action){
        onSave = action
    }

    internal fun setOnLoadAction(action : Action){
        onLoad = action
    }

    fun addWidthListener(key : Any?, action : Action) : LSceneCore{
        w.addListener(key, action)
        return this
    }

    fun addWidthListener(action : Action) : LSceneCore{
        w.addListener(action)
        return this
    }

    fun addHeightListener(key : Any?, action : Action) : LSceneCore{
        h.addListener(key, action)
        return this
    }

    fun addHeightListener(action : Action) : LSceneCore{
        h.addListener(action)
        return this
    }

    fun addDimensionListener(key : Any?, action : Action) : LSceneCore = addWidthListener(key, action).addHeightListener(key, action)

    fun addDimensionListener(action : Action) : LSceneCore = addWidthListener(action).addHeightListener(action)

    fun removeWidthListener(key : Any?) : LSceneCore{
        w.removeListener(key)
        return this
    }

    fun removeHeightListener(key : Any?) : LSceneCore{
        h.removeListener(key)
        return this
    }

    fun removeDimensionListener(key : Any?) : LSceneCore = removeWidthListener(key).removeHeightListener(key)

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