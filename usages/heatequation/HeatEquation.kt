package usages.heatequation

import llayout.DEFAULT_COLOR
import llayout.displayers.Label
import llayout.displayers.TextButton
import llayout.displayers.VerticalDoubleSlider
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LScene
import java.awt.Graphics
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sqrt

val heatEquationApplication : LApplication = LApplication{
    mainFrame.run()
    graphFrame.run()
}

private val mainScene : LScene = object : LScene(){

    private val kSlider : VerticalDoubleSlider = VerticalDoubleSlider(0.3, 0.5, 50, 0.9)

    private val kLabel : Label = Label(0, 0.5, "k")

    private val rangeSlider : VerticalDoubleSlider = VerticalDoubleSlider(0.6, 0.5, 50, 0.9)

    private val rangeLabel : Label = Label(0, 0.5, "Range")

    private val reloadButton : TextButton = TextButton(0.8, 0.2, "Reload", {reload()})

    init{
        kSlider.setMinimum(0.05).setMaximum(5).setPrecision(0.05)
        kLabel.alignLeftToRight(kSlider, 5)
        rangeSlider.setMinimum(0.5).setMaximum(5).setPrecision(0.5)
        rangeLabel.alignLeftToRight(rangeSlider, 5)
        add(kLabel)
        add(kSlider)
        add(rangeLabel)
        add(rangeSlider)
        add(reloadButton)
    }

    private fun reload() = graphScene.reload(kSlider.value(), rangeSlider.value())

}

private val mainFrame : LFrame = LFrame(mainScene)

private class GraphScene : LScene(){

    companion object{
        private const val DEFAULT_TIME : Double = 0.001
        private const val DEFAULT_K : Double = 1.0
        private const val DEFAULT_RANGE : Double = 1.0
        private const val DELTA_T : Double = 0.001
    }

    private var k : Double = DEFAULT_K

    private var t : Double = DEFAULT_TIME

    private var range : Double = DEFAULT_RANGE

    init{
        w.addListener{addPoints()}
        h.addListener{addPoints()}
    }

    private fun k() : Double = k

    private fun t() : Double = t

    private fun f(x : Double) : Double = f(x, t())

    private fun f(x : Double, t : Double) : Double = 1 / sqrt(4 * PI * k() * t) * exp(- x * x / (4 * k() * t))

    private fun xOfPixel(i : Int) : Double = ( - 0.5 + (i.toDouble() / width()) ) * range

    private fun maximum() : Double = f(0.0, DEFAULT_TIME)

    private fun pixelYToY(y : Double) : Int = ( height() * (1 - (y / maximum()) ) ).toInt()

    fun addPoints(){
        graphics.clear()
        for(i : Int in 0..width()){
            addGraphicAction{g : Graphics, _, _ -> run{
                g.color = DEFAULT_COLOR
                g.fillRect(i, pixelYToY(f(xOfPixel(i))), 1, 1)
            }}
        }
    }

    fun reload(k : Double, range : Double){
        this.k = k
        this.range = range
        t = DEFAULT_TIME
    }

    override fun load(){
        addPoints()
    }

    override fun onTimerTick() {
        t += DELTA_T
    }

}

private val graphScene : GraphScene = GraphScene()

private val graphFrame : LFrame = LFrame(graphScene)
