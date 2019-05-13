package llayout.displayers

import llayout.Action
import llayout.DEFAULT_COLOR
import llayout.GraphicAction
import llayout.utilities.LProperty
import java.awt.Color
import java.awt.Graphics

class Switch : ResizableDisplayer {

    private companion object{
        private val DEFAULT_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int -> run{
            val lineThickness : Int = 2
            g.color = DEFAULT_COLOR
            g.fillRect(0, 0, lineThickness, h)
            g.fillRect(0, 0, w, lineThickness)
            g.fillRect(0, h - lineThickness, w, lineThickness)
            g.fillRect(w - lineThickness, 0, lineThickness, h)
        }}
        private val DEFAULT_CLICKED_BACKGROUND : GraphicAction = {g : Graphics, w : Int, h : Int -> run{
            val lateralDelta : Int = 5
            DEFAULT_BACKGROUND.invoke(g, w, h)
            g.color = Color(0, 191, 255)
            g.fillRect(lateralDelta, lateralDelta, w - 2 * lateralDelta, h - 2 * lateralDelta)
        }}
    }

    private val value : LProperty<Boolean> = LProperty(false)

    private var background : GraphicAction = DEFAULT_BACKGROUND

    private var clickedBackground : GraphicAction = DEFAULT_CLICKED_BACKGROUND

    override var onMouseRelease: Action = {switch()}

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y, width, height)

    fun setTrue() : Switch{
        value.value = true
        return this
    }

    fun setFalse() : Switch{
        value.value = false
        return this
    }

    fun setValue(v : Boolean) : Switch{
        value.value = v
        return this
    }

    fun switch() : Switch{
        value.value = !value.value
        return this
    }

    fun isOn() : Boolean = value()

    fun isOff() : Boolean = !value()

    fun addValueListener(key : Any?, action : Action) : Switch{
        value.addListener(key, action)
        return this
    }

    fun addValueListener(action : Action) : Switch{
        value.addListener(action)
        return this
    }

    fun value() : Boolean = value.value

    fun setOffBackground(background : GraphicAction) : Switch{
        this.background = background
        return this
    }

    fun setOnBackground(background : GraphicAction) : Switch{
        clickedBackground = background
        return this
    }

    override fun loadParameters(g: Graphics) {}

    override fun drawDisplayer(g: Graphics) {
        if(isOn()) clickedBackground.invoke(g, width(), height()) else background.invoke(g, width(), height())
    }

}